package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.JTerrainMap;
import view.ToolsPanel;

public class ChangeListenerofJTerrainMapListener implements MouseListener {

	private ToolsPanel ttp;
	private JTerrainMap map;
	
	public ChangeListenerofJTerrainMapListener(ToolsPanel toolsPanel, JTerrainMap j){
		this.ttp = toolsPanel;
		this.map = j;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Object source = arg0.getSource();
		if (source == this.ttp.getDefinePlancheButton()){
			System.out.println("tentative de plancheListerner");
			map.setTerrainListener(new PlancheListener(map));
		} 
		else if (source == this.ttp.getSelectCase()){
			System.out.println("tentative de caselistener");

			map.setTerrainListener(new CaseListener(map));
		}
		System.out.println(map.getTerrainListener().toString());

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
