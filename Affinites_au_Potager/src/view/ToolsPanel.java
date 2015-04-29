package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Plante;
import controler.CaseListener;
import controler.ChangeListenerofJTerrainMapListener;
import controler.FixOrVariableListener;
import controler.ZoomListener;

public class ToolsPanel extends JPanel {

	private static final long serialVersionUID = -361628317248498679L;

	private Gui gui;
	
	private Plante planteAFixer;

	//Zoom	
	private JButton zoomIn;
	private JButton zoomOut;
	//Case
	private JButton whiteCase;
	private JButton ocreCase;
	private JButton greenCase;
	private JButton selectCase;
	//Planches
	//private ToolsPlantationPanel tpp;
	private JButton FixeOrVariableButton;
	private JButton definePlancheButton;
	private JButton defineMultiPlancheHorizontal;
	private JButton defineMultiPlancheVertical;
	private JButton supPlanche;
	//Plantes

	public ToolsPanel(/*JTerrainMap j*/Gui gui) {
		this.gui = gui;
		
		ChangeListenerofJTerrainMapListener changeListener = new ChangeListenerofJTerrainMapListener(this, gui);
		JTerrainMap j = gui.getTerrainPanel();
		this.setPreferredSize(new Dimension(100, 600));
		//this.setLayout(new GridLayout(7, 1));
		this.zoomIn = new JButton(new ImageIcon("images/Zoom-In-icon.png"));
		this. zoomOut = new JButton(new ImageIcon("images/Zoom-Out-icon.png"));
		this.zoomIn.addActionListener(new ZoomListener(this.gui, this));
		this.zoomIn.setPreferredSize(new Dimension(50, 50));

		this.zoomOut.addActionListener(new ZoomListener(this.gui, this));
		this.zoomOut.setPreferredSize(new Dimension(50, 50));

		/* Outils de Zoom */
		JPanel zoomPanel = new JPanel(new GridLayout(1,2));
		zoomPanel.add(this.zoomIn);
		zoomPanel.add(this.zoomOut);
		this.add(zoomPanel);

		/* Outils de modification des cases du terrain */
		JPanel outilsTerrain = new JPanel(new GridLayout(2,1));
		outilsTerrain.add(new JLabel("  Outils  "));
		outilsTerrain.add(new JLabel("Terrain"));
		this.add(outilsTerrain);
		JPanel tp = new JPanel();
		tp.setLayout(new GridLayout(2,2));
		tp.setBorder(BorderFactory.createLineBorder(Color.black));

		this.whiteCase = new JButton(new ImageIcon("images/cursor_arrow_white.png"));
		this.whiteCase.setPreferredSize(new Dimension(50, 50));
		this.ocreCase = new JButton(new ImageIcon("images/cursor_arrow_ocre.png"));
		this.ocreCase.setPreferredSize(new Dimension(50, 50));
		this.greenCase = new JButton(new ImageIcon("images/cursor_arrow_green.png"));	
		this.greenCase.setPreferredSize(new Dimension(50, 50));
		TypeTerrainListener ttl = new TypeTerrainListener((CaseListener)j.getTerrainListener());
		this.ocreCase.addActionListener(ttl);
		this.ocreCase.addMouseListener(changeListener);
		this.greenCase.addActionListener(ttl);
		this.greenCase.addMouseListener(changeListener);
		this.whiteCase.addActionListener(ttl);
		this.whiteCase.addMouseListener(changeListener);

		Image img = new ImageIcon("images/cursor_arrow_vf.png").getImage();
		Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		this.FixeOrVariableButton = new JButton(new ImageIcon(newImg));
		this.FixeOrVariableButton.setPreferredSize(new Dimension(50, 50));
		this.FixeOrVariableButton.addActionListener(new FixePlanteListener(j));
		tp.add(this.whiteCase);
		tp.add(this.greenCase);
		tp.add(this.ocreCase);
		tp.add(this.FixeOrVariableButton);

		this.add(tp);

		/* Outils de modification des planches du terrain */
		JPanel outilsPlantation= new JPanel(new GridLayout(2,1));
		outilsPlantation.add(new JLabel("  Outils  "));
		outilsPlantation.add(new JLabel("Plantation"));
		this.add(outilsPlantation);

		Image img2 = new ImageIcon("images/planche.png").getImage();
		Image newImg2 = img2.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		this.definePlancheButton = new JButton(new ImageIcon(newImg2));
		this.definePlancheButton.setPreferredSize(new Dimension(50,50));
		this.definePlancheButton.addMouseListener(changeListener);
		Image img3 = new ImageIcon("images/planche_multihorizontal.png").getImage();
		Image newImg3 = img3.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		this.defineMultiPlancheHorizontal = new JButton(new ImageIcon(newImg3));
		this.defineMultiPlancheHorizontal.setPreferredSize(new Dimension(50,50));
		this.defineMultiPlancheHorizontal.addMouseListener(changeListener);
		Image img4 = new ImageIcon("images/planche_multivertical.png").getImage();
		Image newImg4 = img4.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		this.defineMultiPlancheVertical = new JButton(new ImageIcon(newImg4));
		this.defineMultiPlancheVertical.setPreferredSize(new Dimension(50,50));
		this.defineMultiPlancheVertical.addMouseListener(changeListener);
		Image img5 = new ImageIcon("images/sup_planche.png").getImage();
		Image newImg5 = img5.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		this.supPlanche = new JButton(new ImageIcon(newImg5));
		this.supPlanche.setPreferredSize(new Dimension(50,50));
		this.supPlanche.addMouseListener(changeListener);


		JPanel toolsPanel = new JPanel(new GridLayout(2,2));	
		toolsPanel.setPreferredSize(new Dimension(100,100));
		toolsPanel.add(definePlancheButton);
		toolsPanel.add(defineMultiPlancheHorizontal);
		toolsPanel.add(defineMultiPlancheVertical);
		toolsPanel.add(supPlanche);
		this.add(toolsPanel);
	}


