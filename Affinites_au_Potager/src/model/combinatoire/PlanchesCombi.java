package model.combinatoire;

import java.util.LinkedList;

import model.Jardin;
import model.Planche;
import model.Plante;

public class PlanchesCombi {

	Planche plancheCourante;
	Plante plante;
	LinkedList<PlanchesCombi> planchesCombiVoisines;
	
	public PlanchesCombi(Planche p, Jardin j){
		this.plancheCourante = p;
		this.plante = null;
		this.planchesCombiVoisines = new LinkedList<PlanchesCombi>();
		for (Planche pv : p.planchesVoisines(j)){
			this.planchesCombiVoisines.add(new PlanchesCombi(pv, j));
		}
		
	}
}
