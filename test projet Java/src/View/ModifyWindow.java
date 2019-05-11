package View;


import Controller.ApplicationController;
import Tools.AllTrajetModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class ModifyWindow extends ListingWindow {
    private JButton modifyButt;
    private AppWindow appWindow;
    private ApplicationController controller;
    public ModifyWindow(AppWindow appWindow){
        this.appWindow = appWindow;
        modifyButt = new JButton("Modifier");
        this.add(modifyButt, BorderLayout.SOUTH);

        ModifyButtonListener deleteButtonListener = new ModifyButtonListener();
        modifyButt.addActionListener(deleteButtonListener);

    }

    private class ModifyButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event){

            int selectedRow = getTable().getSelectedRow();
            String id = String.valueOf(getTable().getValueAt(selectedRow,0));
            String nbKm = String.valueOf(getTable().getValueAt(selectedRow, 1));
            String nbPassager = String.valueOf(getTable().getValueAt(selectedRow,2));
            String matricule = String.valueOf(getTable().getValueAt(selectedRow, 3));
            String codePostal = String.valueOf(getTable().getValueAt(selectedRow, 4));
            String nomLocalite = String.valueOf(getTable().getValueAt(selectedRow, 5));
            String idClient = String.valueOf(getTable().getValueAt(selectedRow, 6));
            boolean panne = ((getTable().getValueAt(selectedRow,7)).equals("Non") ? false : true);
            boolean embouteillage = ((getTable().getValueAt(selectedRow,8)).equals("Non") ? false : true);

            JScrollPane scroller = new JScrollPane(new TrajectFormPanel(id,nbKm, nbPassager, panne, embouteillage, matricule, codePostal, nomLocalite, idClient, appWindow));
            appWindow.getFrameContainer().removeAll();
            appWindow.getFrameContainer().add(scroller, BorderLayout.CENTER);
            appWindow.getFrameContainer().repaint();
            appWindow.setVisible(true);

            controller = new ApplicationController();
            int selectLine = getTable().getSelectedRow();
            String request = "DELETE FROM trajet WHERE identifiant = " + getTable().getModel().getValueAt(selectLine,0)+";";
            try{
                controller.removeTrajet(request);
                ((AllTrajetModel)getTable().getModel()).removeRow(selectLine);
            }
            catch(SQLException exception){
                JOptionPane.showMessageDialog (null, exception.getMessage(), "Exception SQL", JOptionPane.ERROR_MESSAGE);
            }

            ModifyWindow.this.dispose();

        }
    }
}
