package hdm.stuttgart.csm.smarthome;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Carpet {

	private Tile [][] tiles ;
	private Path path; 
	
	/**
	 * Create a new carpet instance with width and height
	 * @param width
	 * @param height
	 */
	public Carpet(int height, int width){
		tiles = new Tile [height][width];
		path = new Path();
	}

	/**
	 * Fill the tile array with new Tiles
	 */
	public void fillWithTiles(){
		for(int i=0; i<getWidth(); i++){
			for(int j=0; j<getHeight(); j++){
				Tile actTile = new Tile(i,j);
				this.addTile(actTile);
			}
		}
	}
	
	/**
	 * Add a tile to the tiles array
	 * @param tile
	 */
	public void addTile(Tile tile){
		tiles[tile.getPosX()][tile.getPosY()] = tile;  
	}
		
	/**
	 * Get the carpet's tiles array
	 * @return
	 */
	public Tile [][] getTiles(){
		return this.tiles;
	}
	
	/**
	 * Get a list of all active tiles
	 * @return
	 */
	public ArrayList<Tile> getActiveTiles(){
		ArrayList <Tile> activeTiles = new ArrayList<Tile>();
		
		for(int i=0; i < tiles.length; i++){
			for(int j=0; j<tiles[0].length; j++){
				Tile actTile = tiles[i][j];
				if(actTile.isActive()){
					activeTiles.add(actTile);
				}				
			}
		}
		return activeTiles;
	}
	
	/**
	 * Parse a JSON string and set the tile's weight
	 * Example: [{"x":-3,"y":-2,"c":0},{"x":-2,"y":-2,"c":0},...]
	 * @param s
	 */
	public Tile parseJSONAndSetWeight(String s){
		
		JSONArray jsonArr;
		try {
			jsonArr = (JSONArray) new JSONParser().parse(s);
			for(int i = 0; i<jsonArr.size(); i++){
				JSONObject json = (JSONObject) jsonArr.get(i);
				int posX = Integer.parseInt(json.get("x").toString()); //x:3
				int posY = Integer.parseInt(json.get("y").toString()); //y:2
				double weight = Double.parseDouble(json.get("c").toString()); //c:34
				
				//if there is any change
				if(weight>0){
					return setTileWeight(posX, posY, weight);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Set the weight of a carpet's tile
	 * @param posX
	 * @param posY
	 */
	public Tile setTileWeight(int posX, int posY, double weight){
		int posXNew = posX+(tiles[0].length/2);
		int posYNew = posY+(tiles.length/2);
		tiles[posYNew][posXNew].setWeight(weight);
		return tiles[posYNew][posXNew];
	}
	
	/**
	 * Reset the carpet
	 */
	public void clearTiles(){
		for(int i=0; i < tiles.length; i++){
			for(int j=0; j<tiles[0].length; j++){
				Tile actTile = tiles[i][j];
				actTile.setWeight(0.0);
			}
		}
	}
	
	/**
	 * Get the carpet's width
	 * @return
	 */
	public int getWidth(){
		return tiles.length;
	}
	
	/**
	 * Get the carpet's height
	 * @return
	 */
	public int getHeight(){
		return tiles[0].length;
	}
	
	public Path getPath(){
		return this.path;
	}
	
	/**
	 * Show the carpet's active tiles in the console
	 * X is the actual tile, L is the last one
	 */
	public void show(){
		for(int i=0; i<getWidth(); i++){
			for(int j=0; j<getHeight(); j++){
				if(tiles[i][j] == path.getLastTile()){
					System.out.print("L");
				}else if(tiles[i][j].isActive()){
					System.out.print("X");
				}else{
					System.out.print(".");					
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
