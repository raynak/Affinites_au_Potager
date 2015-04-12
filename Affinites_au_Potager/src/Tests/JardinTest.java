package Tests;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Case;
import model.CaseCultivable;
import model.CaseFixe;
import model.CaseHorsJardin;
import model.CaseNonCultivable;
import model.Jardin;
import model.Planche;
import model.Plante;
import model.ZonePlantation;

import org.junit.Test;

import exceptions.PlancheConstructorException;
import exceptions.PlancheNonMitoyenneException;

public class JardinTest {

	@Test
	public void testJardin() {
		Jardin j = new Jardin();
		assertNotNull(j);
	}

	@Test
	public void testJardinIntInt() {
		Jardin j = new Jardin(4,5);
		assertNotNull(j);
		assertEquals(4, j.getTerrain().length);
		assertEquals(5, j.getTerrain()[0].length);
		assertNotNull(j.getPlantes());
		assertNotNull(j.getZones());
		for (int i=0; i<j.getTerrain().length; i++){
			for (int k=0; k<j.getTerrain()[0].length; k++){
				assertTrue(j.getTerrain()[i][k] instanceof CaseNonCultivable);
			}
		}
	}

	@Test
	public void testJardinString() {
	}


	@Test
	public void testFindZoneOfPlanche() throws PlancheNonMitoyenneException, PlancheConstructorException {
		Jardin jardin = new Jardin(4,5);
		ZonePlantation z1 = new ZonePlantation();
		ZonePlantation z2 = new ZonePlantation();
		LinkedList<ZonePlantation> lz = new LinkedList<ZonePlantation>();
		jardin.setZones(lz);
		lz.add(z1);
		lz.add(z2);
		Planche pl1 = new Planche(1, 2, 2, true);
		Planche pl2 = new Planche(3,4,1,true);
		z1.ajouterPlanche(pl1, jardin);
		z2.ajouterPlanche(pl2, jardin);
		assertEquals(z1, jardin.findZoneOfPlanche(pl1));
		assertEquals(z2, jardin.findZoneOfPlanche(pl2));
	}


	@Test
	public void testCasesVoisines() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		Jardin jardin = new Jardin(5,6);
		/*test pour une case complètement entourée d'autres cases*/
		ArrayList<Case> voisines = jardin.casesVoisines(jardin.getTerrain()[2][2]);
		assertEquals(8, voisines.size());
		assertTrue(voisines.contains(jardin.getTerrain()[1][1]));
		assertTrue(voisines.contains(jardin.getTerrain()[2][1]));
		assertTrue(voisines.contains(jardin.getTerrain()[3][1]));
		assertTrue(voisines.contains(jardin.getTerrain()[1][2]));
		assertTrue(voisines.contains(jardin.getTerrain()[3][2]));
		assertTrue(voisines.contains(jardin.getTerrain()[1][3]));
		assertTrue(voisines.contains(jardin.getTerrain()[2][3]));
		assertTrue(voisines.contains(jardin.getTerrain()[3][3]));
		assertTrue(!voisines.contains(jardin.getTerrain()[2][2]));

		/*test pour une case située en 0,0 : coin supérieur gauche**/
		ArrayList<Case> voisines00 = jardin.casesVoisines(jardin.getTerrain()[0][0]);
		assertEquals(3, voisines00.size());
		assertTrue(voisines00.contains(jardin.getTerrain()[0][1]));
		assertTrue(voisines00.contains(jardin.getTerrain()[1][1]));
		assertTrue(voisines00.contains(jardin.getTerrain()[1][0]));
		assertTrue(!voisines00.contains(jardin.getTerrain()[0][0]));

		/*test pour une case située en 2,0 :  sur le bord supérieur*/
		ArrayList<Case> voisines20 = jardin.casesVoisines(jardin.getTerrain()[2][0]);
		assertEquals(5, voisines20.size());
		assertTrue(voisines20.contains(jardin.getTerrain()[1][0]));
		assertTrue(voisines20.contains(jardin.getTerrain()[3][0]));
		assertTrue(voisines20.contains(jardin.getTerrain()[1][1]));
		assertTrue(voisines20.contains(jardin.getTerrain()[2][1]));
		assertTrue(voisines20.contains(jardin.getTerrain()[3][1]));
		assertTrue(!voisines20.contains(jardin.getTerrain()[2][0]));

