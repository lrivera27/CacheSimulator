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
    public final ArrayList<Integer> _addresses = new ArrayList<>(Arrays.asList(1, 2, 3, 2, 1, 5, 2, 1, 6, 2, 5, 6));
    public byte _cacheSize;
    public byte _ways;
    public long _startTime;
    // TODO: Change time to Float
    
    
    public void Setup(byte cacheSize, byte ways, String filePath) {
        _startTime = System.currentTimeMillis();
        
        _cacheSize = cacheSize;
        _ways = ways;
    }
    
    public void displayCache() {

    }
    
    public void LoadAddresses() {
        
    }
}
