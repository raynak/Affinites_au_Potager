package view;

import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import controler.CaseListener;
import controler.InfoPlanteListener;
import model.Case;
import model.CaseCultivable;
import model.CaseNonCultivable;
import model.Jardin;
import model.Planche;
import model.Plante;
import model.ZonePlantation;


public class JTerrainMap extends JComponent {

	private static final long serialVersionUID = -8687259756826973846L;

	private Gui gui;
	private Jardin terrain;
	private int tailleCase;
	private Color color;
	private MouseListener terrainListener;
	private InfoPlanteListener infoPlanteListener;
	private Color[] planteColor;
	private Color[] zoneColor;
	private boolean showAffinites;

	private boolean showPlantesName;

	public JTerrainMap(Gui gui) {
		this.gui = gui;
		this.terrain = gui.getJardin();
		this.tailleCase = 20;
		this.showAffinites = false;
		this.infoPlanteListener = new InfoPlanteListener(gui);
		this.color = Color.green;
		this.planteColor = new Color[this.terrain.getPlantes().size()];
		this.zoneColor = new Color[this.terrain.getZones().size()];
		Random r = new Random();
		for (int i=0; i<this.planteColor.length; i++){
			this.planteColor[i] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		}
		for (int j=0; j<this.zoneColor.length; j++){
			this.zoneColor[j] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), 20);
		}
		this.terrainListener = new CaseListener(this);
		this.addMouseListener(terrainListener);
		this.upDatePreferredSize();
		System.out.println("plantecolor :"+this.planteColor.length);
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

	public boolean isShowAffinites() {
		return showAffinites;
	}

	public void setShowAffinites(boolean showAffinites) {
		this.showAffinites = showAffinites;
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
		this.changePlanteColor(j.getPlantes().size());
		this.repaint();
	}
	public Color[] getPlanteColor() {
		return planteColor;
	}

	public void changePlanteColor(int nbColor){
		this.planteColor = new Color[nbColor];
		Random r = new Random();
		for (int i=0; i<this.planteColor.length; i++){
			this.planteColor[i] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		}
	}

	public void changeZoneColor(int nbZone){
		this.zoneColor = new Color[nbZone];
		Random r = new Random();
		for (int i=0; i<this.zoneColor.length; i++){
			this.zoneColor[i] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		}
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

	/**
	 * Increase the visualization size of a Case
	 */
	public void taillecaseplus(){
		if (this.tailleCase < 80){
			this.tailleCase *= 2;
		}
		this.repaint();
		this.upDatePreferredSize();
	}

	/**
	 * Decrease the visualization size of a Case
	 */
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
		Case c = this.terrain.getTerrain()[y/this.tailleCase][x/this.tailleCase];
		try {
			this.color = c.getColor();
		}
		catch (Exception e){
			this.color = Color.white;
		}
		g.setColor(this.color);
		g.fillRect(y, x, this.tailleCase-1, this.tailleCase-1);


		for (int z=0; z<this.getTerrain().getZones().size(); z++){
			ZonePlantation zone = this.getTerrain().getZones().get(z);

			//			g.setColor(this.zoneColor[z]);
			//			zone.paintFieldZone(g, this.tailleCase);
			//			
			for (Planche p : zone.getPlanches()){
				p.paintFieldPlanche(g, this.tailleCase);
			}
		}
		if (c.getHasPlant()){
			Plante plante = ((CaseCultivable)c).getPlante();
			g.setColor(this.gui.getPlanteColor(plante));
			int tailleCircle = this.tailleCase/2;
			if (((CaseCultivable)c).isVariable()){
				g.fillOval(y+tailleCircle/2, x+tailleCircle/2, tailleCircle, tailleCircle);
			}
			else {
				g.drawOval(y+tailleCircle/2, x+tailleCircle/2, tailleCircle, tailleCircle);

			}
		}
		if (this.showAffinites){
			this.terrain.paintRelationBetweenPlante(g, this.tailleCase);
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

		Plante carotte = Plante.getInstanceOf("Carotte");
		Plante oignon = Plante.getInstanceOf("Oignon");
		Plante ail = Plante.getInstanceOf("Ail");
		Plante chou = Plante.getInstanceOf("Chou");
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

		f.setVisible(true);
	}

	public void setShowPlantesName(boolean b) {
		this.showPlantesName = b;
	}

	public boolean isShowPlantesName() {
		return showPlantesName;
	}

	public void showPlantesName(){
		if (this.getMouseMotionListeners().length == 0){
			System.out.println("ajout listener info");
			this.addMouseMotionListener(infoPlanteListener);
		}
		else {
			System.out.println("suppression listener info");
			this.removeMouseMotionListener(infoPlanteListener);
		}
	}
}




