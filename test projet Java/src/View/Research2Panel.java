package View;

import Controller.ApplicationController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Research2Panel extends JPanel {
    private ApplicationController controller;
    private ArrayList allzones;
    private int[] trajetsZone;
    private JLabel nameZone;
    private int i;
    private int iMax;

    public Research2Panel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controller = new ApplicationController();

        /*
        try{

        } catch(SQLException exception){
            JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }*/

        iMax = researchMax(trajetsZone);

        for(int i = 0; i < allzones.size(); i++){
            nameZone = new JLabel("Zone --> " + allzones.get(i).toString() + " avec " + trajetsZone[i] + " " + (trajetsZone[i] > 1 ? "trajets" : "trajet"));
            nameZone.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            if(iMax == i){
                nameZone.setForeground(Color.RED);
            }

            this.add(nameZone);
        }

    }

    public int researchMax(int[] tab){
        int i = 0;
        int iMax = 1;
        while(i < tab.length){
            if(tab[i] > tab[iMax]){
                iMax = i;
            }
            i++;
        }
        return iMax;
    }
}
