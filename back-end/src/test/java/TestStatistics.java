import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import statistics.Statistics;
import static org.junit.jupiter.api.Assertions.*;

public class TestStatistics{
  @Test
  public void testOverlapBasicCase() {
    Statistics<String> stats = new Statistics<>();
    String[] a = {"Ex-Factor", "Heart to Heart", "Lush Life", "Invitation"};
    List<String> user1 = Arrays.asList(a);
    String[] b = {"Still Beating", "Ex-Factor", "To Zion", "Raid", "Invitation"};
    List<String> user2 = Arrays.asList(b);
    assertEquals(2.f / 7.f, stats.computeOverlap(user1, user2));
  }

  @Test
  public void testOverlapEmptyCases() {
    //one user's list is empty
    Statistics<String> stats = new Statistics<>();
    String[] a = {"Ex-Factor", "Heart to Heart", "Lush Life", "Invitation"};
    List<String> user1 = Arrays.asList(a);
    String[] b = {};
    List<String> user2 = Arrays.asList(b);
    assertEquals(0, stats.computeOverlap(user1, user2));
    assertEquals(0, stats.computeOverlap(user2, user1));


    //both user lists are empty
    String[] c = {};
    List<String> user3 = Arrays.asList(c);
    assertEquals(0, stats.computeOverlap(user2, user3));
  }

  @Test
  public void testOverlapRepeats(){
    //Note: we've designed our overlapping algorithm to more heavily weight repeats that appear in both users lists
    Statistics<String> stats = new Statistics<>();
    String[] a = {"Rap", "Jazz", "Soul", "Classical", "Soul", "Classical", "Classical", "Rap"};
    List<String> user1 = Arrays.asList(a);
    String[] b = {"Rap", "Jazz", "Soul", "Soul", "Classical", "Rap", "Jazz"};
    List<String> user2 = Arrays.asList(b);
    assertEquals(2.f / 3.f, stats.computeOverlap(user1, user2));
  }

  @Test
  public void testSorting(){
    Statistics stats = new Statistics<>();
    Map<String, Float> overlaps = new HashMap<>();
    overlaps.put("Rahel", 0.5f);
    overlaps.put("Ruth", 0.9f);
    overlaps.put("David", 0.8f);
    overlaps.put("Tim", 0.4f);
    List<Map.Entry<String, Float>> sortedOverlaps = stats.rankedList(overlaps);
    assertEquals("Ruth", sortedOverlaps.get(0).getKey());
    assertEquals("David", sortedOverlaps.get(1).getKey());
    assertEquals("Rahel", sortedOverlaps.get(2).getKey());
    assertEquals("Tim", sortedOverlaps.get(3).getKey());
  }
}