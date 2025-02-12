package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;

public class LogicalExpression implements IExpression{
    private final IExpression left;
    private final IExpression right;
    private final LogicalOperator operator;
    public LogicalExpression(IExpression l, LogicalOperator op, IExpression r)
    {
        this.left = l;
        this.right = r;
        this.operator = op;
    }
    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue evaluatedExpressionLeft = this.left.eval(symTbl, heap);
        IValue evaluatedExpressionRight= this.right.eval(symTbl, heap);
        if (!evaluatedExpressionLeft.getType().equals(new BoolType()))
            throw new ExpressionException("Left expression is not of type BoolType");
        if (!evaluatedExpressionRight.getType().equals(new BoolType()))
            throw new ExpressionException("Right expression is not of type BoolType");
        switch (operator)
        {
            case AND:
                return new BoolValue(((BoolValue)evaluatedExpressionLeft).getValue() &&
                        ((BoolValue)evaluatedExpressionRight).getValue());
            case OR:
                return new BoolValue(((BoolValue)evaluatedExpressionLeft).getValue() ||
                        ((BoolValue)evaluatedExpressionRight).getValue());
            default:
                throw new ExpressionException("Unknown operator");
        }
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException
    {
        IType t1, t2;
        t1 = left.typecheck(typeEnv);
        t2 = right.typecheck(typeEnv);
        if (t1.equals(new BoolType())) {
            if (t2.equals(new BoolType()))
                return new BoolType();
            else
                throw new ExpressionException("Second operand is not a boolean");
        }
        throw new ExpressionException("First operand is not a boolean");
    }

    @Override
    public IExpression deepCopy()
    {
        return new LogicalExpression(left.deepCopy(), operator, right.deepCopy());
    }
    @Override
    public String toString()
    {
        return this.left.toString() + " " + this.operator + " " + this.right.toString();
    }
}
