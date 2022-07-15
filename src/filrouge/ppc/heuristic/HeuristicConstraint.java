package filrouge.ppc.heuristic;

import java.util.HashMap;
import java.util.Set;

import filrouge.representations.Constraint;
import filrouge.representations.RestrictedDomain;
import filrouge.representations.Variable;

/**
 * {@inheritDoc}
 */
public class HeuristicConstraint implements Heuristic {

    private Constraint constraint;
    private int mode = 1;

    public HeuristicConstraint() {}

    public void setConstraint(Constraint constraint) { this.constraint = constraint; }

	/**
 * {@inheritDoc}
 */
    @Override
    public void setMode(int mode){ this.mode = mode; }

	/**
 * {@inheritDoc}
 */
	  @Override
    public Variable work(HashMap<Variable, Set<String>> map) {
 		if(this.constraint == null)
		{
    		System.out.println("Pas de contrainte mise dans l'heuristic!");
        	return null;
      	}
		int count = 0;
		int comp = 0;
		Variable res = null;

		for(Variable v : map.keySet()) {
			count =0;
			for(RestrictedDomain rd : this.constraint.getPremise()) {
				if(rd.getVariable().equals(v))
				count++;
			}
			for(RestrictedDomain rd : this.constraint.getConclusion()){
				if(rd.getVariable().equals(v))
				count++;
			}
			if(mode>1) {
				if(count >= comp) {
					res = v;
					comp = count;
				}
			}
			else
			{
				if(count <= comp) {
					res = v;
					comp = count;
				}
			}
		}
		return res;
    }
}
