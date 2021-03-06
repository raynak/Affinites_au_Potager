package model.jardin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;
import exceptions.PlancheNonMitoyenneException;

/**
 * Classe de type Multiton : toutes les plantes d'un même genre sont représentées par un seul et unique objet
 * les instances de chaque plante est sauvé dans une hashmap 
 * @author Tanguy Maréchal
 * @author Delphine Poux
 */

public class Plante {

	private static ConcurrentHashMap<String, Plante> lesPlantes = new ConcurrentHashMap<String, Plante>();

	private String nom;
	private HashMap<String, Integer> affinites;
	private float qte;

	/**
	 * Constructeur de la classe plante
	 * @param nom le nom de la plante
	 */
	private Plante(String nom){
		this.nom = nom;
		this.qte = 1;
	}

	/**
	 * Constructeur de la classe plante
	 * @param nom le nom de la plante
	 * @param affFile le fichier d'affinités
	 */
	private Plante(String nom, String affFile){
		this.nom = nom;
		this.qte = 1;
		this.affinites = new HashMap<String, Integer>();
		this.setAffinites(affFile);
	}

	/**
	 * Retourne l'instance unique de la plante dont le nom est passé en paramètre
	 * @param nom le nom de la plante
	 * @return l'instance de la plante
	 */
	public static synchronized Plante getInstanceOf(String nom){
		lesPlantes.putIfAbsent(nom, new Plante(nom));
		return lesPlantes.get(nom);
	}

	/**
	 * Retourne l'instance unique de la plante dont le nom est passé en paramètre
	 * @param nom le nom de la plante
	 * @param affFile le fichier d'affinités
	 * @return l'instance de la plante
	 */
	public static synchronized Plante getInstanceOf(String nom, String affFile){
		lesPlantes.putIfAbsent(nom, new Plante(nom, affFile));
		return lesPlantes.get(nom);
	}

	/**
	 * @return la quantite
	 */
	public float getQte(){
		return this.qte;
	}

	/**
	 * Modifie la quantite
	 * @param qte la valeur à modifier
	 */
	public void setQte(float qte){
		this.qte = qte;
	}

	/**
	 * @return the affinites
	 */
	public HashMap<String, Integer> getAffinites() {
		return affinites;
	}

	/**
	 * @param affinites the affinites to set
	 */
	public void setAffinites(HashMap<String, Integer> affinites) {
		this.affinites = affinites;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Renvoie la valeur de l'affinite de la plante courante avec la platne passée en paramètre
	 * @param plante 
	 * @return la valeur de l'affinite de la plante courante avec la platne passée en paramètre
	 */
	public int getAffinite(Plante plante){
		try{
			return this.affinites.get(plante.getNom());
		} catch (NullPointerException e){
			System.out.println("Erreur :\n"+this.toString()+ " cherche affinite avec "+plante.nom);
			throw new IllegalStateException();
		}
	}

	/**
	 * Met à jour les affinités de la plante en fonction des affinités décrites dans le fichier d'affinités
	 * dont le nom est passé en paramètre
	 * @param fichier le fichier d'affinités
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
					if(plante.getAttribute("nom").equals(this.getNom())){
						NodeList enfants = plante.getChildNodes(); 
						int nombreDeNoeudsEnfants = enfants.getLength();
						for(int j=1;j<nombreDeNoeudsEnfants;j+=2){
							Node enfant = enfants.item(j);
							String nomPlante = enfant.getNodeName();
							int valeurAffinite = Integer.parseInt(enfant.getChildNodes().item(0).getNodeValue());
							this.affinites.put(nomPlante, valeurAffinite);
							/* Avec les plantes voulues */
							//							if(this.plantes.contains(enfant.getTextContent()))
							//								this.affinites.put(enfant.getNodeName(), Integer.parseInt(enfant.getTextContent()));
						}
					}
				}				
			}
			System.out.println("ok");
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

	@Override
	public String toString(){
		return this.nom;
	}

	public static void main(String[] args) throws FileNotFoundException, GardenWrongDataFormatException, PlancheConstructorException, PlancheNonMitoyenneException {
		Plante p = new Plante("carotte", "plante2.xml");
		System.out.println(p);
		System.out.println(p.affinites.keySet());
		for (String s : p.affinites.keySet()){
			System.out.println(s+" - "+p.affinites.get(s));
		}
	}
}
