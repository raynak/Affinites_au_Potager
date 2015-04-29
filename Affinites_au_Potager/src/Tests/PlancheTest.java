package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import model.Case;
import model.CaseCultivable;
import model.CaseVariable;
import model.Jardin;
import model.Planche;
import model.Plante;

import org.junit.Test;

import exceptions.PlancheConstructorException;

public class PlancheTest {

	@Test
	public void testPlancheIntIntIntBoolean() {
		Planche pl = new Planche(3, 2, 4,true);
		assertNotNull(pl);
		assertEquals(3,pl.getX());
		assertEquals(2, pl.getY());
		assertEquals(4, pl.getNbCases());
	}

	@Test
	public void testPlancheIntIntIntBooleanJardin() {
		Jardin j = new Jardin(8,6);
		Planche pl = new Planche(3, 2, 4,true, j);
		assertNotNull(pl);
		assertEquals(3,pl.getX());
		assertEquals(2, pl.getY());
		assertEquals(4, pl.getNbCases());	
		assertEquals(4, pl.getCases().size());
		assertEquals(j.getTerrain()[3][2], pl.getCases().get(0));
		assertEquals(j.getTerrain()[4][2], pl.getCases().get(1));
		assertEquals(j.getTerrain()[5][2], pl.getCases().get(2));
		assertEquals(j.getTerrain()[6][2], pl.getCases().get(3));
		Planche pl2 = new Planche(0, 0, 3,false, j);
		assertEquals(3, pl2.getCases().size());
		assertEquals(j.getTerrain()[0][0], pl2.getCases().get(0));
		assertEquals(j.getTerrain()[0][1], pl2.getCases().get(1));
		assertEquals(j.getTerrain()[0][2], pl2.getCases().get(2));

	}

	@Test
	public void testPlancheCase() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, PlancheConstructorException {
		Jardin j = new Jardin (3,4);
		j.setCase(2, 2, "Variable");
		assertTrue(j.getCase(2, 2) instanceof CaseCultivable);
		Planche pl = new Planche(j.getCase(2, 2));
		assertNotNull(pl);
		assertEquals(pl.getCases().get(0), j.getCase(2, 2));
	}


	@Test(expected = PlancheConstructorException.class)
	public void testPlancheCaseException() throws PlancheConstructorException{
		Jardin j = new Jardin(3,4);
		@SuppressWarnings("unused")
		Planche pl = new Planche(j.getCase(0, 0));
	}

	@Test
	public void testPlancheLinkedListOfCase() throws PlancheConstructorException {
		Case c1 = new CaseVariable(2,2);
		Case c2 = new CaseVariable(2,3);
		Case c3 = new CaseVariable(2,4);
		LinkedList<Case> list = new LinkedList<Case>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		Planche pl = new Planche(list);
		assertFalse(pl.isOrientation());
		assertEquals(3, pl.getCases().size());
		assertEquals(2, pl.getX());
		assertEquals(2, pl.getY());	
		LinkedList<Case> listeUneCase = new LinkedList<Case>();
		listeUneCase.add(c1);
		Planche pl2 = new Planche(listeUneCase);
		assertTrue(pl2.isOrientation());
		assertEquals(1, pl2.getCases().size());
		assertEquals(2, pl2.getX());
		assertEquals(2, pl2.getY());
	}

