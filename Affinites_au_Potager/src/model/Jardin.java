package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;
import exceptions.PlancheNonMitoyenneException;
import exceptions.PlancheNonValideException;
import exceptions.ZoneScindeeEnDeuxException;

public class Jardin {

	private Case[][] terrain;
	private LinkedList<ZonePlantation> zonesPlantation;
	private LinkedList<Plante> plantes;

	public Jardin() {
		// TODO Auto-generated constructor stub
	}

	public Jardin(int longueur, int largeur){
		this.terrain = new Case[longueur][largeur];
		this.zonesPlantation = new LinkedList<ZonePlantation>();
		this.plantes = new  LinkedList<Plante>();
		for (int i=0; i<this.terrain.length;i++){
			for (int j=0; j<this.terrain[i].length;j++){
				//System.out.println("ajout hj");
				this.terrain[i][j] = new CaseNonCultivable(i, j);
			}
		}
	}

	/**
	 * Constructeur de la classe Jardin : permet la creation d'une instance de Jardin 
	 * à partir d'un fichier passé en paramètre
	 * @param fileName le fichier à partir duquel créer l'instance de Jardin
	 * @throws FileNotFoundException
	 * @throws GardenWrongDataFormatException
	 * @throws PlancheConstructorException
	 * @throws PlancheNonMitoyenneException 
	 */
	public Jardin(String fileName) throws FileNotFoundException, GardenWrongDataFormatException, PlancheConstructorException, PlancheNonMitoyenneException{
		this.plantes = new LinkedList<Plante>();
		Scanner fluxIn = new Scanner(new File(/*fileName*//*"data/"+*/fileName));
		ArrayList<LinkedList<Integer>> tabZone = null;
		ArrayList<LinkedList<Case>> tabPlanche = null;
		int tailleTabZone;
		int tailletabPlanche;
		boolean configuration= false;	
		while (fluxIn.hasNextLine()){
			System.out.println("traitement des lignes");
			String line = fluxIn.nextLine();
			System.out.println(line);
			if (line.charAt(0)=='#'){
				/* on passe les lignes de commentaires*/
				System.out.println("Commentaires");}
			else if (line.substring(0,  4).equals("Conf")){
				System.out.println("Configuration");
				/* on est sur la ligne ee configuratio du jardin*/			
				if (configuration == true) {
					/*on a déjà lu une ligne de configuration => le fichier est mal construit*/
					fluxIn.close();
					System.out.println("Configuration déjà analysé : fichier erroné !");
					throw new GardenWrongDataFormatException();
				}
				else {/*lecture de la configuration du jardin*/
					configuration = true;
					String[] info = line.split("[\\s]+");
					this.terrain = new Case[Integer.parseInt(info[1])][Integer.parseInt(info[2])];
					tabZone = new ArrayList<LinkedList<Integer>>();
					tabPlanche = new ArrayList<LinkedList<Case>>();
					tailleTabZone = Integer.parseInt(info[3]);
					tailletabPlanche = Integer.parseInt(info[4]);
					for (int i=0; i<tailleTabZone; i++){
						tabZone.add(new LinkedList<Integer>());
					}
					for (int i=0; i<tailletabPlanche; i++){
						tabPlanche.add(new LinkedList<Case>());
					}
				}
			}
			else if  (line.substring(0, 2).equals("Pl")){
				String[] plantesTab = line.substring(8, line.length()).split(",");
				System.out.println(line.substring(8, line.length()));
				for (String nom : plantesTab){
					this.plantes.add(new Plante(nom));
				}
				System.out.println(this.plantes.size());
			}
			else {
				System.out.println("case");

				if (configuration == false) {
					/*aucune ligne de configuration n'a été lu => le fichier est mal construit*/
					fluxIn.close();
					System.out.println("Configuration non définie : fichier erroné !");
					throw new GardenWrongDataFormatException();
				}
				this.zonesPlantation = new LinkedList<ZonePlantation>();
				/* traitement des informations des cases */

				String[] info = line.split("[\\s]+");
				int abscisse = Integer.parseInt(info[0]); 
				int ordonnee = Integer.parseInt(info[1]);
				int numZone;
				int numPlanche;

				Case laCase;
				/*traitement différent selon le type de case*/
				if (info[2].equalsIgnoreCase("hj")){				
					laCase = new CaseHorsJardin(abscisse, ordonnee);		
				}
				else if (info[2].equalsIgnoreCase("nc")){
					laCase = new CaseNonCultivable(abscisse, ordonnee);		
				}
				else {
					numZone = Integer.parseInt(info[3]);
					numPlanche = Integer.parseInt(info[4]);
					Plante plante = new Plante(info[6]);
					char c = info[5].charAt(0);
					if (c == 'v' || c == 'n'){
						laCase = new CaseVariable(abscisse, ordonnee, plante);	
					}
					else
					{
						laCase = new CaseFixe(abscisse, ordonnee, plante);
					}

					/*ajout de la case a la planche*/
					tabPlanche.get(numPlanche-1).add(laCase);
					/*ajout de la planche a la zone correspondante s'il n'est pas encore ajouté */
					if (!tabZone.get(numZone-1).contains(numPlanche)){
						tabZone.get(numZone-1).push(numPlanche);
					}
				}
				this.terrain[abscisse][ordonnee] = laCase;
			}
		}

		/*l'ensemble des cases a été créé */
		/*contruction des planches et des zones de plantations */
		ArrayList<Planche> planches = new ArrayList<Planche>();
		for (LinkedList<Case> list : tabPlanche){
			planches.add(new Planche(list));
		}
		for (int i=0; i<tabZone.size(); i++){
			ZonePlantation z = new ZonePlantation();
			for (int j=0; j<tabZone.get(i).size(); j++){
				z.ajouterPlanche(planches.get(tabZone.get(i).get(j)-1), this);
			}
			this.zonesPlantation.add(z);
		}
		fluxIn.close();

	}


