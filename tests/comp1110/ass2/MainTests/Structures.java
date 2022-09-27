package comp1110.ass2.MainTests;

import comp1110.ass2.Building.PlayerBuildings;
import comp1110.ass2.Main.GameState;
import comp1110.ass2.Resource.PlayerResources;
import comp1110.ass2.Resource.ResourceJoker;

import static comp1110.ass2.Resource.ResourceType.NULL;

public class Structures
{
     // TODO - Fill in the details
     // This class contains methods and variables that are used to test the structure of the code

    public static final GameState startState = new GameState();

    public static final int partialTurn = 3;
    public static final int partialScore = 10;
    public static final PlayerResources[] partialResources = new PlayerResources[]
    {
          new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
    };
    public static final PlayerBuildings[] partialBuildings = new PlayerBuildings[]
    {
              new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
    };
    public static final ResourceJoker[] partialJokers = new ResourceJoker[]
    {
              new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
    };

    public static final GameState partialState = new GameState
            (
              partialTurn
                    , partialScore
                    , partialResources
                    , partialJokers
                    , partialBuildings
            );

    public static final int fullTurn = 14;
    public static final int fullScore = 100;
    public static final PlayerResources[] fullResources = new PlayerResources[]
    {
          new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
        , new PlayerResources()
    };
    public static final PlayerBuildings[] fullBuildings = new PlayerBuildings[]
    {
              new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
            , new PlayerBuildings()
    };
    public static final ResourceJoker[] fullJokers = new ResourceJoker[]
    {
              new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
            , new ResourceJoker(NULL)
    };
    public static final GameState finalState = new GameState
    (
          fullTurn
        , fullScore
        , fullResources
        , fullJokers
        , fullBuildings
    );
}
