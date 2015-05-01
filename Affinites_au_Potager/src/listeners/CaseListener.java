package listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import view.Gui;

public class CaseListener implements MouseListener {

	private Gui gui;
	private Point start;
	private String soltype;


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
	}



	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
		int tailleCase = this.gui.getTerrainPanel().getTailleCase();

		int x = paramMouseEvent.getX()/tailleCase;
		int y = paramMouseEvent.getY()/tailleCase;
		this.gui.setCase(x, y, this.soltype);
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
		int tailleCase = this.gui.getTerrainPanel().getTailleCase();
		Point end = new Point(e.getX(), e.getY());
		this.gui.setCases(start.x/tailleCase, end.x/tailleCase, start.y/tailleCase, end.y/tailleCase, this.soltype);
	}

	public String toString(){
		return("Listener de type CaseListener");
	}

}


