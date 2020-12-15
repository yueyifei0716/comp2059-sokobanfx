package sokobanfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import sokobanfx.business.GameEngine;

public class Main extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage);
        scene = new Scene(loadFXML("/fxml/menu"));
        primaryStage.setTitle(GameEngine.GAME_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The entrance of the whole project.
     *
     * @param args the input arguments
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Gets the primary stage
     *
     * @return the primary stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets the primary stage
     *
     * @param primaryStage the primary stage
     */
    public static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    /**
     * Gets the scene
     *
     * @return the scene
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * Sets the root
     *
     * @param fxml the fxml file
     * @throws IOException if the fxml file is not found
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Returns a FXML loader
     *
     * @param fxml the fxml file
     * @return the parent node
     * @throws IOException if the fxml file is not found
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}
