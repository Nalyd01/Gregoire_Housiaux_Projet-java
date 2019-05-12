package View;

import Controller.ApplicationController;
import Tools.AllTrajetModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DeleteWindow extends ListingWindow {
    private JButton deleteButt;

    public DeleteWindow(){
        deleteButt = new JButton("Supprimer");
        this.add(deleteButt,BorderLayout.SOUTH);

            DeleteButtonListener deleteButtonListener = new DeleteButtonListener();
            deleteButt.addActionListener(deleteButtonListener);
    }

    private class DeleteButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent event){
            deleteTrajet();
        }
    }
}
