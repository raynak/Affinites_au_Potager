package view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Jardin;

@SuppressWarnings("serial")
public class NewGardenOptionPane extends JFrame {
	
	
    public NewGardenOptionPane(Jardin j)
    {
        JTextField longueurTerrain = new JTextField();
        JTextField largeurTerrain = new JTextField();
        Object[] msg = {"Longueur :", longueurTerrain, "Largeur :", largeurTerrain};
  
        JOptionPane op = new JOptionPane(
            msg,
            JOptionPane.QUESTION_MESSAGE,
            JOptionPane.OK_CANCEL_OPTION,
            null,
            null);
  
        JDialog dialog = op.createDialog(this, "Jardin");
        dialog.setVisible(true);
  
        int result = JOptionPane.OK_OPTION;
  
        try
        {
            result = ((Integer)op.getValue()).intValue();
        }
        catch(Exception uninitializedValue)
        {}
  
        if(result == JOptionPane.OK_OPTION)
        {
            System.out.println(longueurTerrain.getText() + " : " + largeurTerrain.getText());
            j = new Jardin(Integer.parseInt(longueurTerrain.getText()), Integer.parseInt(largeurTerrain.getText()));
        }
        else
        {
            System.out.println("Canceled");
        }
  
    }
  
  
    public static void main(String[] args)
    {
    	Jardin j = new Jardin();
        JFrame frame = new NewGardenOptionPane(j);
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.setAlwaysOnTop(true);
        frame.pack();
        frame.setVisible(true);
    }
}