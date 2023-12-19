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

    GetUsersTopTracksRequest getUsersTopSongsRequest = this.spotifyApi.getUsersTopTracks()
        .time_range("medium_term")
        .limit(10)
        .build();
    try {
      Paging<Track> songs = getUsersTopSongsRequest.execute();
      responseMap.put("songs", songs.getItems());
      responseMap.put("result", "success");
    } catch (Exception e) {
      responseMap.put("result", "failure");
      e.printStackTrace();
    }

    return adapter.toJson(responseMap);
  }
}
