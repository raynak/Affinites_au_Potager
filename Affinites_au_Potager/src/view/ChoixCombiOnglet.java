package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class ChoixCombiOnglet extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1657093878185936722L;
	Gui gui;
	JPanel listeCheck;
	JComboBox<String> comboBox;
	LinkedList<String> choixCombi;


	public  ChoixCombiOnglet(Gui gui) throws SAXException, IOException, ParserConfigurationException{
		this.gui = gui;
		this.choixCombi = new LinkedList<String>();

		this.comboBox = new JComboBox<String>();

		this.comboBox.addItem("ModeleCombiGlouton");
		this.comboBox.addItem("ModeleCombiAlea");
		this.comboBox.addItem("ModeleCombiGloutonContraintes");
		this.comboBox.addItem("ModeleCombiGloutonQtes");

		this.add(this.comboBox);
		JButton valider = new JButton("Valider");
		valider.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
		//		ChoixCombiOnglet.this.gui.setModeleCombi((String)ChoixCombiOnglet.this.comboBox.getSelectedItem());
			}

		});
		this.add(valider);
	
	}

}
