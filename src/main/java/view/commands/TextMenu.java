package view.commands;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.RepoException;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu()
    {
        this.commands = new HashMap<>();

    }
    public void addCommand(Command c)
    {
        this.commands.put(c.getKey(), c);
    }

    private void printMenu()
    {
        for (Command c : this.commands.values())
        {
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show() throws ADTException, RepoException, ExpressionException, FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            printMenu();
            System.out.println("Option: ");
            String line = sc.nextLine();
            Command c = this.commands.get(line);
            if (c == null)
            {
                System.out.println("Invalid option!");
                continue;
            }
            try {
                c.execute();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
