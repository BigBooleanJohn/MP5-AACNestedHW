package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Your Name Here
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
    // +-----------+---------------------------------------------------
    // | Constants |
    // +-----------+

    /**
     * The default capacity of the initial array.
     */
    static final int DEFAULT_CAPACITY = 16;

    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The size of the associative array (the number of key/value pairs).
     */
    int size;

    /**
     * The array of key/value pairs.
     */
    KVPair<K, V> pairs[];

    // +--------------+------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Create a new, empty associative array.
     */
    @SuppressWarnings({ "unchecked" })
    public AssociativeArray() {
        // Creating new arrays is sometimes a PITN.
        this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
                DEFAULT_CAPACITY);
        this.size = 0;
    } // AssociativeArray()

    // +------------------+--------------------------------------------
    // | Standard Methods |
    // +------------------+

    /**
     * Create a copy of this AssociativeArray.
     */
    public AssociativeArray<K, V> clone() {
        AssociativeArray<K, V> newArray = new AssociativeArray<K, V>();
        for (int i = 0; i < this.pairs.length; i++) {
            if (this.pairs[i] != null) {
                newArray.set(this.pairs[i].key, this.pairs[i].value);
            }
        }
        newArray.size = newArray.size();
        return newArray;
    } // clone()

    /**
     * Convert the array to a string.
     */
    public String toString() {
        String s = "{";// initial empty String
        for (int i = 0; i < this.pairs.length; i++) {
            if (this.pairs[i] != null) {
                s = s + "key" + i + ":";
                s = s + this.pairs[i].key.toString();
                s = s + ",";// providing a comma betwen the key and the value
                s = s + "value" + i + ": ";
                s = s + this.pairs[i].value.toString();
                s = s + " ";// providing spacing between pairs
            } else {
                s = s + "null, ";
            }
        }
        s = s + "}";
        return s;
    } // toString()

    // +----------------+----------------------------------------------
    // | Public Methods |
    // +----------------+

    /**
     * Set the value associated with key to value. Future calls to
     * get(key) will return value.
     */
    @SuppressWarnings({ "unchecked" })
    public void set(K key, V value) {
        Boolean foundValue = false;
        int index = 0;
        int OpenIndex = 0;// find the first null index of the array for a potential storage of the key and
                          // value if not found initially
        boolean foundOpen = false;// find the first null index of the array for a potential storage of the key and
                                  // value if not found initially
        while (foundValue == false && index < this.pairs.length) {
            if (this.pairs[index] == null) {// if we are at a null index
                if (foundOpen == false)// if this is the first null index that we have seen
                {
                    foundOpen = true;
                    OpenIndex = index;
                }
            } else if (this.pairs[index].key.equals(key) == true) {// if the pair at the index we are at has the key
                this.pairs[index].value = value;
                foundValue = true;
            }
            index++;
        }
        if (foundValue == false && foundOpen == true) {// this means we can't find the key int the array, but there is
                                                       // an
                                                       // opening in our array
            KVPair<K, V> newPair = new KVPair<K, V>(key, value);
            this.pairs[OpenIndex] = newPair;
            this.size++;
        } else if (foundValue == false && foundOpen == false) {// this means we couldn't find the key, and have to
                                                               // expand
                                                               // the array
            this.expand();
            KVPair<K, V> newPair = new KVPair<K, V>(key, value);
            this.pairs[this.size] = newPair;
            this.size++;
        }
    } // set(K,V)

    /**
     * Get the value associated with key.
     *
     * @throws KeyNotFoundException
     *                              when the key does not appear in the associative
     *                              array.
     */
    public V get(K key) throws KeyNotFoundException {
        try {
            int i = this.find(key);
            return this.pairs[i].value;
        } catch (KeyNotFoundException K) {
            throw new Error("there is no value corresponding to this key in the associate array\n");
        }
    } // get(K)

    /**
     * Determine if key appears in the associative array.
     */
    public boolean hasKey(K key) {
        for (int i = 0; i < this.size(); i++) {
            if (this.pairs[i] != null) {
                if (this.pairs[i].key.equals(key) == true) {
                    return true;
                }
            }
        }
        return false;// if the code in the for loop doesn't execute, return false
    } // hasKey(K)

    /**
     * Remove the key/value pair associated with a key. Future calls
     * to get(key) will throw an exception. If the key does not appear
     * in the associative array, does nothing.
     */
    public void remove(K key) {
        try {
            int index = this.find(key);
            this.pairs[index] = null;
            this.size = this.size();
        } catch (KeyNotFoundException KNFE) {
            throw new Error("the key was not found");
        }
    }// remove(K)

    /**
     * Determine how many values are in the associative array.
     */
    public int size() {
        int sizeCount = 0;
        for (int i = 0; i < this.pairs.length; i++) {
            if (this.pairs[i] != null) {// if we are not at a null index
                sizeCount++;
            }
        }
        return sizeCount;
    } // size()

    /**
     * sets the key at an index to be a key passed in
     */

    /*
     * public void setKey(K key) {
     * boolean placed = false;
     * for (int i = 0; i < this.pairs.length && placed != true; i++) {
     * if (this.pairs[i] == null) {
     * this.pairs[i].key = key;
     * }
     * }
     * }
     */

    /**
     * Determine how many indices are in the array, these can be null or occupied
     */
    public int length() {
        return this.pairs.length;
    }

    /*
     * Return the value at the index of the AssocArray. if it is null of out of
     * bounds, return null;
     */
    public V valueAt(int index) {
        if (index >= this.pairs.length || this.pairs[index] == null) {
            return null;
        } else {
            return this.pairs[index].value;
        }
    }
    /*
     * Return the key at the index of the AssocArray. if it is null of out of
     * bounds, return null;
     */

    public K KeyAt(int index) {
        if (index >= this.pairs.length || this.pairs[index] == null) {
            return null;
        } else {
            return this.pairs[index].key;
        }
    }

    /* this method increments the size by one */
    public void sizeIncrement() {
        this.size++;
    }

    /* this method determines whether or not the given index is a null index */
    public boolean isNull(int index) {
        if (this.pairs[index] == null) {
            return true;
        } else {
            return false;
        }
    }

    // +-----------------+---------------------------------------------
    // | Private Methods |
    // +-----------------+

    /**
     * Expand the underlying array.
     */
    public void expand() {
        this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
    } // expand()

    /**
     * Find the index of the first entry in `pairs` that contains key.
     * If no such entry is found, throws an exception.
     */
    public int find(K key) throws KeyNotFoundException {
        for (int i = 0; i < this.pairs.length; i++) {
            if (this.pairs[i] != null) {
                if (this.pairs[i].key.equals(key) == true) {
                    return i;
                }
            }
        }
        throw new KeyNotFoundException("The key couldn't be found by the find method\n");
    }// find(K)

} // class AssociativeArray