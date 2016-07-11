package hdm.stuttgart.csm.smarthome.event;

import java.util.List;

import org.json.simple.parser.ParseException;

import hdm.stuttgart.csm.smarthome.object.Path;
import hdm.stuttgart.csm.smarthome.object.Tile;

/**
 * An concrete implementation of the abstract BaseClusterListener. 
 * In this class the cluster event received by the SensFloor is parsed into Tile objects. 
 * Besides, a reference point is set if necessary for the calculation of the direction.
 * Afterwards, all execute methods of the given ClusterEventHandlers are called.
 * 
 * @author Joerg Einfeldt, Thomas Derleth, Merle Hiort, Marc Stauffer
 */
public class ClusterListener extends BaseClusterListener{
	
	/**
	 * A list of all ClusterEventHandlers to be called on every incoming socket
	 */
	private List<ClusterEventHandler> list;
	/**
	 * The current path consisting of the current and the last COG
	 */
	private Path path;
	/**
	 * The Tile to be used as a reference point for the direction calculations
	 */
	private Tile referenceTile;
	/**
	 * A threshold that defines the minimum capacity to be handled
	 */
	private Long capacityThreshold;
	/**
	 * Defines an interval during which all incoming sockets are ignored
	 */
	private int socketDelay;
	/**
	 * A counter to check if the interval duration is reached
	 */
	private int counter;
	
	/**
	 * Constructor of a ClusterListener
	 * @param list list of ClusterEventHandlers to be called by every socket
	 * @param referenceTile the Tile to be used 
	 * @param capacityThreshold the threshold to be used
	 * @param socketDelay the delay interval to be used
	 */
	public ClusterListener(List <ClusterEventHandler> list, Tile referenceTile, Long capacityThreshold, int socketDelay){
		this.list = list;
		this.referenceTile = referenceTile;
		this.capacityThreshold = capacityThreshold;
		this.path = new Path(referenceTile);
		this.counter = 0;
		this.socketDelay = socketDelay;
	}
	
	/**
	 * Parses the incoming data from the cluster event, sets a reference point if necessary and executes all event handlers.
	 */
	@Override
	public void call(Object... args) {
		// delay execution if necessary to ignore some events
		counter++;
		if(counter<socketDelay){
			return;
		}
		counter=0;
		// read the raw data and parse it to JSON objects
		try {
			readJSON(args[0].toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// parse the JSON objects to Tile objects
		Tile parsedCOG = parseJSON(rawCOG);
		Tile [] parsedPoints = parseJSON(rawPoints);
		// if first call to carpet, the current COG is set as reference point for further calculations
		if(capacityThreshold != null && referenceTile == null && parsedCOG.getCapacity() >= capacityThreshold){
			referenceTile = parsedCOG;
			path.setReferenceTile(referenceTile);
		}
		// prepare and execute all given event handlers
		for(ClusterEventHandler currentHandler: list){
			path.setActTile(parsedCOG);
			currentHandler.setPath(path);
			currentHandler.setCOG(parsedCOG);
			currentHandler.setPoints(parsedPoints);
			currentHandler.execute();
		}
	}
	
	/**
	 * Sets the current reference Tile used for direction calculations.
	 * @param tile the current reference Tile
	 */
	public void setReferenceTile(Tile tile){
		this.referenceTile = tile;
	}
	
	/**
	 * Returns the current reference Tile used for direction calculations.
	 * @return the current reference Tile
	 */
	public Tile getReferenceTile(){
		return this.referenceTile;
	}
	
	/**
	 * Returns the current path consisting of the current and the last COG.
	 * @return
	 */
	public Path getPath(){
		return this.path;
	}
}
