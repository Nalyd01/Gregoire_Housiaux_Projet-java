package Business;

import Controller.ApplicationController;

import javax.swing.*;
import java.sql.SQLException;
import java.util.*;

public class NbTrajetsParZone {
    private static ApplicationController controller;
    private static HashMap<String,Integer> nbTrajetsZone;
    private static ArrayList<String> allZones;
    private static ArrayList<Integer> chauffeurs;
    private static String zoneChauffeur;

    public static HashMap<String,Integer> getNbTrajets(){
        controller = new ApplicationController();
        nbTrajetsZone = new HashMap<>();

        try{
            allZones = controller.getAllZones();

            for(String zone : allZones){
                nbTrajetsZone.put(zone,0);
            }

            chauffeurs = controller.getMatriculesTrajets();

            for(int matricule : chauffeurs){
                zoneChauffeur = controller.getZoneChauffeur(matricule);
                nbTrajetsZone.put(zoneChauffeur, nbTrajetsZone.get(zoneChauffeur)+1);
            }

        }
        catch (SQLException sqlException){
            JOptionPane.showMessageDialog (null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }

        return sortByValue(nbTrajetsZone);
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hashMap)
    {
        List<Map.Entry<String, Integer> > list = new LinkedList<>(hashMap.entrySet());

        Collections.sort(list,(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> (o2.getValue()).compareTo(o1.getValue()));

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> data : list) {
            temp.put(data.getKey(), data.getValue());
        }
        return temp;
    }
}
