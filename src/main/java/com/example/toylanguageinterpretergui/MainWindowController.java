package com.example.toylanguageinterpretergui;

import controller.IController;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.value.StringValue;

import java.io.BufferedReader;
import java.util.List;
import java.util.NoSuchElementException;

public class MainWindowController {
    IController controller;
    MyIHeap heap;
    private MyIList<String> output;
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    public MainWindowController(IController controller) {
        this.controller = controller;
    }

    @FXML
    private Label programStatesLabel;

    @FXML
    private ListView<Integer> programStatesListView;

    @FXML
    private ListView<IStatement> executionStackListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<String> outListView;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTableTableView;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> heapAddressesColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> heapValuesColumn;

    @FXML
    private TableView<Pair<String, IValue>> symbolTableTableView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symbolNameColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symbolValueColumn;

    @FXML
    private Button runButton;

    @FXML
    private Button oneStepButton;

    public void refresh() {
        Integer selectedProgramId = this.programStatesListView.getSelectionModel().getSelectedItem();
        this.programStatesListView.getItems().clear();
        this.heapTableTableView.getItems().clear();
        this.outListView.getItems().clear();
        this.fileTableListView.getItems().clear();
        this.symbolTableTableView.getItems().clear();
        this.executionStackListView.getItems().clear();

        this.programStatesLabel.setText("Program states: " + this.controller.getProgStates().size());
        this.controller.getProgStates().forEach(progState -> this.programStatesListView.getItems().add(progState.getPrgById()));

        if (!this.controller.getProgStates().isEmpty()) {
            this.heap = this.controller.getProgStates().getFirst().getHeap();
            this.output = this.controller.getProgStates().getFirst().getOutput();
            this.fileTable = this.controller.getProgStates().getFirst().getFileTable();

        }

        if (this.heap != null) {
            this.heap.getMap().forEach((key, value) ->
                    this.heapTableTableView.getItems().add(new Pair<>(key, value)));
        }

        if (this.output != null) {
            this.output.toList().forEach(output
                    -> this.outListView.getItems().add(output));
        }

        if (this.fileTable != null) {
            this.fileTable.getKeys().forEach(key
                    -> this.fileTableListView.getItems().add(key.toString()));
        }

        PrgState current;
        try{
            current = this.controller.getProgStates().stream().filter(x -> Integer.valueOf(x.getPrgById()).equals(selectedProgramId)).findAny().get();
            current.getSymTable().getMap().forEach((x, y) -> this.symbolTableTableView.getItems().add(new Pair<>(x,y)));
            List<IStatement> statementList = current.getExeStack().toList();
            for(int i = statementList.size() - 1;i >= 0;i--){
                this.executionStackListView.getItems().add(statementList.get(i));
            }
            this.programStatesListView.getSelectionModel().select(selectedProgramId);
        } catch (NoSuchElementException e) {
            return ;
        } finally {
            this.programStatesListView.refresh();
            this.heapTableTableView.refresh();
            this.outListView.refresh();
            this.fileTableListView.refresh();
            this.symbolTableTableView.refresh();
            this.executionStackListView.refresh();
        }
    }


    @FXML
    public void initialize() {
        this.heapAddressesColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.heapValuesColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().toString()));
        this.symbolNameColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        this.symbolValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().toString()));
        this.refresh();

        this.oneStepButton.setOnAction(actionEvent -> {
            try {
                this.controller.oneStepGUI();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
            this.refresh();
        });

        this.runButton.setOnAction(actionEvent -> {
            try {
                this.controller.allStep();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
            this.refresh();
        });

        this.programStatesListView.setOnMouseClicked(x -> this.refresh());
    }
}
