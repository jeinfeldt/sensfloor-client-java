package hdm.stuttgart.csm.smarthome.object;

public class Path {
	
	private Tile lastTile;
	private Tile actTile;
	
	public Path(){
		lastTile = new Tile(0,0,0);
		actTile = new Tile(0,0,0);
	}
	
	/**
	 * Set the active tile and last tile
	 * @param tile
	 */
	public void setActTile(Tile tile){
		this.lastTile = this.actTile; 
		this.actTile = tile;
	}
	
	/**
	 * Get the last active tile
	 * @return
	 */
	public Tile getLastTile(){
		return this.lastTile;
	}
	
	/**
	 * Get the active tile
	 * @return
	 */
	public Tile getActTile(){
		return this.actTile;
	}
	
	
	/**
	 * Get the direction between two active tiles
	 * @param tile1
	 * @param tile2
	 * @return
	 */
	public String getActDirection(){
		String s = "";
		if(lastTile.getPosY()<actTile.getPosY()){
			s += "RIGHT";
		}
		if(lastTile.getPosY()>actTile.getPosY()){
			s += "LEFT";
		}
		if(lastTile.getPosX()<actTile.getPosX()){
			s += "DOWN";
		}
		if(lastTile.getPosX()>actTile.getPosX()){
			s += "TOP";
		}
		return s;
	}
	
}
