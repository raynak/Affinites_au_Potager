package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import exceptions.PlancheNonValideException;
import model.jardin.Jardin;
import model.jardin.Planche;
import view.JTerrainMap;


public class PlancheSuppListener implements MouseListener {

	private JTerrainMap jterrainmap;

	public PlancheSuppListener(JTerrainMap j){
		this.jterrainmap = j;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int tailleCase = this.jterrainmap.getTailleCase();
		int x = e.getX()/tailleCase;
		int y = e.getY()/tailleCase;
		Jardin j = this.jterrainmap.getTerrain();
		Planche pl = j.getCase(x, y).getPlanche(j);
		try {
			j.supprimerPlanche(pl);
		} catch (PlancheNonValideException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.jterrainmap.repaint();
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
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public String toString(){
		return("Listener de type PlancheListener");
	}

}


