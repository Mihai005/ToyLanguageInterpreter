package model.adt;

import java.util.List;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T>{
    private final List<T> list;
    public MyList()
    {
        this.list = new ArrayList<>();
    }

    public MyList(List<T> l)
    {
        this.list = l;
    }
    @Override
    public void add(T elem) {
        this.list.add(elem);
    }

    @Override
    public List<T> getAll() {
        return this.list;
    }

    @Override
    public List<T> toList() {
        return list;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for(T element : this.list)
            str.append(element).append("\n");
        return "Out: \n" + str.toString();
    }
}
