package comp1110.ass2.Main;

import comp1110.ass2.Building.Building;
import comp1110.ass2.Building.PlayerBuildings;
import comp1110.ass2.Resource.PlayerResources;
import comp1110.ass2.Resource.Resource;
import comp1110.ass2.Resource.ResourceJoker;

import java.util.ArrayList;

import static comp1110.ass2.Resource.ResourceType.*;

public class GameState
{

    private int turn;

    private int currentScore;
    private PlayerResources[] resourceRecord = new PlayerResources[15];
    private ResourceJoker[] jokers = new ResourceJoker[6];
    private PlayerBuildings[] buildingRecord = new PlayerBuildings[15];

    // Initialise a GameState - this is what each player would be assigned at the start of the game,
    // and it will be updated each turn
    public GameState()
    {
        this.turn = 0;
        this.currentScore = 0;
        // initialise the resource record and building records as null
        for (int i = 0; i < 15; i++)
        {
            resourceRecord[i] = new PlayerResources();
            buildingRecord[i] = new PlayerBuildings();
        }
        // initialise the knights / jokers
        this.jokers = new ResourceJoker[]
                {
                    new ResourceJoker(GRAIN)
                ,   new ResourceJoker(LUMBER)
                ,   new ResourceJoker(WOOL)
                ,   new ResourceJoker(ORE)
                ,   new ResourceJoker(BRICK)
                ,   new ResourceJoker(NULL) // this joker can be used as anything (it is a wild card)
                };
        }

    // Create a new GameState
    public GameState(int turn, int currentScore, PlayerResources[] resourceRecord, ResourceJoker[] jokers, PlayerBuildings[] buildingRecord)
    {
        this.turn = turn;
        this.currentScore = currentScore;
        this.resourceRecord = resourceRecord;
        this.jokers = jokers;
        this.buildingRecord = buildingRecord;
    }


    public int getTurn() {
        return turn;
    }

    public void updateTurn() {
        this.turn++;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public PlayerResources[] getResourceRecord()
    {
        return resourceRecord;
    }

    public ResourceJoker[] getJokers()
    {
        return jokers;
    }

    public PlayerBuildings[] getBuildingRecord()
    {
        return buildingRecord;
    }

    public void updateScore() {
        this.currentScore = calculateScore(this.buildingRecord);
    }
    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void setResourceRecord(PlayerResources[] resourceRecord)
    {
        this.resourceRecord = resourceRecord;
    }

    public void setBuildingRecord(PlayerBuildings[] buildingRecord)
    {
        this.buildingRecord = buildingRecord;
    }

    public int calculateScore(PlayerBuildings[] buildingRecord)
    {
        int score = 0;
        for (PlayerBuildings playerBuildings : buildingRecord)
        {
            score += playerBuildings.getTurnScore();
        }
        return score;
    }

    public Building[] calculateAvailableBuildings(PlayerResources[] resourceRecord)
    {
        Resource[] availableResources = resourceRecord[turn].getResources(); // get the resources available to the player
        ArrayList<Building> availableBuildings = new ArrayList<Building>();
        // TODO: calculate available buildings


        int size = availableBuildings.size();
        Building[] availableBuildingsArray = new Building[size];
        for (int i = 0; i < size; i++)
        {
            availableBuildingsArray[i] = availableBuildings.get(i);
        }
        return availableBuildingsArray;

    }

    public Building[] removeAvailableDuplicates(Building[] availableBuildings)
    {
        return null;
    }
}
