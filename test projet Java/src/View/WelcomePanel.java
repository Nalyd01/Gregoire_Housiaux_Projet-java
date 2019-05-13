package View;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    private JLabel line1, line2;
    private Font font;

    public WelcomePanel() {
        this.setLayout(new FlowLayout());

        line1 = new JLabel("Bienvenue sur notre application de gestion de taxis.");
        line2 = new JLabel("Par Grégoire Dylan et Housiaux Louis");

        font = new Font("Arial",Font.BOLD,14);

        line1.setFont(font);
        line2.setFont(font);

        this.add(line1);
        this.add(line2);
    }

}
