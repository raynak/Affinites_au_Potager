package model.jardin;

import java.util.Collections;
import java.util.LinkedList;

import exceptions.PlancheConstructorException;

public class Planche {

	private int x;
	private int y;
	private int nbCases;
	private boolean orientation;	
	private LinkedList<Case> cases;

	public Planche(){
		this.cases = new LinkedList<Case>();
	}

	/**
	 * Constructeur de la planche
	 * @param x la coordonnée x de la première case de la planche
	 * @param y la coordonnée y de la première case de la planche
	 * @param nbCases le nombre de cases de la planche
	 * @param orientation l'orientation de la planche, true pour une planche horizontale et false pour une planche verticale 
	 */
	public Planche(int x, int y, int nbCases, boolean orientation){
		this.x = x;
		this.y = y;
		this.nbCases = nbCases;
		this.orientation = orientation;
		this.cases = new LinkedList<Case>();

	}

	/**
	 * Le constructeur de la planche dans un jardin donné en paramètre
	 * @param x la coordonnée x de la première case de la planche
	 * @param y la coordonnée y de la première case de la planche
	 * @param nbCases le nombre de cases de la planche
	 * @param orientation l'orientation de la planche, true pour une planche horizontale, false pour une planche verticale
	 * @param j le jardin où l'on souhaite mettre la planche
	 */
	public Planche(int x, int y, int nbCases, boolean orientation, Jardin j){
		this.x = x;
		this.y = y;
		this.nbCases = nbCases;
		this.orientation = orientation;
		this.cases = new LinkedList<Case>();
		for (int i=0; i<nbCases; i++){
			if (orientation){
				this.cases.addLast(j.getTerrain()[x+i][y]);
			} else {
				this.cases.addLast(j.getTerrain()[x][y+i]);
			}
		}
	}

	/**
	 * Constructeur pour une c planche d'une seule case
	 * @param uneCase la case contenue dans la planche
	 * @throws PlancheConstructorException
	 */
	public Planche(Case uneCase) throws PlancheConstructorException{
		this.x = uneCase.getX();
		this.y = uneCase.getY();
		this.nbCases = 1;
		this.orientation = true;
		LinkedList<Case> list = new LinkedList<Case>();
		System.out.println("Cultivable ?"+(uneCase instanceof CaseCultivable));
		if (! (uneCase instanceof CaseCultivable)){
			throw new PlancheConstructorException();	
		}
		list.add(uneCase);
		this.cases = list;
	}

