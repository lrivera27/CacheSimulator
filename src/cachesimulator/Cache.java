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
    public ArrayList<Integer> addresses = new ArrayList<>();
    public byte cacheSize;
    public byte ways;
    public long startTime;
//Constructor
    public Cache (ArrayList<Integer> addresses, byte ways, byte cacheSize){
        this.addresses = addresses;
        this.ways = ways;
        this.cacheSize = cacheSize;
        startTime = System.currentTimeMillis();
    }
//
// Aqui estaba el metodo de setup pero lo quite pq es mejor inicializar las cosas con el contructor ^
    
    public void displayCache() {

    }
    
    public void LoadAddresses() {
        
    }
}
