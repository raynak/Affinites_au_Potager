package model;

import java.util.LinkedList;

public class ModeleCombinatoire {

	private Jardin jardin;
	private LinkedList<ZonePlantation> zones;
	
	public ModeleCombinatoire(Jardin jardin){
		this.jardin = jardin;
	}
	
	public void ajouterZone(ZonePlantation zone){
		this.zones.add(zone);
	}
	
	public void retirerZone(ZonePlantation zone){
		this.zones.remove(zone);
	}
	
	public LinkedList<CaseCultivable> voisinsCase(){
		return null;
	}

	// A GARDER ??
	public LinkedList<Plante> voisinsPlanche(){
		return null;
	}
	
	public void algoGlouton(){
		/* Initialisation */
		for (ZonePlantation zonePlantation : zones) {
		int maxX = this.jardin.getTerrain().length;
		int maxY = this.jardin.getTerrain()[0].length;
		int aleaX = (int)(Math.random() * (maxX + 1));
		int aleaY = (int)(Math.random() * (maxY + 1));
		this.jardin.getTerrain()[aleaX][aleaY].setPlante(zonePlantation.getCurrentPlantation().getPlantes().get(0));
		//faire avec les planches
		
		int cpt = 1;
		/* algo*/
			LinkedList<CaseCultivable> voisins = this.jardin.voisinsCase(aleaX, aleaY);
			/* Tant que toutes les cases ne sont pas remplies */
			while(cpt != this.jardin.getTerrain().length*this.jardin.getTerrain()[0].length){
				/* Pour chaque voisin, on calcule le score max */
			for (CaseCultivable caseCultivable : voisins) {
				if(caseCultivable.getPlante() == null){
					/* try catch */
					int score = -8;
					int scoreTmp;
					Plante plante = this.aPlanter[0];
					for (Plante aPlanter : this.aPlanter) {
						scoreTmp = caseTerrain.getJardin().scoreCase(aPlanter,caseTerrain.getX(), caseTerrain.getY());
						if (scoreTmp < score){
							score = scoreTmp;
							plante = aPlanter;
						}
					}
					caseTerrain.getCulture().setPlante(plante);
					cpt++;
				}
				voisins.addAll(caseTerrain.getJardin().voisinsCase(caseTerrain.getX(), caseTerrain.getY()));
			}
			}
		}
	}
}
