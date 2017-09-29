package softBall.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lucija Raženj
 * @version 1.0
 */
public class CSVLoader<DM extends CSVDataClass> {

    public final List<String> variableOrder;


    public CSVLoader(List<String> variableOrder) {
        this.variableOrder = variableOrder;
    }

    public DM buildModel(String line, DataModelBuilder<DM> builder) {
        String[] parts = line.split("\\,");
        Map<String, Double> variables = new HashMap<>();
        int num = -1;
        String name = "Unknown";
        for (int i = 0; i < variableOrder.size(); i++) {
            String varName = variableOrder.get(i).trim();
            if (varName.equals(Consts.NAME)) {
                name = parts[i].trim();
            }else if(Consts.HASH.equals(varName)) {
                num = Integer.parseInt(parts[i].trim());
            } else{
                variables.put(varName, Double.parseDouble(parts[i].trim()));
            }
        }
        return builder.build(num, name, variables);
    }

    @FunctionalInterface
    public interface DataModelBuilder<DM extends CSVDataClass> {
        public DM build(int num, String name, Map<String, Double> variables);
    }
}