	/**
	 * Constructeur d'une planche en partant d'une liste de cases
	 * @param list la liste des cases contenues dans la planche à construire
	 * @throws PlancheConstructorException
	 */
	public Planche(LinkedList<Case> list) throws PlancheConstructorException{
		if (list.size()==1){
			Case uneCase = list.get(0);
			this.x = uneCase.getX();
			this.y = uneCase.getY();
			this.nbCases = 1;
			this.orientation = true;
			if (! (uneCase instanceof CaseCultivable)){
				throw new PlancheConstructorException();	
			}
			this.cases = list;
		}
		else {
			boolean orientation = true;
			int x0 = list.get(0).x;
			int y0 = list.get(0).y;
			int x1 = list.get(1).x;
			int y1 = list.get(1).y;
			System.out.println("cases : "+x0 + ";"+x1+";"+y0+";"+y1);
			if (x0 == x1){
				System.out.println("horizontal");
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
				System.out.println("vertical");
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
	}

	/**
	 * Retourne le hasPlant indiquant si la planche contient une plante ou non
	 * @return bool true si la planche courante a une plante, false sinon
	 */
	public boolean getHasPlant(){
		return this.cases.get(0).getHasPlant();
	}

	/**
	 * Retourne la liste des planches voisines à la planche courante dans le jardin
	 * @param jardin le jardin dans lequel nous travaillons
	 * @return la liste des planches voisines à la planche courante
	 */
	public LinkedList<Planche> planchesVoisines(Jardin jardin){
		LinkedList<Planche> voisines = new LinkedList<Planche>();
		LinkedList<CaseCultivable> casesVoisines = this.voisinsPlanche(jardin);
		for (CaseCultivable caseCultivable : casesVoisines) {
			Planche p = caseCultivable.getPlanche(jardin);
			if(!voisines.contains(p) && p!=null){
				voisines.add(p);
			}
		}
		return voisines;
	}

	/**
	 * Retourne la liste des planches voisines qui ne sont pas plantées
	 * @param jardin le jardin dans lequel nous travaillons
	 * @return la liste des planches voisines à la planche courante n'ayant pas de plante.
	 */
	public LinkedList<Planche> planchesVoisinesSansPlante(Jardin jardin){
		LinkedList<Planche> voisines = new LinkedList<Planche>();
		LinkedList<CaseCultivable> casesVoisines = this.voisinsPlanche(jardin);
		System.out.println("cases voisines : "+casesVoisines.size());
		for (CaseCultivable caseCultivable : casesVoisines) {
			Planche p = caseCultivable.getPlanche(jardin);
			if(!voisines.contains(p) && p!=null && !p.getHasPlant()){
				System.out.println(p);
				voisines.add(p);
			}
		}
		return voisines;
	}

	/**
	 * Retourne la liste des cases voisines à la planche courante dans un jardin donné en paramètre
	 * @param jardin le jardin dans lequel nous travaillons
	 * @return la liste des cases voisines à la planche courante
	 */
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

	/**
	 * @return the cases
	 */
	public LinkedList<Case> getCases() {
		return cases;
	}

	/**
	 * set the cases
	 * @param cases
	 */
	public void setCases(LinkedList<Case> cases) {
		this.cases = cases;
	}

	/**
	 * @return the orientation
	 */
	public boolean isOrientation() {
		return orientation;
	}

	/**
	 * Retourne un booléen qui indique l'appartenance d'une case à la planche courante
	 * @param caseTerrain la case dont nous souhaitons tester l'appartenance à courante
	 * @return true si la case appartient à la planche, false sinon
	 */
	public boolean appartientALaPlanche(Case caseTerrain){
		for (int i=0; i<this.nbCases; i++){
			if (this.getCases().get(i).equals(caseTerrain)){
				return true;
			}
		}
		return false;
	}

	/**
	 * set une plante sur la planche
	 * @param plante 
	 */
	public void setPlante(Plante plante){
		LinkedList<Case> cases = this.getCases();
		for (Case case1 : cases) {
			case1.setPlante(plante);
		}
	}


	/**
	 * Retourne vrai si la planche en parametre est mitoyenne de la planche courante, 
	 * c'est à dire si une des cases de la planche en paramêtre est mitoyenne d'une des cases de la planche courante
	 * @param p la planche dont il faut tester la mitoyenneté avec la planche courante
	 * @return true si p est mitoyenne de la planche courante, faux sinon
	 */
	public boolean estMitoyenne(Planche p, Jardin j){
		//System.out.println(this+"\n"+p);
		for (Case laCase : p.cases){
			for (Case laCase2 : this.cases){
				if (laCase.estMitoyenne(laCase2, j)){
					//System.out.println("mitoyenne");
					return true;
				}
			}
		}
		//System.out.println("non mitoyenne");
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

	public Plante getPlante() {
		return this.cases.get(0).getPlante();
	}

	/**
	 * Ajoute une case à la liste des cases de la planche
	 * @param c la case à ajouter à la planche
	 * @throws PlancheConstructorException
	 */
	public void addCase(CaseCultivable c) throws PlancheConstructorException {
		if ( (this.cases.size()==0) 
				|| (this.orientation && this.y==c.getY())
				|| (!this.orientation && this.x==c.getX()) ){
			this.cases.add(c);
			this.x = c.getX();
			this.y = c.getY();
		}
	}


}
