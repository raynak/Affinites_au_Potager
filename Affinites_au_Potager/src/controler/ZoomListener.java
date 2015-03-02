package controler;

import view.JTerrainMap;
import view.ToolsPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZoomListener implements ActionListener {

	private JTerrainMap jt;
	private ToolsPanel tools;

	public ZoomListener(JTerrainMap jt, ToolsPanel tools) {
		this.jt = jt;
		this.tools = tools;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source==this.tools.getZoomIn()){
			System.out.println("zoom plus");
			this.jt.taillecaseplus();
		}
		else if (source==this.tools.getZoomOut()){
			this.jt.taillecasemoins();
		}
	}

}
