package main;

import controller.Controller;
import controller.IController;
import repository.IRepository;
import repository.Repository;
import com.example.toylanguageinterpretergui.MainView;

public class Main {
    public static void main(String[] args){
        IRepository repository = new Repository("logGUI.txt");
        IController controller = new Controller(repository, true);
        MainView.setController(controller);
        MainView view = new MainView();
        view.run(args);

    }
}