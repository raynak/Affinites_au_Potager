package model;

public class Planche {

	private int x;
	private int y;
	private int nbCases;
	private boolean orientation;
	
	public Planche(int x, int y, int nbCases, boolean orientation){
		this.x = x;
		this.y = y;
		this.nbCases = nbCases;
		this.orientation = orientation;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the nbCases
	 */
	public int getNbCases() {
		return nbCases;
	}

	/**
	 * @return the orientation
	 */
	public boolean isOrientation() {
		return orientation;
	}

	public boolean appartientALaPlanche(Case caseTerrain){
		return false;
	}
	
}
