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

import model.jardin.Plante;

public class CombinatoirePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gui gui;
	private JPanel plantes;
	private JLabel score;
	//private ModeleCombi m;
	private JTerrainMap map;

	public CombinatoirePanel(Gui g){
		this.gui = g;
		//de base on instancie un glouton comme modele combinatoire
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.map = g.getTerrainPanel();

		JButton optiJardin = new JButton("Cultiver");
		JButton calculScore = new JButton("Score");
		JButton infoPlante = new JButton("Info");
		infoPlante.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("click info");
				CombinatoirePanel.this.gui.infoPlantes();
			}
		});

		JButton affinitesButton = new JButton("Affinites");
		affinitesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CombinatoirePanel.this.gui.showAffinites();
			}
		});

		JPanel j = new JPanel();
		this.score = new JLabel("0");
		j.add(this.score);

		calculScore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CombinatoirePanel.this.gui.score();
			}
		});
		
		optiJardin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CombinatoirePanel.this.gui.algoOptimisation();
			}
		});

		this.plantes = new JPanel();
		this.plantes.setLayout(new BoxLayout(this.plantes, BoxLayout.Y_AXIS));
		try  {
			LinkedList<Plante> plantes = this.map.getTerrain().getPlantes();

			for (int i=0; i<plantes.size(); i++){
				JPanel unePlanteCouleur = new JPanel();

				unePlanteCouleur.add(new JLabel(plantes.get(i).getNom()));
				JPanel jplante = new JPanel();
				jplante.setBackground(this.gui.getPlanteColor(plantes.get(i)));
				unePlanteCouleur.add(jplante);

				this.plantes.add(unePlanteCouleur);
			}
		} catch(Exception e) {};
		this.add(this.plantes);
		this.add(infoPlante);
		this.add(affinitesButton);
		this.add(optiJardin);
		this.add(calculScore);
		this.add(j);
		
	}

	public Gui getGui(){
		return this.gui;
	}

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
