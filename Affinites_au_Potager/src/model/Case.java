package model;

import java.awt.Color;
import java.util.ArrayList;

import exceptions.NoAffiniteException;

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

	public abstract boolean getHasPlant();

	/**
	 * Retourne la planche qui correspond à la case dans un jardin donné en paramètre
	 * @param jardin le jardin dans lequel nous cherchons la planche de la case
	 * @return la planche qui correspond à la case
	 */
	public Planche getPlanche(Jardin jardin){
		for (ZonePlantation z : jardin.getZones()) {
			for(Planche p : z.getPlanches()){
				if(p.getCases().contains(this))
					return p;
			}
		}
		return null;
	}

	/**
	 * Retourne les voisins de la case dans le jardin passé en paramètre
	 * @param jardin le jardin dans lequel nous faisons les calculs
	 * @return une liste de cases correspondants aux voisins de la case
	 */
	public ArrayList<Case> voisins(Jardin jardin){
		return jardin.casesVoisines(this);
	}

	/**
	 * Retourne le score de la case dans un jardin donné en paramètre
	 * @param jardin le jardin dans lequel nous faisons les calculs
	 * @return le score de la case
	 */
	public abstract int score(Jardin jardin);

	/**
	 * A RETIRER ??
	 * @return
	 */
	public boolean aUnVoisinLibre(){
		return true;
	}

	public abstract String typeString();

	/**
	 * Retourne la couleur de la case
	 * @return la couleur de la case
	 */
	public Color getColor(){
		return this.couleur;
	}

	/**
	 * Retourne la coordonnée X de la case
	 * @return la coordonnée X de la case
	 */
	public int getX() {
		return x;
	}

	/**
	 * Retourne la coordonnée Y de la case
	 * @return la coordonnée Y de la case
	 */
	public int getY() {
		return y;
	}

	/**
	 * Retourne vrai si la case passée en paramètre est mitoyenne à la case dans le jardin
	 * @param laCase2 le case dont on souhaite connaitre la mitoyenneté
	 * @param jardin le jardin dans lequel nous faisons les calculs
	 * @return true si les cases sont mitoyennes, false sinon
	 */
	public boolean estMitoyenne(Case laCase2, Jardin jardin) {
		ArrayList<Case> liste = this.voisins(jardin);
		return liste.contains(laCase2);
	}
	
	public abstract Case passToFixOrVariable(Plante plante);


	public static void main(String[] args){
		Jardin j = new Jardin(6,8);
		
		Case c1 = j.getTerrain()[1][0];
		ArrayList<Case> liste = c1.voisins(j);
		System.out.println(liste);
		Case c2 = j.getTerrain()[1][1];
		System.out.println(c1.estMitoyenne(c2, j));
	}

	public abstract Plante getPlante();
	
	public abstract int getAffinites(Case c) throws NoAffiniteException;
}
