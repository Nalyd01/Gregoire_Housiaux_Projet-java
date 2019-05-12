package View;

import Controller.ApplicationController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;

public class Research2Panel extends JPanel {
    private ApplicationController controller;
    private HashMap<String,Integer> trajetsZone;
    private JLabel zone;
    private int max;

    public Research2Panel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controller = new ApplicationController();

        try{
            trajetsZone = controller.getNbTrajetsParZones();
        } catch(SQLException exception){
            JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }

        max = (Collections.max(trajetsZone.values()));

        for(HashMap.Entry<String,Integer> entry : trajetsZone.entrySet()){
            zone = new JLabel(entry.getKey() + " --> avec " + entry.getValue() + (entry.getValue() > 1 ? " trajets" : " trajet"));
            zone.setBorder(new EmptyBorder(20,50,20,50));
            this.add(zone);

            if(entry.getValue() == max){
                zone.setForeground(Color.RED);
            }
        }

    }
}
