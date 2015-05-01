package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.jardin.Case;
import model.jardin.CaseCultivable;
import model.jardin.Plante;
import view.JTerrainMap;

public class PlanteListener implements MouseListener{

	private Plante plante;
	private JTerrainMap map;
	
	public PlanteListener(Plante plante, JTerrainMap map){
		this.plante = plante;
		this.map = map;
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX()/this.map.getTailleCase();
		int y = arg0.getY()/this.map.getTailleCase();
		Case laCase = this.map.getTerrain().getTerrain()[x][y];
		if (laCase instanceof CaseCultivable){
			((CaseCultivable)laCase).setPlante(plante);
		}
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