	public ToolsPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public JButton getDefinePlancheButton() {
		return definePlancheButton;
	}

	public JButton getDefineMultiPlancheHorizontal() {
		return defineMultiPlancheHorizontal;
	}

	public JButton getDefineMultiPlancheVertical() {
		return defineMultiPlancheVertical;
	}

	public JButton getSupPlanche() {
		return supPlanche;
	}

	public JButton getSelectCase() {
		return selectCase;
	}

	/**
	 * @return the zoomIn
	 */
	public JButton getZoomIn() {
		return zoomIn;
	}

	/**
	 * @return the zoomOut
	 */
	public JButton getZoomOut() {
		return zoomOut;
	}

	public JButton getWhiteCase() {
		return whiteCase;
	}

	public JButton getOcreCase() {
		return ocreCase;
	}

	public JButton getGreenCase() {
		return greenCase;
	}

	public Plante getPlanteAFixer() {
		return planteAFixer;
	}

	public void setPlanteAFixer(Plante planteAFixer) {
		this.planteAFixer = planteAFixer;
	}

	public JButton getFixeOrVariableButton() {
		return FixeOrVariableButton;
	}

	public void setFixeOrVariableButton(JButton fixeOrVariableButton) {
		FixeOrVariableButton = fixeOrVariableButton;
	}

	public void fixNewTypeTerrainListener(CaseListener cl){
		this.greenCase.removeActionListener(this.greenCase.getActionListeners()[0]);
		this.greenCase.addActionListener(new TypeTerrainListener(cl));
		this.whiteCase.removeActionListener(this.whiteCase.getActionListeners()[0]);
		this.whiteCase.addActionListener(new TypeTerrainListener(cl));
		this.ocreCase.removeActionListener(this.ocreCase.getActionListeners()[0]);
		this.ocreCase.addActionListener(new TypeTerrainListener(cl));

	}

	private class TypeTerrainListener implements ActionListener {

		private CaseListener tl;

		public TypeTerrainListener(CaseListener tl){
			this.tl = tl;
		}
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source == ToolsPanel.this.ocreCase){
				this.tl.setSoltype("Cultivable");
			}
			else if (source==ToolsPanel.this.greenCase){
				this.tl.setSoltype("NonCultivable");
			}
			else if (source==ToolsPanel.this.whiteCase){
				this.tl.setSoltype("HorsJardin");
			}
			System.out.println(this.tl.getSoltype());
		}

	}

	private class FixePlanteListener implements ActionListener{

		private JTerrainMap map;

		public FixePlanteListener(JTerrainMap map){
			this.map = map;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (this.map.getTerrain().getPlantes().size() == 0){
				JOptionPane.showMessageDialog(ToolsPanel.this, "Choisissez d'abord un ensemble de plantes via l'onglet Plante");
			}
			else {
				String[] choixPlante = new String[this.map.getTerrain().getPlantes().size()];
				Iterator<Plante> it = this.map.getTerrain().getPlantes().iterator();
				int i=0;
				while (it.hasNext()){
					choixPlante[i++]=it.next().getNom();
				}
				String planteName = (String)JOptionPane.showInputDialog(ToolsPanel.this, "Choisissez la plante à fixer", 
						"Customized Dialog", JOptionPane.PLAIN_MESSAGE, new ImageIcon("images/icon-feuille.png"), choixPlante, choixPlante[0]);
				Plante plante = Plante.getInstanceOf(planteName);
				gui.setPlanteAFixer(plante);
				map.addMouseListener(new FixOrVariableListener(gui));

			}
		}

	}

	public static void main(String[] args){
//		JFrame f = new JFrame();
//		f.add(new ToolsPanel(new JTerrainMap(new Jardin(8, 6))));
//		f.pack();
//		f.setVisible(true);
	}
}
