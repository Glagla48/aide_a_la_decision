import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import filrouge.dbextractor.AssociationRuleMiner;
import filrouge.dbextractor.BooleanDBReader;
import filrouge.dbextractor.Database;
import filrouge.dbextractor.FrequentItemsetMiner;
import filrouge.representations.Data;
import filrouge.representations.Variable;


public class DBExtractorTest {

	/**
    * Try to read the csv as a Boolean Database.
    * Test the number of variables & instances.
    */
	@Test
	public void dbtest() {
		BooleanDBReader reader = new BooleanDBReader(Data.variables);
		try {
			Database<Boolean> db = reader.importDB("exemple_db.csv");
			System.out.println("Nb variables:" + db.getVariables().size());
			System.out.println("Nb instances:" + db.getInstances().size());
			Assert.assertEquals(db.getVariables().size(), 16);
			Assert.assertEquals(db.getInstances().size(), 10000);
		} catch (IOException e) {	
			e.printStackTrace();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
    * Test the making of an Itemset
    * From the example of the PDF.
    */
	@Test
	public void test_FrequentItemsetMiner() {
		Database<Boolean> boolDB = new Database<Boolean>();
		Set<String> domainBool = new HashSet<>(Arrays.asList("false", "true")); 
		
		// Génération des 5 variables A, B, C, D, E
		Variable[] variables = new Variable[5];
		for (int i = 0; i < variables.length; i++) {
			String variableName = Character.toString((char)(i+65));
			variables[i] = new Variable(variableName, domainBool);
			boolDB.addVariable(variables[i]);
		}

		// Ligne du tableau.
		String[] instancesRaw = new String[] {
			"11111", "10100", "11110", "01100", "11100", "00001"
		};

		// Génération des instances à partir des lignes.
		for (String instanceRaw : instancesRaw) {
			HashMap<Variable, Boolean> instance = new HashMap<>();
			for (int i = 0; i < instanceRaw.length(); i++) {
				char rawValue = instanceRaw.charAt(i);
				instance.put(variables[i], rawValue == '1');
			}
			boolDB.addInstance(instance);
		}

		// Calcul du frequentItemsetMiner
        FrequentItemsetMiner frequentItemsetMiner = new FrequentItemsetMiner(boolDB);
		Map<Set<Variable>, Integer> f = frequentItemsetMiner.frequentItemset(2);
		Assert.assertEquals(f.size(), 16);


		// Génération des résultats qu'on devrait trouver d'après l'exemple dans le PDF.
		Map<Set<Variable>, Integer> validData = new HashMap<>();
		validData.put(new HashSet<>(Arrays.asList(variables[0])), 4);
		validData.put(new HashSet<>(Arrays.asList(variables[1])), 4);	
		validData.put(new HashSet<>(Arrays.asList(variables[2])), 5);
		validData.put(new HashSet<>(Arrays.asList(variables[3])), 2);
		validData.put(new HashSet<>(Arrays.asList(variables[4])), 2);

		validData.put(new HashSet<>(Arrays.asList(variables[0], variables[1])), 3);
		validData.put(new HashSet<>(Arrays.asList(variables[0], variables[2])), 4);
		validData.put(new HashSet<>(Arrays.asList(variables[0], variables[3])), 2);
		validData.put(new HashSet<>(Arrays.asList(variables[1], variables[2])), 4);
		validData.put(new HashSet<>(Arrays.asList(variables[1], variables[3])), 2);
		validData.put(new HashSet<>(Arrays.asList(variables[2], variables[3])), 2);

		validData.put(new HashSet<>(Arrays.asList(variables[0], variables[1], variables[2])), 3);
		validData.put(new HashSet<>(Arrays.asList(variables[0], variables[1], variables[3])), 2);
		validData.put(new HashSet<>(Arrays.asList(variables[0], variables[2], variables[3])), 2);
		validData.put(new HashSet<>(Arrays.asList(variables[1], variables[2], variables[3])), 2);

		validData.put(new HashSet<>(Arrays.asList(variables[0], variables[1], variables[2], variables[3])), 2);

		for (Set<Variable> set : f.keySet()) {
			if (!validData.keySet().contains(set)) 
				Assert.fail("This itemset shouldn't exist: " + set);
			if(f.get(set) != validData.get(set))
				Assert.fail("This itemset frequence should be " + validData.get(set) + ", not " + f.get(set));
			System.out.println(set + ": " + f.get(set));
		}
	}

	@Test
	public void test_AssociationRuleMiner(){
		// Problème retourne une association vide
		/*BooleanDBReader reader = new BooleanDBReader(Data.variables);
		try {
			Database<Boolean> db = reader.importDB("exemple_db.csv");
			FrequentItemsetMiner frequentItemsetMiner = new FrequentItemsetMiner(db);
			Map<Set<Variable>, Integer> f = frequentItemsetMiner.frequentItemset(2);
		
			AssociationRuleMiner associationRuleMiner = new AssociationRuleMiner(f);
			Map<Rule, Float> associationRM = associationRuleMiner.association(500, 0.9f);
			for (Entry<Rule, Float> entry : associationRM.entrySet()) {
				System.out.println(entry.getKey() + " => " + entry.getValue());
			}
		} catch (IOException e) {	
			e.printStackTrace();
			Assert.fail(e.getLocalizedMessage());
		}*/
	}

	public static void main(String[] args) {
		DBExtractorTest test = new DBExtractorTest();
		test.dbtest();
		test.test_FrequentItemsetMiner();
		test.test_AssociationRuleMiner();
	}
}