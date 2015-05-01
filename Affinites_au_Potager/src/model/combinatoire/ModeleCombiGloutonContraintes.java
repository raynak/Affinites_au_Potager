package model.combinatoire;

import java.util.LinkedList;

import model.jardin.CaseCultivable;
import model.jardin.Jardin;
import model.jardin.Planche;
import model.jardin.Plante;
import model.jardin.ZonePlantation;

public class ModeleCombiGloutonContraintes extends ModeleCombi {

	public ModeleCombiGloutonContraintes(Jardin jardin) {
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
				System.out.println("Parcours des planches de la zone");
				System.out.println(planche.getCases().get(0).getHasPlant());
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
				System.out
						.println("on plante la planche avec " + this.getMax());
				planche.setPlante(this.getMax());
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
				if (!laPlanche.getHasPlant()) {
					int score = -100;
					int scoreTmp;
					Plante plante = this.plantes.get(0);
					LinkedList<Plante> plantesMax = new LinkedList<Plante>();
					plantesMax.add(plante);
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
					for (Plante plante2 : plantesMax) {
						System.out.println("plantes max : "+plante2.getNom());
					}
					laPlanche.setPlante(this.getMinContraintes(plantesMax));
					cpt++;
				}
				voisins.addAll(laPlanche.planchesVoisines(this.jardin));
			}
		}
	}
}
