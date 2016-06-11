package hdm.stuttgart.csm.smarthome.event;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import hdm.stuttgart.csm.smarthome.object.Path;
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
	private Path path;
	private Tile referenceTile;
	
	public ClusterListener(List <ClusterEventHandler> list, Tile referenceTile){
		this.list = list;
		this.referenceTile = referenceTile;
		this.path = new Path(this.referenceTile);
	}
	
	public void call(Object... args) {
		// parse json
		parseJSON(args[0].toString());
		// init values
		Tile cog = parseJSONTile(this.cog);
		Tile [] points = initPoints();
		// prepare and execute listeners
		for(ClusterEventHandler currentHandler: list){
			path.setActTile(cog);
			currentHandler.setPath(path);
			currentHandler.setCOG(cog);
			currentHandler.setPoints(points);
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
	
	private Tile[] initPoints(){
		Tile[] result = new Tile[points.size()];
		for(int i=0; i<points.size(); i+=1){
			JSONObject current = (JSONObject) points.get(i);
			result[i] = parseJSONTile(current);
		}
		return result;
	}
	
	private Tile parseJSONTile(JSONObject tile){
		Double x =  Double.parseDouble((String)tile.get(X_KEY));
		Double y = Double.parseDouble((String)tile.get(Y_KEY));;
		Long c = (Long) tile.get(CAPACITY_KEY);
		return new Tile(x, y, c);
	}
	

}
