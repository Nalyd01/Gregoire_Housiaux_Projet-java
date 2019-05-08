import java.sql.SQLException;
import java.util.ArrayList;

public class Manager {
    private DBAcces dao;

    public Manager(){
        dao = new DBAcces();
    }

    public ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        return dao.getAllTrajets();
    }

    public ArrayList<Trajet> getAllTrajets(String nomChauffeur, String départ, String fin) throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        return dao.getAllTrajets(nomChauffeur,départ,fin);
    }

    public void removeTrajet(String request) throws SQLException{
        dao.removeTrajet(request);
    }

    public ArrayList getChauffeurs() throws SQLException{
        return dao.getChauffeurs();
    }

    public ArrayList getLocalite() throws SQLException{
        return dao.getLocalite();
    }

    public ArrayList getClient() throws SQLException {
        return dao.getClient();
    }
}
