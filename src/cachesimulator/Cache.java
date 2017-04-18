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
    public ArrayList<Integer> _addresses = new ArrayList<>();
    public byte _cacheSize;
    public byte _ways;
    public long _startTime;
    public int _numberOfBlocksPerSet;
//Constructor
    public Cache (ArrayList<Integer> addresses, byte ways, byte cacheSize){
        _addresses = addresses;
        _ways = ways;
        _cacheSize = cacheSize;
        _numberOfBlocksPerSet = _cacheSize / _ways;
        _startTime = System.currentTimeMillis();

    }
//
// Aqui estaba el metodo de setup pero lo quite pq es mejor inicializar las cosas con el contructor ^
    
    public void displayCache() {

    }
    
    public void LoadAddresses() {
        
    }
}
