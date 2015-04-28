package view;

//imports graphiques
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//imports gestion de fichiers
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.xml.parsers.ParserConfigurationException;

import model.Case;
//import modèle
import model.Jardin;
import model.Plante;
import model.combinatoire.ModeleCombi;
import model.combinatoire.ModeleCombiAlea;
import model.combinatoire.ModeleCombiGlouton;

import org.xml.sax.SAXException;

//imports listener
import controler.KeyboardListener;
//imports exception
import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;
import exceptions.PlancheNonMitoyenneException;

public class Gui {

	private Jardin jardin;
	private ModeleCombi combi;
	private String modeleCombi;

	private LinkedList<Plante> plantesFixes;
	private LinkedList<Plante> plantesVariables;
	private HashMap<Plante, Color> plantesColor;
	private Plante planteAFixer;

	private JFrame framePrincipale;
	private ToolsPanel tools;
	private JTerrainMap terrainPanel;
	private CombinatoirePanel combinatoire;


	public Jardin getJardin() {
		return jardin;
	}

	public void setJardin(Jardin jardin) {
		this.jardin = jardin;
		//this.combi.setJardin(this.jardin);
		this.terrainPanel.setTerrain(this.jardin);
		this.setPlantesVariables(jardin.getPlantes());
		this.plantesFixes = jardin.getPlantesFixes();;
		this.genereColor();
		System.out.println("taille jardin"+this.jardin.toString());

		//this.combi.setJardin(this.jardin);
		System.out.println(this.combi.getJardin());
		this.combinatoire.changeColorPlantes(plantesColor);
	}

	public JTerrainMap getTerrainPanel() {
		return terrainPanel;
	}

	public void setTerrainPanel(JTerrainMap jtp){
		this.terrainPanel = jtp;
	}

	public CombinatoirePanel getCombinatoire() {
		return combinatoire;
	}

	public void setCombinatoire(CombinatoirePanel combinatoire) {
		this.combinatoire = combinatoire;
	}

	public ModeleCombi getCombi() {
		return combi;
	}


	public void setCombi(ModeleCombi combi) {
		this.combi = combi;
	}


	public Plante getPlanteAFixer() {
		return planteAFixer;
	}

	public void setPlanteAFixer(Plante planteAFixer) {
		this.planteAFixer = planteAFixer;
		this.addPlanteFixe(planteAFixer);
	}

	/**
	 * Retourne la couleur correspondante à la plante passée en paramètre
	 * @param plante la plante de laquelle trouver la couleur
	 * @return la couleur de la plante pour l'affichage
	 */
	public Color getPlanteColor(Plante plante){
		return this.plantesColor.get(plante);
	}

	public LinkedList<Plante> getPlantesVariables() {
		return plantesVariables;
	}

	public void setPlantesVariables(LinkedList<Plante> plantesVariables) {
		this.plantesVariables = plantesVariables;
	}

