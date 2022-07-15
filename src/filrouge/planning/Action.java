package filrouge.planning;

import java.util.HashMap;
import java.util.Set;


public class Action {

    /**
     * HashMap of base State and post State.
     */
    private HashMap<State, State> rules;

    /**
     * An Action.
     * @param precondition  An HashMap of State and State.
     */
    public Action(HashMap<State, State> precondition) {
        this.setPrecondition(precondition);
    }

    /**
     * Gets rules attribute.
     * @return An HashMap of State and State.
     */
    public HashMap<State, State> getRules() {return this.rules;}

    /**
     * Gets the precondition, the keys of the rules attribute.
     * @return A Set of State.
     */
    public Set<State> getPreconditions(){return this.rules.keySet();}


    /**
     * Gets a specific effet of the rules attribute.
     * @param precondition A key State from rules.
     * @return A State representing the effect of the precondition on the State.
     */
    public State getEffet(State precondition){ return this.rules.get(precondition);}
        
    /**
     * Sets rules attribute.
     * @param rules An HashMap ofState and State.
     */
    public void setPrecondition(HashMap<State, State> rules) {this.rules = rules;}

    /**
     * Adds a new precondition in rules.
     * @param state A State representing the initial state.
     * @param effect A State representing the effect.
     */
    public void addPrecondicion(State state, State effect) {this.rules.put(state, effect);}

    @Override
    public String toString()
    {
        String res = "";
        for(State key : this.getPreconditions())
        {
            res += "precondition: " + key + " effet: " + this.getEffet(key);
        }
        return res;
    }
}