package model.combinatoire;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import java.io.*;
import java.util.LinkedList;

public class parserXml {
	private LinkedList<String> plantes;
	private String fichier;

	public parserXml(String fichier) {
		this.fichier = fichier;
		this.plantes = new LinkedList<>();
		parser(fichier);
	}

	public void parser(String fichier) {
		try {
			DocumentBuilderFactory fabrique = DocumentBuilderFactory
					.newInstance();

			// création d'un constructeur de documents
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();

			// lecture du contenu d'un fichier XML avec DOM
			File xml = new File("data/plante2.xml");
			Document document = constructeur.parse(xml);
			Element racine = document.getDocumentElement();
			// System.out.println(racine.getNodeName());
			NodeList racineNoeuds = racine.getChildNodes();
			int nbRacineNoeuds = racineNoeuds.getLength();

			for (int i = 0; i < nbRacineNoeuds; i++) {
				if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element plante = (Element) racineNoeuds.item(i);
					plantes.add(plante.getAttribute("nom"));
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
