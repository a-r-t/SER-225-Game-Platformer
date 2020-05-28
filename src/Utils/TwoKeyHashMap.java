package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoKeyHashMap<K1,K2,V> {
    
    private Map<K1,Map<K2,V>> tkHashMap;
    
    public TwoKeyHashMap(){
        tkHashMap = new HashMap<K1,Map<K2,V>>();
    }
    
    public V put(K1 k1, K2 k2, V v) {
        Map<K2,V> k2Map = null;
        if(tkHashMap.containsKey(k1)) {
            k2Map = tkHashMap.get(k1);
        } else {
            k2Map = new HashMap<K2,V>();
            tkHashMap.put(k1, k2Map);
        }
        return k2Map.put(k2, v);
    }
    
    public boolean containsKey(K1 k1, K2 k2) {
        if(tkHashMap.containsKey(k1)) {
            Map<K2,V> k2Map = tkHashMap.get(k1);
            return k2Map.containsKey(k2);
        }
        return false;
    }
    
    public boolean containsKey(K1 k1) {
        return tkHashMap.containsKey(k1);
    }
    
    public V get(K1 k1, K2 k2) {
    	if(tkHashMap.containsKey(k1)) {
            Map<K2,V> k2Map = tkHashMap.get(k1);
            return k2Map.get(k2);
        }
        return null;
    }
    
    public Map<K2,V> get(K1 k1) {
        return tkHashMap.get(k1);
    }
    
    public V remove(K1 k1, K2 k2) {
        if(tkHashMap.containsKey(k1)) {
            Map<K2,V> k2Map = tkHashMap.get(k1);
            return k2Map.remove(k2);
        } 
        return null;
    }
    
    private Map<K2,V> remove(K1 k1) {
        return tkHashMap.remove(k1);
    }
    
    public int size() {
        int size = 0;
        for(Map<K2,V> k2Map : tkHashMap.values()) {
            size++;
            size += k2Map.size();
        }
        return size;
    }
    
    public List<V> getAllItems(){
        List<V> items = new ArrayList<V>();
        for(Map<K2,V> k2Map : tkHashMap.values()) {
            for(V value : k2Map.values()) {
                items.add(value);
            }
        }
        return items;
    }
    
    public void clear() {
        for(Map<K2,V> k2Map : tkHashMap.values()) {
            k2Map.clear();
        }
        tkHashMap.clear();
    }
    
}