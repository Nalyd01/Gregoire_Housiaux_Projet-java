package View;

import Controller.ApplicationController;
import Model.Trajet;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class OnGoingTrajectPanel extends JPanel {
    private JProgressBar progressBar;
    private JLabel trajetText;
    private ApplicationController controller;
    private Trajet trajet;
    private double pourcentage;

    public OnGoingTrajectPanel(Trajet trajet){
        this.trajet = trajet;
        controller = new ApplicationController();
        progressBar = new JProgressBar();
        this.setVisible(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        try {
            trajetText = new JLabel(controller.idChauffeur(trajet.getMatricule()));
            trajetText.setHorizontalAlignment(SwingConstants.CENTER);
            progressBar.setString(trajet.getHeureDepart().toString()  + " --> " + trajet.getHeureArrivee().toString());
            progressBar.setStringPainted(true);

            this.add(trajetText);
            this.add(progressBar);
        }catch (SQLException e){
            e.printStackTrace();
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
