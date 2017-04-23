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

    public int _hits;
    public int _misses;
    public long _endTime;
    public ArrayList<Integer> _lruCache = new ArrayList<>();

    public void Start(Cache cacheDetails) {
        for (byte i = 0; i < cacheDetails._cacheSize; i++) {
            _lruCache.add(null);
        }

        int address;
        int setIndex;
        int indexOfHit;

        int startingIndex;
        int endingIndex;

        for (byte i = 0; i < cacheDetails._addresses.size(); i++) {
            address = cacheDetails._addresses.get(i);
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

                _lruCache.remove(indexOfHit);
                _lruCache.add((cacheDetails._numberOfBlocksPerSet - 1) + setIndex * cacheDetails._numberOfBlocksPerSet, address);
            } else {
                _misses++;

                _lruCache.remove((setIndex * cacheDetails._numberOfBlocksPerSet));
                _lruCache.add(((cacheDetails._numberOfBlocksPerSet - 1) + setIndex * cacheDetails._numberOfBlocksPerSet), address);
            }
        }

        _endTime = System.currentTimeMillis() - cacheDetails._startTime;
    }
}
