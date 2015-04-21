package Tests;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import exceptions.PlancheNonMitoyenneException;
import model.Jardin;
import model.Planche;
import model.Plante;
import model.PlantesFichier;
import model.ZonePlantation;
import model.combinatoire.ModeleCombiAlea;
import model.combinatoire.ModeleCombiGlouton;
import model.combinatoire.ModeleCombiGloutonContraintes;

public class Performances {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, PlancheNonMitoyenneException {
		Jardin jardin = new Jardin(100, 100);
		LinkedList<Plante> listePlante = new LinkedList<Plante>();
		File file = new File("data/plante2.xml");
		System.out.println(file.exists());
		PlantesFichier plantes = new PlantesFichier("plante2.xml");
		System.out.println(plantes.getPlantesDispo().size());
		for (int i = 0; i < 15; i++) {
			listePlante.add(plantes.getPlantesDispo().get(i));
		}
		for (int i = 0; i < jardin.getTerrain().length; i++) {
			for (int j = 0; j < jardin.getTerrain()[i].length; j++) {
				jardin.setCase(i, j, "Variable");
			}
		}
		jardin.setPlantes(listePlante);
		System.out.println(listePlante.get(0).getAffinite(listePlante.get(1)));
		ZonePlantation z1 = new ZonePlantation();
		LinkedList<ZonePlantation> lz = new LinkedList<ZonePlantation>();
		int j = 0;
		for (int i =0;i<jardin.getTerrain().length-5;i+=2){
			Planche pl = new Planche(i, j++, 5, true, jardin);
			z1.ajouterPlanche(pl, jardin);
			System.out.println(i);
		}
		
		lz.add(z1);
		jardin.setZones(lz);

		ModeleCombiAlea algoAlea = new ModeleCombiAlea(jardin);
		algoAlea.algoOptimisation();
		System.out.println(algoAlea.score());
		jardin.initHasPlant();
		
		ModeleCombiGlouton algoGlouton = new ModeleCombiGlouton(jardin);
		
		algoGlouton.algoOptimisation();
		System.out.println("score : "+algoGlouton.score());
		
		jardin.initHasPlant();
		ModeleCombiGloutonContraintes algoContraintes = new ModeleCombiGloutonContraintes(jardin);
		algoContraintes.algoOptimisation();
		System.out.println("score : "+algoContraintes.score());
	}
	
}
