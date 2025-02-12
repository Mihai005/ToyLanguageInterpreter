package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import javafx.util.Pair;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.value.IntValue;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NewBarrierStatement implements IStatement {
    private final String var;
    private final IExpression exp;

    public NewBarrierStatement(String var, IExpression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, KeyNotFoundException, ADTException {
        if (! this.exp.eval(state.getSymTable(), state.getHeap()).getType().equals(new IntType()))
            throw new ExpressionException("Invalid type");
        int nr = ((IntValue)(this.exp.eval(state.getSymTable(), state.getHeap()))).getValue();
        synchronized (state.getBarrierTable())
        {
            int nextFree = state.getBarrierTable().getFreeAddress();
            state.getBarrierTable().put(nextFree, new Pair<>(nr, new ArrayList<>()));
            if (state.getSymTable().contains(var) && state.getSymTable().getValue(var).getType().equals(new IntType()))
                state.getSymTable().update(var, new IntValue(nextFree));
        }
        return null;
    }


    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        if (typeEnv.getValue(var).equals(new IntType())) {
            if (this.exp.typecheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else
                throw new ExpressionException("Invalid Expression type");
        }
        throw new ExpressionException("Invalid Variable type");
    }

    @Override
    public IStatement deepCopy() {
        return new NewBarrierStatement(var, exp.deepCopy());
    }

    @Override
    public String toString()
    {
        return "newBarrier(" + this.var + ", " + this.exp.toString() + ")";
    }
}
