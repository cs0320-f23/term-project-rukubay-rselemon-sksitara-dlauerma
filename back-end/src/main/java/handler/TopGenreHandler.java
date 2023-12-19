package handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.util.ArrayList;
import java.util.List;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import statistics.Statistics;

public class TopGenreHandler implements Route{
  private final SpotifyApi spotifyApi;
  public TopGenreHandler(SpotifyApi spotifyApi) {
    this.spotifyApi = spotifyApi;
  }

  public Object handle(Request request, Response response) {
    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
    Map<String, Object> responseMap = new HashMap<>();

    GetUsersTopArtistsRequest getUsersTopArtistsRequest = this.spotifyApi.getUsersTopArtists()
        .time_range("medium_term")
        .limit(10)
        .build();
    try {
      Paging<Artist> artists = getUsersTopArtistsRequest.execute();
      List<Artist> artistList = List.of(artists.getItems());
      List<String> genreList = new ArrayList<>();
      for (Artist artist : artistList) {
        genreList.addAll(List.of(artist.getGenres()));
      }

      Map<String, Float> frequencyMap = new HashMap<>();
      // Count the frequencies
      for (String str : genreList) {
        frequencyMap.put(str, frequencyMap.getOrDefault(str, 0.f) + 1.f);
      }
      Statistics stats = new Statistics();
      List<Map.Entry<String, Float>> rankedGenres = stats.rankedList(frequencyMap);
      List<String> rankedList = new ArrayList<>();
      for (Map.Entry<String, Float> a : rankedGenres){
        rankedList.add(a.getKey());
      }
      System.out.println(rankedGenres);
      responseMap.put("genres", rankedList);
      responseMap.put("result", "success");
    } catch (Exception e) {
      responseMap.put("result", "failure");
      e.printStackTrace();
    }

    return adapter.toJson(responseMap);
  }
}

