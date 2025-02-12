package controller;

import exceptions.*;
import model.adt.*;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.RefValue;
import model.value.StringValue;
import repository.IRepository;


import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Controller implements IController{
    private final IRepository repo;
    private final boolean flag;
    private ExecutorService executor;
    private List<PrgState> prgList;

    public Controller(IRepository r, boolean f)
    {
        this.repo = r;
        this.flag = f;
    }

    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(toList());
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream().filter(PrgState::isNotComplete).collect(toList());
    }

    private void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {

        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepoException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        List<Callable<PrgState>> callList = prgList.stream().map((PrgState p) -> (Callable<PrgState>)(p::oneStep)).toList();

        List<PrgState> newPrgStates = executor.invokeAll(callList).stream().map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e)
                    {
                        throw new RuntimeException(e.getMessage());
                    }
                }
        )
                .filter(Objects::nonNull).toList();

        prgList.addAll(newPrgStates);
        prgList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepoException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        repo.setPrgList(prgList);
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap)
    {
        synchronized (heap) {
            List<Integer> addresses = new ArrayList<>(symTableAddr);
            boolean found = true;
            while (found) {
                found = false;
                List<IValue> referenced = new ArrayList<>();
                for (Integer address : addresses) {
                    IValue value = heap.get(address);
                    if (value != null)
                        referenced.add(value);
                }
                List<Integer> newAddresses = getAddrFromSymTable(referenced);
                for (Integer address : newAddresses)
                    if (!addresses.contains(address)) {
                        addresses.add(address);
                        found = true;
                    }
            }

            Map<Integer, IValue> result = new HashMap<>();
            for (Map.Entry<Integer, IValue> entry : heap.entrySet()) {
                if (addresses.contains(entry.getKey())) {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
            return result;
        }
    }

    private void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = programStates.stream()
                .flatMap(p -> getAddrFromSymTable(p.getSymTable().getMap().values()).stream())
                .collect(Collectors.toList());

        programStates.forEach(p -> {
            Map<Integer, IValue> newHeapContent = safeGarbageCollector(symTableAddresses, p.getHeap().getMap());
            p.getHeap().set(newHeapContent);
        });
    }

    @Override
    public void allStep() throws ControllerException {
        executor = Executors.newFixedThreadPool(2);
        prgList = removeCompletedPrg(repo.getPrgList());
        while (!prgList.isEmpty())
        {
            try {
                conservativeGarbageCollector(prgList);
                oneStepForAllPrg(prgList);
                if (this.flag)
                    prgList.forEach(System.out::println);
            }
            catch(RuntimeException | InterruptedException e)
            {
                throw new ControllerException(e.getMessage());
            }
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdown();
    }

    @Override
    public void addProgram(IStatement statement) throws ExpressionException, KeyNotFoundException {
        statement.typecheck(new MyDictionary<>());
        this.repo.addPrgState(new PrgState(statement));
    }

    public void oneStepGUI() throws ControllerException, InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);
        List<PrgState> programsList = removeCompletedPrg(repo.getPrgList());

        if (programsList.isEmpty()) {
            throw new ControllerException("No programs to execute.");
        }


        programsList.forEach(System.out::println);
        conservativeGarbageCollector(programsList);
        oneStepForAllPrg(programsList);
        programsList.forEach(System.out::println);
    }

    @Override
    public List<PrgState> getProgStates() {
        return this.repo.getPrgList();
    }

    @Override
    public void setProgram(IStatement statement) throws ExpressionException, KeyNotFoundException, RepoException {
        statement.typecheck(new MyDictionary<>());

        this.repo.clear();
        this.repo.addPrgState(new PrgState(
                statement));

        this.repo.logPrgStateExec(this.repo.getPrgList().getFirst());

        if(this.flag){
            this.repo.getPrgList().forEach(System.out::println);
        }
    }
}
