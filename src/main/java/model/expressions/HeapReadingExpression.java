package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapReadingExpression implements IExpression{
    private final IExpression expression;

    public HeapReadingExpression(IExpression e)
    {
        this.expression = e;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue evalexpr = this.expression.eval(symTbl, heap);
        if (evalexpr.getType().equals(new RefType(evalexpr.getType())))
            throw new ExpressionException("Expression isn't RefValue type");
        RefValue r = (RefValue) evalexpr;
        int address = r.getAddr();
        if (!heap.getMap().containsKey(address))
            throw new ExpressionException("Address not a key in Heap table");
        return heap.getValue(address);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException
    {
        IType typ = expression.typecheck(typeEnv);
        if (typ instanceof RefType)
        {
            return ((RefType) typ).getInner();
        }
        else
            throw new ExpressionException("the rH argument is not a Ref Type");
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReadingExpression(expression.deepCopy());
    }

    public String toString()
    {
        return "rH(" + this.expression.toString() + ")";
    }
}
