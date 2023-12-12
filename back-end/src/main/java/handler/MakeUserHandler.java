package handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.util.List;
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

import datatypes.ourUser;

/**
 * Endpoint which creates and populates a user profile, expects a username and password
 */
public class MakeUserHandler implements Route {
    private final SpotifyApi spotifyApi;
    public MakeUserHandler(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }
    public Object handle(Request request, Response response) {
        Moshi moshi = new Moshi.Builder().build();
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
        Map<String, Object> responseMap = new HashMap<>();

        // user info
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        ourUser newUser = new ourUser(username, password);

//        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
//                .build();
//        try {
//            User user = getCurrentUsersProfileRequest.execute();
//            String spotifyUsername = user.getDisplayName();
//
//        } catch (IOException | SpotifyWebApiException | ParseException e) {
//            e.printStackTrace();
//        }


        GetUsersTopArtistsRequest getUsersTopArtistsRequest = this.spotifyApi.getUsersTopArtists()
                .time_range("medium_term")
                .limit(50)
                .build();

        try {
            Paging<Artist> artists = getUsersTopArtistsRequest.execute();
            newUser.setTopArtists(List.of(artists.getItems()));
            //System.out.println(newUser.getTopArtists().get(0).getName());
            //responseMap.put("artists", artists.getItems());
            responseMap.put("result", "success");
        } catch (Exception e) {
            responseMap.put("result", "failure");
            e.printStackTrace();
        }

        GetUsersTopTracksRequest getUsersTopTracksRequest = this.spotifyApi.getUsersTopTracks()
                .time_range("medium_term")
                .limit(50)
                .offset(0)
                .build();

        try {
            Paging<Track> tracks = getUsersTopTracksRequest.execute();
            newUser.setTopTracks(List.of(tracks.getItems()));
            System.out.println(newUser.getTopArtists().get(0).getName());
        } catch (Exception e) {
            responseMap.put("result", "failure");
            e.printStackTrace();
        }

        return adapter.toJson(responseMap);
    }
}
