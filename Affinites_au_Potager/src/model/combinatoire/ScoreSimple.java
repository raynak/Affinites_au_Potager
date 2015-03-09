package model.combinatoire;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;
import exceptions.PlancheNonMitoyenneException;
import model.Case;
import model.CaseCultivable;
import model.Jardin;
import model.Planche;
import model.ZonePlantation;

public class ScoreSimple implements Scoring {

	@Override
	public int scoreCase(Jardin jardin, CaseCultivable caseTerrain, Affinites affinites) {
		int score = 0;
		ArrayList<CaseCultivable> voisins = jardin.casesVoisinesCultivables(caseTerrain);;
		for (CaseCultivable laCase : voisins) {
			if(laCase.getHasPlant())
		/** a modifier avec les affinites */
				score += affinites.getAffinite(caseTerrain.getPlante(), laCase.getPlante());
		}
		return score;

	}

	@Override
	public int scorePlantation(Jardin jardin, Affinites affinites) {
		int score = 0;
		for (ZonePlantation zone : jardin.getZones()) {
			score += scoreZone(jardin, zone, affinites);
		}
		return 0;
	}

	@Override
	public int scoreZone(Jardin jardin, ZonePlantation zone, Affinites affinites){
		int score = 0;
		for(Planche planche : zone.getPlanches()){
			score += scorePlanche(jardin,planche,affinites);
		}
		return score;
	}
	
	@Override
	public int scorePlanche(Jardin jardin, Planche planche, Affinites affinites) {
		int score = 0;
		for (Case laCase : planche.getCases()) {
			/* Cases de la planche sont des cases cultivables ? A modifier ? */
			//score += scoreCase(jardin,laCase,affinites);
		}
		return score;
	}


}
