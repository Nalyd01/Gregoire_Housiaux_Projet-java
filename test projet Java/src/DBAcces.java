import java.sql.*;
import java.util.ArrayList;

public class DBAcces implements DataAccess {
    private Connection connection;
    private String sql;
    private Trajet trajet;
    private Boolean aEuPanne;
    private ArrayList<Trajet> allTrajets;
    private PreparedStatement statement;
    private ResultSet data;
    private ResultSetMetaData meta;
    private ArrayList chauffeurs, localites, clients;

    @Override
    public ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        connection = SingletonConnection.getInstance();

        sql = "SELECT * FROM trajet";
        statement = connection.prepareStatement(sql);

        // Récupération des données
        data = statement.executeQuery();

        // Récupération des méta-données
        meta = data.getMetaData();

        allTrajets = new ArrayList<>();
        while(data.next()){
            créaTrajets();
        }
        return allTrajets;
    }

    public ArrayList<Trajet> getAllTrajets(String nomChauffeur, String départ, String fin) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        connection = SingletonConnection.getInstance();

        sql = "SELECT * FROM Trajet WHERE heureDepart = ? AND heureArrivee = ? AND matricule = (SELECT matricule FROM chauffeur WHERE chauffeur.nom = ?)";

        statement = connection.prepareStatement(sql);

            statement.setTime(1,Time.valueOf(départ));
            statement.setTime(2,Time.valueOf(fin));
            statement.setString(3,nomChauffeur);

        data = statement.executeQuery();

        allTrajets = new ArrayList<>();
        while(data.next()){
            créaTrajets();
        }
        return allTrajets;
    }


    @Override
    public void removeTrajet(String request) throws SQLException{
        connection = SingletonConnection.getInstance();
        statement = connection.prepareStatement(request);
        statement.executeUpdate(request);
    }

    @Override
    public ArrayList getChauffeurs() throws SQLException{
        récupData("SELECT nom FROM chauffeur");

        chauffeurs = new ArrayList();
        while(data.next()){
            chauffeurs.add(data.getString("nom"));
        }
        return chauffeurs;
    }

    @Override
    public ArrayList getLocalite() throws SQLException{
        récupData("SELECT nom FROM localite");

        localites = new ArrayList();
        while(data.next()){
            localites.add(data.getString("nom"));
        }
        return localites;
    }

    @Override
    public ArrayList getClient() throws SQLException{
        récupData("SELECT nom, prenom FROM client");

        clients = new ArrayList();
        while(data.next()){
            clients.add(data.getString("nom"), data.getString("prenom"));
        }
        return clients;
    }

    public void récupData(String sql) throws SQLException{
        connection = SingletonConnection.getInstance();
        statement = connection.prepareStatement(sql);
        data = statement.executeQuery();
    }

    public void créaTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        aEuPanne = data.getBoolean("panne");
        if(data.wasNull()){
            aEuPanne = false;
        }

        trajet = new Trajet(data.getInt("identifiant"), data.getInt("nbKm"), data.getInt("nbPassagers"),
                data.getInt("matricule"), data.getInt("codePostal"), data.getString("nom"),
                data.getInt("client_id"), aEuPanne, data.getBoolean("aEuEmbouteillage"), data.getTimestamp("heureArrivee")
                , data.getTimestamp("heureDepart"));

        allTrajets.add(trajet);
    }

}
