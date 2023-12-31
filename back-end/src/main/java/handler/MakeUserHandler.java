package handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import server.Server;
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

        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
                .build();
        String spotifyUsername = "";
        try {
            User user = getCurrentUsersProfileRequest.execute();
            spotifyUsername = user.getDisplayName();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
        }

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
            newUser.setTopArtists(List.of(artistsShort.getItems()), 0);
            Paging<Artist> artistsMed = getUsersTopArtistsRequestMed.execute();
            newUser.setTopArtists(List.of(artistsMed.getItems()), 1);
            Paging<Artist> artistsLong = getUsersTopArtistsRequestLong.execute();
            newUser.setTopArtists(List.of(artistsLong.getItems()),2);
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
            newUser.setTopTracks(List.of(tracksShort.getItems()), 0);
            Paging<Track> tracksMed = getUsersTopTracksRequestMed.execute();
            newUser.setTopTracks(List.of(tracksMed.getItems()), 1);
            Paging<Track> tracksLong = getUsersTopTracksRequestLong.execute();
            newUser.setTopTracks(List.of(tracksLong.getItems()), 2);
        } catch (Exception e) {
            responseMap.put("result", "failure");
            e.printStackTrace();
        }

        // creating lists of top genres for short, medium, and long terms
        for (int i = 0; i < 2; i++){
            newUser.setTopGenre(new ArrayList<>(), i);
            for (Artist artist : newUser.getTopArtists(i)) {
                newUser.getTopGenre(i).addAll(List.of(artist.getGenres()));
            }
        }

        Server.addUser(username, newUser);

        return adapter.toJson(responseMap);
    }
}