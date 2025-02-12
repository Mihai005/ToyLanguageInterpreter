package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.RefType;
import model.value.IValue;
import model.value.RefValue;

import java.io.FileNotFoundException;

public class HeapWritingStatement implements IStatement{
    private final String varName;
    private final IExpression expression;

    public HeapWritingStatement(String varName, IExpression exp)
    {
        this.varName = varName;
        this.expression = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException, FileNotFoundException {
        if (! state.getSymTable().contains(varName))
            throw new ExpressionException("Variable doesn't exist");
        IValue varNameType = state.getSymTable().getValue(varName);
        if (! varNameType.getType().equals(new RefType(varNameType.getType()).getInner()))
            throw new ExpressionException("Variable has wrong type");
        RefValue varNameValue = (RefValue) varNameType;
        if (! state.getHeap().containsKey(varNameValue.getAddr()))
            throw new ExpressionException("Variable doesn't exist on the heap");
        IValue evaluated = this.expression.eval(state.getSymTable(), state.getHeap());
        if (! evaluated.getType().equals(varNameValue.getLocationType()))
            throw new ExpressionException("Variable type mismatch");
        state.getHeap().update(varNameValue.getAddr(), evaluated);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        IType typevar = typeEnv.getValue(varName);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new ExpressionException("HeapWrite: Variable type mismatch");
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWritingStatement(this.varName, this.expression.deepCopy());
    }

    public String toString()
    {
        return "wH(" + this.varName + "," + this.expression.toString() + ")";
    }
}
