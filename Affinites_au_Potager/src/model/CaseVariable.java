package model;

public class CaseVariable extends CaseCultivable {

	public CaseVariable(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public String toString(){
		return ("V "+super.x+" "+super.y+";");
	}	

}
