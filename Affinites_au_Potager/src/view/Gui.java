package view;

//imports graphiques
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;






import javax.swing.JTabbedPane;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

//imports gestion de fichiers
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;





//imports listener
import controler.KeyboardListener;
//imports exception
import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;
import exceptions.PlancheNonMitoyenneException;

//import modèle
import model.Jardin;
import model.Plante;
import model.combinatoire.ModeleCombi;
import model.combinatoire.ModeleCombiAlea;
import model.combinatoire.ModeleCombiGlouton;

public class Gui {
	
	private Jardin jardin;
	private ModeleCombi combi;
	
	private JFrame framePrincipale;
	private ToolsPanel tools;
	private JTerrainMap terrainPanel;

	public JTerrainMap getTerrainPanel() {
		return terrainPanel;
	}

	public void setTerrainPanel(JTerrainMap jtp){
		this.terrainPanel = jtp;
	}

	public ModeleCombi getCombi() {
		return combi;
	}


	public void setCombi(ModeleCombi combi) {
		this.combi = combi;
	}


	public Gui(Jardin j) throws SAXException, IOException, ParserConfigurationException {
		this.jardin = j;
		this.framePrincipale = new JFrame();
		this.terrainPanel = new JTerrainMap(this.jardin);	
		JPanel terrainFrame = new JPanel(new BorderLayout());
		System.out.println(this.terrainPanel.getPreferredSize());
		terrainFrame.setPreferredSize(this.terrainPanel.getSize());
		terrainFrame.setBorder(BorderFactory.createLineBorder(Color.green));

		//this.terrainPanel.setPreferredSize(new Dimension(800,600));
		terrainFrame.add(this.terrainPanel, BorderLayout.CENTER);
		JScrollPane scrollpane = new JScrollPane(terrainFrame, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setPreferredSize(new Dimension(800,600));
		this.tools = new ToolsPanel(this.terrainPanel);
		this.framePrincipale.setLayout(new BorderLayout());
		this.framePrincipale.add(this.tools, BorderLayout.WEST);
		JTabbedPane onglet = new JTabbedPane();
		onglet.addTab("Jardin", null, scrollpane, "Représentation déométrique du jardin");
		onglet.addTab("Plantes",null, new ChoixPlanteOnglet(), "Choix des plantes");
		onglet.addTab("Combinatoire",null, new JPanel(), "Choix du modèle combinatoire : invisible pour les utilisateurs");

		//this.framePrincipale.add(/*terrainPanel*/terrainFrame, BorderLayout.EAST);
		this.framePrincipale.add(/*terrainFrame/*this.terrainPanel*//*scrollpane*/onglet
				, BorderLayout.CENTER);
		this.framePrincipale.add(new CombinatoirePanel(this), BorderLayout.EAST);

		JPanel loadSave = new JPanel();
		JButton load = new JButton("Charger Jardin");
		load.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File repertoireCourant = null;
				try {
					// obtention d'un objet File qui désigne le répertoire courant. Le
					// "getCanonicalFile" n'est pas absolument nécessaire mais permet
					// d'éviter les /Truc/./Chose/ ...
					repertoireCourant = new File(".").getCanonicalFile();
					System.out.println("Répertoire courant : " + repertoireCourant);
				} catch(IOException e) {}

				// création de la boîte de dialogue dans ce répertoire courant
				// (ou dans "home" s'il y a eu une erreur d'entrée/sortie, auquel
				// cas repertoireCourant vaut null)
				JFileChooser dialogue = new JFileChooser(repertoireCourant+"/data");
				// affichage
				dialogue.showOpenDialog(null);

				// récupération du fichier sélectionné
				try {
					Gui.this.getTerrainPanel().setTerrain(new Jardin(dialogue.getSelectedFile().toString()));;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}

		});
		
		JButton save = new JButton("Sauvegarder Jardin");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File repertoireCourant = null;
				try {
					// obtention d'un objet File qui désigne le répertoire courant. Le
					// "getCanonicalFile" n'est pas absolument nécessaire mais permet
					// d'éviter les /Truc/./Chose/ ...
					repertoireCourant = new File(".").getCanonicalFile();
					System.out.println("Répertoire courant : " + repertoireCourant);
				} catch(IOException e) {}

				// création de la boîte de dialogue dans le répertoire data 
				//obtenu à partir du répertoirecourant
				JFileChooser dialogue = new JFileChooser(repertoireCourant+"/data");
				dialogue.setApproveButtonText("Sauver");
				// affichage
				dialogue.showOpenDialog(null);

				// récupération du fichier sélectionné
				try {
					Gui.this.getTerrainPanel().getTerrain().saveJardin(dialogue.getSelectedFile().toString());;;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		});
		loadSave.add(load);
		loadSave.add(save);
		this.framePrincipale.add(loadSave, BorderLayout.SOUTH);

		this.framePrincipale.addKeyListener(new KeyboardListener(this.terrainPanel));
		this.framePrincipale.setFocusable(true);
		this.framePrincipale.requestFocus();

		this.framePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
	}


	

	public static void main(String[] args) throws GardenWrongDataFormatException, PlancheConstructorException, PlancheNonMitoyenneException, SAXException, IOException, ParserConfigurationException{
		Jardin j = new Jardin(5	,4);

		//Jardin j = new Jardin("jardin.txt");
		//	System.out.println(j.toString());
		//	 JFileChooser dialogue = new JFileChooser();
		//     
		//     // affichage
		//     dialogue.showOpenDialog(null);
		//  
		

		Plante carotte = new Plante("Carotte");
		Plante oignon = new Plante("Oignon");
		Plante ail = new Plante("Ail");
		Plante chou = new Plante("Chou");
		HashMap<String,Integer> affCarotte = new HashMap<String,Integer>();
		HashMap<String,Integer> affOignon = new HashMap<String,Integer>();
		HashMap<String,Integer> affAil = new HashMap<String,Integer>();
		HashMap<String,Integer> affChou = new HashMap<String,Integer>();
		affCarotte.put("Oignon", 1);
		affCarotte.put("Carotte", -2);
		affCarotte.put("Ail", -1);
		affCarotte.put("Chou", 1);
		
		affOignon.put("Oignon", 0);
		affOignon.put("Carotte", 1);
		affOignon.put("Ail", -1);
		affOignon.put("Chou", 1);
		
		affAil.put("Oignon", -1);
		affAil.put("Carotte", -1);
		affAil.put("Ail", 0);
		affAil.put("Chou", 1);
		
		affChou.put("Oignon", 1);
		affChou.put("Carotte", 1);
		affChou.put("Ail", 1);
		affChou.put("Chou", 0);
		carotte.setAffinites(affCarotte);
		chou.setAffinites(affChou);
		ail.setAffinites(affAil);
		oignon.setAffinites(affOignon);
		System.out.println("Carrotte a un nom "+carotte.getNom());
		LinkedList<Plante> listePlante = new LinkedList<Plante>();
		listePlante.add(carotte);
		listePlante.add(chou);
		listePlante.add(oignon);
		listePlante.add(ail);
		j.setPlantes(listePlante);
		
		Gui g = new Gui(j);
		g.combi = new ModeleCombiGlouton(g.jardin);
		System.out.println(g.combi.toString());
	//	g.combi = new ModeleCombiAlea(g.jardin);
		g.framePrincipale.pack();
		g.framePrincipale.setVisible(true);
		System.out.println(g.framePrincipale.isFocusOwner());


	}
}
