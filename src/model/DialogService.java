package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.controller.*;

import java.io.IOException;
import java.util.Optional;

/**
 * DialogService manages views creation
 *
 * @author vareversat lturpinat
 */
public class DialogService {

    private static final String SETUP_VIEW = "/view/fxml/SetUpView.fxml";
    private static final String FIRST_VIEW = "/view/fxml/FirstView.fxml";
    private static final String LEADER_BOARD_VIEW = "/view/fxml/LeaderBoardView.fxml";
    private static final String MAIN_VIEW = "/view/fxml/MainView.fxml";
    private static final String POPUP_VIEW = "/view/fxml/PopUpView.fxml";
    private static final String WINNING_VIEW = "/view/fxml/WinningView.fxml";

    private static final String SETUP_VIEW_TITLE = "Set up your game";
    private static final String FIRST_VIEW_TITLE = "Welcome";
    private static final String LEADER_BOARD_VIEW_TITLE = "Leader board";
    private static final String MAIN_VIEW_TITLE = "Let's go !";
    private static final String POPUP_VIEW_TITLE = "Warning";
    private static final String WINNING_VIEW_TITLE = "You won !!";

    private DialogService() {}

    public static void openFirstView(Manager manager){
        loadWindow(FIRST_VIEW_TITLE, FIRST_VIEW, Optional.of(new FirstViewController(manager))).show();
    }

    public static void openLeaderBoardView(Manager manager){
        loadWindow(LEADER_BOARD_VIEW_TITLE, LEADER_BOARD_VIEW, Optional.of(new LeaderBoardViewController(manager))).show();
    }

    public static void openMainView(Manager manager, Board board){
        Stage stage = loadWindow(MAIN_VIEW_TITLE, MAIN_VIEW, Optional.of(new MainViewController(manager, board)));
        stage.setMaximized(true);
        stage.show();
    }

    public static void openSetUpView(Manager manager){
        loadWindow(SETUP_VIEW_TITLE, SETUP_VIEW, Optional.of(new SetUpViewController(manager))).show();
    }

    public static void openPopUpView(String msg){
        loadWindow(POPUP_VIEW_TITLE,POPUP_VIEW, Optional.of(new PopUpViewController(msg))).show();
    }


    public static void openWinView(Manager manager, Board board){
        loadWindow(WINNING_VIEW_TITLE, WINNING_VIEW, Optional.of(new WinningViewController(manager, board))).show();
    }

    /**
     * Enable to load a window with or without a controller
     * @param windowTitle window title
     * @param windowPath FXML view path
     * @param controller optional controller to link to the view
     * @return stage of the view
     */
    private static Stage loadWindow(String windowTitle, String windowPath, Optional<Controller> controller){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = new Stage();

            fxmlLoader.setLocation(DialogService.class.getResource(windowPath));
            controller.ifPresent(fxmlLoader::setController); //Link the controller only if it has been specified
            Parent parent = fxmlLoader.load();

            Scene sceneSetUpView = new Scene(parent);
            stage.setTitle(windowTitle);
            stage.setScene(sceneSetUpView);
            stage.getIcons().add(new Image("/resources/icon.png"));

            return stage;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
