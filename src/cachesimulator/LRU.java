/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachesimulator;

import java.util.ArrayList;

/**
 *
 * @author lrive
 */
public class LRU {

    //  Holds the general LRU details
    public int _hits;
    public int _misses;
    public long _endTime;
    
    //  Holds the cache that we will be applying LRU's algorithm to.
    public ArrayList<Integer> _lruCache = new ArrayList<>();

    /*
        This Start Method will simply start the LRU replacement algorithm.
        It takes as a parameter an instance of Cache as it relies on several
        variables explained uptop.
    */
    public void Start(Cache cacheDetails) {
        long startTime = System.currentTimeMillis();
        
        /*
            Since we are using dynamic arrays, we need to fill them
            up with nulls so we can reference them later on.
        */
        for (byte i = 0; i < cacheDetails._cacheSize; i++) {
            _lruCache.add(-1);
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
            in the array to find hits would mean our algorithm would be
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
                if (_lruCache.get(j) == address) {
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

                /*
                    This removes the element at the index of hit since we need
                    have the most recently used element at the bottom. The remove 
                    mathod also shifts the remainding elements to the left.
                */
                _lruCache.remove(indexOfHit);
                
                /*
                    Since remove already shifts the values to the left, we just
                    need to add the new address at the end of the list.
                */
                _lruCache.add((cacheDetails._numberOfBlocksPerSet - 1) + setIndex * cacheDetails._numberOfBlocksPerSet, address);
            } else {
                _misses++;

                /*
                    This removes the element that was least recently used and 
                    shifts the remainding elements of the list.
                */
                _lruCache.remove((setIndex * cacheDetails._numberOfBlocksPerSet));
                
                /*
                    Since we want to keep the most recently used element at the bottom
                    we just use the method add
                */
                _lruCache.add(((cacheDetails._numberOfBlocksPerSet - 1) + setIndex * cacheDetails._numberOfBlocksPerSet), address);
            }
        }

        _endTime = System.currentTimeMillis() - startTime;
    }
}
