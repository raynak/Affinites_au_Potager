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

import model.combinatoire.AffinitePlante;
import model.jardin.Jardin;
import model.jardin.Plante;
import model.jardin.PlantesFichier;

import org.xml.sax.SAXException;


public class ChoixPlanteOnglet extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1657093878185936722L;
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
		this.comboBox.setSelectedIndex(this.comboBox.getSelectedObjects().length-1);
		this.comboBox.addActionListener(new CheckListener());

		this.add(this.comboBox);
		JButton valider = new JButton("Valider");
		valider.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Jardin j = ChoixPlanteOnglet.this.gui.getJardin();
				/*Reset jardin pour eviter tout conflit de repaint*/
				j.resetJardin();
				
				LinkedList<Plante> liste = new LinkedList<Plante>();
				/*parcours des chekBox*/
				for (JCheckBox cb : ChoixPlanteOnglet.this.checkBoxes){
					if (cb.isSelected()){
						System.out.println("fichier "+(String)ChoixPlanteOnglet.this.comboBox.getSelectedItem());
						Plante planteSelectionnee = Plante.getInstanceOf(cb.getText());
						AffinitePlante affplante = new AffinitePlante(planteSelectionnee, (String)ChoixPlanteOnglet.this.comboBox.getSelectedItem());
						
						planteSelectionnee.setAffinites(affplante.getAffinites());
						System.out.println(affplante.getAffinites().size());
					//	planteSelectionnee.setAffinites(
								
						System.out.println(planteSelectionnee.getAffinites().size());
						/*ajout des plantes correspondant au checkbox slectionnées*/
						liste.add(planteSelectionnee);
					}
				}
				System.out.println("Nouveau choix plantes :"+liste);
				/*modification de la liste des plantes du jardin via le controleru gui qui se charge de déléguer les changements à tous les panels de l'interface graphique*/
				ChoixPlanteOnglet.this.gui.changePlantesJardinDApresPlantes(liste);
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

		System.out.println("count"+checkPanel.getComponentCount());
		return checkPanel;
	}

	private class CheckListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {

			@SuppressWarnings("unchecked")
			JComboBox<String> c = (JComboBox<String>)arg0.getSource();
			String fileAff = (String)c.getSelectedItem();
			System.out.println("fileAff"+fileAff);
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
