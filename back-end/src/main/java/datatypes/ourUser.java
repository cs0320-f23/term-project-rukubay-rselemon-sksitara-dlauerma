package datatypes;
import java.util.List;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class ourUser {
  private final String username;
  private final String password;
  private List<Artist> topArtists;
  private List<Track> topSongs;
  public ourUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public List<Artist> getTopArtists() {
    return topArtists;
  }

  public void setTopArtists(List<Artist> artists) {
    topArtists = artists;
  }
  public List<Track> getTopTracks() {
    return topSongs;
  }

  public void setTopTracks(List<Track> topSongs) {
    topSongs = topSongs;
  }
}
