package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import Model.Trajet;
import Controller.ApplicationController;
import Exception.*;
import Tools.AllTrajetModel;

public class ListingWindow extends JFrame {
    private ApplicationController controller;
    private Container frameContainer;
    private JPanel panel;
    private JTable table;
    private JScrollPane scrollPane;
    private int matriculeChauffeur, codePostal;
    private String nomLocalite;
    private ArrayList<Trajet> trajets;
    private AllTrajetModel model;
    private DefaultTableCellRenderer render;
    private static ListingWindow listingWindow;
    private static ListingWindow rechercheWindow;

    public ListingWindow(){
        super("Listing des trajets");
        initListing();
        afficherListing();
        closePreviousWindow(true);
    }

    public ListingWindow(String chauffeur, Timestamp date1, Timestamp date2){
        super("Listing des clients d'un chauffeur entre 2 dates");

        initListing();

        matriculeChauffeur = Integer.parseInt(chauffeur.substring(15, chauffeur.indexOf(" ", 16)));
        afficherListing(matriculeChauffeur, date1, date2);

        closePreviousWindow(false);
    }

    public ListingWindow(Timestamp date1, Timestamp date2, String localites){
        super("Listing des clients d'une localités entre 2 dates");

        initListing();

        codePostal = Integer.parseInt(localites.substring(0,localites.indexOf(" ")));
        nomLocalite = localites.substring(localites.indexOf(" ")+1);

        afficherListing(codePostal, nomLocalite, date1, date2);

        closePreviousWindow(false);

    }

    public ListingWindow(ArrayList matricule){
        super("Lisiting des clients d'une zone");

        initListing();

        afficherListing(matricule);

        closePreviousWindow(false);
    }

