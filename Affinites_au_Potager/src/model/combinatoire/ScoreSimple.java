package model.combinatoire;

import java.util.ArrayList;
import model.CaseCultivable;
import model.Jardin;
import model.Planche;

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
	public int scorePlantation(Jardin jardin) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int scorePlanche(Jardin jardin, Planche planche) {
		// TODO Auto-generated method stub
		return 0;
	}

}
