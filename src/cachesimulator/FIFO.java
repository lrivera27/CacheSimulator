package cachesimulator;
import java.util.ArrayList;
public class FIFO {
    public int hits;
    public int misses;
    public long endTime;
    public ArrayList<Integer> fifoCache = new ArrayList<>();
    public ArrayList<Integer> cacheIndex = new ArrayList<>();
    public void Start(Cache cacheDetails) { 
        for(byte i = 0; i < cacheDetails.cacheSize; i++){
            fifoCache.add(null);
        }
        for(byte i = 0; i < cacheDetails.ways; i++){
            cacheIndex.add(0);
        }
        int numberOfBlocksPerSet = cacheDetails.cacheSize / cacheDetails.ways;
        int setIndex;
        int address;
        for(byte i = 0; i < cacheDetails.addresses.size(); i++) {      
            address = cacheDetails.addresses.get(i);          
            setIndex = address % cacheDetails.ways;     
            if(fifoCache.contains(address)) {
                hits++;
            } else {
                if((cacheIndex.get(setIndex)) >= numberOfBlocksPerSet) {
                   
                    cacheIndex.set(setIndex, 0);
                }
                misses++;
                fifoCache.set(cacheIndex.get(setIndex) + (setIndex * numberOfBlocksPerSet), address);
                cacheIndex.set(setIndex, cacheIndex.get(setIndex)+1);
            }
        }
        endTime = System.currentTimeMillis() - cacheDetails.startTime;
       
    }
    public void DisplayCache(){
        System.out.println("_______");
        for(byte i = 0; i < fifoCache.size(); i++){
            System.out.print("|__");
            System.out.print(fifoCache.get(i));
            System.out.println("__|");
        }
        System.out.println("_______");
    }
}

