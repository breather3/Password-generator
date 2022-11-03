package PasswdGen;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class Log {
    public void Write(String name, String username, String passwd){
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("password", passwd);
        try (FileWriter file = new FileWriter(name)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter file = new FileWriter("ListOfSites", true)){
            file.write(name + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String ReadUsername(String name){
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(name)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String username = (String) jsonObject.get("username");
            return username;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String ReadPassword(String name){
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(name)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String passwd = (String) jsonObject.get("password");
            return passwd;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("chyba");
        return "";
    }
}
