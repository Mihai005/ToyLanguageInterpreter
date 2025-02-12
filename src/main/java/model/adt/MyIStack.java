package model.adt;

import exceptions.EmptyStackException;

import java.util.List;
import java.util.Stack;

public interface MyIStack<T>{
    void push(T element);
    T pop() throws EmptyStackException;
    int size();
    boolean isEmpty();
    List<T> toList();
    Stack<String> getAsStringStack();
}
