package server;

import handler.GetUserCodeHandler;
import handler.LoginHandler;
import handler.MakeUserHandler;
import handler.TopArtistsHandler;
import java.util.Map;
import spark.Spark;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;
import datatypes.ourUser;

import static spark.Spark.after;

/**
 * This class makes the server which makes all the handlers related to the project
 */
public class Server {
    public Map<String, ourUser> users;
    private Map<String, ourUser> getUsers() {
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
        MakeUserHandler makeUserHandler = new MakeUserHandler(spotifyApi);

        Spark.path("/api", () -> {
            Spark.get("/get-user-code", getUserCodeHandler);
            Spark.get("/login", loginHandler);
            Spark.get("/top-artists", topArtistsHandler);
            Spark.get("/make-user", makeUserHandler);
        });

        Spark.init();
        Spark.awaitInitialization();

        System.out.println("Server started at http://localhost:" + port);
    }

}
