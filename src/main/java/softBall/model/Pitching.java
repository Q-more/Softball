package softBall.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @author Lucija Raženj
 * @version 1.0
 */
public class Pitching  extends CSVDataClass {

    public Pitching(int num, String name, Map<String, Double> parameters) {
        super(num, name, parameters);
    }

    @Override
    protected boolean containsParameter(String name) {
        return true;
    }
    
    public static List<Pitching> fromCsv(Path fileName) {
        return readFromCSV(fileName, Pitching::new);
    }
}
