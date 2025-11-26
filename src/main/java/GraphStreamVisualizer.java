import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

class GraphStreamVisualizer {
  private static final String STYLESHEET = "node {" +
      "  size: 30px;" +
      "  fill-color: #4A90E2;" +
      "  text-size: 20px;" +
      "  text-style: bold;" +
      "  text-color: white;" +
      "  text-alignment: center;" +
      "  stroke-mode: plain;" +
      "  stroke-color: #2C3E50;" +
      "  stroke-width: 2px;" +
      "}" +
      "node.paved {" +
      "  fill-color: #2ECC71;" +
      "  size: 35px;" +
      "}" +
      "edge {" +
      "  fill-color: #95A5A6;" +
      "  size: 2px;" +
      "  text-size: 14px;" +
      "  text-background-mode: rounded-box;" +
      "  text-background-color: #ECF0F1;" +
      "  text-color: #2C3E50;" +
      "  text-padding: 5px;" +
      "}" +
      "edge.paved {" +
      "  fill-color: #27AE60;" +
      "  size: 4px;" +
      "  text-background-color: #D5F4E6;" +
      "}" +
      "edge.notpaved {" +
      "  fill-color: #E74C3C;" +
      "  size: 2px;" +
      "}";

  public static void showInitialGraph(Graph graph) {
    System.setProperty("org.graphstream.ui", "swing");

    org.graphstream.graph.Graph gsGraph = new SingleGraph("Village Roads - Initial Graph");
    gsGraph.setAttribute("ui.stylesheet", STYLESHEET);
    gsGraph.setAttribute("ui.quality");
    gsGraph.setAttribute("ui.antialias");

    // Add all edges from the graph
    for (VillageEdge edge : graph.villages) {
      // Add nodes if they don't exist
      if (gsGraph.getNode(edge.from.toString()) == null) {
        Node node = gsGraph.addNode(edge.from.toString());
        node.setAttribute("ui.label", edge.from.toString());
      }
      if (gsGraph.getNode(edge.to.toString()) == null) {
        Node node = gsGraph.addNode(edge.to.toString());
        node.setAttribute("ui.label", edge.to.toString());
      }

      // Add edge
      String edgeId = edge.from.toString() + "-" + edge.to.toString();
      Edge e = gsGraph.addEdge(edgeId, edge.from.toString(), edge.to.toString(), false);
      String label = String.format("d:%d v:%d", edge.distance, edge.value);
      e.setAttribute("ui.label", label);
    }

    // Display the graph
    Viewer viewer = gsGraph.display();
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
  }

  public static void showSolutionComparison(Graph graph, List<VillageEdge> pavedRoads, SummaryContext summary,
      int initialBudget, int remainingBudget) {
    System.setProperty("org.graphstream.ui", "swing");

    // Create a frame to hold both graphs side by side
    JFrame frame = new JFrame("Village Paving Solution");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new GridLayout(1, 2, 10, 0));

    // Set to maximum screen size
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize(screenSize.width, screenSize.height);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    // Create initial graph
    org.graphstream.graph.Graph initialGraph = new SingleGraph("Initial Graph");
    initialGraph.setAttribute("ui.stylesheet", STYLESHEET);
    initialGraph.setAttribute("ui.quality");
    initialGraph.setAttribute("ui.antialias");

    // Use deterministic positioning based on character code
    for (VillageEdge edge : graph.villages) {
      if (initialGraph.getNode(edge.from.toString()) == null) {
        Node node = initialGraph.addNode(edge.from.toString());
        node.setAttribute("ui.label", edge.from.toString());
        // Set initial position based on character for consistency
        int seed = edge.from.charValue();
        node.setAttribute("xyz", seed * 0.1, seed * 0.15, 0);
      }
      if (initialGraph.getNode(edge.to.toString()) == null) {
        Node node = initialGraph.addNode(edge.to.toString());
        node.setAttribute("ui.label", edge.to.toString());
        // Set initial position based on character for consistency
        int seed = edge.to.charValue();
        node.setAttribute("xyz", seed * 0.1, seed * 0.15, 0);
      }

      String edgeId = edge.from.toString() + "-" + edge.to.toString();
      Edge e = initialGraph.addEdge(edgeId, edge.from.toString(), edge.to.toString(), false);
      String label = String.format("d:%d v:%d", edge.distance, edge.value);
      e.setAttribute("ui.label", label);

      // Mark if paved or not
      boolean isPaved = pavedRoads.stream()
          .anyMatch(pe -> (pe.from.equals(edge.from) && pe.to.equals(edge.to)) ||
              (pe.from.equals(edge.to) && pe.to.equals(edge.from)));
      if (isPaved) {
        e.setAttribute("ui.class", "paved");
      } else {
        e.setAttribute("ui.class", "notpaved");
      }
    }

    // Mark paved nodes
    for (VillageEdge edge : pavedRoads) {
      Node n1 = initialGraph.getNode(edge.from.toString());
      Node n2 = initialGraph.getNode(edge.to.toString());
      if (n1 != null)
        n1.setAttribute("ui.class", "paved");
      if (n2 != null)
        n2.setAttribute("ui.class", "paved");
    }

