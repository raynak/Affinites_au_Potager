package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import exceptions.PlancheNonMitoyenneException;
import exceptions.ZoneScindeeEnDeuxException;

public class ZonePlantation {

	private LinkedList<Planche> planches;
	private LinkedList<Plantation> plantations;

	public ZonePlantation(){
		this.planches = new LinkedList<Planche>();
	}

	public ZonePlantation(Planche p){
		this.planches = new LinkedList<Planche>();
		this.planches.push(p);
	}

	/**
	 * @return the planches
	 */
	public LinkedList<Planche> getPlanches() {
		return planches;
	}

	public LinkedList<Case> getCases() {
		LinkedList<Case> cases = new LinkedList<Case>();
		for (Planche planche : planches) {
			cases.addAll(planche.getCases());
		}
		return cases;
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

	public boolean peutAccueillirPlanche(Planche planche, Jardin j){
		if (this.planches.size() == 0) {return true;}
		else {
			for (Planche p : this.planches){
				if ( p.estMitoyenne(planche, j) ) {
					return true;
				}
			}
		}
		return false;
	}


	public void ajouterPlanche(Planche planche, Jardin j) throws PlancheNonMitoyenneException{
		System.out.println("ajout planche");
		if (this.peutAccueillirPlanche(planche, j)){
			this.planches.add(planche);
		} else {
			throw new PlancheNonMitoyenneException();
		}
	}



	/**
	 * Supprimer la planche de la zone
	 * Déclenche l'exception si la suprresion de la planche entraine la scission de la zone en 2
	 * @param planche la planche à supprimer
	 * @throws ZoneScindeeEnDeuxException si la suppression de la planche scinde la zone en deux
	 */
	public void supprimerPlanche(Planche planche, Jardin jardin) throws ZoneScindeeEnDeuxException{
		this.planches.remove(planche);
		Planche[] ptab = new Planche[this.planches.size()];
		this.planches.toArray(ptab);
		for (int i=0; i<ptab.length-1; i++){
			for (int j=i+1; j<ptab.length; j++){
				if (!ptab[i].estMitoyenne(ptab[j], jardin)){
					/* on declenche l'exception pour indiquer que la supression de la planche va scinder la zone en 2*/
					throw new ZoneScindeeEnDeuxException();
				}
			}
		}
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

	public boolean containsPlanche(Planche p){
		return this.planches.contains(p);
	}

	public void paintFieldZone(Graphics g, int size) {
		for (Planche p : this.planches){
			for (Case laCase : p.getCases()){
			//	g.setColor(new Color(0,0,150,20));
				g.fillRect(laCase.x*size, laCase.y*size, size, size);
			}
		}
	}

}
