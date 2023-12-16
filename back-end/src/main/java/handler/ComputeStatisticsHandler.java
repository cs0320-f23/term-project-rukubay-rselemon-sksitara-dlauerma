package handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import datatypes.ourUser;
import java.util.ArrayList;
import java.util.List;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import server.Server;


/**
 * Fetches the redirect URI to get user credentials
 */
public class ComputeStatisticsHandler implements Route {
  private String feature;
  private String username;
  private int timeRange;
  private ourUser user;
  private List<String> rawData;

  private Map<String, Float> overlaps;
  public ComputeStatisticsHandler(){}

  public Object handle(Request request, Response response) {
    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
    Map<String, Object> responseMap = new HashMap<>();

    feature = request.queryParams("compare-by");
    username = request.queryParams("username");
    switch (request.queryParams("time-range")){
      case "short":
        timeRange = 0;
      case "medium":
        timeRange = 1;
      case "long":
        timeRange = 2;
    }
    user = Server.getUsers().get(username);

    if (feature.equals("artists")) {
      for (Map.Entry<String, ourUser> otherUser : Server.getUsers().entrySet()) {
        float overlap = computeOverlap(user.getTopArtists(timeRange),
            otherUser.getValue().getTopArtists(timeRange));
        overlaps.put(otherUser.getKey(), overlap);
      }
    }
    responseMap.put("result", "success");
    responseMap.put("overlaps", overlaps);
    return adapter.toJson(responseMap);
  }
  public float computeOverlap(List<Artist> user1, List<Artist> user2) {
    float sum = 0;
    for (Artist a : user1) {
      if (user2.contains(a)){
        sum++;
        user2.remove(a);
      }
    }
    return (!user1.isEmpty() && !user2.isEmpty()) ? sum / user1.size() : 0;
  }

  public List<Float> computeWeightedMatches(List<List<Float>> featureWiseOverlaps, List<Float> weights){
    List<Float> overlaps = new ArrayList<Float>();
    for (List<Float> o : featureWiseOverlaps) {
      float sum = 0;
      for (int i = 0; i < weights.size(); i++) {
        sum += weights.get(i) * o.get(i);
      }
      overlaps.add(sum);
    }
    return overlaps;
  }
}