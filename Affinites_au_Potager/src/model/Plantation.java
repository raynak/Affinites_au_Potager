package model;

import java.util.LinkedList;

public class Plantation {

	private LinkedList<Plante> plantes;
	private Jardin jardin;
	
	public Plantation(){
		 this.plantes = new LinkedList<Plante>();
	}
	
	public void addPlante(Plante plante){
		this.plantes.add(plante);
	}
	
	public void setPlantes(LinkedList<Plante> plantes){
		this.plantes = plantes;
	}
	
	public LinkedList<Plante> getPlantes(){
		return this.plantes;
	}
	
}
