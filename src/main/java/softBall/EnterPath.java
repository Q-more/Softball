package softBall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Lucija Raženj
 * @version 1.0
 */
public class EnterPath extends JFrame {
    private static final String BATTING_PATH = "C:\\Users\\K3T1-3000\\Documents\\softball\\Euroliga zapisnik\\STAT\\1";
    private JTextField textField;
    private JLabel poruka;
    private JButton ok;

    public EnterPath() {
        setTitle("SoftBall");
        poruka = new JLabel("Hello Katarina ! ");
        poruka.setHorizontalTextPosition(JLabel.CENTER);
        add(poruka, BorderLayout.NORTH);
        textField = new JTextField();
        textField.setText(BATTING_PATH);
        textField.setVisible(true);
        add(textField, BorderLayout.CENTER);

        ok = new JButton("OK");
        ok.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    Path path = Paths.get(textField.getText());

                    GetData getData;

                    try{
                        getData = new GetData(path);
                        SwingBatting swingBatting = new SwingBatting(getData.battingMap());
                        swingBatting.setResizable(true);
                        swingBatting.setVisible(true);
                        swingBatting.setLocation(10, 50);
                        swingBatting.pack();

                        SwingPitching swingPitching = new SwingPitching(getData.pitchingMap());
                        swingPitching.setResizable(true);
                        swingPitching.setVisible(true);
                        swingPitching.setLocation(470, 50);
                        swingPitching.pack();

                        SwingFielding swingFielding = new SwingFielding(getData.fieldingMap());
                        swingFielding.setResizable(true);
                        swingFielding.setVisible(true);
                        swingFielding.setLocation(925,50);
                        swingFielding.pack();

                    }catch (Exception ex){
                        textField.setText("Nije uspio pronaci upisani direktorij, pokusaj ponovo.");
                    }



                }).run();
            }
        });
        add(ok, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        EnterPath path = new EnterPath();
        path.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        path.setVisible(true);
        path.setResizable(true);
        path.setLocation(500, 500);
        path.setBackground(Color.black);
        path.pack();
    }

}