    // Create solution graph (only paved roads)
    org.graphstream.graph.Graph solutionGraph = new SingleGraph("Solution - Paved Roads");
    solutionGraph.setAttribute("ui.stylesheet", STYLESHEET);
    solutionGraph.setAttribute("ui.quality");
    solutionGraph.setAttribute("ui.antialias");

    for (VillageEdge edge : pavedRoads) {
      if (solutionGraph.getNode(edge.from.toString()) == null) {
        Node node = solutionGraph.addNode(edge.from.toString());
        node.setAttribute("ui.label", edge.from.toString());
        node.setAttribute("ui.class", "paved");
        // Set initial position based on character for consistency
        int seed = edge.from.charValue();
        node.setAttribute("xyz", seed * 0.1, seed * 0.15, 0);
      }
      if (solutionGraph.getNode(edge.to.toString()) == null) {
        Node node = solutionGraph.addNode(edge.to.toString());
        node.setAttribute("ui.label", edge.to.toString());
        node.setAttribute("ui.class", "paved");
        // Set initial position based on character for consistency
        int seed = edge.to.charValue();
        node.setAttribute("xyz", seed * 0.1, seed * 0.15, 0);
      }

      String edgeId = edge.from.toString() + "-" + edge.to.toString();
      Edge e = solutionGraph.addEdge(edgeId, edge.from.toString(), edge.to.toString(), false);
      String label = String.format("d:%d v:%d", edge.distance, edge.value);
      e.setAttribute("ui.label", label);
      e.setAttribute("ui.class", "paved");
    }

    // Create viewers with fixed seed for consistent layout
    Viewer viewer1 = initialGraph.display(false);
    org.graphstream.ui.layout.springbox.implementations.SpringBox layout1 = new org.graphstream.ui.layout.springbox.implementations.SpringBox(
        false);
    layout1.setStabilizationLimit(0.9);
    layout1.setForce(2.0);
    layout1.setQuality(0.5);
    viewer1.enableAutoLayout(layout1);
    ViewPanel view1 = (ViewPanel) viewer1.getDefaultView();

    Viewer viewer2 = solutionGraph.display(false);
    org.graphstream.ui.layout.springbox.implementations.SpringBox layout2 = new org.graphstream.ui.layout.springbox.implementations.SpringBox(
        false);
    layout2.setStabilizationLimit(0.9);
    layout2.setForce(2.0);
    layout2.setQuality(0.5);
    viewer2.enableAutoLayout(layout2);
    ViewPanel view2 = (ViewPanel) viewer2.getDefaultView();

    // Create panels with titles
    JPanel panel1 = new JPanel(new BorderLayout());

    // Title for initial graph
    JLabel label1 = new JLabel("Initial Graph - All Roads", SwingConstants.CENTER);
    label1.setFont(new Font("Arial", Font.BOLD, 16));
    label1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Budget summary panel
    JPanel budgetSummary = new JPanel();
    budgetSummary.setLayout(new GridLayout(3, 1, 5, 2));
    budgetSummary.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

    JLabel budgetLabel = new JLabel(
        String.format("Cost/m: Rp %,d | Initial Budget: Rp %,d | Used: Rp %,d (%.1f%%) | Remaining: Rp %,d",
            summary.costPerMeter, initialBudget, summary.usedBudget, summary.percentageUsed, remainingBudget),
        SwingConstants.CENTER);
    budgetLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    budgetSummary.add(budgetLabel);

    JPanel topPanel1 = new JPanel(new BorderLayout());
    topPanel1.add(label1, BorderLayout.NORTH);
    topPanel1.add(budgetSummary, BorderLayout.CENTER);

    panel1.add(topPanel1, BorderLayout.NORTH);
    panel1.add(view1, BorderLayout.CENTER);

    JPanel panel2 = new JPanel(new BorderLayout());

    // Title for solution graph
    JLabel label2 = new JLabel("Solution - Paved Roads Only", SwingConstants.CENTER);
    label2.setFont(new Font("Arial", Font.BOLD, 16));
    label2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Value summary panel
    JPanel valueSummary = new JPanel();
    valueSummary.setLayout(new GridLayout(3, 1, 5, 2));
    valueSummary.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

    JLabel valueLabel = new JLabel(
        String.format("Maximum Value: %d | Value Gained: %d (%.1f%%) | Roads Paved: %d of %d",
            summary.initialValue, summary.valueGained, summary.percentageValueGained, pavedRoads.size(),
            graph.roadsCount),
        SwingConstants.CENTER);
    valueLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    valueSummary.add(valueLabel);

    JPanel topPanel2 = new JPanel(new BorderLayout());
    topPanel2.add(label2, BorderLayout.NORTH);
    topPanel2.add(valueSummary, BorderLayout.CENTER);

    panel2.add(topPanel2, BorderLayout.NORTH);
    panel2.add(view2, BorderLayout.CENTER);

    frame.add(panel1);
    frame.add(panel2);
    frame.setVisible(true);

    // Disable auto layour after 3 seconds
    new Thread(() -> {
      try {
        Thread.sleep(1000);
        viewer1.disableAutoLayout();
        viewer2.disableAutoLayout();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }
}