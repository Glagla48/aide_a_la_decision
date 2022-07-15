package filrouge.ppc.heuristic;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import filrouge.representations.Variable;

/**
 * {@inheritDoc}
 */
public class HeuristicRandom implements Heuristic {

	private Random rand = new Random();

	/**
 * {@inheritDoc}
 */
	public void setMode(int mode){};

	/**
 * {@inheritDoc}
 */
	@Override
	public Variable work(HashMap<Variable, Set<String>> map) {
		int random = rand.nextInt(map.size());
		Variable res = null;
		for(Variable v : map.keySet()) {
			if(random == 0) {
				res = v;
			}
			random--;
		}
		return res;
	}
}
