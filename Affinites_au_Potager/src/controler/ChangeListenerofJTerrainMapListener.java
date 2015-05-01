package controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.Gui;
import view.JTerrainMap;
import view.ToolsPanel;

public class ChangeListenerofJTerrainMapListener implements MouseListener {

	private ToolsPanel ttp;
	private JTerrainMap map;
	private Gui gui;
	
	public ChangeListenerofJTerrainMapListener(ToolsPanel toolsPanel, Gui gui){
		this.ttp = toolsPanel;
		this.map = gui.getTerrainPanel();
		this.gui = gui;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Object source = arg0.getSource();
		if (source == this.ttp.getDefinePlancheButton()){
			System.out.println("tentative de plancheListerner");
			map.setTerrainListener(new PlancheListener(map));
		} 
		else if (source == this.ttp.getSupPlanche()){
			System.out.println("suppression de planche");
			map.setTerrainListener(new PlancheSuppListener(map));
		}
		else if (source == this.ttp.getSelectCase() || source == this.ttp.getOcreCase() || source == this.ttp.getWhiteCase() || source == this.ttp.getGreenCase()){
			System.out.println("tentative de caselistener");
			CaseListener cl = new CaseListener(gui);
			if (source == this.ttp.getOcreCase()){
				cl.setSoltype("Variable");
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
		else if (source == this.ttp.getDefineMultiPlancheHorizontal()){
			this.map.setTerrainListener(new PlancheMultiListener(map, true));
		}
		else if (source == this.ttp.getDefineMultiPlancheVertical()){
			this.map.setTerrainListener(new PlancheMultiListener(map, false));
		}
		else if (source == this.ttp.getFixeOrVariableButton()){
			this.map.setTerrainListener(new FixOrVariableListener(this.gui));
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
