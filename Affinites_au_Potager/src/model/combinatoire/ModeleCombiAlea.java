package model.combinatoire;

import java.util.LinkedList;

import model.Jardin;
import model.Planche;
import model.ZonePlantation;

public class ModeleCombiAlea extends ModeleCombi {

	public ModeleCombiAlea(Jardin jardin) {
		super(jardin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void algoOptimisation() {
		for (ZonePlantation zonePlantation : zones) {
			for (Planche planche : zonePlantation.getPlanches()) {
				int aleaPlante = (int) (Math.random() * this.jardin
						.getPlantes().size());
				planche.setPlante(this.jardin.getPlantes().get(aleaPlante));
			}
		}

	}

	@Override
	public int score() {
		// TODO Auto-generated method stub
		return 0;
	}

}
