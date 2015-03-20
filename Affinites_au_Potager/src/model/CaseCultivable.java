package model;

import java.awt.Color;
import java.util.LinkedList;

public class CaseCultivable extends Case {

	private Plante plante;

	public CaseCultivable(int x, int y) {
		super(x, y);
		super.couleur = new Color(220,170,50);
		super.hasPlant = false;
		this.plante = null;

	}

	public CaseCultivable(int x, int y, Plante plante){
		super(x, y);
		super.couleur = new Color(220,170,50);
		this.plante = plante;
	}


	public LinkedList<CaseCultivable> voisinsCase(Jardin jardin){
		return jardin.casesVoisinesCultivables(this);
	}

	/**
	 * @return the plante
	 */
	public Plante getPlante() {
		return plante;
	}

	/**
	 * @param plante the plante to set
	 */
	public void setPlante(Plante plante) {
		this.plante = plante;
		this.hasPlant = true;
	}

	@Override
	public int score(Jardin jardin) {
		//System.out.println("nom de la plante en entree de score :"+this.plante.getNom());
		int score = 0;
	//	System.out.println("CaseCourante "+this.getX()+"-"+this.getY());
		if (!this.getHasPlant()){
			return score;
		}
		
		LinkedList<CaseCultivable> voisins = this.voisinsCase(jardin);
		System.out.println("nb voisins "+voisins.size());
		for (CaseCultivable caseCultivable : voisins) {
			System.out.println(caseCultivable.getHasPlant());
			System.out.println(caseCultivable.getX()+" "+caseCultivable.getY());

			if(caseCultivable.getHasPlant()){
				System.out.println("nom de la plante"+this.getPlante().toString());
				score += caseCultivable.getPlante().getAffinite(this.getPlante());
				System.out.println("etat du score " +score);
			}
		}
		System.out.println("Score case cultivable : "+score);

		return score;
	}

	/**
	 * Choisit la plante à cultiver sur la case en maximisant ses affinités 
	 * avec les plantes des cases voisines
	 */
	public void optimiserPlante(){

	}

	public  String typeString(){
		return "Cultivable";
	}
	
	public String toString(){
		return this.x+"-"+this.y+"-"+this.plante.getNom();
	}
}
