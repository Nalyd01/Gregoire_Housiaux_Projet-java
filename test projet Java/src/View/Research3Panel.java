package View;

import Controller.ApplicationController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

public class Research3Panel extends JPanel {
    private JLabel chauffeur, dateDébut, dateFin;
    private JComboBox comboBoxChauffeurs;
    private ApplicationController controller;
    private JSpinner pointDépart, pointFin;
    private SpinnerDateModel model;
    private JSpinner.DateEditor editor;
    private JButton researchButton;
    private ArrayList list;

    public Research3Panel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        chauffeur = new JLabel("Nom du chauffeur : ");
        chauffeur.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(chauffeur);

        controller = new ApplicationController();

        try{
            list = controller.getChauffeurs();
            comboBoxChauffeurs = new JComboBox(list.toArray());
            comboBoxChauffeurs.setEditable(false);
            this.add(comboBoxChauffeurs);

        }
        catch(SQLException exception){
            JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }

        dateDébut = new JLabel("Temps de départ : ");
        dateDébut.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(dateDébut);

        pointDépart = new JSpinner();
        model = new SpinnerDateModel();
        pointDépart.setModel(model);
        editor = new JSpinner.DateEditor(pointDépart,"dd-MM-yyyy HH:mm:ss");
        pointDépart.setEditor(editor);
        this.add(pointDépart);



        dateFin = new JLabel("Temps de fin : ");
        dateFin.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(dateFin);

        pointFin = new JSpinner();
        model = new SpinnerDateModel();
        pointFin.setModel(model);
        editor = new JSpinner.DateEditor(pointFin,"dd-MM-yyyy HH:mm:ss");
        pointFin.setEditor(editor);
        this.add(pointFin);

        this.add(Box.createRigidArea(new Dimension(10,10)));

        researchButton = new JButton("Rechercher");
        this.add(researchButton);

        ResearchListener listener = new ResearchListener();
        researchButton.addActionListener(listener);
    }


    private class ResearchListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            new ListingWindow(comboBoxChauffeurs.getSelectedItem().toString(), new Timestamp( ((Date)pointDépart.getValue()).getTime()),  new Timestamp( ((Date)pointFin.getValue()).getTime()));
        }
    }
}
