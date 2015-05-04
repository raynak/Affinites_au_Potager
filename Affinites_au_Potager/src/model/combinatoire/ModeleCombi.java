package model.combinatoire;

import java.util.HashMap;
import java.util.LinkedList;

import model.jardin.CaseCultivable;
import model.jardin.Jardin;
import model.jardin.Plante;
import model.jardin.ZonePlantation;

public abstract class ModeleCombi {
	public Jardin jardin;
	public LinkedList<ZonePlantation> zones;
	// public LinkedList<Planche> planches; ??
	public LinkedList<Plante> plantes;

	/**
	 * Constructeur d'un modèle combi
	 * @param jardin
	 */
	public ModeleCombi(Jardin jardin) {
		this.jardin = jardin;
		this.zones = jardin.getZones();
		this.plantes = jardin.getPlantes();
	}

	/**
	 * Retourne le jardin du modèle combi courant
	 * @return le jardin
	 */
	public Jardin getJardin() {
		return jardin;
	}

	/**
	 * Set le jardin dans le modèle combi courant
	 * @param jardin un jardin
	 */
	public void setJardin(Jardin jardin) {
		this.jardin = jardin;
		this.zones = jardin.getZones();
		this.plantes = jardin.getPlantes();
	}

	/**
	 * Algorithme d'optimisation du jardin, plante toutes les planches pour obtenir un score maximal
	 */
	public abstract void algoOptimisation();

	/**
	 * Retourne le score du jardin
	 * @return le score du jardin
	 */
	public int score() {
		//System.out.println("Calcul du score du jardin :");
		int score = 0;
		for (int i = 0; i < this.jardin.getTerrain().length; i++) {
			for (int j = 0; j < this.jardin.getTerrain()[0].length; j++) {
				//System.out.println("Ajout du score de la case " + i + "-" + j);
				if (this.jardin.getTerrain()[i][j] instanceof CaseCultivable) {
					//System.out.println("cultivable "
						//	+ this.jardin.getTerrain()[i][j].getHasPlant());
				} else {
					//System.out.println(" non cultivable");
				}
				score += this.jardin.getTerrain()[i][j].score(jardin);
			}
		}
		return score;
	};

	// Ajout des contraintes sur les plantes
	
	/**
	 * Retourne les contraintes d'une plante p avec l'ensemble des autres plantes du jardin.
	 * Pour cela, il faut sommer les affinités de la plante p avec toutes les autres
	 * @param p la plante
	 * @return la somme des affinités de la plante, ce que nous appelons la contrainte
	 */
	public int getTotalAffinite(Plante p) {
		int affTotal = 0;
		for (Plante plante : this.jardin.getPlantes()) {
			affTotal += p.getAffinite(plante);
		}
		return affTotal;
	}

	/**
	 * Retourne la hashMap contenant toutes les contraintes de toutes les plantes
	 * @return une hashMap avec pour clé le nom de la plante et pour valeur la contrainte de cette plante
	 */
	public HashMap<String, Integer> contraintesPlantes() {
		HashMap<String, Integer> contraintesPlantes = new HashMap<String, Integer>();
		for (Plante plante : this.jardin.getPlantes()) {
			contraintesPlantes.put(plante.getNom(), getTotalAffinite(plante));
		}
		return contraintesPlantes;
	}

	/**
	 * Retourne la plante dont la contrainte avec les plantes passées en paramètres est minimale (et donc l'affinité avec les autres plantes maximale)
	 * @param plantes la liste des plantes dont on souhaite celle de contrainte minimale
	 * @return la plante de contrainte minimale
	 */
	public Plante getMinContraintes(LinkedList<Plante> plantes) {
		int maxPlante = getTotalAffinite(plantes.get(0));
		Plante p = plantes.get(0);
		for (Plante plante : plantes) {
			int aff = getTotalAffinite(plante);
			if (aff > maxPlante) {
				maxPlante = aff;
				p = plante;
			}
		}
		System.out.println("a planter : "+p.getNom());
		return p;
	}

	/**
	 * Retourne la plante de contrainte minimale parmi toutes les plantes du jardin
	 * @return la plante de contrainte minimale parmi toutes les plantes du jardin
	 */
	public Plante getMax() {
		return getMinContraintes(this.jardin.getPlantes());
	}
	

}
