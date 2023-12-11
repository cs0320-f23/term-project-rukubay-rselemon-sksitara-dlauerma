package server;

import handler.GetUserCodeHandler;
import handler.LoginHandler;
import spark.Spark;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

import static spark.Spark.after;

/**
 * This class makes the server which makes all the handlers related to the project
 */
public class Server {

    private static final String clientId = "9c97352297bb4fa69f00cde739367e5e";

    private static final String clientSecret = "cea87719a9394c28a22492fac7df0416";

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http:localhost:8080/api/get-user-code");

    private static final String code = "";

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



        GetUserCodeHandler GetUserCodeHandler = new GetUserCodeHandler();
        LoginHandler LoginHandler = new LoginHandler(spotifyApi);

        Spark.path("/api", () -> {
            Spark.get("/get-user-code", GetUserCodeHandler);
            Spark.get("/login", LoginHandler);
        });

        Spark.init();
        Spark.awaitInitialization();

        System.out.println("Server started at http://localhost:" + port);
    }

}
