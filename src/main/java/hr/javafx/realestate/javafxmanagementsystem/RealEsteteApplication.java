package hr.javafx.realestate.javafxmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealEsteteApplication extends Application {
    private static Stage mainStage;
    public static final Logger logger = LoggerFactory.getLogger(RealEsteteApplication.class);


    @Override
    public void start(Stage stage) throws IOException {

        mainStage = stage; // NOSONAR

        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("firstScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 924, 611);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getMainStage() {
        return mainStage;
    }
}