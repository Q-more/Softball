package softBall.model;

import java.util.Map;

/**
 * @author Lucija Raženj
 * @version 1.0
 */
public class Batting extends CSVDataClass{

    public Batting(int num, String name, Map<String, Double> parameters) {
        super(num, name, parameters);
    }

    @Override
    protected boolean containsParameter(String name) {
        return true;
    }
}
