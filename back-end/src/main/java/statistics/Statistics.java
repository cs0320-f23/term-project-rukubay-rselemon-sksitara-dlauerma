package statistics;
import java.util.List;
public class Statistics<T> {
  public float computeOverlap(List<T> user1, List<T> user2) {
    float sum = 0;
    for (T a : user1) {
      if (user2.contains(a)){
        sum++;
        user2.remove(a);
      }
    }
    return (!user1.isEmpty() && !user2.isEmpty()) ? sum / user1.size() : 0;
  }
}