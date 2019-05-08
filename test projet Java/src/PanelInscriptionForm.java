import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class PanelInscriptionForm extends JPanel {
    private JLabel idLabel, kmLabel, nbPassagersLabel, nomChauffeur, nomLocalite;
    private JTextField idText, kmText, nbPassagersText;
    private JComboBox comboBoxChauffeurs, comboBoxLocalites;
    private ApplicationController controller;
    private ArrayList list;

    public PanelInscriptionForm(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        controller = new ApplicationController();

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

        nomChauffeur = new JLabel("Nom du chauffeur : ");
        this.add(nomChauffeur);

            try{
                list = controller.getChauffeurs();
                Collections.sort(list);
                comboBoxChauffeurs = new JComboBox(list.toArray());
                Collections.sort(controller.getChauffeurs());
                comboBoxChauffeurs.setEditable(false);
                this.add(comboBoxChauffeurs);
            }
            catch(SQLException exception){
                JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
            }

        nomLocalite = new JLabel("Nom de la localité : ");
        this.add(nomLocalite);

            try{
                list = controller.getLocalite();
                Collections.sort(list);
                comboBoxLocalites = new JComboBox(list.toArray());
                comboBoxLocalites.setEditable(false);
                this.add(comboBoxLocalites);
            }
            catch(SQLException exception){
                JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
            }
    }

}
