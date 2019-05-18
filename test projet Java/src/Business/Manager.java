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

    public ArrayList<Trajet> getAllTrajets() throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException {
        return dao.getAllTrajets();
    }

    public ArrayList<Trajet> getAllTrajets(int matricule, Timestamp date1, Timestamp date2) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException {
        numberOfDigits = String.valueOf(matricule).length();
        if(numberOfDigits != 6){
            throw new IdException(matricule);
        }
        if(date1.getTime() >= date2.getTime()){
            throw new TimeException();
        }
        return dao.getAllTrajets(matricule ,date1, date2);
    }

    public ArrayList<Trajet> getAllTrajets(Timestamp date1, Timestamp date2, int client_id) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException {
        if(client_id <= 0){
            throw new ValeurException();
        }
        if(date1.getTime() >= date2.getTime()){
            throw new TimeException();
        }
        return dao.getAllTrajets(date1,date2,client_id);
    }

    public ArrayList<Trajet> getAllTrajets(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException {
        if(codePostal < 1000 || codePostal > 9992){
            throw new CodePostalException(codePostal);
        }
        if(date1.getTime() >= date2.getTime()){
            throw new TimeException();
        }
        return dao.getAllTrajets(codePostal, nomLocalite ,date1, date2);
    }

    public ArrayList<Trajet> getAllTrajets(ArrayList matricule) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException, ListException {
        if(matricule.isEmpty()){
            throw new ListException();
        }
        return dao.getAllTrajets(matricule);
    }

    public Trajet getTrajetById(int idTrajet) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException{
        if(idTrajet <= 0){
            throw new ValeurException();
        }
        return  dao.getTrajetById(idTrajet);
    }

    public void removeTrajetById(int idTrajet) throws SQLException, ValeurException {
        if(idTrajet <= 0){
            throw new ValeurException();
        }
        dao.removeTrajetById(idTrajet);
    }

    public ArrayList getAllChauffeurs() throws SQLException {
        return dao.getAllChauffeurs();
    }

    public ArrayList getAllLocalites() throws SQLException {
        return dao.getAllLocalites();
    }

    public ArrayList getAllClients() throws SQLException {
        return dao.getAllClients();
    }

    public String getNextIdTrajet() throws SQLException {
        return dao.getNextIdTrajet();
    }

    public void insertTrajet(Trajet newTrajet) throws SQLException {
        dao.insertTrajet(newTrajet);
    }

    public HashMap<String, Integer> getNbTrajetsParZones() throws SQLException {
        return dao.getNbTrajetsParZones();
    }

    public String chauffeurById(int matricule) throws SQLException, IdException {
        numberOfDigits = String.valueOf(matricule).length();
        if(numberOfDigits != 6){
            throw new IdException(matricule);
        }
        return dao.chauffeurById(matricule);
    }

    public String clientById(int client_id) throws SQLException, ValeurException {
        if(client_id <= 0){
            throw new ValeurException();
        }
        return dao.clientById(client_id);
    }

    public ArrayList getChauffeursByZone(int zone_id) throws SQLException, ValeurException {
        if(zone_id <= 0){
            throw new ValeurException();
        }
        return dao.getChauffeursByZone(zone_id);
    }

    public ArrayList<Trajet> getOnGoingTraject()throws SQLException{
        return dao.getOnGoingTraject();
    }

}