	public LinkedList<Plante> getPlantes(){
		return this.plantes;
	}

	public void setPlantes(LinkedList<Plante> plantes) {
		this.plantes = plantes;
	}

	public ZonePlantation findZoneOfPlanche(Planche p){
		for (ZonePlantation zone : this.zonesPlantation){
			if (zone.containsPlanche(p)){
				return zone;
			}
		}
		return null;
	}

	/**
	 * Return true if a case belong to a plantation zone
	 * @param c the case
	 * @return true if the case belong to a zone, false either
	 */
	public boolean caseInZone(Case c){
		for (ZonePlantation zone : this.zonesPlantation){
			for (Planche p : zone.getPlanches()){
				if (p.getCases().contains(c))
					return true;
			}
		}
		return false;
	}



	public void saveJardin(String fileName) throws IOException{
		FileWriter fileOut = new FileWriter(/*"data/"*/fileName);
		BufferedWriter buffOut = new BufferedWriter(fileOut);
		int nbPlanches = 0;
		/*Calcul du nombres de planches*/
		for (ZonePlantation zone : this.zonesPlantation){
			nbPlanches += zone.getPlanches().size();
		}
		String configJardin = "Conf "+this.terrain.length+" "+this.terrain[0].length+" "+this.zonesPlantation.size()+" "+nbPlanches+"\n";
		/*ecriture de la ligne de configuration*/
		buffOut.write(configJardin);
		/*ecriture des lignes de description des cases*/
		for (int i=0; i<this.terrain.length; i++){
			for (int j=0; j<this.terrain[0].length; j++){
				Case laCase = this.terrain[i][j];
				if ((laCase instanceof CaseHorsJardin)){
					buffOut.write(""+laCase.x+" "+laCase.y+" HJ\n");
				}
				else if ((laCase instanceof CaseNonCultivable)){
					buffOut.write(""+laCase.x+" "+laCase.y+" NC\n");
				}
				else {	
				}
			}
		}
		for (int z=0; z<this.zonesPlantation.size(); z++){
			for (int p=0; p<this.zonesPlantation.get(z).getPlanches().size(); p++){
				Planche planche = this.zonesPlantation.get(z).getPlanches().get(p);
				for (int c=0; c<planche.getNbCases(); c++){

					CaseCultivable laCase = (CaseCultivable)planche.getCases().get(c);
					buffOut.write(""+laCase.x+" "+laCase.y+" C "+(z+1)+" "+(p+1)+" ");
					/*variable ou fixe*/
					if (laCase instanceof CaseFixe) {
						buffOut.write("fixe "+laCase.getPlante().getNom()+"\n");
					}
					else if (laCase instanceof CaseVariable){
						buffOut.write("variable "+laCase.getPlante().getNom()+"\n");
					}
				}
			}
		}
		buffOut.close();
	}


	/**
	 * @return the terrain
	 */
	public Case[][] getTerrain() {
		return terrain;
	}

	/**
	 * @param terrain the terrain to set
	 */
	public void setTerrain(Case[][] terrain) {
		this.terrain = terrain;
	}

	public LinkedList<ZonePlantation> getZones(){
		return this.zonesPlantation;
	}

