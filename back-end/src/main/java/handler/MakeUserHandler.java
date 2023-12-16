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

        // create the requests for short, medium, and long term
        GetUsersTopArtistsRequest getUsersTopArtistsRequestShort = this.spotifyApi.getUsersTopArtists()
            .time_range("medium_term")
            .limit(50)
            .build();
//        GetUsersTopArtistsRequest getUsersTopArtistsRequestMed = this.spotifyApi.getUsersTopArtists()
//                .time_range("medium_term")
//                .limit(50)
//                .build();
//        GetUsersTopArtistsRequest getUsersTopArtistsRequestLong = this.spotifyApi.getUsersTopArtists()
//                .time_range("long_term")
//                .limit(50)
//                .build();

        // fetch all of the artists and store them in the user's profile
        try {
            Paging<Artist> artistsShort = getUsersTopArtistsRequestShort.execute();
            newUser.setTopArtists(List.of(artistsShort.getItems()), 0);
//            Paging<Artist> artistsMed = getUsersTopArtistsRequestMed.execute();
//            newUser.setTopArtists(List.of(artistsMed.getItems()), 1);
//            Paging<Artist> artistsLong = getUsersTopArtistsRequestLong.execute();
//            newUser.setTopArtists(List.of(artistsLong.getItems()),2);
            responseMap.put("result", "success");
        } catch (Exception e) {
            responseMap.put("result", "failure");
            e.printStackTrace();
        }

        // setting the track requests
        GetUsersTopTracksRequest getUsersTopTracksRequestShort = this.spotifyApi.getUsersTopTracks()
            .time_range("short_term")
            .limit(50)
            .offset(0)
            .build();
//        GetUsersTopTracksRequest getUsersTopTracksRequestMed = this.spotifyApi.getUsersTopTracks()
//                .time_range("medium_term")
//                .limit(50)
//                .offset(0)
//                .build();
//        GetUsersTopTracksRequest getUsersTopTracksRequestLong = this.spotifyApi.getUsersTopTracks()
//                .time_range("long_term")
//                .limit(50)
//                .offset(0)
//                .build();

        // executing the requests and storing them
        try {
            Paging<Track> tracksShort = getUsersTopTracksRequestShort.execute();
            newUser.setTopTracks(List.of(tracksShort.getItems()), 0);
//            Paging<Track> tracksMed = getUsersTopTracksRequestMed.execute();
//            newUser.setTopTracks(List.of(tracksMed.getItems()), 1);
//            Paging<Track> tracksLong = getUsersTopTracksRequestLong.execute();
//            newUser.setTopTracks(List.of(tracksLong.getItems()), 2);
        } catch (Exception e) {
            responseMap.put("result", "failure");
            e.printStackTrace();
        }

        // creating maps of top genres for short term
        newUser.setTopGenre(new HashMap<>(), 0);
        for (Artist artist : newUser.getTopArtists(0)) {
            for (String genre : artist.getGenres()) {
                int count = newUser.getTopGenre(0).getOrDefault(genre, 0);
                newUser.getTopGenre(0).put(genre, count);
            }
        }

        // repeat for medium term
//        newUser.setTopGenre(new HashMap<>(), 1);
//        for (Artist artist : newUser.getTopArtists(1)) {
//            for (String genre : artist.getGenres()) {
//                int count = newUser.getTopGenre(1).getOrDefault(genre, 0);
//                newUser.getTopGenre(1).put(genre, count);
//            }
//        }
//
//        // once more for long term
//        newUser.setTopGenre(new HashMap<>(), 2);
//        for (Artist artist : newUser.getTopArtists(2)) {
//            for (String genre : artist.getGenres()) {
//                int count = newUser.getTopGenre(2).getOrDefault(genre, 0);
//                newUser.getTopGenre(2).put(genre, count);
//            }
//        }

        return adapter.toJson(responseMap);
    }
}