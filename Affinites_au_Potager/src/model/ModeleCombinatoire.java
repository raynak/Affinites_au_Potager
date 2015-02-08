package model;

import java.util.LinkedList;

public class ModeleCombinatoire {

	private Jardin jardin;
	private LinkedList<ZonePlantation> zones;
	
	public ModeleCombinatoire(Jardin jardin){
		this.jardin = jardin;
	}
	
	public void ajouterZone(ZonePlantation zone){
		this.zones.add(zone);
	}
	
	public void retirerZone(ZonePlantation zone){
		this.zones.remove(zone);
	}
	
	public LinkedList<CaseCultivable> voisinsCase(){
		return null;
	}

	// A GARDER ??
	public LinkedList<Plante> voisinsPlanche(){
		return null;
	}
	
	public void algoGlouton(){
		
	}
}
