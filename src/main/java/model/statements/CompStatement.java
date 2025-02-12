package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

public class CompStatement implements IStatement{
    private final IStatement statement1;
    private final IStatement statement2;
    public CompStatement(IStatement one, IStatement two)
    {
        this.statement1 = one;
        this.statement2 = two;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        state.getExeStack().push(statement2);
        state.getExeStack().push(statement1);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        return statement2.typecheck(statement1.typecheck(typeEnv));
    }

    @Override
    public IStatement deepCopy()
    {
        return new CompStatement(statement1, statement2);
    }
    @Override
    public String toString()
    {
        return this.statement1.toString() + ";" + this.statement2.toString();
    }
}
