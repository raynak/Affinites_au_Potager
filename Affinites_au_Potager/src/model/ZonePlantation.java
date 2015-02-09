package model;

import java.util.LinkedList;

public class ZonePlantation {

	private LinkedList<Planche> planches;
	private LinkedList<Plantation> plantations;
	
	public ZonePlantation(){
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
	
	public void ajouterPlantation(Plantation plantation){
		this.plantations.add(plantation);
	}
	
	public void reinitialiserPlantations(){
		this.plantations = new LinkedList<Plantation>();
	}

	public void ajouterPlanche(Planche planche){
		
	}
	
	public void supprimerPlanche(Planche planche){
		
	}
	
	public Plantation getCurrentPlantation(){
		return this.plantations.get(this.plantations.size());
	}
	
	public Plantation getPlantation(int i){
		return this.plantations.get(i);
	}
	
	public Planche trouverPlanche(Case caseTerrain){
		return null;
	}
	
	public void planteFixe(Plante planteFixe,Case caseTerrain){
		
	}
	

}
