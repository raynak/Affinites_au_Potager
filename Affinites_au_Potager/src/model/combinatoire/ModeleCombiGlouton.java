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

	/**
	 * Choisir la planche doit le plus de voisins sont plantés Choisir plante
	 * selon affinités max Choisir plante parmi celles possibles au hasard
	 * (Nouvelle liste) Quantités
	 */
	public void algoOptimisation() {
		System.out.println("Execution de l'algo glouton");
		/* Initialisation */
		System.out.println(jardin.getZones().size());

		for (ZonePlantation zonePlantation : zones) {
			System.out.println("Parcours des zones de plantation");
			LinkedList<Planche> planchesFixes = new LinkedList<Planche>();
			for (Planche planche : zonePlantation.getPlanches()) {
				// System.out.println(((CaseCultivable)planche.getCases().get(0)).getPlante().getNom());
				if (planche.getCases().get(0).getHasPlant()) {
					System.out.println("La planche a une plante");
					planchesFixes.add(planche);
				}
			}
			Planche planche;
			System.out.println("taille planche fixee " + planchesFixes.size());
			/*
			 * Si aucune planche ne contient une plante fixée, on en prend une
			 * au hasard
			 */
			if (planchesFixes.isEmpty()) {
				System.out.println("Aucune planches avec une plante fixee");
				int aleaPlanche = (int) (Math.random() * (zonePlantation
						.getPlanches().size() - 1));
				/* On récupère la planche */
				planche = zonePlantation.getPlanches().get(aleaPlanche);
				/* Et on met la même plante sur toute la planche */
				System.out.println("on plante la planche avec "
						+ this.jardin.getPlantes().get(0));
				int aleaPlante = (int) (Math.random() * (this.jardin
						.getPlantes().size() - 1));
				planche.setPlante(this.jardin.getPlantes().get(aleaPlante));
				System.out
						.println(((CaseCultivable) this.jardin.getTerrain()[planche
								.getX()][planche.getY()]).getPlante().getNom()
								+ " en "
								+ planche.getX()
								+ "-"
								+ planche.getY());
			} else {
				/* Sinon on part d'une plante fixée choisie au hasard */
				int alea = (int) (Math.random() * planchesFixes.size());
				planche = planchesFixes.get(alea);
				System.out.println(planche);
			}
			int cpt = 1;
			/* algo */
			/* Récupération des voisins de la planche */
			LinkedList<Planche> voisins = planche.planchesVoisines(this.jardin);
			/* Tant que toutes les cases ne sont pas remplies */
			while (cpt != zonePlantation.getPlanches().size()) {
				/* Pour chaque voisin, on calcule le score max */
				Planche laPlanche = voisins.get(0);
				voisins.removeFirst();
				System.out.println(laPlanche);
				if (!laPlanche.getHasPlant()) {
					int score = -100;
					int scoreTmp;
					Plante plante = this.plantes.get(0);
					LinkedList<Plante> plantesMax = new LinkedList<Plante>();
					plantesMax.add(this.plantes.get(0));
					// Quand plusieurs possibilités : choisir par quantités
					// probabilistes de l'utilisateur
					for (Plante aPlanter : this.plantes) {
						laPlanche.setPlante(aPlanter);
						scoreTmp = laPlanche.scorePlanche(this.jardin);
						if (scoreTmp > score) {
							plantesMax.clear();
							plantesMax.add(aPlanter);
							score = scoreTmp;
							plante = aPlanter;
						} else {
							if (scoreTmp == score) {
								plantesMax.add(aPlanter);
							}
						}
					}

					// Choix de la plante à partir de plantesMax
					// Choix aléatoire
					int aleaPlante = (int) (Math.random() * (plantesMax.size() - 1));
					System.out.println("Plantage de la plante :"
							+ plante.toString());
					laPlanche.setPlante(plantesMax.get(aleaPlante));
					cpt++;
					System.out.println("cpt : "+cpt);
					System.out.println("total : "+zonePlantation.getPlanches().size());
				}
				voisins.addAll(laPlanche.planchesVoisinesSansPlante(this.jardin));
			}
		}
	}

	public String toString() {
		return "Je suis un glouton";
	}

	// @Override
	// public int score() {
	// // TODO Auto-generated method stub
	// return 42;
	// }

}
