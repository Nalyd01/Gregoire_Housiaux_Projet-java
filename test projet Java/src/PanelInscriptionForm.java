import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class PanelInscriptionForm extends JPanel {
    private JLabel idLabel, kmLabel, nbPassagersLabel, chauffeurLabel, localiteLabel, clientLabel, panneLabel, embouteillageLabel, hArriveeLabel, hDepartLabel;
    private JTextField idText, kmText, nbPassagersText;
    private JComboBox comboBoxChauffeurs, comboBoxLocalites, comboBoxClients, comboBoxPane, comboBoxEmb;
    private ApplicationController controller;
    private ArrayList list;
    private JSpinner pointDépart, pointFin;
    private SpinnerDateModel model;
    private JSpinner.DateEditor editor;

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
            Collections.sort(list);
            comboBoxLocalites = new JComboBox(list.toArray());
            comboBoxLocalites.setEditable(false);

            list = controller.getClient();
            Collections.sort(list);
            comboBoxClients = new JComboBox(list.toArray());
            comboBoxClients.setEditable(false);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }

        idLabel = new JLabel("Identifiant du trajet : ");
        this.add(idLabel);

            idText = new JTextField();
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

        String[] yesOrNo = {"oui", "non"};

        panneLabel = new JLabel("Est-ce qu'il y a eu une panne : ");
        this.add(panneLabel);

        comboBoxPane = new JComboBox(yesOrNo);
        this.add(comboBoxPane);

        embouteillageLabel = new JLabel("Est-ce qu'il y a eu un embouteillage : ");
        this.add(embouteillageLabel);
        comboBoxEmb = new JComboBox(yesOrNo);
        this.add(comboBoxEmb);

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
    }

}
