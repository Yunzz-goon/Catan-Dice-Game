package comp1110.ass2.Main;

import comp1110.ass2.Building.Building;
import comp1110.ass2.Resource.Resource;

public class GameState {

    private int turn;

    private int currentScore;
    private Resource[][] resourceRecord;
    private boolean[] knightsUsed;
    private Building[][] buildingRecord;

    // Initialise a GameState
    public GameState() {
        this.turn = 0;
        this.currentScore = 0;
        this.resourceRecord = new Resource[15][6];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 6; j++) {
                this.resourceRecord[i][j] = null;
            }
        }
        this.knightsUsed = new boolean[6];
        for (int i = 0; i < 6; i++) {
            this.knightsUsed[i] = false;
        }
        this.buildingRecord = new Building[15][4]; // impossible to build more than four buildings on one turn
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 4; j++) {
                this.buildingRecord[i][j] = null;
            }
        }
    }

    // Create a GameState
    public GameState(int turn, int currentScore, Resource[][] resourceRecord, boolean[] knightsUsed, Building[][] buildingRecord) {
        this.turn = turn;
        this.currentScore = currentScore;
        this.resourceRecord = resourceRecord;
        this.knightsUsed = knightsUsed;
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

    public Resource[][] getResourceRecord()
    {
        return resourceRecord;
    }

    public boolean[] getKnightsUsed()
    {
        return knightsUsed;
    }

    public Building[][] getBuildingRecord()
    {
        return buildingRecord;
    }



    public void useKnight(int knightID)
    {
        assert knightID >= 0 && knightID <= 5;
        this.knightsUsed[knightID] = true;
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

    public void setResourceRecord(Resource[][] resourceRecord) {
        this.resourceRecord = resourceRecord;
    }

    public void setKnightsUsed(boolean[] knightsUsed) {
        this.knightsUsed = knightsUsed;
    }

    public void setBuildingRecord(Building[][] buildingRecord) {
        this.buildingRecord = buildingRecord;
    }

    public int calculateScore(Building[][] buildingRecord) {
        int score = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 4; j++) {
                if (buildingRecord[i][j] != null) {
                    score += buildingRecord[i][j].getPoint();
                }
            }
        }
        return score;
    }

    public void calculateAvailableBuildings(Resource[][] resourceRecord) {
        Resource[] availableResources = new Resource[6];
        for (int i = 0; i < 6; i++) {
            assert resourceRecord[turn][i] != null;
        }
        System.arraycopy(resourceRecord[turn], 0, availableResources, 0, 6);
        // TODO: calculate available buildings

    }
}
