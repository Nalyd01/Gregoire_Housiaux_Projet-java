package View;

import Controller.ApplicationController;
import Model.Trajet;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WelcomePanel extends JPanel {
    private JLabel line1, line2;
    private JPanel messageAcceuil, onGoingTraject;
    private JScrollPane scroller;
    private Font font;
    private ArrayList<Trajet> trajets;
    private ApplicationController controller;
    private AppWindow appWindow;

    public WelcomePanel(AppWindow appWindow) {
        this.appWindow = appWindow;
        messageAcceuil = new JPanel();
        messageAcceuil.setLayout(new BorderLayout());
        controller = new ApplicationController();

        line1 = new JLabel("Bienvenue sur notre application de gestion de taxis.");
        line2 = new JLabel("Par Grégoire Dylan et Housiaux Louis");

        line1.setHorizontalAlignment(SwingConstants.CENTER);
        line2.setHorizontalAlignment(SwingConstants.CENTER);

        font = new Font("Arial",Font.BOLD,14);

        line1.setFont(font);
        line2.setFont(font);

        messageAcceuil.add(line1, BorderLayout.NORTH);
        messageAcceuil.add(line2,BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        this.add(messageAcceuil, BorderLayout.NORTH);

        onGoingTraject = new JPanel();
        onGoingTraject.setLayout(new BoxLayout(onGoingTraject, BoxLayout.Y_AXIS));
        scroller = new JScrollPane(onGoingTraject);
        this.add(scroller, BorderLayout.CENTER);

        TrajectUpdateThread trajectUpdateThread = new TrajectUpdateThread(this, appWindow);
        trajectUpdateThread.start();
    }

    public void update(){
        try {
            onGoingTraject.removeAll();
            trajets = controller.getOnGoingTraject();
            OnGoingTrajectPanel temp;
            for (Trajet trajet :  trajets){
                temp = new OnGoingTrajectPanel(trajet);
                temp.update();
                onGoingTraject.add(temp);
            }
            this.repaint();
            this.revalidate();
        }catch (Exception e){
            JOptionPane.showMessageDialog (null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

}
