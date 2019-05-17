package View;

import Controller.ApplicationController;
import Business.TripCost;
import Model.Trajet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TripCostWindow extends ListingWindow {
    private JButton modifyButt;
    private AppWindow appWindow;
    private ApplicationController controller;
    private Trajet trajet;
    private double prix;

    public TripCostWindow(AppWindow appWindow){
        this.appWindow = appWindow;
        controller = new ApplicationController();

        modifyButt = new JButton("Calculer le prix de ce trajet");
        this.add(modifyButt, BorderLayout.SOUTH);
        getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ModifyButtonListener deleteButtonListener = new ModifyButtonListener();
        modifyButt.addActionListener(deleteButtonListener);

    }

    private class ModifyButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event){
            int selectedRow = getTable().getSelectedRow();
            try {
                trajet = controller.getTrajetById((int) getTable().getValueAt(selectedRow, 0));
                prix = TripCost.getCost(trajet);
                JOptionPane.showMessageDialog(null, "Le prix de ce trajet est de " + prix + "€" , "Calcul du prix de ce trajet", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e){
                JOptionPane.showMessageDialog (null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }


        }
    }
}
