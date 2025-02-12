package repository;

import exceptions.RepoException;
import javafx.beans.InvalidationListener;
import model.state.PrgState;

import java.util.List;

public interface IRepository {
    List<PrgState> getStates();
    void addPrgState(PrgState p);
    void logPrgStateExec(PrgState state) throws RepoException;
    void remove(PrgState p);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    void clear();
    void addListener(InvalidationListener listener);
    void notifyListeners();
    PrgState getState(int index);
}
