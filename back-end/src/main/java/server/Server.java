package server;

import handler.APIHandler;
import spark.Spark;

import static spark.Spark.after;

/**
 * This class makes the server which makes all the handlers related to the project
 */
public class Server {

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

        APIHandler APIHandler = new APIHandler();
        Spark.get("api", APIHandler);

        Spark.path("/api", () -> {
            Spark.get("/get-user-code", APIHandler);
        });

        Spark.init();
        Spark.awaitInitialization();

        System.out.println("Server started at http://localhost:" + port);
    }

}
