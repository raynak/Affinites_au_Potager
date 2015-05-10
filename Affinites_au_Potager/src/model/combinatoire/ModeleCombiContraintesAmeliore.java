package model.combinatoire;

import java.util.LinkedList;

import model.jardin.CaseCultivable;
import model.jardin.Jardin;
import model.jardin.Planche;
import model.jardin.Plante;
import model.jardin.ZonePlantation;

public class ModeleCombiContraintesAmeliore extends ModeleCombi {

	public ModeleCombiContraintesAmeliore(Jardin jardin) {
		super(jardin);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Choisir la planche doit le plus de voisins sont plantés Choisir plante
	 * selon affinités max Choisir plante parmi celles possibles au hasard
	 * (Nouvelle liste) Quantités
	 */
	public void algoOptimisation() {
		Plante p1 = null;
		Plante p2;
		int cpt = 0;
		System.out.println("Execution de l'algo glouton");
		/* Initialisation */
		System.out.println(jardin.getZones().size());

		for (ZonePlantation zonePlantation : zones) {
			//System.out.println("Parcours des zones de plantation");
			LinkedList<Planche> planchesFixes = new LinkedList<Planche>();
			for (Planche planche : zonePlantation.getPlanches()) {
				//System.out.println("Parcours des planches de la zone");
				//System.out.println(planche.getCases().get(0).getHasPlant());
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
				p2 = this.getMax();
//				System.out
//						.println(((CaseCultivable) this.jardin.getTerrain()[planche
//								.getX()][planche.getY()]).getPlante().getNom()
//								+ " en "
//								+ planche.getX()
//								+ "-"
//								+ planche.getY());
				cpt = 1;
			} else {
				/* Sinon on part d'une plante fixée choisie au hasard */
				int alea = (int) (Math.random() * planchesFixes.size());
				planche = planchesFixes.get(alea);
				p2 = planche.getPlante();
				cpt = planchesFixes.size();
			}
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
						if (aPlanter != p1) {
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
					}

					// Choix de la plante à partir de plantesMax
					Plante p = this.getMinContraintes(plantesMax);
					laPlanche.setPlante(p);
					System.out.println("Plante plantée : "+p.getNom());
					p1 = p2;
					p2 = p;
					cpt++;
				}
				voisins.addAll(laPlanche.planchesVoisines(this.jardin));
			}
		}
	}
}
