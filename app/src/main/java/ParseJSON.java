import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseJSON {
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

        JSONParser parser = new JSONParser();
        URL url = new URL("http://www.poznan.pl/mim/plan/map_service.html?mtype=pub_transport&co=cluster");

        try {
            InputStream is = url.openStream();

            String przystankijson = null;
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                przystankijson = scanner.useDelimiter("\\A").next();
            }

            Object obj = parser.parse(przystankijson);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get("features");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String id = (String) json.get("id");
                JSONObject geometry = (JSONObject) json.get("geometry");
                JSONObject properties = (JSONObject) json.get("properties");
                System.out.println("Id przystanku: " + id);
                System.out.println("Nazwa przystanku: " + properties.get("stop_name"));
                System.out.println("Współrzędne: " + geometry.get("coordinates"));
                System.out.println("Strefa: " + properties.get("zone"));
                System.out.println("Head signs: " + properties.get("headsigns") + "\n");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
