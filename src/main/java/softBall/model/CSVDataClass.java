package softBall.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import lombok.SneakyThrows;
import one.util.streamex.EntryStream;
import sun.nio.cs.StreamEncoder;

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
        if (!containsParameter(parameterName.toLowerCase())){
            throw new IllegalArgumentException("You passed parameter that is not contained in this object you stupid fuck.");
        }
        return parameters.get(parameterName.toLowerCase());
    }

    protected abstract boolean containsParameter(String name);
    
    
    @SneakyThrows
    static <T extends CSVDataClass> List<T> readFromCSV(Path fileName, CSVDataBuilder<T> builder) {
        List<String> lines = Files.lines(fileName).collect(Collectors.toList());
        String[] headerFields = lines.get(0).replaceAll("\"", "").split(",");
        List<T> resultList = new ArrayList<>();
        for (String line : lines.subList(1, lines.size()-1)) {
            int num = 0;
            String name = null;
            Map<String, Double> parameterMap = new HashMap<>();
            String[] parameters = line.split(",");
            for (int i = 0; i < headerFields.length; i++) {
                String field = headerFields[i].trim().toLowerCase();
                if (field.equals("#")) {
                    num = Integer.parseInt(parameters[i].trim());
                } else if (field.equals("name")) {
                    name = parameters[i];
                } else {
                    parameterMap.put(field, Double.parseDouble(parameters[i]));
                }
            }
            T dataObj = builder.build(num, name, EntryStream.of(parameterMap).mapKeys(String::toLowerCase).toMap());
            resultList.add(dataObj);
        }
        return resultList;
    }
    
    public interface CSVDataBuilder<T extends CSVDataClass> {
        T build(int num, String name, Map<String, Double> parameters);
    }
    
    public String[] getParameterNames() {
        String[] parameterNames = new String[2 + this.parameters.size()];
        parameterNames[0] = "#";
        parameterNames[1] = "name";
        int i = 2;
        for (Map.Entry<String, Double> entry : this.parameters.entrySet()) {
            parameterNames[i++] = entry.getKey();
        }
        return parameterNames;
    }
    
    public Object[] toArray() {
        Object[] objects = new Object[2 + parameters.size()];
        objects[0] = num;
        objects[1] = name;
        int i = 2;
        for (Map.Entry<String, Double> entry : parameters.entrySet()) {
            objects[i++] = entry.getValue();
        }
        return objects;
    }
    
}
