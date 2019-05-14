package Controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import Business.Manager;
import Exception.*;
import Tools.*;

public class ApplicationController {
    private Manager manager;

    public ApplicationController(){
        this.manager = new Manager();
    }

    public ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        return manager.getAllTrajets();
    }

    public ArrayList<Trajet> getAllTrajets(int matricule, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        return manager.getAllTrajets(matricule, date1, date2);
    }

    public ArrayList<Trajet> getAllTrajets(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        return manager.getAllTrajets(codePostal, nomLocalite, date1, date2);
    }

    public ArrayList<Trajet> getAllTrajets(ArrayList matricule) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        return manager.getAllTrajets(matricule);
    }

    public Trajet getTrajet(int idTrajet) throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        return manager.getTrajet(idTrajet);
    }

    public void removeTrajet(int idTrajet) throws SQLException{
        manager.removeTrajet(idTrajet);
    }

    public ArrayList getChauffeurs() throws SQLException{
        return manager.getChauffeurs();
    }

    public ArrayList getLocalite() throws SQLException{
        return manager.getLocalite();
    }

    public ArrayList getClient() throws SQLException {
        return manager.getClient();
    }

    public String getIdTrajet() throws SQLException {
        return manager.getIdTrajet();
    }

    public void insertTrajet(Trajet newTrajet) throws SQLException {
        manager.insertTrajet(newTrajet);
    }

    public HashMap<String, Integer> getNbTrajetsParZones() throws SQLException {
        return manager.getNbTrajetsParZones();
    }

    public String idChauffeur(int matricule) throws SQLException {
        return manager.idChauffeur(matricule);
    }

    public String idClient(int client_id) throws SQLException {
        return manager.idClient(client_id);
    }

    public ArrayList getChauffeursZone(int zone_id) throws SQLException {
        return manager.getChauffeursZone(zone_id);
    }

    public ArrayList<Trajet> getOnGoingTraject()throws SQLException{
        return manager.getOnGoingTraject();
    }

}
