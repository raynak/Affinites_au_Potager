package model.combinatoire;

import java.util.LinkedList;

import model.CaseCultivable;
import model.Jardin;
import model.Planche;
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

	
	public  int score(){
		System.out.println("Calcul du score du jardin :");
		int score = 0;
		for (int i=0; i<this.jardin.getTerrain().length; i++){
			for (int j=0; j<this.jardin.getTerrain()[0].length; j++){
				System.out.println("Ajout du score de la case "+i+"-"+j);
				if (this.jardin.getTerrain()[i][j] instanceof CaseCultivable){System.out.println("cultivable "+this.jardin.getTerrain()[i][j].getHasPlant());}
				else {System.out.println(" non cultivable");}
				score += this.jardin.getTerrain()[i][j].score(jardin);
			}
		}
		return score;
	};
	
}
