package cz.cuni.mff.d3s.jdeeco.ros;

import org.ros.node.ConnectedNode;

public class Communication extends TopicSubscriber {
	
	Communication(){}
	
	@Override
	protected void subscribeDescendant(ConnectedNode connectedNode) {
		// TODO Auto-generated method stub
		
	}

}

/* TODO:
 
+ /mobile_base/events/cliff
+ /mobile_base/version_info
+ /tf
 
*/