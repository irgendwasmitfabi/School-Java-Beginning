import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) throws IOException {

        //Welcome message
        System.out.println("   ,\n" +
                "_,,)\\.~,,._\n" +
                "(()`  ``)\\))),,_\n" +
                " |     \\ ''((\\)))),,_          ____\n" +
                " |6`   |   ''((\\())) \"-.____.-\"    `-.-,\n" +
                " |    .'\\    ''))))'                  \\)))\n" +
                " |   |   `.     ''                     ((((\n" +
                " \\, _)     \\/                          |))))\n" +
                "  `'        |                          (((((\n" +
                "            \\                  |       ))))))\n" +
                "             `|    |           ,\\     /((((((\n" +
                "              |   / `-.______.<  \\   |  )))))\n" +
                "              |   |  /         `. \\  \\  ((((\n" +
                "              |  / \\ |           `.\\  | (((\n" +
                "              \\  | | |             )| |  ))\n" +
                "               | | | |             || |  '\n" +
                "       \t       | | | |             || |  \n" +
                "       \t       | | | |             || |  \n" +
                "       \t       | | | |             || |  \n" +
                "       \t       | | | |             || |  ");
        System.out.println("********************");
        System.out.println("Was möchtest du tun?");
        System.out.println("********************");
        System.out.println("1. Drucksensor auslesen");
        System.out.println("2. Klasse auslesen & JSON speichern");
        System.out.println("3. Begrüßung ausgeben");
        System.out.println("4. Höhenunterschied ausgeben");
        System.out.println("5. Person erstellen");

        Scanner scanner = new Scanner(System.in);
        String welcomeInput = scanner.nextLine();

        switch (welcomeInput){
            case "1":
                //Drucksensor auslesen
                System.out.println("Drucksensor auslesen");
                GetPressure();
                break;
            case "2":
                //Klasse auslesen & JSON speichern
                System.out.println("Klasse auslesen & JSON speichern");
                SchoolClassJSON();
                break;
            case "3":
                //Begrüßung ausgeben
                System.out.println("Begrüßung ausgeben");
                Greeting();
                break;
            case "4":
                //Höhenunterschied ausgeben
                System.out.println("Höhenunterschied ausgeben");
                GetHeightDifference();
                break;
                case "5":
                    //Person erstellen
                    System.out.println("Person erstellen");
                    VariableTest();
                break;

        }
    }

    //Variables
    static String PhyPhoxUrl = "http://172.20.115.158/get?pressure";

    //Get pressure from PhyPhox
    public static void GetPressure() throws IOException {
        StringBuilder result = GetConnection();

        System.out.println("HTTP Responce: "+result);
        JSONObject obj = new JSONObject(result.toString());
        System.out.println("Druck ist:"+obj.getJSONObject("buffer").getJSONObject("pressure").getJSONArray("buffer").getDouble(0)+" hPa." + " "+ "updateMode" + " " + obj.getJSONObject("buffer").getJSONObject("pressure").getString("updateMode") +"\n");
    }

    public static void GetHeightDifference() throws IOException {
        StringBuilder result = GetConnection();
        Double measureOne = 0.0;
        Double measureTwo = 0.0;

        //Measure one
        try{
            JSONObject obj = new JSONObject(result.toString());
            measureOne = obj.getJSONObject("buffer").getJSONObject("pressure").getJSONArray("buffer").getDouble(0);
            System.out.println("Erste Messung:" + measureOne);
        }
        catch (Exception e){
            System.out.println("Error: "+e);
        }

        Scanner wait = new Scanner(System.in);
        System.out.println("Bereit? Enter drücken");
        wait.nextLine();

        //Measure two
        try{
            StringBuilder result2 = GetConnection();
            JSONObject obj2 = new JSONObject(result2.toString());
            measureTwo = obj2.getJSONObject("buffer").getJSONObject("pressure").getJSONArray("buffer").getDouble(0);
            System.out.println("Zweite Messung:" + measureTwo);
        }
        catch (Exception e){
            System.out.println("Error: "+e);
        }

        //Get Height difference between two pressure values
        Double heightDifference = 44330 * (1 - Math.pow((measureTwo / measureOne), 1 / 5.255));
        System.out.println("Höhenunterschied: "+ heightDifference +" m");
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

    public static void Greeting() throws IOException {
        //Variables
        String name = "";
        String salutation = "";

        System.out.println("Wie ist Ihr Nachname? \n");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
        System.out.println("Wie ist Ihre Anrede? \n");
        Scanner scanner1 = new Scanner(System.in);
        salutation = scanner.nextLine();

        //Output
        System.out.println("Hallo " + salutation + " " + name + "!");

    }

    public static StringBuilder GetConnection() throws IOException {
        URL url = new URL(PhyPhoxUrl);    // URL to Parse
        HttpURLConnection con = (HttpURLConnection) url.openConnection();   // Open Connection
        Scanner reader = new Scanner(con.getInputStream());  // Get the input data
        StringBuilder inputLine= new StringBuilder();   // Create a string to store the data
        while (reader.hasNext()) {  // Loop through the data
            inputLine.append(reader.nextLine());    // Add the data to the string
        }
        reader.close(); // Close the reader
        return inputLine;
    }

    public static void VariableTest(){
        //Variables
        String name = "";
        String address = "";
        Integer postalCode = 0;
        String City = "";
        List<String> person = new ArrayList<String>();

        //Input
        System.out.println("Wie ist Ihr Name? \n");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
        person.add(name);
        System.out.println("Wie ist Ihre Adresse? \n");
        Scanner scanner1 = new Scanner(System.in);
        address = scanner.nextLine();
        person.add(address);
        System.out.println("Wie ist Ihre Postleitzahl? \n");
        Scanner scanner2 = new Scanner(System.in);
        postalCode = scanner.nextInt();
        person.add(postalCode.toString());
        System.out.println("Wie ist Ihre Stadt? \n");
        Scanner scanner3 = new Scanner(System.in);
        City = scanner3.nextLine();
        person.add(City);
        //Output
        System.out.println(person);
    }
}
