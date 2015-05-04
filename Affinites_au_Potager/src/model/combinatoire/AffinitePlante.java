package model.combinatoire;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.jardin.Plante;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;
import exceptions.PlancheNonMitoyenneException;

public class AffinitePlante implements Affinites {
	private Plante plante;
	private LinkedList<String> plantes;
	private HashMap<String, Integer> affinites;
	private String fichier;

	/**
	 * Constructeur de AffinitePlante, construit l'affinité d'une plante à partir d'un fichier passé en paramètre
	 * @param plante la plante dont nous souhaitons l'affinité
	 * @param fichier le fichier qui contient les affinités de la plante
	 */
	public AffinitePlante (Plante plante, String fichier){
		this.plante = plante;
		this.setFichier(fichier);
		this.plantes = new LinkedList<String>();
		this.affinites = new HashMap<String, Integer>();
		setAffinites(fichier);
	}

	/**
	 * set l'affinité de la plante à partir d'un fichier passé en paramètre
	 * @param fichier le fichier contenant les affinités
	 */
	public void setAffinites(String fichier){
		// création d'une fabrique de documents
		try {
			DocumentBuilderFactory fabrique = DocumentBuilderFactory
					.newInstance();

			// création d'un constructeur de documents
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();

			// lecture du contenu d'un fichier XML avec DOM
			File xml = new File("data/affinites/"+fichier);
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
					if(plante.getAttribute("nom").equals(this.plante.getNom())){
						NodeList enfants = plante.getChildNodes(); 
						int nombreDeNoeudsEnfants = enfants.getLength();
						for(int j=1;j<nombreDeNoeudsEnfants;j+=2){
							Node enfant = enfants.item(j);
							String nomPlante = enfant.getNodeName();
							int valeurAffinite = Integer.parseInt(enfant.getChildNodes().item(0).getNodeValue());
							this.plantes.addLast(nomPlante);
							this.affinites.put(nomPlante, valeurAffinite);
							/* Avec les plantes voulues */
//							if(this.plantes.contains(enfant.getTextContent()))
//								this.affinites.put(enfant.getNodeName(), Integer.parseInt(enfant.getTextContent()));
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

	/**
	 * Retourne les affinités
	 * @return affinites
	 */
	public HashMap<String, Integer> getAffinites(){
		return this.affinites;
	}

	/**
	 * Retourne l'affinité de la plante courante avec la plante p
	 * @param p une plante
	 * @return l'affinité entre la plante courante et p
	 */
	public int getAffinite(Plante p){
		return this.affinites.get(p.getNom());
	}

	@Override
	public int getAffinite(Plante p1, Plante p2) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Retourne le fichier utilisé
	 * @return le fichier
	 */
	public String getFichier() {
		return fichier;
	}

	/**
	 * set le fichier
	 * @param fichier
	 */
	public void setFichier(String fichier) {
		this.fichier = fichier;
	}

	public static void main(String[] args) throws FileNotFoundException, GardenWrongDataFormatException, PlancheConstructorException, PlancheNonMitoyenneException {
		Plante p = Plante.getInstanceOf("bette");
		AffinitePlante aff = new AffinitePlante(p, "affinitesHomeJardin2.xml");
		System.out.println(aff.plantes);
		System.out.println(aff.affinites.keySet());
		for (String s : aff.affinites.keySet()){
			System.out.println(s+" - "+aff.affinites.get(s));
		}
	}
}