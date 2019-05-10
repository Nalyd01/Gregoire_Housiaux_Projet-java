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
        récupData("SELECT codePostal, nom FROM localite");

        localites = new ArrayList();
        while(data.next()){
            localites.add(data.getInt("codePostal"));
            localites.add(data.getString("nom"));
        }
        return localites;
    }

    @Override
    public ArrayList getClient() throws SQLException{
        récupData("SELECT nom, prenom FROM client");

        clients = new ArrayList();
        while(data.next()){
            clients.add(data.getString("nom"));
            clients.add(data.getString("prenom"));
        }
        return clients;
    }

    @Override
    public void insertTrajet(Trajet newTrajet) throws SQLException {
        connection = SingletonConnection.getInstance();
        sql = "INSERT INTO trajet VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        statement = connection.prepareStatement(sql);
        statement.setInt(1, newTrajet.getIdentifiant());
        statement.setInt(2, newTrajet.getNbKm());
        statement.setInt(3, newTrajet.getNbPassagers());
        statement.setInt(4, newTrajet.getMatricule());
        statement.setInt(5, newTrajet.getCodePostal());
        statement.setString(6, newTrajet.getNom());
        statement.setInt(7, newTrajet.getClient_id());
        statement.setNull(8, Types.BOOLEAN);
        statement.setBoolean(9, newTrajet.getaEuEmbouteillage());
        statement.setTimestamp(10, newTrajet.getHeureArrivee());
        statement.setTimestamp(11, newTrajet.getHeureDepart());

        statement.executeUpdate();

        // Colonnes facultatives
        if (aEuPanne != null) {
            sql = "UPDATE trajet SET panne = ? WHERE identifiant = '" + newTrajet.getIdentifiant() + "'";
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, aEuPanne);
            statement.executeUpdate();
        }
    }

    @Override
    public int getChauffeurMatricule(String nom) throws SQLException {
        sql = "SELECT matricule FROM chauffeur where nom = '" + nom + "'";
        récupData(sql);
        return data.getInt("matricule");
    }

    @Override
    public int getClient_id(String nom) throws SQLException {
        sql = "SELECT identifiant FROM client WHERE nom = '" + nom + "'";
        récupData(sql);
        return data.getInt("identifiant");
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
