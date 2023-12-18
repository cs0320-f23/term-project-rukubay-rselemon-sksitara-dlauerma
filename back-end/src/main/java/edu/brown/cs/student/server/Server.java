package edu.brown.cs.student.server;

import edu.brown.cs.student.handler.ComputeStatisticsHandler;
import edu.brown.cs.student.handler.GetUserCodeHandler;
import edu.brown.cs.student.handler.LoginHandler;
import edu.brown.cs.student.handler.MakeUserHandler;
import edu.brown.cs.student.handler.TopArtistsHandler;

import java.util.HashMap;
import java.util.Map;
import spark.Spark;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;
import edu.brown.cs.student.datatypes.ourUser;

import static spark.Spark.after;

/**
 * This class makes the edu.brown.cs.student.server which makes all the handlers related to the project
 */
public class Server {
    public static Map<String, ourUser> users = new HashMap<>();
    public static Map<String, ourUser> getUsers() {
        return users;
    }
    public static void addUser(String name, ourUser user) {
        users.put(name, user);
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

    /**
     * This method constructs the edu.brown.cs.student.server and instantiates the instance variables and the port number. It also makes all
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
        MakeUserHandler makeUserHandler = new MakeUserHandler(spotifyApi);
        ComputeStatisticsHandler computeStatisticsHandler = new ComputeStatisticsHandler();

        Spark.path("/api", () -> {
            Spark.get("/get-user-code", getUserCodeHandler);
            Spark.get("/login", loginHandler);
            Spark.get("/top-artists", topArtistsHandler);
            Spark.get("/make-user", makeUserHandler);
            Spark.get("/compute-edu.brown.cs.student.statistics", computeStatisticsHandler);
        });

        Spark.init();
        Spark.awaitInitialization();

        System.out.println("Server started at http://localhost:" + port);
    }

}
