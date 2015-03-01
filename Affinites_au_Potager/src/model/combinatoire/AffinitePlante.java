package model.combinatoire;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Plante;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AffinitePlante implements Affinites {
	private Plante plante;
	private LinkedList<Plante> plantes;
	private HashMap<String, Integer> affinites;
	private String fichier;
	
	public AffinitePlante (Plante plante, String fichier){
		this.plante = plante;
		this.fichier = fichier;
		setAffinites(fichier);
	}
	
	public void setAffinites(String fichier){
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
				/* On cherche la bonne plante */
				if(plante.getAttribute("nom") == this.plante.getNom()){
					NodeList enfants = plante.getChildNodes(); 
					int nombreDeNoeudsEnfants = enfants.getLength();
					for(int j=1;j<nombreDeNoeudsEnfants;j+=2){
						Node enfant = enfants.item(j);
						this.affinites.put(enfant.getNodeName(), Integer.parseInt(enfant.getTextContent()));
					}
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
	
	public HashMap<String, Integer> getAffinites(){
		return this.affinites;
	}
	
	public int getAffinite(Plante p){
		return this.affinites.get(p.getNom());
	}

	@Override
	public int getAffinite(Plante p1, Plante p2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
