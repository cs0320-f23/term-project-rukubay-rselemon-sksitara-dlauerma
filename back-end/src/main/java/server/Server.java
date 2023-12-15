package server;

<<<<<<< Updated upstream
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import datasource.MockCensusDatasource;
=======
import handler.ComputeStatisticsHandler;
import handler.GetUserCodeHandler;
import handler.LoginHandler;
import handler.MakeUserHandler;
import handler.TopArtistsHandler;
import java.util.Map;
>>>>>>> Stashed changes
import spark.Spark;
import datasource.APICensusDatasource;
import handler.BroadbandHandler;
import handler.LoadHandler;
import handler.SearchHandler;
import handler.ViewHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static spark.Spark.after;

/**
 * This class makes the server which makes all the handlers related to the project
 */
public class Server {
<<<<<<< Updated upstream
    private static ArrayList<List<String>> loadedCSV;
    private static List<String> header;
    private static Boolean hasHeader;
    private static Boolean isLoaded;
=======
    private static Map<String, ourUser> users;
    public static Map<String, ourUser> getUsers() {
        return users;
    }

    private void setUsers(Map<String, ourUser> users) {
        this.users = users;
    }

    private static final String clientId = spotifyKeys.CLIENT_ID;

    private static final String clientSecret = spotifyKeys.CLIENT_SECRET;

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3232/api/get-user-code");

    private String code = "";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
>>>>>>> Stashed changes

    /**
     * This method constructs the server and instantiates the instance variables and the port number. It also makes all
     * the handlers which basically handle all the user stories and goals of the program
     */
    public Server() {
        loadedCSV = new ArrayList<>();
        header = new ArrayList<>();
        hasHeader = Boolean.TRUE;
        isLoaded = Boolean.FALSE;

        int port = 3232;

        Spark.port(port);
        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "*");
        });

        LoadHandler loadHandler = new LoadHandler();
        Spark.get("loadcsv", loadHandler);

<<<<<<< Updated upstream
        ViewHandler viewHandler = new ViewHandler();
        Spark.get("viewcsv", viewHandler);

        SearchHandler searchHandler = new SearchHandler();
        Spark.get("searchcsv", searchHandler);

        Cache<String, String[]> cache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();

        BroadbandHandler broadbandHandler = new BroadbandHandler(cache, new APICensusDatasource());
        Spark.get("broadband", broadbandHandler);
=======
        GetUserCodeHandler getUserCodeHandler = new GetUserCodeHandler(spotifyApi, code);
        LoginHandler loginHandler = new LoginHandler(spotifyApi);
        TopArtistsHandler topArtistsHandler = new TopArtistsHandler(spotifyApi);
        MakeUserHandler makeUserHandler = new MakeUserHandler(spotifyApi);
        ComputeStatisticsHandler computeStatisticsHandler = new ComputeStatisticsHandler();

        Spark.path("/api", () -> {
            Spark.get("/get-user-code", getUserCodeHandler);
            Spark.get("/login", loginHandler);
            Spark.get("/top-artists", topArtistsHandler);
            Spark.get("/make-user", makeUserHandler);
            Spark.get("/compute-statistics", computeStatisticsHandler);
        });
>>>>>>> Stashed changes

        Spark.init();
        Spark.awaitInitialization();

        System.out.println("Server started at http://localhost:" + port);
    }

    /**
     * This method returns the loaded csv
     * @return loaded csv as a list of lists
     */
    public static ArrayList<List<String>> getLoadedCSV() {
        return loadedCSV;
    }

    /**
     * Sets the instance variable loaded csv to have a new value
     * @param newLoadedCSV is the csv as a list of lists
     */
    public static void setLoadedCSV(ArrayList<List<String>> newLoadedCSV) {
        loadedCSV = newLoadedCSV;
    }

    /**
     * Sets the newCSVHeader instance variable to have a new value
     * @param newCSVHeader is the new value
     */
    public static void setCSVHeader(List<String> newCSVHeader) {
        header = newCSVHeader;
    }

    /**
     * The method sets the hasHeader instance variable
     * @param bool is the new value
     */
    public static void setHasHeader(Boolean bool) {hasHeader = bool; }

    /**
     * This method gets the header of the loaded csv file
     * @return the header
     */
    public static List<String> getHeader() { return header; }

    /**
     * This method returns the boolean hasHeader
     * @return the hasHeader variable
     */
    public static Boolean getHasHeader() { return hasHeader; }

    /**
     * This method tells the program if the csv is loaded or not
     * @return isLoaded instance variable
     */
    public static Boolean getIsLoaded() {
        return isLoaded;
    }

    /**
     * This method sets the isLoaded variable to the parameter
     * @param value is the new value
     */
    public static void setIsLoaded(Boolean value) {
        isLoaded = value;
    }


}
