package model.adt;

import exceptions.ADTException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MyBarrierTable implements MyIBarrierTable {
    private final HashMap<Integer, Pair<Integer, List<Integer>>> map;
    ReentrantLock lock;
    private int nextFree = 0;

    public MyBarrierTable(){
        this.map = new HashMap<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return this.map.containsKey(key);
        }
    }

    @Override
    public void put(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized(this) {
            if (this.map.containsKey(key))
                throw new ADTException("Key already existent in Semaphore Table");
            this.map.put(key, value);
        }
    }

    @Override
    public Pair<Integer, List<Integer>> getValue(int key) throws ADTException {
        synchronized(this) {
            if (!this.map.containsKey(key))
                throw new ADTException("Key doesn't exist in Semaphore Table");
            return this.map.get(key);
        }
    }

    @Override
    public void update(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this)
        {
            if (!this.map.containsKey(key))
                throw new ADTException("Key doesn't exist in Semaphore Table");
            this.map.replace(key, value);
        }
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getMap()
    {
        synchronized (this){
            return this.map;
        }
    }

    @Override
    public int getFreeAddress(){
        synchronized (this)
        {
            this.nextFree ++;
            return this.nextFree;
        }
    }

    @Override
    public List<Pair<Pair<Integer, Integer>, List<Integer>>> getBarrierTableAsList() {
        this.lock.lock();
        List<Pair<Pair<Integer, Integer>, List<Integer> > > answer = new ArrayList<>();
        this.map.forEach((x, y) -> {
            answer.add(new Pair<>(new Pair<>(x, y.getKey()), y.getValue()));
        });
        this.lock.unlock();
        return answer;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for (int key: this.map.keySet())
            str.append(key).append("-->").append(map.get(key).toString()).append("\n");
        return "SemaphoreTable contains: \n" + str;
    }
}
