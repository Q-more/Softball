package softBall;

import java.nio.file.Files;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import softBall.model.Batting;
import softBall.model.CSVLoader;

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
                        DataLoader loader = new DataLoader();
                        Files.walkFileTree(path, loader);
                        
                        
                        SwingBatting swingBatting = new SwingBatting(loader.getBatterHeaders(), loader.getBattingMap());
                        swingBatting.setResizable(true);
                        swingBatting.pack();
                        swingBatting.setLocation(10, 50);
                        swingBatting.setVisible(true);

                        SwingPitching swingPitching = new SwingPitching(loader.getPitchingHeaders(), loader.getPitchingMap());
                        swingPitching.setResizable(true);
                        swingPitching.setLocation(470, 50);
                        swingPitching.pack();
                        swingPitching.setVisible(true);

                        SwingFielding swingFielding = new SwingFielding(loader.getFieldingHeaders(), loader.getFieldingMap());
                        swingFielding.setResizable(true);
                        swingFielding.setLocation(925,50);
                        swingFielding.pack();
                        swingFielding.setVisible(true);

                    }catch (Exception ex){
                        textField.setText("Nije uspio pronaci upisani direktorij, pokusaj ponovo.");
                        ex.printStackTrace();
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
