package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public class NopStatement implements IStatement{
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public String toString()
    {
        return "nop";
    }
}
