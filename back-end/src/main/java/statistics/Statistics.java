package statistics;

public class Statistics{
  private List<int> users;
  private List<float> overlaps;

  public List<int> getUsers(){
    return users;
  }
  public List<float> getOverlaps(){
    return overlaps;

  public float computeOverlap(List<String> user1, List<String> user2){
    float sum = 0;
    for (int i = 0; i < user1.size(); i++){
      if (user2.contains(user1.get(i))){
        sum++;
      }
    }
    return sum / user1.size();
  }

  public float computeWeightedMatch(List<float> overlaps, List<float> weights){
    float sum = 0;
    for (int i = 0; i < overlaps.size(); i++){
      sum += overlaps.get(i) * weights.get(i);
    }
    return sum;
  }

  public List<float> rankedList(List<String> user, List<List<String>> friends) {
    for (int i = 0; i < user.size(); i++) {
      overlaps.add(computeOverlap(user, friends.get(i)));
      users.add(i);
    }
    //note: add sorting functionality here
  }
}