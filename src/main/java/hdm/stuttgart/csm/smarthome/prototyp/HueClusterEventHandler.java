package hdm.stuttgart.csm.smarthome.prototyp;

import java.net.URL;

import hdm.stuttgart.csm.smarthome.event.ClusterEventHandler;
import hdm.stuttgart.csm.smarthome.object.Direction;

public class HueClusterEventHandler extends ClusterEventHandler{
	
	private String url;
	private boolean debug;
	
	public HueClusterEventHandler(String url, boolean debug){
		this.url = url;
		this.debug = debug;
	}
	
	@Override
	public void execute(){
		long capacity = getCOG().getCapacity();
		// evaluate cog
		String state = "OFF";
		
		if(debug){
			debug();
		}
		
		if(capacity > 100){
			state = "ON";
		}
		// adjust hue light based on cog
		try {
			URL hueUrl = new URL(url + state);
			//hueUrl.openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void debug(){
		// output data
		long capacity = getCOG().getCapacity();
		
		if(getPath().getReferenceDirection() != Direction.UNCHANGED){
			System.out.println("--------- DEBUG START --------");
			System.out.println("Last COG: " + getPath().getLastTile().getPosX()
					+ ", " + getPath().getLastTile().getPosY() 
					+ ", " + getPath().getLastTile().getCapacity());
			System.out.println("Current COG: " + getCOG().getPosX() 
					+ ", " + getCOG().getPosY() + ", " + capacity);
			System.out.println("ReferenceDirection: " + getPath().getReferenceDirection());
			System.out.println("XDirection: " + getPath().getXDirection());
			System.out.println("YDirection: " + getPath().getYDirection());
			System.out.println("--------- DEBUG END --------");
		}
	}
}
