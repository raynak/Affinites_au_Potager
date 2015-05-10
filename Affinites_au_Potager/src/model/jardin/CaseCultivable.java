package model.jardin;

import java.awt.Color;
import java.util.LinkedList;

import exceptions.NoAffiniteException;

public abstract class CaseCultivable extends Case {

	protected Plante plante;

	public CaseCultivable(int x, int y) {
		super(x, y);
		//System.out.println("Bien dans ce constructeur");

		super.couleur = new Color(220,170,50);
		super.hasPlant = false;
		this.plante = null;

	}

//	public CaseCultivable(int x, int y, Plante plante){
//		super(x, y);
//		super.couleur = new Color(220,170,50);
//		this.plante = plante;
//	}


	/**
	 * Retourne la liste des voisins de la case dans le jardin passé en paramètre
	 * @param jardin le jardin dans lequel nous cherchons les voisins
	 * @return la liste des voisins de la case dans le jardin jardin
	 */
	public LinkedList<CaseCultivable> voisinsCase(Jardin jardin){
		return jardin.casesVoisinesCultivables(this);
	}

	/**
	 * Retourne la plante sur la case
	 * @return plante la plante qui est sur la case 
	 */
	public Plante getPlante() {
		return plante;
	}

	/**
	 * Plante la plante plante sur la case et met à jour hasPlant
	 * @param plante la plante à planter
	 */
	public void setPlante(Plante plante) {
		this.plante = plante;
		this.hasPlant = true;
	}
	
	/**
	 * Change hasPlant en bool
	 * @param bool le booléen à mettre dans hasPlant
	 */
	public void setHasPlant(boolean bool){
		this.hasPlant = bool;
	}

	public  String typeString(){
		return "Cultivable";
	}
	
	public abstract CaseCultivable passToFixOrVariable(Plante plante);
	
	
	@Override
	public int getAffinites(Case c) throws NoAffiniteException {
		if (this.getHasPlant() && c.getHasPlant()){
			return this.getPlante().getAffinite(c.getPlante());
		}
		else {
			throw new NoAffiniteException();
		}
	}
	
	/**
	 * Retourne vrai si la case est une case variable, faux si c'est une case fixe
	 * @return true si la case est une case variable, false si c'est une case fixe
	 */
	public abstract boolean isVariable();
	
	public String toString(){
		return this.x+"-"+this.y+"-"+this.plante.getNom();
	}
}
