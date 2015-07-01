package cz.cuni.mff.d3s.jdeeco.ros;

import geometry_msgs.Twist;

import java.util.HashMap;
import java.util.Map;

import kobuki_msgs.WheelDropEvent;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import cz.cuni.mff.d3s.jdeeco.ros.datatypes.MotorPower;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.WheelID;
import cz.cuni.mff.d3s.jdeeco.ros.datatypes.WheelState;

/**
 * Provides methods to command wheel actuators and to obtain data from wheel
 * sensors through ROS. Registration of appropriate ROS topics is handled in the
 * {@link #subscribe(ConnectedNode)} method.
 * 
 * @author Dominik Skoda <skoda@d3s.mff.cuni.cz>
 */
public class Wheels extends TopicSubscriber {

	/**
	 * The name of the velocity topic.
	 */
	private static final String VELOCITY_TOPIC = "/mobile_base/commands/velocity";
	/**
	 * The name of the topic for motor power messages.
	 */
	private static final String MOTOR_POWER_TOPIC = "/mobile_base/commands/motor_power";
	/**
	 * The name of the topic to report wheel drop changes.
	 */
	private static final String WHEEL_DROP_TOPIC = "/mobile_base/events/wheel_drop";

	/**
	 * The maximum absolute linear speed. Value taken from kobuki_keyop.
	 */
	private static final double MAX_LINEAR_VELOCITY = 1.5;
	/**
	 * The maximum absolute angular speed. Value taken from kobuki_keyop.
	 */
	private static final double MAX_ANGULAR_VELOCITY = 6.6;
	/**
	 * The time period (in milliseconds) of sending the velocity updates to the
	 * robot. If the robot doesn't receive velocity update for 600 ms the motor
	 * power is automatically being set to 0. The less the period is, the less
	 * is the latency from a DEECo component setting the value to the change of
	 * the robot's velocity.
	 */
	private static final long VELOCITY_UPDATE_PERIOD = 500;

	/**
	 * Current linear speed. Range from -{@link #MAX_LINEAR_VELOCITY} to +
	 * {@link #MAX_LINEAR_VELOCITY}.
	 */
	private double linearVelocity;
	/**
	 * Current angular speed. Range from -{@link #MAX_ANGULAR_VELOCITY} to +
	 * {@link #MAX_ANGULAR_VELOCITY}.
	 */
	private double angularVelocity;

	/**
	 * The state of motor power to be set.
	 */
	private MotorPower motorPower;
	/**
	 * The lock to wait and notify on when a motor power should be set.
	 */
	private final Object motorPowerLock;

	/**
	 * The map of wheel states.
	 */
	private Map<WheelID, WheelState> wheelState;

	/**
	 * Internal constructor enables the {@link RosServices} to be in the control
	 * of instantiating {@link Wheels}.
	 */
	Wheels() {
		motorPowerLock = new Object();

		wheelState = new HashMap<>();
		for (WheelID wheel : WheelID.values()) {
			wheelState.put(wheel, WheelState.RAISED);
		}
		motorPower = MotorPower.ON;
	}

	/**
	 * Register and subscribe to required ROS topics for actuator commands.
	 * 
	 * @param connectedNode
	 *            The ROS node on which the DEECo node runs.
	 */
	@Override
	protected void subscribeDescendant(ConnectedNode connectedNode) {
		subscribeVelocity(connectedNode);
		subscribeMotorPower(connectedNode);
		subscribeWheelDrop(connectedNode);
	}

	/**
	 * Subscribe to the ROS topic for velocity.
	 * 
	 * @param connectedNode
	 *            The ROS node on which the DEECo node runs.
	 */
	private void subscribeVelocity(ConnectedNode connectedNode) {
		final Publisher<Twist> velocityTopic = connectedNode.newPublisher(
				VELOCITY_TOPIC, Twist._TYPE);
		connectedNode.executeCancellableLoop(new CancellableLoop() {
			@Override
			protected void setup() {
				linearVelocity = 0;
				angularVelocity = 0;
			}

			@Override
			protected void loop() throws InterruptedException {
				Twist twist = velocityTopic.newMessage();
				// Set the linear velocity (X axis)
				twist.getLinear().setX(linearVelocity);
				twist.getLinear().setY(0);
				twist.getLinear().setZ(0);
				// Set the angular velocity (Z axis)
				twist.getAngular().setX(0);
				twist.getAngular().setY(0);
				twist.getAngular().setZ(angularVelocity);
				// TODO: logging
				velocityTopic.publish(twist);
				Thread.sleep(VELOCITY_UPDATE_PERIOD);
			}
		});
	}

