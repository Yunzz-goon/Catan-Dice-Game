package comp1110.ass2.gui;

import comp1110.ass2.Main.BuildBuilding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;


public class Game extends Viewer {
    double begin_x = 700;
    double begin_y = 50;
    double y_gap = 50;
    double input_width = 300;
    private static int round = 0;
    private static int mark = 0;
    private static int total_mark;
    ArrayList<Text> scoreRecords = new ArrayList<>();
    private TextField boardTextField_roll;
    private TextField boardTextField_build;
    private TextField boardTextField_trade;
    private TextField boardTextField_swap;
    private static int seed = 6710;
    int[] resource_state = new int[6];
    Text text_resource = new Text();

    //    private final Group root = new Group();
//    private static final int WINDOW_WIDTH = 1200;
//    private static final int WINDOW_HEIGHT = 700;
    private final Group controls = new Group();


    class ScoreNode extends Rectangle {
        ScoreNode(double xPos, double yPos){
            this.setLayoutX(xPos);
            this.setLayoutY(yPos);
            this.setWidth(HEXAGON_SIDE_LENGTH/2);
            this.setHeight(HEXAGON_SIDE_LENGTH/2);
            this.setFill(Color.WHITE);
        }
    }
    public static void rollDiceReal(int n_dice, int[] resource_state) {
        /*
        The order of the resources is (0) Ore, (1) Grain, (2) Wool, (3) Lumber, (4) Bricks and (5) Gold.
        For example, the array { 1, 0, 1, 2, 0, 2 } indicates that the player has 1 Ore, 1 Wool, 2 Lumber
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
     * @param resource_reroll: Some number in 0-5 Each represents Grain, Wool, Lumber,
     *                      Brick, Gold respectively.
     * @param resource_state
     */
    public void rollDiceDisplay(String resource_reroll, int[] resource_state){
        int seed = 0;

        int n_dice;
        if (resource_reroll.equals("") == false) {
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
                + resource_state[3] + " Lumber, "
                + resource_state[4] + " Brick, "
                + resource_state[5] + " Gold. ");
        text_resource.setFont(Font.font(15));
    }

    @Override
    void displayState(String structure) {
        char build_type = structure.charAt(0);
        int length = structure.length()-1;
        char[] build_no = new char[length];
        structure.getChars(1, length+1, build_no, 0);
        String build_no_str = new String(build_no);
        Integer build_no_int = Integer.valueOf(build_no_str);

        // road 0-15
        if (build_type == 'R'){
            roads_fx.get(build_no_int).setFill(Color.GREEN);
            // settlement
        } else if (build_type == 'S') {
            for (SettlementFX settlement : settlements_fx){
                if (settlement.mark == build_no_int){
                    settlement.setFill(Color.GREEN);
                    break;
                }
            }
            // city
        } else if (build_type == 'C') {
            for (CityFX city : cities_fx){
                if (city.mark == build_no_int){
                    city.setFill(Color.GREEN);
                    break;
                }
            }
            // Knight J
        } else if (build_type == 'J') {
            knights_fx.get(build_no_int-1).setFill(Color.GREEN);
            // Knight K
        } else if (build_type == 'K') {
            knights_fx.get(build_no_int-1).setFill(Color.RED);
        } else{
            System.out.println("Unexpected type");
        }
    }


    BuildBuilding buildBuild = new BuildBuilding();
    public void buildingChangeDisplay(String structure, int[] resource_state){

        int point = buildBuild.buildBuilding(structure, resource_state);
        if (point == 0){
            return;
        }
        mark += point;
        displayState(structure);
        text_resource.setText("You' ve got " + resource_state[0] + " Ore, "
                + resource_state[1] + " Grain, "
                + resource_state[2] + " Wool, "
                + resource_state[3] + " Lumber, "
                + resource_state[4] + " Brick, "
                + resource_state[5] + " Gold. ");
    }

    /**
     * trade: gold 2: 1 other resource. -- input: the resource type and amount that we want to swap(e.g. 2-Grain means:
     *                                            use 4 gold to replace two grain.)
     *
     * @param type_amount
     * @param resource_state
     */
    public void tradeDisplay(String type_amount, int[] resource_state){
        String[] tmp = type_amount.split("-");
        Integer amount = Integer.valueOf(tmp[0]);
        String type = tmp[1];
        switch (type){
            case "Ore": resource_state[0] += amount;break;
            case "Grain": resource_state[1] += amount;break;
            case "Wool": resource_state[2] += amount;break;
            case "Lumber": resource_state[3] += amount;break;
            case "Brick": resource_state[4] += amount;break;
            default:
                System.out.println("Wrong type in trade");
        }
        resource_state[5] -= amount*2;
        text_resource.setText("You' ve got " + resource_state[0] + " Ore, "
                + resource_state[1] + " Grain, "
                + resource_state[2] + " Wool, "
                + resource_state[3] + " Lumber, "
                + resource_state[4] + " Brick, "
                + resource_state[5] + " Gold. ");
    }

    /**
     * swap: other resource 1: 1 the resource marked on the joker,
     *       where "6 joker" can swap for any resource. -- input: the amount and type of the source we would like to pay,
     *      and the joker number that we want to use <no. of resource - Type - Joker no. - (resource want if in joker 6) :
     *      (e.g. 2-Grain-1 means we want to use 2 grains to replace 2 ore(whose number is 1)).
     * @param amount_type_jokerno
     * @param resource_state
     */
    public void swapDisplay(String amount_type_jokerno, int[] resource_state){
        String[] tmp_list = amount_type_jokerno.split("-");
        Integer amount = Integer.valueOf(tmp_list[0]);
        String type = tmp_list[1];
        Integer jokerno = Integer.valueOf(tmp_list[2]);
        if (jokerno==6){
            knights_fx.get(5).setFill(Color.RED);
            String resource_want = tmp_list[3];
            switch (resource_want){
                case "Ore":
                    resource_state[0] += amount;break;

                case "Grain": resource_state[1] += amount;break;
                case "Wool": resource_state[2] += amount;break;
                case "Lumber": resource_state[3] += amount;break;
                case "Brick": resource_state[4] += amount;break;
                default:
                    System.out.println("Wrong type in swap");
            }
        } else{
            switch (jokerno){
                case 1:
                    resource_state[0] += amount;
                    knights_fx.get(0).setFill(Color.RED);
                    break;
                case 2:
                    resource_state[1] += amount;
                    knights_fx.get(1).setFill(Color.RED);
                    break;
                case 3:
                    resource_state[2] += amount;
                    knights_fx.get(2).setFill(Color.RED);
                    break;
                case 4:
                    resource_state[3] += amount;
                    knights_fx.get(3).setFill(Color.RED);
                    break;
                case 5:
                    resource_state[4] += amount;
                    knights_fx.get(4).setFill(Color.RED);
                    break;
                default:
                    System.out.println("Wrong type in swap");
            }
        }
        switch (type){
            case "Ore": resource_state[0] -= amount;break;
            case "Grain": resource_state[1] -= amount;break;
            case "Wool": resource_state[2] -= amount;break;
            case "Lumber": resource_state[3] -= amount;break;
            case "Brick": resource_state[4] -= amount;break;
            default:
                System.out.println("Wrong type in swap");
        }
        text_resource.setText("You' ve got " + resource_state[0] + " Ore, "
                + resource_state[1] + " Grain, "
                + resource_state[2] + " Wool, "
                + resource_state[3] + " Lumber, "
                + resource_state[4] + " Brick, "
                + resource_state[5] + " Gold. ");
        // TODO: change the used status for the knight and change its color.
    }

    private void scoreBoardInitial(){

        ScoreNode scoreNode = new ScoreNode(800 , 400);
        Text score = new Text("0");
        score.setLayoutX(scoreNode.getLayoutX()+scoreNode.getWidth()/2);
        score.setLayoutY(scoreNode.getLayoutY()+scoreNode.getHeight()/2);
        scoreRecords.add(score);
        root.getChildren().addAll(scoreNode, score);

        for (int i=1; i < 5; i++){
            scoreNode = new ScoreNode(scoreNode.getLayoutX()+scoreNode.getWidth(), scoreNode.getLayoutY());
            score = new Text("0");
            score.setLayoutX(scoreNode.getLayoutX()+scoreNode.getWidth()/2);
            score.setLayoutY(scoreNode.getLayoutY()+scoreNode.getHeight()/2);
            scoreRecords.add(score);
            root.getChildren().addAll(scoreNode, score);
        }
        for (int i=0; i < 2; i++){
            scoreNode = new ScoreNode(scoreNode.getLayoutX(), scoreNode.getLayoutY()+scoreNode.getHeight());
            score = new Text("0");
            score.setLayoutX(scoreNode.getLayoutX()+scoreNode.getWidth()/2);
            score.setLayoutY(scoreNode.getLayoutY()+scoreNode.getHeight()/2);
            scoreRecords.add(score);
            root.getChildren().addAll(scoreNode, score);
        }
        for (int i=1; i < 5; i++){
            scoreNode = new ScoreNode(scoreNode.getLayoutX()-scoreNode.getWidth(), scoreNode.getLayoutY());
            score = new Text("0");
            score.setLayoutX(scoreNode.getLayoutX()+scoreNode.getWidth()/2);
            score.setLayoutY(scoreNode.getLayoutY()+scoreNode.getHeight()/2);
            scoreRecords.add(score);
            root.getChildren().addAll(scoreNode, score);
        }
        for (int i=0; i < 2; i++) {
            scoreNode = new ScoreNode(scoreNode.getLayoutX(), scoreNode.getLayoutY() + scoreNode.getHeight());
            score = new Text("0");
            score.setLayoutX(scoreNode.getLayoutX()+scoreNode.getWidth()/2);
            score.setLayoutY(scoreNode.getLayoutY()+scoreNode.getHeight()/2);
            scoreRecords.add(score);
            root.getChildren().addAll(scoreNode, score);
        }
        for (int i=0; i < 2; i++){
            scoreNode = new ScoreNode(scoreNode.getLayoutX()+scoreNode.getWidth(), scoreNode.getLayoutY());
            score = new Text("0");
            score.setLayoutX(scoreNode.getLayoutX()+scoreNode.getWidth()/2);
            score.setLayoutY(scoreNode.getLayoutY()+scoreNode.getHeight()/2);
            scoreRecords.add(score);
            root.getChildren().addAll(scoreNode, score);
        }

        ScoreNode total_score_node = new ScoreNode(scoreNode.getLayoutX()+scoreNode.getWidth()*1.5, scoreNode.getLayoutY());
        total_score_node.setWidth(scoreNode.getWidth()*1.5);
        Text total_score = new Text("0");
        total_score.setLayoutX(total_score_node.getLayoutX()+total_score_node.getWidth()/2);
        total_score.setLayoutY(total_score_node.getLayoutY()+total_score_node.getHeight()/2);
        scoreRecords.add(total_score);
        root.getChildren().addAll(total_score_node, total_score);
    }

    public void endOfRound() {
        if (round == 15){
            scoreRecords.get(round).setText(String.valueOf(total_mark));
            return;
        }
        // clear resource
        for (int i = 0; i < 6; i++){
            resource_state[i] = 0;
        }
        if (mark == 0){
            mark = -2;
        }
        scoreRecords.get(round).setText(String.valueOf(mark));
        round += 1;
        total_mark += mark;
        mark = 0;
        text_resource.setText("You' ve got " + resource_state[0] + " Ore, "
                + resource_state[1] + " Grain, "
                + resource_state[2] + " Wool, "
                + resource_state[3] + " Lumber, "
                + resource_state[4] + " Brick, "
                + resource_state[5] + " Gold. ");
    }


    @Override
    protected void makeControls() {

        // Dice and resource
        Label rollLabel = new Label("Dice:");
        boardTextField_roll = new TextField();
        boardTextField_roll.setPrefWidth(input_width);
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
        hb_roll.setLayoutX(begin_x);
        hb_roll.setLayoutY(begin_y+y_gap);
        controls.getChildren().add(hb_roll);

        // building
        Label buildLabel = new Label("Build:");
        boardTextField_build = new TextField();
        boardTextField_build.setPrefWidth(input_width);
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
        hb_build.setLayoutX(begin_x);
        hb_build.setLayoutY(begin_y+2*y_gap);
        controls.getChildren().add(hb_build);

        // trade and swap
        Label tradeLabel = new Label("Trade:");
        boardTextField_trade = new TextField();
        boardTextField_trade.setPrefWidth(input_width);
        Button button_trade = new Button("Trade it!");
        button_trade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tradeDisplay(boardTextField_trade.getText(), resource_state);
            }
        });
        HBox hb_trade = new HBox();
        hb_trade.getChildren().addAll(tradeLabel, boardTextField_trade, button_trade);
        hb_trade.setSpacing(10);
        hb_trade.setLayoutX(begin_x);
        hb_trade.setLayoutY(begin_y + 3*y_gap);
        controls.getChildren().add(hb_trade);

        Label swapLabel = new Label("Swap:");
        boardTextField_swap = new TextField();
        boardTextField_swap.setPrefWidth(input_width);
        Button button_swap = new Button("Swap it!");
        button_swap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                swapDisplay(boardTextField_swap.getText(), resource_state);
            }
        });
        HBox hb_swap = new HBox();
        hb_swap.getChildren().addAll(swapLabel, boardTextField_swap, button_swap);
        hb_swap.setSpacing(10);
        hb_swap.setLayoutX(begin_x);
        hb_swap.setLayoutY(begin_y+4*y_gap);
        controls.getChildren().add(hb_swap);

        // kill the round
        Button button_end = new Button("End Round");
        button_end.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                endOfRound();
            }
        });
        button_end.setLayoutX(begin_x+input_width/2);
        button_end.setLayoutY(begin_y+5*y_gap);
        controls.getChildren().add(button_end);
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Board State Viewer");

        text_resource.setText("You' ve got " + resource_state[0] + " Ore, "
                + resource_state[1] + " Grain, "
                + resource_state[2] + " Wool, "
                + resource_state[3] + " Lumber, "
                + resource_state[4] + " Brick, "
                + resource_state[5] + " Gold. ");
        text_resource.setFont(Font.font(15));


        buildBuildingFX();
        displayinitial();
        scoreBoardInitial();


        text_resource.setLayoutX(begin_x + 50);
        text_resource.setLayoutY(begin_y);
        root.getChildren().add(text_resource);

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