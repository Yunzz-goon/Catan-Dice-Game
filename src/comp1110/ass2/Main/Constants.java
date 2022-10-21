package comp1110.ass2.Main;

import javafx.scene.paint.Color;

import static comp1110.ass2.Resource.ResourceType.*;

public class Constants
{
    public final static int GOLD_ID = GOLD.ordinal();
    public final static int GRAIN_ID = GRAIN.ordinal();
    public final static int LUMBER_ID = LUMBER.ordinal();
    public final static int WOOL_ID = WOOL.ordinal();
    public final static int ORE_ID = ORE.ordinal();
    public final static int BRICK_ID = BRICK.ordinal();


    public static final int[] knightResources = {1, 1, 1, 0, 0, 0};
    public static final int[] roadResources = {0, 0, 0, 1, 1, 0};
    public static final int[] settlementResources = {0, 1, 1, 1, 1, 0};
    public static final int[] cityResources = {3, 2, 0, 0, 0, 0};

    public static final Color goldColor = Color.GOLD;
    public static final Color grainColor = Color.YELLOW;
    public static final Color lumberColor = Color.BROWN;
    public static final Color woolColor = Color.LIGHTGREEN;
    public static final Color oreColor = Color.LIGHTGRAY;
    public static final Color brickColor = Color.RED;
}
