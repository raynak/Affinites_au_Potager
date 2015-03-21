package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Plante;
import model.combinatoire.ModeleCombi;
import model.combinatoire.ModeleCombiAlea;
import model.combinatoire.ModeleCombiGlouton;

public class CombinatoirePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel score;
	ModeleCombi m;
	JTerrainMap map;

	public CombinatoirePanel( Gui g){
		//de base on instancie un glouton comme modele combinatoire
		this.m = new ModeleCombiAlea(g.getTerrainPanel().getTerrain());
		this.setLayout(new GridLayout(7,1));
		this.map = g.getTerrainPanel();

//		String[] liste = {"Glouton", "Simple"};
//		JComboBox<String> comboBoxModelCombi = new JComboBox<String>(liste);
//		String[] listeAffinite = {"Affinite1", "Affinite2"};
//		JComboBox<String> comboBoxAffinites = new JComboBox<String>(listeAffinite);
//		String[] listeChoixCase = {"Largeur", "Longueur"};
//		JComboBox<String> comboBoxParcours = new JComboBox<String>(listeChoixCase);
//		String[] listeScoreCase = {"Minimum", "Maximum"};
//		JComboBox<String> comboBoScoreCase = new JComboBox<String>(listeScoreCase);
//		String[] listeScoreGlobal = {"argMax", "ArgMax du min"};
//		JComboBox<String> comboBoxScoreGlobal = new JComboBox<String>(listeScoreGlobal);

		JButton calculScore = new JButton("Score");
		JPanel j = new JPanel();
		this.score = new JLabel("0");
		j.add(this.score);

		calculScore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				m.algoOptimisation();
				m.jardin.affichePlante();
				CombinatoirePanel.this.score.setText(CombinatoirePanel.this.m.score()+"");
				map.repaint();
			}
		});
//		comboBoxModelCombi.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				};
//
//	
//		});
//
//		this.add(comboBoxModelCombi);
//		this.add(comboBoxAffinites);
//		this.add(comboBoxParcours);
//		this.add(comboBoScoreCase);
//		this.add(comboBoxScoreGlobal);
		
		JPanel plantesColor = new JPanel();
		plantesColor.setLayout(new BoxLayout(plantesColor, BoxLayout.Y_AXIS));
		LinkedList<Plante> plantes = this.map.getTerrain().getPlantes();
		for (int i=0; i<plantes.size(); i++){
			JPanel unePlanteCouleur = new JPanel();

			unePlanteCouleur.add(new JLabel(plantes.get(i).getNom()));
			JPanel jplante = new JPanel();
			jplante.setBackground(this.map.getPlanteColor()[i]);
			unePlanteCouleur.add(jplante);
			
			plantesColor.add(unePlanteCouleur);
		}
		this.add(plantesColor);
		this.add(calculScore);
		this.add(j);

	}

}
