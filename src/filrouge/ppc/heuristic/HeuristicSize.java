package filrouge.ppc.heuristic;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import filrouge.representations.Variable;

/**
 * {@inheritDoc}
 */
public class HeuristicSize implements Heuristic{

	private int mode;

	public HeuristicSize() {
		this.mode = 1;
	}

	/**
 * {@inheritDoc}
 */
	@Override
	public void setMode(int mode) {this.mode = mode;}

	/**
 * {@inheritDoc}
 */
	@Override
	public Variable work(HashMap<Variable, Set<String>> map) {
		HashMap<Variable, Integer> v_size = new HashMap<>();

		for(Variable v : map.keySet())
		v_size.put(v, map.get(v).size());

		Variable res = null;
		Integer comp;
		if(mode >= 0)
		comp = Integer.MIN_VALUE;
		else
		comp = Integer.MAX_VALUE;

		for(Entry<Variable, Integer> entry : v_size.entrySet()) {
			if(mode >= 0)
			{
				if(entry.getValue() >= comp)
				{
					res = entry.getKey();
					comp = entry.getValue();
				}
			}
			else
			{
				if(entry.getValue() <= comp)
				{
					res = entry.getKey();
					comp = entry.getValue();
				}
			}

		}

		return res;
	}
}
