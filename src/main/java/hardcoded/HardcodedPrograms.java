package hardcoded;

import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.util.ArrayList;
import java.util.List;

public class HardcodedPrograms {
    public static final List<IStatement> hardcodedPrograms = new ArrayList<IStatement>(List.of(
            new CompStatement(new VarDeclStatement("v", new IntType()),
                    new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                            new PrintStatement(new VariableExpression("v")))), new CompStatement(new VarDeclStatement("a", new IntType()),
                    new CompStatement(new VarDeclStatement("b", new IntType()),
                            new CompStatement(new AssignStatement("a", new ArithmeticalExpression(new ValueExpression(new IntValue(2)), ArithmeticalOperator.ADD, new
                                    ArithmeticalExpression(new ValueExpression(new IntValue(3)), ArithmeticalOperator.MULTIPLY, new ValueExpression(new IntValue(5))))),
                                    new CompStatement(new AssignStatement("b", new ArithmeticalExpression(new VariableExpression("a"), ArithmeticalOperator.ADD, new ValueExpression(new
                                            IntValue(1)))), new PrintStatement(new VariableExpression("b")))))), new CompStatement(new VarDeclStatement("a", new BoolType()),
                    new CompStatement(new VarDeclStatement("v", new IntType()),
                            new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                    new CompStatement(new IfStatement(new AssignStatement("v", new ValueExpression(new
                                            IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3))), new VariableExpression("a")), new PrintStatement(new
                                            VariableExpression("v")))))), new CompStatement(new VarDeclStatement("varf", new StringType()),
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
                                                                                    new VariableExpression("varf")))))))))), new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                    new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                            new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                    new CompStatement(new NewStatement("a", new VariableExpression("v")),
                                            new CompStatement(
                                                    new PrintStatement(new VariableExpression("v")),
                                                    new PrintStatement(new VariableExpression("a"))))))),
            new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                    new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                            new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                    new CompStatement(new NewStatement("a", new VariableExpression("v")),
                                            new CompStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                                    new PrintStatement(new ArithmeticalExpression(new HeapReadingExpression(
                                                            new HeapReadingExpression
                                                                    (new VariableExpression("a"))), ArithmeticalOperator.ADD,
                                                            new ValueExpression(new IntValue(5))))))))),
            new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                    new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(
                            new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new CompStatement(
                            new HeapWritingStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(
                            new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v")),
                                    ArithmeticalOperator.ADD, new ValueExpression(new IntValue(5)))))))),
            new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                    new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                            new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                    new CompStatement(new NewStatement("a", new VariableExpression("v")),
                                            new CompStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                    new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))))))))),
            new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v",
                    new ValueExpression(new IntValue(4))), new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"),
                    RelationalOperator.GREATER, new ValueExpression(new IntValue(0))), new CompStatement(new PrintStatement(new VariableExpression("v")),
                    new AssignStatement("v", new ArithmeticalExpression(new VariableExpression("v"), ArithmeticalOperator.SUBTRACT,
                            new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v"))))),
            new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new VarDeclStatement(
                    "a", new RefType(new IntType())), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                    new CompStatement(new NewStatement("a", new ValueExpression(new IntValue(22))), new CompStatement(new ForkStatement(
                            new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))), new CompStatement(
                                    new AssignStatement("v", new ValueExpression(new IntValue(32))), new CompStatement(new PrintStatement(
                                    new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))
                            ))
                    ), new CompStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))))))
            )),
            new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new VarDeclStatement("x", new IntType()),
                    new CompStatement(new VarDeclStatement("y", new IntType()), new CompStatement(new AssignStatement("v",
                            new ValueExpression(new IntValue(0))), new CompStatement(new RepeatUntilStatement(new CompStatement(new ForkStatement(new CompStatement(
                                    new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticalExpression(new VariableExpression("v"), ArithmeticalOperator.SUBTRACT,
                            new ValueExpression(new IntValue(1)))))), new AssignStatement("v", new ArithmeticalExpression(new VariableExpression("v"), ArithmeticalOperator.ADD, new ValueExpression(new IntValue(1)))))
                    , new RelationalExpression(new VariableExpression("v"), RelationalOperator.EQUAL, new ValueExpression(new IntValue(3)))),
                            new CompStatement(new AssignStatement("x", new ValueExpression(new IntValue(1))), new CompStatement(new NopStatement(),
                                    new CompStatement(new AssignStatement("y", new ValueExpression(new IntValue(3))), new CompStatement(
                                            new NopStatement(), new PrintStatement(new ArithmeticalExpression(new VariableExpression("v"), ArithmeticalOperator.MULTIPLY,
                                            new ValueExpression(new IntValue(10))))
                                    ))))))))),
            new CompStatement(new VarDeclStatement("v1", new RefType(new IntType())), new CompStatement(new VarDeclStatement(
                    "v2", new RefType(new IntType())
            ), new CompStatement(new VarDeclStatement("v3", new RefType(new IntType())), new CompStatement(
                    new VarDeclStatement("cnt", new IntType()), new CompStatement(new NewStatement("v1", new ValueExpression(
                            new IntValue(2)
            )), new CompStatement(new NewStatement("v2", new ValueExpression(new IntValue(3))), new CompStatement(new NewStatement(
                    "v3", new ValueExpression(new IntValue(4))
            ), new CompStatement(new NewBarrierStatement("cnt", new HeapReadingExpression(new VariableExpression("v2"))),
                    new CompStatement(new ForkStatement(new CompStatement(new AwaitStatement("cnt"), new CompStatement(
                            new HeapWritingStatement("v1", new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v1")),
                                    ArithmeticalOperator.MULTIPLY, new ValueExpression(new IntValue(10)))
                    ), new PrintStatement(new HeapReadingExpression(new VariableExpression("v1")))))), new CompStatement(new ForkStatement(new CompStatement(new AwaitStatement("cnt"), new CompStatement(new HeapWritingStatement(
                            "v2", new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v2")), ArithmeticalOperator.MULTIPLY,
                            new ValueExpression(new IntValue(10)))
                    ), new CompStatement(new HeapWritingStatement("v2", new ArithmeticalExpression(new HeapReadingExpression(new VariableExpression("v2")), ArithmeticalOperator.MULTIPLY,
                            new ValueExpression(new IntValue(10)))),new PrintStatement(new HeapReadingExpression(new VariableExpression("v2"))))))), new CompStatement(
                                    new AwaitStatement("cnt"), new PrintStatement(new HeapReadingExpression(new VariableExpression("v3")))
                    )))))))
            ))))));


}