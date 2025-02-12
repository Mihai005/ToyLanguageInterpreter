package model.adt;

import exceptions.ADTException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface MyIBarrierTable {
    boolean containsKey(int key);
    void put(int key, Pair<Integer, List<Integer>> value) throws ADTException;
    Pair<Integer, List<Integer>> getValue(int key) throws ADTException;
    void update(int key, Pair<Integer, List<Integer>> value) throws ADTException;
    HashMap<Integer, Pair<Integer, List<Integer>>> getMap();
    int getFreeAddress();
    List<Pair<Pair<Integer, Integer>, List<Integer>>> getBarrierTableAsList();
}
