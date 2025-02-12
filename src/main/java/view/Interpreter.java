package view;

import controller.Controller;
import controller.IController;
import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.RepoException;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;
import view.commands.TextMenu;

import java.io.FileNotFoundException;

public class Interpreter {
    public static void main(String[] args) throws ADTException, RepoException, ExpressionException, FileNotFoundException {
        IStatement example1 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        IRepository repo1 = new Repository("log1.txt");
        IController controller1 = new Controller(repo1, true);
        controller1.addProgram(example1);


        IStatement example2 = new CompStatement(new VarDeclStatement("a",new IntType()),
                new CompStatement(new VarDeclStatement("b",new IntType()),
                        new CompStatement(new AssignStatement("a", new ArithmeticalExpression(new ValueExpression(new IntValue(2)), ArithmeticalOperator.ADD,new
                                ArithmeticalExpression(new ValueExpression(new IntValue(3)),ArithmeticalOperator.MULTIPLY,new ValueExpression(new IntValue(5))))),
                                new CompStatement(new AssignStatement("b",new ArithmeticalExpression(new VariableExpression("a"), ArithmeticalOperator.ADD,new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        IRepository repo2 = new Repository("log2.txt");
        IController controller2 = new Controller(repo2, true);
        controller2.addProgram(example2);


        IStatement example3 = new CompStatement(new VarDeclStatement("a",new BoolType()),
                new CompStatement(new VarDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3))), new VariableExpression("a")), new PrintStatement(new
                                        VariableExpression("v"))))));
        IRepository repo3 = new Repository("log3.txt");
        IController controller3 = new Controller(repo3, true);
        controller3.addProgram(example3);


        IStatement example4 = new CompStatement(new VarDeclStatement("varf", new StringType()),
                new CompStatement(new AssignStatement("varf", new ValueExpression(
                        new StringValue("test.in"))),
                        new CompStatement(new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompStatement(new VarDeclStatement("varc", new IntType()),
                                        new CompStatement(new ReadFileStatement(
                                                new VariableExpression("varf"), "varc"),
                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(new ReadFileStatement(
                                                                new VariableExpression("varf"), "varc"),
                                                                new CompStatement(
                                                                        new PrintStatement(
                                                                                new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(
                                                                                new VariableExpression("varf"))))))))));


        IRepository repo4 = new Repository("test.out");
        Controller controller4 = new Controller(repo4,true);
        controller4.addProgram(example4);

        IStatement example5 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                        new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));

        IRepository repo5 = new Repository("log4.txt");
        IController controller5 = new Controller(repo5, true);
        controller5.addProgram(example5);

        IStatement example6 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticalExpression(new HeapReadingExpression(
                                                        new HeapReadingExpression
                                                (new VariableExpression("a"))), ArithmeticalOperator.ADD,
                                                        new ValueExpression(new IntValue(5)))))))));


        IRepository repo6 = new Repository("log5.txt");
        IController controller6 = new Controller(repo6, true);
        controller6.addProgram(example6);

        IStatement example7 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(
                new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new CompStatement(
                                new HeapWritingStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(
                                        new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v")),
                                             ArithmeticalOperator.ADD, new ValueExpression(new IntValue(5))))))));

        IRepository repository7 = new Repository("log6.txt");
        IController controller7 = new Controller(repository7, true);
        controller7.addProgram(example7);

        IStatement example8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))))))));

        IRepository repository8 = new Repository("log7.txt");
        IController controller8 = new Controller(repository8, true);
        controller8.addProgram(example8);

        IStatement example9 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v",
                new ValueExpression(new IntValue(4))), new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"),
                RelationalOperator.GREATER, new ValueExpression(new IntValue(0))), new CompStatement(new PrintStatement(new VariableExpression("v")),
                 new AssignStatement("v", new ArithmeticalExpression(new VariableExpression("v"), ArithmeticalOperator.SUBTRACT,
                         new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v")))));

        IRepository repository9 = new Repository("log8.txt");
        IController controller9 = new Controller(repository9, true);
        controller9.addProgram(example9);

        IStatement example10 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new VarDeclStatement(
                "a", new RefType(new IntType())), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                new CompStatement(new NewStatement("a", new ValueExpression(new IntValue(22))), new CompStatement(new ForkStatement(
                        new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))), new CompStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(32))), new CompStatement(new PrintStatement(
                                        new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))
                        ))
                ), new CompStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))))))
        ));

        IRepository repository10 = new Repository("log9.txt");
        IController controller10 = new Controller(repository10, true);
        controller10.addProgram(example10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", example1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", example2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", example3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", example4.toString(), controller4));
        menu.addCommand(new RunExampleCommand("5", example5.toString(), controller5));
        menu.addCommand(new RunExampleCommand("6", example6.toString(), controller6));
        menu.addCommand(new RunExampleCommand("7", example7.toString(), controller7));
        menu.addCommand(new RunExampleCommand("8", example8.toString(), controller8));
        menu.addCommand(new RunExampleCommand("9", example9.toString(), controller9));
        menu.addCommand(new RunExampleCommand("10", example10.toString(), controller10));
        menu.show();

    }
}
