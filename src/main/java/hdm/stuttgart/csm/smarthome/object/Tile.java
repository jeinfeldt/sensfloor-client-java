package hdm.stuttgart.csm.smarthome.object;

/**
 * A representation of a SensFloor tile consisting of an x position, an y position and the measured capacity.
 *
 * @author Joerg Einfeldt, Thomas Derleth, Merle Hiort, Marc Stauffer
 */
public class Tile {

	/**
	 * The x position of this Tile
	 */
	private double posX; 
	/**
	 * The y position of this Tile
	 */
	private double posY;
	/**
	 * The measured capacity of this Tile
	 */
	private long capacity;
	
	/**
	 * Create a new Tile instance with posX, posY and a capacity
	 * @param posX the x position
	 * @param posY the y position
	 * @param capacity the capacity
	 */
	public Tile(double posX, double posY, long capacity){
		this.posX = posX;
		this.posY = posY;
		this.capacity = capacity;
	}
	
	/**
	 * Checks if a tile has measured a capacity
	 * @return true if the capacity is not 0, false otherwise
	 */
	public boolean isActive(){
		return (this.getCapacity() > 0);
	}
	
	/**
	 * Gets the tiles x position
	 * @return the x position
	 */
	public double getPosX(){
		return this.posX;
	}
	
	/**
	 * Gets the tile's y position
	 * @return the y position
	 */
	public double getPosY(){
		return this.posY;
	}
	
	/**
	 * Sets the capacity of the tile
	 * @param the current capacity
	 */
	public void setCapacity(long capacity){
		this.capacity = capacity;
	}
	
	/**
	 * Gets the tile's capacity
	 * @return the current capacity
	 */
	public long getCapacity(){
		return this.capacity;
	}
	
	@Override
	public boolean equals(Object obj){
		
		// check reference
		if(!(obj instanceof Tile)){
			return false;
		}
		
		if(obj == this){
			return true;
		}
		
		// check attributes
		Tile tile = (Tile) obj;
		
		if(tile.getPosX() == posX && tile.getPosY() == posY){
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString(){
		return "Tile x: " + posX + " y: " + posY + " capacity: " + capacity;
	}
	
	@Override
	public Tile clone(){
		Tile t = new Tile(posX, posY, capacity);
		return t;
	}
}
