package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import model.Case;
import model.CaseCultivable;
import model.CaseFixe;
import model.CaseHorsJardin;
import model.CaseNonCultivable;
import model.CaseVariable;
import model.Jardin;
import model.Planche;
import model.Plante;

import org.junit.Test;

import exceptions.PlancheConstructorException;

public class CaseTest {

	@Test
	public void testCase() {
		Case c = new CaseHorsJardin(2,3);
		assertNotNull(c);
	}

	@Test
	public void testGetHasPlant() {
		Case c = new CaseHorsJardin(2,3);
		assertFalse(c.getHasPlant());
		Case c1 = new CaseCultivable(1,1);
		assertFalse(c1.getHasPlant());
		c1.setPlante(new Plante("Ail"));
		assertTrue(c1.getHasPlant());
	}

	@Test
	public void testGetPlanche() throws PlancheConstructorException {
		Jardin j = new Jardin(3,3);
		
		Case c = new CaseCultivable(1,1);
		assertNull(c.getPlanche(j));
		Planche pl = new Planche(c);
		j.ajouterPlanche(pl);
		assertEquals(pl, c.getPlanche(j));
	}

	@Test
	public void testVoisins() {
		Jardin jardin = new Jardin(4,5);
		Case c = jardin.getTerrain()[2][3];
		ArrayList<Case> listecases = jardin.casesVoisines(c);
		assertEquals(listecases, c.voisins(jardin));
	}


	@Test
	public void testAUnVoisinLibre() {
		Case c = new CaseCultivable(1,1);
		assertTrue(c.aUnVoisinLibre());}

	@Test
	public void testGetColor() {
		Case c = new CaseCultivable(1,1);
		assertEquals(new Color(220,170,50),c.getColor());
		Case cf = new CaseFixe(1,1, new Plante("Ail"));
		assertEquals(new Color(220,170,50),cf.getColor());
		Case cv = new CaseVariable(1,1);
		assertEquals(new Color(220,170,50),cv.getColor());
		Case chj = new CaseHorsJardin(1,1);
		assertEquals(Color.white, chj.getColor());
		Case cnc = new CaseNonCultivable(1,1);
		assertEquals(new Color(0,255,0), cnc.getColor());
	}

	@Test
	public void testGetXGetY() {
		Case c = new CaseCultivable(1, 3);
		assertEquals(1, c.getX());
		assertEquals(3, c.getY());
	}


	@Test
	public void testEstMitoyenne() {
		Jardin j = new Jardin(4,5);
		Case c = j.getTerrain()[2][2];
		ArrayList<Case> liste = j.casesVoisines(c);
		for (Case c1 : liste){
			assertTrue(c.estMitoyenne(c1, j));
		}
	}

	
}
