package Tools;


import javax.swing.*;
import View.WelcomePanel;

import java.awt.*;

public class TrajectUpdateThread extends Thread {
    private WelcomePanel welcomePanel;
    private AppWindow appWindow;

    public TrajectUpdateThread(WelcomePanel welcomePanel, AppWindow appWindow){
        this.welcomePanel = welcomePanel;
        this.appWindow = appWindow;
    }

    @Override
    public void run() {
        while(containComponent(appWindow.getContentPane().getComponents(), welcomePanel)){
            try {
                welcomePanel.update();
                Thread.sleep(1000);
            }catch (Exception e){
                JOptionPane.showMessageDialog (null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public boolean containComponent(Component[] components, Component component){
        for (Component temp : components){
            if (temp == component)
            {
                return true;
            }
        }
        return false;
    }
}
