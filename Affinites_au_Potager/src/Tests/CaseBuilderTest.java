package Tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import model.jardin.Case;
import model.jardin.CaseBuilder;
import model.jardin.CaseCultivable;
import model.jardin.CaseHorsJardin;
import model.jardin.CaseNonCultivable;
import model.jardin.CaseVariable;

import org.junit.Test;

public class CaseBuilderTest {

	@Test
	public void testConstructCase() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		CaseBuilder cb = new CaseBuilder();
		
		Case chj = cb.constructCase(0, 0, "HorsJardin");
		Case cc = cb.constructCase(1, 1, "Cultivable");
		Case cnc = cb.constructCase(2, 2, "NonCultivable");
		Case cv = cb.constructCase(4, 4, "Variable");
		assertTrue(chj instanceof CaseHorsJardin);
		assertTrue(cc instanceof CaseCultivable);
		assertTrue(cnc instanceof CaseNonCultivable);
		assertTrue(cv instanceof CaseVariable);
		
		
	}

}
