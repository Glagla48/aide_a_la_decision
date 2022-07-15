package filrouge.ppc.heuristic;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import filrouge.factories.*;
import filrouge.representations.Variable;

/**
 * {@inheritDoc}
 */
public class HeuristicGrandPetit implements Heuristic{

	private int mode;

	/**
 * {@inheritDoc}
 */
	public HeuristicGrandPetit() {
		this.mode = 1;
	}

	/**
 * {@inheritDoc}
 */
	@Override
	public void setMode(int mode) { this.mode = mode; }

	/**
 * {@inheritDoc}
 */
	@Override
	public Variable work(HashMap<Variable, Set<String>> map) {
		Variable res = null;
		String currentValue = "";

		if(this.mode < 0) {
			for(Entry<Variable, Set<String>> entry : map.entrySet()) {
				if(entry.getValue().contains(Utilities.NON))
					return entry.getKey();
				if(entry.getValue().contains(Utilities.AUCUN))
					return entry.getKey();
				if(entry.getValue().contains(Utilities.FAIBLE)) {
					currentValue = Utilities.FAIBLE;
					res = entry.getKey();
				}
				else if(entry.getValue().contains(Utilities.MOYEN) && !currentValue.equals(Utilities.FAIBLE)) {
					currentValue = "MOYEN";
					res = entry.getKey();
				}
				else if(entry.getValue().contains(Utilities.ELEVE) && !currentValue.equals(Utilities.FAIBLE) && !currentValue.equals(Utilities.MOYEN)) {
					currentValue = "ELEVE";
					res = entry.getKey();
				}

			}
		}
		else {
			for(Entry<Variable, Set<String>> entry : map.entrySet()) {
				if(entry.getValue().contains(Utilities.OUI))
					return entry.getKey();
				if(entry.getValue().contains(Utilities.ELEVE))
					return entry.getKey();
				else if(entry.getValue().contains(Utilities.MOYEN) && !currentValue.equals(Utilities.ELEVE)) {
					currentValue = Utilities.MOYEN;
					res = entry.getKey();
				}
				else if(entry.getValue().contains(Utilities.FAIBLE) && !currentValue.equals(Utilities.ELEVE) && !currentValue.equals(Utilities.MOYEN)) {
					currentValue = Utilities.FAIBLE;
					res = entry.getKey();
				}
			}
		}
		return res;
	}
}
