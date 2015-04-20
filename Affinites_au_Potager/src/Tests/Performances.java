package Tests;

import java.io.File;
import java.util.LinkedList;

import model.Jardin;
import model.Plante;
import model.PlantesFichier;

public class Performances {
	public static void main(String[] args) {
		Jardin jardin = new Jardin(3, 3);
		LinkedList<Plante> listePlante = new LinkedList<Plante>();
		File file = new File("data/plante2.xml");
		System.out.println(file.exists());
		PlantesFichier plantes = new PlantesFichier("plante2.xml");
		System.out.println(plantes.getPlantesDispo().size());
		for (int i = 0; i < 15; i++) {
			listePlante.add(plantes.getPlantesDispo().get(i));
		}
		System.out.println(listePlante.get(0).getAffinite(listePlante.get(1)));
	}
}
