package Tests;

import java.io.File;

import org.w3c.dom.*;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.PlancheConstructorException;
import model.Case;
import model.Jardin;
import model.Planche;
import model.ZonePlantation;

public class Main {
	public static void main(String[] args) throws PlancheConstructorException {
		/*
		 * Jardin jardin = new Jardin(10,20); LinkedList<Case> cases = new
		 * LinkedList<Case>(); for(int i=0;i<5;i++){
		 * cases.add(jardin.getTerrain()[0][i]); } Planche planche = new
		 * Planche(cases); LinkedList<Planche> planches = new
		 * LinkedList<Planche>(); planches.add(planche); ZonePlantation zone =
		 * new ZonePlantation(); zone.setPlanches(planches);
		 * LinkedList<ZonePlantation> zones = new LinkedList<>();
		 * zones.add(zone); jardin.setZones(zones);
		 */
		// création d'une fabrique de documents
		try {
			DocumentBuilderFactory fabrique = DocumentBuilderFactory
					.newInstance();

			// création d'un constructeur de documents
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();

			// lecture du contenu d'un fichier XML avec DOM
			File xml = new File("data/plante2.xml");
			Document document = constructeur.parse(xml);
			Element racine = document.getDocumentElement();
			//System.out.println(racine.getNodeName());
			NodeList racineNoeuds = racine.getChildNodes();
			int nbRacineNoeuds = racineNoeuds.getLength();

			for (int i = 0; i<nbRacineNoeuds; i++) {
			    if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			        final Element plante = (Element) racineNoeuds.item(i);
				System.out.println(plante.getAttribute("nom"));
				//System.out.println("ail : " + plante.getAttribute("ail"));
				System.out.println("   affinités : ");
				NodeList enfants = plante.getChildNodes(); 
				int nombreDeNoeudsEnfants = enfants.getLength();
				for(int j=1;j<nombreDeNoeudsEnfants;j+=2){
					Node enfant = enfants.item(j);
					String valeur = enfant.getTextContent();
				System.out.println("   "+enfant.getNodeName() + " valeur : "+valeur);
				}
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

	}
}
