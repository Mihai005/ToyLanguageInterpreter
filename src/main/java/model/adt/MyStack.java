package model.adt;

import exceptions.EmptyStackException;

import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{

    private final Stack<T> stack;
    public MyStack()
    {
        this.stack = new Stack<>();
    }
    @Override
    public void push(T element) {
        this.stack.push(element);
    }

    @Override
    public T pop() throws EmptyStackException {
        if (this.stack.isEmpty())
            throw new EmptyStackException("Stack is empty!\n");
        return this.stack.pop();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for(T element: this.stack)
            str.append(element).append("\n");
        return "MyStack contains: " + str.toString();
    }

    @Override
    public Stack<String> getAsStringStack() {
        Stack<String> strStack = new Stack<>();

        //preserve the order of the elements
        for(int i = stack.size() - 1; i >= 0; i--){
            strStack.push(stack.get(i).toString());
        }

        return strStack;
    }

    @Override
    public List<T> toList()
    {
        return this.stack.stream().toList();
    }
}
