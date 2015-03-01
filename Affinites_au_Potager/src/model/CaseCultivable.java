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
	
	
	public LinkedList<CaseCultivable> voisinsCase(Jardin jardin){
		LinkedList<CaseCultivable> cases = new LinkedList<CaseCultivable>();
	/*	for(int i = -1;i<2;i++){
			for(int j=-1;j<2;j++){
				Case caseJardin = jardin.getTerrain()[this.x+i][this.y+j];
				if(i!=0 && j!=0 && caseJardin.???){
					cases.add(jardin.getTerrain()[this.x+i][this.y+j]);
				}
			}
		}*/
		return cases;
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
	int score = 0;
		LinkedList<CaseCultivable> voisins = this.voisinsCase(jardin);
		for (CaseCultivable caseCultivable : voisins) {
			if(caseCultivable.getHasPlant())
			score += caseCultivable.getPlante().getAffinite(this.getPlante());
		}
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
}
