package model.combinatoire;

import java.util.LinkedList;

import model.jardin.CaseCultivable;
import model.jardin.Jardin;
import model.jardin.Planche;
import model.jardin.Plante;
import model.jardin.ZonePlantation;

public class ModeleCombiGloutonQtes extends ModeleCombi {

	public ModeleCombiGloutonQtes(Jardin jardin) {
		super(jardin);
	}

	/**
	 * Choisir la planche doit le plus de voisins sont plantés
	 * Choisir plante selon affinités max
	 * Choisir plante parmi celles possibles au hasard (Nouvelle liste)
	 * Quantités
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
			//	System.out.println(((CaseCultivable)planche.getCases().get(0)).getPlante().getNom());
				if (planche.getCases().get(0).getHasPlant()){
					System.out.println("La planche a une plante");
					planchesFixes.add(planche);
				}
			}
			Planche planche;
			System.out.println("taille planche fixee "+planchesFixes.size());
			/* Si aucune planche ne contient une plante fixée, on en prend une au hasard */
			if (planchesFixes.isEmpty()) {
				System.out.println("Aucune planches avec une plante fixee");
				int aleaPlanche = (int) (Math.random() * (zonePlantation.getPlanches().size() - 1));
				/* On récupère la planche */
				planche = zonePlantation.getPlanches().get(aleaPlanche);
				/* Et on met la même plante sur toute la planche */
				System.out.println("on plante la planche avec "+this.getPlantePreferee(this.jardin.getPlantes()));
				planche.setPlante(this.getPlantePreferee(this.jardin.getPlantes()));
				System.out.println(((CaseCultivable)this.jardin.getTerrain()[planche.getX()][planche.getY()]).getPlante().getNom()+" en "+planche.getX()+"-"+planche.getY());
			} else {
				/* Sinon on part d'une plante fixée choisie au hasard */
				int alea = (int)(Math.random() * planchesFixes.size());
				planche = planchesFixes.get(alea);
			}
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
						LinkedList<Plante> plantesMax = new LinkedList<Plante>();
						plantesMax.add(plante);
						// Quand plusieurs possibilités : choisir par quantités probabilistes de l'utilisateur
						for (Plante aPlanter : this.plantes) {
							planche.setPlante(aPlanter);
							scoreTmp = planche.scorePlanche(this.jardin);
							if (scoreTmp > score) {
								plantesMax.clear();
								plantesMax.add(aPlanter);
								score = scoreTmp;
								plante = aPlanter;
							} else {
								if(scoreTmp == score){
									plantesMax.add(aPlanter);
								}
							}
						}
						
						//Choix de la plante à partir de plantesMax
						//Choix aléatoire
						planche.setPlante(this.getPlantePreferee(plantesMax));
						cpt++;
					}
					voisins.addAll(planche.voisinsPlanche(this.jardin));
				}
			}
		}
	}
	
	public Plante getPlantePreferee(LinkedList<Plante> plantes){
		float max = 0;
		float somme = 0;
		float tmp = 0;
		Plante p = plantes.get(0);
		for (Plante plante : plantes) {
			for (Plante plante1 : plantes) {
				if(plante != plante1)
					somme += plante1.getQte();
			}
			tmp = plante.getQte()/somme;
			if(tmp > max){
				max = tmp;
				p = plante;
			}
		}
			return p;
	}

}
