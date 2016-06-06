package hdm.stuttgart.csm.smarthome.object;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import hdm.stuttgart.csm.smarthome.connect.ISensFloorConnector;
import hdm.stuttgart.csm.smarthome.connect.SensFloorConnector;
import hdm.stuttgart.csm.smarthome.event.ClusterListener;
import hdm.stuttgart.csm.smarthome.event.ClusterEventHandler;
import io.socket.client.Socket;

/**
 * SensFloor Class for Interaction with the SensFloor Carpet
 * 1. Initialize with SensFloor address
 * 2. Add specific event listeners (clusters)
 * 3. Open Connection
 */
public class SensFloor {
	
	// attributes
	private Socket socket;
	private ISensFloorConnector connector;
	private List<ClusterEventHandler> clusterHandlers;
	
	
	// constructors
	public SensFloor(String protocol, String address, String port){
		this.connector = new SensFloorConnector(protocol, address, port);
		this.clusterHandlers = new ArrayList<ClusterEventHandler>();
	}
	
	public SensFloor(ISensFloorConnector connector){
		this.connector = connector;
		this.clusterHandlers = new ArrayList<ClusterEventHandler>();
	}
	
	// connection
	public void openConnection() throws URISyntaxException{
		socket = connector.connect();
		// add event listeners
		socket.on("cluster", new ClusterListener(clusterHandlers));
	}
	
	public void closeConnection(){
		connector.disconnect();
		socket = null;
	}
	
	//logic
	public void addClusterEventHandler(ClusterEventHandler handler){
		clusterHandlers.add(handler);
	}
	
	public void removeClusterEventHandler(ClusterEventHandler handler){
		clusterHandlers.remove(handler);
	}
	
	public void clearClusterEventHandlers(){
		clusterHandlers.clear();
	}
	
	// utilities
	/*
	public void show(){
		for(int i=0; i<getWidth(); i++){
			for(int j=0; j<getHeight(); j++){
				if(tiles[i][j] == path.getLastTile()){
					System.out.print("L");
				}else if(tiles[i][j].isActive()){
					System.out.print("X");
				}else{
					System.out.print(".");					
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	*/
}
