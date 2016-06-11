package hdm.stuttgart.csm.smarthome.prototyp;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class IngoAppTest {
	
	public static void main(String[] args) throws URISyntaxException {	
		Socket socky = IO.socket("http://localhost");
		socky.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

		  public void call(Object... args) {
			  System.out.println("Connect dude");
		  }

		}).on("test", new Emitter.Listener() {

		  public void call(Object... args) {
			  System.out.println("Es wurde ein testcall ausgeführt");
		  }
		});
		socky.connect();
	}

}
