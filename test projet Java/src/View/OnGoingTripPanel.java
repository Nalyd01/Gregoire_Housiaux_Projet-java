package View;

import Controller.ApplicationController;
import Model.Trajet;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import Exception.*;

public class OnGoingTripPanel extends JPanel {
    private JProgressBar progressBar;
    private JLabel trajetText;
    private ApplicationController controller;
    private Trajet trajet;
    private double pourcentage;
    private SimpleDateFormat formatDateHeure;
    private String heureDepart, heureArrivee;

    public OnGoingTripPanel(Trajet trajet){
        this.trajet = trajet;
        controller = new ApplicationController();

        UIManager.put("ProgressBar.selectionForeground", Color.RED);
        UIManager.put("ProgressBar.selectionBackground", Color.WHITE);

        this.add(Box.createRigidArea(new Dimension(10,10)));

        progressBar = new JProgressBar();

        this.setVisible(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        formatDateHeure = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        heureDepart = formatDateHeure.format(trajet.getHeureDepart());
        heureArrivee = formatDateHeure.format(trajet.getHeureArrivee());

        try {
            trajetText = new JLabel(controller.chauffeurById(trajet.getMatricule()));
            trajetText.setHorizontalAlignment(SwingConstants.CENTER);
            progressBar.setString(heureDepart  + " --> " + heureArrivee);
            progressBar.setStringPainted(true);

            this.add(trajetText);
            this.add(progressBar);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog (null, e.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        catch (IdException idException){
            JOptionPane.showMessageDialog (null, idException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void update(){
        progressBar.setMinimum(0);
        progressBar.setMaximum((int)(trajet.getHeureArrivee().getTime()- trajet.getHeureDepart().getTime()));
        progressBar.setValue((int) (System.currentTimeMillis() - trajet.getHeureDepart().getTime()));
        pourcentage = (double) progressBar.getValue() / progressBar.getMaximum();
        progressBar.setBackground(new Color((int)((1-pourcentage)*255), (int)(pourcentage*255),0));
        progressBar.setForeground(Color.LIGHT_GRAY);
    }
}
