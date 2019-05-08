import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationController {
    private Manager manager;

    public ApplicationController(){
        manager = new Manager();
    }

    public ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        return manager.getAllTrajets();
    }

    public ArrayList<Trajet> getAllTrajets(String nomChauffeur, String départ, String fin) throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        return manager.getAllTrajets(nomChauffeur,départ,fin);
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
}
