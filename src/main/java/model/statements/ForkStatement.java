package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyStack;
import model.state.PrgState;
import model.types.IType;

import java.io.FileNotFoundException;

public class ForkStatement implements IStatement{
    private final IStatement statement;

    public ForkStatement(IStatement s)
    {
        this.statement = s;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException, FileNotFoundException {
        //return new PrgState(state.getSymTable().deepCopy(), state.getExeStack(), state.getOutput(), this.statement, state.getFileTable(),
               // state.getHeap(), state.getBarrierTable());
        return new PrgState(state.getSymTable().deepCopy(), new MyStack<>(), state.getOutput(), this.statement, state.getFileTable(),
                state.getHeap(), state.getBarrierTable());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        return statement.typecheck(typeEnv.deepCopy());
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    public String toString()
    {
        return "fork(" + this.statement.toString() + ")";
    }
}
