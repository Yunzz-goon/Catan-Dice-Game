package comp1110.ass2.Main;

import comp1110.ass2.Building.*;
import comp1110.ass2.Resource.PlayerResources;
import comp1110.ass2.Resource.Resource;
import comp1110.ass2.Resource.ResourceJoker;


import static comp1110.ass2.Resource.ResourceType.*;

//FIXME - I'm not fully happy with the implementation of GameState - perhaps a better way would
// be to have the buildings be all in a list, and have a boolean assigned to them depending on
// if they have been built or not - a problem for future Matt

public class ConvertString
{
    // TODO - A large portion of this class is still not fully implemented due to limitations with the string boardState -
    //  potentially we could write to something like JSON to allow us to keep track of whats going on?
    public GameState convertToGState(String boardState, String resourceState)
    {
        // This method will convert the string representation of the board state to
        // a GameState object
        int turn = determineTurn(boardState);
        int currentScore = determineScore(boardState);
        PlayerResources[] resourceRecord = determineResourceRecord(resourceState);
        ResourceJoker[] jokers = determineJokers(boardState);
        PlayerBuildings[] buildingRecord = determineBuildingRecord(boardState);
        return new GameState(turn, currentScore, resourceRecord, jokers, buildingRecord);
    }

    public String convertGSToBS(GameState gameState)
    {
        // This method will convert the GameState object to the string representation of the board state
        StringBuilder boardState = new StringBuilder();
        PlayerBuildings[] buildingRecord = gameState.getBuildingRecord();
        for (PlayerBuildings playerBuildings : buildingRecord)
        {
            Building[] buildings = playerBuildings.getBuildingsAsArray();
            for (Building building : buildings)
            {
                if (building != null)
                {

                    {
                    if (building instanceof Settlement) boardState.append('S');
                    else if (building instanceof Road) boardState.append('R');
                    else if (building instanceof City) boardState.append('C');
                    else if (building instanceof Knight) boardState.append('K');
                    else
                    {
                        throw new IllegalArgumentException("Invalid building instance");
                    }
                    boardState.append(building.getPoint());
                    boardState.append(',');
                    }
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
        Resource[] resourcesThisTurn = new Resource[6];
        PlayerResources[] resources = gameState.getResourceRecord();
        for (int i = 0; i < resources.length; i++)
        {
            // finds the first null in the array - the elements before this are the current resources available
            if (resources[i] == null)
            {
                resourcesThisTurn = resources[i - 1].getResources();
            }
            // if there are no nulls, then we want the elements in the last row - these are the currently
            // available elements on the last turn of the game
            else if (i == resources.length - 1)
            {
                resourcesThisTurn = resources[i].getResources();
            }

        }
        int[] output = new int[6];
        for (Resource r : resourcesThisTurn)
        {
            if (r != null)
            {
                switch (r.getResourceType())
                {
                    case BRICK:
                        output[0] = r.getResourceQuantity();
                        break;
                    case GRAIN:
                        output[1] = r.getResourceQuantity();
                        break;
                    case LUMBER:
                        output[2] = r.getResourceQuantity();
                        break;
                    case ORE:
                        output[3] = r.getResourceQuantity();
                        break;
                    case WOOL:
                        output[4] = r.getResourceQuantity();
                        break;
                    case GOLD:
                        output[5] = r.getResourceQuantity();
                        break;
                }
            }
        }
        return output;
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
    public ResourceJoker[] determineJokers(String boardState)
    {
        ResourceJoker[] jokers = new ResourceJoker[6];
        String[] buildings = boardState.split(",");
        for (String building : buildings)
        {
            if (building.charAt(0) == 'K')
            {
                try
                {
                    int jNumber = Integer.parseInt(String.valueOf(building.charAt(1)));
                    jokers[jNumber] = new ResourceJoker
                            (
                                    switch (jNumber)
                                    {
                                        case 1 -> ORE;
                                        case 2 -> GRAIN;
                                        case 3 -> WOOL;
                                        case 4 -> LUMBER;
                                        case 5 -> BRICK;
                                        case 6 -> NULL;
                                        default -> throw new IllegalArgumentException("Invalid joker number");
                                    }
                            );
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error in determining the " +
                            "jokers of this gameState - potentially the" +
                            " gameState was not well formed" + e);
                }


            }
            else if (building.charAt(0) == 'J')
            {
                try
                {
                    int jNumber = Integer.parseInt(String.valueOf(building.charAt(1)));
                    jokers[jNumber] = new ResourceJoker
                            (
                                    switch (jNumber)
                                            {
                                                case 1 -> ORE;
                                                case 2 -> GRAIN;
                                                case 3 -> WOOL;
                                                case 4 -> LUMBER;
                                                case 5 -> BRICK;
                                                case 6 -> NULL;
                                                default -> throw new IllegalArgumentException("Invalid joker number");
                                            }
                            );
                    jokers[jNumber].setUsed();
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error in determining the " +
                            "jokers of this gameState - potentially the" +
                            " gameState was not well formed" + e);
                }

            }
        }



        return jokers;
    }
    public PlayerResources[] determineResourceRecord(String resourceState)
    {
        // TODO - again this will be impossible to do without keeping a continuous record of the resources throughout
        //  the state, however it is possible to do this for the current turn if we can work out how to keep track of
        //  the turns throughout instances of conversion
        return null;
    }
    public PlayerBuildings[] determineBuildingRecord(String boardState)
    {
        // TODO - this one is doable, however it would require stacking all the buildings into the first elements of
        //  the array, as we cannot keep accurate tracking otherwise
//        Building[][] output = new Building[15][4];
//        String[] buildings = boardState.split(",");
//        char[] buildingChars = new char[buildings.length];
//        int[] buildingPoints = new int[buildings.length];
//        for (int i = 0; i < buildings.length; i++)
//        {
//            {
//                try
//                {
//                    buildingChars[i] = buildings[i].charAt(0);
//                    // this is the string minus the first character
//                    String pointString = buildings[i].substring(1);
//                    buildingPoints[i] = Integer.parseInt(pointString);
//                }
//                catch (NumberFormatException e) {
//                    System.out.println("Error in determining the " +
//                            "building record of this gameState - potentially the" +
//                            " gameState was not well formed" + e);
//                }
//            }
//        }
        return null;
    }
}
