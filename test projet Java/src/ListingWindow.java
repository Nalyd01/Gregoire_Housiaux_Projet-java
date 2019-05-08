import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListingWindow extends JFrame {
    private ApplicationController controller;
    private Container frameContainer;
    private MainPanel panel;
    private JTable table;
    private JScrollPane scrollPane;

    public ListingWindow(){
        super("Listing des trajets");
        setBounds(100,100,1000,400);

        controller = new ApplicationController();
        panel = new MainPanel();
        frameContainer = this.getContentPane();
        frameContainer.setLayout(new BorderLayout());
        frameContainer.add(panel,BorderLayout.CENTER);
        afficherListing();
    }

    public ListingWindow(String nomChauffeur, String début, String fin){
        super("Listing des clients d'un chauffeur à une certaine date");
        setBounds(100,100,1000,400);

        controller = new ApplicationController();
        panel = new MainPanel();
        frameContainer = this.getContentPane();
        frameContainer.setLayout(new BorderLayout());
        frameContainer.add(panel,BorderLayout.CENTER);
        afficherListing(nomChauffeur,début,fin);
    }

    public void afficherListing(){
        try{
            ArrayList<Trajet> trajets = controller.getAllTrajets();
            créaTable(trajets);
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
    }

    public void afficherListing(String nomChauffeur, String début, String fin){
        try{
            ArrayList<Trajet> trajets = controller.getAllTrajets(nomChauffeur, début, fin);
            créaTable(trajets);
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
    }

    public void créaTable(ArrayList<Trajet> trajets){
        AllTrajetModel model = new AllTrajetModel(trajets);

        table = new JTable(model);
        largeurColonnes();
        centrerTable();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);

        frameContainer.add(scrollPane);
        frameContainer.repaint();

        this.setVisible(true);
    }

    public JTable getTable(){
        return table;
    }

    public void centrerTable() {
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(render);
        }
    }

    public void largeurColonnes(){
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(int i = 0; i < table.getColumnCount(); i++){
            if(i == 9 || i == 10){
                table.getColumnModel().getColumn(i).setPreferredWidth(150);
            } else{
                table.getColumnModel().getColumn(i).setPreferredWidth(100);
            }
        }
    }

}
