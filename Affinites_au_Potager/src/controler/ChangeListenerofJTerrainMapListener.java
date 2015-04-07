package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

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
		else if (source == this.ttp.getSelectCase() || source == this.ttp.getOcreCase() || source == this.ttp.getWhiteCase() || source == this.ttp.getGreenCase()){
			System.out.println("tentative de caselistener");
			CaseListener cl = new CaseListener(map);
			if (source == this.ttp.getOcreCase()){
				cl.setSoltype("Cultivable");
			}
			else if (source == this.ttp.getWhiteCase()){
				cl.setSoltype("HorsJardin");
			}
			else if (source == this.ttp.getGreenCase()){
				cl.setSoltype("NonCultivable");
			}
			map.setTerrainListener(cl);
			this.ttp.fixNewTypeTerrainListener(cl);
		}
		else if (source == this.ttp.getFixeOrVariableButton()){
			PlanteListener pl = new PlanteListener(this.ttp.getPlanteAFixer(), this.map);
			this.map.setTerrainListener(pl);
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