	@Test
	public void testVoisinsPlanche() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Jardin jardin = new Jardin(8,6);
		for (int i=2; i<6; i++){
			for (int j=1; j<4; j++ ){
				jardin.setCase(i, j, "Variable");
			}
		}
		Planche pl = new Planche(2, 2, 4,true, jardin);
		LinkedList<CaseCultivable> list = pl.voisinsPlanche(jardin);
	System.out.println("test voisin"+list.size());
		for (Case c : list){
			System.out.println(c.toString());
		}
		System.out.println("fin test voisin");
		LinkedList<CaseCultivable> listConnue = new LinkedList<CaseCultivable>();
		listConnue.add((CaseCultivable) jardin.getCase(2, 1));
		listConnue.add((CaseCultivable) jardin.getCase(3, 1));
		listConnue.add((CaseCultivable) jardin.getCase(4, 1));
		listConnue.add((CaseCultivable) jardin.getCase(4, 1));
		listConnue.add((CaseCultivable) jardin.getCase(4, 1));
		listConnue.add((CaseCultivable) jardin.getCase(4, 1));

		
	}

	@Test
	public void testPlanchesVoisines() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetX() {
		Planche pl = new Planche(3, 2, 4,true);
		assertNotNull(pl);
		assertEquals(3,pl.getX());
	}

	@Test
	public void testGetY() {
		Planche pl = new Planche(3, 2, 4,true);
		assertNotNull(pl);
		assertEquals(2,pl.getY());
	}

	@Test
	public void testGetNbCases() {
		Planche pl = new Planche(3, 2, 4,true);
		assertNotNull(pl);
		assertEquals(4,pl.getNbCases());
	}

	@Test
	public void testGetAndSetCases() {
		Case c1 = new CaseVariable(2,2);
		Case c2 = new CaseVariable(2,3);
		Case c3 = new CaseVariable(2,4);
		LinkedList<Case> list = new LinkedList<Case>();
		list.add(c1);
		list.add(c2);
		list.add(c3);	
		Planche pl = new Planche(2,2,3,false);
		pl.setCases(list);
		assertEquals(list, pl.getCases());
	}

	@Test
	public void testIsOrientation() throws PlancheConstructorException {
		Case c1 = new CaseVariable(2,2);
		Case c2 = new CaseVariable(2,3);
		Case c3 = new CaseVariable(2,4);
		LinkedList<Case> list = new LinkedList<Case>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		Planche pl = new Planche(list);
		assertFalse(pl.isOrientation());
		Case c10 = new CaseVariable(3,3);
		Case c11 = new CaseVariable(4,3);
		LinkedList<Case> list2 = new LinkedList<Case>();
		list2.add(c10);
		list2.add(c11);
		Planche pl2 = new Planche(list2);
		assertTrue(pl2.isOrientation());
	}

	@Test
	public void testAppartientALaPlanche() {
		Jardin j = new Jardin(8,6);
		Planche pl = new Planche(3, 2, 4,true, j);
		System.out.println(j.getCase(3, 2));
		System.out.println(pl.getCases().get(0));
		assertTrue(pl.appartientALaPlanche(j.getCase(3, 2)));
		assertTrue(pl.appartientALaPlanche(j.getCase(4, 2)));
		assertFalse(pl.appartientALaPlanche(j.getCase(3, 3)));
	}

	@Test
	public void testSetPlante() throws PlancheConstructorException {
		Case c1 = new CaseVariable(2,2);
		Case c2 = new CaseVariable(2,3);
		Case c3 = new CaseVariable(2,4);
		LinkedList<Case> list = new LinkedList<Case>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		Planche pl = new Planche(list);
		Plante plante = Plante.getInstanceOf("Carotte");
		pl.setPlante(plante);
		for (Case c : pl.getCases()){
			if (c.getHasPlant()){
				assertEquals(plante, ((CaseCultivable)c).getPlante());
			}
		}
	}

	@Test
	public void testScorePlanche() {
		fail("Not yet implemented");
	}

	@Test
	public void testEstMitoyenne() {
		Jardin j = new Jardin(8,6);
		Planche pl = new Planche(3, 2, 2,true, j);
		Planche pl2 = new Planche(4,3,2,true, j);
		assertTrue(pl.estMitoyenne(pl2, j));
		Planche pl3 = new Planche(7,1,1,true, j);
		assertFalse(pl.estMitoyenne(pl3, j));

			}

	@Test
	public void testToString() {
		Planche pl = new Planche(3, 2, 4,true);
		assertEquals("Case de départ : 3 - 2, NbCases : 4, Horizontal", pl.toString());
		Planche pl2 = new Planche(5, 6, 2,false);
		assertEquals("Case de départ : 5 - 6, NbCases : 2, Vertical", pl2.toString());

			}
	
	@Test
	public void testAddCase() throws PlancheConstructorException{
		Planche pl = new Planche();
		assertEquals(0, pl.getCases().size());
		CaseCultivable c1 = new CaseVariable(2,2);
		pl.addCase(c1);
		assertEquals(1, pl.getCases().size());
		CaseCultivable c2 = new CaseVariable(2,3);
		pl.addCase(c2);
		assertEquals(2, pl.getCases().size());
		CaseCultivable c3 = new CaseVariable(2,4);
		pl.addCase(c3);
		assertEquals(3, pl.getCases().size());
		//ajout d'une case qui ne conviendra pas
		CaseCultivable c4 = new CaseVariable(5,6);
		pl.addCase(c4);
		assertEquals(3, pl.getCases().size());
	}

}
