package softBall;

import softBall.model.Batting;
import softBall.model.Fielding;
import softBall.model.Pitching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author Lucija Raženj
 * @version 1.0
 */
public class GetData {

    DataLoader batters;

    public GetData(Path path) throws IOException {

        batters = new DataLoader();

            Files.walkFileTree(path, batters);

    }

    public Map<String, Batting> battingMap() {
        return batters.getBattingMap();
    }

    public Map<String, Fielding> fieldingMap() {
        return batters.getFieldingMap();
    }

    public Map<String, Pitching> pitchingMap() {
        return batters.getPitchingMap();
    }
}
