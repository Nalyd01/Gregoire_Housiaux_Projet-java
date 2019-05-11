package Model;

import java.sql.*;
import java.util.ArrayList;
import Exception.*;
import Tools.*;


public class DBAcces implements DataAccess {
    private Connection connection;
    private String sql;
    private Trajet trajet;
    private Boolean aEuPanne;
    private ArrayList<Trajet> allTrajets;
    private PreparedStatement statement;
    private ResultSet data;
    private ArrayList chauffeurs, localites, clients;

    @Override
    public ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        connection = SingletonConnection.getInstance();

        sql = "SELECT * FROM trajet";
        statement = connection.prepareStatement(sql);

        // Récupération des données
        data = statement.executeQuery();

        allTrajets = new ArrayList<>();
        while(data.next()){
            créaTrajets();
        }
        return allTrajets;
    }

    public ArrayList<Trajet> getAllTrajets(int matricule, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        connection = SingletonConnection.getInstance();

        sql = "SELECT * FROM trajet WHERE heureDepart >= ? AND heureArrivee <= ? AND matricule = ?;";

        statement = connection.prepareStatement(sql);

        statement.setTimestamp(1, date1);
        statement.setTimestamp(2, date2);
        statement.setInt(3,matricule);

        data = statement.executeQuery();

        allTrajets = new ArrayList<>();
        while(data.next()){
            créaTrajets();
        }
        return allTrajets;
    }

    public ArrayList<Trajet> getAllTrajets(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        connection = SingletonConnection.getInstance();

        sql = "SELECT * FROM trajet WHERE heureDepart >= ? AND heureArrivee <= ? AND codePostal = ? AND nom = ?;";

        statement = connection.prepareStatement(sql);

        statement.setTimestamp(1, date1);
        statement.setTimestamp(2, date2);
        statement.setInt(3, codePostal);
        statement.setString(4, nomLocalite);

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
        récupData("SELECT matricule, nom FROM chauffeur");

        chauffeurs = new ArrayList();
        while(data.next()){
            chauffeurs.add("Matricule n° : " + data.getInt("matricule") + " " +data.getString("nom"));
        }
        return chauffeurs;
    }

    @Override
    public ArrayList getLocalite() throws SQLException{
        récupData("SELECT codePostal, nom FROM localite");

        localites = new ArrayList();
        while(data.next()){
            localites.add(data.getInt("codePostal") + " " + data.getString("nom"));
        }
        return localites;
    }

    @Override
    public ArrayList getClient() throws SQLException{
        récupData("SELECT nom, prenom, identifiant FROM client");

        clients = new ArrayList();
        while(data.next()){
            clients.add("n°: " + data.getInt("identifiant") + " "+ data.getString("nom")+ " " + data.getString("prenom"));
        }
        return clients;
    }

    @Override
    public String getIdTrajet() throws SQLException{
        récupData("SELECT MAX(identifiant) FROM trajet");
        data.next();
        return String.valueOf(data.getInt(1)+1);
    }

    @Override
    public void insertTrajet(Trajet newTrajet) throws SQLException {
        connection = SingletonConnection.getInstance();
        sql = "INSERT INTO trajet VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        statement = connection.prepareStatement(sql);
        statement.setInt(1, newTrajet.getIdentifiant());
        statement.setInt(2, newTrajet.getNbKm());
        statement.setInt(3, newTrajet.getNbPassagers());
        statement.setNull(4, Types.BOOLEAN);
        statement.setBoolean(5, newTrajet.getaEuEmbouteillage());
        statement.setInt(6, newTrajet.getMatricule());
        statement.setInt(7, newTrajet.getCodePostal());
        statement.setString(8, newTrajet.getNom());
        statement.setInt(9, newTrajet.getClient_id());
        statement.setTimestamp(10, newTrajet.getHeureArrivee());
        statement.setTimestamp(11, newTrajet.getHeureDepart());

        statement.executeUpdate();

        // Colonnes facultatives
        if (newTrajet.getaEuPanne() != null) {
            sql = "UPDATE trajet SET panne = ? WHERE identifiant = '" + newTrajet.getIdentifiant() + "'";
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, newTrajet.getaEuPanne());
            statement.executeUpdate();
        }
    }

    public void récupData(String sql) throws SQLException{
        connection = SingletonConnection.getInstance();
        statement = connection.prepareStatement(sql);
        data = statement.executeQuery();
    }

    public void créaTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
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
