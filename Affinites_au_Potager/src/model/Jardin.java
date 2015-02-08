package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;

public class Jardin {

	private Case[][] terrain;
	
	public Jardin() {
		// TODO Auto-generated constructor stub
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
		Scanner fluxIn = new Scanner(new File(/*fileName*/"./data/jardin.txt"));
		ArrayList<LinkedList<Integer>> tabZone;
		ArrayList<LinkedList<Case>> tabPlanche;
		int tailleTabZone;
		int tailletabPlanche;

		while (fluxIn.hasNextLine() && fluxIn.nextLine().charAt(0)=='#'){
			/* on passe les lignes de commentaires*/
		}
		if (fluxIn.hasNextLine()){
			String line = fluxIn.nextLine();
			/*la première ligne contient les informations géométrique*/
			String[] info = line.split(" ");
			this.terrain = new Case[Integer.parseInt(info[0])][Integer.parseInt(info[1])];
			tabZone = new ArrayList<LinkedList<Integer>>();
			tabPlanche = new ArrayList<LinkedList<Case>>();
			tailleTabZone = Integer.parseInt(info[2]);
			tailletabPlanche = Integer.parseInt(info[3]);
		}
		else {
			throw new GardenWrongDataFormatException();
		}
		/* traitement des informations des cases */
		while (fluxIn.hasNextLine()){
			String line = fluxIn.nextLine();
			/*la première ligne contient les informations géométrique*/
			String[] info = line.split(" ");
			int abscisse = Integer.parseInt(info[0]); 
			int ordonnee = Integer.parseInt(info[1]);
			int numZone = Integer.parseInt(info[3]);
			int numPlanche = Integer.parseInt(info[4]);
			Case laCase;
			if (info[2].equalsIgnoreCase("hc")){				
				laCase = new CaseHorsJardin(abscisse, ordonnee);		
			}
			else if (info[2].equalsIgnoreCase("nc")){
				laCase = new CaseNonCultivable(abscisse, ordonnee);		
			}
			else {
				Plante plante = new Plante(info[5]);
				char c = info[6].charAt(0);
				if (c == 'v'){
					laCase = new CaseVariable(abscisse, ordonnee);		
				}
				else
				{
					laCase = new CaseFixe(abscisse, ordonnee, plante);
					
				}
			}
			this.terrain[abscisse][ordonnee] = laCase;
			/*ajout de la case a la planche*/
			tabPlanche.get(numPlanche).add(laCase);
			/*ajout de la planche a la zone correspondante s'il n'est pas encore ajouté */
			if (!tabZone.get(numZone).contains(numPlanche)){
				tabZone.get(numZone).push(numPlanche);
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
			z.ajouterPlanche(planches.get(i));
		}
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

	public ArrayList<Case> casesVoisines(Case caseJardin){
		return new ArrayList<Case>();
	}

	public CaseCultivable prochaineCaseAvecUnVoisinLibre(){
		return null;
	}
	
	public int nbCasesLibres(){
		return 0;
	}
	
}
