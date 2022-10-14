package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Viewer extends Application {

//    private static final int VIEWER_WIDTH = 1200;
//    private static final int VIEWER_HEIGHT = 700;

    protected final Group root = new Group();
    protected final Group controls = new Group();
    protected TextField playerTextField;
    public TextField boardTextField;

    public static double HEXAGON_SIDE_LENGTH = 120;
    public static double HEXAGON_SIDE_LENGTH_half = HEXAGON_SIDE_LENGTH/2;
    public static double HEXAGON_HEIGHT = Math.sqrt(HEXAGON_SIDE_LENGTH * HEXAGON_SIDE_LENGTH
            - HEXAGON_SIDE_LENGTH_half * HEXAGON_SIDE_LENGTH_half);
    public static double WINDOW_WIDTH = 1200;
    public static double WINDOW_HEIGHT = 700;
    protected ArrayList<RoadFX> roads_fx = new ArrayList<RoadFX>();
    protected ArrayList<SettlementFX> settlements_fx = new ArrayList<SettlementFX>();
    protected ArrayList<KnightFX> knights_fx = new ArrayList<KnightFX>();
    protected ArrayList<CityFX> cities_fx = new ArrayList<CityFX>();


    class Hexagon extends Polygon {
        Hexagon(double xPos, double yPos, double sideLength){
            double halfSideLength = sideLength / 2;
            double halfHeight= Math.sqrt(sideLength*sideLength
                    - halfSideLength * halfSideLength); // 勾股定理得到高度
            this.getPoints().addAll(
                    -halfSideLength, -halfHeight,
                    halfSideLength, -halfHeight,
                    2 * halfSideLength, 0.0,
                    halfSideLength, halfHeight,
                    -halfSideLength, halfHeight,
                    -2*halfSideLength, 0.0);
            this.setFill(Color.GOLDENROD);
            this.setLayoutX(xPos);
            this.setLayoutY(yPos);
        }
    }

    class RoadFX extends Polygon{
        int mark;
        RoadFX(double xPos, double yPos, double degree, int mark){
            this.mark = mark;
            double height = HEXAGON_SIDE_LENGTH / 2;
            double width = HEXAGON_SIDE_LENGTH / 6;
            this.getPoints().addAll(-width/2, -height/2,
                    width/2, -height/2,
                    width/2, height/2,
                    -width/2, height/2);
            this.setFill(Color.GOLD);
            this.setLayoutX(xPos);
            this.setLayoutY(yPos);
            this.setRotate(degree);
        }
    }

    class ResourceFX extends Circle{
        ResourceFX(double xPos, double yPos, double radius){
            this.setLayoutX(xPos);
            this.setLayoutY(yPos);
            this.setRadius(radius);
            this.setFill(Color.GOLD);
        }
    }

    class KnightFX extends Ellipse{
        int mark;
        double centralX;
        double centralY;
        KnightFX(double xPos, double yPos, int mark){
            double width_half = HEXAGON_SIDE_LENGTH_half / 6;
            double height_half = HEXAGON_HEIGHT / 4;
            this.setCenterX(xPos);
            this.setCenterY(yPos - height_half);
            this.setRadiusX(width_half);
            this.setRadiusY(height_half);
            this.mark = mark;
            this.setFill(Color.GOLD);
            this.centralX = xPos;
            this.centralY = yPos - height_half;
        }
    }

    class SettlementFX extends Path{
        int mark;
        double centralX;
        double centralY;
        SettlementFX(double xPos, double yPos, int mark){
            double widthHalf = HEXAGON_SIDE_LENGTH_half / 6;
            double heightWall = HEXAGON_SIDE_LENGTH / 6;
            double ceilLength = HEXAGON_SIDE_LENGTH_half / 6;
            double gapCeilWall = HEXAGON_SIDE_LENGTH/30;
            double ceilHeight = Math.sqrt(8) / 2 * ceilLength;
            this.centralX = xPos;
            this.centralY = yPos;
            xPos += 1.25 * widthHalf + gapCeilWall;
            yPos += 0.5 * (heightWall+ceilHeight)/2;
            MoveTo moveto = new MoveTo(xPos, yPos);
            LineTo line1 = new LineTo(xPos, yPos - heightWall);
            LineTo line2 = new LineTo(xPos + gapCeilWall, yPos - heightWall);
            LineTo line3 = new LineTo(xPos - widthHalf, yPos - heightWall - ceilHeight);
            LineTo line4 = new LineTo(xPos - 2 * widthHalf -  gapCeilWall, yPos - heightWall);
            LineTo line5 = new LineTo(xPos - 2 * widthHalf, yPos - heightWall);
            LineTo line6 = new LineTo(xPos - 2 * widthHalf, yPos);
            LineTo line7 = new LineTo(xPos, yPos);
            this.getElements().add(moveto);
            this.getElements().addAll(line1, line2, line3, line4, line5, line6, line7);
            this.mark = mark;
            this.setFill(Color.GOLD);
        }
    }

    class CityFX extends Path{
        int mark;
        double centralX;
        double centralY;
        CityFX(double xPos, double yPos, int mark){
            double widthHalf = HEXAGON_SIDE_LENGTH_half / 6;
            double heightWall = HEXAGON_SIDE_LENGTH / 6;
            double ceilLength = HEXAGON_SIDE_LENGTH_half / 6;
            double gapCeilWall = HEXAGON_SIDE_LENGTH/30;
            double heightWall2 = 2 * heightWall / 3;
            double ceilHeight = Math.sqrt(8) / 2 * ceilLength;
            this.centralX = xPos;
            this.centralY = yPos;
            xPos += 1.25 * widthHalf + gapCeilWall;
            yPos += 0.5 * (heightWall+ceilHeight)/2;
            MoveTo moveto = new MoveTo(xPos, yPos);
            LineTo line1 = new LineTo(xPos, yPos - heightWall);
            LineTo line2 = new LineTo(xPos + gapCeilWall, yPos - heightWall);
            LineTo line3 = new LineTo(xPos - widthHalf, yPos - heightWall - ceilHeight);
            LineTo line4 = new LineTo(xPos - 2 * widthHalf -  gapCeilWall, yPos - heightWall);
            LineTo line5 = new LineTo(xPos - 2 * widthHalf, yPos - heightWall);
            LineTo line6 = new LineTo(xPos - 2 * widthHalf, yPos - heightWall2);
            LineTo line7 = new LineTo(xPos - 4 * widthHalf, yPos - heightWall2);
            LineTo line8 = new LineTo(xPos - 4 * widthHalf, yPos);
            LineTo line9 = new LineTo(xPos, yPos);
            this.getElements().add(moveto);
            this.getElements().addAll(line1, line2, line3, line4, line5, line6, line7, line8, line9);
            this.mark = mark;
            this.setFill(Color.GOLD);
        }
    }

    /**
     * Show the state of a (single player's) board in the window.
     *
     * @param board_state string representation of the board state.
     */
    void displayState(String board_state) {
        // For the status of the buildings:
        // - Road, settlement, city have only two states: unbuilt(gold), built(green)
        // - Knight have three states: unbuilt(gold), built also unused(green), built also used(red)
        // unbuilt is the default state with color gold.

        // default initialization for color
        for (RoadFX road : roads_fx){
            road.setFill(Color.GOLD);
        }
        for (CityFX city : cities_fx){
            city.setFill(Color.GOLD);
        }
        for (SettlementFX settlement: settlements_fx){
            settlement.setFill(Color.GOLD);
        }
        for (KnightFX knight : knights_fx){
            knight.setFill(Color.GOLD);
        }

        String[] states = board_state.split(",");
        for (String state : states){
            char build_type = state.charAt(0);
            int length = state.length()-1;
            char[] build_no = new char[length];
            state.getChars(1, length+1, build_no, 0);
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
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    protected void makeControls() {
        Label boardLabel = new Label("Board State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(500);
        Button button = new Button("Show");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel, boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(500);
        controls.getChildren().add(hb);
    }

    public void buildBuildingFX(){
        double[] resource_x = {2 * HEXAGON_SIDE_LENGTH_half, 2 * HEXAGON_SIDE_LENGTH_half,
                5 * HEXAGON_SIDE_LENGTH_half, 8 * HEXAGON_SIDE_LENGTH_half,
                8 * HEXAGON_SIDE_LENGTH_half, 5 * HEXAGON_SIDE_LENGTH_half};
        double[] resource_y = {2 * HEXAGON_HEIGHT, 4 * HEXAGON_HEIGHT,
                5 * HEXAGON_HEIGHT, 4 * HEXAGON_HEIGHT,
                2 * HEXAGON_HEIGHT, HEXAGON_HEIGHT};
        // Hexagon:
        ArrayList<Hexagon> hexagons = new ArrayList<Hexagon>();
        for (int i = 0; i < 6; i++){
            Hexagon hexagon = new Hexagon(resource_x[i], resource_y[i], HEXAGON_SIDE_LENGTH);
            hexagons.add(hexagon);
            root.getChildren().add(hexagon);
        }

        // resource:
        String[] text_content = {"Ore", "Grain", "Wool", "Timber", "Bricks", "Gold"};
        double radiusCircle = HEXAGON_SIDE_LENGTH/3;
        ArrayList<ResourceFX> resources = new ArrayList<ResourceFX>();
        for (int i = 0; i < 6; i++){
            ResourceFX resourcefx = new ResourceFX(resource_x[i], resource_y[i], radiusCircle);
            resources.add(resourcefx);
            Text text = new Text(resource_x[i], resource_y[i], text_content[i]);
            root.getChildren().addAll(resourcefx, text);
        }

        // knight
        for (int i = 0; i < 6; i++){
            KnightFX knightfx = new KnightFX(resource_x[i], resource_y[i] - radiusCircle, i+1);
            Text text = new Text(knightfx.centralX, knightfx.centralY, String.valueOf(i+1));
            root.getChildren().addAll(knightfx, text);
            knights_fx.add(knightfx);
        }
        System.out.println(knights_fx.size());

        // settlement
        double[] settlement_x = {4 * HEXAGON_SIDE_LENGTH_half,
                4 * HEXAGON_SIDE_LENGTH_half,
                4 * HEXAGON_SIDE_LENGTH_half,
                7 * HEXAGON_SIDE_LENGTH_half,
                7 * HEXAGON_SIDE_LENGTH_half,
                7 * HEXAGON_SIDE_LENGTH_half};
        double[] settlement_y = {2 * HEXAGON_HEIGHT, 4 * HEXAGON_HEIGHT,
                6 * HEXAGON_HEIGHT, 5 * HEXAGON_HEIGHT,
                3 * HEXAGON_HEIGHT, HEXAGON_HEIGHT};
        int[] settlement_marks = {3, 4, 5, 7, 9, 11};

        for (int i = 0; i < 6; i++){
            SettlementFX settlementfx = new SettlementFX(settlement_x[i], settlement_y[i], settlement_marks[i]);
            settlements_fx.add(settlementfx);
            Text text = new Text(String.valueOf(settlementfx.mark));
            text.setLayoutX(settlementfx.centralX);
            text.setLayoutY(settlementfx.centralY);
            root.getChildren().addAll(settlementfx, text);
        }

        // city
        double[] city_x = {HEXAGON_SIDE_LENGTH_half,
                HEXAGON_SIDE_LENGTH_half,
                10 * HEXAGON_SIDE_LENGTH_half,
                10 * HEXAGON_SIDE_LENGTH_half};
        double[] city_y = {3 * HEXAGON_HEIGHT, 5 * HEXAGON_HEIGHT,
                4 * HEXAGON_HEIGHT, 2 * HEXAGON_HEIGHT};
        int[] city_marks = {7, 12, 20,30};

        for (int i = 0; i < 4; i++){
            CityFX cityfx = new CityFX(city_x[i], city_y[i], city_marks[i]);
            Text text = new Text(String.valueOf(cityfx.mark));
            text.setLayoutX(cityfx.centralX);
            text.setLayoutY(cityfx.centralY);
            root.getChildren().addAll(cityfx, text);
            cities_fx.add(cityfx);
        }

        // road
        double[] road_x = {7 * HEXAGON_SIDE_LENGTH_half / 2,
                2 * HEXAGON_SIDE_LENGTH_half,
                7 * HEXAGON_SIDE_LENGTH_half / 2,
                7 * HEXAGON_SIDE_LENGTH_half / 2,
                2 * HEXAGON_SIDE_LENGTH_half,
                7 * HEXAGON_SIDE_LENGTH_half / 2,
                10 * HEXAGON_SIDE_LENGTH_half / 2,
                13 * HEXAGON_SIDE_LENGTH_half / 2,
                13 * HEXAGON_SIDE_LENGTH_half / 2,
                13 * HEXAGON_SIDE_LENGTH_half / 2,
                13 * HEXAGON_SIDE_LENGTH_half / 2,
                13 * HEXAGON_SIDE_LENGTH_half / 2,
                8 * HEXAGON_SIDE_LENGTH_half,
                19 * HEXAGON_SIDE_LENGTH_half / 2,
                19 * HEXAGON_SIDE_LENGTH_half / 2,
                19 * HEXAGON_SIDE_LENGTH_half / 2,
        };
        double[] road_y = {5 * HEXAGON_HEIGHT / 2,
                3 * HEXAGON_HEIGHT,
                7 * HEXAGON_HEIGHT / 2,
                9 * HEXAGON_HEIGHT / 2,
                5 * HEXAGON_HEIGHT,
                11 * HEXAGON_HEIGHT / 2,
                6 * HEXAGON_HEIGHT,
                11 * HEXAGON_HEIGHT / 2,
                9 * HEXAGON_HEIGHT / 2,
                7 * HEXAGON_HEIGHT / 2,
                5 * HEXAGON_HEIGHT / 2,
                3 * HEXAGON_HEIGHT / 2,
                5 * HEXAGON_HEIGHT,
                9 * HEXAGON_HEIGHT / 2,
                7 * HEXAGON_HEIGHT / 2,
                5 * HEXAGON_HEIGHT / 2,
        };

        double[] road_degree = {30, 90, 330, 30, 90, 330, 90, 30, 330, 30, 330, 30, 90, 30, 330, 30};
//        ArrayList<RoadFX> roads = new ArrayList<RoadFX>();

        for (int i = 0; i < 16; i++){
            RoadFX roadfx = new RoadFX(road_x[i], road_y[i], road_degree[i], 1);
            roads_fx.add(roadfx);
            Text text = new Text("1");
            text.setLayoutX(road_x[i]);
            text.setLayoutY(road_y[i]);
            text.setRotate(road_degree[i]);
            root.getChildren().addAll(roadfx, text);
        }



    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Board State Viewer");

        buildBuildingFX();

        Scene scene = new Scene(root,WINDOW_WIDTH,WINDOW_HEIGHT,Color.
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
