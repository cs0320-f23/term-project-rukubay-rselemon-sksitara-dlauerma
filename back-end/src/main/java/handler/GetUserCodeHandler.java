package handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import se.michaelthelin.spotify.SpotifyApi;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GetUserCodeHandler implements Route {
    private final SpotifyApi spotifyApi;
    private final String code;

    public GetUserCodeHandler(SpotifyApi spotifyApi, String code) {
        this.spotifyApi = spotifyApi;
        this.code = code;
    }
    public Object handle(Request request, Response response) {
        Moshi moshi = new Moshi.Builder().build();
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", "success");



        return adapter.toJson(responseMap);

    }
}
