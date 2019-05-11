package View;

import Controller.ApplicationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyWindow extends ListingWindow {
    private ApplicationController controller;
    private JButton modifyButt;

    public ModifyWindow(){
        modifyButt = new JButton("Modifier");
        this.add(modifyButt, BorderLayout.SOUTH);

       ModifyButtonListener deleteButtonListener = new ModifyButtonListener();
        modifyButt.addActionListener(deleteButtonListener);
    }

    public void dispose(){
        this.dispose();
    }

    private class ModifyButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event){
            controller = new ApplicationController();

            /*int selectLine = getTable().getSelectedRow();
            String request = "DELETE FROM trajet WHERE identifiant = " + getTable().getModel().getValueAt(selectLine,0)+";";
            try{
                //controller.removeTrajet(request);
                //((AllTrajetModel)getTable().getModel()).removeRow(selectLine);

            }
            catch(Exception exception){
                JOptionPane.showMessageDialog (null, exception.getMessage(), "Exception SQL", JOptionPane.ERROR_MESSAGE);
            }*/
        }
    }
}
