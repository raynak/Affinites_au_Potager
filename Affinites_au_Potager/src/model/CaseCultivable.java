package model;

import java.awt.Color;
import java.util.LinkedList;

public class CaseCultivable extends Case {

	private Plante plante;
	
	public CaseCultivable(int x, int y) {
		super(x, y);
		super.couleur = new Color(80,100,50);
		
	}
	
	public CaseCultivable(int x, int y, Plante plante){
		super(x, y);
		super.couleur = Color.GREEN;
		this.plante = plante;
	}
	
	
	public LinkedList<CaseCultivable> voisinsCase(){
		return null;
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
	}

	@Override
	public int score() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Choisit la plante à cultiver sur la case en maximisant ses affinités 
	 * avec les plantes des cases voisines
	 */
	public void optimiserPlante(){
		
	}

	public  String typeString(){
		return "Cultivable";
	};
}
