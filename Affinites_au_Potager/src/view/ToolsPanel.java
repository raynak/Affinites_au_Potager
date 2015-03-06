package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controler.CaseListener;
import controler.ChangeListenerofJTerrainMapListener;
import controler.ZoomListener;
import model.Jardin;

public class ToolsPanel extends JPanel {

	private static final long serialVersionUID = -361628317248498679L;
	//Zoom	
	private JButton zoomIn;
	private JButton zoomOut;
	//Case
	private JButton selectCase;
	private JRadioButton horsJardin;
	private JRadioButton cultivable;
	private JRadioButton nonCultivable;
	//Planches
	//private ToolsPlantationPanel tpp;
	private JButton FixeOrVariableButton;
	private JButton definePlancheButton;
	//Plantes
	
	public ToolsPanel(JTerrainMap j) {
		
		ChangeListenerofJTerrainMapListener changeListener = new ChangeListenerofJTerrainMapListener(this, j);
		
		this.setPreferredSize(new Dimension(100, 600));
		this.setLayout(new GridLayout(7, 1));
		this.zoomIn = new JButton(new ImageIcon("images/Zoom-In-icon.png"));
		this. zoomOut = new JButton(new ImageIcon("images/Zoom-Out-icon.png"));
		this.zoomIn.addActionListener(new ZoomListener(j, this));
		this.zoomOut.addActionListener(new ZoomListener(j, this));

		/* Outils de Zoom */
		JPanel zoomPanel = new JPanel(new GridLayout(1,2));
		zoomPanel.add(this.zoomIn);
		zoomPanel.add(this.zoomOut);
		this.add(zoomPanel);
		
		/* Outils de modification des cases du terrain */
		this.add(new JLabel("Outils Terrain"));
		
		JPanel tp = new JPanel();
		tp.setLayout(new GridLayout(2,2));
		
		this.selectCase = new JButton(new ImageIcon("images/cursor_arrow.png"));
		this.selectCase.addMouseListener(changeListener);
		tp.add(this.selectCase);
		this.horsJardin = new JRadioButton("HJ");
		this.cultivable = new JRadioButton("C");
		this.nonCultivable = new JRadioButton("NC");
		ButtonGroup groupe = new ButtonGroup();
		groupe.add(this.horsJardin);
		groupe.add(this.cultivable);
		groupe.add(this.nonCultivable);
		tp.add(this.horsJardin);
		tp.add(this.cultivable);
		tp.add(this.nonCultivable);
		TypeTerrainListener ttl = new TypeTerrainListener((CaseListener)j.getTl());
		this.horsJardin.addActionListener(ttl);
		this.cultivable.addActionListener(ttl);
		this.nonCultivable.addActionListener(ttl);
		this.add(tp);
		
		/* Outils de modification des planches du terrain */
		this.add(new JLabel("Outils Plantation"));
		//this.tpp = new ToolsPlantationPanel();
		//this.add(this.tpp);
		
		Image img = new ImageIcon("images/cursor_arrow_vf.png").getImage();
		Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		this.FixeOrVariableButton = new JButton(new ImageIcon(newImg));
		this.FixeOrVariableButton.setPreferredSize(new Dimension(50, 50));
		
		Image img2 = new ImageIcon("images/planche.png").getImage();
		Image newImg2 = img2.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		this.definePlancheButton = new JButton(new ImageIcon(newImg2));
		this.definePlancheButton.setPreferredSize(new Dimension(50,50));
		this.definePlancheButton.addMouseListener(changeListener);

		JPanel toolsPanel = new JPanel(new BorderLayout());	
		toolsPanel.setPreferredSize(new Dimension(100,50));
		toolsPanel.add(FixeOrVariableButton, BorderLayout.EAST);
		toolsPanel.add(definePlancheButton, BorderLayout.WEST);
		this.add(toolsPanel);
		
		
		/* Outils de modification des plantes du terrain */
		this.add(new JLabel("Outils Culture"));
		
		JPanel toolsCulturePanel = new JPanel(new GridLayout(2, 1));
		JButton choisirPlanteButton = new JButton("choix");
		JButton planterButton = new JButton("planter");
		toolsCulturePanel.add(choisirPlanteButton);
		toolsCulturePanel.add(planterButton);
		this.add(toolsCulturePanel);
	}

	/*public ToolsPlantationPanel getTpp() {
		return tpp;
	}*/

	public ToolsPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public JButton getDefinePlancheButton() {
		return definePlancheButton;
	}

	public JButton getSelectCase() {
		return selectCase;
	}

	public ToolsPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
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
	
	public void fixNewTtl(CaseListener cl){
		this.horsJardin.removeActionListener(this.horsJardin.getActionListeners()[0]);
		this.horsJardin.addActionListener(new TypeTerrainListener(cl));

		this.nonCultivable.removeActionListener(this.horsJardin.getActionListeners()[0]);
		this.nonCultivable.addActionListener(new TypeTerrainListener(cl));
	
		this.cultivable.removeActionListener(this.horsJardin.getActionListeners()[0]);
		this.cultivable.addActionListener(new TypeTerrainListener(cl));
	}

	private class TypeTerrainListener implements ActionListener {

		private CaseListener tl;

		public TypeTerrainListener(CaseListener tl){
			this.tl = tl;
		}
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source == ToolsPanel.this.cultivable){
				this.tl.setSoltype("Cultivable");
			}
			else if (source==ToolsPanel.this.nonCultivable){
				this.tl.setSoltype("NonCultivable");
			}
			else if (source==ToolsPanel.this.horsJardin){
				this.tl.setSoltype("HorsJardin");
			}
			System.out.println(this.tl.getSoltype());
		}

	}

	public static void main(String[] args){
		JFrame f = new JFrame();
		f.add(new ToolsPanel(new JTerrainMap(new Jardin(8, 6))));
		f.pack();
		f.setVisible(true);
	}
}
