package secondLab;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable, OpenableWindow {
    private Stage stage;
    @FXML
    StackPane stackPane;
    private final Map<String, OpenableWindow> controllerMap = new HashMap<>();

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        stage.setResizable(false);
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Initializer(stage).initializeWindowControllers(controllerMap);
        Image image = SwingFXUtils.toFXImage(ImageProcessor.getImage(), null);
        stackPane.getChildren().add(new ImageView(image));
    }

    private void show(boolean isResizable, StackTraceElement stackTraceElement) {
        Stage stage = lookupController(stackTraceElement.getMethodName()).getStage();
        stage.setResizable(isResizable);
        stage.show();
    }

    private void show(boolean isResizable) {
        show(isResizable, Thread.currentThread().getStackTrace()[2]);
    }

    private OpenableWindow lookupController() {
        return lookupController(Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    private OpenableWindow lookupController(String path) {
        OpenableWindow controller = controllerMap.get(path + ".fxml");
        return controllerMap.get(path + ".fxml");
    }

}

