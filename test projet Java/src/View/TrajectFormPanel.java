package View;
import Exception.TimeException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import Exception.*;

import Model.Trajet;
import Controller.ApplicationController;

public class TrajectFormPanel extends JPanel {
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
    private AppWindow appWindow;
    private int idTrajet, nbKm, nbPassagers, idChauffeur, codePostal, idCLient;
    private String localité, strIdClient;
    private Boolean aPanne, aEmbouteillage;
    private Timestamp heureDépart, heureFin;

    public TrajectFormPanel(Trajet trajet, AppWindow appWindow){
        this(appWindow);
        idText.setText(String.valueOf( trajet.getIdentifiant()));
        idText.setEditable(false);
        kmText.setText(String.valueOf( trajet.getNbKm()));
        nbPassagersText.setText(String.valueOf( trajet.getNbPassagers()));
        panne.setSelected(trajet.getaEuPanne());
        embouteillage.setSelected(trajet.getaEuEmbouteillage());

        try {
            setComboBoxSelection(comboBoxChauffeurs, controller.chauffeurById(trajet.getMatricule()));
            setComboBoxSelection(comboBoxLocalites, trajet.getCodePostal() + " " + trajet.getNomLocalite());
            setComboBoxSelection(comboBoxClients, controller.clientById(trajet.getClient_id()));
        }catch (Exception e){
            JOptionPane.showMessageDialog (null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }

        pointDépart.setValue(new Timestamp(trajet.getHeureDepart().getTime()));
        pointFin.setValue(new Timestamp(trajet.getHeureArrivee().getTime()));

    }


    public TrajectFormPanel(AppWindow appWindow){
        this.appWindow = appWindow;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        controller = new ApplicationController();

        try {
            list = controller.getAllChauffeurs() ;
            comboBoxChauffeurs = new JComboBox(list.toArray());
            comboBoxChauffeurs.setEditable(false);

            list = controller.getAllLocalites();
            comboBoxLocalites = new JComboBox(list.toArray());
            comboBoxLocalites.setEditable(false);

            list = controller.getAllClients();
            comboBoxClients = new JComboBox(list.toArray());
            comboBoxClients.setEditable(false);

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        idLabel = new JLabel("Identifiant du trajet : ");
        idLabel.setToolTipText("L'identifiant doit être composé de maximum 6 chiffres");
        this.add(idLabel);

        idText = new JFormattedTextField();
        idText.setEditable(false);

        try{
            idText.setText(controller.getNextIdTrajet());
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        this.add(idText);

        espace();

        kmLabel = new JLabel("Nombre de km parcourus : ");
        kmLabel.setToolTipText("A partir du point de départ jusqu'à l'arrivée");
        this.add(kmLabel);

        kmText = new JFormattedTextField();
        this.add(kmText);

        espace();

        nbPassagersLabel = new JLabel("Nombre de passagers : ");
        this.add(nbPassagersLabel);

        nbPassagersText = new JTextField();
        this.add(nbPassagersText);

        espace();

        chauffeurLabel = new JLabel("Nom du chauffeur : ");
        this.add(chauffeurLabel);

        this.add(comboBoxChauffeurs);

        espace();

        localiteLabel = new JLabel("Nom de la localité : ");
        this.add(localiteLabel);

        this.add(comboBoxLocalites);

        espace();

        clientLabel = new JLabel("Nom et prénom du client : ");
        clientLabel.setToolTipText("Personne qui paie le trajet");
        this.add(clientLabel);

        this.add(comboBoxClients);

        espace();

        panneLabel = new JLabel("Est-ce qu'il y a eu une panne : ");
        panneLabel.setToolTipText("Cette ligne est facultative");
        this.add(panneLabel);

        panne = new JRadioButton("oui");
        this.add(panne);
        notPanne = new JRadioButton("non");
        this.add(notPanne);

        espace();

        jRadioButtonGroupPanne = new ButtonGroup();
        jRadioButtonGroupPanne.add(panne);
        jRadioButtonGroupPanne.add(notPanne);

        embouteillageLabel = new JLabel("Est-ce qu'il y a eu un embouteillage : ");
        embouteillageLabel.setToolTipText("Cette ligne est facultative");
        this.add(embouteillageLabel);

        embouteillage = new JRadioButton("oui");
        this.add(embouteillage);
        notEmbouteillage = new JRadioButton("non");
        this.add(notEmbouteillage);

        espace();

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

        espace();

        hArriveeLabel = new JLabel("Date et heure d'arrivée du trajet : ");
        this.add(hArriveeLabel);

        pointFin = new JSpinner();
        model = new SpinnerDateModel();
        pointFin.setModel(model);
        editor = new JSpinner.DateEditor(pointFin, "dd-MM-yyyy HH:mm");
        pointFin.setEditor(editor);
        this.add(pointFin);
        pointDépart.setValue(pointFin.getValue());

        espace();

        insert = new JButton("Insérer le trajet dans la base de données");
        this.add(insert);
        insert.addActionListener(new InsertListener());
    }

    private class InsertListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            try {
                idTrajet = Integer.parseInt(idText.getText());

                if(kmText.getText().isEmpty()){
                    errorEmptyField("Vous devez remplir le nombre de km parcourus");
                    kmLabel.setForeground(Color.RED);
                } else{
                    try{
                        Integer.parseInt(kmText.getText());
                        nbKm = Integer.parseInt(kmText.getText());
                    } catch(NumberFormatException exception){
                        JOptionPane.showMessageDialog(null, "Le nombre de km doit être un nombre entier","Erreur dans le formulaire", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if(nbPassagersText.getText().isEmpty()){
                    errorEmptyField("Vous devez remplir le nombre de passagers");
                    nbPassagersLabel.setForeground(Color.RED);
                } else{
                    try{
                        Integer.parseInt(nbPassagersText.getText());
                        nbPassagers = Integer.parseInt(nbPassagersText.getText());
                    } catch(NumberFormatException exception){
                        JOptionPane.showMessageDialog(null, "Le nombre de passagers doit être un nombre entier","Erreur dans le formulaire", JOptionPane.ERROR_MESSAGE);
                    }
                }

                idChauffeur = Integer.parseInt(comboBoxChauffeurs.getSelectedItem().toString().substring(15, comboBoxChauffeurs.getSelectedItem().toString().indexOf(" ", 16)));

                codePostal = Integer.parseInt(comboBoxLocalites.getSelectedItem().toString().substring(0, comboBoxLocalites.getSelectedItem().toString().indexOf(" ")));

                localité = comboBoxLocalites.getSelectedItem().toString().substring(comboBoxLocalites.getSelectedItem().toString().indexOf(" ")+1);

                strIdClient = comboBoxClients.getSelectedItem().toString().substring(4);
                strIdClient = strIdClient.substring(0, strIdClient.indexOf(" "));
                idCLient = Integer.parseInt(strIdClient);

                aPanne = panne.isSelected();

                aEmbouteillage = embouteillage.isSelected();

                heureDépart = new Timestamp(((Date)pointDépart.getValue()).getTime());
                heureFin = new Timestamp(((Date)pointFin.getValue()).getTime());


                if (!controller.getAllTrajets(idChauffeur, heureDépart, heureFin).isEmpty()) {
                    throw  new ChauffeurExcpetion();
                }
                if(!controller.getAllTrajets(heureDépart, heureFin, idCLient).isEmpty()){
                    throw new ClientException();
                }

                newTrajet = new Trajet(idTrajet, nbKm, nbPassagers, idChauffeur, codePostal, localité, idCLient, aPanne, aEmbouteillage, heureFin, heureDépart);

                if(controller.getAllTrajets(idChauffeur,heureDépart,heureFin).isEmpty() && controller.getAllTrajets(heureDépart,heureFin,idCLient).isEmpty()){
                    controller.insertTrajet(newTrajet);
                    JOptionPane.showMessageDialog(null, "Trajet créé avec succès", "Succès !", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if(!controller.getAllTrajets(idChauffeur,heureDépart,heureFin).isEmpty()){
                        JOptionPane.showMessageDialog(null, "Ce chauffeur n'est pas disponible pour le moment", "Indisponibilité", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if(!controller.getAllTrajets(heureDépart,heureFin,idCLient).isEmpty()){
                        JOptionPane.showMessageDialog(null, "Ce client n'est pas disponible pour le moment", "Indisponibilité", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                JScrollPane scroller = new JScrollPane(new TrajectFormPanel(appWindow));
                appWindow.getContentPane().removeAll();
                appWindow.getContentPane().add(scroller, BorderLayout.CENTER);
                appWindow.getContentPane().repaint();
                appWindow.setVisible(true);

            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
            }catch (TimeException timeException){
                JOptionPane.showMessageDialog(null, "L'heure de départ doit être être antérieure à la date d'arrivée et de maximum 24h" ,"heure invalide", JOptionPane.ERROR_MESSAGE);
            }catch (ValeurException exception){
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Le nombre doit être positif", JOptionPane.ERROR_MESSAGE);
            }catch (CodePostalException codePostalException){
                JOptionPane.showMessageDialog (null, codePostalException.getMessage(), "Erreur sur le code postal", JOptionPane.ERROR_MESSAGE);
            }catch (IdException idException){
                JOptionPane.showMessageDialog (null, idException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
            }catch (ChauffeurExcpetion chauffeurExcpetion){
                JOptionPane.showMessageDialog(null, chauffeurExcpetion.getMessage(), "Chauffeur indisponible", JOptionPane.ERROR_MESSAGE);
            }catch (ClientException clientException){
                JOptionPane.showMessageDialog(null, clientException.getMessage(), "Client indisponible", JOptionPane.ERROR_MESSAGE);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setComboBoxSelection(JComboBox comboBox, String str){
        for(int i = 0; i < comboBox.getItemCount(); i++){
            if (comboBox.getItemAt(i).equals(str)){
                comboBox.setSelectedIndex(i);
            }
        }
    }

    public void espace(){
        this.add(Box.createRigidArea(new Dimension(10,10)));
    }

    public void errorEmptyField(String message){
        JOptionPane.showMessageDialog(null, message, "Erreur dans le formulaire", JOptionPane.ERROR_MESSAGE);
    }

}
