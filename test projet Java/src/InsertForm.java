import javax.swing.*;
import java.awt.*;

public class InsertForm extends JPanel {

    public InsertForm(){
        this.setLayout(new BorderLayout());
        this.add(new PanelInscriptionForm(),BorderLayout.NORTH);
    }
}
