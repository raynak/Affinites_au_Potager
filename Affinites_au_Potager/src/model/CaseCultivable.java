package model;

import java.awt.Color;

public class CaseCultivable extends Case {

	private Plante plante;
	private boolean fixeOuVariable;
	
	public CaseCultivable(int x, int y) {
		super(x, y);
		super.couleur = Color.GREEN;
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

	/**
	 * @return the fixeOuVariable
	 */
	public boolean isFixeOuVariable() {
		return fixeOuVariable;
	}

	/**
	 * @param fixeOuVariable the fixeOuVariable to set
	 */
	public void setFixeOuVariable(boolean fixeOuVariable) {
		this.fixeOuVariable = fixeOuVariable;
	}

	@Override
	public int score() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void fixerPlante(){
		this.fixeOuVariable = true;
	}
	
	public void libererPlante(){
		this.fixeOuVariable = false;
	}

	/**
	 * Choisit la plante à cultiver sur la case en maximisant ses affinités 
	 * avec les plantes des cases voisines
	 */
	public void optimiserPlante(){
		
	}
}
