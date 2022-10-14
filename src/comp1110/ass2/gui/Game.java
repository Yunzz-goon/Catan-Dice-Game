package comp1110.ass2.gui;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Viewer {

//    private final Group root = new Group();
//    private static final int WINDOW_WIDTH = 1200;
//    private static final int WINDOW_HEIGHT = 700;
//    private final Group controls = new Group();





    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Board State Viewer");

        buildBuildingFX();

        Scene scene = new Scene(root,WINDOW_WIDTH,WINDOW_HEIGHT, Color.
                DEEPSKYBLUE);

        root.getChildren().add(controls);
        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
