package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteWindow extends ListingWindow {
    private JButton deleteButt;

    public DeleteWindow(){
        deleteButt = new JButton("Supprimer");
        this.add(deleteButt,BorderLayout.SOUTH);

            DeleteButtonListener deleteButtonListener = new DeleteButtonListener();
            deleteButt.addActionListener(deleteButtonListener);
    }

    private class DeleteButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            String[] options = {"Oui","Non"};
            int confirmation = JOptionPane.showOptionDialog(null,"Confirmer la suppression","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if(confirmation == JOptionPane.YES_OPTION){
                deleteTrajet();
            }
        }
    }
}
