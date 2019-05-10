package Model;

import java.sql.SQLException;
import java.util.ArrayList;
import Exception.*;
import Tools.*;

public interface DataAccess {
    ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException;

    void removeTrajet(String request) throws SQLException;

    ArrayList getChauffeurs() throws SQLException;

    ArrayList getLocalite() throws SQLException;

    ArrayList getClient() throws SQLException;

    void insertTrajet(Trajet newTrajet) throws SQLException;

    int getChauffeurMatricule(String nom) throws SQLException;

    int getClient_id(String nom) throws SQLException;
}
