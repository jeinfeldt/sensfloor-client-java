package hdm.stuttgart.csm.smarthome.connect;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket; 

/**
 * This class is an implementation of a SensFloor Connector.
 * It administrates the connection to the SensFloor. 
 * @author Joerg Einfeldt, Thomas Derleth, Merle Hiort, Marc Stauffer
 *
 */
public class SensFloorConnector implements ISensFloorConnector {
	
	// constants
	private final String PORT_SEPERATOR = ":";
	private final String PROTOCOL_SEPERATOR = "://";
	
	/**
	 * The protocol of the connection, e.g. http
	 */
	private String protocol;
	/**
	 * The address of the connection, e.g. an IP address
	 */
	private String address;
	/**
	 * The port of the connection, e.g. 8000
	 */
	private String port;
	/**
	 * The socket of the connection
	 */
	private Socket sensFloorSocket;
	
	public SensFloorConnector(String protocol, String address, String port){
		this.protocol = protocol;
		this.address = address;
		this.port = port;
	}
	
	/**
	 * Connects a socket with the SensFloor and returns the current connection socket.
	 * @return the current connection socket
	 * @throws URISyntaxException
	 */
	public Socket connect() throws URISyntaxException{
			String machineName = protocol+PROTOCOL_SEPERATOR+address+PORT_SEPERATOR+port; 
			sensFloorSocket = IO.socket(machineName);
			sensFloorSocket.connect();
			return sensFloorSocket;
	}
	
	/**
	 * Disconnects the socket from the SensFloor.
	 */
	public void disconnect() {
		sensFloorSocket.disconnect();
	}
	
	/**
	 * Returns the protocol of this connection.
	 * @return the name of the current protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Sets the protocol of this connection. 
	 * @param protocol the protocol name of this connection
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	/**
	 * Returns the address of this connection.
	 * @return the address of this connection.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address of this connection.
	 * @param address the address of this connection
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Returns the port of this connection.
	 * @return the port of this connection
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Sets the port of this connection.
	 * @param port the port of this connection
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * Returns the current connection socket.
	 * @return the current connection socket
	 */
	public Socket getSensFloorSocket() {
		return sensFloorSocket;
	}

	/**
	 * Sets the current connection socket.
	 * @param sensFloorSocket a connection socket
	 */
	public void setSensFloorSocket(Socket sensFloorSocket) {
		this.sensFloorSocket = sensFloorSocket;
	}
}
