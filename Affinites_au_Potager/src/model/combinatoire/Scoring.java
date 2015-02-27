package model.combinatoire;

import model.CaseCultivable;
import model.Jardin;
import model.Planche;

/**
 * 
 * @author Tanguy, Delphine
 *
 */
public interface Scoring {

	public int scoreCase(Jardin jardin, CaseCultivable caseTerrain, Affinites affinites);
	
	public int scorePlanche(Jardin jardin, Planche planche);
	
	public int scorePlantation(Jardin jardin);
	
}
