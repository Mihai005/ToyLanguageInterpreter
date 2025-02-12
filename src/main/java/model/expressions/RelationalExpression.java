package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExpression implements IExpression{
    private final IExpression exp1;
    private final IExpression exp2;
    private final RelationalOperator operator;

    public RelationalExpression(IExpression exp1, RelationalOperator operator, IExpression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        var value1 = exp1.eval(symTbl, heap);
        var value2 = exp2.eval(symTbl, heap);
        if (!value1.getType().equals(new IntType()))
            throw new ExpressionException("First expression has wrong type");
        if (!value2.getType().equals(new IntType()))
            throw new ExpressionException("Second expression has wrong type");
        switch (operator)
        {
            case LESS:
                return new BoolValue(((IntValue) value1).getValue() < ((IntValue) value2).getValue());
            case LESS_OR_EQUAL:
                return new BoolValue(((IntValue) value1).getValue() <= ((IntValue) value2).getValue());
            case EQUAL:
                return new BoolValue(((IntValue) value1).getValue() == ((IntValue) value2).getValue());
            case NOT_EQUAL:
                return new BoolValue(((IntValue) value1).getValue() != ((IntValue) value2).getValue());
            case GREATER:
                return new BoolValue(((IntValue) value1).getValue() > ((IntValue) value2).getValue());
            case GREATER_OR_EQUAL:
                return new BoolValue(((IntValue) value1).getValue() >= ((IntValue) value2).getValue());
            default:
                throw new ExpressionException("Unknown operator");
        }
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException
    {
        IType t1, t2;
        t1 = exp1.typecheck(typeEnv);
        t2 = exp2.typecheck(typeEnv);
        if (t1.equals(new IntType()))
        {
            if (t2.equals(new IntType()))
                return new BoolType();
            else
                throw new ExpressionException("Second operand is not an integer");
        }
        else
            throw new ExpressionException("First operand is not an integer");
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(this.exp1.deepCopy(), this.operator, this.exp2.deepCopy());
    }

    @Override
    public String toString()
    {
        if (operator.equals(RelationalOperator.LESS))
            return this.exp1.toString() + "<" + this.exp2.toString();
        else if (operator.equals(RelationalOperator.LESS_OR_EQUAL))
            return this.exp1.toString() + "<=" + this.exp2.toString();
        else if (operator.equals(RelationalOperator.EQUAL))
            return this.exp1.toString() + "==" + this.exp2.toString();
        else if (operator.equals(RelationalOperator.NOT_EQUAL))
            return this.exp1.toString() + "!=" + this.exp2.toString();
        else if (operator.equals(RelationalOperator.GREATER))
            return this.exp1.toString() + ">" + this.exp2.toString();
        else
            return this.exp1.toString() + ">=" + this.exp2.toString();

    }
}
