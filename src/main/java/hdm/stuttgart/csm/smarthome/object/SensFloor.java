package hdm.stuttgart.csm.smarthome.object;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
	// constants
	private final String PROTOCOL_KEY = "protocol";
	private final String HOST_KEY = "host";
	private final String PORT_KEY = "port";
	private final String REFERENCE_X_KEY = "referenceX";
	private final String REFERENCE_Y_KEY = "referenceY";
	
	// attributes
	private Socket socket;
	private ISensFloorConnector connector;
	private List<ClusterEventHandler> clusterHandlers;
	private Properties prop;
	private Tile referenceTile = new Tile(0 ,0, 0);
	
	// constructors
	public SensFloor(String protocol, String address, String port){
		this.connector = new SensFloorConnector(protocol, address, port);
		this.clusterHandlers = new ArrayList<ClusterEventHandler>();
	}
	
	public SensFloor(String protocol, String address, String port, Tile referenceTile){
		this.connector = new SensFloorConnector(protocol, address, port);
		this.referenceTile = referenceTile;
		this.clusterHandlers = new ArrayList<ClusterEventHandler>();
	}
	
	public SensFloor(ISensFloorConnector connector){
		this.connector = connector;
		this.clusterHandlers = new ArrayList<ClusterEventHandler>();
	}
	
	public SensFloor(ISensFloorConnector connector, Tile referenceTile){
		this.connector = connector;
		this.referenceTile = referenceTile;
		this.clusterHandlers = new ArrayList<ClusterEventHandler>();
	}
	
	public SensFloor(String propertyFile) throws IOException{
		this.prop = readProperties(propertyFile);
		this.connector = new SensFloorConnector(prop.getProperty(PROTOCOL_KEY),
				prop.getProperty(HOST_KEY), prop.getProperty(PORT_KEY));
		this.clusterHandlers = new ArrayList<ClusterEventHandler>();
		// reading reference tile
		double refX = Double.parseDouble(prop.getProperty(REFERENCE_X_KEY));
		double refY = Double.parseDouble(prop.getProperty(REFERENCE_Y_KEY));
		this.referenceTile = new Tile(refX, refY, 0);
	}
	
	// connection
	public void openConnection() throws URISyntaxException{
		socket = connector.connect();
		// add event listeners
		socket.on("cluster", new ClusterListener(clusterHandlers, referenceTile));
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
	private Properties readProperties(String propertyFile) throws IOException{
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propertyFile + "' not found");
		}
		return prop;
	}
	
	// GETTERS AND SETTERS
	public Properties getProperties() {
		return prop;
	}

	public void setProperties(Properties properties) {
		this.prop = properties;
	}
}
