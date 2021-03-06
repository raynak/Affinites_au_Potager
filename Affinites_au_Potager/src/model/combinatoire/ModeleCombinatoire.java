package model.combinatoire;

import java.util.LinkedList;

import model.jardin.CaseCultivable;
import model.jardin.Jardin;
import model.jardin.Planche;
import model.jardin.Plante;
import model.jardin.ZonePlantation;

public class ModeleCombinatoire extends ModeleCombi{
	
	public ModeleCombinatoire(Jardin jardin) {
		super(jardin);
	}

	public void algo(){
		/* Initialisation */
		for (ZonePlantation zonePlantation : zones) {
		int aleaPlanche = (int) (Math.random() * (zonePlantation.getPlanches().size()-1));
		/* On récupère la planche */
		Planche planche = zonePlantation.getPlanches().get(aleaPlanche);
		/* Et on met la même plante sur toute la planche */
		planche.setPlante(this.jardin.getPlantes().get(0));
		//faire avec les planches
		int cpt = 1;
		/* algo*/
			/* Récupération des voisins de la planche */
			LinkedList<CaseCultivable> voisins = planche.voisinsPlanche(this.jardin);
			/* Tant que toutes les cases ne sont pas remplies */
			while(cpt != zonePlantation.getPlanches().size()){
				/* Pour chaque voisin, on calcule le score max */
			for (CaseCultivable caseCultivable : voisins) {
				if(!caseCultivable.getHasPlant()){
					/* try catch */
					planche = zonePlantation.trouverPlanche(caseCultivable);
					int score = -100;
					int scoreTmp;
					Plante plante = this.plantes.get(0);
					for (Plante aPlanter : this.plantes) {
						planche.setPlante(aPlanter);
						scoreTmp = this.sc.scorePlanche(planche);
						if (scoreTmp < score){
							score = scoreTmp;
							plante = aPlanter;
						}
					}
					planche.setPlante(plante);
					cpt++;
				}
				voisins.addAll(planche.voisinsPlanche(this.jardin));
			}
			}
		}
	}

	@Override
	public void algoOptimisation() {
		
	}

	@Override
	public int score() {
		// TODO Auto-generated method stub
		return 0;
	}

}
