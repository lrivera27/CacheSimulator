/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachesimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author lrive
 */
public class Optimum {

    public int _hits;
    public int _misses;
    public long _endTime;

    public class Block {

        Integer _address;
        int _frequency;

        public Block(Integer address, int frequency) {
            _address = address;
            _frequency = frequency;
        }
    }

    public ArrayList<Integer> _optimumCache = new ArrayList<>();
    public ArrayList<Block> _listOfFrequency = new ArrayList<>();

    public void Start(Cache cacheDetails) {
        long startTime = System.currentTimeMillis();

        /*
            Since we are using dynamic arrays, we need to fill them
            up with instances of Blocks so we can reference them later on. 
         */
        for (byte i = 0; i < cacheDetails._cacheSize; i++) {
            _optimumCache.add(-1);

        }

        Set<Integer> unique = new HashSet<>(cacheDetails._addresses);

        for (Integer key : unique) {
            int frequency = Collections.frequency(cacheDetails._addresses, key);
            _listOfFrequency.add(new Block(key, frequency));
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
                if (_optimumCache.get(j) == address) {
                    indexOfHit = j;
                    break;
                }
            }

            if (indexOfHit >= 0) {
                _hits++;
            } else {
                _misses++;

                int minimumIndex = startingIndex;
                int minimumFrequency = 0;
                Block block = null;
                for (int j = startingIndex; j <= endingIndex; j++) {
               
                    int indexInList = -1;
                    for (int x = 0; x < _listOfFrequency.size(); x++) {
                        if (_optimumCache.get(j) == _listOfFrequency.get(x)._address) {
                            block = _listOfFrequency.get(x);
                            indexInList = x;
                        }
                    }

                    if (indexInList == -1) {
                        System.out.println("-1 Found -> Index: " + j);
                        minimumIndex = j;
                        break;
                    } else {
                        if (minimumFrequency > block._frequency) {
                            minimumFrequency = block._frequency;
                            minimumIndex = j;
                        }
                    }

                }

                _optimumCache.set(minimumIndex, address);
            }
            cacheDetails.DisplayCache(_optimumCache);
        }

        _endTime = System.currentTimeMillis() - startTime;
        System.out.println("ENDED!!!!!");
    }

}
