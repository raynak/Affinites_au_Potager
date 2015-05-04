package model.jardin;

import java.util.LinkedList;

import exceptions.PlancheNonMitoyenneException;
import exceptions.ZoneScindeeEnDeuxException;

public class ZonePlantation {

	private LinkedList<Planche> planches;

	/**
	 * Constructeur d'une zone de plantation, le constructeur construit une nouvelle liste (pour l'instant vide) de planches
	 */
	public ZonePlantation(){
		this.planches = new LinkedList<Planche>();
	}

	/**
	 * Constructeur d'une zone de plantation contenant pour le moment une seule planche
	 * @param p la planche contenue dans la zone
	 */
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


	/**
	 * @param planches the planches to set
	 */
	public void setPlanches(LinkedList<Planche> planches) {
		this.planches = planches;
	}
	public LinkedList<Case> getCases() {
		LinkedList<Case> cases = new LinkedList<Case>();
		for (Planche planche : planches) {
			cases.addAll(planche.getCases());
		}
		return cases;
	}
	
	/**
	 * Retourne vrai si la planche passée en paramètre peut être ajouté à la zone
	 * @param planche la planche dont on veut savoir si elle peut etre ajoutée à la zone
	 * @param j le jardin 
	 * @return true si la zone peut etre ajoutée à la zone, false sinon
	 */
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


	/**
	 * Ajoute la planche passée en paramètre à la zone si la planche peut l'etre
	 * @param planche la planche à ajouter à la zone
	 * @param j le jardin
	 * @throws PlancheNonMitoyenneException
	 */
	public void ajouterPlanche(Planche planche, Jardin j) throws PlancheNonMitoyenneException{
		//System.out.println("ajout planche");
		if (this.peutAccueillirPlanche(planche, j)){
			this.planches.add(planche);
		} else {
			throw new PlancheNonMitoyenneException();
		}
	}



	/**
	 * Supprime la planche de la zone
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

	/**
	 * Retourne la planche à laquelle appartient la case passée en paramètre
	 * @param laCase la case dont trouver la planche
	 * @return la planche à laquelle appartient la case
	 */
	public Planche trouverPlanche(Case laCase){
		for (Planche planche : this.planches){
			if (planche.appartientALaPlanche(laCase)){
				return planche;
			}
		}
		return null;
	}

	/**
	 * Retourne vrai si la planche passée en paramètre appartient à la zone
	 * @param p la planche dont tester l'appartenance à la zone
	 * @return true si la planche appartient à la zone, false sinon
	 */
	public boolean containsPlanche(Planche p){
		return this.planches.contains(p);
	}
	
	/**
	 * Retourne vrai si l'ensemble des planches de la zone est valide,
	 * c'est à dire si elles font bien toutes parties de la même unique zone
	 * @param jardin
	 * @return vrai si la zone est valide
	 */
	public boolean validerEnsemblePlanches(Jardin jardin){
		for (int i=0; i<this.planches.size(); i++){
			for (int j=0; j<this.planches.size(); j++){
				if (i==j){ continue; }
				if ( !(this.planches.get(i).estMitoyenne(this.planches.get(j), jardin)) ) {
					return false;
				}
			}
		}
		return true;
	}

}
