package softBall;

import javax.swing.*;
import java.awt.*;

/**
 * @author Lucija Raženj
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnterPath path= new EnterPath();
            path.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            path.setVisible(true);
            path.setResizable(true);
            path.setLocation(470,50);

            path.setBackground(Color.black);
           path.pack();

        });
    }
}
