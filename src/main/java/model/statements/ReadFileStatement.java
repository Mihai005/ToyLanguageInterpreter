package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;


public class ReadFileStatement implements IStatement{
    private final IExpression exp;
    private final String varName;
    public ReadFileStatement(IExpression e, String v)
    {
        this.exp = e;
        this.varName = v;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException {
        var table = state.getSymTable();
        if (!table.contains(varName))
        {
            throw new StatementException("The variable was not defined");
        }
        if (!table.getValue(varName).getType().equals(new IntType()))
        {
            throw new StatementException("The type is incorrect");
        }
        var res = exp.eval(table, state.getHeap());
        if (!res.getType().equals(new StringType()))
        {
            throw new StatementException("The result is not String Type!");
        }
        BufferedReader reader = state.getFileTable().getValue((StringValue)res);
        String read;
        try {
            read = reader.readLine();
            if (read.isEmpty())
                read = "0";
            int parser = Integer.parseInt(read);
            table.insert(varName, new IntValue(parser));
        }
        catch (IOException e)
        {
            throw new StatementException("Error reading file");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        IType typevar = typeEnv.getValue(varName);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new IntType())){
            if (typexp.equals(new StringType()))
                return typeEnv;
            else
                throw new ExpressionException("ReadFile: Expression is not a string");
        }
        else
            throw new ExpressionException("ReadFile: Variable is not an integer");
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(this.exp.deepCopy(), varName);
    }

    @Override
    public String toString()
    {
        return "readFile(" + this.exp.toString() + "," + this.varName + ")";
    }
}
