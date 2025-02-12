package view.commands;

import exceptions.ADTException;
import exceptions.ControllerException;
import exceptions.ExpressionException;
import exceptions.RepoException;

import java.io.FileNotFoundException;

public abstract class Command {
    private String key;
    private String description;

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    protected Command(String key, String description)
    {
        this.key = key;
        this.description = description;
    }

    public abstract void execute() throws ADTException, RepoException, ExpressionException, FileNotFoundException, ControllerException;
}
