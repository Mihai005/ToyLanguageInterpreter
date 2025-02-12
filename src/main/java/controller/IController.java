package controller;

import exceptions.*;
import model.state.PrgState;
import model.statements.IStatement;

import java.util.List;
import java.util.Map;

public interface IController {
    void addProgram(IStatement statement) throws ExpressionException, KeyNotFoundException;
    void allStep() throws ControllerException;
    List<PrgState> getProgStates();
    void oneStepGUI() throws ControllerException, InterruptedException;
    void setProgram(IStatement statement) throws ExpressionException, KeyNotFoundException, RepoException;
}
