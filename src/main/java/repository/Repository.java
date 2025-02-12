package repository;

import exceptions.RepoException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> prgStateList;
    private final String filename;
    private int currentPosition;
    private List<InvalidationListener> listeners ;

    public Repository(String f)
    {
        this.filename = f;
        this.prgStateList = new ArrayList<PrgState>();
        this.currentPosition = 0;
        this.listeners = new ArrayList<>();
    }

    @Override
    public List<PrgState> getStates() {
        return this.prgStateList;
    }

    @Override
    public PrgState getState(int index) {
        if(index > 0)
            return prgStateList.get(index);
        return prgStateList.getFirst();
    }

    @Override
    public void addPrgState(PrgState p) {
        this.prgStateList.add(p);
    }

    @Override
    public void remove(PrgState p)
    {
        this.prgStateList.remove(p);
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.prgStateList;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.prgStateList = prgList;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws RepoException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.filename, true)));
            logFile.println(prgStateList.toString());
            logFile.close();
        }
        catch (IOException e) {
            throw new RepoException("File doesn't exist");
        }
    }
    @Override
    public void clear()
    {
        this.prgStateList.clear();
    }

    @Override
    public void addListener(InvalidationListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void notifyListeners()
    {
        for (InvalidationListener listener : listeners)
            listener.invalidated((Observable) this);
    }
}
