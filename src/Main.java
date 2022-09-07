import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) throws IOException {

        URL url = new URL("http://172.20.115.158/get?pressure");    // URL to Parse
        HttpURLConnection con = (HttpURLConnection) url.openConnection();   // Open Connection
        System.out.println("\nSending 'GET' request to URL : " + url);   // Print URL
        Scanner reader = new Scanner(con.getInputStream());  // Get the input data
        StringBuilder inputLine= new StringBuilder();   // Create a string to store the data
        while (reader.hasNext()) {  // Loop through the data
            inputLine.append(reader.nextLine());    // Add the data to the string
        }
        reader.close(); // Close the reader
        System.out.println("HTTP Responce: "+inputLine);
        JSONObject obj = new JSONObject(inputLine.toString());
        System.out.println("Druck ist: "+obj.getJSONObject("buffer").getJSONObject("pressure").getJSONArray("buffer").getDouble(0)+" mBAR\n");
        System.out.println("Jetzt kommt der UpdateMode: \n");

        //Print JSON attributes from updatemode
        System.out.println(obj);

        Scanner inputs = new Scanner(System.in);
        String proceed = inputs.nextLine();

        System.out.println("Möchtest du deine Schulklasse sehen? y/n");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        if(Objects.equals(input, "y")){
            SchoolClassJSON();
        }
        else{
            System.out.println("Okay, dann nicht.");
        }
    }

    public static void SchoolClassJSON() throws IOException {
        System.out.println("Schulklasse: \n");
        //Create JSON Object schoolclass
        JSONObject schoolclass = new JSONObject();
        schoolclass.put("name", "FIAE22M");
        schoolclass.put("year", 2022);
        schoolclass.put("students", (new String[]{"Paul", "Lauris", "Fabi"}));
        schoolclass.put("teacher", (new String[]{"Frau", "SP", "Spang, freundlich"}));
        System.out.println(schoolclass);

        FileWriter schoolclassFile = new FileWriter("schoolclass.json");
        schoolclassFile.write(schoolclass.toString());
        schoolclassFile.close();
    }
}
