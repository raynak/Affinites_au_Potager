package model;

public class quantiteVoulues {
	private Plante plante;
	private float qte;

	public quantiteVoulues(Plante plante, float quantite){
		this.plante = plante;
		this.qte = quantite;
	}
	public Plante getPlante() {
		return plante;
	}

	public void setPlante(Plante plante) {
		this.plante = plante;
	}

	public float getQte() {
		return qte;
	}

	public void setQte(float qte) {
		this.qte = qte;
	}	
	
}
