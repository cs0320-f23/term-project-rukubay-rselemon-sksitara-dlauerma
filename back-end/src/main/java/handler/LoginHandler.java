package handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Fetches the redirect URI to get user credentials
 */
public class LoginHandler implements Route {
    public final SpotifyApi spotifyApi;

    public LoginHandler(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }
    public Object handle(Request request, Response response) {
        Moshi moshi = new Moshi.Builder().build();
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", "success");

        //fetch the uri which redirects the user to login page
        AuthorizationCodeUriRequest authorizationCodeUriRequest = this.spotifyApi.authorizationCodeUri()
                .build();
        URI uri = authorizationCodeUriRequest.execute();
        responseMap.put("uri", uri.toString());

        System.out.println(uri);

        return adapter.toJson(responseMap);
    }
}
