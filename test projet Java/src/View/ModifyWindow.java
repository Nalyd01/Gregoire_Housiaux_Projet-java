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
    private JScrollPane scroller;
    private ApplicationController controller;
    private Trajet trajet;

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
            int selectedRow = getTable().getSelectedRow();
            try {
                trajet = controller.getTrajet((int) getTable().getValueAt(selectedRow, 0));
            }catch (Exception e){
                e.printStackTrace();
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
