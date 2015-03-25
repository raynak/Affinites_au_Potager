package view;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.*;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import controler.CaseListener;
import model.Case;
import model.CaseCultivable;
import model.CaseNonCultivable;
import model.Jardin;
import model.Planche;
import model.Plante;
import model.ZonePlantation;


public class JTerrainMap extends JComponent {

	private static final long serialVersionUID = -8687259756826973846L;

	private Jardin terrain;
	private int tailleCase;
	private Color color;
	private MouseListener terrainListener;
	private Color[] planteColor;

	public JTerrainMap(Jardin terrain) {
		this.terrain = terrain;
		this.tailleCase = 20;
		this.color = Color.green;
		this.planteColor = new Color[this.terrain.getPlantes().size()];
		Random r = new Random();
		for (int i=0; i<this.planteColor.length; i++){
			this.planteColor[i] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		}
		this.terrainListener = new CaseListener(this);
		this.addMouseListener(terrainListener);
		this.upDatePreferredSize();
		System.out.println(this.getPreferredSize());
		//	this.addKeyListener(new KeyboardListener(this));
		/*	this.setFocusable(true);
		this.requestFocus();*/
	}

	public void upDatePreferredSize(){
		System.out.println(this.tailleCase);
		this.setPreferredSize(new Dimension(this.terrain.getTerrain()[0].length*this.tailleCase,
				this.terrain.getTerrain().length*this.tailleCase));
		this.setSize(new Dimension(this.terrain.getTerrain()[0].length*this.tailleCase,
				this.terrain.getTerrain().length*this.tailleCase));

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

	public void setTerrain(Jardin j){
		this.terrain = j;
		this.repaint();
	}
	public Color[] getPlanteColor() {
		return planteColor;
	}

	/**
	 * @return the tl
	 */
	public MouseListener getTl() {
		return terrainListener;
	}

	public void setTerrainListener(MouseListener terrainListener) {
		this.removeMouseListener(this.terrainListener);
		this.terrainListener = terrainListener;
		this.addMouseListener(this.terrainListener);
		System.out.println(this.terrainListener.toString());
	}

	public MouseListener getTerrainListener() {
		return terrainListener;
	}

	public void taillecaseplus(){
		if (this.tailleCase < 80){
			this.tailleCase *= 2;
		}
		this.repaint();
		this.upDatePreferredSize();
	}

	public void taillecasemoins(){
		if (this.tailleCase > 5){
			this.tailleCase /= 2;
		}
		this.repaint();
		this.upDatePreferredSize();
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
		Color couleur;
		try {
			couleur = this.terrain.getTerrain()[y/this.tailleCase][x/this.tailleCase].getColor();
			this.color = couleur;
			//System.out.println(couleur.toString());
		}
		catch (Exception e){
			this.color = Color.white;
		}
		g.setColor(this.color);
		g.fillRect(y, x, this.tailleCase-1, this.tailleCase-1);
		Case c = this.terrain.getTerrain()[y/this.tailleCase][x/this.tailleCase];
		if (c instanceof CaseCultivable && ((CaseCultivable)c).getHasPlant()){
			Plante plante = ((CaseCultivable)c).getPlante();
			int index = this.terrain.getPlantes().indexOf(plante);
			System.out.println(this.terrain.getPlantes()+"-"+plante.getNom());
			g.setColor(this.planteColor[index]/*Color.yellow*/);
			int tailleCircle = this.tailleCase/2;
			g.fillOval(y+tailleCircle/2, x+tailleCircle/2, tailleCircle, tailleCircle);
		}

		for (ZonePlantation zone : this.terrain.getZones()){

			for (Planche p : zone.getPlanches()){
				p.paintFieldPlanche(g, this.tailleCase);
			}
			zone.paintFieldZone(g, this.tailleCase);
		}

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
				this.terrain.getTerrain().length*120,
				this.terrain.getTerrain()[0].length*120);
	}


	/** Queues a repaint event for the given field. Adds some pixel to support selections. */
	private void repaint(int x, int y) {
		repaint(
				x,
				y,
				5,
				5);
	}




	public static void main(String[] arguments) throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		JFrame f = new JFrame("Jardin");
		f.setSize(1000, 600);
		System.out.println("1");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("2");
		Jardin j = new Jardin(20, 16);
		j.getTerrain()[2][2] = new CaseNonCultivable(2,2);
		j.setCase(1, 2, "Cultivable");
		j.setCase(2, 2, "Cultivable");
		j.setCase(5, 8, "Cultivable");

		Plante carotte = new Plante("Carotte");
		Plante oignon = new Plante("Oignon");
		Plante ail = new Plante("Ail");
		Plante chou = new Plante("Chou");
		HashMap<String,Integer> affCarotte = new HashMap<String,Integer>();
		HashMap<String,Integer> affOignon = new HashMap<String,Integer>();
		HashMap<String,Integer> affAil = new HashMap<String,Integer>();
		HashMap<String,Integer> affChou = new HashMap<String,Integer>();
		affCarotte.put("Oignon", 1);
		affCarotte.put("Carotte", 0);
		affCarotte.put("Ail", -1);
		affCarotte.put("Chou", 1);

		affOignon.put("Oignon", 0);
		affOignon.put("Carotte", 1);
		affOignon.put("Ail", -1);
		affOignon.put("Chou", 1);

		affAil.put("Oignon", -1);
		affAil.put("Carotte", -1);
		affAil.put("Ail", 0);
		affAil.put("Chou", 1);

		affChou.put("Oignon", 1);
		affChou.put("Carotte", 1);
		affChou.put("Ail", 1);
		affChou.put("Chou", 0);
		carotte.setAffinites(affCarotte);
		chou.setAffinites(affChou);
		ail.setAffinites(affAil);
		oignon.setAffinites(affOignon);
		System.out.println("Carrotte a un nom "+carotte.getNom());
		LinkedList<Plante> listePlante = new LinkedList<Plante>();
		listePlante.add(carotte);
		listePlante.add(chou);
		listePlante.add(oignon);
		listePlante.add(ail);
		j.setPlantes(listePlante);

		j.getTerrain()[1][2].setPlante(j.getPlantes().get(0));

		j.getTerrain()[2][2].setPlante(j.getPlantes().get(1));
		j.getTerrain()[5][8].setPlante(j.getPlantes().get(3));


		System.out.println("type du terrain en 2 2 "+j.getTerrain()[2][2].typeString());
		System.out.println("3");
		JTerrainMap m = new JTerrainMap(j);
		JPanel p = new JPanel(new BorderLayout());
		p.add(m, BorderLayout.CENTER);
		//m.rotateGBoard();
		System.out.println("5");
		f.getContentPane().add(new JScrollPane(p), BorderLayout.CENTER);
		//f.pack();

		f.setVisible(true);
	}

}