	/**
	 * Subscribe to the ROS topic for motor power messages. To publish motor
	 * power message wait until notified by the
	 * {@link #setMotorPower(MotorPower)} setter.
	 * 
	 * @param connectedNode
	 *            The ROS node on which the DEECo node runs.
	 */
	private void subscribeMotorPower(ConnectedNode connectedNode) {
		// Subscribe to publish motor power changes
		final Publisher<kobuki_msgs.MotorPower> motorPowerTopicPub = connectedNode
				.newPublisher(MOTOR_POWER_TOPIC, kobuki_msgs.MotorPower._TYPE);
		connectedNode.executeCancellableLoop(new CancellableLoop() {
			@Override
			protected void setup() {
				motorPower = MotorPower.ON;
			}

			@Override
			protected void loop() throws InterruptedException {
				synchronized (motorPowerLock) {
					motorPowerLock.wait();
				}

				kobuki_msgs.MotorPower motorPowerMsg = motorPowerTopicPub
						.newMessage();
				motorPowerMsg.setState(motorPower.value);
				motorPowerTopicPub.publish(motorPowerMsg);
				// TODO: log
			}
		});
		
		// Subscribe to listen on motor power changes
		Subscriber<kobuki_msgs.MotorPower> motorPowerTopicSub = connectedNode
				.newSubscriber(MOTOR_POWER_TOPIC, kobuki_msgs.MotorPower._TYPE);
		motorPowerTopicSub
				.addMessageListener(new MessageListener<kobuki_msgs.MotorPower>() {
					@Override
					public void onNewMessage(kobuki_msgs.MotorPower message) {

						MotorPower parsedMotorPower = MotorPower
								.fromByte(message.getState());
						if (parsedMotorPower != null) {
							motorPower = parsedMotorPower;
						}
						// TODO: log
					}
				});
	}
	
	/**
	 * Subscribe to the ROS topic for wheel drop changes.
	 * 
	 * @param connectedNode
	 *            The ROS node on which the DEECo node runs.
	 */
	private void subscribeWheelDrop(ConnectedNode connectedNode) {
		Subscriber<WheelDropEvent> bumperTopic = connectedNode.newSubscriber(
				WHEEL_DROP_TOPIC, WheelDropEvent._TYPE);
		bumperTopic.addMessageListener(new MessageListener<WheelDropEvent>() {
			@Override
			public void onNewMessage(WheelDropEvent message) {

				WheelID wheel = WheelID.fromByte(message.getWheel());
				WheelState state = WheelState.fromByte(message.getState());
				if (wheel != null && state != null) {
					wheelState.put(wheel, state);
				}
				// TODO: log
			}
		});
	}

	/**
	 * Set the velocity. Use normalized values from -1 to 1. The sign determines
	 * direction and the value percentage of motor power. If a value outside of
	 * the range is provided it is used the nearest bound value.
	 * 
	 * @param linear
	 *            The linear velocity. Value in [-1, 1]. Negative value moves
	 *            the robot backwards. Positive value moves the robot forwards.
	 * @param angular
	 *            The angular velocity. Value in [-1, 1]. Negative value turns
	 *            the robot right. Positive value turns the robot left.
	 */
	public void setVelocity(double linear, double angular) {
		if (linear < -1) {
			linear = -1;
		}
		if (linear > 1) {
			linear = 1;
		}
		if (angular < -1) {
			angular = -1;
		}
		if (angular > 1) {
			angular = 1;
		}

		linearVelocity = fromNormalized(linear, MAX_LINEAR_VELOCITY);
		angularVelocity = fromNormalized(angular, MAX_ANGULAR_VELOCITY);
	}

	/**
	 * Enable or disable robot's motors.
	 * 
	 * @param motorPower
	 *            The motor power state to be set.
	 */
	public void setMotorPower(MotorPower motorPower) {
		this.motorPower = motorPower;
		synchronized (motorPowerLock) {
			motorPowerLock.notify();
		}
	}

	/**
	 * Get the state of robot's motor power.
	 * 
	 * @return the state of motor power.
	 */
	public MotorPower getMotorPower() {
		return motorPower;
	}
	
	/**
	 * Get the state of specified wheel of the robot.
	 * 
	 * @param wheel
	 *            The wheel to obtain the state of.
	 * @return The state of the specified wheel. Null If the specified wheel is
	 *         not valid.
	 */
	public WheelState getWheelState(WheelID wheel) {
		if (wheelState.containsKey(wheel)) {
			return wheelState.get(wheel);
		}
		return null;
	}

	/**
	 * Transform the normalized value to absolute using the given maximum.
	 * 
	 * @param normalized
	 *            The value to be transformed. If it's bigger than 1 then 1 is
	 *            used instead. If it's smaller than -1 then -1 is used instead.
	 * @param max
	 *            The maximum absolute value.
	 * @return The absolute value obtained from the normalized by it's
	 *         multiplication with max.
	 */
	private double fromNormalized(double normalized, double max) {
		return normalized * max;
	}
}