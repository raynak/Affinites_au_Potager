package model.combinatoire;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import model.Plante;

public class AffinitesHomeJardin implements Affinites {

	HashMap<String, HashMap<String, Integer>> affinites;


	public AffinitesHomeJardin() throws FileNotFoundException{
		this.affinites = new HashMap<String, HashMap<String, Integer>>();
		//		File file = new File("http://www.homejardin.com/coup_de_pouce_compagnonnage/aide_conseil_assistance_explications.html");
		File file = new File("data/aide_conseil_assistance_explications.html");

		Scanner scanner = new Scanner(file);
		String propices= "", nefastes="";
		while (scanner.hasNext()){
			String line = scanner.nextLine();
			if (line.contains("<br><h3><b><u> LES VOISINAGES PROPICES")){
				propices = line;
			}
			else if (line.contains("<br><h3><b><u>LES VOISINAGES NEFASTES")){
				nefastes = line;
			}
		}
		System.out.println(propices);
		System.out.println(nefastes);
		try {
			propices = propices.substring("</center> <br><h3><b><u>LES VOISINAGES NEFASTES :</u></b><br></h3><br>• <b>".length()+1);
			nefastes = nefastes.substring("</center> <br><h3><b><u>LES VOISINAGES NEFASTES :</u></b><br></h3><br>• <b>".length());
			/* on sépare les différentes plantes : une case du tableau équivaut à une plante et ses affinités*/
			System.out.println(propices);
			System.out.println(nefastes);

			String[] plantesPropices = propices.split("<b>");
			String[] plantesNefastes = nefastes.split("<b>");
			for (String planteLatine : plantesPropices){
				System.out.println(planteLatine);
				String[] p = planteLatine.replace(".<br><br>•", "").split("</b>");
				int index = p[0].indexOf('(');
				if (index==-1){index = p[0].indexOf("'".charAt(0));}//une erreur de conception dans la page

				String laPlante = p[0].substring(0,index).trim().toLowerCase();

				if (laPlante.indexOf('&') != -1){laPlante = laPlante.substring(0, laPlante.indexOf('&'));}

				System.out.println("*Pos*"+laPlante+"**");
				if (!this.affinites.containsKey(planteLatine)){
					System.out.println("ajout dans la map");
					this.affinites.put(laPlante, new HashMap<String, Integer>());
				}
				/*suppression des caractères spéciaux inutiles*/
				p[1] = p[1].replace("&#160;","");
				p[1] = p[1].replace("&#39;","'");

				String[] lesPlantesaAffinitesPositives = p[1].split(",");
				for (String paff : lesPlantesaAffinitesPositives){
					paff = paff.toLowerCase();
					//System.out.println("la*"+paff);
					/*traitement du cas spéicla tomate*/
					if (paff.contains("tomate") && "tomate".equals(paff.substring(1, 7))){paff = "tomate";}

					this.affinites.get(laPlante).put(paff.trim(), (Integer)1);
				}
			}

			for (String plantenef : plantesNefastes){
				System.out.println(plantesNefastes.length);
				System.out.println("traitements des plantes nefastes");
				plantenef = plantenef.replace(". <br><br>•", "");
				plantenef = plantenef.replace(".<br><center>", "");

				String[] p = plantenef.replace(".<br><br>•", "").split("</b>");
				System.out.println(p[0]);
				//	String[] p = plantenef.split("</b>");
				int index = p[0].indexOf('(');
				if (index==-1){index = p[0].indexOf("'".charAt(0));}//une erreur de conception dans la page
				if (index==-1){index = p[0].indexOf('&');}
				if (index==-1){index = p[0].indexOf(':');}

				System.out.println("truc"+index);
				String laPlante = p[0].substring(0,index).trim().toLowerCase();
				System.out.println(laPlante);
				if (laPlante.indexOf('&') != -1){laPlante = laPlante.substring(0, laPlante.indexOf('&'));}
				//	laPlante = laPlante.toLowerCase();

				System.out.println("*Neg"+laPlante+"*");
				if (!this.affinites.containsKey(laPlante)){
					this.affinites.put(laPlante, new HashMap<String, Integer>());
				}
				p[1] = p[1].replace("&#160;","");
				p[1] = p[1].replace("&#39;","'");
				if (p[1].length()>=7 && "tomate".equals(p[1].substring(1, 6))){ p[1] = "tomate";}


				String[] lesPlantesaAffinitesNegatives = p[1] .split(",");
				for (String paff : lesPlantesaAffinitesNegatives){
					paff = paff.trim().toLowerCase();
					System.out.println("ici*"+paff);
					this.affinites.get(laPlante).put(paff.trim(), (Integer)(1));
				}
			}
			scanner.close();
		}
		catch (Exception e){
			System.out.println("Pas de définitions d'affinités"+e.getMessage());
		}
	}

	public HashMap<String, HashMap<String, Integer>> getAffinites() {
		return affinites;
	}

	@Override
	public int getAffinite(Plante p1, Plante p2) {
		return this.getAffinite(p1.getNom(), p2.getNom());
	}

	public int getAffinite(String p1, String p2){
		return this.affinites.get(p1).get(p2);
	}


	public static void main(String[] args) throws FileNotFoundException{
		AffinitesHomeJardin a = new AffinitesHomeJardin();
		System.out.println(a.getAffinites().size());
		System.out.println(a.getAffinites().keySet());
		System.out.println(a.getAffinites().get("Brocoli".toLowerCase()).get("chou"));
		//System.out.println(a.getAffinite("Ail", "Asperge"));
	}
}
