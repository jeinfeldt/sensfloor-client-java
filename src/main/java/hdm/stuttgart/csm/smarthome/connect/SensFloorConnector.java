package hdm.stuttgart.csm.smarthome.connect;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket; 

public class SensFloorConnector implements ISensFloorConnector {
	
	// constants
	private final String PORT_SEPERATOR = ":";
	private final String PROTOCOL_SEPERATOR = "://";
	
	// attributes
	private String protocol;
	private String address;
	private String port;
	private Socket sensFloorSocket;
	
	public SensFloorConnector(String protocol, String address, String port){
		this.protocol = protocol;
		this.address = address;
		this.port = port;
	}
	
	public Socket connect() throws URISyntaxException{
			//connect to the machine
			String machineName = protocol+PROTOCOL_SEPERATOR+address+PORT_SEPERATOR+port; 
			sensFloorSocket = IO.socket(machineName);
			sensFloorSocket.connect();
			return sensFloorSocket;
	}
	
	public void disconnect() {
		sensFloorSocket.disconnect();
	}
	
	// GETTER AND SETTER
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Socket getSensFloorSocket() {
		return sensFloorSocket;
	}

	public void setSensFloorSocket(Socket sensFloorSocket) {
		this.sensFloorSocket = sensFloorSocket;
	}
}
