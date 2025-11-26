import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class VillagePavingOptimizer {
  private int budget;
  private int initialBudget;
  private Graph graph;
  private List<VillageEdge> pavedRoads;
  private SummaryContext summaryContext;

  public VillagePavingOptimizer(Graph graph, int budget) {
    this.graph = graph;
    this.budget = budget;
    this.initialBudget = budget;
    this.pavedRoads = new ArrayList<>();
  }

  public void optimize() {
    System.out.println("----- Optimization Result -----");
    System.out.println("Initial Budget: Rp " + String.format("%,d", this.budget));

    pavedRoads.clear();
    graph.sortEdgesByValueCost();

    for (VillageEdge edge : graph.villages) {
      int cost = edge.getPriceToPave();
      if (cost <= budget) {
        budget -= cost;
        pavedRoads.add(edge);
      }
    }

    System.out.println("Paved Roads:");
    for (VillageEdge paved : pavedRoads) {
      System.out.println(paved);
    }

    System.out.println("-----------------------");
    int usedBudget = initialBudget - budget;
    float percentageUsed = (float) (usedBudget * 100) / initialBudget;

    int valueGained = pavedRoads.stream().mapToInt(e -> e.value).sum();
    int initialValue = Arrays.stream(graph.villages).mapToInt(e -> e.value).sum();
    float percentageValueGained = (float) (valueGained * 100) / initialValue;

    summaryContext = new SummaryContext(usedBudget, percentageUsed, valueGained, initialValue, percentageValueGained);

    System.out.println("----- Budget Summary -----");
    System.out.println("Initial Budget: Rp " + String.format("%,d", initialBudget));
    System.out.println("Budget Used: Rp " + String.format("%,d", usedBudget) +
        " (" + percentageUsed + "%)");
    System.out.println("Remaining Budget: Rp " + String.format("%,d", budget));
    System.out.println();
    System.out.println("----- Value Summary -----");
    System.out.println("Maximum Value: " +
        initialValue);
    System.out.println("Value Gained: " +
        valueGained);
    System.out.println("Percentage Value Gained: " +
        percentageValueGained + "%");
    System.out.println("-----------------------");
  }

  public List<VillageEdge> getPavedRoads() {
    return pavedRoads;
  }

  public int getRemainingBudget() {
    return budget;
  }

  public int getInitialBudget() {
    return initialBudget;
  }

  public SummaryContext getSummaryContext() {
    return summaryContext;
  }
}