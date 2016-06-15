package hdm.stuttgart.csm.smarthome.prototyp;

import hdm.stuttgart.csm.smarthome.event.ClusterEventHandler;
import io.socket.client.Socket;

public class NodeClusterEventHandler extends ClusterEventHandler{

	// constants
	private String EVENT_SENSFLOOR = "event_sensfloor";
	
	// attributes
	private boolean debug;
	private Socket socket;
	
	public NodeClusterEventHandler(Socket socket, boolean debug) {
		this.socket = socket;
		this.debug = debug;
	}
	
	@Override
	public void execute() {	
		// if certain criteria is met, notify node server
		if(getCOG().getCapacity() >= 120){
			if(debug){
				debug();
			}
			socket.emit(EVENT_SENSFLOOR, "{status: on}");
		}
	}
	
	private void debug(){
		System.out.println("Im sending information to node");
	}

}
