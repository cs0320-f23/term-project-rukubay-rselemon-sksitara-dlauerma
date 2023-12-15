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
                .time_range("short_term")
                .limit(50)
                .build();
        GetUsersTopArtistsRequest getUsersTopArtistsRequestMed = this.spotifyApi.getUsersTopArtists()
                .time_range("medium_term")
                .limit(50)
                .build();
        GetUsersTopArtistsRequest getUsersTopArtistsRequestLong = this.spotifyApi.getUsersTopArtists()
                .time_range("long_term")
                .limit(50)
                .build();

        // fetch all of the artists and store them in the user's profile
        try {
            Paging<Artist> artistsShort = getUsersTopArtistsRequestShort.execute();
            newUser.setTopArtistsShort(List.of(artistsShort.getItems()));
            Paging<Artist> artistsMed = getUsersTopArtistsRequestMed.execute();
            newUser.setTopArtistsMed(List.of(artistsMed.getItems()));
            Paging<Artist> artistsLong = getUsersTopArtistsRequestLong.execute();
            newUser.setTopArtistsLong(List.of(artistsLong.getItems()));
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
        GetUsersTopTracksRequest getUsersTopTracksRequestMed = this.spotifyApi.getUsersTopTracks()
                .time_range("medium_term")
                .limit(50)
                .offset(0)
                .build();
        GetUsersTopTracksRequest getUsersTopTracksRequestLong = this.spotifyApi.getUsersTopTracks()
                .time_range("long_term")
                .limit(50)
                .offset(0)
                .build();

        // executing the requests and storing them
        try {
            Paging<Track> tracksShort = getUsersTopTracksRequestShort.execute();
            newUser.setTopTracksShort(List.of(tracksShort.getItems()));
            Paging<Track> tracksMed = getUsersTopTracksRequestMed.execute();
            newUser.setTopTracksMed(List.of(tracksMed.getItems()));
            Paging<Track> tracksLong = getUsersTopTracksRequestLong.execute();
            newUser.setTopTracksLong(List.of(tracksLong.getItems()));
        } catch (Exception e) {
            responseMap.put("result", "failure");
            e.printStackTrace();
        }

        // creating maps of top genres for short term
        newUser.setTopGenreShort(new HashMap<>());
        for (Artist artist : newUser.getTopArtistsShort()) {
            for (String genre : artist.getGenres()) {
                int count = newUser.getTopGenreShort().getOrDefault(genre, 0);
                newUser.getTopGenreShort().put(genre, count);
            }
        }

        // repeat for medium term
        newUser.setTopGenreMed(new HashMap<>());
        for (Artist artist : newUser.getTopArtistsMed()) {
            for (String genre : artist.getGenres()) {
                int count = newUser.getTopGenreMed().getOrDefault(genre, 0);
                newUser.getTopGenreMed().put(genre, count);
            }
        }

        // once more for long term
        newUser.setTopGenreLong(new HashMap<>());
        for (Artist artist : newUser.getTopArtistsLong()) {
            for (String genre : artist.getGenres()) {
                int count = newUser.getTopGenreLong().getOrDefault(genre, 0);
                newUser.getTopGenreLong().put(genre, count);
            }
        }

        return adapter.toJson(responseMap);
    }
}
