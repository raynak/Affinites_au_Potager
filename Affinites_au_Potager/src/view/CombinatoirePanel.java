package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Plante;
import model.combinatoire.ModeleCombi;

public class CombinatoirePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gui gui;
	private JPanel plantes;
	private JLabel score;
	private ModeleCombi m;
	private JTerrainMap map;

	public CombinatoirePanel(Gui g){
		this.gui = g;
		//de base on instancie un glouton comme modele combinatoire
		this.m = g.getCombi();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.map = g.getTerrainPanel();

		JButton calculScore = new JButton("Score");
		JPanel j = new JPanel();
		this.score = new JLabel("0");
		j.add(this.score);

		calculScore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CombinatoirePanel.this.gui.algoOptimisation();
//				CombinatoirePanel.this.m.algoOptimisation();
//				m.jardin.affichePlante();
//				CombinatoirePanel.this.score.setText(CombinatoirePanel.this.m.score()+"");
//				
//				map.repaint();
			}
		});

		this.plantes = new JPanel();
		this.plantes.setLayout(new BoxLayout(this.plantes, BoxLayout.Y_AXIS));
		LinkedList<Plante> plantes = this.map.getTerrain().getPlantes();
		for (int i=0; i<plantes.size(); i++){
			JPanel unePlanteCouleur = new JPanel();

			unePlanteCouleur.add(new JLabel(plantes.get(i).getNom()));
			JPanel jplante = new JPanel();
			jplante.setBackground(this.gui.getPlanteColor(plantes.get(i)));
			unePlanteCouleur.add(jplante);

			this.plantes.add(unePlanteCouleur);
		}
		this.add(this.plantes);
		this.add(calculScore);
		this.add(j);
	}
	
	public Gui getGui(){
		return this.gui;
	}

//	public void changeListPlantes(LinkedList<Plante> liste){
//		System.out.println("nouvelle liste "+liste+" taille"+liste.size());
//		this.remove(this.plantes);
//		this.repaint();
//		this.plantes = new JPanel();
//		this.plantes.setLayout(new BoxLayout(this.plantes, BoxLayout.Y_AXIS));
//
//		for (int i=0; i<liste.size(); i++){
//			JPanel unePlanteCouleur = new JPanel();
//
//			unePlanteCouleur.add(new JLabel(liste.get(i).getNom()));
//			JPanel jplante = new JPanel();
//			jplante.setBackground(this.map.getPlanteColor()[i]);
//			unePlanteCouleur.add(jplante);
//
//			this.plantes.add(unePlanteCouleur);
//		}
//		this.add(this.plantes);
//		this.plantes.revalidate();
//		//this.setComponentZOrder(this.plantes, 0);
//		this.revalidate();
//		this.repaint();
//	}


	public void changeColorPlantes(HashMap<Plante, Color> couleurs) {
		this.remove(this.plantes);
		this.repaint();
		System.out.println("dans methode changecolorplantes "+couleurs.keySet());
		this.plantes = new JPanel();
		this.plantes.setLayout(new BoxLayout(this.plantes, BoxLayout.Y_AXIS));

		for (Plante plante : couleurs.keySet()){
			JPanel unePlanteCouleur = new JPanel();

			unePlanteCouleur.add(new JLabel(plante.getNom()));
			JPanel jplante = new JPanel();
			jplante.setBackground(couleurs.get(plante));
			unePlanteCouleur.add(jplante);

			this.plantes.add(unePlanteCouleur);
		}
		this.add(this.plantes);
		this.plantes.revalidate();
		this.setComponentZOrder(this.plantes, 0);
		this.revalidate();
		this.repaint();

	}
	
	public void setScore(int score){
		this.score.setText(""+score);
	}
}
