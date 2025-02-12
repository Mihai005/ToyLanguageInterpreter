package view.commands;

import controller.IController;
import exceptions.ADTException;
import exceptions.ControllerException;
import exceptions.ExpressionException;
import exceptions.RepoException;

import java.io.FileNotFoundException;

public class RunExampleCommand extends Command {
    private final IController controller;

    public RunExampleCommand(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws ADTException, RepoException, ExpressionException, FileNotFoundException, ControllerException {
        this.controller.allStep();
    }
}
