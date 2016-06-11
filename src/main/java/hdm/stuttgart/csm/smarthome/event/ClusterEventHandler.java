package hdm.stuttgart.csm.smarthome.event;

import hdm.stuttgart.csm.smarthome.object.Path;
import hdm.stuttgart.csm.smarthome.object.Tile;

public abstract class ClusterEventHandler {
	
	protected Tile cog;
	protected Tile [] points;
	protected Path path;
	
	public abstract void execute();

	// GETTER AND SETTER
	public void setCOG(Tile cog){
		this.cog = cog;
	}
	
	public Tile getCOG(){
		return this.cog;
	}
	
	public void setPoints(Tile [] points){
		this.points = points;
	}
	
	public Tile [] getPoints(){
		return this.points;
	}
	
	public void setPath(Path path){
		this.path = path;
	}
	
	public Path getPath(){
		return this.path;
	}
}
