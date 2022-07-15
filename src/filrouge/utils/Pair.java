package filrouge.utils;

/**
 * Creates a Pair. The first value and the second can be of different type.
 * @param <K> The type of the forst element.
 * @param <V> The type of the second element.
 */
public class Pair<K, V>{

    /**
     * The first value of K type. 
     */
    public K key;

    /**
     * The secon value of V type.
     */
    public V value;

    /**
     * Creates a Pair. The first value and the second can be of different type.
     * @param key The first value of the Pair. 
     * @param value The second value of the Pair.
     */
    public Pair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}