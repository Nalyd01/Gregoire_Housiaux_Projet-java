package View;

import Constroller.ApplicationController;
import View.ListingWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Research3Panel extends JPanel {
    private JLabel chauffeur, dateDébut, dateFin;
    private JComboBox comboBoxChauffeurs;
    private ApplicationController controller;
    private JSpinner pointDépart, pointFin;
    private SpinnerDateModel model;
    private JSpinner.DateEditor editor;
    private String selectChauffeur;
    private String dateD, dateF;
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
            Collections.sort(list);
            comboBoxChauffeurs = new JComboBox(list.toArray());
            comboBoxChauffeurs.setEditable(false);
            this.add(comboBoxChauffeurs);

                ComboBoxChauffeursListener listenerBox = new ComboBoxChauffeursListener();
                comboBoxChauffeurs.addItemListener(listenerBox);
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
            editor = new JSpinner.DateEditor(pointDépart,"yyyy-mm-dd hh:mm:ss");
            pointDépart.setEditor(editor);
            this.add(pointDépart);

                JSpinnerDébutListener listenerDébut = new JSpinnerDébutListener();
                pointDépart.addChangeListener(listenerDébut);


        dateFin = new JLabel("Temps de fin : ");
        dateFin.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(dateFin);

            pointFin = new JSpinner();
            model = new SpinnerDateModel();
            pointFin.setModel(model);
            editor = new JSpinner.DateEditor(pointFin,"yyyy-mm-dd hh:mm:ss");
            pointFin.setEditor(editor);
            this.add(pointFin);

                JSpinnerFinListener listenerFin = new JSpinnerFinListener();
                pointFin.addChangeListener(listenerFin);

        researchButton = new JButton("Rechercher");
        this.add(researchButton);

        ResearchListener listener = new ResearchListener();
        researchButton.addActionListener(listener);
    }

    private class ComboBoxChauffeursListener implements ItemListener{

        public void itemStateChanged(ItemEvent event){
            selectChauffeur = comboBoxChauffeurs.getSelectedItem().toString();
        }
    }

    private class JSpinnerDébutListener implements ChangeListener {

        public void stateChanged(ChangeEvent event){
            dateD = pointDépart.getValue().toString();
        }
    }

    private class JSpinnerFinListener implements ChangeListener{

        public void stateChanged(ChangeEvent event){
            dateF = pointDépart.getValue().toString();
        }
    }

    private class ResearchListener implements ActionListener {

        public void actionPerformed(ActionEvent event){
            new ListingWindow(selectChauffeur, dateD, dateF);
        }
    }
}
