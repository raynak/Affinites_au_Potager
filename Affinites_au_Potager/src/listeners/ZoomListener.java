package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Gui;
import view.ToolsPanel;

public class ZoomListener implements ActionListener {

	private Gui gui;
	private ToolsPanel tools;
	
	public ZoomListener(Gui gui, ToolsPanel tools) {
		this.gui = gui;
		this.tools = tools;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source==this.tools.getZoomIn()){
			gui.zoom(true);
		}
		else if (source==this.tools.getZoomOut()){
			gui.zoom(false);
		}
	}

}
