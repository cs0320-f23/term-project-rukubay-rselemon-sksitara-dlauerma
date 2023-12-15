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

  public List<Artist> getTopArtistsShort() {
    return topArtistsShort;
  }
  public void setTopArtistsShort(List<Artist> artists) {
    topArtistsShort = artists;
  }
  public List<Artist> getTopArtistsMed() {
    return topArtistsMed;
  }
  public void setTopArtistsMed(List<Artist> artists) {
    topArtistsMed = artists;
  }
  public List<Artist> getTopArtistsLong() {
    return topArtistsLong;
  }
  public void setTopArtistsLong(List<Artist> artists) {
    topArtistsLong = artists;
  }
  public List<Track> getTopTracksShort() {
    return topSongsShort;
  }
  public void setTopTracksShort(List<Track> songs) {
    topSongsShort = songs;
  }
  public List<Track> getTopTracksMed() {
    return topSongsMed;
  }
  public void setTopTracksMed(List<Track> songs) {
    topSongsMed = songs;
  }
  public List<Track> getTopTracksLong() {
    return topSongsLong;
  }
  public void setTopTracksLong(List<Track> songs) {
    topSongsLong = songs;
  }
  public HashMap<String, Integer> getTopGenreShort() {
    return topGenresShort;
  }
  public void setTopGenreShort(HashMap<String, Integer> genres) {
    topGenresShort = genres;
  }
  public HashMap<String, Integer> getTopGenreMed() {
    return topGenresMed;
  }
  public void setTopGenreMed(HashMap<String, Integer> genres) {
    topGenresMed = genres;
  }
  public HashMap<String, Integer> getTopGenreLong() {
    return topGenresLong;
  }
  public void setTopGenreLong(HashMap<String, Integer> genres) {
    topGenresLong = genres;
  }
}
