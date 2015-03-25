package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class ChoixPlanteOnglet extends JPanel {

	
	Gui gui;
	JPanel listeCheck;
	ArrayList<String> choixPlante;
	ArrayList<JCheckBox> checkBoxes;
	
	public JPanel getListeCheck() {
		return listeCheck;
	}


	public void setListeCheck(JPanel listeCheck) {
		this.listeCheck = listeCheck;
	}

	public  ChoixPlanteOnglet() throws SAXException, IOException, ParserConfigurationException{
		//this.gui = gui;
		this.checkBoxes = new ArrayList<JCheckBox>();
		this.choixPlante = new ArrayList<String>();
		
		JComboBox<String> listeAff = new JComboBox<String>();
		
		File dir = new File("data/affinites");
		File[] fileTab = dir.listFiles();
		for (File f : fileTab){
			listeAff.addItem(f.getName());
		}
		listeAff.addActionListener(new CheckListener());

		this.add(listeAff);
		JButton valider = new JButton("Valider");
		valider.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
for (JCheckBox cb : ChoixPlanteOnglet.this.checkBoxes){
	if (cb.isSelected()){
		ChoixPlanteOnglet.this.choixPlante.add(cb.getText());
		System.out.println(cb.getText());
	}
}
				}
				
			});
		this.add(valider);

		this.listeCheck = this.checkPanel((String)listeAff.getSelectedItem());

		this.add(listeCheck);
	}


	public JPanel checkPanel(String fileAff){
		JPanel checkPanel = new JPanel();
		
		int nbcheck = 0;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder loader = factory.newDocumentBuilder();
			Document document = loader.parse("data/affinites/"+fileAff);
			//System.out.println(document.getNodeName());
			NodeList nl = document.getElementsByTagName("plante");
			//NodeList nl = document.getChildNodes().item(0).getChildNodes();
			nbcheck = nl.getLength();
			//System.out.println(nbcheck+"-"+nl.item(0).toString());
			for (int i=1; i<nl.getLength(); i++){
				Node item = nl.item(i);
				NamedNodeMap nnm = item.getAttributes();
				//System.out.println(item.getNodeValue());
				//System.out.println(nnm.getNamedItem("nom").toString());
				String attr = (nnm.getNamedItem("nom").toString().split("="))[1].replace('"', ' ').trim();
				JCheckBox ckeck = new JCheckBox(attr);
				this.checkBoxes.add(ckeck);
				checkPanel.setLayout(new GridLayout(nbcheck/5, 10, 10, 10));
				checkPanel.add(ckeck);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(checkPanel.getComponentCount());
		return checkPanel;
	}

	private class CheckListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JComboBox<String> c = (JComboBox<String>)arg0.getSource();
			String fileAff = (String)c.getSelectedItem();
			System.out.println(fileAff);
			ChoixPlanteOnglet.this.choixPlante.removeAll(choixPlante);
			ChoixPlanteOnglet.this.checkBoxes.removeAll(checkBoxes);
			ChoixPlanteOnglet.this.remove(ChoixPlanteOnglet.this.listeCheck);
			ChoixPlanteOnglet.this.repaint();
			ChoixPlanteOnglet.this.setListeCheck(ChoixPlanteOnglet.this.checkPanel(fileAff));
			ChoixPlanteOnglet.this.add(ChoixPlanteOnglet.this.listeCheck);
			ChoixPlanteOnglet.this.revalidate();
			ChoixPlanteOnglet.this.listeCheck.repaint();

		}

	}

}
