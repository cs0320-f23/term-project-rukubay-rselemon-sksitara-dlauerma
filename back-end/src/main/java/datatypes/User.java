package datatypes;
import java.util.List;

public class User {
  private final String displayName;
  private List<String> topArtists;
  private List<String> topSongs;
  public User(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
  public List<String> getTopArtists() {
    return topArtists;
  }
  public List<String> getTopSongs() {
    return topSongs;
  }
}
