package Tests;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;

import model.Jardin;
import model.Planche;
import model.Plante;
import model.ZonePlantation;
import model.combinatoire.ModeleCombiGloutonContraintes;

import org.junit.Before;
import org.junit.Test;

import exceptions.PlancheNonMitoyenneException;

public class GloutonContraintesTests {

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
		carotte = Plante.getInstanceOf("Carotte");
		oignon = Plante.getInstanceOf("Oignon");
		ail = Plante.getInstanceOf("Ail");
		chou = Plante.getInstanceOf("Chou");
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
		affChou.put("Ail", -1);
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
				jardin.setCase(i, j, "Variable");
			}
		}
		System.out.println(jardin.getTerrain().length);
	}

	@Test
	public void testDeuxPlanchesGloutonContraintes() throws PlancheNonMitoyenneException {
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

		ModeleCombiGloutonContraintes modele = new ModeleCombiGloutonContraintes(jardin);
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
	public void testTroisPlanchesGloutonContraintes() throws PlancheNonMitoyenneException {
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

		ModeleCombiGloutonContraintes modele = new ModeleCombiGloutonContraintes(jardin);
		modele.algoOptimisation();
		if (!(pl1.getPlante() == carotte))
			fail("Carotte non fixée");
		if (!(pl2.getPlante() == chou))
			fail("Chou aurait du être planté à cet endroit");
		System.out.println("Planche 3 : "+pl3.getPlante().getNom());
		if(!(pl3.getPlante() == carotte))
			fail("Carotte aurait du être planté à cet endroit");
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
	public void testQuatrePlanchesGloutonContraintes() throws PlancheNonMitoyenneException {
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

		ModeleCombiGloutonContraintes modele = new ModeleCombiGloutonContraintes(jardin);
		modele.algoOptimisation();
		
		if(!(pl1.getPlante() == carotte))
			fail("La carotte n'est pas restée fixe");
		if(!(pl2.getPlante() == chou))
			fail("Une carotte devrait être plantée ici");
		if(!(pl3.getPlante() == chou))
			fail("Une carotte devrait être plantée ici");
		if(!(pl4.getPlante() == carotte))
			fail("Un chou devrait être planté ici");
		if(!(pl5.getPlante() == carotte))
			fail("Un chou devrait être planté ici");	
		
		if(!(modele.score() == 12))
			fail("Mauvais score");
	}

}