		/*test pour une case située en 4,0 : coin supérieur droit*/
		ArrayList<Case> voisines40 = jardin.casesVoisines(jardin.getTerrain()[4][0]);
		assertEquals(3, voisines40.size());
		assertTrue(voisines40.contains(jardin.getTerrain()[3][0]));
		assertTrue(voisines40.contains(jardin.getTerrain()[3][1]));
		assertTrue(voisines40.contains(jardin.getTerrain()[4][1]));
		assertTrue(!voisines40.contains(jardin.getTerrain()[4][0]));

		/*test pour une case située en 0,3 :  sur le bord gauche*/
		ArrayList<Case> voisines03 = jardin.casesVoisines(jardin.getTerrain()[0][3]);
		assertEquals(5, voisines03.size());
		assertTrue(voisines03.contains(jardin.getTerrain()[0][2]));
		assertTrue(voisines03.contains(jardin.getTerrain()[1][2]));
		assertTrue(voisines03.contains(jardin.getTerrain()[1][3]));
		assertTrue(voisines03.contains(jardin.getTerrain()[0][4]));
		assertTrue(voisines03.contains(jardin.getTerrain()[1][4]));
		assertTrue(!voisines03.contains(jardin.getTerrain()[0][3]));

		/*test pour une case située en 0,3 :  sur le bord droit*/
		ArrayList<Case> voisines43 = jardin.casesVoisines(jardin.getTerrain()[4][3]);
		assertEquals(5, voisines43.size());
		assertTrue(voisines43.contains(jardin.getTerrain()[3][2]));
		assertTrue(voisines43.contains(jardin.getTerrain()[4][2]));
		assertTrue(voisines43.contains(jardin.getTerrain()[3][3]));
		assertTrue(voisines43.contains(jardin.getTerrain()[3][4]));
		assertTrue(voisines43.contains(jardin.getTerrain()[4][4]));
		assertTrue(!voisines43.contains(jardin.getTerrain()[4][3]));

		/*test pour une case située en 0,5 : coin inférieur gauche**/
		ArrayList<Case> voisines05 = jardin.casesVoisines(jardin.getTerrain()[0][5]);
		assertEquals(3, voisines05.size());
		assertTrue(voisines05.contains(jardin.getTerrain()[0][4]));
		assertTrue(voisines05.contains(jardin.getTerrain()[1][4]));
		assertTrue(voisines05.contains(jardin.getTerrain()[1][5]));
		assertTrue(!voisines05.contains(jardin.getTerrain()[0][5]));

		/*test pour une case située en 2,5 :  sur le bord inférieur*/
		ArrayList<Case> voisines25 = jardin.casesVoisines(jardin.getTerrain()[2][5]);
		assertEquals(5, voisines25.size());
		assertTrue(voisines25.contains(jardin.getTerrain()[1][4]));
		assertTrue(voisines25.contains(jardin.getTerrain()[2][4]));
		assertTrue(voisines25.contains(jardin.getTerrain()[3][4]));
		assertTrue(voisines25.contains(jardin.getTerrain()[1][5]));
		assertTrue(voisines25.contains(jardin.getTerrain()[3][5]));
		assertTrue(!voisines25.contains(jardin.getTerrain()[2][5]));

		/*test pour une case située en 4,5 : coin inférieur droit*/
		ArrayList<Case> voisines45 = jardin.casesVoisines(jardin.getTerrain()[4][5]);
		assertEquals(3, voisines45.size());
		assertTrue(voisines45.contains(jardin.getTerrain()[3][4]));
		assertTrue(voisines45.contains(jardin.getTerrain()[4][4]));
		assertTrue(voisines45.contains(jardin.getTerrain()[3][5]));
		assertTrue(!voisines45.contains(jardin.getTerrain()[4][5]));

		for (int i=0; i<jardin.getTerrain().length; i++){
			for (int j=0; j<jardin.getTerrain()[0].length; j++){
				jardin.setCase(i, j, "Cultivable");
			}
		}
		Case c = jardin.getTerrain()[3][3];

