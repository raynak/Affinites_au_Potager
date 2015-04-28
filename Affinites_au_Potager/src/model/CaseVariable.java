package model;

public class CaseVariable extends CaseCultivable {

	public CaseVariable(int x, int y, Plante plante) {
		super(x, y);
		super.setPlante(plante);
		this.hasPlant = true;
	}
	
	public CaseVariable(int x, int y){
		super(x,y);
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

	@Override
	public CaseCultivable passToFixOrVariable(Plante plante) {
		return new CaseFixe(this.x, this.y, plante);
	}

	@Override
	public boolean isVariable() {
		return true;
	}

	@Override
	public boolean getHasPlant() {
		if (this.plante==null){
			return false;
		}
		else {
			return true;
		}
	}
}
