package model;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Case {

	protected int x;
	protected int y;
	protected Color couleur;

	public Case(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public ArrayList<Case> voisins(Jardin j){
		return new ArrayList<Case>();
	}
	
	public abstract int score();
	
	public boolean aUnVoisinLibre(){
		return true;
	}
}
