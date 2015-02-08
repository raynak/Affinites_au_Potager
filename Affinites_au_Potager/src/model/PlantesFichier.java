package model;

import java.util.LinkedList;

public class PlantesFichier {

	private String fichier;
	private LinkedList<Plante> plantesDispo;
	
	PlantesFichier(String fichier){
		this.fichier = fichier;
		this.plantesDispo = setPlantesDispo(fichier);
	}
	
	public LinkedList<Plante> getPlantesDispo(){
		return this.plantesDispo;
	}
	
	public LinkedList<Plante> setPlantesDispo(String fichier){
		return plantesDispo;
		// Récupérer toutes les plantes du fichier
	}
}
