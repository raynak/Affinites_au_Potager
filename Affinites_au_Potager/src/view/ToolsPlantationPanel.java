package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolsPlantationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1428948126973056861L;
	private JButton FixeOrVariableButton;
	private JButton definePlancheButton;

	public ToolsPlantationPanel() {
		JPanel toolsPanel = new JPanel(new BorderLayout());	
		toolsPanel.setPreferredSize(new Dimension(100,50));
		Image img = new ImageIcon("images/cursor_arrow_vf.png").getImage();
		Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		this.FixeOrVariableButton = new JButton(new ImageIcon(newImg));
		this.FixeOrVariableButton.setPreferredSize(new Dimension(50, 50));
		Image img2 = new ImageIcon("images/planche.png").getImage();
		Image newImg2 = img2.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
	
		this.definePlancheButton = new JButton(new ImageIcon(newImg2));
		this.definePlancheButton.setPreferredSize(new Dimension(50,50));

		toolsPanel.add(FixeOrVariableButton, BorderLayout.EAST);
		toolsPanel.add(definePlancheButton, BorderLayout.WEST);
		this.add(toolsPanel);
	}

	public ToolsPlantationPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public ToolsPlantationPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public ToolsPlantationPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
