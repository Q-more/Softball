package softBall.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author Lucija Raženj
 * @version 1.0
 */
@Data
@EqualsAndHashCode(of = "name")
public abstract class CSVDataClass {

    private final int num;
    private final String name;
    private final Map<String, Double> parameters;

    public double getParameter(String parameterName) {
        if (!containsParameter(parameterName)){
            throw new IllegalArgumentException("You passed parameter that is not contained in this object you stupid fuck.");
        }
        return parameters.get(parameterName);
    }

    protected abstract boolean containsParameter(String name);
}
