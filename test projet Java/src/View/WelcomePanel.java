package View;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    private JLabel line1, line2;

    public WelcomePanel() {
        this.setLayout(new FlowLayout());

        line1 = new JLabel("Bienvenue sur notre application de gestion de taxis.");
        line2 = new JLabel("Par Grégoire Dylan et Housiaux Louis");

        this.add(line1);
        this.add(line2);
    }

}
