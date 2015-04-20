package model;

/**
 * Permet à l'utilisateur d'ajouter les plantes qu'il veut planter par ordre de préférence
 *
 */
public class PreferencesUtilisateur {
	private Plante[] preferences;
	private Plantation plantation;
	public PreferencesUtilisateur(Plantation plantation){
		this.plantation = plantation;
		this.preferences = new Plante[plantation.getPlantes().size()];
	}
	
	public void addPreferences(Plante[] preferences){
		this.preferences = preferences;
	}
	
	public void addPreference(Plante p, int classement){
		this.preferences[classement] = p;
	}
	
	public Plante getPlantePreferee(){
		return this.preferences[0];
	}
	
	public Plante[] getPreferences(){
		return this.preferences;
	}
}
