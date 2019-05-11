package Tools;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import Exception.*;
import Model.DBAcces;

public class Manager {
    private DBAcces dao;

    public Manager(){
        dao = new DBAcces();
    }

    public ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        return dao.getAllTrajets();
    }

    public ArrayList<Trajet> getAllTrajets(int matricule, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        return dao.getAllTrajets(matricule ,date1, date2);
    }

    public ArrayList<Trajet> getAllTrajets(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        return dao.getAllTrajets(codePostal, nomLocalite ,date1, date2);
    }

    public void removeTrajet(String request) throws SQLException {
        dao.removeTrajet(request);
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

}
