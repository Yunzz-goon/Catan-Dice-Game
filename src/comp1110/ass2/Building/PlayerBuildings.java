package comp1110.ass2.Building;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerBuildings
{
    private ArrayList<Building> buildings;
    private int turnScore; // the score earned on this turn

    // initialise a PlayerBuildings object
    public PlayerBuildings()
    {
        this.buildings = new ArrayList<>();
        this.turnScore = 0;
    }

    // create a new PlayerBuildings object from a given turn
    public PlayerBuildings(Building[] buildings, int turnScore)
    {
        this.buildings = (ArrayList<Building>) Arrays.asList(buildings);
        this.turnScore = turnScore;
    }

    public ArrayList<Building> getBuildings()
    {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings)
    {
        this.buildings = buildings;
    }

    public int getTurnScore()
    {
        return turnScore;
    }

    public void setTurnScore(int turnScore)
    {
        this.turnScore = turnScore;
    }

    public int calculateTurnScore(ArrayList<Building> buildings)
    {
        int score = 0;
        for (Building building : buildings)
        {
            score += building.getPoint();
        }
        return score;
    }

    public Building[] getBuildingsAsArray()
    {
        Building[] buildingsArray = new Building[buildings.size()];
        for (int i = 0; i < buildings.size(); i++)
        {
            buildingsArray[i] = buildings.get(i);
        }
        return buildingsArray;
    }
}
