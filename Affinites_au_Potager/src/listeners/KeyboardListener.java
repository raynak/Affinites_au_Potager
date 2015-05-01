package listeners;

import view.JTerrainMap;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

	private JTerrainMap jterrain;

	public KeyboardListener(JTerrainMap jterrain) {
		this.jterrain = jterrain;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println("touche tapee");
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		System.out.println("touche tapee");
		System.out.println(arg0.getKeyCode());
		if (arg0.getKeyChar()=='+'){
			System.out.println(arg0.getKeyChar());
			this.jterrain.taillecaseplus();
		}
		else if (arg0.getKeyChar()=='-'){
			this.jterrain.taillecasemoins();
		}
		this.jterrain.repaint();

	}

	
}
