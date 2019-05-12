package View;

import javax.swing.*;
import Controller.ApplicationController;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;


public class Research2Panel extends JPanel {
    private ApplicationController controller;
    private HashMap<String,Integer> trajetsZone;
    private JLabel zone;
    public Research2Panel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controller = new ApplicationController();
        try{
            trajetsZone =  sortByValue( controller.getNbTrajetsParZones());
            for(HashMap.Entry<String,Integer> entry : trajetsZone.entrySet()){
                zone = new JLabel(entry.getKey() + " --> avec " + entry.getValue() + (entry.getValue() > 1 ? " trajets" : " trajet"));
                zone.setBorder(new EmptyBorder(20,50,20,50));
                this.add(zone);
            }
        } catch(SQLException exception){
            JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }

    }

    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
