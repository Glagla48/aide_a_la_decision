package filrouge.planning;

import java.util.HashMap;
import java.util.Map.Entry;

import filrouge.representations.Variable;

/**
 * A state of the world.
 */
public class State {

    /**
     * A Map that represents the sate of the world.
     */
    private HashMap<Variable, String> state;

    /**
     * Constructs a State.
     * @param state Map of Variable and their values.
     */
    public State(HashMap<Variable, String> state)
    {
        this.state = state;
    }

    /**
     * Constructs a copy State.
     * @param s Constructs the State with the same attribute as s.
     */
    public State(State s)
    {
        this.state = s.getCopy();
    }

    public State()
    {
        this.state = new HashMap<Variable, String>();
    }

    /**
     * Set the State's attribute.
     * @param state The new attribute.
     */
    public void setState(HashMap<Variable, String> state){this.state = state;}

    /**
     * @return The current State of the world;
     */
    public HashMap<Variable, String> getState(){return this.state;}

    /**
     * Returns a value from the state attribut.
     * @param key The key Variable
     * @return Returns the string value associated with the key parameter.
     */
    public String getValue(Variable key){return this.state.get(key);}

    /**
     * Adds an element to the state of the world.
     * @param v The Variable to add to the current state.
     * @param s the value of the Variable.
     */
    public void addState(Variable v, String s){this.state.put(v, s);}

    /**
     * Adds multiple elements to the state of the world.
     * @param s State containing the multiple eleemnts to add.
     */
    public void addState(State s)
    {
        for(Entry<Variable, String> entry : s.getState().entrySet())
        {
            this.state.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Copies the State attribute
     * @return The copied attribute
     */
    public HashMap<Variable, String> getCopy()
    {
        HashMap<Variable, String> hm = new HashMap<>();

        for(Entry<Variable, String> entry : this.getState().entrySet())
        {
            hm.put(entry.getKey(), entry.getValue());
        }       
        return hm;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o)
    {
        if(o instanceof State)
        {
            State toCompare = (State) o;
            HashMap<Variable, String> map = toCompare.getState();
            for(Entry<Variable, String> entry : this.state.entrySet())
            {
                if(map.containsKey(entry.getKey()))
                {
                    if(!entry.getValue().equals(map.get(entry.getKey())))
                        return false;
                }
                else
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        String res = "";
        for(Variable v : this.getState().keySet())
        {
            res += v.getName() + "=" + this.getValue(v)+" ";
        }

        return res;
    }
}