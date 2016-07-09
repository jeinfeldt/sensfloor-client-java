package hdm.stuttgart.csm.smarthome.object;

public class Path {
	
	private Tile lastTile;
	private Tile actTile;
	private Tile referenceTile;
	private Direction x;
	private Direction y;
	private Direction reference;
	private Double lastReferenceDistance;
	
	public Path(){
		this.lastTile = new Tile(0,0,0);
		this.actTile = new Tile(0,0,0);
		this.referenceTile = null;
	}
	
	public Path(Tile referenceTile){
		this.lastTile = new Tile(0,0,0);
		this.actTile = new Tile(0,0,0);
		this.referenceTile = referenceTile;
	}
	
	// LOGIC
	/**
	 * Refresshes path information with current tile from carpet. All direction information are
	 * automatically updated after calling this method.
	 * @param tile
	 */
	public void setActTile(Tile tile){
		this.lastTile = this.actTile; 
		this.actTile = tile;
		refreshDirections();
	}
		
	// UTILITIES
	private void refreshDirections(){
		refreshX();
		refreshY();
		refreshReference();
	}
	
	private void refreshX(){
		this.x = Direction.UNCHANGED;
		if(actTile.getPosX() > lastTile.getPosX()){
			this.x = Direction.INCR_X;
		} else if(actTile.getPosX() < lastTile.getPosX()) {
			this.x = Direction.DECR_X;
		}
	}
	
	private void refreshY(){
		this.y = Direction.UNCHANGED;
		if(actTile.getPosY() > lastTile.getPosY()){
			this.y = Direction.INCR_Y;
		} else if(actTile.getPosY() < lastTile.getPosY()) {
			this.y = Direction.DECR_Y;
		}
	}
	
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

	private double calculateDistance(double x1,double x2,double y1,double y2){
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) );
	}
	
	// GETTERS AND SETTERS
	public Direction getXDirection(){
		return this.x;
	}
	
	public Direction getYDirection(){
		return this.y;
	}
	
	public Direction getReferenceDirection(){
		return this.reference;
	}
	
	public Tile getLastTile(){
		return this.lastTile;
	}
	
	public Tile getReferenceTile(){
		return this.referenceTile;
	}
	
	public void setReferenceTile(Tile tile){
		this.referenceTile = tile;
	}
}
