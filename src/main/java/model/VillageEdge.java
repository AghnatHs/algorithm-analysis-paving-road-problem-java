package model;

public class VillageEdge implements Comparable<VillageEdge> {
  public Character from;
  public Character to;
  public int distance;
  public int value;

  public VillageEdge(Character v1, Character v2, int weight, int value) {
    this.from = v1;
    this.to = v2;
    this.distance = weight;
    this.value = value;
  }

  public float getValuePerCost() {
    return (float) value / getPriceToPave();
  }

  public int getPriceToPave() {
    return (int) distance * 45; // RP_PER_METER constant
  }

  @Override
  public int compareTo(VillageEdge other) {
    return Integer.compare(this.distance, other.distance);
  }

  @Override
  public String toString() {
    return "Edge < " +
        "from = " + from +
        ", to = " + to +
        ", distance = " + distance +
        ", value = " + value +
        ", cost = " + this.getPriceToPave() +
        ", value/cost = " + this.getValuePerCost() +
        " >";

  }
}
