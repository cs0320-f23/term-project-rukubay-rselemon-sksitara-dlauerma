package datatypes;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class ourUser {
  private final String username;
  private final String password;
  private List<Artist> topArtistsShort;
  private List<Artist> topArtistsMed;
  private List<Artist> topArtistsLong;
  private List<Track> topSongsShort;
  private List<Track> topSongsMed;
  private List<Track> topSongsLong;
  private HashMap<String, Integer> topGenresShort;
  private HashMap<String, Integer> topGenresMed;
  private HashMap<String, Integer> topGenresLong;
  public ourUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public boolean checkPassword(String attempt) {
    return Objects.equals(attempt, password);
  }
  public String getUsername() {
    return username;
  }

  public List<Artist> getTopArtists(int timeRange) {
    if (timeRange == 0) return topArtistsShort;
    else if (timeRange == 1) return topArtistsMed;
    else return topArtistsLong;
  }
  public void setTopArtists(List<Artist> artists, int timeRange) {
    if (timeRange == 0) topArtistsShort = artists;
    else if (timeRange == 1) topArtistsMed = artists;
    else topArtistsLong = artists;
  }
  public List<Track> getTopTracks(int timeRange) {
    if (timeRange == 0) return topSongsShort;
    else if (timeRange == 1) return topSongsMed;
    else return topSongsLong;
  }
  public void setTopTracks(List<Track> songs, int timeRange) {
    if (timeRange == 0) topSongsShort = songs;
    else if (timeRange == 1) topSongsMed = songs;
    else topSongsLong = songs;
  }
  public HashMap<String, Integer> getTopGenre(int timeRange) {
    if (timeRange == 0) return topGenresShort;
    else if (timeRange == 1) return topGenresMed;
    else return topGenresLong;
  }
  public void setTopGenre(HashMap<String, Integer> genres, int timeRange) {
    if (timeRange == 0) topGenresShort = genres;
    else if (timeRange == 1) topGenresShort = genres;
    else topGenresShort = genres;
  }
}