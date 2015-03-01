package model;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Affinites {
	private Plante plante;
	private LinkedList<Plante> plantes;
	private LinkedList<Plante> affinites;
	private LinkedList<Plante> nonAffinites;
	private String fichier;
	
	public Affinites(Plante plante, String fichier){
		this.plante = plante;
		this.fichier = fichier;
		setAffinites(fichier);
	}
	
	public void setAffinites(String fichier){
		try{
			// création d'une fabrique de documents 
			DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance(); 
  
			// création d'un constructeur de documents 
			DocumentBuilder constructeur = fabrique.newDocumentBuilder(); 
  
			// lecture du contenu d'un fichier XML avec DOM 
			File xml = new File(fichier); 
			Document document = constructeur.parse(xml);   
            Element racine = document.getDocumentElement();
            System.out.println(racine);
  
		}catch(ParserConfigurationException pce){ 
			System.out.println("Erreur de configuration du parseur DOM"); 
			System.out.println("lors de l'appel à fabrique.newDocumentBuilder();"); 
		}catch(SAXException se){ 
			System.out.println("Erreur lors du parsing du document"); 
			System.out.println("lors de l'appel à construteur.parse(xml)"); 
		}catch(IOException ioe){ 
			System.out.println("Erreur d'entrée/sortie"); 
			System.out.println("lors de l'appel à construteur.parse(xml)"); 
		} 
	} 
}
