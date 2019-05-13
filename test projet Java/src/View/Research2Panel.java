package View;

import javax.swing.*;
import Controller.ApplicationController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Research2Panel extends JPanel {
    private ApplicationController controller;
    private HashMap<String,Integer> trajetsZone;
    private ArrayList zones, matriculeChauffeurs;
    private JComboBox boxZones;
    private JLabel zoneLabel;
    private JButton researchButton;
    private int zone_id;

    public Research2Panel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        controller = new ApplicationController();
        zones = new ArrayList();

        zoneLabel = new JLabel("Nombre de trajets par zone : ");
        this.add(zoneLabel);

        try{
            trajetsZone =  sortByValue(controller.getNbTrajetsParZones());
            for(HashMap.Entry<String,Integer> entry : trajetsZone.entrySet()){
                zones.add(entry.getKey() + " --> avec " + entry.getValue() + (entry.getValue() > 1 ? " trajets" : " trajet"));
            }
        } catch(SQLException exception){
            JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }

        boxZones = new JComboBox(zones.toArray());
        boxZones.setEditable(false);
        this.add(boxZones);

        this.add(Box.createRigidArea(new Dimension(10,10)));

        researchButton = new JButton("Rechercher");
        this.add(researchButton);

        ResearchListener listener = new ResearchListener();
        researchButton.addActionListener(listener);

    }

    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hashMap)
    {
        List<Map.Entry<String, Integer> > list = new LinkedList<>(hashMap.entrySet());

        Collections.sort(list,(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> (o2.getValue()).compareTo(o1.getValue()));

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> data : list) {
            temp.put(data.getKey(), data.getValue());
        }
        return temp;
    }

    private class ResearchListener implements ActionListener{

        public void actionPerformed(ActionEvent event){
            zone_id = Integer.parseInt(boxZones.getSelectedItem().toString().substring(7,12));
            try{
                    matriculeChauffeurs = controller.getChauffeursZone(zone_id);
                    new ListingWindow(matriculeChauffeurs);
            } catch(SQLException exception){
                JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
