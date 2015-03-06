package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import controler.KeyboardListener;
import exceptions.GardenWrongDataFormatException;
import exceptions.PlancheConstructorException;
import exceptions.PlancheNonMitoyenneException;
import model.Jardin;

public class Gui {

	private JFrame framePrincipale;
	private ToolsPanel tools;
	private JTerrainMap terrainPanel;
	
	
	public Gui(Jardin j) {
		this.framePrincipale = new JFrame();
		this.terrainPanel = new JTerrainMap(j);	
		JPanel terrainFrame = new JPanel(new BorderLayout());
		System.out.println(this.terrainPanel.getPreferredSize());
		terrainFrame.setPreferredSize(this.terrainPanel.getSize());
		terrainFrame.setBorder(BorderFactory.createLineBorder(Color.green));
		
		
		//this.terrainPanel.setPreferredSize(new Dimension(800,600));
		terrainFrame.add(this.terrainPanel, BorderLayout.CENTER);
		JScrollPane scrollpane = new JScrollPane(terrainFrame, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	 scrollpane.setPreferredSize(new Dimension(800,600));
		this.tools = new ToolsPanel(this.terrainPanel);
		this.framePrincipale.setLayout(new FlowLayout());
		this.framePrincipale.add(this.tools/*, BorderLayout.WEST*/);
		//this.framePrincipale.add(/*terrainPanel*/terrainFrame, BorderLayout.EAST);
	    this.framePrincipale.add(/*terrainFrame/*this.terrainPanel*/scrollpane/*
	    , BorderLayout.EAST*/);

		this.framePrincipale.addKeyListener(new KeyboardListener(this.terrainPanel));
		this.framePrincipale.setFocusable(true);
		this.framePrincipale.requestFocus();
		
		this.framePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		

	}


public static void main(String[] args) throws FileNotFoundException, GardenWrongDataFormatException, PlancheConstructorException, PlancheNonMitoyenneException{
	Jardin j = new Jardin(28,18);
	
	//Jardin j = new Jardin("jardin.txt");
	System.out.println(j.toString());
	Gui g = new Gui(j);
	
	g.framePrincipale.pack();
	g.framePrincipale.setVisible(true);
	System.out.println(g.framePrincipale.isFocusOwner());
	

}
}
