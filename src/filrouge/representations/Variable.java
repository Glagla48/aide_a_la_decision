package filrouge.representations;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Variable {

	private String name;
	private Set<String> domain;

	/**
	 * Creates a Variable.
	 * @param nom The name of the Variable
	 * @param domain The multiple values the Variable can take.
	 */
	public Variable(String nom, Set<String> domain) {
		this.name = nom;
		this.domain = domain;
	}

	/**
	 * 
	 * @return Returns the name of the Variable.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Changes the name of Variable
	 * @param name The new name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the Variable's domain attribute.
	 */
	public Set<String> getDomaine() {
		return this.domain;
	}

	/**
	 * Set the doamin attribute.
	 * @param domain The new domain.
	 */
	public void setDomaine(Set<String> domain) {
		this.domain = domain;
	}

	/**
	 * 
	 * @return Returns one of Variable's random value.
	 */
	public String getRandom() {
		int target = new Random().nextInt(this.domain.size());
		int i = 0;
		for(String obj : this.domain)
		{
			if (i == target)
				return obj;
			i++;
		}
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return this.name;
	}


	/**
	 * 
	 * @return Returns an instance of RestrictedDomain with the same subDomain as the Variable.
	 */
	public RestrictedDomain getRestrictedDomain() {
		return new RestrictedDomain(this, this.getDomaine());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Variable)) return false;
		return this.getName() == ((Variable)obj).getName();
	}

	/**
	 * Copies a Variable
	 * @return Return an new insance of Variable with the same attributes.
	 */
	public Variable getCopy()
	{
		HashSet<String> newSet = new HashSet<>();

		for(String s: this.getDomaine())
		{
			newSet.add(new String(s));
		}

		return new Variable(new String(this.name), newSet);
	}
}