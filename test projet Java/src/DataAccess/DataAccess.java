package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import Exception.*;
import Model.Trajet;

public interface DataAccess {
    ResultSet récupData(String sql) throws SQLException;

    ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, NbPassagersException, CodePostalException, IdException, TimeException;

    ArrayList<Trajet> getAllTrajets(int matricule, Timestamp date1, Timestamp date2) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException;

    ArrayList<Trajet> getAllTrajets(Timestamp date1, Timestamp date2, int client_id) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException;

    ArrayList<Trajet> getAllTrajets(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException;

    ArrayList<Trajet> getAllTrajets(ArrayList matricule) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException;

    Trajet getTrajetById(int idTrajet) throws SQLException, NbPassagersException, ValeurException, CodePostalException, IdException, TimeException;

    void removeTrajetById(int idTrajet) throws SQLException;

    ArrayList getAllChauffeurs() throws SQLException;

    String chauffeurById(int matricule) throws SQLException;

    String clientById(int client_id) throws SQLException;

    ArrayList getAllLocalites() throws SQLException;

    ArrayList getAllClients() throws SQLException;

    String getNextIdTrajet() throws SQLException;

    void insertTrajet(Trajet newTrajet) throws SQLException;

    ArrayList<String> getAllZones() throws SQLException;

    String getZoneChauffeur(int matricule) throws SQLException;

    ArrayList getChauffeursByZone(int zone_id) throws SQLException;

    HashMap<String, Integer> getNbTrajetsParZones() throws SQLException;

    ArrayList<Trajet> getOnGoingTraject()throws SQLException, ValeurException, NbPassagersException, CodePostalException, IdException, TimeException;
}
