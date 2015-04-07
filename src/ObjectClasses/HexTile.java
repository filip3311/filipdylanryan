package ObjectClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class HexTile implements Serializable{
	private ArrayList<Clearing> clearings;
	private ArrayList<MapChit> chits;
	private String name;
	public HexTile(String inpName){
		name = inpName;
		clearings = new ArrayList<Clearing>();
		chits = new ArrayList<MapChit>();
	}
	
	public void addClearing(Clearing input){
		clearings.add(input);
	}
	
	public ArrayList<Clearing> getClearings(){
		return clearings;
	}
	
	public ArrayList<MapChit> getChits(){
		return chits;
	}
	
	public MapChit getWarningChit(){
		for (MapChit chit : chits){
			if(chit.getType().equals(MapChit.Type.WARNING)){
				return chit;
			}
		}
		return null;
	}
	
	public MapChit getSoundChit(){
		for (MapChit chit : chits){
			if(chit.getType().equals(MapChit.Type.SOUND)){
				return chit;
			}
		}
		return null;
	}

	public MapChit getSiteChit(){
		for (MapChit chit : chits){
			if(chit.getType().equals(MapChit.Type.SITE)){
				return chit;
			}
		}
		return null;
	}
	
	public String getName(){
		return name;
	}
	
	public void addChit(MapChit chit){
		chits.add(chit);
	}
}
