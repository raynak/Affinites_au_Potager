package model.combinatoire;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.jardin.Plante;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
				//				

				String[] lesPlantesaAffinitesNegatives = p[1] .split(",");
				for (String paff : lesPlantesaAffinitesNegatives){
					paff = paff.trim().toLowerCase();
					System.out.println("ici*"+paff);
					this.affinites.get(laPlante).put(paff.trim(), (Integer)(-1));
				}
			}
			scanner.close();
		}
		catch (Exception e){
			System.out.println("Pas de définitions d'affinités"+e.getMessage());
		}

		Set<String> plantesConnues = this.affinites.keySet();
		for (String unePlanteConnue : plantesConnues){
			Set<String> affConnue = this.affinites.get(unePlanteConnue).keySet();
			for (String plconnue : plantesConnues){
				if (!affConnue.contains(plconnue)){
					this.affinites.get(unePlanteConnue).put(plconnue, 0);
				}
			}
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

	public void hashMapToXML() throws ParserConfigurationException{
		this.hashMapCarre();
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
		
			Element rootElement = doc.createElement("catalogue");
			doc.appendChild(rootElement);

			for (String plante : this.affinites.keySet()){
				// staff elements
				Element staff = doc.createElement("plante");
				rootElement.appendChild(staff);

				// set attribute to staff element
				staff.setAttribute("nom", plante);

				// firstname elements
				for (String planteAff : this.affinites.get(plante).keySet()){
					try {
						String planteAffcor = planteAff.replace(" ", "_");
						planteAffcor = planteAffcor.replace("œ", "oe");
						planteAffcor = planteAffcor.replace("'", "_");
						if (planteAffcor.equals("sauge.<br><center>")){
							planteAffcor = "sauge";
						}
						if (planteAffcor.equals("maïs_(pour_les_haricots_à_rame)")){
							planteAffcor = "maïs";
						}
						if (planteAffcor.equals("romarin._<br><br>•")){
							planteAffcor = "romarin";
						}

						if (planteAffcor.equals("") 
								||planteAffcor.equals("doit_être_éloigné_de_tous_les_autres_légumes_et_aromatiques")
								||planteAffcor.equals("tous_les_légumes_sauf_fraisier")
								||planteAffcor.equals("tous_les_légumes_et_principalement_les_tomates")){
							continue;
						}
						//System.out.println(planteAffcor);
						Element firstname = doc.createElement(planteAffcor);

						firstname.appendChild(doc.createTextNode(this.affinites.get(plante).get(planteAff).toString()));
						staff.appendChild(firstname);
					}

					catch (DOMException e){
						e.printStackTrace();
						System.out.println("*"+planteAff+"*");
						System.exit(0);
					}
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("data/affinitesHomeJardin2.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}


	public void hashMapCarre(){
		HashMap<String, HashMap<String, Integer>> hmSecondaire = new HashMap<String, HashMap<String, Integer>>();

		Set<String> keySetPrincipal = this.affinites.keySet();
		for (String plantePrincipale : keySetPrincipal){
			HashMap<String, Integer> affPlantePrincipale = this.affinites.get(plantePrincipale);
			Set<String> keySetSecondaire = affPlantePrincipale.keySet();
			for (String planteSecondaire : keySetSecondaire){
				if (!keySetPrincipal.contains(planteSecondaire)){
					if (!hmSecondaire.keySet().contains(planteSecondaire)){
						hmSecondaire.put(planteSecondaire, new HashMap<String, Integer>());		
					}
					hmSecondaire.get(planteSecondaire).put(plantePrincipale, 
							this.affinites.get(plantePrincipale).get(planteSecondaire));
				}
			}
		}
		String[] plantesSec = new String[hmSecondaire.size()];
		for (String pl : hmSecondaire.keySet().toArray(plantesSec)){
			for (String plante : keySetPrincipal){

				if (!hmSecondaire.get(pl).keySet().contains(plante)){
					hmSecondaire.get(pl).put(plante, 0);
				}
			}
		}
		this.affinites.putAll(hmSecondaire);

		LinkedList<String> listeDesPlantes = new LinkedList<String>();
		Set<String> keySet = this.affinites.keySet();
		listeDesPlantes.addAll(keySet);
		for (String s : keySet){
			HashMap<String, Integer> h = this.affinites.get(s);
			for (String s2 : h.keySet()){
				if (!listeDesPlantes.contains(s)){
					listeDesPlantes.add(s);
				}
			}
		}

		System.out.println(listeDesPlantes.size()+ ": taille de plantes");
		for (String s : keySet){
			HashMap<String, Integer> h = this.affinites.get(s);
			for (String pl : listeDesPlantes){
				if (!h.containsKey(pl)){
					h.put(pl, 0);
				}
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException{
		AffinitesHomeJardin a = new AffinitesHomeJardin();
		System.out.println(a.getAffinites().size());
		System.out.println(a.getAffinites().keySet());
		System.out.println(a.getAffinites().get("Brocoli".toLowerCase()).get("chou"));
		System.out.println(a.getAffinites().keySet().size());
		for (String s : a.getAffinites().keySet()){
			System.out.println(a.getAffinites().get(s).keySet().size());
		}
		a.hashMapToXML();
		//System.out.println(a.getAffinite("Ail", "Asperge"));
	}
}
