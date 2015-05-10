package model.combinatoire;

import java.util.LinkedList;

import model.jardin.Case;
import model.jardin.CaseCultivable;
import model.jardin.Jardin;
import model.jardin.Planche;
import model.jardin.ZonePlantation;

public class ScoreSimple implements Scoring {
	
	private Jardin jardin;
	
	public ScoreSimple(Jardin jardin){
		this.jardin = jardin;
	}

	/**
	 * Retourne le score de la case dans le jardin jardin
	 * @param le jardin dans lequel nous calculons le score
	 * @return le score de la case
	 */
	@Override
	public int scoreCase(Case c) {
		int score = 0;
		if (!c.getHasPlant()){
			return score;
		}
		
		LinkedList<CaseCultivable> voisins = c.voisinsCase(jardin);
		//System.out.println("nb voisins "+voisins.size());
		for (CaseCultivable caseCultivable : voisins) {
			//System.out.println(caseCultivable.getHasPlant());
			//System.out.println(caseCultivable.getX()+" "+caseCultivable.getY());

			if(caseCultivable.getHasPlant()){
				//System.out.println("nom de la plante "+this.getPlante().toString());
				score += caseCultivable.getPlante().getAffinite(c.getPlante());
				//System.out.println("etat du score " +score);
			}
		}
		//System.out.println("Score case cultivable : "+score);

		return score;
	}

	@Override
	public int scorePlantation() {
		int score = 0;
		for (ZonePlantation zone : jardin.getZones()) {
			score += scoreZone(zone);
		}
		return score;
	}

	@Override
	public int scoreZone(ZonePlantation zone){
		int score = 0;
		for(Planche planche : zone.getPlanches()){
			score += scorePlanche(planche);
		}
		return score;
	}
	
	/**
	 * Retourne le score de la planche courante dans le jardin j
	 * @param j le jardin
	 * @return le score de la planche dans le jardin j
	 */
	public int scorePlanche(Planche p) {
		int score = 0;
		LinkedList<Case> cases = p.getCases();
		for (Case case1 : cases) {
			score += scoreCase(case1);
		}
		return score;
	}


}
