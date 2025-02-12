package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.expressions.NotExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;

import java.io.FileNotFoundException;

public class RepeatUntilStatement implements IStatement{
    private final IStatement stmt1;
    private final IExpression exp2;

    public RepeatUntilStatement(IStatement stmt1, IExpression exp2)
    {
        this.stmt1 = stmt1;
        this.exp2 = exp2;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException, FileNotFoundException {
        IStatement newStatement = new CompStatement(this.stmt1, new WhileStatement(new NotExpression(this.exp2), this.stmt1));
        state.getExeStack().push(newStatement);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        if(! exp2.typecheck(typeEnv).equals(new BoolType()))
            throw new ExpressionException("Expression is not of type bool");
        this.stmt1.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatUntilStatement(stmt1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString()
    {
        return "(repeat(" + this.stmt1 + ") until " + this.exp2 + ")";
    }
}
