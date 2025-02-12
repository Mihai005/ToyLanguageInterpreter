package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;

import java.io.FileNotFoundException;

public class WhileStatement implements IStatement{
    private final IExpression expression;
    private final IStatement statement;

    public WhileStatement(IExpression e, IStatement s)
    {
        this.expression = e;
        this.statement = s;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException, FileNotFoundException {
        IValue evaluated = this.expression.eval(state.getSymTable(), state.getHeap());
        if (! evaluated.getType().equals(new BoolType()))
            throw new ExpressionException("Wrong type");
        BoolValue evaluatedValue = (BoolValue) evaluated;
        if (evaluatedValue.getValue())
        {
            state.getExeStack().push(this);
            state.getExeStack().push(this.statement);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        IType typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType()))
        {
            return statement.typecheck(typeEnv);
        }
        else
            throw new ExpressionException("While condition is not of type bool");
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    public String toString()
    {
        return "(" + "while" + "(" + this.expression.toString() + ") " + this.statement.toString() + ")";
    }
}
