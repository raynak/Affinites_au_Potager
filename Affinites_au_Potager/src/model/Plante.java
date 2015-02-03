package model;

import java.util.HashMap;

public class Plante {

	private String nom;
	private HashMap<String, Integer> affinites;
	
	public Plante(String nom){
		this.nom = nom;
	}
	
	/**
	 * @return the affinites
	 */
	public HashMap<String, Integer> getAffinites() {
		return affinites;
	}

	/**
	 * @param affinites the affinites to set
	 */
	public void setAffinites(HashMap<String, Integer> affinites) {
		this.affinites = affinites;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	public boolean peutEtrePlanteeSur(Case caseTerrain){
		return false;
	}
	
	public int getAffinite(Plante plante){
		return 0;
	}
	
}
