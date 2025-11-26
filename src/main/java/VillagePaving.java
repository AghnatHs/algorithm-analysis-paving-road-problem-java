import interfaces.IOptimizer;
import model.Graph;
import model.VillageEdge;

public class VillagePaving {
  public static final int RP_PER_METER = 45;

  public static void main(String[] args) {
    int V = 18;
    int E = 23;

    int budget = 1_500;
    Graph area = new Graph(V, E);

    area.villages[0] = new VillageEdge('P', 'A', 5, 80);
    area.villages[1] = new VillageEdge('P', 'B', 22, 65);
    area.villages[2] = new VillageEdge('P', 'C', 13, 60);
    area.villages[3] = new VillageEdge('P', 'D', 4, 55);
    area.villages[4] = new VillageEdge('P', 'E', 15, 57);
    area.villages[5] = new VillageEdge('P', 'I', 16, 75);
    area.villages[6] = new VillageEdge('P', 'J', 1, 10);
    area.villages[7] = new VillageEdge('P', 'H', 4, 100);
    area.villages[8] = new VillageEdge('I', 'H', 20, 100);
    area.villages[9] = new VillageEdge('J', 'I', 30, 70);
    area.villages[10] = new VillageEdge('J', 'H', 19, 95);
    area.villages[11] = new VillageEdge('J', 'U', 5, 71);
    area.villages[12] = new VillageEdge('H', 'K', 3, 100);
    area.villages[13] = new VillageEdge('H', 'U', 20, 89);
    area.villages[14] = new VillageEdge('E', 'K', 16, 60);
    area.villages[15] = new VillageEdge('E', 'F', 21, 60);
    area.villages[16] = new VillageEdge('D', 'F', 7, 65);
    area.villages[17] = new VillageEdge('F', 'G', 8, 42);
    area.villages[18] = new VillageEdge('U', 'L', 10, 56);
    area.villages[19] = new VillageEdge('U', 'M', 17, 90);
    area.villages[20] = new VillageEdge('M', 'N', 13, 37);
    area.villages[21] = new VillageEdge('M', 'O', 11, 24);
    area.villages[22] = new VillageEdge('M', 'Q', 10, 43);

    IOptimizer optimizer = new GreedyByRatioValueOptimizer(area, budget);

    // Run optimization
    optimizer.optimize();

    // Show visualization with GraphStream
    System.out.println("\n=== Opening Graph Visualization ===");
    GraphStreamVisualizer.showSolutionComparison(area, optimizer.getPavedRoads(), optimizer.getSummaryContext(),
        optimizer.getInitialBudget(), optimizer.getRemainingBudget());
  }
}