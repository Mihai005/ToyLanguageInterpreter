package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;

public class IfStatement implements IStatement{
    private final IStatement statementThan;
    private final IStatement statementElse;
    private final IExpression expression;
    public IfStatement(IStatement than, IStatement stmtelse, IExpression exp)
    {
        this.statementThan = than;
        this.statementElse = stmtelse;
        this.expression = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws ExpressionException, ADTException {
        IValue value = this.expression.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new BoolType()))
            throw new StatementException("Expression isn't bool");
        if (((BoolValue) value).getValue())
        {
            state.getExeStack().push(statementThan);
        }
        else
        {
            state.getExeStack().push(statementElse);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        IType typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType()))
        {
            statementThan.typecheck(typeEnv.deepCopy());
            statementElse.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new ExpressionException("The condition of IF has not the type bool");
    }

    @Override
    public IStatement deepCopy()
    {
        return new IfStatement(statementThan, statementElse, expression);
    }
    @Override
    public String toString()
    {
        return "if(" + this.expression + "){" + this.statementThan + "}else{" + this.statementElse + "}";
    }
}