	public void setZones(LinkedList<ZonePlantation> zones){
		this.zonesPlantation = zones;
	}

	public ArrayList<Case> casesVoisines(Case caseJardin){
		ArrayList<Case> liste = new ArrayList<Case>();
		int abscisse = caseJardin.getX();
		int ordonnee = caseJardin.getY();
		for (int i=abscisse-1; i<=abscisse+1; i++){
			for (int j=ordonnee-1; j<=ordonnee+1; j++){
				try {
					liste.add(this.terrain[i][j]);
				}
				catch (Exception e){
					System.out.println("Case hors terrain");
					/** on ne fait rien dans le cas d'un IndexArrayOutOfBounds 
					 * car cela correspond à une case en dehors du jardin (qui n'existe donc pas)
					 */
				}
			}
		}
		/*il faut retirer la case courante qui n'est pas voisine d'elle-même*/
		liste.remove(caseJardin);
		return liste;
	}


	public LinkedList<CaseCultivable> casesVoisinesCultivables(Case caseJardin){
		LinkedList<CaseCultivable> liste = new LinkedList<CaseCultivable>();
		for (Case laCase : this.casesVoisines(caseJardin)) {
			if (laCase instanceof CaseCultivable) {
				liste.add((CaseCultivable)laCase);
			}
		}
		return liste;
	}

	public CaseCultivable prochaineCaseAvecUnVoisinLibre(){
		return null;
	}

	public int nbCasesLibres(){
		int nbCasesLibres = 0;
		for (int i=0; i<this.getTerrain().length; i++){
			for (int j=0; j<this.getTerrain()[0].length; j++){
				Case c = this.getTerrain()[i][j];
				if (c instanceof CaseCultivable){
					if ( !((CaseCultivable)c).hasPlant ){
						nbCasesLibres++;
					}
				}
			}
		}
		return nbCasesLibres;
	}


	public void setCase(int x, int y, String solType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		Case laCase = new CaseBuilder().constructCase(x, y, solType);
		this.setCase(x, y, laCase);
	}
	
	public void setCase(int x, int y, Case laCase){
		this.terrain[x][y] = laCase; 
	}

	/**
	 * Ajoute une planche au jardin. Si la planche est mitoyennes de plusieurs zones, on les fusionne en une seule
	 * @param p la planche à ajouter
	 * @throws PlancheNonValideException 
	 */
	public void ajouterPlanche(Planche p) throws PlancheNonValideException{
		for (Case c : p.getCases()){
			if (this.caseInZone(c)){
				throw new PlancheNonValideException();
			}
			System.out.println(c.x+" - "+c.y);
			if (!(this.terrain[c.x][c.y] instanceof CaseCultivable)){
				throw new PlancheNonValideException();
			}
		}
		LinkedList<ZonePlantation> listeZonesMitoyennes = new LinkedList<ZonePlantation>();
		for (ZonePlantation z : this.zonesPlantation){
			if (z.peutAccueillirPlanche(p, this)){
				listeZonesMitoyennes.add(z);
			}
		}
		/*on ajoute la zone constitue de la planche à la zone mitoyenne*/
		listeZonesMitoyennes.add(new ZonePlantation(p));
		ZonePlantation newZone = this.fusionnerZones(listeZonesMitoyennes);
		this.zonesPlantation.removeAll(listeZonesMitoyennes);
		this.zonesPlantation.addLast(newZone);
		System.out.println("ajout de la planche");
	}

//	/**
//	 * Add a planche in the correct zone of the garden, 
//	 * @param p the planche to add to the garden
//	 * @throws PlancheNonMitoyenneException
//	 * @throws PlancheNonValideException
//	 */
//	public void addPlanche(Planche p) throws PlancheNonMitoyenneException, PlancheNonValideException{
//		for (Case c : p.getCases()){
//			if (this.caseInZone(c)){
//				throw new PlancheNonValideException();
//			}
//			System.out.println(c.x+" - "+c.y);
//			if (!(this.terrain[c.x][c.y] instanceof CaseCultivable)){
//				throw new PlancheNonValideException();
//			}
//		}
////		ZonePlantation z = this.findZoneOfPlanche(p);
////		if (z == null){
////			z = new ZonePlantation();
////			z.ajouterPlanche(p, this);
////			this.zonesPlantation.add(z);
////		}
////		else {
////			z.ajouterPlanche(p,this);
////		}
//		this.ajouterPlanche(p);
//		System.out.println("taille de la planche"+p.getCases().size());
//		for (Case c : p.getCases()){
//			this.terrain[c.x][c.y] = c;
//		}
//	}

