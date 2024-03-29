package Tools;

import Controller.ApplicationController;
import Model.Trajet;
import Exception.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AllTrajetModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<Trajet> contents;
    private String hArriveeFormatted, hDepartFormatted;
    private DateFormat newFormat;
    private ApplicationController controller;

    public AllTrajetModel(ArrayList<Trajet> trajets) {
        controller = new ApplicationController();
        columnNames = new ArrayList<>();
        columnNames.add("Identifiant du trajet");
        columnNames.add("Nombre de kilomètres");
        columnNames.add("Nombre de passagers");
        columnNames.add("Identifiant du chauffeur");
        columnNames.add("Code postal de la localité de départ");
        columnNames.add("Nom de la localité de départ");
        columnNames.add("Identifiant du client");
        columnNames.add("Panne");
        columnNames.add("Embouteillage");
        columnNames.add("Heure d'arrivée");
        columnNames.add("Heure de départ");
        contents = trajets;
    }

    public int getColumnCount( ) { return columnNames.size( ); }

    public int getRowCount( ) { return contents.size( ); }

    public String getColumnName(int column) { return columnNames.get(column); }

    public Object getValueAt(int row, int column){
        Trajet trajet = contents.get(row);
        switch (column){
            case 0 : return trajet.getIdentifiant();
            case 1 : return trajet.getNbKm();
            case 2 : return trajet.getNbPassagers();
            case 3 : {
                try{
                    return controller.chauffeurById(trajet.getMatricule());
                }
                catch (SQLException sqlException){
                    JOptionPane.showMessageDialog (null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
                }
                catch (IdException idException){
                    JOptionPane.showMessageDialog (null, idException.getMessage(), "Erreur sur l'identifiant", JOptionPane.ERROR_MESSAGE);
                }
            }
            case 4 : return trajet.getCodePostal();
            case 5 : return trajet.getNomLocalite();
            case 6 : {
                try{
                    return controller.clientById(trajet.getClient_id());
                }
                catch (SQLException sqlException){
                    JOptionPane.showMessageDialog (null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
                }
                catch (ValeurException valeurException){
                    JOptionPane.showMessageDialog (null, valeurException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
                }
            }
            case 7 : if(trajet.getaEuPanne() != null){
                if(trajet.getaEuPanne()){
                    return "Oui";
                } else{
                    return "Non";
                }
            } else{
                return null;
            }
            case 8 : if(trajet.getaEuEmbouteillage() != null){
                if(trajet.getaEuEmbouteillage()){
                    return "Oui";
                } else{
                    return "Non";
                }
            } else{
                return null;
            }
            case 9: {
                newFormat = new SimpleDateFormat("HH:mm --> dd-MMM-yyyy");
                hArriveeFormatted = newFormat.format(trajet.getHeureArrivee());
                return hArriveeFormatted;
            }
            case 10: {
                hDepartFormatted = newFormat.format(trajet.getHeureDepart());
                return hDepartFormatted;
            }
            default : return null;
        }
    }

    public Class getColumnClass(int column){
        Class c;
        switch (column){
            case 0 : c = Integer.class;
                break;
            case 1 : c = Integer.class;
                break;
            case 2 : c = Integer.class;
                break;
            case 3 : c = String.class;
                break;
            case 4 : c = Integer.class;
                break;
            case 5 : c = String.class;
                break;
            case 6 : c = String.class;
                break;
            case 7 : c = String.class;
                break;
            case 8 : c = String.class;
                break;
            case 9 : c = String.class;
                break;
            case 10 : c = String.class;
                break;
            default : c = String.class;
        }
        return c;
    }

    public void removeRow(int position){
        this.contents.remove(position);
        this.fireTableDataChanged();
    }
}
