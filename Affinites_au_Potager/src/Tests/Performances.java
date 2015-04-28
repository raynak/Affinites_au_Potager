package Tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	
	public static void triPerf(String fileResult) throws IOException{
		int[] resAlea = new int[100];
		int[] resGlouton = new int[100];
		int[] resContraintes = new int[100];
		int[] resAleaNeg = new int[20];
		for(int i=0;i<100;i++){
			resAlea[i] = 0;
			resGlouton[i] = 0;
			resContraintes[i] = 0;
		}
		for(int i=0;i<resAleaNeg.length;i++){
			resAleaNeg[i] = 0;
		}
		try{
			FileInputStream ips=new FileInputStream(fileResult); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				System.out.println(ligne);
				String[] sp = ligne.split(";");
				if(Integer.parseInt(sp[1]) >=0){
				resAlea[Integer.parseInt(sp[1])]++;
				} else {
					resAleaNeg[-Integer.parseInt(sp[1])]++;
				}
				resGlouton[Integer.parseInt(sp[2])]++;
				resContraintes[Integer.parseInt(sp[3])]++;
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		FileWriter fw = new FileWriter("prepTests.csv", true);
		BufferedWriter output = new BufferedWriter(fw);
		for(int i=resAleaNeg.length-1;i>=0;i--){
			output.write("-"+i+";"+resAleaNeg[i]+";0;0\n");
			output.flush();
		}
		for(int i=0;i<resAlea.length;i++){
			output.write(i+";"+resAlea[i]+";"+resGlouton[i]+";"+resContraintes[i]+"\n");
			output.flush();
		}
		output.close();
		
	}
	
	public static void testPerf() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException, PlancheNonMitoyenneException,
			IOException {

		FileWriter fw = new FileWriter("compareAlgo.csv", true);
		BufferedWriter output = new BufferedWriter(fw);

		for (int k = 0; k < 100; k++) {
			output.write(k + ";");
			output.flush();
			Jardin jardin = new Jardin(35, 35);
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
			System.out.println(listePlante.get(0).getAffinite(
					listePlante.get(1)));
			ZonePlantation z1 = new ZonePlantation();
			LinkedList<ZonePlantation> lz = new LinkedList<ZonePlantation>();
			int j = 0;
			for (int i = 0; i < jardin.getTerrain().length - 3; i += 2) {
				Planche pl = new Planche(i, j++, 3, true, jardin);
				z1.ajouterPlanche(pl, jardin);
				System.out.println(i);
			}

			lz.add(z1);
			jardin.setZones(lz);

			ModeleCombiAlea algoAlea = new ModeleCombiAlea(jardin);
			algoAlea.algoOptimisation();
			output.write(algoAlea.score() + ";");
			output.flush();
			jardin.initHasPlant();

			ModeleCombiGlouton algoGlouton = new ModeleCombiGlouton(jardin);

			algoGlouton.algoOptimisation();
			output.write(algoGlouton.score() + ";");
			output.flush();

			jardin.initHasPlant();
			ModeleCombiGloutonContraintes algoContraintes = new ModeleCombiGloutonContraintes(
					jardin);
			algoContraintes.algoOptimisation();
			output.write(algoContraintes.score() + "\n");
			output.flush();		}
		output.close();
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, PlancheNonMitoyenneException {
		testPerf();
		triPerf("compareAlgo.csv");
	}

}
