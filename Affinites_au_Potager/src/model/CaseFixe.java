package model;

public class CaseFixe extends CaseCultivable {

	public CaseFixe(int x, int y, Plante plante) {
		super(x, y);
		this.setPlante(plante);
		// TODO Auto-generated constructor stub
	}
	
	public String toString(){
		return ("F "+super.x+" "+super.y+";");
	}
	

}
