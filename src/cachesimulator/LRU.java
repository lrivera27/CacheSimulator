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
    
   
    public void Start(Cache cacheDetails){
        for(byte i = 0; i < cacheDetails._cacheSize; i++){
            _lruCache.add(null);
        }
        
        int address;
        int setIndex;
        int indexOfHit;
        
        for(byte i = 0; i < cacheDetails._addresses.size(); i++){
            address = cacheDetails._addresses.get(i);
            indexOfHit = _lruCache.indexOf(address);
            setIndex = address % cacheDetails._ways;
            
            
            if(indexOfHit > 0) {
                System.out.println("HEREEEE!!");
                _hits++;
                
                _lruCache.remove(indexOfHit);
                _lruCache.add((cacheDetails._numberOfBlocksPerSet - 1) + setIndex * cacheDetails._numberOfBlocksPerSet, address);
            } else {
                _misses++;
                
                _lruCache.remove((setIndex * cacheDetails._numberOfBlocksPerSet));
                _lruCache.add(((cacheDetails._numberOfBlocksPerSet - 1) + setIndex * cacheDetails._numberOfBlocksPerSet), address);
            }
            
            DisplayCache();
        }
        
        _endTime = System.currentTimeMillis() - cacheDetails._startTime;
    }
    
    public void DisplayCache(){
        System.out.println("_______");
        for(byte i = 0; i < _lruCache.size(); i++){
            System.out.print("|__");
            System.out.print(_lruCache.get(i));
            System.out.println("__|");
        }
        System.out.println("_______");
    }
}
