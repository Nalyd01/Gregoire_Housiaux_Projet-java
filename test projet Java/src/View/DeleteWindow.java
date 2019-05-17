package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteWindow extends ListingWindow {
    private JButton deleteButt;
    private static DeleteWindow uniqueInstance;

    private DeleteWindow(){
        deleteButt = new JButton("Supprimer");
        this.add(deleteButt,BorderLayout.SOUTH);

            DeleteButtonListener deleteButtonListener = new DeleteButtonListener();
            deleteButt.addActionListener(deleteButtonListener);
    }

    public static DeleteWindow getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new DeleteWindow();
        } else {
            JOptionPane.showMessageDialog (null, "Vous ne pouvez ouvrir qu'une seule fenêtre de suppression à la fois", "Erreur à l'ouverture", JOptionPane.INFORMATION_MESSAGE);
        }
        return uniqueInstance;
    }

    private class DeleteButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            deleteTrajet();
        }
    }
}
