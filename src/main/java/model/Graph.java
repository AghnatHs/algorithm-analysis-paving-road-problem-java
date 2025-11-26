package model;

import java.util.Arrays;

public class Graph {
  public int villagesCount;
  public int roadsCount;
  public VillageEdge[] villages;

  public Graph(int v, int e) {
    this.villagesCount = v;
    this.roadsCount = e;
    this.villages = new VillageEdge[e];
  }

  public void sortEdgesByValueCost() {
    Arrays.sort(this.villages, (a, b) -> {
      float r1 = a.getValuePerCost();
      float r2 = b.getValuePerCost();
      if (r1 < r2)
        return 1;
      else if (r1 > r2)
        return -1;
      else
        return 0;
    });
  }
}
