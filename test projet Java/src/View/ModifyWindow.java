package View;

import Controller.ApplicationController;
import Model.Trajet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyWindow extends ListingWindow {
    private JButton modifyButt;
    private JScrollPane scroller;
    private ApplicationController controller;
    private Trajet trajet;
    private static ModifyWindow uniqueInstance;
    private static AppWindow appWindow;

    private ModifyWindow(AppWindow appWindow){
        this.appWindow = appWindow;
        controller = new ApplicationController();

        modifyButt = new JButton("Modifier");
        this.add(modifyButt, BorderLayout.SOUTH);
        getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ModifyButtonListener deleteButtonListener = new ModifyButtonListener();
        modifyButt.addActionListener(deleteButtonListener);

    }

    public static ModifyWindow getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new ModifyWindow(appWindow);
        } else{
            JOptionPane.showMessageDialog (null, "Vous ne pouvez ouvrir qu'une seule fenêtre de modification à la fois", "Erreur à l'ouverture", JOptionPane.INFORMATION_MESSAGE);
        }
        return uniqueInstance;
    }

    private class ModifyButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent event){
            int selectedRow = getTable().getSelectedRow();

            if(selectedRow == -1){
                JOptionPane.showMessageDialog (null, "Vous devez sélectionner une ligne", "Erreur pour la modification", JOptionPane.ERROR_MESSAGE);
            } else{
                try {
                    trajet = controller.getTrajetById((int) getTable().getValueAt(selectedRow, 0));
                }catch (Exception e){
                    JOptionPane.showMessageDialog (null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                scroller = new JScrollPane(new TrajectFormPanel(trajet, appWindow));

                appWindow.getFrameContainer().removeAll();
                appWindow.getFrameContainer().add(scroller, BorderLayout.CENTER);
                appWindow.getFrameContainer().repaint();
                appWindow.setVisible(true);

                deleteTrajet();
                ModifyWindow.this.dispose();
            }
        }
    }
}
