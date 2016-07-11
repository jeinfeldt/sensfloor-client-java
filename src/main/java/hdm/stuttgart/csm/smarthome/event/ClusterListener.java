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
	private Long capacityThreshold;
	private int socketDelay;
	private int counter;
	
	public ClusterListener(List <ClusterEventHandler> list, Tile referenceTile, Long capacityThreshold, int socketDelay){
		this.list = list;
		this.referenceTile = referenceTile;
		this.capacityThreshold = capacityThreshold;
		this.path = new Path(referenceTile);
		this.counter = 0;
		this.socketDelay = socketDelay;
	}
	
	public void call(Object... args) {
		// delay execution if necessary
		counter++;
		if(counter<socketDelay){
			return;
		}
		counter=0;
		// parse json
		try {
			readJSON(args[0].toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// init values
		Tile parsedCOG = parseJSON(rawCOG);
		Tile [] parsedPoints = parseJSON(rawPoints);
		// if first call to carpet, threshold capacity COG is set as reference
		if(capacityThreshold != null && referenceTile == null && parsedCOG.getCapacity() >= capacityThreshold){
			referenceTile = parsedCOG;
			path.setReferenceTile(referenceTile);
		}
		// prepare and execute listeners
		for(ClusterEventHandler currentHandler: list){
			path.setActTile(parsedCOG);
			currentHandler.setPath(path);
			currentHandler.setCOG(parsedCOG);
			currentHandler.setPoints(parsedPoints);
			currentHandler.execute();
		}
	}
	
	public void setReferenceTile(Tile tile){
		this.referenceTile = tile;
	}
	
	public Tile getReferenceTile(){
		return this.referenceTile;
	}
	
	public Path getPath(){
		return this.path;
	}
}