		assertEquals(8, jardin.casesVoisines(c).size());

	}

	@Test
	public void testCasesVoisinesCultivables() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Jardin jardin = new Jardin(5,6);
		jardin.setCase(2, 2, "Cultivable");
		Case c = jardin.getTerrain()[2][2];
		assertTrue(c instanceof CaseCultivable);
		assertEquals(0, jardin.casesVoisinesCultivables(c).size());
		jardin.setCase(1, 1, "Cultivable");
		assertEquals(1, jardin.casesVoisinesCultivables(c).size());
		assertEquals(jardin.getTerrain()[1][1], jardin.casesVoisinesCultivables(c).get(0));
		jardin.setCase(2, 1, "Cultivable");
		assertEquals(2, jardin.casesVoisinesCultivables(c).size());
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[2][1]));
		jardin.setCase(3, 3, "Cultivable");
		assertEquals(3, jardin.casesVoisinesCultivables(c).size());
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[3][3]));

		for (int i=0; i<jardin.getTerrain().length; i++){
			for (int j=0; j<jardin.getTerrain()[0].length; j++){
				jardin.setCase(i, j, "Cultivable");
			}
		}
		c = jardin.getTerrain()[2][2];

		assertEquals(8, jardin.casesVoisines(c).size());
		assertEquals(8, jardin.casesVoisinesCultivables(c).size());
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[1][1]));
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[1][2]));
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[1][3]));
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[2][1]));
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[2][3]));
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[3][1]));
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[3][2]));
		assertTrue(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[3][3]));
		assertFalse(jardin.casesVoisinesCultivables(c).contains(jardin.getTerrain()[2][2]));



	}

	@Test
	public void testProchaineCaseAvecUnVoisinLibre() {
		fail("Not yet implemented");
	}

	@Test
	public void testNbCasesLibres() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Jardin jardin = new Jardin(4,5);
		assertEquals(0, jardin.nbCasesLibres());
		jardin.setCase(2, 2, "Cultivable");
		jardin.setCase(3, 4, "Cultivable");
		assertEquals(2, jardin.nbCasesLibres());
		CaseCultivable c = (CaseCultivable)jardin.getTerrain()[2][2];
		c.setPlante(new Plante("Carotte"));
		assertEquals(1,	jardin.nbCasesLibres());


	}

	@Test
	public void testSetCase() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Jardin jardin = new Jardin(4,5);
		assertTrue(jardin.getTerrain()[2][2] instanceof Case);
		assertFalse(jardin.getTerrain()[2][2] instanceof CaseCultivable);
		assertFalse(jardin.getTerrain()[2][2] instanceof CaseHorsJardin);

		jardin.setCase(2, 2, "Cultivable");
		assertTrue(jardin.getTerrain()[2][2] instanceof Case);
		assertTrue(jardin.getTerrain()[2][2] instanceof CaseCultivable);
		assertFalse(jardin.getTerrain()[2][2] instanceof CaseHorsJardin);

		jardin.setCase(2, 2, "HorsJardin");
		assertTrue(jardin.getTerrain()[2][2] instanceof Case);
		assertFalse(jardin.getTerrain()[2][2] instanceof CaseCultivable);
		assertTrue(jardin.getTerrain()[2][2] instanceof CaseHorsJardin);
	}
	
	@Test
	public void resetJardinTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		Jardin jardin = new Jardin(5,5);
		jardin.setCase(2, 2, "Cultivable");
		jardin.setCase(2, 3, "Cultivable");
		Case c = new CaseFixe(4, 4, new Plante("Ail"));
		jardin.getTerrain()[4][4] = c;
		jardin.getCase(2, 2).setPlante(new Plante("Carotte"));
		jardin.getCase(2, 3).setPlante(new Plante("Ail"));
		
		assertEquals(c, jardin.getCase(4, 4));
		assertTrue(jardin.getCase(2, 2).getHasPlant());
		assertTrue(jardin.getCase(2, 3).getHasPlant());
		jardin.resetJardin();
		assertEquals(c, jardin.getCase(4, 4));
		assertNull(((CaseCultivable)jardin.getCase(2, 2)).getPlante());
		assertNull(((CaseCultivable)jardin.getCase(2, 3)).getPlante());
		assertFalse(jardin.getCase(2, 2).getHasPlant());
		assertFalse(jardin.getCase(2, 3).getHasPlant());
		
	}

	@Test
	public void testAjouterPlanche() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddPlanche() {
		fail("Not yet implemented");
	}

	@Test
	public void testFusionnerZones() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testAffichePlante() {
		fail("Not yet implemented");
	}


}
