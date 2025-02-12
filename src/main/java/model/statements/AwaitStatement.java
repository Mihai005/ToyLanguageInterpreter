package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import javafx.util.Pair;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.value.IntValue;

import java.io.FileNotFoundException;
import java.util.List;

public class AwaitStatement implements IStatement{
    private final String var;

    public AwaitStatement(String var)
    {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException, FileNotFoundException {
        if (! state.getSymTable().contains(var))
            throw new KeyNotFoundException("Variable is not in SymTable");
        if (! state.getSymTable().getValue(var).getType().equals(new IntType()))
            throw new ExpressionException("Value is not of int type");
        int foundIndex = ((IntValue)state.getSymTable().getValue(var)).getValue();
        if (! state.getBarrierTable().containsKey(foundIndex))
            throw new KeyNotFoundException("Variable is not in Barrier Table");
        synchronized (state.getSymTable()) {
            Pair<Integer, List<Integer>> entry = state.getBarrierTable().getValue(foundIndex);
            int n1 = state.getPrgById();
            int n2 = entry.getValue().size();
            if (n1 > n2) {
                if (entry.getValue().contains(state.getPrgById()))
                    state.getExeStack().push(this);
                else {
                    entry.getValue().add(state.getPrgById());
                    state.getExeStack().push(this);
                }
            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        if (typeEnv.getValue(var).equals(new IntType()))
            return typeEnv;
        throw new ExpressionException("Variable is not int");
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitStatement(var);
    }

    @Override
    public String toString()
    {
        return "await(" + var + ")";
    }
}
