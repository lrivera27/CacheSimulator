/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cachesimulator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author lrive
 */
public class Cache {
    // Variable holds the addresses loaded from the file
    public final ArrayList<Integer> addresses = new ArrayList<>(Arrays.asList(1, 2, 3, 2, 1, 5, 2, 1, 6, 2, 5, 6));
    public byte cacheSize;
    public byte ways;
    public long startTime;
    // TODO: Change time to Float
    
    
    public void Setup(byte cacheSize, byte ways, String filename) {
        startTime = System.currentTimeMillis();
        
        this.cacheSize = cacheSize;
        this.ways = ways;
    }
    
    public void displayCache() {

    }
}
