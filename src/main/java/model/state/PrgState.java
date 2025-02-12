package model.state;

import exceptions.ADTException;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import model.adt.*;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class PrgState {
    private MyIDictionary<String, IValue> symTable;
    private MyIStack<IStatement> exeStack;
    private MyIList<String> output;
    private IStatement initialState;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap heap;
    private MyIBarrierTable barrierTable;
    private static int lastIndex;
    private int id;

    private synchronized int getNewId()
    {
        lastIndex++;
        return lastIndex;
    }
    public PrgState(MyIDictionary<String, IValue> symTable, MyIStack<IStatement> exeStack, MyIList<String> output,
                    IStatement initialState, MyIDictionary<StringValue, BufferedReader> file, MyIHeap h, MyIBarrierTable barrierTable)
    {
        this.symTable = symTable;
        this.exeStack = exeStack;
        this.output = output;
        this.initialState = initialState.deepCopy();
        this.exeStack.push(this.initialState);
        this.fileTable = file;
        this.heap = h;
        this.barrierTable = barrierTable;
        this.id = getNewId();
    }

    public PrgState(IStatement statement)
    {
        this.symTable = new MyDictionary<>();
        this.exeStack = new MyStack<>();
        this.output = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.heap = new MyHeap();
        this.barrierTable = new MyBarrierTable();
        this.exeStack.push(statement);
    }

    public String fileTableToString()
    {
        StringBuilder text = new StringBuilder();
        text.append("FileTable:\n");
        if (this.fileTable != null) {
            for (StringValue k : this.fileTable.getKeys()) {
                text.append(k).append("\n");
            }
        }
        return text.toString();
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable()
    {
        return this.fileTable;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return this.symTable;
    }

    public MyIStack<IStatement> getExeStack(){
        return this.exeStack;
    }

    public MyIList<String> getOutput()
    {
        return this.output;
    }

    public MyIHeap getHeap()
    {
        return this.heap;
    }

    public MyIBarrierTable getBarrierTable(){return this.barrierTable;}

    public void setHeap(MyIHeap heap)
    {
        this.heap = heap;
    }

    public PrgState oneStep() throws ADTException, ExpressionException, FileNotFoundException {
        if (exeStack.isEmpty())
            throw new EmptyStackException("prgstate stack is empty");
        IStatement current = exeStack.pop();
        return current.execute(this);
    }

    public boolean isNotComplete()
    {
        return ! this.exeStack.isEmpty();
    }

    public String toString()
    {
        return "PrgState with id: " + this.id + "\n" + this.symTable.toString() + "\n" + this.exeStack.toString() + "\n" + this.output.toString() + "\n" + fileTableToString() + "\n"
                + this.heap.toString() + "\n";
    }

    public String getId()
    {
        return "State: " + this.id;
    }

    public int getPrgById(){
        return this.id;
    }
}