    public void afficherListing(){
        try{
            trajets = controller.getAllTrajets();
            testAffichageListing(trajets);
        }
        catch (SQLException sqlException){
            JOptionPane.showMessageDialog (null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        catch (ValeurException valeurException){
            JOptionPane.showMessageDialog (null, valeurException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (CodePostalException codePostalException){
            JOptionPane.showMessageDialog (null, codePostalException.getMessage(), "Erreur sur le code postal", JOptionPane.ERROR_MESSAGE);
        }
        catch (IdException idException){
            JOptionPane.showMessageDialog (null, idException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (TimeException timeException){
            JOptionPane.showMessageDialog (null, timeException.getMessage(), "Erreur sur l'heure", JOptionPane.ERROR_MESSAGE);
        }
        catch (NbPassagersException nbPassagersException){
            JOptionPane.showMessageDialog (null, nbPassagersException.getMessage(), "Erreur sur le nombre de passagers", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void afficherListing(int matriculeChauffeur, Timestamp date1, Timestamp date2){
        try{
            trajets = controller.getAllTrajets(matriculeChauffeur, date1, date2);
            testAffichageListing(trajets);
        }
        catch (SQLException sqlException){
            JOptionPane.showMessageDialog (null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        catch (ValeurException valeurException){
            JOptionPane.showMessageDialog (null, valeurException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (CodePostalException codePostalException){
            JOptionPane.showMessageDialog (null, codePostalException.getMessage(), "Erreur sur le code postal", JOptionPane.ERROR_MESSAGE);
        }
        catch (IdException idException){
            JOptionPane.showMessageDialog (null, idException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (TimeException timeException){
            JOptionPane.showMessageDialog (null, timeException.getMessage(), "Erreur sur l'heure", JOptionPane.ERROR_MESSAGE);
        }
        catch (NbPassagersException nbPassagersException){
            JOptionPane.showMessageDialog (null, nbPassagersException.getMessage(), "Erreur sur le nombre de passagers", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void afficherListing(int codePostal, String nomLocalite, Timestamp date1, Timestamp date2){
        try{
            trajets = controller.getAllTrajets(codePostal, nomLocalite, date1, date2);
            testAffichageListing(trajets);
        }
        catch (SQLException sqlException){
            JOptionPane.showMessageDialog (null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        catch (ValeurException valeurException){
            JOptionPane.showMessageDialog (null, valeurException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (CodePostalException codePostalException){
            JOptionPane.showMessageDialog (null, codePostalException.getMessage(), "Erreur sur le code postal", JOptionPane.ERROR_MESSAGE);
        }
        catch (IdException idException){
            JOptionPane.showMessageDialog (null, idException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (TimeException timeException){
            JOptionPane.showMessageDialog (null, timeException.getMessage(), "Erreur sur l'heure", JOptionPane.ERROR_MESSAGE);
        }
        catch (NbPassagersException nbPassagersException){
            JOptionPane.showMessageDialog (null, nbPassagersException.getMessage(), "Erreur sur le nombre de passagers", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void afficherListing(ArrayList matricule){
        try{
            trajets = controller.getAllTrajets(matricule);
            testAffichageListing(trajets);
        }
        catch (SQLException sqlException){
            JOptionPane.showMessageDialog (null, sqlException.getMessage(), "Erreur SQL", JOptionPane.ERROR_MESSAGE);
        }
        catch (ValeurException valeurException){
            JOptionPane.showMessageDialog (null, valeurException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (CodePostalException codePostalException){
            JOptionPane.showMessageDialog (null, codePostalException.getMessage(), "Erreur sur le code postal", JOptionPane.ERROR_MESSAGE);
        }
        catch (IdException idException){
            JOptionPane.showMessageDialog (null, idException.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
        }
        catch (TimeException timeException){
            JOptionPane.showMessageDialog (null, timeException.getMessage(), "Erreur sur l'heure", JOptionPane.ERROR_MESSAGE);
        }
        catch (ListException listException){
            JOptionPane.showMessageDialog (null, listException.getMessage(), "Exception sur la liste", JOptionPane.ERROR_MESSAGE);
        }
        catch (NbPassagersException nbPassagersException){
            JOptionPane.showMessageDialog (null, nbPassagersException.getMessage(), "Erreur sur le nombre de passagers", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void créaTable(ArrayList<Trajet> trajets){
        model = new AllTrajetModel(trajets);

        table = new JTable(model);
        largeurColonnes();
        centrerTable();

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        scrollPane = new JScrollPane(table);

        frameContainer.add(scrollPane);
        frameContainer.repaint();

        this.setVisible(true);
    }

    public JTable getTable(){
        return table;
    }

    public void centrerTable() {
        render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(render);
        }
    }

    public void largeurColonnes(){
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(int i = 0; i < table.getColumnCount(); i++){
            if(i < 3){
                table.getColumnModel().getColumn(i).setPreferredWidth(135);
            } else{
                if( i == 3){
                    table.getColumnModel().getColumn(i).setPreferredWidth(180);
                } else{
                    if(i < 6){
                        table.getColumnModel().getColumn(i).setPreferredWidth(195);
                    }  else{
                        if(i == 6){
                            table.getColumnModel().getColumn(i).setPreferredWidth(160);
                        } else{
                            if(i < 9){
                                table.getColumnModel().getColumn(i).setPreferredWidth(90);
                            } else{
                                table.getColumnModel().getColumn(i).setPreferredWidth(150);
                            }
                        }
                    }
                }
            }
        }
    }

    public void deleteTrajet() {
        controller = new ApplicationController();
        String[] options = {"oui", "non"};

        int[] selectLines = getTable().getSelectedRows();

        if(selectLines.length == 0){
            JOptionPane.showMessageDialog (null, "Vous devez sélectionner au moins une ligne", "Erreur pour la suppression", JOptionPane.ERROR_MESSAGE);
        } else{
            int n = JOptionPane.showOptionDialog(null,"Confirmer la suppression","Confirmation",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if(n == JOptionPane.YES_OPTION){
                for(int i = 0; i < selectLines.length; i++){
                    int modelRow = table.convertRowIndexToModel(selectLines[i]-i);
                    try{
                        controller.removeTrajetById((int)getTable().getModel().getValueAt(selectLines[i]-i,0));
                        ((AllTrajetModel)getTable().getModel()).removeRow(modelRow);
                    }
                    catch(SQLException exception){
                        JOptionPane.showMessageDialog (null, exception.getMessage(), "Exception SQL", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(ValeurException exception){
                        JOptionPane.showMessageDialog (null, exception.getMessage(), "Erreur sur la valeur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    public void initListing(){
        setBounds(100,100,1000,500);

        controller = new ApplicationController();
        panel = new JPanel();
        frameContainer = this.getContentPane();
        frameContainer.setLayout(new BorderLayout());
        frameContainer.add(panel,BorderLayout.CENTER);
    }

    public void testAffichageListing(ArrayList trajets){
        if(trajets.isEmpty()){
            JOptionPane.showMessageDialog (null, "Aucune correspondance trouvée", "Erreur dans le listing", JOptionPane.ERROR_MESSAGE);
        } else{
            créaTable(trajets);
        }
    }

    public void closePreviousWindow(boolean isAListing){
        if(isAListing){
            if(listingWindow != null){
                listingWindow.dispose();
            }
            listingWindow = this;
        }
        else {
            if(rechercheWindow != null){
                rechercheWindow.dispose();
            }
            rechercheWindow = this;
        }

    }



}
