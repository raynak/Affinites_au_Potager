package model.combinatoire;

import model.jardin.Jardin;
import model.jardin.Planche;
import model.jardin.ZonePlantation;

public class ModeleCombiAlea extends ModeleCombi {

	public ModeleCombiAlea(Jardin jardin) {
		super(jardin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void algoOptimisation() {
		System.out.println("Algo alea");
		for (ZonePlantation zonePlantation : zones) {
			for (Planche planche : zonePlantation.getPlanches()) {
				int aleaPlante = (int) (Math.random() * this.jardin
						.getPlantes().size());
				System.out.println(aleaPlante);
				planche.setPlante(this.jardin.getPlantes().get(aleaPlante));
			}
		}

	}


}
