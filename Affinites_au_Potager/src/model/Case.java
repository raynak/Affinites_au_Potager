package model;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Case {

	protected int x;
	protected int y;
	protected Color couleur;
	protected boolean hasPlant;

	public Case(int x, int y) {
		this.x = x;
		this.y = y;
		this.hasPlant=false;
	}

	public abstract void setPlante(Plante plante);

	public boolean getHasPlant(){
		return hasPlant;
	}

	public Planche getPlanche(Jardin jardin){
		for (ZonePlantation z : jardin.getZones()) {
			for(Planche p : z.getPlanches()){
				if(p.getCases().contains(this))
					return p;
			}
		}
		return null;
	}

	public ArrayList<Case> voisins(Jardin jardin){
		return jardin.casesVoisines(this);
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
		ArrayList<Case> liste = this.voisins(jardin);
		return liste.contains(laCase2);
	}

	public static void main(String[] args){
		Jardin j = new Jardin(6,8);
		
		Case c1 = j.getTerrain()[1][0];
		ArrayList<Case> liste = c1.voisins(j);
		System.out.println(liste);
		Case c2 = j.getTerrain()[1][1];
		System.out.println(c1.estMitoyenne(c2, j));
	}

	public abstract Plante getPlante();
}
