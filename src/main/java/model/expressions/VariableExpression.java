package model.expressions;

import exceptions.ADTException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.value.IValue;

public class VariableExpression implements IExpression{
    private final String variable;
    public VariableExpression(String s)
    {
        this.variable = s;
    }
    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException {
        return symTbl.getValue(this.variable);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws KeyNotFoundException {
        return typeEnv.getValue(variable);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(this.variable);
    }

    @Override
    public String toString()
    {
        return this.variable;
    }
}
