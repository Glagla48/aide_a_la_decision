package filrouge.dbextractor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import filrouge.representations.Variable;
import filrouge.dbextractor.Database;

public class FrequentItemsetMiner{

    private Database<Boolean> database;

    public FrequentItemsetMiner(Database<Boolean> database)
    {
        this.database = database;
    }

    /**
     * Gets dataBase attribute.
     * @return A BooleanDatabase.
     */
    public Database<Boolean> getDatabase() {return database;}

    /**
     * Sets database attribute.
     * @param database A BooleanDatabase.
     */
    public void setDatabase(Database<Boolean> database) {
        this.database = database;
    }

    /**
     * Compares 2 Set of Variable.  
     * If all the elements of set1 are in the map and are of value true, returns true.
     * Returns false otherwise.
     * @param set1 A Set of Variable.
     * @param map A map of Variable and its associated Charater. 
     * @return true if set1 is included in set2.
     */
    public boolean hasSet(Set<Variable> set1, Map<Variable, Boolean> map)
    {
        for(Variable var : set1)
        {
            if(map.keySet().contains(var))
            {
        		if(!map.get(var)) return false;
            }
            else
                return false;
        }
        return true;
    }


    public Set<Set<Variable>> compose(Set<Variable> set1, Set<Variable> set2)
    {
        Set<Set<Variable>> composed = new HashSet<>();
        if(!set1.equals(set2))
        {
            Iterator<Variable> iter1 = set1.iterator();
            Iterator<Variable> iter2 = set2.iterator();
            while(iter1.hasNext())
            {
                Variable newVar = iter1.next();
                if(!set1.contains(newVar))
                {
                    Set<Variable> newSet = new HashSet<>();
                    newSet.addAll(set1);
                    newSet.add(newVar);
                    composed.add(newSet);
                }
                
            }
            while(iter2.hasNext())
            {
                Variable newVar = iter2.next();
                if(!set1.contains(newVar))
                {
                    Set<Variable> newSet = new HashSet<>();
                    newSet.addAll(set1);
                    newSet.add(newVar);
                    composed.add(newSet);
                }
                
            }
        }


        return composed;
    }

    /**
     * Creates a cartesian product with the values of a Set.
     * @param set A Set of a Set of Variable.
     * @return The cartesian product.
     */
    public Set<Set<Variable>> compose(Set<Set<Variable>> set)
    {
        Set<Set<Variable>> composed = new HashSet<>();

        int pass = 0;
        for(Set<Variable> set_1 : set)
        {
            int count = 0;
            pass++;
            for(Set<Variable> set_2 : set)
            {
                count++;
                if(count <= pass)
                    continue;
 
                composed.addAll((this.compose(set_1, set_2)));
            }

        }


        return composed;
    }

    

    /**
     * Associates the sets of Variable from the database with a frequency.
     * @param minFreq An int. If the frequency of a set of Variable is smaller than the param,
     * the set won't be in the returned map.
     * @return Returns a map of a set of Variable and an Integer representing its frequency.
     */
    public Map<Set<Variable>, Integer> frequentItemset(int minFreq)
    {
        Map<Set<Variable>, Integer> frqItemSet = new HashMap<>();

        Set<Set<Variable>> tmp = new HashSet<>();
        /*
        Regarder d'abord pour les singletons. 
        avant le pd cart. si leur fr < min_fr ne pas faire le duo
        faire la frq des paires avant les triplets
        etc.
         */
        int count = 0;
        /* Cree et calcule les frequances des variables seules */
        for(Variable v: this.database.getVariables())
        {
            HashSet<Variable> setVar = new HashSet<>();
            setVar.add(v);
            count = 0;
            for(Map<Variable, Boolean> transaction : this.database.getInstances())
            {
                if(this.hasSet(setVar, transaction))
                    count++;
            }
            
            if(count >= minFreq)
            {
                frqItemSet.put(setVar, count);
                tmp.add(setVar);

            }
        }
      
        /* Cree et calcule pour tous les sous ensembles. */
        Set<Set<Variable>> newTmp = new HashSet<>();
        newTmp = tmp;

        while(tmp.size() > 1)
        {
            
            tmp = this.compose(newTmp);
            newTmp = new HashSet<>();
            for(Set<Variable> setVar : tmp)
            {
                count = 0;
                for(Map<Variable, Boolean> transaction : this.database.getInstances())
                {
                    if(this.hasSet(setVar, transaction))
                    {
                        count++;
                    }
				}
                if(count >= minFreq)
                {
                    frqItemSet.put(setVar, count);
                    newTmp.add(setVar);
                }
            }       
        }

        return frqItemSet;
    }     


    public static void main(String[] args) {
        Set<String> domainBool = new HashSet<>(); 
        domainBool.add("0");
        domainBool.add("1");
        
        //Variables 5
        Variable A = new Variable("A", domainBool);
        Variable B = new Variable("B", domainBool);
        Variable C = new Variable("C", domainBool);
        Variable D = new Variable("D", domainBool);
        Variable E = new Variable("E", domainBool);
        
        //Transactions 4x5
        Map<Variable,Boolean> t1 = new HashMap<>();
        t1.put(A, true);
        t1.put(B, true);
        t1.put(C, true);
        t1.put(D, true);
        t1.put(E, true);
        
        Map<Variable,Boolean> t2 = new HashMap<>();
        t2.put(A, true);
        t2.put(B, false);
        t2.put(C, true);
        t2.put(D, false);
        t2.put(E, false);
        
        Map<Variable,Boolean> t3 = new HashMap<>();
        t3.put(A, true);
        t3.put(B, true);
        t3.put(C, true);
        t3.put(D, true);
        t3.put(E, false);
        
        Map<Variable,Boolean> t4 = new HashMap<>();
        t4.put(A, false);
        t4.put(B, true);
        t4.put(C, true);
        t4.put(D, false);
        t4.put(E, false);

        Map<Variable,Boolean> t5 = new HashMap<>();
        t5.put(A, true);
        t5.put(B, true);
        t5.put(C, true);
        t5.put(D, false);
        t5.put(E, false);

        Map<Variable,Boolean> t6 = new HashMap<>();
        t6.put(A, false);
        t6.put(B, false);
        t6.put(C, false);
        t6.put(D, false);
        t6.put(E, true); 

        //BoolDataBase
        Database<Boolean> boolDB = new Database<>();
        boolDB.addVariable(A);
        boolDB.addVariable(B);
        boolDB.addVariable(C);
        boolDB.addVariable(D);
        boolDB.addVariable(E);
        boolDB.addInstance(t1);
        boolDB.addInstance(t2);
        boolDB.addInstance(t3);
        boolDB.addInstance(t4);
        boolDB.addInstance(t5);
        boolDB.addInstance(t6);
        
        //Test
        FrequentItemsetMiner frequentItemsetMiner = new FrequentItemsetMiner(boolDB);
        Map<Set<Variable>, Integer> res = frequentItemsetMiner.frequentItemset(3);
        for(Set<Variable> set : res.keySet())
        {
            for(Variable v : set){
                System.out.print(v.getName()+",");
            }
            System.out.println(res.get(set));
        }
    }
}