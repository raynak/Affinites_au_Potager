package model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CaseBuilder {

	public Case constructCase(int x, int y, String typeCase) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		Class<?> c = Class.forName("model.Case"+typeCase);
		Constructor<?> constructeur = c.getConstructor(new Class[]	{int.class,int.class});
		//System.out.println(constructeur.toString());		
		Object o = constructeur.newInstance(new Object[] {x, y});
		
		return (Case)o;
		
	}
	
public static void main(String[] args){
	Case c;
	try {
		c = new CaseBuilder().constructCase(2, 2, "Variable");
		System.out.println(c.toString());

	} catch (ClassNotFoundException | InstantiationException
			| IllegalAccessException | NoSuchMethodException
			| SecurityException | IllegalArgumentException
			| InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
}
