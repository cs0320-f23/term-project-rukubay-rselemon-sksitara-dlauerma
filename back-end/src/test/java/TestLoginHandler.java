
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import handler.LoginHandler;
import java.net.URI;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import server.spotifyKeys;
import spark.Spark;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the broadband handler and the datasource class
 */
public class TestLoginHandler {

  private static SpotifyApi spotifyApi;
  /**
   * This method sets up the port number and the logger
   */
  @BeforeAll
  public static void setup_before_everything() {
    Spark.port(3232);
    Logger.getLogger("").setLevel(Level.WARNING);
    String clientId = spotifyKeys.CLIENT_ID;
    String clientSecret = spotifyKeys.CLIENT_SECRET;
    URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3232/api/get-user-code");
    spotifyApi = new SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .setRedirectUri(redirectUri)
        .build();
  }

  /**
   * This method helps set up the url pathways
   */
  @BeforeEach
  public void setup() {
    Spark.get("/api/login", new LoginHandler(spotifyApi));
    Spark.init();
    Spark.awaitInitialization();
  }

  /**
   * This method is for tearing down the pathways
   */
  @AfterEach
  public void teardown() {
    Spark.unmap("/login");
    Spark.awaitStop();
  }

  /**
   * This method helps set up the url's connection with one parameter
   * @param apiCall
   * @return the connection
   * @throws IOException
   */
  static private HttpURLConnection tryRequest(String apiCall) throws IOException {
    URL requestURL = new URL("http://localhost:"+Spark.port()+"/"+apiCall);
    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();
    clientConnection.connect();
    return clientConnection;
  }

  /**
   * This method tests to see if the csv loaded can be successful
   */
  @Test
  public void testSuccessfulLogin() {
    boolean exceptionThrown = false;
    try {
      HttpURLConnection clientConnection = tryRequest("api/login");
      assertEquals(200, clientConnection.getResponseCode());

      Moshi moshi = new Moshi.Builder().build();
      Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
      JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);

      Map<String, Object> response = adapter.fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
      clientConnection.disconnect();
      assertEquals(response.get("result"), "success");
      assertEquals(response.get("uri").toString().substring(0, 53 + spotifyApi.getClientId().length()), "https://accounts.spotify.com:443/authorize?client_id=" + spotifyKeys.CLIENT_ID);
    }
    catch (IOException e) {
      exceptionThrown = Boolean.TRUE;
    }
    assertFalse(exceptionThrown); // making sure no exceptions were thrown in process
  }
}