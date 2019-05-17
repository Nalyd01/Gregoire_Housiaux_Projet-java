package View;

import Controller.ApplicationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Research1Panel extends JPanel {
    private JLabel localiteLabel, tempsDebut, tempsFin;
    private JComboBox comboBoxLocalites;
    private ApplicationController controller;
    private ArrayList listLocalites;
    private JSpinner pointDépart, pointFin;
    private SpinnerDateModel model;
    private JSpinner.DateEditor editor;
    private JButton research;

    public Research1Panel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controller = new ApplicationController();

        localiteLabel = new JLabel("Identifiant de la localité : ");
        localiteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(localiteLabel);

        try{
            listLocalites = controller.getAllLocalites();
            comboBoxLocalites = new JComboBox(listLocalites.toArray());
            comboBoxLocalites.setEditable(false);
            this.add(comboBoxLocalites);
        } catch(SQLException exception){
            JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }

        tempsDebut = new JLabel("Date et heure de départ : ");
        tempsDebut.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(tempsDebut);

        pointDépart = new JSpinner();
        model = new SpinnerDateModel();
        pointDépart.setModel(model);
        editor = new JSpinner.DateEditor(pointDépart,"dd-MM-yyyy HH:mm:ss");
        pointDépart.setEditor(editor);
        this.add(pointDépart);

        tempsFin = new JLabel("Date et heure de fin : ");
        tempsFin.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(tempsFin);

        pointFin = new JSpinner();
        model = new SpinnerDateModel();
        pointFin.setModel(model);
        editor = new JSpinner.DateEditor(pointFin,"dd-MM-yyyy HH:mm:ss");
        pointFin.setEditor(editor);
        this.add(pointFin);

        this.add(Box.createRigidArea(new Dimension(10,10)));

        research = new JButton("Rechercher");
        this.add(research);

        ResearchListener researchListener = new ResearchListener();
        research.addActionListener(researchListener);
    }

    private class ResearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new ListingWindow(new Timestamp( ((Date)pointDépart.getValue()).getTime()),  new Timestamp( ((Date)pointFin.getValue()).getTime()), comboBoxLocalites.getSelectedItem().toString());
        }
    }
}
