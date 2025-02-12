package model.adt;

import exceptions.ADTException;
import exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V>{
    private final Map<K, V> map;
    public MyDictionary()
    {
        this.map = new HashMap<>();
    }
    public MyDictionary(Map<K, V> map)
    {
        this.map = new HashMap<>(map);
    }
    @Override
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public V getValue(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key))
            throw new KeyNotFoundException("Key doesn't exist\n");
        return this.map.get(key);
    }

    @Override
    public void remove(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key))
            throw new KeyNotFoundException("Key doesn't exist\n");
        this.map.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return this.map.containsKey(key);
    }
    public Map<K, V> getMap()
    {
        return this.map;
    }
    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for (K key: this.map.keySet())
            str.append(key.toString()).append("-->").append(map.get(key).toString()).append("\n");
        return "MyDictionary contains: \n" + str;
    }
    @Override
    public Set<K> getKeys()
    {
        return this.map.keySet();
    }

    @Override
    public MyIDictionary<K, V> deepCopy()
    {
        MyIDictionary<K, V> deepCopyDict = new MyDictionary<>();
        for(K key: this.map.keySet())
            deepCopyDict.insert(key, this.map.get(key));
        return deepCopyDict;
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!this.map.containsKey(key))
            throw new ADTException("Key not present in Dictionary");
        this.map.replace(key, value);
    }
}
