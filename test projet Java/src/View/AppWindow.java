package View;

import Tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;


public class AppWindow extends JFrame {
    private Container frameContainer;
    private WelcomePanel panel;
    private JMenuBar menuBar;
    private JMenu welcome, insert, change, suppression, listing, research, businessTask;
    private JMenuItem welcomeMenu, newTrip, changeTrip, deleteTrip, listTrip, research1, research2, research3, costTrip;
    private JScrollPane scroller;

    public AppWindow(){
        super("Gestion de société de taxis");
        setBounds(100,100,500,500);

        panel = new WelcomePanel(this);
        frameContainer = this.getContentPane();
        frameContainer.setLayout(new BorderLayout());
        frameContainer.add(panel,BorderLayout.CENTER);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

            welcome = new JMenu("Accueil");
            menuBar.add(welcome);

        welcomeMenu = new JMenuItem("Message d'accueil");
        welcome.add(welcomeMenu);

        AccueilListener welcomeListener = new AccueilListener();
        welcomeMenu.addActionListener(welcomeListener);

            insert = new JMenu("Insertion");
            menuBar.add(insert);

                newTrip = new JMenuItem("Nouveau trajet");
                insert.add(newTrip);

                InsertListener insertListener = new InsertListener();
                newTrip.addActionListener(insertListener);

            change = new JMenu("Modification");
            menuBar.add(change);

                changeTrip = new JMenuItem("Modifier un trajet");
                change.add(changeTrip);

                ModifyListener modifyListener = new ModifyListener();
                changeTrip.addActionListener(modifyListener);

            suppression = new JMenu("Suppression");
            menuBar.add(suppression);

                deleteTrip = new JMenuItem("Supprimer un trajet");
                suppression.add(deleteTrip);

                DeleteListener deleteListener = new DeleteListener();
                deleteTrip.addActionListener(deleteListener);

            listing = new JMenu("Listing");
            menuBar.add(listing);

                listTrip = new JMenuItem("Listing des trajets");
                listing.add(listTrip);

                ListingListener listingListener = new ListingListener();
                listTrip.addActionListener(listingListener);

            research = new JMenu("Recherches");
            menuBar.add(research);

                research1 = new JMenuItem("Listing des clients d'une localité entre 2 dates");
                research.add(research1);
                research.addSeparator();

                research2 = new JMenuItem("Recherche des zones où il y a le plus de clients");
                research.add(research2);
                research.addSeparator();

                research3 = new JMenuItem("Listing des clients d’un chauffeur à une certaine date");
                research.add(research3);

                ResearchListener researchListener = new ResearchListener();
                research1.addActionListener(researchListener);
                research2.addActionListener(researchListener);
                research3.addActionListener(researchListener);

            businessTask = new JMenu("Tâche métier");
            menuBar.add(businessTask);

                costTrip = new JMenuItem("Calcul du coût d'un trajet");
                businessTask.add(costTrip);
                TacheMetierListener tacheMetierListener = new TacheMetierListener();
                costTrip.addActionListener(tacheMetierListener);

        this.addWindowListener( new WindowAdapter()
        { public void windowClosing( WindowEvent e)
        { System.exit(0); }
        } );


        setVisible(true);
    }

    private class AccueilListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event){
            frameContainer.removeAll();
            frameContainer.add(new WelcomePanel(AppWindow.this), BorderLayout.CENTER);
            frameContainer.repaint();
            AppWindow.this.setVisible(true);
        }
    }

    private class InsertListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            scroller = new JScrollPane(new TrajectFormPanel(AppWindow.this));
            frameContainer.removeAll();
            frameContainer.add(scroller, BorderLayout.CENTER);
            frameContainer.repaint();
            AppWindow.this.setVisible(true);
        }
    }

    private class ModifyListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            new ModifyWindow(AppWindow.this);
        }
    }

    private class ListingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event){
            new ListingWindow();
        }
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event){
            new DeleteWindow();
        }
    }

    private class ResearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            frameContainer.removeAll();
            if(event.getSource() == research1){
                frameContainer.add(new Research1Panel(), BorderLayout.NORTH);
            }
            if(event.getSource() == research2){
                frameContainer.add(new Research2Panel(), BorderLayout.NORTH);
            }
            if(event.getSource() == research3){
                frameContainer.add(new Research3Panel(), BorderLayout.NORTH);
            }
            frameContainer.repaint();
            AppWindow.this.setVisible(true);
        }
    }

    private class TacheMetierListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new TacheMetierWindow(AppWindow.this);
            }catch (Exception err){
                err.printStackTrace();
            }
        }
    }

    public Container getFrameContainer(){
        return frameContainer;
    }


}


