package hdm.stuttgart.csm.smarthome;

public class Tile {

	private int posX; 
	private int posY;
	private double weight;
	
	/**
	 * Create a new Tile instance with posX and posY
	 * @param posX
	 * @param posY
	 */
	public Tile(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}
	
	/**
	 * check if a tile has weight on it
	 * @return
	 */
	public boolean isActive(){
		return (this.getWeight() > 0);
	}
	
	/**
	 * Get the tiles x position
	 * @return
	 */
	public int getPosX(){
		return this.posX;
	}
	
	/**
	 * Get the tile's y position
	 * @return
	 */
	public int getPosY(){
		return this.posY;
	}
	
	/**
	 * Set the weight of the tile
	 * @param weight
	 */
	public void setWeight(double weight){
		this.weight = weight;
	}
	
	/**
	 * Get the tile's weight
	 * @return
	 */
	public double getWeight(){
		return this.weight;
	}
}
