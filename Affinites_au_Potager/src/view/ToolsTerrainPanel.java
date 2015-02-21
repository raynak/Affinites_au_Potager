package view;

import java.awt.Dimension;
import java.awt.GridLayout;
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

import controler.TerrainListener;
import controler.ZoomListener;
import model.Jardin;

public class ToolsTerrainPanel extends JPanel {

	private static final long serialVersionUID = -361628317248498679L;

	private JRadioButton horsJardin;
	private JRadioButton cultivable;
	private JRadioButton nonCultivable;
	private JButton zoomIn;
	private JButton zoomOut;

	public ToolsTerrainPanel(JTerrainMap j) {
		this.setPreferredSize(new Dimension(100, 600));
		this.setLayout(new GridLayout(6, 1));
		this.zoomIn = new JButton(new ImageIcon("images/Zoom-In-icon.png"));
		this. zoomOut = new JButton(new ImageIcon("images/Zoom-Out-icon.png"));
		this.zoomIn.addActionListener(new ZoomListener(j, this));
		this.zoomOut.addActionListener(new ZoomListener(j, this));

		JPanel zoomPanel = new JPanel(new GridLayout(1,2));
		zoomPanel.add(this.zoomIn);
		zoomPanel.add(this.zoomOut);
		this.add(zoomPanel);
		
		this.add(new JLabel("Outils Terrain"));
		
		JPanel tp = new JPanel();
		tp.setLayout(new GridLayout(2,2));
		tp.add(new JButton(new ImageIcon("images/cursor_arrow.png")));
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
		TypeTerrainListener ttl = new TypeTerrainListener(j.getTl());
		this.horsJardin.addActionListener(ttl);
		this.cultivable.addActionListener(ttl);
		this.nonCultivable.addActionListener(ttl);
		this.add(tp);
		
		this.add(new JLabel("Outils Plantation"));
		
		this.add(new JLabel("Outils Culture"));
	}

	public ToolsTerrainPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public ToolsTerrainPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public ToolsTerrainPanel(LayoutManager layout, boolean isDoubleBuffered) {
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

	private class TypeTerrainListener implements ActionListener {

		private TerrainListener tl;

		public TypeTerrainListener(TerrainListener tl){
			this.tl = tl;
		}
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source == ToolsTerrainPanel.this.cultivable){
				this.tl.setSoltype("CULTIVABLE");
			}
			else if (source==ToolsTerrainPanel.this.nonCultivable){
				this.tl.setSoltype("NONCULTIVABLE");
			}
			else if (source==ToolsTerrainPanel.this.horsJardin){
				this.tl.setSoltype("HORSJARDIN");
			}
		}

	}

	public static void main(String[] args){
		JFrame f = new JFrame();
		f.add(new ToolsTerrainPanel(new JTerrainMap(new Jardin(8, 6))));
		f.pack();
		f.setVisible(true);
	}
}
