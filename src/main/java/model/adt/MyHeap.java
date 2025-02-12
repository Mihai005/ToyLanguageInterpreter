package model.adt;
import exceptions.KeyNotFoundException;
import model.value.IValue;
import java.util.Map;

public class MyHeap implements MyIHeap{
    private MyIDictionary<Integer, IValue> map;
    static int nextFree = 1;

    public MyHeap()
    {
        this.map = new MyDictionary<>();
    }

    @Override
    public int allocate(IValue val) {
        map.insert(nextFree, val);
        nextFree++;
        return nextFree-1;
    }

    @Override
    public IValue getValue(int key) throws KeyNotFoundException {
        if (! this.map.getMap().containsKey(key))
            throw new KeyNotFoundException("Key doesn't exist");
        return this.map.getValue(key);
    }

    @Override
    public void set(Map<Integer, IValue> values) {
           MyDictionary<Integer, IValue> m = new MyDictionary<>();
           for (Map.Entry<Integer, IValue> v : values.entrySet())
           {
               m.insert(v.getKey(), v.getValue());
           }
           this.map = m;
    }

    @Override
    public Map<Integer, IValue> getMap() {
        return this.map.getMap();
    }

    @Override
    public boolean containsKey(int key) {
        return this.map.getMap().containsKey(key);
    }

    @Override
    public void update(int address, IValue value)
    {
        this.map.insert(address, value);
    }

    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for (int k : this.map.getKeys()) {
            try {
                str.append(k).append("-->").append(map.getValue(k).toString()).append("\n");
            } catch (KeyNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return "MyHeap contains: \n" + str;
    }
}
