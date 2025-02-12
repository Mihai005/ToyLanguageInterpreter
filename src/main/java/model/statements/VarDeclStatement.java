package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.*;
import model.value.*;

public class VarDeclStatement implements IStatement{
    private final String varName;
    private final IType type;

    public VarDeclStatement(String v, IType t)
    {
        this.varName = v;
        this.type = t;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException
    {
        if(state.getSymTable().contains(varName))
            throw new StatementException("Variable" + this.varName + " already exists.");
        state.getSymTable().insert(varName, defaultValue());
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        typeEnv.insert(varName, type);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(this.varName, this.type);
    }

    public String toString()
    {
        return this.varName + " = " + this.type.toString();
    }

    public IValue defaultValue()
    {
        return this.type.defaultValue();
    }
}
