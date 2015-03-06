package model;

import java.awt.Color;

public class CaseFixe extends CaseCultivable {

	public CaseFixe(int x, int y, Plante plante) {
		super(x, y);
		this.setPlante(plante);
		this.hasPlant = true;
	}
	
	public String toString(){
		return ("F "+super.x+" "+super.y+";");
	}
	
	public void setPlante(Plante plante){
		super.setPlante(plante);
	}

	@Override
	public String typeString() {
		return "FIXE";
	}

}
