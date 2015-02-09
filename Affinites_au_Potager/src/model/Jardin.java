package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import sun.misc.Regexp;
import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;

public class Jardin {

	private Case[][] terrain;
	private LinkedList<ZonePlantation> zonesPlantation;

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
		Scanner fluxIn = new Scanner(new File(/*fileName*/"data/jardin.txt"));
		ArrayList<LinkedList<Integer>> tabZone;
		ArrayList<LinkedList<Case>> tabPlanche;
		int tailleTabZone;
		int tailletabPlanche;

		for (int n=0; n<5; n++){
			if (fluxIn.nextLine().charAt(0)=='#'){
				/* on passe les lignes de commentaires*/
				System.out.println("t");}
			else {
				throw new GardenWrongDataFormatException();
			}
		}
		if (fluxIn.hasNextLine()){
			String line = fluxIn.nextLine();
			/*la première ligne contient les informations géométrique*/
			String[] info = line.split("[\\s]+");
			this.terrain = new Case[Integer.parseInt(info[0])][Integer.parseInt(info[1])];
			tabZone = new ArrayList<LinkedList<Integer>>();
			tabPlanche = new ArrayList<LinkedList<Case>>();
			tailleTabZone = Integer.parseInt(info[2]);
			tailletabPlanche = Integer.parseInt(info[3]);
			for (int i=0; i<tailleTabZone; i++){
				tabZone.add(new LinkedList<Integer>());
			}
			for (int i=0; i<tailletabPlanche; i++){
				tabPlanche.add(new LinkedList<Case>());
			}
		}
		else {
			throw new GardenWrongDataFormatException();
		}

		this.zonesPlantation = new LinkedList<ZonePlantation>();
		/* traitement des informations des cases */
		while (fluxIn.hasNextLine()){
			String line = fluxIn.nextLine();
			/*la première ligne contient les informations géométrique*/
			String[] info = line.split("[\\s]+");
			int abscisse = Integer.parseInt(info[0]); 
			int ordonnee = Integer.parseInt(info[1]);
			int numZone;
			int numPlanche;

			Case laCase;
			if (info[2].equalsIgnoreCase("hj")){				
				laCase = new CaseHorsJardin(abscisse, ordonnee);		
			}
			else if (info[2].equalsIgnoreCase("nc")){
				laCase = new CaseNonCultivable(abscisse, ordonnee);		
			}
			else {
				numZone = Integer.parseInt(info[3]);
				numPlanche = Integer.parseInt(info[4]);
				Plante plante = new Plante(info[5]);
				char c = info[6].charAt(0);
				if (c == 'v'){
					laCase = new CaseVariable(abscisse, ordonnee);		
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

		/*l'ensemble des cases a été créé */
		/*contruction des planches et des zones de plantations */
		ArrayList<Planche> planches = new ArrayList<Planche>();
		for (LinkedList<Case> list : tabPlanche){
			planches.add(new Planche(list));
		}
System.out.println("nplanch "+planches.size());
		for (int i=0; i<tabZone.size(); i++){
			ZonePlantation z = new ZonePlantation();
			for (int j=0; j<tabZone.get(i).size(); j++){
				z.ajouterPlanche(planches.get(tabZone.get(i).get(j)-1));
			}
			this.zonesPlantation.add(z);
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

	public static void main(String[] args) throws FileNotFoundException, GardenWrongDataFormatException, PlancheConstructorException{
		Jardin j = new Jardin("truc");
		System.out.println(j.toString());
	}

	public LinkedList<CaseCultivable> voisinsCase(int aleaX, int aleaY) {
		// TODO Auto-generated method stub
		return null;
	}
}
