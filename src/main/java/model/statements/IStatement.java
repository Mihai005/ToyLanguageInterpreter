package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

import java.io.FileNotFoundException;

public interface IStatement {
    PrgState execute(PrgState state) throws ADTException, ExpressionException, FileNotFoundException;
    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException, KeyNotFoundException;
    IStatement deepCopy();
}
