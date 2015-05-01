package model.jardin;

import java.awt.Color;

public class CaseHorsJardin extends CaseNonCultivable {

	public CaseHorsJardin(int x, int y) {
		super(x, y);
		super.couleur = Color.white;
	}

	public String toString(){
		return ("HJ "+super.x+" "+super.y+";");
	}
	
	public String typeString(){
		return "HJ";
	};

}
