package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.value.IValue;


public class AssignStatement implements IStatement{
    private final String variableName;
    private final IExpression exp;

    public AssignStatement(String varname, IExpression iexpr) {
        this.variableName = varname;
        this.exp = iexpr;
    }
    @Override
    public PrgState execute(PrgState state) throws ExpressionException, ADTException
    {
        if (!state.getSymTable().contains(this.variableName))
            throw new StatementException("Variable was not found");
        IValue value = state.getSymTable().getValue(this.variableName);
        IValue evalValue = this.exp.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(evalValue.getType()))
            throw new StatementException("Value type mismatch");
        state.getSymTable().insert(this.variableName, evalValue);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        IType typevar = typeEnv.getValue(variableName);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new ExpressionException("Assignment: right hand side and left hand side have different types");
    }

    @Override
    public IStatement deepCopy()
    {
        return new AssignStatement(variableName, exp.deepCopy());
    }
    @Override
    public String toString()
    {
        return this.variableName + "=" + this.exp.toString();
    }
}
