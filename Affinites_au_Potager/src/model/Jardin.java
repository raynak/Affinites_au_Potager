package model;

import java.util.ArrayList;

public class Jardin {

	private Case[][] terrain;
	private Plantation plantation;
	
	public Jardin() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the terrain
	 */
	public Case[][] getTerrain() {
		return terrain;
	}

	/**
	 * @param terrain the terrain to set
	 */
	public void setTerrain(Case[][] terrain) {
		this.terrain = terrain;
	}

	/**
	 * @return the plantation
	 */
	public Plantation getPlantation() {
		return plantation;
	}

	/**
	 * @param plantation the plantation to set
	 */
	public void setPlantation(Plantation plantation) {
		this.plantation = plantation;
	}

	public ArrayList<Case> casesVoisines(Case caseJardin){
		return new ArrayList<Case>();
	}

	public CaseCultivable prochaineCaseAvecUnVoisinLibre(){
		return null;
	}
	
	public int nbCasesLibres(){
		return 0;
	}
	
}
