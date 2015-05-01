package controler;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import view.Gui;
import view.JTerrainMap;


public class CaseListener implements MouseListener {

	private Gui gui;
	private JTerrainMap jterrainmap;
	private Point start;
	private String soltype;

	public CaseListener(JTerrainMap j){
		this.jterrainmap = j;
		this.soltype = "HorsJardin";
	}

	public CaseListener(Gui gui){
		this.gui = gui;
		this.soltype = "HorsJardin";
	}

	/**
	 * @return the soltype
	 */
	public String getSoltype() {
		return soltype;
	}

	/**
	 * @param soltype the soltype to set
	 */
	public void setSoltype(String soltype) {
		this.soltype = soltype;
		System.out.println("soltype par set :"+soltype);
	}



	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
		System.out.println("clic"+this.soltype);
		int tailleCase = this.gui.getTerrainPanel().getTailleCase();

		int x = paramMouseEvent.getX()/tailleCase;
		int y = paramMouseEvent.getY()/tailleCase;
		System.out.println("abscisse "+x+" ordonnee "+y);
		this.gui.setCase(x, y, this.soltype);
		//		try{
		//			System.out.println("sol "+this.soltype);
		//			this.jterrainmap.getTerrain().setCase(x, y, soltype);
		//			//System.out.println("case "+this.jterrainmap.getTerrain().getTerrain()[y][x].typeString()/*0.getSoltype()*/);
		//			this.jterrainmap.repaint();	
		//		}
		//		catch(Exception e){
		//			e.printStackTrace();		}
	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.start = new Point(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("released");
		int tailleCase = this.gui.getTerrainPanel().getTailleCase();
		Point end = new Point(e.getX(), e.getY());
		this.gui.setCases(start.x/tailleCase, end.x/tailleCase, start.y/tailleCase, end.y/tailleCase, this.soltype);
	}

	public String toString(){
		return("Listener de type CaseListener");
	}

}


