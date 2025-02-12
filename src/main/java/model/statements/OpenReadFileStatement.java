package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements IStatement{
    private final IExpression exp;

    public OpenReadFileStatement(IExpression e)
    {
        this.exp = e;
    }
    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException, FileNotFoundException {
        var table = state.getSymTable();
        IValue result = exp.eval(table, state.getHeap());
        if (!result.getType().equals(new StringType()))
            throw new StatementException("The type is incorrect");
        var filetable = state.getFileTable();
        if (filetable.contains((StringValue)result))
            throw new StatementException("The file is already open");
        try{
            BufferedReader read = new BufferedReader(new FileReader(((StringValue) (result)).getValue()));
            filetable.insert((StringValue)result, read);
            return null;
        }
        catch(FileNotFoundException f)
        {
            throw new FileNotFoundException(f.toString());
        }
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new ExpressionException("OpenReadFile: Expression is not a string");
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFileStatement(this.exp.deepCopy());
    }

    @Override
    public String toString()
    {
        return "openRFile(" + this.exp.toString() + ")";
    }
}
