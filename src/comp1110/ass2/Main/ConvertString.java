package comp1110.ass2.Main;

import comp1110.ass2.Building.*;
import comp1110.ass2.Resource.Resource;

public class ConvertString
{
    // This class will convert the string representation of the board state to
    // whichever state we choose to use
    // It will also convert it back

    // TODO - A large portion of this class is still not fully implemented due to limitations with the string boardState -
    //  potentially we could write to something like JSON to allow us to keep track of whats going on?
    public GameState convertToGState(String boardState, String resourceState)
    {
        // This method will convert the string representation of the board state to
        // a GameState object
        int turn = determineTurn(boardState);
        int currentScore = determineScore(boardState);
        Resource[][] resourceRecord = determineResourceRecord(resourceState);
        boolean[] knightsUsed = determineKnightsUsed(boardState);
        Building[][] buildingRecord = determineBuildingRecord(boardState);
        return new GameState(turn, currentScore, resourceRecord, knightsUsed, buildingRecord);
    }

    public String convertGSToBS(GameState gameState)
    {
        // This method will convert the GameState object to the string representation of the board state
        Building[][] buildingRecord = gameState.getBuildingRecord();
        StringBuilder boardState = new StringBuilder();
        for (Building[] turns : buildingRecord)
        {
            for (Building building : turns)
            {
                if (building != null)
                {
                    if (building instanceof Settlement) boardState.append('S');
                    else if (building instanceof Road) boardState.append('R');
                    else if (building instanceof City) boardState.append('C');
                    else if (building instanceof Knight) boardState.append('K');
                    else
                    {
                        throw new IllegalArgumentException("Invalid building type");
                    }
                    boardState.append(String.valueOf(building.getPoint()));
                    boardState.append(',');
                }
            }
        }
        // remove the last comma, if it exists
        if (boardState.length() > 0)
        {
            boardState.deleteCharAt(boardState.length() - 1);
        }
        return boardState.toString();
    }
    public int[] convertGSToRS(GameState gameState)
    {
        int[] resourceState = new int[6];
        Resource[][] resources = gameState.getResourceRecord();
        for (int i = 0; i < resources.length; i++)
        {
            // finds the first null in the array - the elements before this are the current resources available
            if (resources[i][0] == null)
            {
                for (int j = 0; j < 6; j++)
                {
                    resourceState[j] = resources[i-1][j].getResourceQuantity();
                }
            }
            // if there are no nulls, then we want the elements in the last row - these are the currently
            // available elements on the last turn of the game
            else if (i == resources.length - 1)
            {
                for (int j = 0; j < 6; j++)
                {
                    resourceState[j] = resources[i][j].getResourceQuantity();
                }
            }
        }
        return resourceState;
    }

    public int determineScore(String boardState)
    {
        int score = 0;
        String[] buildings = boardState.split(",");
        try
        {
            for (String building : buildings)
            {
                score += Integer.parseInt(String.valueOf(building.charAt(1)));
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in determining the " +
                    "score of this gameState - potentially the" +
                    " gameState was not well formed" + e);
        }
        return score;
    }

    public int determineTurn(String boardState)
    {
        // TODO - this may not be possible given the string representation of the game we need to convert to and from -
        //  ideally the counter for the turn will be continuous, as in a single instance of GameState for each player
        //  throughout their game
        return 0;
    }
    public boolean[] determineKnightsUsed(String boardState)
    {
        boolean[] knightsUsed = new boolean[6];
        String[] buildings = boardState.split(",");
        for (String building : buildings)
        {
            if (building.charAt(0) == 'K')
            {
                try
                {
                    int knightID = Integer.parseInt(String.valueOf(building.charAt(1)));
                    knightsUsed[knightID] = true;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error in determining the " +
                            "knights used in this gameState - potentially the" +
                            " gameState was not well formed" + e);
                }
            }

        }
        return knightsUsed;
    }
    public Resource[][] determineResourceRecord(String resourceState)
    {
        // TODO - again this will be impossible to do without keeping a continuous record of the resources throughout
        //  the state, however it is possible to do this for the current turn if we can work out how to keep track of
        //  the turns throughout instances of conversion
        return null;
    }
    public Building[][] determineBuildingRecord(String boardState)
    {
        // TODO - this one is doable, however it would require stacking all the buildings into the first elements of
        //  the array, as we cannot keep accurate tracking otherwise
        Building[][] output = new Building[15][4];
        String[] buildings = boardState.split(",");
        char[] buildingChars = new char[buildings.length];
        int[] buildingPoints = new int[buildings.length];
        for (int i = 0; i < buildings.length; i++)
        {
            {
                try
                {
                    buildingChars[i] = buildings[i].charAt(0);
                    // this is the string minus the first character
                    String pointString = buildings[i].substring(1);
                    buildingPoints[i] = Integer.parseInt(pointString);
                }
                catch (NumberFormatException e) {
                    System.out.println("Error in determining the " +
                            "building record of this gameState - potentially the" +
                            " gameState was not well formed" + e);
                }
            }
        }
        return output;
    }
}
