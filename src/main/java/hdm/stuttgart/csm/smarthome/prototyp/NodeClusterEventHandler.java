package hdm.stuttgart.csm.smarthome.prototyp;

import hdm.stuttgart.csm.smarthome.event.ClusterEventHandler;
import hdm.stuttgart.csm.smarthome.object.Direction;
import hdm.stuttgart.csm.smarthome.object.Tile;
import io.socket.client.Socket;

public class NodeClusterEventHandler extends ClusterEventHandler{

	// constants
	private String EVENT_SENSFLOOR = "event_sensfloor";
	public String EVENT_REFERENCE = "get_reference";
	
	// attributes
	private Socket socket;
	private Tile referenceTile;
	
	public NodeClusterEventHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void execute() {	
		fireCarpetEvent();
		fireReferenceEvent();
	}
	
	/**
	 * Event is fired to node server in refernce tile is approached
	 */
	private void fireCarpetEvent(){
		// if certain criteria is met, notify node server
		String data = "";
		if(getPath().getReferenceDirection() == Direction.DECR_REFERENCE_DIST){
			data = "{\"status\": \"on\"}";
			socket.emit(EVENT_SENSFLOOR, data);
		} else if(getPath().getReferenceDirection() == Direction.INCR_REFERENCE_DIST){
			data = "{\"status\": \"off\"}";
			socket.emit(EVENT_SENSFLOOR, data);
		}
	}
	
	/**
	 * Event is fired to node server if reference tile is resetet
	 */
	private void fireReferenceEvent(){
		String data = "";
		// notify if tile changed
		if(path.getReferenceTile() != null){
			// cache current tile
			if(!path.getReferenceTile().equals(referenceTile)){
				double x = path.getReferenceTile().getPosX();
				double y = path.getReferenceTile().getPosY();
				System.out.println("Caching and Emitting da reference");
				data = "{\"x\": \""  + x + "\", \"y\": \"" + y + "\"}";
				socket.emit(EVENT_REFERENCE, data);
				this.referenceTile = path.getReferenceTile();
			}
		}
	}

}
