package model.combinatoire;

import java.util.LinkedList;

import model.Jardin;
import model.Plante;
import model.ZonePlantation;

public abstract class ModeleCombi {
	public Jardin jardin;
	public LinkedList<ZonePlantation> zones;
//	public LinkedList<Planche> planches; ??
	public LinkedList<Plante> plantes;
	
	public ModeleCombi(Jardin jardin){
		this.jardin = jardin;
		this.zones = jardin.getZones();
		this.plantes = jardin.getPlantes();
	}
	
	/* Score à mettre */
	
	public abstract void algoOptimisation();

}
