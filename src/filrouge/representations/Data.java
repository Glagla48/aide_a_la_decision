package filrouge.representations;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

import filrouge.factories.FactoryDomain;
import filrouge.factories.FactoryVariable;
import filrouge.factories.Utilities;
import filrouge.representations.Constraint;
import filrouge.representations.IncompatibilityConstraint;
import filrouge.representations.RestrictedDomain;
import filrouge.representations.Rule;
import filrouge.representations.Variable;

public class Data {
	
	// Maladie
	public static final Variable angine = FactoryVariable.CreateBooleanVariable("Angine");
	public static final Variable grippe = FactoryVariable.CreateBooleanVariable("Grippe");
	public static final Variable allergieSucre = FactoryVariable.CreateLevelVariable("Allergie sucre");

	// Symptomes
	public static final Variable toux = FactoryVariable.CreateBooleanVariable("Toux");
	public static final Variable fatigue = FactoryVariable.CreateBooleanVariable("Fatigué(e)");
	public static final Variable hypothermie = FactoryVariable.CreateBooleanVariable("Hypothermie");
	public static final Variable fievre = FactoryVariable.CreateLevelVariable("Fièvre");
	public static final Variable boutons = FactoryVariable.CreateBooleanVariable("Boutons");
	public static final Variable oedeme = FactoryVariable.CreateBooleanVariable("œdème");

	// "Variables"
	public static final Variable virus = FactoryVariable.CreateBooleanVariable("Virus");
	public static final Variable vaccination = FactoryVariable.CreateBooleanVariable("Vacciné(e)");
	public static final Variable sirop = FactoryVariable.CreateBooleanVariable("Prise sirop");

	//#region Rules
	public static final Set<RestrictedDomain> premise = new HashSet<RestrictedDomain>()
	{{add(FactoryDomain.CreateRestrictedDomainOUI(angine));}};
	public static final Set<RestrictedDomain> conclusion = new HashSet<RestrictedDomain>()
	{{add(FactoryDomain.CreateRestrictedDomain(fievre, Utilities.MOYEN,Utilities.ELEVE));}};
	
	public static final Constraint c_angine_Fievre = new Rule(premise, conclusion);

	public static final Set<RestrictedDomain> premiseToux = new HashSet<RestrictedDomain>()
	{{add(FactoryDomain.CreateRestrictedDomainOUI(angine));}};
	public static final Set<RestrictedDomain> conclusionToux = new HashSet<RestrictedDomain>() 
	{{add(FactoryDomain.CreateRestrictedDomainOUI(toux));}};
	public static final Constraint c_angine_toux = new Rule(premiseToux, conclusionToux);

	public static final Set<RestrictedDomain> premise2 = new HashSet<RestrictedDomain>() 
	{{
		add(FactoryDomain.CreateRestrictedDomainOUI(grippe)); 
		add(FactoryDomain.CreateRestrictedDomainNON(vaccination));
	}};
	public static final Set<RestrictedDomain> conclusion2 = new HashSet<RestrictedDomain>() 
	{{
		add(FactoryDomain.CreateRestrictedDomain(fievre, Utilities.ELEVE));
		add(FactoryDomain.CreateRestrictedDomainOUI(fatigue));
		add(FactoryDomain.CreateRestrictedDomainOUI(virus));
	}};
	
	public static final Constraint c_grippeNoVac_FievreFatigue = new Rule(premise2, conclusion2);

	public static final Set<RestrictedDomain> premise3 = new HashSet<RestrictedDomain>() 
	{{		
		add(FactoryDomain.CreateRestrictedDomainOUI(sirop));
		add(FactoryDomain.CreateRestrictedDomain(allergieSucre, Utilities.MOYEN));
	}};
	public static final Set<RestrictedDomain> conclusion3 = new HashSet<RestrictedDomain>() 
	{{
		add(FactoryDomain.CreateRestrictedDomainOUI(boutons));
		add(FactoryDomain.CreateRestrictedDomainOUI(toux));
	}};
	public static final Constraint c_siropAllergiM_boutons = new Rule(premise3, conclusion3);

	public static final Set<RestrictedDomain> premise4 = new HashSet<RestrictedDomain>()
	{{
		add(FactoryDomain.CreateRestrictedDomainOUI(sirop));
		add(FactoryDomain.CreateRestrictedDomain(allergieSucre, Utilities.ELEVE));
	}};
	public static final Set<RestrictedDomain> conclusion4 = new HashSet<RestrictedDomain>()
	{{
		add(FactoryDomain.CreateRestrictedDomainOUI(boutons));
		add(FactoryDomain.CreateRestrictedDomainOUI(oedeme));
	}};
	public static final Constraint c_siropAllergiH_oedeme = new Rule(premise4, conclusion4);

	public static final Set<RestrictedDomain> atoms = new HashSet<RestrictedDomain>()
	{{
		add(FactoryDomain.CreateRestrictedDomain(fievre, Utilities.MOYEN, Utilities.ELEVE));
		add(FactoryDomain.CreateRestrictedDomainNON(hypothermie));
	}};
	public static final Constraint c_fievreMH_no_hypothermie = new IncompatibilityConstraint(atoms);
	//#endregion

	public static final Set<Constraint> constraints = new HashSet<>(Arrays.asList(new Constraint[] {
		c_angine_Fievre, 
		c_angine_toux,
		c_fievreMH_no_hypothermie,
		c_grippeNoVac_FievreFatigue,
		c_siropAllergiH_oedeme,
		c_siropAllergiM_boutons
	}));

	public static final Set<Variable> variables = new HashSet<>(Arrays.asList(new Variable[] {
		angine,
		grippe,
		allergieSucre,
		toux,
		fatigue,
		hypothermie,
		fievre,
		boutons,
		oedeme,
		virus,
		vaccination,
		sirop
	}));
}