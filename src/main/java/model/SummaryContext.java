package model;

public class SummaryContext {
  public int usedBudget;
  public float percentageUsed;
  public int valueGained;
  public int initialValue;
  public float percentageValueGained;

  public int costPerMeter = 45; // RP_PER_METER constant

  public SummaryContext(int usedBudget, float percentageUsed, int valueGained, int initialValue,
      float percentageValueGained) {
    this.usedBudget = usedBudget;
    this.percentageUsed = percentageUsed;
    this.valueGained = valueGained;
    this.initialValue = initialValue;
    this.percentageValueGained = percentageValueGained;
  }

  public String toString() {
    return String.format(
        "\nOptimization Summary:\n" +
            "  Budget Used: Rp %,d / Rp %,d (%.1f%%)\n" +
            "  Value Gained: %d / %d (%.1f%%)\n" +
            "  Cost per meter: Rp %,d",
        usedBudget,
        (int) (usedBudget / (percentageUsed / 100)),
        percentageUsed,
        valueGained,
        initialValue,
        percentageValueGained,
        costPerMeter);
  }
}
