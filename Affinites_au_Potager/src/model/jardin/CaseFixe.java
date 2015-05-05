package model.jardin;


public class CaseFixe extends CaseCultivable {

	public CaseFixe(int x, int y, Plante plante) {
		super(x, y);
		this.setPlante(plante);
		this.hasPlant = true;
	}
	
	
	public String toString(){
		return ("F "+super.x+" "+super.y+";");
	}
	
	/** Fixe la plante dans le case
	 * @param plante la plante à fixer
	 * 
	 */
	public void setPlante(Plante plante){
		super.setPlante(plante);
	}

	@Override
	public String typeString() {
		return "FIXE";
	}

	@Override
	public CaseCultivable passToFixOrVariable(Plante plante) {
		return new CaseVariable(this.x, this.y, plante);
	}

	@Override
	public boolean isVariable() {
		return false;
	}


	@Override
	public boolean getHasPlant() {
		return true;
	}

}
