package filrouge.dbextractor;

import java.util.HashSet;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import filrouge.representations.RestrictedDomain;
import filrouge.representations.Rule;
import filrouge.representations.Variable;


public class AssociationRuleMiner {


    private Map<Set<Variable>, Integer> itemsetF;

    public AssociationRuleMiner(Map<Set<Variable>, Integer> itemsetF)
    {
        this.itemsetF = itemsetF;
    }

    /**
     * Gets itemsetF attribute.
     * @return  A Map of a Set of Variable and an Integer.
     */
    public Map<Set<Variable>, Integer> getItemsetF() {
        return itemsetF;
    }

    /**
     * Sets itemsetF attribute.
     * @param itemsetF A Map of a Set of Variable and an Integer.
     */
    public void setItemsetF(Map<Set<Variable>, Integer> itemsetF) {
        this.itemsetF = itemsetF;
    }

/**
 * Returns a set of a set of Variable from the itemsetF attribute that respects given parameters.
 * @param minFreq The minimum frequency requiered.
 * @param minConf The minimum trust requiered. A value between 0 and 1.
 * @return Returns a set of a set of Variable.
 */
    public Map<Rule, Float> association(int minFreq, float minConf){
        Map<Rule, Float> result = new HashMap<Rule, Float>();

        for(Set<Variable> set : this.itemsetF.keySet()){
            if(this.itemsetF.get(set) >= minFreq && set.size()>1){
                for(Variable variable: set){
                    Set<Variable> x = new HashSet<Variable>();
                    x.add(variable);
                    float confiance = this.itemsetF.get(set)/this.itemsetF.get(x);
                    if(confiance >= minConf){
                      Set<Variable> y = new HashSet<>(set);
                      y.remove(variable);
                      result.put(new Rule(RestrictedDomain.createSetRD(x),RestrictedDomain.createSetRD(y)), confiance);
                    }
                }
            }
        }
        return result;
    }
}