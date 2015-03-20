package model.combinatoire;

import java.util.LinkedList;

import model.CaseCultivable;
import model.Jardin;
import model.Planche;
import model.Plante;
import model.PreferencesUtilisateur;
import model.ZonePlantation;

public class ModeleCombiGlouton extends ModeleCombi {

	public ModeleCombiGlouton(Jardin jardin) {
		super(jardin);
		// TODO Auto-generated constructor stub
	}

	public void algoOptimisation() {
		System.out.println("Executioh de l'algo glouton");
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
			System.out.println("taiille planche fixee "+planchesFixes.size());
			/* Si aucune planche ne contient une plante fixée, on en prend une au hasard */
			if (planchesFixes.isEmpty()) {
				System.out.println("Aucune planches avec une plante fixee");
				int aleaPlanche = (int) (Math.random() * (zonePlantation.getPlanches().size() - 1));
				/* On récupère la planche */
				planche = zonePlantation.getPlanches().get(aleaPlanche);
				/* Et on met la même plante sur toute la planche */
				System.out.println("on plante la planche avec "+this.jardin.getPlantes().get(0));
				planche.setPlante(this.jardin.getPlantes().get(0));
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
						for (Plante aPlanter : this.plantes) {
							planche.setPlante(aPlanter);
							scoreTmp = planche.scorePlanche(this.jardin);
							if (scoreTmp > score) {
								score = scoreTmp;
								plante = aPlanter;
							}
						}
						System.out.println("Plantage de la plante :"+plante.toString());
						planche.setPlante(plante);
						cpt++;
					}
					voisins.addAll(planche.voisinsPlanche(this.jardin));
				}
			}
		}
	}

//	@Override
//	public int score() {
//		// TODO Auto-generated method stub
//		return 42;
//	}

}