	public void addMultiPlanche(LinkedList<Planche> planches) throws PlancheNonMitoyenneException, PlancheNonValideException{
		System.out.println("add multipanches");
		for (Planche pl : planches){
			System.out.println(pl.toString());
			this.ajouterPlanche(pl);
		}

	}

	public void addMultiPlanche(int x1, int y1, int x2, int y2, boolean orientation) throws PlancheNonMitoyenneException, PlancheNonValideException{
		LinkedList<Planche> planches = new LinkedList<Planche>();
		if (orientation){
			int nbCases = Math.abs(x2-x1)+1;
			for (int i=Math.min(y1,  y2); i<=Math.max(y1, y2); i++){
				planches.add(new Planche(Math.min(x1, x2), i, nbCases, orientation, this));
			}
		}
		else {
			int nbCases = Math.abs(y2-y1)+1;
			for (int i=Math.min(x1,x2); i<=Math.max(x1, x2); i++){
				planches.add(new Planche(i, Math.min(y1, y2), nbCases, orientation, this));
			}
		}
		System.out.println(planches.size()+" planches àajouter");
		this.addMultiPlanche(planches);
	}

	public ZonePlantation fusionnerZones(LinkedList<ZonePlantation> zones){
		ZonePlantation newZone = new ZonePlantation();
		for (ZonePlantation z : zones){
			newZone.getPlanches().addAll(z.getPlanches());
		}
		return newZone;
	}

	/**
	 * Supprime une planche du jardin
	 * @param planche la planche à supprimer
	 * @throws PlancheNonValideException 
	 */
	public void supprimerPlanche(Planche planche) throws PlancheNonValideException{
		ZonePlantation z = this.findZoneOfPlanche(planche);

		try {
			/*si la planche ne scinde pas la zone en deux, on la supprime de la zone*/
			z.supprimerPlanche(planche, this);
		}
		catch(ZoneScindeeEnDeuxException e){
			/*la zone est scindée en deux*/
			LinkedList<Planche> listePlanches = z.getPlanches();
			/*suppression de la planche*/
			listePlanches.remove(planche);
			/*l'appel à ajouter planche pour les planches restantes va recréer les deux zones*/
			for (Planche pl : listePlanches){
				this.ajouterPlanche(pl);
			}
		}
	}

	public String toString(){
		String s = "";
		for (int i=0; i<this.terrain.length; i++){
			for (int j=0; j<this.terrain[0].length; j++){
				try {s += this.terrain[i][j].toString();}
				catch (Exception e){
					s+="Case vide";
				}
			}
			s += "\n";
		}
		return s;
	}

	public void affichePlante(){
		for (int i=0; i<this.terrain.length; i++){
			for (int j=0; j<this.terrain[0].length; j++){

				Case c = this.getTerrain()[i][j];
				if (c instanceof CaseCultivable ){

					if (c.getHasPlant()){
						System.out.println(((CaseCultivable) c).toString());}
					else {
						System.out.println("pas de plante");
					}
				}
				else {System.out.println("case non cultivable");
				}

			}
		}
	}

	public Case getCase(int i, int j){
		return this.getTerrain()[i][j];
	}

	/**
	 * Remove all the plant of the case that are not CaseFixe
	 */
	public void resetJardin(){
		for (int i=0; i<this.terrain.length; i++){
			for (int j=0; j<this.terrain[0].length; j++){
				Case c = this.getCase(i, j);
				if (c.getHasPlant() && !(c instanceof CaseFixe)){
					c.setPlante(null);
					c.hasPlant = false;
				}
			}
		}
	}


