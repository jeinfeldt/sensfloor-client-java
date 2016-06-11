package hdm.stuttgart.csm.smarthome.event;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import hdm.stuttgart.csm.smarthome.object.Tile;
import io.socket.emitter.Emitter;

public abstract class BaseClusterListener implements Emitter.Listener{
	
	// constants
	protected final String COG_KEY = "cog";
	protected final String POINTS_KEY = "points";
	protected final String X_KEY = "x";
	protected final String Y_KEY = "y";
	protected final String CAPACITY_KEY = "c";
	
	// attributes
	protected JSONObject rawCOG;
	protected JSONArray rawPoints;
	
	// empty constructor
	public BaseClusterListener(){
		
	}

	// abstract methods
	public abstract void call(Object... args);

	// protected methods
	protected void readJSON(String rawData) throws ParseException{
		JSONObject data = (JSONObject) new JSONParser().parse(rawData);
		rawCOG = (JSONObject) data.get(COG_KEY);
		rawPoints = (JSONArray) data.get(POINTS_KEY);
	}
	
	protected Tile[] parseJSON(JSONArray array){
		Tile[] result = new Tile[array.size()];
		for(int i=0; i<array.size(); i+=1){
			JSONObject current = (JSONObject) array.get(i);
			result[i] = parseJSON(current);
		}
		return result;
	}
	
	protected Tile parseJSON(JSONObject tile){
		Double x =  Double.parseDouble((String)tile.get(X_KEY));
		Double y = Double.parseDouble((String)tile.get(Y_KEY));;
		Long c = (Long) tile.get(CAPACITY_KEY);
		return new Tile(x, y, c);
	}
}
