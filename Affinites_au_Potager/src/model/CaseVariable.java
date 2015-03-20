package model;

public class CaseVariable extends CaseCultivable {

	public CaseVariable(int x, int y, Plante plante) {
		super(x, y);
		super.setPlante(plante);
		this.hasPlant = false;
	}
	
	public CaseVariable(int x, int y){
		super(x,y);
		//super.setPlante(null);
	}
	
	public String toString(){
		return ("V "+super.x+" "+super.y+";");
	}	

	public void setPlante(Plante plante){
		super.setPlante(plante);
	}

	@Override
	public String typeString() {
		return "VARIABLE";
	}
}
