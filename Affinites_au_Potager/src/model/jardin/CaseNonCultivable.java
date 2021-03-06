package model.jardin;

import java.awt.Color;

import exceptions.NoAffiniteException;

public class CaseNonCultivable extends Case {

	public CaseNonCultivable(int x, int y) {
		super(x, y);
		super.couleur = Color.green;
		}

	public String toString(){
		return ("NC "+super.x+" "+super.y+";");
	}
	
	public String typeString(){
		return "NC";
	}

	@Override
	public void setPlante(Plante plante) {	
	}

	@Override
	public Plante getPlante() {
		return null;
	}

	@Override
	public Case passToFixOrVariable(Plante plante) {
		return this;
	}

	@Override
	public int getAffinites(Case c) throws NoAffiniteException {
		throw new NoAffiniteException();
	}

	@Override
	public boolean getHasPlant() {
		return false;
	};

}
