package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import Tools.*;
import Constroller.ApplicationController;


public class TrajetForm extends JPanel {
    private JLabel idLabel, kmLabel, nbPassagersLabel, chauffeurLabel, localiteLabel, clientLabel, panneLabel, embouteillageLabel, hArriveeLabel, hDepartLabel;
    private JTextField idText, kmText, nbPassagersText;
    private JComboBox comboBoxChauffeurs, comboBoxLocalites, comboBoxClients;
    private JRadioButton panne, notPanne, embouteillage, notEmbouteillage;
    private ButtonGroup jRadioButtonGroupPanne, jRadioButtonGroupEmbouteillage;
    private ApplicationController controller;
    private ArrayList list;
    private JSpinner pointDépart, pointFin;
    private SpinnerDateModel model;
    private JSpinner.DateEditor editor;
    private JButton insert;
    private Trajet newTrajet;

    public TrajetForm(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        controller = new ApplicationController();

        try {
            list = controller.getChauffeurs() ;
            Collections.sort(list);
            comboBoxChauffeurs = new JComboBox(list.toArray());
            comboBoxChauffeurs.setEditable(false);

            list = controller.getLocalite();
            Collections.sort(list);
            comboBoxLocalites = new JComboBox(list.toArray());
            comboBoxLocalites.setEditable(false);

            list = controller.getClient();
            Collections.sort(list);
            comboBoxClients = new JComboBox(list.toArray());
            comboBoxClients.setEditable(false);

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }catch (Exception e){
            e.printStackTrace();
        }

        idLabel = new JLabel("Identifiant du trajet : ");
        idLabel.setToolTipText("L'identifiant doit être composé de maximum 6 chiffres");
        this.add(idLabel);

        idText = new JFormattedTextField();
            this.add(idText);


        kmLabel = new JLabel("Nombre de km parcourus : ");
        kmLabel.setToolTipText("A partir du point de départ jusqu'à l'arrivée");
        this.add(kmLabel);

            kmText = new JTextField();
            this.add(kmText);

        nbPassagersLabel = new JLabel("Nombre de passagers : ");
        this.add(nbPassagersLabel);

            nbPassagersText = new JTextField();
            this.add(nbPassagersText);

        chauffeurLabel = new JLabel("Nom du chauffeur : ");
        this.add(chauffeurLabel);

        this.add(comboBoxChauffeurs);

        localiteLabel = new JLabel("Nom de la localité : ");
        this.add(localiteLabel);

        this.add(comboBoxLocalites);

        clientLabel = new JLabel("Nom et prénom du client : ");
        this.add(clientLabel);

        this.add(comboBoxClients);

        panneLabel = new JLabel("Est-ce qu'il y a eu une panne : ");
        panneLabel.setToolTipText("Cette ligne est facultative");
        this.add(panneLabel);

        panne = new JRadioButton("oui");
        this.add(panne);
        notPanne = new JRadioButton("non", true);
        this.add(notPanne);
        jRadioButtonGroupPanne = new ButtonGroup();
        jRadioButtonGroupPanne.add(panne);
        jRadioButtonGroupPanne.add(notPanne);

        embouteillageLabel = new JLabel("Est-ce qu'il y a eu un embouteillage : ");
        this.add(embouteillageLabel);

        embouteillage = new JRadioButton("oui");
        this.add(embouteillage);
        notEmbouteillage = new JRadioButton("non",true);
        this.add(notEmbouteillage);
        jRadioButtonGroupEmbouteillage = new ButtonGroup();
        jRadioButtonGroupEmbouteillage.add(embouteillage);
        jRadioButtonGroupEmbouteillage.add(notEmbouteillage);

        hDepartLabel = new JLabel("Date et heure de départ du trajet : ");
        this.add(hDepartLabel);

        pointDépart = new JSpinner();
        model = new SpinnerDateModel();
        pointDépart.setModel(model);
        editor = new JSpinner.DateEditor(pointDépart, "dd-MM-yyyy HH:mm");
        pointDépart.setEditor(editor);
        this.add(pointDépart);

        hArriveeLabel = new JLabel("Date et heure d'arrivée du trajet : ");
        this.add(hArriveeLabel);

        pointFin = new JSpinner();
        model = new SpinnerDateModel();
        pointFin.setModel(model);
        editor = new JSpinner.DateEditor(pointFin, "dd-MM-yyyy HH:mm");
        pointFin.setEditor(editor);
        this.add(pointFin);

        insert = new JButton("Insérer le trajet dans la base de données");
        this.add(insert);
        insert.addActionListener(new InsertListener());
    }


    private class InsertListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            try {
                int idTrajet = Integer.parseInt(idText.getText());
                int nbkm = Integer.parseInt(kmText.getText());
                int nbPassager = Integer.parseInt(nbPassagersText.getText());
                int idChauffeur = Integer.parseInt(comboBoxChauffeurs.getSelectedItem().toString().substring(15, comboBoxChauffeurs.getSelectedItem().toString().indexOf(" ", 16)));
                int codePostal = Integer.parseInt(comboBoxLocalites.getSelectedItem().toString().substring(0, comboBoxLocalites.getSelectedItem().toString().indexOf(" ")));
                String localité = comboBoxLocalites.getSelectedItem().toString().substring(comboBoxLocalites.getSelectedItem().toString().indexOf(" ")+1);
                String strIdClient = comboBoxClients.getSelectedItem().toString().substring(4);
                strIdClient = strIdClient.substring(0, strIdClient.indexOf(" "));
                int idCLient = Integer.parseInt(strIdClient);
                Boolean aPanne = panne.isSelected();
                boolean aEmbouteillage = embouteillage.isSelected();
                Timestamp heureDépart = new Timestamp(((Date)pointDépart.getValue()).getTime());
                Timestamp heureFin = new Timestamp(((Date)pointFin.getValue()).getTime());

                newTrajet = new Trajet(idTrajet, nbkm,nbPassager, idChauffeur, codePostal, localité, idCLient, aPanne, aEmbouteillage, heureFin, heureDépart);

                controller.insertTrajet(newTrajet);

                JOptionPane.showMessageDialog(null, "Trajet Créer avec Succès", "Succès !", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}