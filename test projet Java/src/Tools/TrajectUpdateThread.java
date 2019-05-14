package Tools;


import View.AppWindow;
import View.WelcomePanel;

import java.awt.*;

public class TrajectUpdateThread extends Thread {
    WelcomePanel welcomePanel;
    AppWindow appWindow;
    public TrajectUpdateThread(WelcomePanel welcomePanel, AppWindow appWindow){
        this.welcomePanel = welcomePanel;
        this.appWindow = appWindow;
    }

    @Override
    public void run() {
        while(containCmmponent(appWindow.getContentPane().getComponents(), welcomePanel)){
            try {
                welcomePanel.update();
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public boolean containCmmponent(Component[] components, Component component){
        for (Component temp : components){
            if (temp == component)
            {
                return true;
            }
        }
        return false;
    }
}
