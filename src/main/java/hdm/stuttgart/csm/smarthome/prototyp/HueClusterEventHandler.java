package hdm.stuttgart.csm.smarthome.prototyp;

import java.net.URL;

import hdm.stuttgart.csm.smarthome.event.ClusterEventHandler;

public class HueClusterEventHandler extends ClusterEventHandler{
	
	private String url;
	
	public HueClusterEventHandler(String url){
		this.url = url;
	}
	
	@Override
	public void execute(){
		long capacity = getCOG().getCapacity();
		// evaluate cog
		String state = "OFF";
		if(capacity > 100){
			state = "ON";
			System.out.println("LUMOS");
		}
		// adjust hue light based on cog
		try {
			URL hueUrl = new URL(url + state);
			System.out.println(url+state);
			hueUrl.openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
