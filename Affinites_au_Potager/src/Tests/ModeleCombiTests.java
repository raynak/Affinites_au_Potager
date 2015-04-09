package Tests;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;

import model.Jardin;
import model.Planche;
import model.Plante;
import model.ZonePlantation;
import model.combinatoire.ModeleCombiAlea;
import model.combinatoire.ModeleCombiGlouton;

import org.junit.Before;
import org.junit.Test;

import exceptions.PlancheConstructorException;
import exceptions.PlancheNonMitoyenneException;
import exceptions.PlancheNonValideException;

public class ModeleCombiTests {

	private static Jardin jardin;
	private static Plante carotte;
	private static Plante oignon;
	private static Plante ail;
	private static Plante chou;

	@Before
	public void doJardin() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException {
		System.out.println("\n\nInitialisation");
		jardin = new Jardin(3, 3);
		carotte = new Plante("Carotte");
		oignon = new Plante("Oignon");
		ail = new Plante("Ail");
		chou = new Plante("Chou");
		HashMap<String, Integer> affCarotte = new HashMap<String, Integer>();
		HashMap<String, Integer> affOignon = new HashMap<String, Integer>();
		HashMap<String, Integer> affAil = new HashMap<String, Integer>();
		HashMap<String, Integer> affChou = new HashMap<String, Integer>();
		affCarotte.put("Oignon", 0);
		affCarotte.put("Carotte", 0);
		affCarotte.put("Ail", 0);
		affCarotte.put("Chou", 1);

		affOignon.put("Oignon", 0);
		affOignon.put("Carotte", 0);
		affOignon.put("Ail", 0);
		affOignon.put("Chou", 0);

		affAil.put("Oignon", -1);
		affAil.put("Carotte", -1);
		affAil.put("Ail", 0);
		affAil.put("Chou", 2);

		affChou.put("Oignon", 0);
		affChou.put("Carotte", 1);
		affChou.put("Ail", 2);
		affChou.put("Chou", 0);
		carotte.setAffinites(affCarotte);
		chou.setAffinites(affChou);
		ail.setAffinites(affAil);
		oignon.setAffinites(affOignon);
		LinkedList<Plante> listePlante = new LinkedList<Plante>();
		listePlante.add(carotte);
		listePlante.add(chou);
		listePlante.add(oignon);
		listePlante.add(ail);
		jardin.setPlantes(listePlante);
		for (int i = 0; i < jardin.getTerrain().length; i++) {
			for (int j = 0; j < jardin.getTerrain()[i].length; j++) {
				jardin.setCase(i, j, "Cultivable");
			}
		}
		System.out.println(jardin.getTerrain().length);
	}

	@Test
	public void testDeuxPlanchesAlea() throws PlancheConstructorException,
			PlancheNonMitoyenneException, PlancheNonValideException {
		System.out.println("\n\n Test deux planches aléa");
		ZonePlantation z1 = new ZonePlantation();
		LinkedList<ZonePlantation> lz = new LinkedList<ZonePlantation>();
		System.out.println(jardin.getTerrain().length);
		Planche pl1 = new Planche(0, 0, 1, true, jardin);
		Planche pl2 = new Planche(1, 0, 1, true, jardin);
		System.out.println(pl2.getX() + " " + pl2.getY());
		z1.ajouterPlanche(pl1, jardin);
		z1.ajouterPlanche(pl2, jardin);
		lz.add(z1);
		jardin.setZones(lz);

		ModeleCombiAlea modele = new ModeleCombiAlea(jardin);
		modele.algoOptimisation();
		for (int i = 0; i < jardin.getTerrain().length; i++) {
			for (int j = 0; j < jardin.getTerrain()[i].length; j++) {
				if ((i == 0 && j == 0) || (i == 1 && j == 0)) {
					if (!jardin.getTerrain()[i][j].getHasPlant())
						fail("Les planches cultivables n'ont pas été plantées");
				} else {
					if (jardin.getTerrain()[i][j].getHasPlant())
						fail("Les cases en dehors des planches ont été plantées");
				}
			}
		}
	}

