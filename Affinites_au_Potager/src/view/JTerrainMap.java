package view;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

import controler.KeyboardListener;
import controler.TerrainListener;
import model.Jardin;
import model.SolType;


public class JTerrainMap extends JComponent {

	private Jardin terrain;
	private int tailleCase;
	private Color color;
	private TerrainListener tl;

	public JTerrainMap(Jardin terrain) {
		this.terrain = terrain;
		this.tailleCase = 20;
		this.color = Color.green;
		this.tl = new TerrainListener(this);
		this.addMouseListener(tl);
	//	this.addKeyListener(new KeyboardListener(this));
	/*	this.setFocusable(true);
		this.requestFocus();*/
	}

	/**
	 * @return the tailleCase
	 */
	public int getTailleCase() {
		return tailleCase;
	}


	/**
	 * @param tailleCase the tailleCase to set
	 */
	public void setTailleCase(int tailleCase) {
		this.tailleCase = tailleCase;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the terrain
	 */
	public Jardin getTerrain() {
		return terrain;
	}

	/**
	 * @return the tl
	 */
	public TerrainListener getTl() {
		return tl;
	}

	public void taillecaseplus(){
		if (this.tailleCase < 80){
			this.tailleCase *= 2;
		}
		this.repaint();
	}

	public void taillecasemoins(){
		if (this.tailleCase > 5){
			this.tailleCase /= 2;
		}
		this.repaint();
	}


	/** Paints this component. */
	protected void paintComponent(Graphics g) {
		int longueur = this.terrain.getTerrain().length;
		int largeur = this.terrain.getTerrain()[0].length;
		if (this.terrain == null) {
			super.paintComponent(g);
			return;
		}
		g.setColor(this.color);
		Rectangle rc = g.getClipBounds();
		Rectangle rf = new Rectangle(0, 0, this.tailleCase, this.tailleCase);
		for (int y = 0; y < longueur; y++, rf.x += this.tailleCase) {
			for (int x = 0; x < largeur; x++, rf.y += this.tailleCase) {
				//				System.out.println(""+x+" - "+y+" : "+this.terrain.getTerrain()[y][x].getSoltype());

				paintField(g, rf.y, rf.x);
			}
			rf.y = 0;
		}
	}

	protected void paintField(Graphics g, int x, int y) {
		if (this.terrain.getTerrain()[y/this.tailleCase][x/this.tailleCase].getSoltype() == SolType.CULTIVABLE){ 
			g.setColor(new Color(100, 60, 30));
		}
		else if (this.terrain.getTerrain()[y/this.tailleCase][x/this.tailleCase].getSoltype() == SolType.NONCULTIVABLE){ 
			g.setColor(new Color(60, 190 ,10));
		}
		else {
			g.setColor(Color.white);
		}
		g.fillRect(x, y, this.tailleCase-1, this.tailleCase-1);

	}




	protected void paintSelection(Graphics g) {
		/*if (this.position != null) {
			int o = (this.position.getXPosition() & 1) * hexheight2;
			g.drawImage(
					selectionImage,
					this.position.getXPosition() * hexwidth - 2 + 2,
					this.position.getYPosition() * hexheight + o - 1 + 2,
					this);
		}*/
		g.drawRect(0, 0, this.tailleCase, this.tailleCase);
	}

	/** Returns the component's preferred size. */
	public Dimension getPreferredSize() {
		if (this.terrain == null)
			return super.getPreferredSize();
		return new Dimension(
				this.terrain.getTerrain().length*20,
				this.terrain.getTerrain()[0].length*20);
	}

	 
	/** Queues a repaint event for the given field. Adds some pixel to support selections. */
	private void repaint(int x, int y) {
		repaint(
				x,
				y,
				5,
				5);
	}

	

	/*
	public void changeImage(Cell c, String s) {
		c.setGroundtype(GroundType.valueOf(s));
		this.repaint(c.getBoardPosition());
	}

	public void illumineCell(Cell c) {
		c.setHighlight(true);
		this.repaint(c.getBoardPosition());
	}
	 */





	public static void main(String[] arguments) throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException {
		JFrame f = new JFrame("Jardin");
		f.setSize(1000, 600);
		System.out.println("1");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("2");
		Jardin j = new Jardin(8, 6);
		j.getTerrain()[2][2].setSoltype(SolType.NONCULTIVABLE);
		System.out.println("type du terrain en 2 2 "+j.getTerrain()[2][2].getSoltype());
		System.out.println("3");
		JTerrainMap m = new JTerrainMap(j);
		//m.rotateGBoard();
		System.out.println("5");
		f.getContentPane().add(new JScrollPane(m));
		//f.pack();

		f.setVisible(true);
	}

}




