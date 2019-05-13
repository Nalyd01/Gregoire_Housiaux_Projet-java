package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Exception.*;
import Tools.*;


public class DBAcces implements DataAccess {
    private Connection connection;
    private String sql;
    private Trajet trajet;
    private Boolean aEuPanne;
    private ArrayList<Trajet> allTrajets;
    private PreparedStatement statement;
    private ArrayList chauffeurs, localites, clients, allZones;
    private HashMap<String, Integer> zoneNbChauffeur;


    @Override
    public ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        connection = SingletonConnection.getInstance();

        sql = "SELECT * FROM trajet";
        statement = connection.prepareStatement(sql);

        // Récupération des données
        ResultSet data = statement.executeQuery();

        allTrajets = new ArrayList<>();
        while(data.next()){
            allTrajets.add(créaTrajets(data));
        }
        return allTrajets;
    }

    @Override
    public ArrayList<Trajet> getAllTrajets(int matricule, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        connection = SingletonConnection.getInstance();

        sql = "SELECT * FROM trajet WHERE heureDepart >= ? AND heureArrivee <= ? AND matricule = ?;";

        statement = connection.prepareStatement(sql);

        statement.setTimestamp(1, date1);
        statement.setTimestamp(2, date2);
        statement.setInt(3,matricule);

        ResultSet data = statement.executeQuery();

        allTrajets = new ArrayList<>();
        while(data.next()){
            allTrajets.add(créaTrajets(data));
        }
        return allTrajets;
    }

    @Override
    public ArrayList<Trajet> getAllTrajets(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        connection = SingletonConnection.getInstance();

        sql = "SELECT * FROM trajet WHERE heureDepart >= ? AND heureArrivee <= ? AND codePostal = ? AND nom = ?;";

        statement = connection.prepareStatement(sql);

        statement.setTimestamp(1, date1);
        statement.setTimestamp(2, date2);
        statement.setInt(3, codePostal);
        statement.setString(4, nomLocalite);

        ResultSet data = statement.executeQuery();

        allTrajets = new ArrayList<>();
        while(data.next()){
            allTrajets.add(créaTrajets(data));
        }
        return allTrajets;
    }

    @Override
    public Trajet getTrajet(int idTrajet) throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        ResultSet data = récupData("SELECT * FROM trajet WHERE identifiant = " + idTrajet + ";");
        data.next();
        return créaTrajets(data);
    }


    @Override
    public void removeTrajet(int idTrajet) throws SQLException{
        String sql = "DELETE FROM trajet WHERE identifiant = " + idTrajet + ";";
        connection = SingletonConnection.getInstance();
        statement = connection.prepareStatement(sql);
        statement.executeUpdate(sql);
    }

    @Override
    public ArrayList getChauffeurs() throws SQLException {
        ResultSet data = récupData("SELECT matricule, nom FROM chauffeur");

        chauffeurs = new ArrayList();
        while(data.next()){
            chauffeurs.add("Matricule n° : " + data.getInt("matricule") + " " +data.getString("nom"));
        }
        return chauffeurs;
    }

    @Override
    public String idChauffeur(int matricule) throws SQLException {
        ResultSet data = récupData("SELECT matricule, nom FROM chauffeur WHERE matricule = '" + matricule + "'");
        data.next();
        return "Matricule n° : " + data.getInt("matricule") + " " +data.getString("nom");
    }

    @Override
    public String idClient(int client_id) throws SQLException {
        ResultSet data = récupData("SELECT nom, prenom, identifiant FROM client WHERE identifiant = '" + client_id + "'");
        data.next();
        return "n°: " + data.getInt("identifiant") + " "+ data.getString("nom")+ " " + data.getString("prenom");
    }

    @Override
    public ArrayList getLocalite() throws SQLException {
        ResultSet data = récupData("SELECT codePostal, nom FROM localite");

        localites = new ArrayList();
        while(data.next()){
            localites.add(data.getInt("codePostal") + " " + data.getString("nom"));
        }
        return localites;
    }

    @Override
    public ArrayList getClient() throws SQLException {
        ResultSet data = récupData("SELECT nom, prenom, identifiant FROM client");

        clients = new ArrayList();
        while(data.next()){
            clients.add("n°: " + data.getInt("identifiant") + " "+ data.getString("nom")+ " " + data.getString("prenom"));
        }
        return clients;
    }

    @Override
    public String getIdTrajet() throws SQLException {
        ResultSet data = récupData("SELECT MAX(identifiant) FROM trajet");
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
        statement.setString(8, newTrajet.getNomLocalite());
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

    @Override
    public HashMap<String, Integer> getNbTrajetsParZones() throws SQLException {
        ResultSet data = récupData("SELECT matricule FROM trajet;");
        zoneNbChauffeur = new HashMap<String, Integer>();
        for (String zone : getAllZones()){
            zoneNbChauffeur.put(zone, 0);
        }
        while(data.next()){
            String zoneChauffeur = getZoneChauffeur(data.getInt("matricule"));
            zoneNbChauffeur.put(zoneChauffeur,zoneNbChauffeur.get(zoneChauffeur)+1);
        }
        return zoneNbChauffeur;
    }

    public ArrayList<String> getAllZones() throws SQLException {
        ResultSet data = récupData("SELECT identifiant, nom FROM zone");
        allZones = new ArrayList<String>();
        while (data.next()){
            allZones.add("Zone n°" + data.getInt("identifiant") + " " + data.getString("nom"));
        }
        return allZones;
    }

    @Override
    public String getZoneChauffeur(int matricule) throws SQLException {
        ResultSet data = récupData("SELECT zone_id FROM chauffeur WHERE matricule = '" + matricule + "'");
        if(!data.next()){
            return null;
        }
        int zoneId = data.getInt("zone_id");
        data = récupData("SELECT identifiant, nom FROM zone WHERE identifiant = '" + zoneId + "';");
        if(data.next()) {
            return "Zone n°" + data.getInt("identifiant") + " " + data.getString("nom");
        }else{
            return  null;
        }
    }

    public ResultSet récupData(String sql) throws SQLException{
        connection = SingletonConnection.getInstance();
        statement = connection.prepareStatement(sql);
        return statement.executeQuery();
    }

    public Trajet créaTrajets(ResultSet data) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        aEuPanne = data.getBoolean("panne");
        if(data.wasNull()){
            aEuPanne = false;
        }

        trajet = new Trajet(data.getInt("identifiant"), data.getInt("nbKm"), data.getInt("nbPassagers"),
                data.getInt("matricule"), data.getInt("codePostal"), data.getString("nom"),
                data.getInt("client_id"), aEuPanne, data.getBoolean("aEuEmbouteillage"), data.getTimestamp("heureArrivee")
                , data.getTimestamp("heureDepart"));

        return trajet;
    }

}
