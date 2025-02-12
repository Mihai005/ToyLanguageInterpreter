package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.IntType;
import model.value.IValue;
import model.value.IntValue;

public class ArithmeticalExpression implements IExpression{
    private final IExpression left;
    private final IExpression right;
    private final ArithmeticalOperator operator;

    public ArithmeticalExpression(IExpression l, ArithmeticalOperator o, IExpression r)
    {
        this.left = l;
        this.right = r;
        this.operator = o;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue evaluatedExpressionLeft = this.left.eval(symTbl, heap);
        IValue evaluatedExpressionRight = this.right.eval(symTbl, heap);
        if (!evaluatedExpressionLeft.getType().equals(new IntType()))
            throw new ExpressionException("Left expression isn't Int Type");
        if (!evaluatedExpressionRight.getType().equals(new IntType()))
            throw new ExpressionException("Right expression isn't Int type");
        IntValue intLeft = (IntValue) evaluatedExpressionLeft;
        IntValue intRight = (IntValue) evaluatedExpressionRight;
        switch (operator)
        {
            case ADD:
                return new IntValue(intLeft.getValue() + intRight.getValue());
            case SUBTRACT:
                return new IntValue(intLeft.getValue() - intRight.getValue());
            case MULTIPLY:
                return new IntValue(intLeft.getValue() * intRight.getValue());
            case DIVIDE:
            {
                if (intRight.getValue() == 0)
                    throw new ExpressionException("Cannot divide by 0");
                return new IntValue(intLeft.getValue() / intRight.getValue());
            }
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
        if (t1.equals(new IntType()))
        {
            if (t2.equals(new IntType()))
                return new IntType();
            else
                throw new ExpressionException("Second operand is not an integer");
        }
        else
            throw new ExpressionException("First operand is not an integer");
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticalExpression(left.deepCopy(), operator, right.deepCopy());
    }

    @Override
    public String toString() {
        if (this.operator.equals(ArithmeticalOperator.ADD))
            return this.left + "+" + this.right;
        else if (this.operator.equals(ArithmeticalOperator.SUBTRACT))
            return this.left + "-" + this.right;
        else if (this.operator.equals(ArithmeticalOperator.MULTIPLY))
            return this.left + "*" + this.right;
        else
            return this.left + "/" + this.right;
    }
}
