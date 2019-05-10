import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PanelInscriptionForm extends JPanel {
    private JLabel idLabel, kmLabel, nbPassagersLabel, chauffeurLabel, localiteLabel, clientLabel, panneLabel, embouteillageLabel, hArriveeLabel, hDepartLabel;
    private JTextField idText, kmText, nbPassagersText;
    private JComboBox comboBoxChauffeurs, comboBoxLocalites, comboBoxClients;
    private JRadioButton panne, notPanne, embouteillage, notEmbouteillage;
    private ButtonGroup jRadioButtonGroup;
    private ApplicationController controller;
    private ArrayList list;
    private JSpinner pointDépart, pointFin;
    private SpinnerDateModel model;
    private JSpinner.DateEditor editor;
    private String[] tabClients, tabLocalites;
    private JButton insert;
    private Trajet newTrajet;
    private String espace;

    public PanelInscriptionForm(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        controller = new ApplicationController();

        try {

            list = controller.getChauffeurs();
            Collections.sort(list);
            comboBoxChauffeurs = new JComboBox(list.toArray());
            Collections.sort(controller.getChauffeurs());
            comboBoxChauffeurs.setEditable(false);

            list = controller.getLocalite();
            tabLocalites = new String[list.size() / 2];
            concat(tabLocalites);
            Arrays.sort(tabLocalites);
            comboBoxLocalites = new JComboBox(tabLocalites);
            comboBoxLocalites.setEditable(false);

            list = controller.getClient();
            tabClients = new String[list.size() / 2];
            concat(tabClients);
            Arrays.sort(tabClients);
            comboBoxClients = new JComboBox(tabClients);
            comboBoxClients.setEditable(false);

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }

        idLabel = new JLabel("Identifiant du trajet : ");
        idLabel.setToolTipText("L'identifiant doit être composé de maximum 6 chiffres");
        this.add(idLabel);

        idText = new JFormattedTextField();
            this.add(idText);
        idText.addActionListener(new TextListener());


        kmLabel = new JLabel("Nombre de km parcourus : ");
        kmLabel.setToolTipText("A partir du point de départ jusqu'à l'arrivée");
        this.add(kmLabel);

            kmText = new JTextField();
            this.add(kmText);
        kmText.addActionListener(new TextListener());

        nbPassagersLabel = new JLabel("Nombre de passagers : ");
        this.add(nbPassagersLabel);

            nbPassagersText = new JTextField();
            this.add(nbPassagersText);
        nbPassagersText.addActionListener(new TextListener());

        chauffeurLabel = new JLabel("Nom du chauffeur : ");
        this.add(chauffeurLabel);

        this.add(comboBoxChauffeurs);
        comboBoxChauffeurs.addItemListener(new ComboBoxListener());

        localiteLabel = new JLabel("Nom de la localité : ");
        this.add(localiteLabel);

        this.add(comboBoxLocalites);
        comboBoxLocalites.addItemListener(new ComboBoxListener());

        clientLabel = new JLabel("Nom et prénom du client : ");
        this.add(clientLabel);

        this.add(comboBoxClients);
        comboBoxClients.addItemListener(new ComboBoxListener());

        jRadioButtonGroup = new ButtonGroup();

        jRadioButtonGroup.add(panne);
        jRadioButtonGroup.add(notPanne);
        jRadioButtonGroup.add(embouteillage);
        jRadioButtonGroup.add(notEmbouteillage);

        panneLabel = new JLabel("Est-ce qu'il y a eu une panne : ");
        panneLabel.setToolTipText("Cette ligne est facultative");
        this.add(panneLabel);

        panne = new JRadioButton("oui");
        panne.addItemListener(new JRadioButtonListener());
        notPanne = new JRadioButton("non");
        notPanne.addItemListener(new JRadioButtonListener());

        this.add(panne);
        this.add(notPanne);

        embouteillageLabel = new JLabel("Est-ce qu'il y a eu un embouteillage : ");
        this.add(embouteillageLabel);

        embouteillage = new JRadioButton("oui");
        embouteillage.addItemListener(new JRadioButtonListener());
        notEmbouteillage = new JRadioButton("non");
        notEmbouteillage.addItemListener(new JRadioButtonListener());

        this.add(embouteillage);
        this.add(notEmbouteillage);

        hDepartLabel = new JLabel("Date et heure de départ du trajet : ");
        this.add(hDepartLabel);

        pointDépart = new JSpinner();
        model = new SpinnerDateModel();
        pointDépart.setModel(model);
        editor = new JSpinner.DateEditor(pointDépart, "dd-MM-yyyy HH:mm");
        pointDépart.setEditor(editor);
        this.add(pointDépart);
        pointDépart.addChangeListener(new JSpinnerListener());

        hArriveeLabel = new JLabel("Date et heure d'arrivée du trajet : ");
        this.add(hArriveeLabel);

        pointFin = new JSpinner();
        model = new SpinnerDateModel();
        pointFin.setModel(model);
        editor = new JSpinner.DateEditor(pointFin, "dd-MM-yyyy HH:mm");
        pointFin.setEditor(editor);
        this.add(pointFin);
        pointFin.addChangeListener(new JSpinnerListener());

        insert = new JButton("Insérer le trajet dans la base de données");
        this.add(insert);
        insert.addActionListener(new InsertListener());
    }

    public void concat(String[] tab) {
        int a = 0;
        int b = 0;
        int c = 1;
        while (a < tab.length) {
            tab[a] = list.get(b) + " " + list.get(c);
            a++;
            b += 2;
            c += 2;
        }
    }

    public String récupAvantEspace(String chaine) {
        espace = " ";
        chaine.substring(0, chaine.indexOf(espace));
        return chaine;
    }

    private class TextListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            try {
                if (event.getSource() == idText) {
                    newTrajet.setIdentifiant(Integer.parseInt(event.getActionCommand()));
                }
                if (event.getSource() == kmText) {
                    newTrajet.setNbKm(Integer.parseInt(event.getActionCommand()));
                }
                if (event.getSource() == nbPassagersText) {
                    newTrajet.setNbPassagers(Integer.parseInt(event.getActionCommand()));
                }
            } catch (ValeurException valeurException) {
                JOptionPane.showMessageDialog(null, valeurException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ComboBoxListener implements ItemListener {

        public void itemStateChanged(ItemEvent event) {
            try {
                if (event.getSource() == comboBoxChauffeurs) {
                    String chauffeur = comboBoxChauffeurs.getSelectedItem().toString();
                    newTrajet.setMatricule(controller.getChauffeurMatricule(chauffeur));
                } else {
                    if (event.getSource() == comboBoxLocalites) {
                        String selectLocalite = comboBoxLocalites.getSelectedItem().toString();
                        récupAvantEspace(selectLocalite);
                        int codePostal = Integer.parseInt(selectLocalite);
                        newTrajet.setCodePostal(codePostal);
                    } else {
                        String selectClient = comboBoxClients.getSelectedItem().toString();
                        récupAvantEspace(selectClient);
                        int client_id = Integer.parseInt(selectClient);
                        newTrajet.setClient_id(client_id);
                    }
                }
            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
            } catch (IdException idException) {
                JOptionPane.showMessageDialog(null, idException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
            } catch (CodePostalException codePostalException) {
                JOptionPane.showMessageDialog(null, codePostalException.getMessage(), "Erreur sur le code postal", JOptionPane.ERROR_MESSAGE);
            } catch (ValeurException valeurException) {
                JOptionPane.showMessageDialog(null, valeurException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class JRadioButtonListener implements ItemListener {

        public void itemStateChanged(ItemEvent event) {
            if (event.getSource() == panne) {
                newTrajet.setaEuPanne(true);
            } else {
                newTrajet.setaEuPanne(false);
            }
            if (event.getSource() == embouteillage) {
                newTrajet.setaEuEmbouteillage(true);
            } else {
                newTrajet.setaEuEmbouteillage(false);
            }
        }
    }

    private class JSpinnerListener implements ChangeListener {

        public void stateChanged(ChangeEvent event) {
            try {
                if (event.getSource() == pointDépart) {
                    newTrajet.setHeureArrivee((Timestamp) pointDépart.getValue());
                }
                if (event.getSource() == pointFin) {
                    newTrajet.setHeureDepart((Timestamp) pointFin.getValue());
                }
            } catch (TimeException timeException) {
                JOptionPane.showMessageDialog(null, timeException.getMessage(), "Erreur sur l'heure", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class InsertListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            try {
                controller.insertTrajet(newTrajet);
            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
