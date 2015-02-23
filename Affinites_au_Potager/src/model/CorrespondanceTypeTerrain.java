package model;

import java.util.HashMap;

public class CorrespondanceTypeTerrain {

	private HashMap<String, String> correspondance;
	
	private CorrespondanceTypeTerrain(){
		this.correspondance = new HashMap<String, String>();
		this.correspondance.put("HJ", "HorsJardin");
		this.correspondance.put("NC", "NonCultivable");
		this.correspondance.put("C", "Cultivable");
		this.correspondance.put("F", "Fixe");
		this.correspondance.put("V", "Variable");
	}
	
	private static CorrespondanceTypeTerrain INSTANCE = new CorrespondanceTypeTerrain();
	
	public static CorrespondanceTypeTerrain getInstance(){
		return INSTANCE;
	}
	
	public String getTypeTerrain(String s){
		return this.correspondance.get(s);
	}
}
