package hdm.stuttgart.csm.smarthome.event;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import hdm.stuttgart.csm.smarthome.object.Tile;
import io.socket.emitter.Emitter;

public class ClusterListener implements Emitter.Listener{
	
	// constants
	private final String COG_KEY = "cog";
	private final String POINTS_KEY = "points";
	private final String X_KEY = "x";
	private final String Y_KEY = "y";
	private final String CAPACITY_KEY = "c";
	
	// attributes
	private List<ClusterEventHandler> list;
	private JSONObject cog;
	private JSONArray points;
	private Tile debug_cog = new Tile(2, 2, 150); //TODO remove
	
	public ClusterListener(List <ClusterEventHandler> list){
		this.list = list;
	}
	
	public void call(Object... args) {
		// parse json
		//parseJSON(args[0].toString()); XXX
		// init values
		//Tile cog = initCOG(); XXX
		// prepare and execute listeners
		for(ClusterEventHandler currentHandler: list){
			currentHandler.setCOG(debug_cog);
			currentHandler.execute();
		}
	}
	
	private void parseJSON(String rawData){
		try {
			JSONObject data = (JSONObject) new JSONParser().parse(rawData);
			cog = (JSONObject) data.get(COG_KEY);
			points = (JSONArray) data.get(POINTS_KEY);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private Tile initCOG(){
		// fetch values
		double x = (Double) cog.get(X_KEY);
		double y = (Double) cog.get(Y_KEY);
		long c = (Long) cog.get(CAPACITY_KEY);
		return new Tile(x, y, c);
	}
	
	private Tile[] initPoints(){
		// TODO: Placeholder
		return null;
	}
	

}
