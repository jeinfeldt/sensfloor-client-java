package hdm.stuttgart.csm.smarthome.object;

/**
 * 
 * The path on the sensfloor consists of the last two COG-tiles that were activated
 * on the sensfloor. The path calculates the current direction for x, y and whether
 * the distance to a reference tile decreased or increased.
 * 
 * @author Joerg Einfeldt, Thomas Derleth, Merle Hiort, Marc Stauffer
 *
 */
public class Path {
	
	// attributes
	private Tile lastTile;
	private Tile actTile;
	private Tile referenceTile;
	private Direction x;
	private Direction y;
	private Direction reference;
	private Double lastReferenceDistance;
	
	// default constructor
	public Path(){
		this.lastTile = new Tile(0,0,0);
		this.actTile = new Tile(0,0,0);
		this.referenceTile = null;
	}
	
	/**
	 * Initialises path object with a given reference tile
	 * @param referenceTile For the path
	 */
	public Path(Tile referenceTile){
		this.lastTile = new Tile(0,0,0);
		this.actTile = new Tile(0,0,0);
		this.referenceTile = referenceTile;
	}
	
	/**
	 * Updates current tile for path, alle directions are refreshed after calling
	 * this method.  
	 * @param tile The tile to be added to the path
	 */
	public void setActTile(Tile tile){
		this.lastTile = this.actTile; 
		this.actTile = tile;
		refreshDirections();
	}
		
	/**
	 * Refreshes x,y and reference direction
	 */
	private void refreshDirections(){
		refreshX();
		refreshY();
		refreshReference();
	}
	
	/**
	 * Refreshes current x direction
	 */
	private void refreshX(){
		this.x = Direction.UNCHANGED;
		if(actTile.getPosX() > lastTile.getPosX()){
			this.x = Direction.INCR_X;
		} else if(actTile.getPosX() < lastTile.getPosX()) {
			this.x = Direction.DECR_X;
		}
	}
	
	/**
	 * Refreshes current y direction
	 */
	private void refreshY(){
		this.y = Direction.UNCHANGED;
		if(actTile.getPosY() > lastTile.getPosY()){
			this.y = Direction.INCR_Y;
		} else if(actTile.getPosY() < lastTile.getPosY()) {
			this.y = Direction.DECR_Y;
		}
	}
	
	/**
	 * Refreshes current direction in regards to the reference tile
	 */
	private void refreshReference(){
		this.reference = Direction.UNCHANGED;
		// guard clause for null values
		if(actTile == null || referenceTile == null){
			return;
		}
		// guard clause for reference distance
		double actDistance = calculateDistance(actTile.getPosX(), referenceTile.getPosX(), actTile.getPosY(), referenceTile.getPosY());
		if(lastReferenceDistance == null){
			lastReferenceDistance = actDistance;
			return;
		}
		// if distances can be compared
		if(actDistance < lastReferenceDistance){
			this.reference = Direction.DECR_REFERENCE_DIST;
		} else if(actDistance > lastReferenceDistance){
			this.reference = Direction.INCR_REFERENCE_DIST;
		}
		lastReferenceDistance = actDistance;
	}

	/**
	 * Calcualte distance between to tiles
	 * @param x1 x value tile1
	 * @param x2 x value tile2
	 * @param y1 y value tile1
	 * @param y2 y value tile2
	 * @return
	 */
	private double calculateDistance(double x1,double x2,double y1,double y2){
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) );
	}
	
	/**
	 * Current direction regarding x
	 * @return xDirection
	 */
	public Direction getXDirection(){
		return this.x;
	}
	
	/**
	 * Current direction regarding y
	 * @return yDirection
	 */
	public Direction getYDirection(){
		return this.y;
	}
	
	/**
	 * Current distance regarding reference point with increase and decrease 
	 * @return referenceDirection
	 */
	public Direction getReferenceDirection(){
		return this.reference;
	}
	
	/**
	 * Last tile for path
	 * @return last tile
	 */
	public Tile getLastTile(){
		return this.lastTile;
	}
	
	/**
	 * Currently used reference tile
	 * @return reference tile
	 */
	public Tile getReferenceTile(){
		return this.referenceTile;
	}
	
	/**
	 * Udates currently used reference tile
	 * @param tile - New reference tile
	 */
	public void setReferenceTile(Tile tile){
		this.referenceTile = tile;
	}
}
