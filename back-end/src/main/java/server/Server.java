package server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import handler.ComputeStatisticsHandler;
import handler.GetUserCodeHandler;
import handler.LoginHandler;
import handler.MakeUserHandler;
import handler.TopArtistsHandler;

import handler.TopGenreHandler;
import handler.TopSongsHandler;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import spark.Spark;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

import datatypes.ourUser;

import static spark.Spark.after;

/**
 * This class makes the server which makes all the handlers related to the project
 * It contains the key shared states, spotifyApi and the users map of our users
 */
public class Server {
    // this map contains all of our users and is serialized every time a new one is added
    public static Map<String, ourUser> users = deserializeHashMap("data/users.ser");
    public static Map<String, ourUser> getUsers() { return users; }
    public static void addUser(String name, ourUser user) {
        users.put(name, user);
        serializeHashMap(users,"data/users.ser");
    }

    private static final String clientId = spotifyKeys.CLIENT_ID;

    private static final String clientSecret = spotifyKeys.CLIENT_SECRET;

//    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3000/dashboard");
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3232/api/get-user-code");

    private String code = "";

    //this api object handles all of the spotify interaction and is shared by handlers
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    /**
     * This method constructs the server and instantiates the instance variables and the port number. It also makes all
     * the handlers which basically handle all the user stories and goals of the program
     */
    public Server() {

        int port = 3232;

        Spark.port(port);
        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "*");
        });


        GetUserCodeHandler getUserCodeHandler = new GetUserCodeHandler(spotifyApi, code);
        LoginHandler loginHandler = new LoginHandler(spotifyApi);
        TopArtistsHandler topArtistsHandler = new TopArtistsHandler(spotifyApi);
        TopSongsHandler topSongsHandler = new TopSongsHandler(spotifyApi);
        TopGenreHandler topGenresHandler = new TopGenreHandler(spotifyApi);
        MakeUserHandler makeUserHandler = new MakeUserHandler(spotifyApi);
        ComputeStatisticsHandler computeStatisticsHandler = new ComputeStatisticsHandler();

        Spark.path("/api", () -> {
            Spark.get("/get-user-code", getUserCodeHandler);
            Spark.get("/login", loginHandler);
            Spark.get("/top-artists", topArtistsHandler);
            Spark.get("/top-songs", topSongsHandler);
            Spark.get("/top-genres", topGenresHandler);
            Spark.get("/make-user", makeUserHandler);
            Spark.get("/compute-statistics", computeStatisticsHandler);
        });

        Spark.init();
        Spark.awaitInitialization();

        System.out.println("Server started at http://localhost:" + port);
    }

    /**
     * Helper function to retain users
     * @param hashMap the map to serialize
     * @param filename where to do it
     */
    private static void serializeHashMap(Map<String, ourUser> hashMap, String filename) {
        Moshi moshi = new Moshi.Builder().build();
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, ourUser.class);
        JsonAdapter<Map<String, ourUser>> adapter = moshi.adapter(mapStringObject);

        try {
            String json = adapter.toJson(hashMap);
            // Write the JSON to a file
            Files.writeString(Path.of(filename), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper which reads the user map out of a file
     * @param filename file where the users are
     * @return
     */
    private static Map<String, ourUser> deserializeHashMap(String filename) {
        Moshi moshi = new Moshi.Builder().build();
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, ourUser.class);
        JsonAdapter<Map<String, ourUser>> adapter = moshi.adapter(mapStringObject);

        Map<String, ourUser> hashMap = new HashMap<>();

        try (Reader reader = new FileReader(filename)) {
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            String json = stringBuilder.toString();
            hashMap = adapter.fromJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}

