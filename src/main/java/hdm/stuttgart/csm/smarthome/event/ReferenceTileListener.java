package hdm.stuttgart.csm.smarthome.event;

import org.json.simple.parser.ParseException;

import hdm.stuttgart.csm.smarthome.object.Tile;

public class ReferenceTileListener extends BaseClusterListener {
	
	private Tile referenceTile;
	
	public ReferenceTileListener(Tile referenceTile){
		this.referenceTile = referenceTile;
	}

	public void call(Object... args) {
		// parse json
		try {
			readJSON(args[0].toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// init values
		Tile currentCOG = parseJSON(rawCOG);
		if(currentCOG.getCapacity() > 100){
			this.referenceTile = currentCOG;
		}
	}
	
	public Tile getReferenceTile(){
		return this.referenceTile;
	}

}
