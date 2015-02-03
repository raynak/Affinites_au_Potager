package model;

import java.util.LinkedList;

public class Plantation {

	private LinkedList<Planche> planches;
	
	public Plantation(){
	}
	
	/**
	 * @return the planches
	 */
	public LinkedList<Planche> getPlanches() {
		return planches;
	}

	/**
	 * @param planches the planches to set
	 */
	public void setPlanches(LinkedList<Planche> planches) {
		this.planches = planches;
	}

	public void ajouterPlanche(Planche planche){
		
	}
	
	public void supprimerPlanche(Planche planche){
		
	}
	
	public Planche trouverPlanche(Case caseTerrain){
		return null;
	}
	
	public void fixerPlante(Case caseTerrain){
		
	}
	

}
