package hdm.stuttgart.csm.smarthome.event;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import hdm.stuttgart.csm.smarthome.object.Tile;
import io.socket.emitter.Emitter;

/**
 * An abstract implementation of a BaseClusterListener. 
 * An extension of this class listens for an incoming cluster socket event and defines the parsing of the transmitted JSON data.
 * This event is send by the SensFloor via socket every 50ms.
 * 
 * @author Joerg Einfeldt, Thomas Derleth, Merle Hiort, Marc Stauffer
 */
public abstract class BaseClusterListener implements Emitter.Listener{
	
	/**
	 * The JSON key used for the center of gravity
	 */
	protected final String COG_KEY = "cog";
	/**
	 * The JSON key used for the points array
	 */
	protected final String POINTS_KEY = "points";
	/**
	 * The JSON key used for the x position of a tile
	 */
	protected final String X_KEY = "x";
	/**
	 * The JSON key used for the y position of a tile
	 */
	protected final String Y_KEY = "y";
	/**
	 * The JSON key used for the capacity of a tile
	 */
	protected final String CAPACITY_KEY = "c";
	
	/**
	 * The current cog as a JSON Object
	 */
	protected JSONObject rawCOG;
	/**
	 * The current points as a JSON Array
	 */
	protected JSONArray rawPoints;
	
	/**
	 * An empty constructor for this BaseClusterListener.
	 */
	public BaseClusterListener(){
		//
	}

	/**
	 * Contains the parsing logic and is called automatically every time the event is received.
	 */
	public abstract void call(Object... args);

	/**
	 * Creates a JSON object for the COG and a JSON array for the points from the incoming event data.
	 * @param rawData the raw data transmitted by the socket event
	 * @throws ParseException
	 */
	protected void readJSON(String rawData) throws ParseException{
		JSONObject data = (JSONObject) new JSONParser().parse(rawData);
		rawCOG = (JSONObject) data.get(COG_KEY);
		rawPoints = (JSONArray) data.get(POINTS_KEY);
	}
	
	/**
	 * Converts a JSON array into a Tile array.
	 * @param array JSON array
	 * @return the converted Tile array
	 */
	protected Tile[] parseJSON(JSONArray array){
		Tile[] result = new Tile[array.size()];
		for(int i=0; i<array.size(); i+=1){
			JSONObject current = (JSONObject) array.get(i);
			result[i] = parseJSON(current);
		}
		return result;
	}
	
	/**
	 * Converts a JSON object into a Tile object.
	 * @param tile the JSON object
	 * @return the converted Tile object
	 */
	protected Tile parseJSON(JSONObject tile){
		Object tmpX = tile.get(X_KEY);
		Object tmpY = tile.get(Y_KEY);
		Object tmpC = tile.get(CAPACITY_KEY);
		Double x = null;
		Double y = null;
		Long c = null;
		
		// parsing x
		if(tmpX instanceof Double){
			x = (Double) tmpX;
		}
		// parsing y
		if(tmpY instanceof Double){
			y = (Double) tmpY;
		} 
		// parsing capacity
		if(tmpC instanceof Long){
			c = (Long) tmpC;
		}
		return new Tile(x, y, c);
	}
}
