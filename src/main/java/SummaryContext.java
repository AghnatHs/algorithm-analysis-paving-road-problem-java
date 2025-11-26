
class SummaryContext {
  int usedBudget;
  float percentageUsed;
  int valueGained;
  int initialValue;
  float percentageValueGained;

  int costPerMeter = VillagePaving.RP_PER_METER;

  public SummaryContext(int usedBudget, float percentageUsed, int valueGained, int initialValue,
      float percentageValueGained) {
    this.usedBudget = usedBudget;
    this.percentageUsed = percentageUsed;
    this.valueGained = valueGained;
    this.initialValue = initialValue;
    this.percentageValueGained = percentageValueGained;
  }
}