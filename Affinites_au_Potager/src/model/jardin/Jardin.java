package model.jardin;

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
	private LinkedList<Plante> plantesFixes;

	public Jardin() {
	}

	public Jardin(int longueur, int largeur){
		this.terrain = new Case[longueur][largeur];
		this.zonesPlantation = new LinkedList<ZonePlantation>();
		this.plantes = new  LinkedList<Plante>();
		this.plantesFixes = new  LinkedList<Plante>();
		for (int i=0; i<this.terrain.length;i++){
			for (int j=0; j<this.terrain[i].length;j++){
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
				/* on est sur la ligne de configuration du jardin*/			
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
				for (String nom : plantesTab){
					this.plantes.add(Plante.getInstanceOf(nom));
				}
			}
			else {
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
				else if (info[2].equalsIgnoreCase("c") && info.length==3){
					laCase = new CaseVariable(abscisse, ordonnee);		
				}

				else {
					numZone = Integer.parseInt(info[3]);
					numPlanche = Integer.parseInt(info[4]);
					Plante plante;
					if (!info[6].equals("null")){
						plante = Plante.getInstanceOf(info[6]);//a voir par rapport au fichier de conf d'affinites
					} else {
						plante = null;
					}
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
		for (int i=0; i<tabPlanche.size(); i++){
			planches.add(new Planche(tabPlanche.get(i)));
		}
		for (int i=0; i<tabZone.size(); i++){
			ZonePlantation z = new ZonePlantation();
			LinkedList<Planche> planchesDeLaZone = new LinkedList<Planche>();
			for (int j=0; j<tabZone.get(i).size(); j++){
				planchesDeLaZone.push(planches.get(tabZone.get(i).get(j)-1));
			}
			z.setPlanches(planchesDeLaZone);
			z.validerEnsemblePlanches(this);
			this.zonesPlantation.add(z);
		}
		fluxIn.close();
		this.repertoriePlantes();
	}

	/**********************/
	/* Getters et Setters */
	/**********************/
	public LinkedList<Plante> getPlantes(){
		return this.plantes;
	}

	public void setPlantes(LinkedList<Plante> plantes) {
		this.plantes = plantes;
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

	/**
	 * @return the plantesFixes
	 */
	public LinkedList<Plante> getPlantesFixes() {
		return this.plantesFixes;
	}

	/**
	 * @return the zones
	 */
	public LinkedList<ZonePlantation> getZones(){
		return this.zonesPlantation;
	}

	/**
	 * Met à jour les zones de plantation
	 * @param zones
	 */
	public void setZones(LinkedList<ZonePlantation> zones){
		this.zonesPlantation = zones;
	}


	/**************************************/
	/* Méthodes de manipulation du jardin */
	/**************************************/

	/**
	 * Renvoie la case du jardin dont les coordonnées sont passées en paramètre
	 * @param i l'abscisse de la case
	 * @param j l'ordonnée de la case
	 * @return la case du jardin dont les coordonnées sont passées en paramètre
	 */
	public Case getCase(int i, int j){
		return this.getTerrain()[i][j];
	}

	/**
	 * Renvoie la zone à laquelle appartient la planche passée en paramètre
	 * @param p la planche de laquelle trouver la zone de plantation
	 * @return zone la zone à laquelle appartient la planche passée en paramètre
	 */
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
		for (Case laCase : this.casesHorsZone()){
			if ((laCase instanceof CaseHorsJardin)){
				buffOut.write(""+laCase.x+" "+laCase.y+" HJ\n");
			}
			else if ((laCase instanceof CaseNonCultivable)){
				buffOut.write(""+laCase.x+" "+laCase.y+" NC\n");
			}
			else if ((laCase instanceof CaseVariable)){
				buffOut.write(""+laCase.x+" "+laCase.y+" C\n");
			}
			else {	
			}
		}
		int numPlanche = 1;
		for (int z=0; z<this.zonesPlantation.size(); z++){
			for (int p=0; p<this.zonesPlantation.get(z).getPlanches().size(); p++){
				Planche planche = this.zonesPlantation.get(z).getPlanches().get(p);
				for (int c=0; c<planche.getNbCases(); c++){
					CaseCultivable laCase = (CaseCultivable)planche.getCases().get(c);
					buffOut.write(""+laCase.x+" "+laCase.y+" C "+(z+1)+" "+(/*p+1*/numPlanche)+" ");
					/*variable ou fixe*/
					if (laCase instanceof CaseFixe) {
						buffOut.write("fixe "+laCase.getPlante().getNom()+"\n");
					}
					else if (laCase instanceof CaseVariable){
						try {
							buffOut.write("variable "+laCase.getPlante().getNom()+"\n");
						} catch (Exception e){
							buffOut.write("variable null\n");
						}
					}
				}
				numPlanche++;
			}
		}
		buffOut.close();
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
					//System.out.println("Case hors terrain");
					/* on ne fait rien dans le cas d'un IndexArrayOutOfBounds 
					 * car cela correspond à une case en dehors du jardin (qui n'existe donc pas)
					 */
				}
			}
		}
		/*il faut retirer la case courante qui n'est pas voisine d'elle-même*/
		liste.remove(caseJardin);
		return liste;
	}


	/**
	 * Retourne l'ensemble des cases cultivables voisines de la case passée en paramètre
	 * @param caseJardin la case dont trouver les cases cultivables voisines
	 * @return l'ensemble des cases cultivables voisines de la case passée en paramètre
	 */
	public LinkedList<CaseCultivable> casesVoisinesCultivables(Case caseJardin){
		LinkedList<CaseCultivable> liste = new LinkedList<CaseCultivable>();
		for (Case laCase : this.casesVoisines(caseJardin)) {
			if (laCase instanceof CaseCultivable) {
				liste.add((CaseCultivable)laCase);
			}
		}
		return liste;
	}

	/**
	 * Reinitialise l'ensemble des cases cultivables du jardin en indiquant qu'elle ne possède pas de plante
	 */
	public void initHasPlant(){
		for(int i=0;i<this.getTerrain().length;i++){
			for (Case laCase : this.getTerrain()[i]) {
				if(laCase instanceof CaseCultivable){
					((CaseCultivable) laCase).setHasPlant(false);
				}
			}
		}
	}

	/**
	 * Retourne le nombre de cases libres pouvant accueillir une plante du jardin
	 * @return l nombre de cases libres pouvant accueillir une plante
	 */
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

	/**
	 * Modifie la case du jardin dont les coordonnées sont passées en paramètre
	 * @param x l'abscisse de la case
	 * @param y l'ordonnée de la case
	 * @param solType le type de la case
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void setCase(int x, int y, String solType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		Case laCase = new CaseBuilder().constructCase(x, y, solType);
		this.setCase(x, y, laCase);
	}

	/**
	 * Remplace la case du jardin dont les coordonnées sont passées en paramètre par la case passée en paramètre
	 * @param x l'abscisse de la case
	 * @param y l'ordonnée de la case
	 * @param laCase la case à mettre en lieu et la place de la case dont les coordonées sont x et y
	 */
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
//			System.out.println(c.x+" - "+c.y);
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


	/**
	 * Ajoute un ensemble de planche au jardin
	 * @param planches l'ensemble des planches à ajouter au jardin
	 * @throws PlancheNonMitoyenneException
	 * @throws PlancheNonValideException
	 */
	public void addMultiPlanche(LinkedList<Planche> planches) throws PlancheNonMitoyenneException, PlancheNonValideException{
		System.out.println("add multipanches");
		for (Planche pl : planches){
			System.out.println(pl.toString());
			this.ajouterPlanche(pl);
		}
	}

	/**
	 * Ajoute un ensemble de planches définies par un rectangle dont les coordonnées sont passées en paramètre
	 * @param x1 l'abscisse de début du rectangle
	 * @param y1 l'abscisse de fin du rectangle
	 * @param x2 l'ordonnée du début du rectangle
	 * @param y2 l'ordonnée de fin du rectangle
	 * @param orientation le sens des planches dans le rectangle : true => horizontal , false => vertical
	 * @throws PlancheNonMitoyenneException
	 * @throws PlancheNonValideException
	 */
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


	/**
	 * Fusionne l'ensemble des zones passées en paramètre en une seule zone
	 * @param zones l'ensemble des zones à fusionner
	 * @return zone la zone fusionnée
	 */
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
			/*suppression de la zone car scindee en deux*/
			this.zonesPlantation.remove(z);
			/*suppression de la planche*/
			listePlanches.remove(planche);
			/*l'appel à ajouter planche pour les planches restantes va recréer les deux zones*/
			for (Planche pl : listePlanches){
				this.ajouterPlanche(pl);
			}
		}
	}

	/**
	 * Affiche l'ensemble des plantes contenues dans le jardin
	 */
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
	 * Change une case de fixe à variable et inversement en fonction de son type de départ
	 * Si la case est fixe au départ, elle devient variable sans plante
	 * @param x l'abscisse de la case
	 * @param y l'ordonnée de la case
	 * @param plante la plante à mettre dans la case si elle devient une case fixe
	 * @throws PlancheNonValideException
	 * @throws PlancheConstructorException
	 */
	public void fixeCase(int x, int y, Plante plante) throws PlancheNonValideException, PlancheConstructorException{
		Case laCase = this.getCase(x, y);
		Case nouvelleCaseFixe = new CaseFixe(x, y, plante);
//		this.setCase(x, y, laCase.passToFixOrVariable(plante));
		System.out.println("planche "+laCase.getPlanche(this)+ "ici");
		System.out.println("nbCase "+laCase.getPlanche(this).getNbCases());

		if (laCase.getPlanche(this) == null) {
			System.out.println("case seule fixe");
			this.setCase(x, y, nouvelleCaseFixe);
			this.ajouterPlanche(new Planche(this.getCase(x, y)));
		}
		/*la case appartient à une planche
		 * =>trois cas possibles */
		else {
			System.out.println("case à fixer appartenant à une planche ");

			Planche planche = laCase.getPlanche(this);
			System.out.println("nbCase "+planche.getNbCases());
			/*la planche ne comporte qu'une seule case*/
			if (planche.getNbCases()==1){
				System.out.println("planche d'une case");
				this.setCase(x, y, nouvelleCaseFixe);

				//planche.getCases().set(0, laCase);
			}else {
				System.out.println("planche de plusieurs cases");
				this.setCase(x, y, nouvelleCaseFixe);

				/*recherche de la position de la case dans la planche*/ 
				int indexCaseDansLaPlanche = planche.getCases().indexOf(laCase);
				/*on sépare la planche en 2 ou trois planche selon la position de la case dans la planche*/
				Planche plancheDeLaCase = new Planche(nouvelleCaseFixe);
				if (indexCaseDansLaPlanche == 0 || indexCaseDansLaPlanche == planche.getNbCases()-1){
					System.out.println("case en premiere ou derniere position");
					/*la case est en première position dans la planche, on a donc 2 planches au final*/
					LinkedList<Case> CaseDeLaNouvellePLanche = new LinkedList<Case>();
					CaseDeLaNouvellePLanche.addAll(planche.getCases());
					CaseDeLaNouvellePLanche.remove(laCase);
					Planche NouvellePlancheSaufLaCase = new Planche(CaseDeLaNouvellePLanche);
					this.supprimerPlanche(planche);
					this.ajouterPlanche(NouvellePlancheSaufLaCase);
				}
				else {
					System.out.println("cases au milieu");
					/*on aura trois planches au final*/
					/*une planche avant la case*/
					LinkedList<Case> CaseDeLaNouvellePLancheAvant = new LinkedList<Case>();
					/*une planche apres la  case*/
					LinkedList<Case> CaseDeLaNouvellePLancheApres = new LinkedList<Case>();
					for (int i=0; i<indexCaseDansLaPlanche; i++){
						CaseDeLaNouvellePLancheAvant.push(planche.getCases().get(i));
					}
					for (int i=indexCaseDansLaPlanche+1; i<planche.getNbCases(); i++){
						CaseDeLaNouvellePLancheApres.push(planche.getCases().get(i));
					}
					Planche NouvellePlancheAvantLaCase;
					Planche NouvellePlancheApresLaCase;
					try { NouvellePlancheAvantLaCase = new Planche(CaseDeLaNouvellePLancheAvant);
					
					 NouvellePlancheApresLaCase = new Planche(CaseDeLaNouvellePLancheApres);
					
					this.supprimerPlanche(planche);
					try {
					this.ajouterPlanche(NouvellePlancheAvantLaCase);
					}catch (Exception e){
e.printStackTrace();}
					try {
						
					this.ajouterPlanche(NouvellePlancheApresLaCase);
					}catch (Exception e){
						e.printStackTrace();
					}
					}catch (Exception e){
						System.out.println("exception gloable");
						e.printStackTrace();
					}

				}
				this.ajouterPlanche(plancheDeLaCase);
			}
		}

	}

	/**
	 * Retourne l'ensemble des cases Hors Zone du jardin
	 * @return l'ensemble des cases Hors Zone du jardin
	 */
	public LinkedList<Case> casesHorsZone(){
		LinkedList<Case> caseHorsZone = new LinkedList<Case>();
		for (int i=0; i<this.terrain.length; i++){
			for (int j=0; j<this.terrain[0].length; j++){
				Case c = this.getCase(i, j);
				if (!this.caseInZone(c)){
					caseHorsZone.add(c);
				}
			}
		}
		return caseHorsZone;
	}

	/**
	 * Classe les plantes du jardin respectivement dans les listes de plantes variables (qui pourront être plantées)
	 * et la liste de plantes fixes(plantés une fois et qui ne changeront pas)
	 */
	public void repertoriePlantes(){
		this.plantes = new LinkedList<Plante>();
		this.plantesFixes = new LinkedList<Plante>();

		for (int i=0; i<this.terrain.length; i++){
			for (int j=0; j<this.terrain[0].length; j++){
				Case c = this.getCase(i, j);
				if (c.getHasPlant()){
					if ( ((CaseCultivable)c).isVariable() && !(this.plantes.contains(c.getPlante())) ){
						this.plantes.add(c.getPlante());
					} else if ( !((CaseCultivable)c).isVariable() && !(this.plantesFixes.contains(c.getPlante())) ){
						this.plantesFixes.add(c.getPlante());
					}
				}
			}
		}
	}

	@Override
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
