package statistics;

import java.util.ArrayList;
import java.util.List;

public class Statistics{
  private List<Integer> users;
  private List<Float> overlaps;

  public List<Integer> getUsers(){
    return users;
  }
  public List<Float> getOverlaps() {return overlaps;}

  public float computeOverlap(List<String> user1, List<String> user2) {
    float sum = 0;
    for (int i = 0; i < user1.size(); i++){
      if (user2.contains(user1.get(i))){
        sum++;
      }
    }
    return (!user1.isEmpty() && !user2.isEmpty()) ? sum / ((float)(user1.size() + user2.size()) / 2.f) : 0;
  }

  public float computeWeightedMatch(List<Float> overlaps, List<Float> weights){
    float sum = 0;
    for (int i = 0; i < overlaps.size(); i++){
      sum += overlaps.get(i) * weights.get(i);
    }
    return sum;
  }

  public void rankedList(List<String> user, List<List<String>> friends) {
    for (int i = 0; i < user.size(); i++) {
      overlaps.add(computeOverlap(user, friends.get(i)));
      users.add(i);
    }
    //note: add sorting functionality here
  }
}