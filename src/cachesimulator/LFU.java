/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachesimulator;

import java.util.ArrayList;

/**
 *
 * @author HerpDerp
 */
public class LFU {

    public int _hits;
    public int _misses;
    public long _endTime;

    public class Block {

        Integer _address;
        int _frequency;

        public Block() {
            _address = -1;
            _frequency = -1;
        }
    }
    public ArrayList<Block> _lfuCache = new ArrayList<>();

    public void Start(Cache cacheDetails) {
        
        //Block block = new Block(null, 0);
        for (byte i = 0; i < cacheDetails._cacheSize; i++) {
            _lfuCache.add(new Block());
            
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
            startingIndex = setIndex * cacheDetails._numberOfBlocksPerSet;
            endingIndex = (cacheDetails._numberOfBlocksPerSet - 1) + setIndex * cacheDetails._numberOfBlocksPerSet;
            for (int j = startingIndex; j <= endingIndex; j++) {
                if (_lfuCache.get(j)._address == address) {
                    indexOfHit = j;
                    break;
                }
            }

            if (indexOfHit >= 0) {
                _hits++;

                _lfuCache.get(indexOfHit)._frequency++;

            } else {
                _misses++;

                int minimumIndex = setIndex * cacheDetails._numberOfBlocksPerSet;
                int minimumFrequency = _lfuCache.get(minimumIndex)._frequency;

                for (int j = startingIndex; j <= endingIndex; j++) {
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
