package Tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import model.Case;
import model.CaseBuilder;
import model.CaseCultivable;
import model.CaseHorsJardin;
import model.CaseNonCultivable;
import model.CaseVariable;

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
