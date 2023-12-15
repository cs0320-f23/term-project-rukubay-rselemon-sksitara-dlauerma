<<<<<<< Updated upstream
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
=======
//package statistics;
//import java.util.List;
//import java.util.Map;
//
//public class Statistics{
//  private List<String> users;
//  private List<Float> overlaps;
//  private List<List<Float>> featureWiseOverlaps;
//  public List<String> getUsers(){return users;}
//  public List<Float> getOverlaps() {return overlaps;}
//
//  public Statistics(List<String> u){
//    users = u;
//  }
//
//  public float computeOverlap(List<String> user1, List<String> user2) {
//    float sum = 0;
//    for (String s : user1) {
//      if (user2.contains(s)){
//        sum++;
//        user2.remove(s);
//      }
//    }
//    return (!user1.isEmpty() && !user2.isEmpty()) ? sum / user1.size() : 0;
//  }
//
//  public void computeWeightedMatches(List<Float> weights){
//    for (List<Float> o : featureWiseOverlaps) {
//      float sum = 0;
//      for (int i = 0; i < weights.size(); i++) {
//        sum += weights.get(i) * o.get(i);
//      }
//      overlaps.add(sum);
//    }
//  }
//
//  public void rankedList(List<String> user, List<List<String>> friends) {
//    for (List<Float> o : featureWiseOverlaps) {
//      float sum = 0;
//      for (int i = 0; i < user.size(); i++) {
//        overlaps.add(computeOverlap(user, friends.get(i)));
//        users.add(i);
//      }
//    }
//    //note: add sorting functionality here
//  }
//}
>>>>>>> Stashed changes
