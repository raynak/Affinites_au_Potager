package model;

import java.util.LinkedList;

public class ModeleCombinatoire extends ModeleCombi{
	
	
	public ModeleCombinatoire(Jardin jardin) {
		super(jardin);
		// TODO Auto-generated constructor stub
	}

	public void algoGlouton(){
		/* Initialisation */
		for (ZonePlantation zonePlantation : zones) {
			/* voir avec getCases de zone */
		int maxX = this.jardin.getTerrain().length;
		int maxY = this.jardin.getTerrain()[0].length;
		int aleaX = (int)(Math.random() * (maxX + 1));
		int aleaY = (int)(Math.random() * (maxY + 1));
		/* On récupère la planche */
		Planche planche = zonePlantation.trouverPlanche(this.jardin.getTerrain()[aleaX][aleaY]);
		/* Et on met la même plante sur toute la planche */
		planche.setPlante(this.jardin.getPlantes().get(0));
		//faire avec les planches
		int cpt = 1;
		/* algo*/
			/* Récupération des voisins de la planche */
			LinkedList<CaseCultivable> voisins = planche.voisinsPlanche();
			/* Tant que toutes les cases ne sont pas remplies */
			while(cpt != zonePlantation.getPlanches().size()){
				/* Pour chaque voisin, on calcule le score max */
			for (CaseCultivable caseCultivable : voisins) {
				if(caseCultivable.getPlante() == null){
					/* try catch */
					planche = zonePlantation.trouverPlanche(caseCultivable);
					int score = -100;
					int scoreTmp;
					Plante plante = this.plantes.get(0);
					for (Plante aPlanter : this.plantes) {
						scoreTmp = planche.scorePlanche(aPlanter);
						if (scoreTmp < score){
							score = scoreTmp;
							plante = aPlanter;
						}
					}
					planche.setPlante(plante);
					cpt++;
				}
				voisins.addAll(planche.voisinsPlanche());
			}
			}
		}
	}
}
