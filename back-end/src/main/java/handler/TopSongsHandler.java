package handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import server.Server;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TopSongsHandler implements Route{
  private final SpotifyApi spotifyApi;
  public TopSongsHandler(SpotifyApi spotifyApi) {
    this.spotifyApi = spotifyApi;
  }

  public Object handle(Request request, Response response) {
    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
    Map<String, Object> responseMap = new HashMap<>();
    String username = request.queryParams("username");
    String timeRange = request.queryParams("time-range");
    int timeInt;
    if (timeRange.equals("short")) {
      timeInt = 0;
    } else if (timeRange.equals("medium")) {
      timeInt = 1;
    } else {
      timeInt = 2;
    }

    try {
      responseMap.put("songs", Server.getUsers().get(username).getTopTracks(timeInt));
      responseMap.put("result", "success");
    } catch (Exception e) {
      responseMap.put("result", "failure");
      e.printStackTrace();
    }

    return adapter.toJson(responseMap);
  }
}
