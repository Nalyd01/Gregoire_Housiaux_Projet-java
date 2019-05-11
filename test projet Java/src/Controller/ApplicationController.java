package Controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import Exception.*;
import Tools.*;

public class ApplicationController {
    private Manager manager;

    public ApplicationController(){
        manager = new Manager();
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

    public void removeTrajet(String request) throws SQLException{
        manager.removeTrajet(request);
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

    public void insertTrajet(Trajet newTrajet) throws SQLException {
        manager.insertTrajet(newTrajet);
    }


}
