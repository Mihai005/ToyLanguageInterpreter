package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.value.IValue;

public class ValueExpression implements IExpression {
    private final IValue value;
    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) {
        return this.value;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException
    {
        return value.getType();
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(this.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}