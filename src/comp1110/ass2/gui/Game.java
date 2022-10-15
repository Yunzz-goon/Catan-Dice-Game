package comp1110.ass2.gui;

import comp1110.ass2.Main.BuildBuilding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;


public class Game extends Viewer {
    private TextField boardTextField_roll;
    private TextField boardTextField_build;
    public static String board_states = "";
    private static int seed = 6710;
    int[] resource_state = new int[6];
    Text text_resource = new Text();

//    private final Group root = new Group();
//    private static final int WINDOW_WIDTH = 1200;
//    private static final int WINDOW_HEIGHT = 700;
    private final Group controls = new Group();

    // Ore, "
    //        + resource_state[1] + " Grain, "
    //        + resource_state[2] + " Wool, "
    //        + resource_state[3] + " Timber, "
    //        + resource_state[4] + " Bricks, "
    //        + resource_state[5] + " Gold. ");

    public static void rollDiceReal(int n_dice, int[] resource_state) {
        /*
        The order of the resources is (0) Ore, (1) Grain, (2) Wool, (3) Timber, (4) Bricks and (5) Gold.
        For example, the array { 1, 0, 1, 2, 0, 2 } indicates that the player has 1 Ore, 1 Wool, 2 Timber
        and 2 Gold available. Note that the total quantity of resources can vary.
         */

        Random random = new Random(seed);
        for (int i = 0; i < n_dice; i++){
            int resource_index = random.nextInt(6);
            resource_state[resource_index] += 1;
        }
        seed += 1;

    }


    /**
     *
     * @param resource_reroll: Some number in 0-5 Each represents Grain, Wool, Timber,
     *                      Brick, Gold respectively.
     * @param resource_state
     */
    public void rollDiceDisplay(String resource_reroll, int[] resource_state){
        int seed = 0;

        int n_dice;
        if (resource_reroll != null) {
            n_dice = resource_reroll.length();
            int resource_int;
            for (int i = 0; i < n_dice; i++) {
                resource_int = Integer.valueOf(resource_reroll.charAt(i)) - 48;
                resource_state[resource_int] -= 1;
                assert (resource_state[resource_int] < 0) : "Lack one of the selected resource";
            }
        } else{
            n_dice = 6;
        }
        rollDiceReal(n_dice, resource_state);
        text_resource.setText("You' ve got " + resource_state[0] + " Ore, "
                + resource_state[1] + " Grain, "
                + resource_state[2] + " Wool, "
                + resource_state[3] + " Timber, "
                + resource_state[4] + " Bricks, "
                + resource_state[5] + " Gold. ");
        text_resource.setFont(Font.font(15));
    }


    BuildBuilding buildBuild = new BuildBuilding();
    public void buildingChangeDisplay(String structure, int[] resource_state){
        buildBuild.buildBuilding(structure, resource_state);
        if (board_states.equals("")){
            board_states += board_states + structure;

        }else{
            board_states += board_states + "," + structure;
        }
        displayState(board_states);
        text_resource.setText("You' ve got " + resource_state[0] + " Ore, "
                + resource_state[1] + " Grain, "
                + resource_state[2] + " Wool, "
                + resource_state[3] + " Timber, "
                + resource_state[4] + " Bricks, "
                + resource_state[5] + " Gold. ");

    }

    public void tradeAndSwapDisplay(){

    }

    public void scoringBoardDisplay(){

    }



    @Override
    protected void makeControls() {
        Label rollLabel = new Label("Dice:");
        boardTextField_roll = new TextField();
        boardTextField_roll.setPrefWidth(500);
        Button button_roll = new Button("Roll it!");
        button_roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                rollDiceDisplay(boardTextField_roll.getText(), resource_state);
            }
        });
        HBox hb_roll = new HBox();
        hb_roll.getChildren().addAll(rollLabel, boardTextField_roll, button_roll);
        hb_roll.setSpacing(10);
        hb_roll.setLayoutX(500);
        controls.getChildren().add(hb_roll);

        Label buildLabel = new Label("Build:");
        boardTextField_build = new TextField();
        boardTextField_build.setPrefWidth(500);
        Button button_build = new Button("Build it!");
        button_build.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                buildingChangeDisplay(boardTextField_build.getText(), resource_state);
            }
        });
        HBox hb_build = new HBox();
        hb_build.getChildren().addAll(buildLabel, boardTextField_build, button_build);
        hb_build.setSpacing(10);
        hb_build.setLayoutX(500);
        hb_build.setLayoutY(100);
        controls.getChildren().add(hb_build);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Board State Viewer");
        rollDiceReal(6, resource_state);

        text_resource.setText("You' ve got " + resource_state[0] + " Ore, "
                + resource_state[1] + " Grain, "
                + resource_state[2] + " Wool, "
                + resource_state[3] + " Timber, "
                + resource_state[4] + " Bricks, "
                + resource_state[5] + " Gold. ");
        text_resource.setFont(Font.font(15));

        buildBuildingFX();
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(500,500);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
//        gridPane.setAlignment(Pos.CENTER);
        gridPane.setLayoutX(650);
        gridPane.setLayoutY(50);

        gridPane.add(text_resource, 0, 0);


        root.getChildren().add(gridPane);

        root.getChildren().add(controls);
        makeControls();

        Scene scene = new Scene(root,WINDOW_WIDTH,WINDOW_HEIGHT, Color.
                DEEPSKYBLUE);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