	/**
	 * Paint the graphic representation of the pplant affinity between two cases
	 * @param g the Graphics
	 * @param taille the size of a case representation
	 */
	public void paintRelationBetweenPlante(Graphics g, int taille){
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));

		for (int i=0; i<this.terrain.length-1; i++){
			for (int j=0; j<this.terrain[0].length; j++){

				if (this.terrain[i][j] instanceof CaseCultivable && ((CaseCultivable)this.terrain[i][j]).getHasPlant()){
					CaseCultivable laCase = ((CaseCultivable)this.terrain[i][j]);
					ArrayList<Case> cases = /*new LinkedList<CaseCultivable>()*/this.casesVoisines(laCase);
					for (Case laVoisine : cases){
						if ( (laVoisine instanceof CaseCultivable) && ((CaseCultivable)laVoisine).getHasPlant() ){

							switch(laCase.getPlante().getAffinite(((CaseCultivable)laVoisine).getPlante()) ){
							case -1: {g.setColor(Color.red); break;}
							case 0: {g.setColor(Color.yellow); break;}
							case 1: {g.setColor(Color.green); break;}
							default:{System.out.println("erreur"+laCase.getPlante().getAffinite(((CaseCultivable)laVoisine).getPlante()));System.exit(0);}
							}
							int x1 = (int)(taille*(i+0.5));
							int y1 = (int)(taille*(j+0.5));
							int x2 = (int)(taille*(laVoisine.getX()+0.5));
							int y2 = (int)(taille*(laVoisine.getY()+0.5));
							if (x1<x2){
								x1 += taille*0.25;
								x2 -= taille*0.25;
							}
							else if (x1>x2){
								x1 -= taille*0.25;
								x2 += taille*0.25;
							}
							if (y1<y2){
								y1 += taille*0.25;
								y2 -= taille*0.25;
							}
							if (y1>y2){
								y1 -= taille*0.25;
								y2 += taille*0.25;
							}
							g.drawLine(x1, y1, x2, y2);
						}
					}
				}
			}
		}
	}

	
	public void fixeCase(int x, int y, Plante plante) throws PlancheNonValideException, PlancheConstructorException{
		Case laCase = this.getCase(x, y);
		if (laCase.getPlanche(this) != null){
			Planche planche = laCase.getPlanche(this);
			/*recherche de la position de la case dans la planche*/ 
			int indexCaseDansLaPlanche = planche.getCases().indexOf(laCase);
			/*on sépare la planche en 2 ou trois planche selon la position de la case dans la planche*/
			Planche plancheDeLaCase = new Planche(laCase);
			if (indexCaseDansLaPlanche == 0 || indexCaseDansLaPlanche == planche.getNbCases()){
				/*la case est en première position dans la planche, on a donc 2 planches au final*/
				LinkedList<Case> CaseDeLaNouvellePLanche = new LinkedList<Case>();
				CaseDeLaNouvellePLanche.addAll(planche.getCases());
				CaseDeLaNouvellePLanche.remove(laCase);
				Planche NouvellePlancheSaufLaCase = new Planche(CaseDeLaNouvellePLanche);
				this.supprimerPlanche(planche);
				this.ajouterPlanche(NouvellePlancheSaufLaCase);
			}
			else {
				/*on aura trois planches au final*/
				/*une planche avant la case*/
				LinkedList<Case> CaseDeLaNouvellePLancheAvant = new LinkedList<Case>();
				/*une planche apres la  case*/
				LinkedList<Case> CaseDeLaNouvellePLancheApres = new LinkedList<Case>();
				for (int i=0; i<indexCaseDansLaPlanche; i++){
					CaseDeLaNouvellePLancheAvant.add(planche.getCases().get(i));
				}
				for (int i=indexCaseDansLaPlanche+1; i<planche.getNbCases(); i++){
					CaseDeLaNouvellePLancheApres.add(planche.getCases().get(i));
				}
				Planche NouvellePlancheAvantLaCase = new Planche(CaseDeLaNouvellePLancheAvant);
				Planche NouvellePlancheApresLaCase = new Planche(CaseDeLaNouvellePLancheApres);
				this.supprimerPlanche(planche);
				this.ajouterPlanche(NouvellePlancheAvantLaCase);
				this.ajouterPlanche(NouvellePlancheApresLaCase);
		
			}
			this.ajouterPlanche(plancheDeLaCase);
		}
		else {
			this.setCase(x, y, laCase.passToFixOrVariable(plante));
			this.ajouterPlanche(new Planche(this.getCase(x, y)));
		}
	}

	public static void main(String[] args) throws GardenWrongDataFormatException, PlancheConstructorException, IOException, PlancheNonMitoyenneException{
		/*Jardin j = new Jardin("jardin2.txt");
		System.out.println("Jardin : \n"+j.toString());
		System.out.println("nzone"+j.zonesPlantation.size());
		System.out.println(("npl"+j.zonesPlantation.get(0).getPlanches().size()));
		for (ZonePlantation zone : j.getZones()){
			for (Planche planche: zone.getPlanches()){
				System.out.println(planche.toString());
			}
		}
		j.saveJardin("jardin3.txt");*/
		Jardin j = new Jardin(6,8);
		System.out.println("ok"+j.terrain[1][1]);
		ArrayList<Case> l = j.casesVoisines(j.terrain[1][1]);
		System.out.println(l.size());
		for (Case c : l){
			System.out.println(c);
		}
	}




}
