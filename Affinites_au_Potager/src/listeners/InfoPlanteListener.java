package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import view.Gui;

public class InfoPlanteListener implements MouseMotionListener {

	private Gui gui;

	public InfoPlanteListener(Gui gui){
		this.gui = gui;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		this.gui.infoPlante(x, y);
	}



}
