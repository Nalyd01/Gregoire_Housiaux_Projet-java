package DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import Exception.*;
import Model.Trajet;


public class DBAcces implements DataAccess {
    private Connection connection;
    private String sql;
    private Trajet trajet;
    private Boolean aEuPanne, aEuEmbouteillage;
    private ArrayList<Trajet> allTrajets, allTrajetsZone;
    private PreparedStatement statement;
    private ArrayList chauffeurs, localites, clients, allZones, chauffeursZone;
    private HashMap<String, Integer> zoneNbChauffeur;

    @Override
    public ResultSet récupData(String sql) throws SQLException{
        connection = SingletonConnection.getInstance();
        statement = connection.prepareStatement(sql);
        return statement.executeQuery();
    }

    @Override
    public ArrayList<Trajet> getAllTrajets() throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        connection = SingletonConnection.getInstance();

        sql = "SELECT * FROM trajet";
        statement = connection.prepareStatement(sql);
        ResultSet data = statement.executeQuery();

        return allTrajetsList(data);
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

        return allTrajetsList(data);
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

        return allTrajetsList(data);
    }

    @Override
    public ArrayList<Trajet> getAllTrajets(ArrayList matricules) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        connection = SingletonConnection.getInstance();

        allTrajetsZone = new ArrayList<>();
        for(Object matricule : matricules){
            sql = "SELECT * FROM trajet WHERE matricule = '"+ matricule +"';";
            statement = connection.prepareStatement(sql);
            ResultSet data = statement.executeQuery();
            allTrajetsZone.addAll(allTrajetsList(data));
        }
        return allTrajetsZone;
    }

    @Override
    public Trajet getTrajetById(int idTrajet) throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        ResultSet data = récupData("SELECT * FROM trajet WHERE identifiant = " + idTrajet + ";");
        data.next();
        return créaTrajets(data);
    }


    @Override
    public void removeTrajetById(int idTrajet) throws SQLException{
        String sql = "DELETE FROM trajet WHERE identifiant = " + idTrajet + ";";
        connection = SingletonConnection.getInstance();
        statement = connection.prepareStatement(sql);
        statement.executeUpdate(sql);
    }

    @Override
    public ArrayList getAllChauffeurs() throws SQLException {
        ResultSet data = récupData("SELECT matricule, nom FROM chauffeur;");
        chauffeurs = new ArrayList();
        while(data.next()){
            chauffeurs.add("Matricule n° : " + data.getInt("matricule") + " " +data.getString("nom"));
        }
        return chauffeurs;
    }

    @Override
    public String chauffeurById(int matricule) throws SQLException {
        ResultSet data = récupData("SELECT matricule, nom FROM chauffeur WHERE matricule = '" + matricule + "';");
        data.next();
        return "Matricule n° : " + data.getInt("matricule") + " " +data.getString("nom");
    }

    @Override
    public String clientById(int client_id) throws SQLException {
        ResultSet data = récupData("SELECT nom, prenom, identifiant FROM client WHERE identifiant = '" + client_id + "';");
        data.next();
        return "n°: " + data.getInt("identifiant") + " "+ data.getString("nom")+ " " + data.getString("prenom");
    }

    @Override
    public ArrayList getAllLocalites() throws SQLException {
        ResultSet data = récupData("SELECT codePostal, nom FROM localite;");

        localites = new ArrayList();
        while(data.next()){
            localites.add(data.getInt("codePostal") + " " + data.getString("nom"));
        }
        return localites;
    }

    @Override
    public ArrayList getAllClients() throws SQLException {
        ResultSet data = récupData("SELECT nom, prenom, identifiant FROM client;");

        clients = new ArrayList();
        while(data.next()){
            clients.add("n°: " + data.getInt("identifiant") + " "+ data.getString("nom")+ " " + data.getString("prenom"));
        }
        return clients;
    }

    @Override
    public String getNextIdTrajet() throws SQLException {
        ResultSet data = récupData("SELECT MAX(identifiant) FROM trajet;");
        data.next();
        return String.valueOf(data.getInt(1)+1);
    }

    @Override
    public void insertTrajet(Trajet newTrajet) throws SQLException {
        connection = SingletonConnection.getInstance();
        sql = "INSERT INTO trajet (identifiant,nbKm,nbPassagers,matricule,codePostal,nom,client_id,heureArrivee,heureDepart) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        statement = connection.prepareStatement(sql);
        statement.setInt(1, newTrajet.getIdentifiant());
        statement.setInt(2, newTrajet.getNbKm());
        statement.setInt(3, newTrajet.getNbPassagers());
        statement.setInt(4, newTrajet.getMatricule());
        statement.setInt(5, newTrajet.getCodePostal());
        statement.setString(6, newTrajet.getNomLocalite());
        statement.setInt(7, newTrajet.getClient_id());
        statement.setTimestamp(8, newTrajet.getHeureArrivee());
        statement.setTimestamp(9, newTrajet.getHeureDepart());

        // Colonnes facultatives
        if (newTrajet.getaEuPanne() != null) {
            sql = "UPDATE trajet SET panne = ? WHERE identifiant = '" + newTrajet.getIdentifiant() + "';";
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, newTrajet.getaEuPanne());
            statement.executeUpdate();
        } else{
            statement.setNull(1, Types.BOOLEAN);
        }

        if (newTrajet.getaEuEmbouteillage() != null) {
            sql = "UPDATE trajet SET aEuEmbouteillage = ? WHERE identifiant = '" + newTrajet.getIdentifiant() + "';";
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, newTrajet.getaEuEmbouteillage());
            statement.executeUpdate();
        } else{
            statement.setNull(1, Types.BOOLEAN);
        }

        statement.executeUpdate();
    }

    @Override
    public ArrayList<String> getAllZones() throws SQLException {
        ResultSet data = récupData("SELECT identifiant, nom FROM zone;");
        allZones = new ArrayList<String>();
        while (data.next()){
            allZones.add("Zone n°" + data.getInt("identifiant") + " " + data.getString("nom"));
        }
        return allZones;
    }

    @Override
    public String getZoneChauffeur(int matricule) throws SQLException {
        ResultSet data = récupData("SELECT zone_id FROM chauffeur WHERE matricule = '" + matricule + "';");
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

    @Override
    public ArrayList getChauffeursByZone(int zone_id) throws SQLException{
        ResultSet data = récupData("SELECT matricule FROM chauffeur WHERE zone_id = '" + zone_id + "';");
        chauffeursZone = new ArrayList();
        while (data.next()){
            chauffeursZone.add(data.getInt("matricule"));
        }
        return chauffeursZone;
    }

    @Override
    public HashMap<String, Integer> getNbTrajetsParZones() throws SQLException {
        ResultSet data = récupData("SELECT matricule FROM trajet;");
        zoneNbChauffeur = new HashMap<>();
        for (String zone : getAllZones()){
            zoneNbChauffeur.put(zone, 0);
        }
        while(data.next()){
            String zoneChauffeur = getZoneChauffeur(data.getInt("matricule"));
            zoneNbChauffeur.put(zoneChauffeur,zoneNbChauffeur.get(zoneChauffeur)+1);
        }
        return zoneNbChauffeur;
    }

    @Override
    public ArrayList<Trajet> getOnGoingTraject()throws SQLException{
        allTrajets = new ArrayList<>();
        ResultSet data = récupData("SELECT * FROM trajet where heureArrivee > current_time();");
        while (data.next()){
            try {
                allTrajets.add(créaTrajets(data));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  allTrajets;
    }

    public Trajet créaTrajets(ResultSet data) throws SQLException, ValeurException, CodePostalException, IdException, TimeException {
        aEuPanne = data.getBoolean("panne");
        if(data.wasNull()){
            aEuPanne = null;
        }
        aEuEmbouteillage = data.getBoolean("aEuEmbouteillage");
        if(data.wasNull()){
            aEuEmbouteillage = null;
        }

        trajet = new Trajet(data.getInt("identifiant"), data.getInt("nbKm"), data.getInt("nbPassagers"),
                data.getInt("matricule"), data.getInt("codePostal"), data.getString("nom"),
                data.getInt("client_id"), aEuPanne, aEuEmbouteillage, data.getTimestamp("heureArrivee")
                , data.getTimestamp("heureDepart"));

        return trajet;
    }

    public ArrayList<Trajet> allTrajetsList(ResultSet data) throws SQLException, ValeurException, CodePostalException, IdException, TimeException{
        allTrajets = new ArrayList<>();
        while(data.next()){
            allTrajets.add(créaTrajets(data));
        }
        return allTrajets;
    }

}
