package statistics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Statistics<T> {
  public float computeOverlap(List<T> user1, List<T> u2) {
    List<T> user2 = new ArrayList<>(u2);
    float sum = 0;
    for (T a : user1) {
      if (user2.contains(a)) {
        sum++;
        user2.remove(a);
      }
    }
    // Jaccard similarity, A int B / A U B
    return (!user1.isEmpty() && !user2.isEmpty()) ? sum / (user1.size() + user2.size()) : 0;
  }
}