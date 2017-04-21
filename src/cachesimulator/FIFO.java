package cachesimulator;
import java.util.ArrayList;

/*
    Software made by: Luis E. Rivera and Christian Camacho
    Objective:
            This class is made to simulate a cache that is 
        using First-in-first-out as it's replacement algorithm.
        It's designed to not move the values around and just
        keep track of which one is the next value to be removed
        from the Cache improving the algorithm's performance (Compared to other Examples).

        It relies on the following variables from the Cache class
        1. cacheSize: This is the size of the cache.
        2. ways: This is how many sets there are for the cache.
        3. startTime: This is just the time in miliseconds created when Cache was first run.
*/

public class FIFO {
    // Holds the general FIFO details
    public int _hits;
    public int _misses;
    public long _endTime;
    
    // Holds the cache that we will apliying FIFO's algorithm to.
    public ArrayList<Integer> _fifoCache = new ArrayList<>();
    
    // Holds the Array with the indexes of the next value to be replaced.
    // The reason we need this to be an array is because this class works 
    // for N-ways, so we need one index for each set.
    public ArrayList<Integer> _cacheIndex = new ArrayList<>();
    
    // This Start Method will simply start the FIFO replacement algorithm.
    // It takes as the parameter an instance of Cache as it relies on several
    // variables explained uptop.
    public void Start(Cache cacheDetails) { 
        // Since we are using Dynamic Arrays, we need to fill them up so
        // we can reference them later on.
        for(byte i = 0; i < cacheDetails._cacheSize; i++){
            _fifoCache.add(null);
        }
        for(byte i = 0; i < cacheDetails._ways; i++){
            _cacheIndex.add(0);
        }
        // Holds the index we are going to be performing FIFO on
        int setIndex;
        // Holds the address gathered from the list of addresses (This needs to be filtered in the Cache class first)
        int address;
        
        for(byte i = 0; i < cacheDetails._addresses.size(); i++) {
            // Loads address at index i
            address = cacheDetails._addresses.get(i);          
            // Calculates which set we will be working on
            setIndex = address % cacheDetails._ways;     
            // Checks wether or not the address taken from the addresses is already in the cache
            // if it is, it's a hit
            if(_fifoCache.contains(address)) {
                _hits++;
                System.out.println("HIT");
            } else {
                // Checks wether the index for the set is more than the number of blocks per set
                // since we dont want to be referencing content out of bounce.
                if((_cacheIndex.get(setIndex)) >= cacheDetails._numberOfBlocksPerSet) {
                   
                    _cacheIndex.set(setIndex, 0);
                }
                _misses++;
                // To get the number we want to replace, we use a formula we created that goes like this:
                // index I want + number of set the number is at times the number of blocks per set
                // i + SetIndex * NumberOfBlocksPerSet
                _fifoCache.set(_cacheIndex.get(setIndex) + (setIndex * cacheDetails._numberOfBlocksPerSet), address);
                _cacheIndex.set(setIndex, _cacheIndex.get(setIndex)+1);
            }
            DisplayCache();
        }
        // This calculates the time it takes to finish the algorithm
        _endTime = System.currentTimeMillis() - cacheDetails._startTime;
    }
    public void DisplayCache(){
        System.out.println("_______");
        for(byte i = 0; i < _fifoCache.size(); i++){
            System.out.print("|__");
            System.out.print(_fifoCache.get(i));
            System.out.println("__|");
        }
        System.out.println("_______");
    }
}

