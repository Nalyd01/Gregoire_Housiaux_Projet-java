package Controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import Business.Manager;
import Exception.*;
import Model.Trajet;

public class ApplicationController {
    private Manager manager;

    public ApplicationController(){
        this.manager = new Manager();
    }

    public ArrayList<Trajet> getAllTrajets() throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException {
        return manager.getAllTrajets();
    }

    public ArrayList<Trajet> getAllTrajets(int matricule, Timestamp date1, Timestamp date2) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException {
        return manager.getAllTrajets(matricule, date1, date2);
    }

    public ArrayList<Trajet> getAllTrajets(Timestamp date1, Timestamp date2, int client_id) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException {
        return manager.getAllTrajets(date1,date2,client_id);
    }

    public ArrayList<Trajet> getAllTrajets(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException {
        return manager.getAllTrajets(codePostal, nomLocalite, date1, date2);
    }

    public ArrayList<Trajet> getAllTrajets(ArrayList matricule) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException, ListException {
        return manager.getAllTrajets(matricule);
    }

    public Trajet getTrajetById(int idTrajet) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException{
        return manager.getTrajetById(idTrajet);
    }

    public void removeTrajetById(int idTrajet) throws SQLException, ValeurException{
        manager.removeTrajetById(idTrajet);
    }

    public ArrayList getAllChauffeurs() throws SQLException{
        return manager.getAllChauffeurs();
    }

    public ArrayList getAllLocalites() throws SQLException{
        return manager.getAllLocalites();
    }

    public ArrayList getAllClients() throws SQLException {
        return manager.getAllClients();
    }

    public String getNextIdTrajet() throws SQLException {
        return manager.getNextIdTrajet();
    }

    public void insertTrajet(Trajet newTrajet) throws SQLException {
        manager.insertTrajet(newTrajet);
    }

    public HashMap<String, Integer> getNbTrajetsParZones() throws SQLException {
        return manager.getNbTrajetsParZones();
    }

    public String chauffeurById(int matricule) throws SQLException, IdException {
        return manager.chauffeurById(matricule);
    }

    public String clientById(int client_id) throws SQLException, ValeurException {
        return manager.clientById(client_id);
    }

    public ArrayList getChauffeursByZone(int zone_id) throws SQLException, ValeurException {
        return manager.getChauffeursByZone(zone_id);
    }

    public ArrayList<Trajet> getOnGoingTraject()throws SQLException{
        return manager.getOnGoingTraject();
    }

}
