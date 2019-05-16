package View;

import Controller.ApplicationController;
import Business.TacheMetier;
import Model.Trajet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TacheMetierWindow extends ListingWindow {
    private JButton modifyButt;
    private ApplicationController controller;
    private Trajet trajet;
    private double prix;
    private static TacheMetierWindow uniqueInstance;
    private static AppWindow appWindow;

    private TacheMetierWindow(AppWindow appWindow){
        this.appWindow = appWindow;
        controller = new ApplicationController();

        modifyButt = new JButton("Calculer le prix de ce trajet");
        this.add(modifyButt, BorderLayout.SOUTH);
        getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ModifyButtonListener deleteButtonListener = new ModifyButtonListener();
        modifyButt.addActionListener(deleteButtonListener);

    }

    public static TacheMetierWindow getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new TacheMetierWindow(appWindow);
        } else{
            JOptionPane.showMessageDialog (null, "Vous ne pouvez ouvrir qu'une seule fenêtre de calcul de coût d'un trajet à la fois", "Erreur à l'ouverture", JOptionPane.INFORMATION_MESSAGE);
        }
        return uniqueInstance;
    }

    private class ModifyButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event){
            int selectedRow = getTable().getSelectedRow();
            try {
                trajet = controller.getTrajetById((int) getTable().getValueAt(selectedRow, 0));
                prix = TacheMetier.getCost(trajet);
                JOptionPane.showMessageDialog(null, "Le prix de ce trajet est de " + prix + "€" , "Calcul du prix de ce trajet", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e){
                JOptionPane.showMessageDialog (null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }


        }
    }
}
