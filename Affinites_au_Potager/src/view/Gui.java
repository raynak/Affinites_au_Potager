package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controler.KeyboardListener;
import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;
import model.Jardin;

public class Gui {

	private JFrame framePrincipale;
	private ToolsTerrainPanel tools;
	private JTerrainMap terrainPanel;
	
	
	public Gui(Jardin j) {
		this.framePrincipale = new JFrame();
		this.terrainPanel = new JTerrainMap(j);	
		JPanel terrainFrame = new JPanel();
		terrainFrame.setPreferredSize(new Dimension(800,600));
		this.terrainPanel.setPreferredSize(new Dimension(800,600));
		terrainFrame.add(this.terrainPanel);
		this.tools = new ToolsTerrainPanel(this.terrainPanel);
		this.framePrincipale.setLayout(new BorderLayout());
		this.framePrincipale.add(this.tools, BorderLayout.WEST);
		this.framePrincipale.add(/*terrainPanel*/terrainFrame, BorderLayout.EAST);
		this.framePrincipale.addKeyListener(new KeyboardListener(this.terrainPanel));
		this.framePrincipale.setFocusable(true);
		this.framePrincipale.requestFocus();
		
		this.framePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;

	}


public static void main(String[] args) throws FileNotFoundException, GardenWrongDataFormatException, PlancheConstructorException{
	//Jardin j = new Jardin(15,20);
	Jardin j = new Jardin("jardin.txt");
	Gui g = new Gui(j);
	g.framePrincipale.pack();
	g.framePrincipale.setVisible(true);
	System.out.println(g.framePrincipale.isFocusOwner());
}
}
