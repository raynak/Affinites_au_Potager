package model;

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

	public LinkedList<CaseCultivable> voisinsPlanche(){
		return null;
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

	public int scorePlanche(Plante aPlanter) {
		int score = 0;
		LinkedList<Case> cases = this.getCases();
		for (Case case1 : cases) {
			score += case1.score();
		}
		return score;
	}
	
}
