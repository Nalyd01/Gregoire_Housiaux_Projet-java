package View;

import Controller.ApplicationController;
import Tools.TrajectUpdateThread;
import Model.Trajet;
import Exception.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class WelcomePanel extends JPanel {
    private JLabel line1, line2;
    private JPanel messageAcceuil, onGoingTraject;
    private JScrollPane scroller;
    private Font font;
    private ArrayList<Trajet> trajets;
    private ApplicationController controller;
    private AppWindow appWindow;
    private TrajectUpdateThread trajectUpdateThread;
    private OnGoingTripPanel temp;

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

        trajectUpdateThread = new TrajectUpdateThread(this, appWindow);
        trajectUpdateThread.start();
    }

    public void update(){
        try {
            onGoingTraject.removeAll();
            trajets = controller.getOnGoingTraject();

            for (Trajet trajet :  trajets){
                temp = new OnGoingTripPanel(trajet);
                temp.update();
                onGoingTraject.add(temp);
            }

            this.repaint();
            this.revalidate();
        }catch (SQLException sqlException){
            JOptionPane.showMessageDialog (null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        catch (ValeurException valeurException){
            JOptionPane.showMessageDialog (null, valeurException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (CodePostalException codePostalException){
            JOptionPane.showMessageDialog (null, codePostalException.getMessage(), "Erreur sur le code postal", JOptionPane.ERROR_MESSAGE);
        }
        catch (IdException idException){
            JOptionPane.showMessageDialog (null, idException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (TimeException timeException){
            JOptionPane.showMessageDialog (null, timeException.getMessage(), "Erreur sur l'heure", JOptionPane.ERROR_MESSAGE);
        }
        catch (NbPassagersException nbPassagersException){
            JOptionPane.showMessageDialog (null, nbPassagersException.getMessage(), "Erreur sur le nombre de passagers", JOptionPane.ERROR_MESSAGE);
        }
    }

}
