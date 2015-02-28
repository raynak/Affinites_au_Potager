package model;

import java.awt.Color;
import java.util.LinkedList;

public abstract class Case {

	protected int x;
	protected int y;
	protected Color couleur;
	protected boolean hasPlant;

	public Case(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setPlante(Plante plante){
	}

	public boolean getHasPlant(){
		return hasPlant;
	}

	public Planche getPlanche(){
		return null;
	}

	public LinkedList<Case> voisins(Jardin jardin){
		return new LinkedList<Case>();
	}

	public abstract int score(Jardin jardin);

	public boolean aUnVoisinLibre(){
		return true;
	}

	public abstract String typeString();

	public Color getColor(){
		return this.couleur;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean estMitoyenne(Case laCase2, Jardin jardin) {
		LinkedList<Case> liste = this.voisins(jardin);
		return liste.contains(laCase2);
	}

}
