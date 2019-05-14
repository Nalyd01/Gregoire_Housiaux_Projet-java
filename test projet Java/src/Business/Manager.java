package Business;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import Exception.*;
import DataAccess.DBAcces;
import Model.Trajet;

public class Manager {
    private DBAcces dao;
    private int numberOfDigits;

    public Manager(){
        this.dao = new DBAcces();
    }

    public ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        return dao.getAllTrajets();
    }

    public ArrayList<Trajet> getAllTrajets(int matricule, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        numberOfDigits = String.valueOf(matricule).length();
        if(numberOfDigits != 6){
            throw new IdException(matricule);
        }
        if(date1.getTime() >= date2.getTime()){
            throw new TimeException();
        }
        return dao.getAllTrajets(matricule ,date1, date2);
    }

    public ArrayList<Trajet> getAllTrajets(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        if(codePostal < 1000 || codePostal > 9992){
            throw new CodePostalException(codePostal);
        }
        if(date1.getTime() >= date2.getTime()){
            throw new TimeException();
        }
        return dao.getAllTrajets(codePostal, nomLocalite ,date1, date2);
    }

    public ArrayList<Trajet> getAllTrajets(ArrayList matricule) throws SQLException, ValeurException, CodePostalException, IdException, TimeException, ListException {
        if(matricule.isEmpty()){
            throw new ListException();
        }
        return dao.getAllTrajets(matricule);
    }

    public Trajet getTrajet(int idTrajet) throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        if(idTrajet <= 0){
            throw new ValeurException(idTrajet);
        }
        return  dao.getTrajet(idTrajet);
    }

    public void removeTrajet(int idTrajet) throws SQLException, ValeurException {
        if(idTrajet <= 0){
            throw new ValeurException(idTrajet);
        }
        dao.removeTrajet(idTrajet);
    }

    public ArrayList getChauffeurs() throws SQLException {
        return dao.getChauffeurs();
    }

    public ArrayList getLocalite() throws SQLException {
        return dao.getLocalite();
    }

    public ArrayList getClient() throws SQLException {
        return dao.getClient();
    }

    public String getIdTrajet() throws SQLException {
        return dao.getIdTrajet();
    }

    public void insertTrajet(Trajet newTrajet) throws SQLException {
        dao.insertTrajet(newTrajet);
    }

    public HashMap<String, Integer> getNbTrajetsParZones() throws SQLException {
        return dao.getNbTrajetsParZones();
    }

    public String idChauffeur(int matricule) throws SQLException, IdException {
        numberOfDigits = String.valueOf(matricule).length();
        if(numberOfDigits != 6){
            throw new IdException(matricule);
        }
        return dao.idChauffeur(matricule);
    }

    public String idClient(int client_id) throws SQLException, ValeurException {
        if(client_id <= 0){
            throw new ValeurException(client_id);
        }
        return dao.idClient(client_id);
    }

    public ArrayList getChauffeursZone(int zone_id) throws SQLException, IdException {
        numberOfDigits = String.valueOf(zone_id).length();
        if(numberOfDigits != 6){
            throw new IdException(zone_id);
        }
        return dao.getChauffeursZone(zone_id);
    }

    public ArrayList<Trajet> getOnGoingTraject()throws SQLException{
        return dao.getOnGoingTraject();
    }
}
