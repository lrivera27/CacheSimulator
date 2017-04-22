package cachesimulator;
import java.util.ArrayList;

/*
    Software made by: Luis E. Rivera and Christian Camacho
    Objective:
            This class is made to simulate a cache that is
        using Least Frequently as it's replacement algorithm.
        It's designed to remove the address with the lowest number
        of uses. It does this by keeping track of how many uses or
        frequency it has.

        It relies on the following variables from the Cache class
        1. _cacheSize: This is the size of the cache.
        2. _ways: This is how many sets there are for the cache.
        3. _startTime: This is just the time in miliseconds created when Cache was first run.
        4. _numberOfBlocksPerSet: This is how many blocks there are per set of the cache.
*/

public class LFU {
    
    //  Holds the general LFU details
    public int _hits;
    public int _misses;
    public long _endTime;

    /* 
        This class will hold the details for each block inside the cache
        We need this class to keep track of the frequency of each address
        in our cache.
    */
    public class Block {

        Integer _address;
        int _frequency;

        public Block() {
            _address = -1;
            _frequency = -1;
        }
    }
    
    //  Holds the cache that we will apliying LFU's algorithm to.
    public ArrayList<Block> _lfuCache = new ArrayList<>();

    /*
        This method will simply start the LFU replacement algorithm.
        It takes as the parameter an instance of Cache as it relies on
        several class variables explained uptop.
    */
    public void Start(Cache cacheDetails) {
         
        /*
            Since we are using dynamic arrays, we need to fill them
            up with instances of Blocks so we can reference them later on. 
        */
        for (byte i = 0; i < cacheDetails._cacheSize; i++) {
            _lfuCache.add(new Block());
            
        }
        
        /*  
            Holds the address from the array of address
            since we will be using it multiple times.
            This needs to be filtered in the Cache class first.
        */
        int address;
        
        //  Holds the index of the set we will be performing the replacement.
        int setIndex;
        
        /* 
            Holds the index of the address that had a hit.
            Caution: This does NOT hold the real index, it still needs
            to go through our formula: Index + Set * Blocks per set
        */
        int indexOfHit;
        
        /*
            Since we have only one array, doing a regular linear search
            in the array to find hits, would mean our algorithm would be
            really slow, so these two variables will hold the first and last
            index of the set.
        */
        int startingIndex;
        int endingIndex;

        for (byte i = 0; i < cacheDetails._addresses.size(); i++) {
            
            //  Loads address at index i
            address = cacheDetails._addresses.get(i);
            
            //  Calculates which set we will be working on.
            setIndex = address % cacheDetails._ways;
            
            indexOfHit = -1;
            
            /*
                To calculate the starting index we simply use our formula
                which is Index + Set * Blocks per ser, but since we want
                the first index of the set, we simple use Set * blocks per set.
            */
            startingIndex = setIndex * cacheDetails._numberOfBlocksPerSet;
            
            /*
                To calculate the ending index we again use our formula
                but this time we need the index to be the last one
                so we take the number of blocks per set and re-write our 
                formula as: Blocks per set + Set * Blocks per set.
            */
            endingIndex = (cacheDetails._numberOfBlocksPerSet - 1) + setIndex * cacheDetails._numberOfBlocksPerSet;
            
            for (int j = startingIndex; j <= endingIndex; j++) {
                
                /*  
                    If we find that the address is already in our cache
                    we renew the value of indexOfHit as the index where
                    the address was.
                */
                if (_lfuCache.get(j)._address == address) {
                    indexOfHit = j;
                    break;
                }
            }

            /*  
                If the indexOfHit is greater than 0 it means we changed the
                initial value of indexOfHit (which was -1), meaning there was
                in fact a hit.
            */
            if (indexOfHit >= 0) {
                _hits++;
                _lfuCache.get(indexOfHit)._frequency++;

            } else {
                _misses++;

                /*
                    To calculate which address has the lowest value of frequency
                    we first need to set the first one as the "minimum", then
                    use that one to compare with the rest.
                */
                int minimumIndex = setIndex * cacheDetails._numberOfBlocksPerSet;
                int minimumFrequency = _lfuCache.get(minimumIndex)._frequency;

                for (int j = startingIndex; j <= endingIndex; j++) {
                    
                    /*
                        If the frequency of 
                    */
                    if (_lfuCache.get(j)._frequency < minimumFrequency) {
                        minimumFrequency = _lfuCache.get(j)._frequency;
                        minimumIndex = j;
                    }
                }

                _lfuCache.get(minimumIndex)._address = address;
                _lfuCache.get(minimumIndex)._frequency = 0;
            }
            DisplayCache();
        }
    }

    public void DisplayCache() {
        System.out.println("_______");
        for (byte i = 0; i < _lfuCache.size(); i++) {
            System.out.print("|__");
            System.out.print(_lfuCache.get(i)._address);
            System.out.println("__|");
        }
        System.out.println("_______");
    }
}
