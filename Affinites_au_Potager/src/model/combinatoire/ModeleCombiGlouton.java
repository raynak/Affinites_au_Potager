package model.combinatoire;

import java.util.LinkedList;

import model.CaseCultivable;
import model.Jardin;
import model.Planche;
import model.Plante;
import model.ZonePlantation;

public class ModeleCombiGlouton extends ModeleCombi {

	public ModeleCombiGlouton(Jardin jardin) {
		super(jardin);
		// TODO Auto-generated constructor stub
	}

	public void algoOptimisation() {
		/* Initialisation */
		for (ZonePlantation zonePlantation : zones) {
			LinkedList<Planche> planchesFixes = new LinkedList<Planche>();
			for (Planche planche : zonePlantation.getPlanches()) {
				if (planche.getCases().get(0).getHasPlant())
					planchesFixes.add(planche);
			}
			Planche planche;
			/* Si aucune planche ne contient une plante fixée, on en prend une au hasard */
			if (planchesFixes.isEmpty()) {
				int aleaPlanche = (int) (Math.random() * (zonePlantation.getPlanches().size() - 1));
				/* On récupère la planche */
				planche = zonePlantation.getPlanches().get(aleaPlanche);
				/* Et on met la même plante sur toute la planche */
				planche.setPlante(this.jardin.getPlantes().get(0));
			} else {
				/* Sinon on part d'une plante fixée choisie au hasard */
				int alea = (int)(Math.random() * planchesFixes.size());
				planche = planchesFixes.get(alea);
			}
			// faire avec les planches
			int cpt = 1;
			/* algo */
			/* Récupération des voisins de la planche */
			LinkedList<CaseCultivable> voisins = planche
					.voisinsPlanche(this.jardin);
			/* Tant que toutes les cases ne sont pas remplies */
			while (cpt != zonePlantation.getPlanches().size()) {
				/* Pour chaque voisin, on calcule le score max */
				for (CaseCultivable caseCultivable : voisins) {
					if (!caseCultivable.getHasPlant()) {
						planche = zonePlantation.trouverPlanche(caseCultivable);
						int score = -100;
						int scoreTmp;
						Plante plante = this.plantes.get(0);
						for (Plante aPlanter : this.plantes) {
							planche.setPlante(aPlanter);
							scoreTmp = planche.scorePlanche(this.jardin);
							if (scoreTmp < score) {
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

}
