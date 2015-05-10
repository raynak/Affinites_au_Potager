package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.Gui;

public class SetVariableListener implements MouseListener {

	private Gui gui;
	
	public SetVariableListener(Gui gui){
		this.gui = gui;
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int tailleCase = this.gui.getTerrainPanel().getTailleCase();
		int x = arg0.getX()/tailleCase;
		int y = arg0.getY()/tailleCase;
		System.out.println(x+"-"+y);
		this.gui.setPlanteToVariable(x, y);
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
