package model.combinatoire;

import model.jardin.Case;
import model.jardin.Planche;
import model.jardin.ZonePlantation;

/**
 * 
 * @author Tanguy, Delphine
 *
 */
public interface Scoring {

	public int scoreCase(Case caseTerrain);
	
	public int scorePlanche(Planche planche);

	public int scorePlantation();
	
	public int scoreZone(ZonePlantation zone);
	
}
