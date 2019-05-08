import java.sql.SQLException;
import java.util.ArrayList;

public interface DataAccess {
    ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException;

    void removeTrajet(String request) throws SQLException;

    ArrayList getChauffeurs() throws SQLException;

    ArrayList getLocalite() throws SQLException;

    ArrayList getClient() throws SQLException;
}
