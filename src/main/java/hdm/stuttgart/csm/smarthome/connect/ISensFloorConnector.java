package hdm.stuttgart.csm.smarthome.connect;

import java.net.URISyntaxException;

import io.socket.client.Socket;

/**
 *  Interface class for a SensFloor connector.
 *  @author Joerg Einfeldt, Thomas Derleth, Merle Hiort, Marc Stauffer
 */
public interface ISensFloorConnector {
	
	/**
	 * Connects a socket with the SensFloor.
	 * @return the current connection socket
	 * @throws URISyntaxException
	 */
	public Socket connect() throws URISyntaxException;	
	
	/**
	 * Disconnects the socket from the SensFloor.
	 */
	public void disconnect();

}
