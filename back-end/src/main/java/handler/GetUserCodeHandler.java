package handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Gets the spotify code for the user and stores the information to be able to access user info
 */
public class GetUserCodeHandler implements Route {
    private final SpotifyApi spotifyApi;
    private String code;

    public GetUserCodeHandler(SpotifyApi spotifyApi, String code) {
        this.spotifyApi = spotifyApi;
        this.code = code;
    }
    public Object handle(Request request, Response response) {

        this.code = request.queryParams("code");

        Moshi moshi = new Moshi.Builder().build();
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
        Map<String, Object> responseMap = new HashMap<>();

        System.out.println(this.code);
        AuthorizationCodeRequest authorizationCodeRequest = this.spotifyApi.authorizationCode(this.code)
                .build();

        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            this.spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            this.spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            responseMap.put("result", "success");

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            responseMap.put("result", "failure");
            e.printStackTrace();
        }

        return adapter.toJson(responseMap);

    }
}
