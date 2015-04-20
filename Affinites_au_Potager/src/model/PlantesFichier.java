package model;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PlantesFichier {

	@SuppressWarnings("unused")
	private String fichier;
	private LinkedList<Plante> plantesDispo;
	
	public PlantesFichier(String fichier){
		this.fichier = fichier;
		this.plantesDispo = setPlantesDispo(fichier);
	}
	
	public LinkedList<Plante> getPlantesDispo(){
		return this.plantesDispo;
	}
	
	public LinkedList<Plante> setPlantesDispo(String fichier){
		LinkedList<Plante> plantesDispo = new LinkedList<Plante>();
		try {
			DocumentBuilderFactory fabrique = DocumentBuilderFactory
					.newInstance();

			// création d'un constructeur de documents
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();

			// lecture du contenu d'un fichier XML avec DOM
			//File xml = new File("data/plante2.xml");
			File xml = new File("data/affinites/"+fichier);
			Document document = constructeur.parse(xml);
			Element racine = document.getDocumentElement();
			// System.out.println(racine.getNodeName());
			NodeList racineNoeuds = racine.getChildNodes();
			int nbRacineNoeuds = racineNoeuds.getLength();

			for (int i = 0; i < nbRacineNoeuds; i++) {
				if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element plante = (Element) racineNoeuds.item(i);
					Plante planteDispo = new Plante(plante.getAttribute("nom"));
					plantesDispo.add(planteDispo);
				}
			}
		} catch (ParserConfigurationException pce) {
			System.out.println("Erreur de configuration du parseur DOM");
			System.out
					.println("lors de l'appel à fabrique.newDocumentBuilder();");
		} catch (SAXException se) {
			System.out.println("Erreur lors du parsing du document");
			System.out.println("lors de l'appel à construteur.parse(xml)");
		} catch (IOException ioe) {
			System.out.println("Erreur d'entrée/sortie");
			System.out.println("lors de l'appel à construteur.parse(xml)");
		}

		return plantesDispo;
	}
	
	public static void main(String[] args) {
		PlantesFichier plantes = new PlantesFichier("data/plante2.xml");
		for(int i=0;i<plantes.getPlantesDispo().size();i++){
			System.out.println(plantes.getPlantesDispo().get(i).getNom());
		}
	}
}
