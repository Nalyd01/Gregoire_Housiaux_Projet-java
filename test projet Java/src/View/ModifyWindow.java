package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyWindow extends ListingWindow {
    private JButton modifyButt;
    private AppWindow appWindow;
    private JScrollPane scroller;

    public ModifyWindow(AppWindow appWindow){
        this.appWindow = appWindow;

        modifyButt = new JButton("Modifier");
        this.add(modifyButt, BorderLayout.SOUTH);
        getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ModifyButtonListener deleteButtonListener = new ModifyButtonListener();
        modifyButt.addActionListener(deleteButtonListener);

    }

    private class ModifyButtonListener implements ActionListener{

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

            scroller = new JScrollPane(new TrajectFormPanel(id,nbKm, nbPassager, panne, embouteillage, matricule, codePostal, nomLocalite, idClient, appWindow));

            appWindow.getFrameContainer().removeAll();
            appWindow.getFrameContainer().add(scroller, BorderLayout.CENTER);
            appWindow.getFrameContainer().repaint();
            appWindow.setVisible(true);

            deleteTrajet();
            ModifyWindow.this.dispose();
        }
    }
}
