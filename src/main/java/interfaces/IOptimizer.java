package interfaces;

import java.util.List;

import model.SummaryContext;
import model.VillageEdge;

/**
 * Interface for optimizing the selection of roads to pave within a given budget
 */
public interface IOptimizer {
  /**
   * Executes the optimization algorithm to select roads to pave
   */
  void optimize();

  /**
   * Returns the list of roads selected for paving
   * 
   * @return List of VillageEdge representing paved roads
   */
  List<VillageEdge> getPavedRoads();

  /**
   * Returns the remaining budget after optimization
   * 
   * @return Remaining budget amount
   */
  int getRemainingBudget();

  /**
   * Returns the initial budget before optimization
   * 
   * @return Initial budget amount
   */
  int getInitialBudget();

  /**
   * Returns the summary context with optimization statistics
   * 
   * @return SummaryContext object containing budget and value metrics
   */
  SummaryContext getSummaryContext();
}
