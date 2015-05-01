package model.combinatoire;

import model.jardin.CaseCultivable;
import model.jardin.Jardin;
import model.jardin.Planche;
import model.jardin.ZonePlantation;

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
