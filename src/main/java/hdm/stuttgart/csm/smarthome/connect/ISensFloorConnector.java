package hdm.stuttgart.csm.smarthome.connect;

import java.net.URISyntaxException;

import io.socket.client.Socket;

public interface ISensFloorConnector {
	
	public Socket connect() throws URISyntaxException;	
	
	public void disconnect();

}
