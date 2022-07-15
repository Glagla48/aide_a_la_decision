package filrouge.representations;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.ArrayList;

public class RestrictedDomain {
	private Variable variable;
	private Set<String> subDomain;

	/**
	 * Creates a RestrictedDomain.
	 * @param variable The Variable wich the RestrictedDomain inherits.
	 * @param subDomain The subDomain of the RestrictedDomain. 
	 */
	public RestrictedDomain(Variable variable, Set<String> subDomain) {
		this.variable = variable;
		this.subDomain = subDomain;
	}

	public RestrictedDomain(Variable variable, String domain) {
		this.variable = variable;
		this.subDomain = new HashSet<>(Arrays.asList(domain));
	}
        
    public RestrictedDomain(Variable variable) {
		this.variable = variable;
		this.subDomain = variable.getDomaine();
	}
        
        public static Set<RestrictedDomain> createSetRD(Set<Variable> set)
        {
            Set<RestrictedDomain> res = new HashSet<>();
       
            for(Variable var : set)
            {
                RestrictedDomain rd = new RestrictedDomain(var);
                res.add(rd);
            }
            
            return res;
        }
        

	/**
	 * 
	 * @return Returns the Variable attribute of RestrictedDomain.
	 */
	public Variable getVariable() {return this.variable;}

	/**
	 * Sets Variable attribute.
	 * @param variable The new Variable attribut.
	 */
	public void setVariable(Variable variable) {this.variable = variable;}

	/**
	 * 
	 * @return Returns the subDomain attribute.
	 */
	public Set<String> getDomain() {return this.subDomain;}

	/**
	 * Sets the subDomain attribute.
	 * @param subDomain The new subDomain.
	 */
	public void setDomain(Set<String> subDomain) {this.subDomain = subDomain;}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getVariable().getName()).append(" => ").append(this.getDomain());
		return builder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override 
	public boolean equals(Object o)
	{
		if(o instanceof RestrictedDomain)
		{
			RestrictedDomain rd = (RestrictedDomain) o;
			return rd.getVariable() == this.getVariable() && this.getDomain() == rd.getDomain();
		}
		return false;
	}
}