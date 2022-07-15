package filrouge.factories;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Utilities {

	public final static String OUI = "Oui";
	public final static String NON = "Non";
	public final static Set<String> BOOLEAN = new HashSet<String>(Arrays.asList(OUI, NON));

	public final static String ELEVE = "Élevé";
	public final static String MOYEN = "Moyen";
	public final static String FAIBLE = "Faible";
	public final static String AUCUN = "Aucun";
	public final static Set<String> LEVEL = new HashSet<String>(Arrays.asList(AUCUN, FAIBLE, MOYEN, ELEVE));
}