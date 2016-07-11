package hdm.stuttgart.csm.smarthome.example;

import hdm.stuttgart.csm.smarthome.event.ClusterEventHandler;
import hdm.stuttgart.csm.smarthome.object.Direction;
import hdm.stuttgart.csm.smarthome.object.Path;
import hdm.stuttgart.csm.smarthome.object.Tile;
import io.socket.client.Socket;

public class NodeClusterEventHandler extends ClusterEventHandler{

	// constants
	private String EVENT_SENSFLOOR = "event_sensfloor";
	public String EVENT_REFERENCE = "get_reference";
	
	// attributes
	private Socket socket;
	private Tile referenceTile;
	private Long capThreshold;
	
	public NodeClusterEventHandler(Socket socket, Tile referenceTile, Long capThreshold) {
		this.socket = socket;
		this.referenceTile = referenceTile;
		this.capThreshold = capThreshold;
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
		// guard clause for capacity - only send if someone on sensfloor
		if(getCOG().getCapacity() <= capThreshold){
			return;
		}
		// notify node if direction changed
		if(getPath().getReferenceDirection() == Direction.DECR_REFERENCE_DIST){
			data = "{\"status\": \"off\"}";
			socket.emit(EVENT_SENSFLOOR, data);
		} else if(getPath().getReferenceDirection() == Direction.INCR_REFERENCE_DIST){
			data = "{\"status\": \"on\"}";
			socket.emit(EVENT_SENSFLOOR, data);
		}
	}
	
	/**
	 * Event is fired to node server if reference tile is reset
	 */
	private void fireReferenceEvent(){
		String data = "";
		if(path.getReferenceTile() != null){
			// if tile changed notify node
			if(!path.getReferenceTile().equals(referenceTile)){
				double x = path.getReferenceTile().getPosX();
				double y = path.getReferenceTile().getPosY();
				data = "{\"x\": \""  + x + "\", \"y\": \"" + y + "\"}";
				socket.emit(EVENT_REFERENCE, data);
				this.referenceTile = path.getReferenceTile().clone();
			}
		}
	}
	
	@Override
	public void setPath(Path path){
		this.path = path;
	}

}
