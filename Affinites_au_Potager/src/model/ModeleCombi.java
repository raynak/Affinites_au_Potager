package model;

import java.util.LinkedList;

public abstract class ModeleCombi {
	public Jardin jardin;
	public LinkedList<ZonePlantation> zones;
	public LinkedList<Planche> planches;
	public LinkedList<Plante> plantes;
	
	public ModeleCombi(Jardin jardin){
		this.jardin = jardin;
		this.zones = jardin.getZones();
		this.plantes = jardin.getPlantes();
	}
	
	public int score(){
		return 0;
	}
	
	public void algo(){
	}

}
