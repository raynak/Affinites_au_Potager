package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedList;

import exceptions.PlancheConstructorException;

public class Planche {

	private int x;
	private int y;
	private int nbCases;
	private boolean orientation;	
	private LinkedList<Case> cases;


	public Planche(int x, int y, int nbCases, boolean orientation){
		this.x = x;
		this.y = y;
		this.nbCases = nbCases;
		this.orientation = orientation;
	}

	public Planche(LinkedList<Case> list) throws PlancheConstructorException{
		boolean orientation = true;
		int x0 = list.get(0).x;
		int y0 = list.get(0).y;
		int x1 = list.get(1).x;
		int y1 = list.get(1).y;
		System.out.println("cases : "+x0 + ";"+x1+";"+y0+";"+y1);
		if (x0 == x1){
			/*si deux abscisses sont égales, la planche doit être verticale*/
			orientation = false;
			for (Case c : list){
				/* si une case ne possèdent pas la meme abscisse que les premières, il y a erreur*/
				if (c.x != x0){
					throw new PlancheConstructorException();
				}
				/*il faut trier les ordonnees dans l'ordre pour ensuite verifier qu'elles sont toutes consécutives*/
				Collections.sort(list, new CaseComparator());
				for (int i=1; i<list.size(); i++){
					if (list.get(i-1).y != list.get(i).y-1){
						throw new PlancheConstructorException();
					}
				}

			}
		}
		if (y0 == y1){
			orientation = true;
			for (Case c : list){
				if (c.y != y0){
					throw new PlancheConstructorException();
				}
				/*il faut trier les ordonnees dans l'ordre pour ensuite verifier qu'elles sont toutes consécutives*/
				Collections.sort(list, new CaseComparator());
				for (int i=1; i<list.size(); i++){
					System.out.println(list.get(i-1).x +"    " +(list.get(i).x-1));
					if (list.get(i-1).x != list.get(i).x-1){
						throw new PlancheConstructorException();
					}
				}
			}
		}
		this.orientation = orientation;
		this.setCases(list);
		this.nbCases = list.size();
		this.x = list.get(0).x;
		this.y = list.get(0).y;
	}

	public LinkedList<CaseCultivable> voisinsPlanche(Jardin jardin){
		LinkedList<CaseCultivable> voisins = new LinkedList<CaseCultivable>();
		for (Case case1 : cases) {
			for (Case case2 : case1.voisins(jardin))
				if (case2 instanceof CaseCultivable){
					voisins.add((CaseCultivable)case2);
				}
		}
		return voisins;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the nbCases
	 */
	public int getNbCases() {
		return nbCases;
	}

	public LinkedList<Case> getCases() {
		return cases;
	}

	public void setCases(LinkedList<Case> cases) {
		this.cases = cases;
	}

	/**
	 * @return the orientation
	 */
	public boolean isOrientation() {
		return orientation;
	}

	public boolean appartientALaPlanche(Case caseTerrain){
		return false;
	}

	public void setPlante(Plante plante){
		LinkedList<Case> cases = this.getCases();
		for (Case case1 : cases) {
			case1.setPlante(plante);
		}
	}

	public int scorePlanche(Jardin j) {
		int score = 0;
		LinkedList<Case> cases = this.getCases();
		for (Case case1 : cases) {
			score += case1.score(j);
		}
		return score;
	}

	/**
	 * Retourne vrai si la planche en parametre est pitoyenne de la planche courante, 
	 * c'est à dire si une des cases de la planche en paramêtre est mitoyenne d'une des cases de la planche courante
	 * @param p la planche dont il faut tester la mitoyenneté avec la planche courante
	 * @return true si p est mitoyenne de la planche courante, faux sinon
	 */
	public boolean estMitoyenne(Planche p, Jardin j){
		for (Case laCase : p.cases){
			for (Case laCase2 : this.cases){
				if (laCase.estMitoyenne(laCase2, j)){
					return true;
				}
			}
		}
		return false;
	}

	public String toString(){
		String s = "Case de départ : "+this.x+" - "+this.y+", NbCases : "+this.nbCases+", ";
		if (orientation){
			s+= "Horizontal";
		}
		else {
			s+="Vertical";
		}
		return s;
	}

	public void paintFieldPlanche(Graphics g, int size) {
		System.out.println(this.x*size +"    "+ this.y*size);
		int longueur = this.nbCases*size;
		int largeur = size;

		if (orientation){
			int tmp;
			tmp = longueur;
			longueur = largeur;
			largeur = tmp;
		}
		g.setColor(new Color(200,0,0,070));
		g.drawRect(this.x*size, this.y*size, largeur-4, longueur-4);

		g.setColor(new Color(200,0,0,8));
		g.fillRect(this.x*size, this.y*size, largeur-4, longueur-4);
	}
}
