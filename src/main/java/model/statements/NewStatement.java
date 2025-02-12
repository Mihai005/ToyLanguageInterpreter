package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.RefType;
import model.value.IValue;
import model.value.RefValue;

import java.io.FileNotFoundException;

public class NewStatement implements IStatement{
    private final String variableName;
    private final IExpression expression;

    public NewStatement(String v, IExpression e)
    {
        this.variableName = v;
        this.expression = e;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException, FileNotFoundException {
        if (!state.getSymTable().contains(variableName))
            throw new StatementException("Variable was not found");
        if (!(state.getSymTable().getValue(variableName).getType() instanceof RefType))
            throw new StatementException("Variable has wrong type");
        IValue expressionValue = expression.eval(state.getSymTable(), state.getHeap());
        RefValue newVariable = (RefValue) state.getSymTable().getValue(variableName);
        RefType newVariableType = (RefType) newVariable.getType();
        if (!expressionValue.getType().equals(newVariableType.getInner()))
            throw new StatementException("Variable type mismatch");
        int address = state.getHeap().allocate(expressionValue);
        state.getSymTable().insert(variableName, new RefValue(address, newVariableType.getInner()));
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        IType typevar = typeEnv.getValue(variableName);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new ExpressionException("NEW stmt: right hand side and left hand side have different types");
    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(variableName, expression.deepCopy());
    }

    public String toString()
    {
        return "new(" + this.variableName + "," + this.expression.toString() + ")";
    }
}