	@Test
	public void testDeuxPlanchesGlouton() throws PlancheNonMitoyenneException {
		System.out.println("\n\n Test deux planches glouton");
		ZonePlantation z1 = new ZonePlantation();
		LinkedList<ZonePlantation> lz = new LinkedList<ZonePlantation>();
		System.out.println(jardin.getTerrain().length);
		Planche pl1 = new Planche(0, 0, 1, true, jardin);
		pl1.setPlante(carotte);
		Planche pl2 = new Planche(1, 0, 1, true, jardin);
		z1.ajouterPlanche(pl1, jardin);
		z1.ajouterPlanche(pl2, jardin);
		lz.add(z1);
		jardin.setZones(lz);

		ModeleCombiGlouton modele = new ModeleCombiGlouton(jardin);
		modele.algoOptimisation();

		jardin.getCase(0, 0).getPlante();
		if(!(pl1.getPlante() == carotte))
			fail("Carotte n'est pas restée fixée");
		
		if(!(pl2.getPlante() == chou))
			fail("Chou aurait dû être planté ici");
		for(int i=0;i<jardin.getTerrain().length;i++){
			for(int j=0;j<jardin.getTerrain()[i].length;j++){
				if(!((i==0 && j==0)||(i==1 && j==0))){
					if(jardin.getTerrain()[i][j].getPlante() != null)
						fail("Une plante a été plantée hors des planches");
				}
					
			}
		}
		if(!(modele.score()==2))
			fail("Le score est erroné");
	}

	@Test
	public void testTroisPlanchesGlouton() throws PlancheNonMitoyenneException {
		System.out.println("\n\n Test trois planches glouton");
		ZonePlantation z1 = new ZonePlantation();
		LinkedList<ZonePlantation> lz = new LinkedList<ZonePlantation>();
		System.out.println(jardin.getTerrain().length);
		Planche pl1 = new Planche(1, 1, 1, true, jardin);
		pl1.setPlante(carotte);
		Planche pl2 = new Planche(1, 2, 1, true, jardin);
		Planche pl3 = new Planche(2, 1, 1, true, jardin);
		z1.ajouterPlanche(pl1, jardin);
		z1.ajouterPlanche(pl2, jardin);
		z1.ajouterPlanche(pl3, jardin);
		lz.add(z1);
		jardin.setZones(lz);

		ModeleCombiGlouton modele = new ModeleCombiGlouton(jardin);
		modele.algoOptimisation();
		if (!(pl1.getPlante() == carotte))
			fail("Carotte non fixée");
		if (!(pl2.getPlante() == chou))
			fail("Chou aurait du être planté à cet endroit");
		for(int i=0;i<jardin.getTerrain().length;i++){
			for(int j=0;j<jardin.getTerrain()[i].length;j++){
				if(!((i==1 && j==1)||(i==1 && j==2)||(i==2 && j==1))){
					if(jardin.getTerrain()[i][j].getPlante() != null)
						fail("Une plante a été plantée hors des planches");
				}
					
			}
		}
	}

	@Test
	public void testQuatrePlanchesGlouton() throws PlancheNonMitoyenneException {
		System.out.println("\n\n Test quatre planches glouton");
		ZonePlantation z1 = new ZonePlantation();
		LinkedList<ZonePlantation> lz = new LinkedList<ZonePlantation>();
		System.out.println(jardin.getTerrain().length);
		Planche pl1 = new Planche(1, 1, 1, true, jardin);
		pl1.setPlante(carotte);
		Planche pl2 = new Planche(0, 1, 1, true, jardin);
		Planche pl3 = new Planche(2, 1, 1, true, jardin);
		Planche pl4 = new Planche(1, 2, 1, true, jardin);
		Planche pl5 = new Planche(1, 0, 1, true, jardin);
		z1.ajouterPlanche(pl1, jardin);
		z1.ajouterPlanche(pl2, jardin);
		z1.ajouterPlanche(pl3, jardin);
		z1.ajouterPlanche(pl4, jardin);
		z1.ajouterPlanche(pl5, jardin);
		lz.add(z1);
		jardin.setZones(lz);

		ModeleCombiGlouton modele = new ModeleCombiGlouton(jardin);
		modele.algoOptimisation();
		
		
	}

}
