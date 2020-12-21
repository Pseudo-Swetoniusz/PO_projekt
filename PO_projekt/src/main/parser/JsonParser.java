package parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonParser {

    public static MapStructure readFromJson(String filePath) {
        JSONParser parser = new JSONParser();
        MapStructure mapProperties = null;

        try {
            FileReader reader = new FileReader(filePath);
            Object object = parser.parse(reader);
            JSONObject map = (JSONObject) object;
            int width = (int) (long) map.get("width");
            int height = (int) (long) map.get("height");
            int startEnergy = (int) (long) map.get("startEnergy");
            int moveEnergy = (int) (long) map.get("moveEnergy");
            int plantEnergy = (int) (long) map.get("plantEnergy");
            double jungleRatio = (Double) map.get("jungleRatio");
            int numberOfFirstAnimals = (int) (long) map.get("numberOfFirstAnimals");

            mapProperties =  new MapStructure(width,height,startEnergy,moveEnergy,plantEnergy,jungleRatio, numberOfFirstAnimals);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return mapProperties;
    }
}
