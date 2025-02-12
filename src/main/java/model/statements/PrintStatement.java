package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.value.IValue;

public class PrintStatement implements IStatement{
    private final IExpression exp;
    public PrintStatement(IExpression expression)
    {
        this.exp = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException {
        IValue result = this.exp.eval(state.getSymTable(), state.getHeap());
        state.getOutput().add(result.toString());
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy()
    {
        return new PrintStatement(this.exp.deepCopy());
    }
    @Override
    public String toString()
    {
        return "print("+this.exp.toString()+")";
    }
}
