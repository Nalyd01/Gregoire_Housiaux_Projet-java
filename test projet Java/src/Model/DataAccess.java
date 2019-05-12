package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Exception.*;
import Tools.*;

public interface DataAccess {
    ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException;

    void removeTrajet(int idTrajet) throws SQLException;

    ArrayList getChauffeurs() throws SQLException;

    ArrayList getLocalite() throws SQLException;

    ArrayList getClient() throws SQLException;

    String getIdTrajet() throws SQLException;

    void insertTrajet(Trajet newTrajet) throws SQLException;

    ResultSet récupData(String sql) throws SQLException;
}
