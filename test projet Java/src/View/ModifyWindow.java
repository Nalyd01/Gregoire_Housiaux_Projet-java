package View;

import Controller.ApplicationController;
import Model.Trajet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyWindow extends ListingWindow {
    private JButton modifyButt;
    private AppWindow appWindow;
    private ApplicationController controller;
    private Trajet trajet;
    private int selectedRow;

    public ModifyWindow(AppWindow appWindow){
        this.appWindow = appWindow;
        controller = new ApplicationController();

        modifyButt = new JButton("Modifier");
        this.add(modifyButt, BorderLayout.SOUTH);
        getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ModifyButtonListener deleteButtonListener = new ModifyButtonListener();
        modifyButt.addActionListener(deleteButtonListener);

    }

    private class ModifyButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent event){
            selectedRow = getTable().getSelectedRow();

            if(selectedRow == -1){
                JOptionPane.showMessageDialog (null, "Vous devez sélectionner une ligne", "Erreur pour la modification", JOptionPane.ERROR_MESSAGE);
            } else{
                try {
                    trajet = controller.getTrajetById((int) getTable().getValueAt(selectedRow, 0));
                }catch (Exception e){
                    JOptionPane.showMessageDialog (null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                appWindow.afficheTrajectForm(trajet);

                ModifyWindow.this.dispose();
            }
        }
    }
}
