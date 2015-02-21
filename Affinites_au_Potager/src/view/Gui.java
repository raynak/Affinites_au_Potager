package view;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controler.KeyboardListener;
import model.Jardin;

public class Gui {

	private JFrame framePrincipale;
	private ToolsTerrainPanel tools;
	private JTerrainMap terrainPanel;
	
	
	public Gui(Jardin j) {
		this.framePrincipale = new JFrame();
		this.terrainPanel = new JTerrainMap(j);		
		this.tools = new ToolsTerrainPanel(this.terrainPanel);
		this.framePrincipale.setLayout(new BorderLayout());
		this.framePrincipale.add(this.tools, BorderLayout.WEST);
		this.framePrincipale.add(terrainPanel, BorderLayout.EAST);
		this.framePrincipale.addKeyListener(new KeyboardListener(this.terrainPanel));
		this.framePrincipale.setFocusable(true);
		this.framePrincipale.requestFocus();
	}


public static void main(String[] args){
	Jardin j = new Jardin(15,20);
	Gui g = new Gui(j);
	g.framePrincipale.pack();
	g.framePrincipale.setVisible(true);
	System.out.println(g.framePrincipale.isFocusOwner());
}
}
