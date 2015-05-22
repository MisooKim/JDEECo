package cz.cuni.mff.d3s.deeco.timer;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import cz.cuni.mff.d3s.deeco.runtime.DEECoContainer;

public class DiscreteEventTimer implements SimulationTimer {

	Queue<EventTime> eventTimes;
	Map<DEECoContainer, EventTime> containerEvents;
	long currentTime;

	public DiscreteEventTimer() {
		currentTime = 0;
		eventTimes = new PriorityQueue<>();
		containerEvents = new HashMap<>();
	}

	@Override
	public long getCurrentMilliseconds() {
		return currentTime;
	}

	@Override
	public void start(long duration) {

		eventTimes.add(new EventTime(duration, new TimerEventListener() {
			@Override
			public void at(long time) {
				// termination time reached, do nothing
			}
		}, true));

		while (!tryToTerminate()) {
			EventTime eventTime = eventTimes.remove();
			currentTime = eventTime.getTimePoint();
			eventTime.getListener().at(currentTime);
		}
	}

	/**
	 * Set notification event for container
	 * 
	 * NOTE: Only one event per container is registered
	 */
	@Override
	public void notifyAt(long time, TimerEventListener listener, DEECoContainer container) {
		EventTime eventTime = new EventTime(time, listener, false);
		if (!eventTimes.contains(eventTime)) {
			// Replace old event for container by the new one
			eventTimes.add(eventTime);
			EventTime old = containerEvents.get(container);
			if (old != null) {
				eventTimes.remove(old);
			}
			containerEvents.put(container, eventTime);
		}
	}

	boolean tryToTerminate() {
		if (eventTimes.isEmpty()) {
			return true;
		}
		EventTime eventTime = eventTimes.peek();
		if (!eventTime.isTerminationEvent()) {
			return false;
		}
		currentTime = eventTime.getTimePoint();
		eventTimes.clear();
		return true;
	}

}