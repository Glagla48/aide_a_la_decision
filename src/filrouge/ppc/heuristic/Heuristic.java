package filrouge.ppc.heuristic;

import java.util.HashMap;
import java.util.Set;

import filrouge.representations.Variable;

/**
 * Chooses a Variable to use in Backtracking
 */
public interface Heuristic {
  /**
   * Main function of Heuristic.
   * @param map Map containning a Varibale to choose.
   * @return Returns A Variable.
   */
  public Variable work(HashMap<Variable, Set<String>> map);

  /**
   * Set the Mode of the Heuristic. The Variable returned by work function depends on the mode.
   * @param i The mode. Positive or negative int.
   */
  public void setMode(int i);
}
