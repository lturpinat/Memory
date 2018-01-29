package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.DialogService;
import model.Manager;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Program extends Application {

    private final static Logger LOGGER = Logger.getLogger(Program.class.getName());

    @Override
    public void start(Stage stage){
        LOGGER.log(Level.INFO, "Welcome to Memory v.1.0 !");

        Manager manager = new Manager();
        manager.loadPlayers();
        DialogService.openFirstView(manager);
    }

}
