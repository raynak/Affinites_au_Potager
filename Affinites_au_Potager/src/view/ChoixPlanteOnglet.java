package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import model.Jardin;
import model.Plante;
import model.PlantesFichier;
import model.combinatoire.AffinitePlante;

import org.xml.sax.SAXException;


public class ChoixPlanteOnglet extends JPanel {


	Gui gui;
	JPanel listeCheck;
	JComboBox<String> comboBox;
	PlantesFichier plantefichier;
	LinkedList<String> choixPlante;
	ArrayList<JCheckBox> checkBoxes;
	AffinitePlante affinites;

	public JPanel getListeCheck() {
		return listeCheck;
	}


	public void setListeCheck(JPanel listeCheck) {
		this.listeCheck = listeCheck;
	}

	public  ChoixPlanteOnglet(Gui gui) throws SAXException, IOException, ParserConfigurationException{
		this.gui = gui;
		this.checkBoxes = new ArrayList<JCheckBox>();
		this.choixPlante = new LinkedList<String>();

		this.comboBox = new JComboBox<String>();

		File dir = new File("data/affinites");
		File[] fileTab = dir.listFiles();
		for (File f : fileTab){
			this.comboBox.addItem(f.getName());
		}
		this.comboBox.addActionListener(new CheckListener());

		this.add(this.comboBox);
		JButton valider = new JButton("Valider");
		valider.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Jardin j = ChoixPlanteOnglet.this.gui.getTerrainPanel().getTerrain();

				LinkedList<Plante> liste = new LinkedList<Plante>();
				/*parcours des chekBox*/
				for (JCheckBox cb : ChoixPlanteOnglet.this.checkBoxes){
					if (cb.isSelected()){
						System.out.println("fichier "+(String)ChoixPlanteOnglet.this.comboBox.getSelectedItem());
						Plante planteSelectionnee = new Plante(cb.getText());
						AffinitePlante affplante = new AffinitePlante(planteSelectionnee, (String)ChoixPlanteOnglet.this.comboBox.getSelectedItem());
						
						planteSelectionnee.setAffinites(affplante.getAffinites());
						System.out.println(affplante.getAffinites().size());
					//	planteSelectionnee.setAffinites(
								
						System.out.println(planteSelectionnee.getAffinites().size());
						/*ajout des plantes correspondant au checkbox slectionnées*/
						liste.add(new Plante(cb.getText()));
					}
				}
				/*modification de la liste des plantes du jardin*/
				j.setPlantes(liste);
				/*modification des couleurs d'affichage des plantes (en fonction du nouveau choix)*/
				ChoixPlanteOnglet.this.gui.getTerrainPanel().changeColor(liste.size());

				ChoixPlanteOnglet.this.gui.getCombinatoire().changeListPlantes(liste);
			}

		});
		this.add(valider);

		this.listeCheck = this.checkPanel((String)this.comboBox.getSelectedItem());

		this.add(listeCheck);
	}


	public JPanel checkPanel(String fileAff){
		JPanel checkPanel = new JPanel();

		this.plantefichier = new PlantesFichier(fileAff);
		for (Plante pl : this.plantefichier.getPlantesDispo()){
			JCheckBox ckeck = new JCheckBox(pl.getNom());
			this.checkBoxes.add(ckeck);
			checkPanel.setLayout(new GridLayout(this.checkBoxes.size()/5, 5));
			checkPanel.add(ckeck);

		}

		//		int nbcheck = 0;
		//		try {
		//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//			DocumentBuilder loader = factory.newDocumentBuilder();
		//			Document document = loader.parse("data/affinites/"+fileAff);
		//			NodeList nl = document.getElementsByTagName("plante");
		//			nbcheck = nl.getLength();
		//			for (int i=1; i<nl.getLength(); i++){
		//				Node item = nl.item(i);
		//				NamedNodeMap nnm = item.getAttributes();
		//				String attr = (nnm.getNamedItem("nom").toString().split("="))[1].replace('"', ' ').trim();
		//				JCheckBox ckeck = new JCheckBox(attr);
		//				this.checkBoxes.add(ckeck);
		//				checkPanel.setLayout(new GridLayout(this.checkBoxes.size()/5, 5));
		//				checkPanel.add(ckeck);
		//			}
		//		} catch (Exception ex) {
		//			ex.printStackTrace();
		//		}
		System.out.println(checkPanel.getComponentCount());
		return checkPanel;
	}

	private class CheckListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JComboBox<String> c = (JComboBox<String>)arg0.getSource();
			String fileAff = (String)c.getSelectedItem();
			System.out.println(fileAff);
			ChoixPlanteOnglet.this.choixPlante.removeAll(choixPlante);
			ChoixPlanteOnglet.this.checkBoxes.removeAll(checkBoxes);
			ChoixPlanteOnglet.this.remove(ChoixPlanteOnglet.this.listeCheck);
			ChoixPlanteOnglet.this.repaint();
			ChoixPlanteOnglet.this.setListeCheck(ChoixPlanteOnglet.this.checkPanel(fileAff));
			ChoixPlanteOnglet.this.add(ChoixPlanteOnglet.this.listeCheck);
			ChoixPlanteOnglet.this.revalidate();
			ChoixPlanteOnglet.this.listeCheck.repaint();
			

		}

	}

}
