package model;

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

public class Jardin {

	private Case[][] terrain;
	private LinkedList<ZonePlantation> zonesPlantation;

	public Jardin() {
		// TODO Auto-generated constructor stub
	}
	
	public Jardin(int longueur, int largeur){
		this.terrain = new Case[longueur][largeur];
		this.zonesPlantation = new LinkedList<ZonePlantation>();
		}

	/**
	 * Constructeur de la classe Jardin : permet la creation d'une instance de Jardin 
	 * à partir d'un fichier passé en paramètre
	 * @param fileName le fichier à partir duquel créer l'instance de Jardin
	 * @throws FileNotFoundException
	 * @throws GardenWrongDataFormatException
	 * @throws PlancheConstructorException
	 */
	public Jardin(String fileName) throws FileNotFoundException, GardenWrongDataFormatException, PlancheConstructorException{
		CorrespondanceTypeTerrain correspondance = CorrespondanceTypeTerrain.getInstance();
		Scanner fluxIn = new Scanner(new File(/*fileName*/"data/"+fileName));
		ArrayList<LinkedList<Integer>> tabZone = null;
		ArrayList<LinkedList<Case>> tabPlanche = null;
		int tailleTabZone;
		int tailletabPlanche;
		boolean configuration= false;	
		while (fluxIn.hasNextLine()){
			System.out.println("traitement des lignes");
			String line = fluxIn.nextLine();
			if (line.charAt(0)=='#'){
				/* on passe les lignes de commentaires*/
				System.out.println("Commentaires");}
			else if (line.substring(0,  4).equals("Conf")){
				System.out.println("Cofiguration");
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
				z.ajouterPlanche(planches.get(tabZone.get(i).get(j)-1));
			}
			this.zonesPlantation.add(z);
			}
		fluxIn.close();

	}
	
	public void setJardinNonCultivable(){
		for (int i=0; i<this.terrain.length; i++){
			for (int j=0; j<this.terrain[i].length; j++){
				this.terrain[i][j]=new CaseNonCultivable(i, j);
			}
		}
	}

	public void saveJardin(String fileName) throws IOException{
		FileWriter fileOut = new FileWriter("data/"+fileName);
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
					buffOut.write(""+laCase.x+" "+laCase.y+" C "+z+" "+p+" ");
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

	public ArrayList<Case> casesVoisines(Case caseJardin){
		return new ArrayList<Case>();
	}

	public CaseCultivable prochaineCaseAvecUnVoisinLibre(){
		return null;
	}

	public int nbCasesLibres(){
		return 0;
	}

	public LinkedList<CaseCultivable> voisinsCase(int aleaX, int aleaY) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setCase(int x, int y, String solType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		Case laCase = new CaseBuilder().constructCase(x, y, solType);
		this.terrain[x][y] = laCase; 
	}
	
	public String toString(){
		String s = "";
		for (int i=0; i<this.terrain.length; i++){
			for (int j=0; j<this.terrain[0].length; j++){
				s += this.terrain[i][j].toString();
			}
			s += "\n";
		}
		return s;
	}

	public static void main(String[] args) throws GardenWrongDataFormatException, PlancheConstructorException, IOException{
		Jardin j = new Jardin("jardin.txt");
		System.out.println("Jardin : \n"+j.toString());
		System.out.println("nzone"+j.zonesPlantation.size());
		System.out.println(("npl"+j.zonesPlantation.get(0).getPlanches().size()));
		
		j.saveJardin("jardin2.txt");
	}

	
}
