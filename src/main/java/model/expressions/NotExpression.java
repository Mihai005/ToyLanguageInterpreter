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


public class NotExpression implements IExpression {
    private final IExpression expression;
    public NotExpression(IExpression expression) {
        this.expression = expression;
    }
    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue evalExpr = this.expression.eval(symTbl, heap);
        if (!(evalExpr instanceof BoolValue)) {
            throw new ExpressionException("Expression must be of boolean type.");
        }
        return new BoolValue(!((BoolValue) evalExpr).getValue());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        return new BoolType();
    }

    @Override
    public IExpression deepCopy() {
        return new NotExpression(expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "!" + expression.toString();
    }
}
