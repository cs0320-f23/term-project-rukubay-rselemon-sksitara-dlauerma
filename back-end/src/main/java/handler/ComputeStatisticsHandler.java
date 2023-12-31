package handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import datatypes.ourUser;
import java.util.ArrayList;
import java.util.List;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import server.Server;

import statistics.Statistics;


/**
 * Fetches the redirect URI to get user credentials
 */
public class ComputeStatisticsHandler implements Route {
  public ComputeStatisticsHandler(){}

  public Object handle(Request request, Response response) {
      Map<String, Float> overlaps = new HashMap<>();
      Moshi moshi = new Moshi.Builder().build();
      Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
      JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
      Map<String, Object> responseMap = new HashMap<>();
    try {

      String feature = request.queryParams("compare-by");
      String username = request.queryParams("username");

      int timeRange = switch (request.queryParams("time-range")) {
        case "short" -> 0;
        case "medium" -> 1;
        default -> 2;
      };

      ourUser user = Server.getUsers().get(username);

      switch (feature) {
        case "artists" -> {
          Statistics<Artist> stats = new Statistics<>();
          for (Map.Entry<String, ourUser> otherUser : Server.getUsers().entrySet()) {
            if (!otherUser.getKey().equals(username)) {
              float overlap = stats.computeOverlap(user.getTopArtists(timeRange),
                      otherUser.getValue().getTopArtists(timeRange));
              overlaps.put(otherUser.getKey(), overlap);
            }
          }
        }
        case "songs" -> {
          Statistics<Track> stats = new Statistics<>();
          for (Map.Entry<String, ourUser> otherUser : Server.getUsers().entrySet()) {
            if (!otherUser.getKey().equals(username)) {
              float overlap = stats.computeOverlap(user.getTopTracks(timeRange),
                      otherUser.getValue().getTopTracks(timeRange));
              overlaps.put(otherUser.getKey(), overlap);
            }
          }
        }
        case "genres" -> {
          Statistics<String> stats = new Statistics<>();
          for (Map.Entry<String, ourUser> otherUser : Server.getUsers().entrySet()) {
            if (!otherUser.getKey().equals(username)) {
              float overlap = stats.computeOverlap(user.getTopGenre(timeRange),
                  otherUser.getValue().getTopGenre(timeRange));
              overlaps.put(otherUser.getKey(), overlap);
            }
          }
        }
      }
      responseMap.put("overlaps", overlaps);
      responseMap.put("result", "success");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return adapter.toJson(responseMap);
  }
}
//  public List<Float> computeWeightedMatches(List<List<Float>> featureWiseOverlaps, List<Float> weights){
//    List<Float> overlaps = new ArrayList<Float>();
//    for (List<Float> o : featureWiseOverlaps) {
//      float sum = 0;
//      for (int i = 0; i < weights.size(); i++) {
//        sum += weights.get(i) * o.get(i);
//      }
//      overlaps.add(sum);
//    }
//    return overlaps;
//  }