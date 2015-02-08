package model;

import java.awt.Color;

public class CaseNonCultivable extends Case {

	public CaseNonCultivable(int x, int y) {
		super(x, y);
		super.couleur = Color.green;
		}

	@Override
	public int score() {
		return 0;
	}

	public String toString(){
		return ("NC "+super.x+" "+super.y+";");
	}
	
}
