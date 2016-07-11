package hdm.stuttgart.csm.smarthome.event;

import hdm.stuttgart.csm.smarthome.object.Path;
import hdm.stuttgart.csm.smarthome.object.Tile;

/**
 * An abstract implementation of a ClusterEventHandler. 
 * An extension of this class defines the business logic to be executed in case of an incoming cluster event.
 * This event is send by the SensFloor via socket every 50ms.
 * 
 * @author Joerg Einfeldt, Thomas Derleth, Merle Hiort, Marc Stauffer
 */
public abstract class ClusterEventHandler {
	
	/**
	 * The current center of gravity of all capacities measured on the SensFloor
	 */
	protected Tile cog;
	/**
	 * An array of Tile objects representing the current state of the SensFloor
	 */
	protected Tile [] points;
	/**
	 * A path consisting of the current and the last COG
	 */
	protected Path path;
	
	/**
	 * Defines the business logic to be executed in case of an incoming cluster event.
	 */
	public abstract void execute();

	/**
	 * Sets the current center of gravity of all capacities measured on the SensFloor.
	 * @param cog the current center of gravity as a Tile object
	 */
	public void setCOG(Tile cog){
		this.cog = cog;
	}
	
	/**
	 * Returns the current center of gravity of all capacities measured on the SensFloor.
	 * @return the current center of gravity as a Tile object
	 */
	public Tile getCOG(){
		return this.cog;
	}
	
	/**
	 * Sets the current state of the SensFloor as an array of Tile objects.
	 * @param points the current state of the SensFloor
	 */
	public void setPoints(Tile [] points){
		this.points = points;
	}
	
	/**
	 * Returns the current state of the SensFloor as an array of Tile objects.
	 * @return the current state of the SensFloor
	 */
	public Tile [] getPoints(){
		return this.points;
	}
	
	/**
	 * Sets the current path consisting of the current and the last cog.
	 * @param path the current path
	 */
	public void setPath(Path path){
		this.path = path;
	}
	
	/**
	 * Returns the current path consisting of the current and the last COG.
	 * @param path the current path
	 */
	public Path getPath(){
		return this.path;
	}
}
