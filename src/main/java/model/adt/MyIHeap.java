package model.adt;

import exceptions.KeyNotFoundException;
import model.value.IValue;

import java.util.List;
import java.util.Map;

public interface MyIHeap {
    int allocate(IValue val);
    IValue getValue(int key) throws KeyNotFoundException;
    boolean containsKey(int key);
    void set(Map<Integer, IValue> values);
    Map<Integer, IValue> getMap();
    void update(int address, IValue value);
}
