package statistics;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import statistics.Statistics;
import static org.junit.jupiter.api.Assertions.*;

public class TestStatistics{
  @Test
  public void testOverlapBasicCase() {
    Statistics stats = new Statistics();
    String[] a = {"Ex-Factor", "Heart to Heart", "Lush Life", "Invitation"};
    List<String> user1 = Arrays.asList(a);
    String[] b = {"Still Beating", "Ex-Factor", "To Zion", "Raid", "Invitation"};
    List<String> user2 = Arrays.asList(b);
    assertEquals(2 / 4.5, stats.computeOverlap(user1, user2));
  }
  
  @Test
  public void testOverlapEmpty() {
    Statistics stats = new Statistics();
    String[] a = {"Ex-Factor", "Heart to Heart", "Lush Life", "Invitation"};
    List<String> user1 = Arrays.asList(a);
    String[] b = {};
    List<String> user2 = Arrays.asList(b);
    String[] c = {};
    List<String> user3 = Arrays.asList(b);
    assertEquals(0, stats.computeOverlap(user1, user2));
    assertEquals(0, stats.computeOverlap(user2, user1));
    assertEquals(0, stats.computeOverlap(user2, user3));
  }
}