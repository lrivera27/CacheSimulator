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
public class Random {

    public int _hits;
    public int _misses;
    public long _endTime;
    public ArrayList<Integer> _randomCache = new ArrayList<>();

    public void Start(Cache cacheDetails) {
        for (byte i = 0; i < cacheDetails._cacheSize; i++) {
            _randomCache.add(-1);
        }

        int address;
        int setIndex;
        int indexOfHit;

        int startingIndex;
        int endingIndex;

        int randomIndex;

        int hasSpace;

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

            hasSpace = -1;
            for (int j = startingIndex; j <= endingIndex; j++) {

                /*  
                    If we find that the address is already in our cache
                    we renew the value of indexOfHit as the index where
                    the address was.
                 */
                if (_randomCache.get(j) == address) {
                    indexOfHit = j;

                    if (hasSpace >= 0) {
                        break;
                    }
                }
                if (_randomCache.get(j) == -1) {
                    hasSpace = j;
                }
            }
            if (indexOfHit >= 0) {
                _hits++;
            } else {
                _misses++;

                if (hasSpace >= 0) {
                    
                    randomIndex = hasSpace + setIndex * cacheDetails._numberOfBlocksPerSet;
                    
                } else {
                    
                    randomIndex = ((int) (Math.random()) * (cacheDetails._numberOfBlocksPerSet - 1))
                            + setIndex * cacheDetails._numberOfBlocksPerSet;
                }
                _randomCache.set(randomIndex, address);
            }
            cacheDetails.DisplayCache(_randomCache);
        }
    }
}
