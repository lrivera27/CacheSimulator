/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachesimulator;

import cachesimulator.LFU.Block;
import java.util.ArrayList;

/**
 *
 * @author lrive
 */
public class Cache {
// Variable holds the addresses loaded from the file

    public ArrayList<Integer> _addresses = new ArrayList<>();
    public byte _cacheSize;
    public byte _ways;
    public long _startTime;
    public int _numberOfBlocksPerSet;
//Constructor

    public Cache(ArrayList<Integer> addresses, byte ways, byte cacheSize) {
        _addresses = addresses;
        _ways = ways;
        _cacheSize = cacheSize;
        _numberOfBlocksPerSet = _cacheSize / _ways;
        _startTime = System.currentTimeMillis();

    }

    public void DisplayCache(ArrayList<Integer> cache) {
        System.out.println("_______");
        for(byte i = 0; i < cache.size(); i++){
            System.out.print("|__");
            System.out.print(cache.get(i));
            System.out.println("__|");
        }
        System.out.println("_______");
    }
    
    /*
        Since Java thinks these two functions are the same, we need a 
        dummy variable to distinguish the two.
    */
    public void DisplayCache(ArrayList<Block> cache, boolean erasure) {
        System.out.println("_______");
        for (byte i = 0; i < cache.size(); i++) {
            System.out.print("|__");
            System.out.print(cache.get(i)._address);
            System.out.println("__|");
        }
        System.out.println("_______");
    }

    public void LoadAddresses() {

    }
}
