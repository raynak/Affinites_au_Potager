package model.combinatoire;

import model.CaseCultivable;
import model.Jardin;
import model.Planche;
import model.ZonePlantation;

/**
 * 
 * @author Tanguy, Delphine
 *
 */
public interface Scoring {

	public int scoreCase(Jardin jardin, CaseCultivable caseTerrain, Affinites affinites);
	
	public int scorePlanche(Jardin jardin, Planche planche, Affinites affinites);

	public int scorePlantation(Jardin jardin, Affinites affinites);
	
	public int scoreZone(Jardin jardin, ZonePlantation zone, Affinites affinites);
	
}