	public Gui(Jardin j) throws SAXException, IOException, ParserConfigurationException {
		this.jardin = j;
		//this.combi = new ModeleCombiAlea(this.jardin);
		this.plantesFixes = new LinkedList<Plante>();
		this.plantesVariables = new LinkedList<Plante>();
		this.plantesVariables.addAll(j.getPlantes());
		this.plantesColor = new HashMap<Plante, Color>();
		this.genereColor();

		this.framePrincipale = new JFrame();
		this.terrainPanel = new JTerrainMap(this);	
		JPanel terrainFrame = new JPanel(new BorderLayout());
		terrainFrame.setPreferredSize(this.terrainPanel.getSize());
		terrainFrame.setBorder(BorderFactory.createLineBorder(Color.green));

		//this.terrainPanel.setPreferredSize(new Dimension(800,600));
		terrainFrame.add(this.terrainPanel, BorderLayout.CENTER);
		JScrollPane scrollpane = new JScrollPane(terrainFrame, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setPreferredSize(new Dimension(800,600));
		this.tools = new ToolsPanel(this/*.terrainPanel*/);
		this.framePrincipale.setLayout(new BorderLayout());
		this.framePrincipale.add(this.tools, BorderLayout.WEST);
		JTabbedPane onglet = new JTabbedPane();
		onglet.addTab("Jardin", null, scrollpane, "Représentation déométrique du jardin");
		onglet.addTab("Plantes",null, new ChoixPlanteOnglet(this), "Choix des plantes");
		onglet.addTab("Combinatoire",null, new ChoixCombiOnglet(this), "Choix du modèle combinatoire : invisible pour les utilisateurs");

		//this.framePrincipale.add(/*terrainPanel*/terrainFrame, BorderLayout.EAST);
		this.framePrincipale.add(/*terrainFrame/*this.terrainPanel*//*scrollpane*/onglet
				, BorderLayout.CENTER);

		this.combinatoire = new CombinatoirePanel(this);
		this.framePrincipale.add(this.combinatoire, BorderLayout.EAST);

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
					Gui.this.setJardin(new Jardin(dialogue.getSelectedFile().toString()));
					Gui.this.genereColor();
					Gui.this.setCombiColor();
					System.out.println(Gui.this.jardin.getPlantes());
					System.out.println(Gui.this.jardin.getPlantesFixes());
				} catch (Exception e) {
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
					e.printStackTrace();
				}			
			}
		});

		JButton newJardin = new JButton("Nouveau Jardin");
		newJardin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				JardinDialog jdialog = new JardinDialog(Gui.this);
			}

		});
		loadSave.add(newJardin);
		loadSave.add(load);
		loadSave.add(save);
		this.framePrincipale.add(loadSave, BorderLayout.SOUTH);

		this.framePrincipale.addKeyListener(new KeyboardListener(this.terrainPanel));
		this.framePrincipale.setFocusable(true);
		this.framePrincipale.requestFocus();

		this.framePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;

		this.framePrincipale.pack();
		this.framePrincipale.setVisible(true);
	}




	@SuppressWarnings("serial")
	private class JardinDialog extends JDialog implements ActionListener{
		Gui gui;
		JButton valider = new JButton("Valider");
		JButton annuler = new JButton("Annuler");
		JTextField width = new JTextField(10);
		JTextField height = new JTextField(9);

		public JardinDialog(Gui gui) {
			this.gui = gui;
			JPanel panneau ;

			Box boite = Box.createVerticalBox();
			setModal(true);
			setTitle("Dimension du jardin");
			panneau = new JPanel();
			panneau.add(new JLabel("Largeur : "));
			panneau.add(width);
			boite.add(panneau);

			panneau = new JPanel();
			panneau.add(new JLabel("Longueur : "));
			panneau.add(height);
			boite.add(panneau);

			panneau = new JPanel();
			panneau.add(valider);
			panneau.add(annuler);
			boite.add(panneau);

			add(boite) ;

			valider.addActionListener(this);
			annuler.addActionListener(this);
			pack();
			setLocation(400, 200);
			setVisible(true);
		}

		public void actionPerformed(ActionEvent evt) {
			Object source = evt.getSource();
			if (source == valider) {
				System.out.println("reussi");

				this.gui.setJardin(new Jardin(Integer.parseInt(this.width.getText()), Integer.parseInt(this.height.getText())));
				this.gui.combi.jardin = this.gui.jardin;
				this.gui.terrainPanel.setShowAffinites(false);
				this.gui.terrainPanel.repaint();
				dispose();
				System.out.println("plantes present dans le combi "+this.gui.combi.plantes);

			}
			else if (source == annuler) { 
				dispose();
			}
		}
	}

	/**
	 * Change une case du jardin en la passant de fix à variable ou inversement
	 * @param x l'abscisse de la case
	 * @param y	l'ordonnée de la case
	 * @param plante la plante si la case passe à fixe, null sinon
	 */
	public void changeCaseToFixOrVariable(int x, int y, Plante plante){
		Case c = this.jardin.getCase(x, y);
		this.jardin.setCase(x, y, c.passToFixOrVariable(plante));
		this.terrainPanel.changePlanteColor(this.terrainPanel.getPlanteColor().length+1);
		this.terrainPanel.repaint();
	}

	public void genereColor(){
		this.plantesColor = new HashMap<Plante, Color>();
		Random r = new Random();
		for (Plante plante : this.plantesVariables){
			this.plantesColor.put(plante, new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		}
		for (Plante plante : this.plantesFixes){
			if (!this.plantesColor.keySet().contains(plante))
				this.plantesColor.put(plante, new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		}
	}
	
	public void setCombiColor(){
		this.combinatoire.changeColorPlantes(plantesColor);
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


		Plante carotte = new Plante("carotte", "plante2.xml");
		Plante oignon = new Plante("epinard", "plante2.xml");
		Plante ail = new Plante("ail", "plante2.xml");
		Plante chou = new Plante("chou", "plante2.xml");
		//				HashMap<String,Integer> affCarotte = new HashMap<String,Integer>();
		//				HashMap<String,Integer> affOignon = new HashMap<String,Integer>();
		//				HashMap<String,Integer> affAil = new HashMap<String,Integer>();
		//				HashMap<String,Integer> affChou = new HashMap<String,Integer>();
		//				affCarotte.put("Oignon", 1);
		//				affCarotte.put("Carotte", -1);
		//				affCarotte.put("Ail", -1);
		//				affCarotte.put("Chou", 1);
		//		
		//				affOignon.put("Oignon", 0);
		//				affOignon.put("Carotte", 1);
		//				affOignon.put("Ail", -1);
		//				affOignon.put("Chou", 1);
		//		
		//				affAil.put("Oignon", -1);
		//				affAil.put("Carotte", -1);
		//				affAil.put("Ail", 0);
		//				affAil.put("Chou", 1);
		//		
		//				affChou.put("Oignon", 1);
		//				affChou.put("Carotte", 1);
		//				affChou.put("Ail", 1);
		//				affChou.put("Chou", 0);
		//				carotte.setAffinites(affCarotte);
		//				chou.setAffinites(affChou);
		//				ail.setAffinites(affAil);
		//				oignon.setAffinites(affOignon);
		//				System.out.println("Carrotte a un nom "+carotte.getNom());
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


		g.framePrincipale.pack();g.framePrincipale.setVisible(true);
	}

	public LinkedList<Plante> getPlantesFixes() {
		return this.plantesFixes;
	}

	public void setPlantesFixes(LinkedList<Plante> plantes) {
		Random r = new Random();
		/*on vérifie pour chaque plante fixe, elle n'est pas aussi variable*/
		for(Plante plante : this.plantesFixes){
			if (!this.plantesVariables.contains(plante)){
				/*la plante n'existe pas aussi dans les plantes variables*/
				/*on peut supprimer sa couleur*/
				this.plantesColor.remove(plante);
			}
		}
		/*toutes les plantes fixes ont été retirées sans retirer les mauvaises couleurs*/
		for (Plante nouvellePlante : plantes){
			if(!this.plantesColor.keySet().contains(nouvellePlante)){
				this.plantesColor.put(nouvellePlante, new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));

			}
		}
		//		for (int i=0; i<this.plantesVariables.size(); i++){
		//			this.plantesColor.removeFirst();
		//		}
		//		this.plantesFixes = plantes;
		//		for (int j=0; j<plantes.size(); j++){
		//			this.plantesColor.addFirst(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		//		}
	}

	public String getModeleCombi() {
		return modeleCombi;
	}

	public void setModeleCombi(String modeleCombi) {
		this.modeleCombi = modeleCombi;
		System.out.println("Mise à jour du modele combinatoire : "+this.modeleCombi);
	}

	public void addPlanteFixe(Plante plante){
		this.terrainPanel.setShowAffinites(false);

		if (this.plantesFixes.contains(plante)){
			return;
		}
		Random r = new Random();
		this.plantesFixes.addFirst(plante);
		if (!this.plantesVariables.contains(plante)){
			this.plantesColor.put(plante, new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		}
	}

	public void changePlantesJardinDApresPlantes(LinkedList<Plante> plantes) {
		this.jardin.setPlantes(plantes);
		this.setPlantesVariables(plantes);
		this.genereColor();
		this.combinatoire.changeColorPlantes(this.plantesColor);
	}

	public void algoOptimisation() {
		System.out.println(this.getPlantesVariables().get(0).getAffinites().toString());
		this.jardin.resetJardin();
		this.terrainPanel.setShowAffinites(true);
		//this.combi.algoOptimisation();
		ModeleCombi combi = new ModeleCombiAlea(this.getJardin());
		combi.algoOptimisation();
		this.jardin.affichePlante();
		this.combinatoire.setScore(combi.score());

		this.terrainPanel.repaint();

	}

	public void infoPlante(int x, int y) {
		System.out.println("info plante");

		int taille = this.terrainPanel.getTailleCase();
		System.out.println(x/taille+ " _ "+y/taille);

		if (this.jardin.getCase(x/taille, y/taille).getHasPlant()){
			ToolTipManager.sharedInstance().setInitialDelay(0);
			ToolTipManager.sharedInstance().setDismissDelay(250);
			System.out.println(x/taille+ " _ "+y/taille);

			this.terrainPanel.setToolTipText(this.jardin.getCase(x/taille, y/taille).getPlante().getNom());}

	}

	public void showAffinites() {
		if (this.terrainPanel.isShowAffinites()){
			this.terrainPanel.setShowAffinites(false);
		}
		else {
			this.terrainPanel.setShowAffinites(true);
		}
		this.terrainPanel.repaint();
	}

	public void infoPlantes() {
		this.terrainPanel.showPlantesName();
	}

	public void zoom(boolean b) {
		int taille = this.terrainPanel.getTailleCase();
		if (b && taille<160){
			this.terrainPanel.setTailleCase(this.terrainPanel.getTailleCase()*2);
			this.terrainPanel.repaint();
		}
		else if (!b && taille>5){
			this.terrainPanel.setTailleCase(this.terrainPanel.getTailleCase()/2);
			this.terrainPanel.repaint();
		}

	}



}
