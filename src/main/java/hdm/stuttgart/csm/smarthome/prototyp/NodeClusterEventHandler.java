package hdm.stuttgart.csm.smarthome.prototyp;

import hdm.stuttgart.csm.smarthome.event.ClusterEventHandler;
import hdm.stuttgart.csm.smarthome.object.Tile;
import io.socket.client.Socket;

public class NodeClusterEventHandler extends ClusterEventHandler{

	// constants
	private String EVENT_SENSFLOOR = "event_sensfloor";
	public String EVENT_REFERENCE = "get_reference";
	
	// attributes
	private boolean debug;
	private Socket socket;
	private Tile referenceTile;
	
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
		// notify if tile changed
		if(path.getReferenceTile() != null){
			// cache current tile
			if(!path.getReferenceTile().equals(referenceTile)){
				double x = path.getReferenceTile().getPosX();
				double y = path.getReferenceTile().getPosY();
				System.out.println("Caching and Emitting da reference");
				socket.emit(EVENT_REFERENCE, "{x: " + x  + " y: " + y + "}");
				this.referenceTile = path.getReferenceTile();
			}
		}
	}
	
	private void debug(){
		System.out.println("Im sending information to node");
	}

}
