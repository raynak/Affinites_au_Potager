package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.jardin.Plante;
import view.Gui;

public class FixOrVariableListener implements MouseListener {

	private Gui gui;
	
	public FixOrVariableListener(Gui gui){
		this.gui = gui;	
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		int tailleCase = this.gui.getTerrainPanel().getTailleCase();
		int absCase = event.getX()/tailleCase;
		int ordoCase = event.getY()/tailleCase;
		Plante plante = this.gui.getPlanteAFixer();
		this.gui.changeCaseToFixOrVariable(absCase, ordoCase, plante);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
