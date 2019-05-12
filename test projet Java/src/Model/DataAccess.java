package Model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import Exception.*;
import Tools.*;

public interface DataAccess {
    ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException;

    ArrayList<Trajet> getAllTrajets(int matricule, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException;

    ArrayList<Trajet> getAllTrajets(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException;

    void removeTrajet(String request) throws SQLException;

    ArrayList getChauffeurs() throws SQLException;

    String idChauffeur(int matricule) throws SQLException;

    String idClient(int client_id) throws SQLException;

    ArrayList getLocalite() throws SQLException;

    ArrayList getClient() throws SQLException;

    String getIdTrajet() throws SQLException;

    void insertTrajet(Trajet newTrajet) throws SQLException;

    HashMap<String, Integer> getNbTrajetsParZones() throws SQLException;

    HashMap<String, Integer> getAllZones() throws SQLException;

    String getZoneChauffeur(int matricule) throws SQLException;

    String nomZone(int zone_id) throws SQLException;
}
