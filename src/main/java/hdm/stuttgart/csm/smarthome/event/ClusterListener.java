package hdm.stuttgart.csm.smarthome.event;

import java.util.List;

import org.json.simple.parser.ParseException;

import hdm.stuttgart.csm.smarthome.object.Path;
import hdm.stuttgart.csm.smarthome.object.Tile;

public class ClusterListener extends BaseClusterListener{
	
	// attributes
	private List<ClusterEventHandler> list;
	private Path path;
	private Tile referenceTile;
	
	public ClusterListener(List <ClusterEventHandler> list, Tile referenceTile){
		this.list = list;
		this.referenceTile = referenceTile;
		this.path = new Path(this.referenceTile);
	}
	
	public void call(Object... args) {
		// parse json
		try {
			readJSON(args[0].toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// init values
		Tile parsedCOG = parseJSON(rawCOG);
		Tile [] parsedPoints = parseJSON(rawPoints);
		// prepare and execute listeners
		for(ClusterEventHandler currentHandler: list){
			path.setActTile(parsedCOG);
			currentHandler.setPath(path);
			currentHandler.setCOG(parsedCOG);
			currentHandler.setPoints(parsedPoints);
			currentHandler.execute();
		}
	}
}
