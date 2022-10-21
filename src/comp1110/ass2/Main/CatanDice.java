package comp1110.ass2.Main;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static comp1110.ass2.Building.City.cityResources;
import static comp1110.ass2.Building.Knight.knightResources;
import static comp1110.ass2.Building.Road.roadResources;
import static comp1110.ass2.Building.Settlement.settlementResources;
import static comp1110.ass2.Resource.Resource.*;
public class CatanDice {
    public static int seed = 100;

    /**
     * Check if the string encoding of a board state is well formed.
     * Note that this does not mean checking if the state is valid
     * (represents a state that the player could get to in game play),
     * only that the string representation is syntactically well formed.
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the string is a well-formed representation of
     *         a board state, false otherwise.
     */
    public static boolean isBoardStateWellFormed(String board_state)
    {
        boolean flag = true; // flag to check if the string is well-formed
        if (board_state.equals("")) return true;
        String[] boardStateArray = board_state.split(",");
        for (String state : boardStateArray)
        {
            if (!isStateWellFormed(state)) flag = false;
        }
        return flag;
    }

    /**
     * Check if the string encoding of a single state is well-formed.
     * Used for both the boardState check and the action check so turned into its own method
     * @param state the string encoding of a single state
     * @return true if the string is well-formed, false otherwise
     */

    public static boolean isStateWellFormed (String state)
    {
        boolean flag = true;
        if (state.equals("")) return true;
        else
        {
            String reference = "RSCKJ";
            char firstChar = state.charAt(0);
            if (!reference.contains(String.valueOf(firstChar))) flag = false;
            String numberString = state.substring(1);
            try
            {
                int endNumber = Integer.parseInt(numberString);
                switch (firstChar)
                {
                    case 'R':
                        if (endNumber < 0 || endNumber > 15)
                        {
                            flag = false;
                            break;
                        }
                        break;
                    case 'S':
                        if (endNumber != 3 && endNumber != 4 && endNumber != 5 && endNumber != 7 && endNumber != 9 && endNumber != 11)
                        {
                            flag = false;
                            break;
                        }
                        break;
                    case 'C':
                        if (endNumber != 7 && endNumber != 12 && endNumber != 20 && endNumber != 30)
                        {
                            flag = false;
                            break;
                        }
                        break;
                    case 'J':
                    case 'K':
                        if (endNumber < 1 || endNumber > 6)
                        {
                            flag = false;
                            break;
                        }
                        break;
                }
            }
            catch (NumberFormatException e)
            {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Check if the string encoding of a player action is well formed.
     *
     * @param action: The string representation of the action.
     * @return true iff the string is a well-formed representation of
     *         a board state, false otherwise.
     */
    public static boolean isActionWellFormed(String action)
    {
        /*
         * Conditions for a well-formed action:
         * 1. The action must be one of the following: build, trade, swap
         * 2. The action must be followed by a space
         * 3. If the action is a build, it must be followed by a valid board state
         *    Else if the action is a trade, it must be followed by a number between 0 and 5 inclusive
         *    Else if the action is a swap, it must be followed by two numbers separated by a space, each number between 0 and 5 inclusive
         */
        boolean flag = true;
        String[] actionArray = action.split(" ");
        int length = actionArray.length;
        // if there are fewer than 2 or more than three elements in the array, the action cannot be well-formed
        if (length < 2 || length > 3) flag = false;
        else if (length == 2)
        {
            if (actionArray[0].equals("build"))
            {
                if (!isBoardStateWellFormed(actionArray[1])) flag = false;
            }
            else if (actionArray[0].equals("trade"))
            {
                try
                {
                    int number = Integer.parseInt(actionArray[1]);
                    if (number < 0 || number > 5) flag = false;
                }
                // if the second element is not an integer, the action cannot be well-formed
                catch (NumberFormatException e)
                {
                    flag = false;
                }
            }
            else flag = false;
        }
        else
        {
            if (!actionArray[0].equals("swap")) flag = false;
            else
            {
                try
                {
                    int firstNumber = Integer.parseInt(actionArray[1]);
                    int secondNumber = Integer.parseInt(actionArray[2]);
                    if (firstNumber < 0 || firstNumber > 5 || secondNumber < 0 || secondNumber > 5) flag = false;
                }
                // if the second or third element is not an integer, the action cannot be well-formed
                catch (NumberFormatException e)
                {
                    flag = false;
                }
            }

        }
        return flag;
    }

    /**
     * Roll the specified number of dice and add the result to the
     * resource state.
     *
     * The resource state on input is not necessarily empty. This
     * method should only _add_ the outcome of the dice rolled to
     * the resource state, not remove or clear the resources already
     * represented in it.
     *
     * @param n_dice: The number of dice to roll (>= 0).
     * @param resource_state: The available resources that the dice
     *        roll will be added to.
     *
     * This method does not return any value. It should update the given
     * resource_state.
     */
    public static void rollDice(int n_dice, int[] resource_state) {
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
    }

    /**
     * Check if the specified structure can be built next, given the
     * current board state. This method should check that the build
     * meets the constraints described in section "Building Constraints"
     * of the README file.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param board_state: The string representation of the board state.
     * @return true iff the structure is a possible next build, false
     *         otherwise.
     */
    public static boolean checkBuildConstraints(String structure,
                                                String board_state)
    {
        List<String> boardStateArray = List.of(board_state.split(","));
        if (boardStateArray.contains(structure)) return false;
        switch (structure)
        {
            case "R0", "S3", "J1" ->
            {
                return true;
            }
            case "R1", "R2" ->
            {
                return boardStateArray.contains("R0");
            }
            case "R3" ->
            {
                return boardStateArray.contains("R2");
            }
            case "R4", "R5" ->
            {
                return boardStateArray.contains("R3");
            }
            case "R6" ->
            {
                return boardStateArray.contains("R5");
            }
            case "R7" ->
            {
                return boardStateArray.contains("R6");
            }
            case "R8", "R12" ->
            {
                return boardStateArray.contains("R7");
            }
            case "R9" ->
            {
                return boardStateArray.contains("R8");
            }
            case "R10" ->
            {
                return boardStateArray.contains("R9");
            }
            case "R11" ->
            {
                return boardStateArray.contains("R10");
            }
            case "R13" ->
            {
                return boardStateArray.contains("R12");
            }
            case "R14" ->
            {
                return boardStateArray.contains("R13");
            }
            case "R15" ->
            {
                return boardStateArray.contains("R14");
            }

            case "C7" ->
            {
                return boardStateArray.contains("R1");
            }
            case "C12" ->
            {
                return boardStateArray.contains("C7") &&
                        boardStateArray.contains("R4");
            }
            case "C20" ->
            {
                return boardStateArray.contains("C12") &&
                        boardStateArray.contains("R13");
            }
            case "C30" ->
            {
                return boardStateArray.contains("C20") &&
                        boardStateArray.contains("R15");
            }

            case "S4" ->
            {
                return boardStateArray.contains("R2") &&
                        boardStateArray.contains("S3");
            }
            case "S5" ->
            {
                return boardStateArray.contains("R5") &&
                        boardStateArray.contains("S4");
            }
            case "S7" ->
            {
                return boardStateArray.contains("R7") &&
                        boardStateArray.contains("S5");
            }
            case "S9" ->
            {
                return boardStateArray.contains("R9") &&
                        boardStateArray.contains("S7");
            }
            case "S11" ->
            {
                return boardStateArray.contains("R11") &&
                        boardStateArray.contains("S9");
            }

            case "J2" ->
            {
                return boardStateArray.contains("J1") ||
                        boardStateArray.contains("K1");
            }
            case "J3" ->
            {
                return boardStateArray.contains("J2") ||
                        boardStateArray.contains("K2");
            }
            case "J4" ->
            {
                return boardStateArray.contains("J3") ||
                        boardStateArray.contains("K3");
            }
            case "J5" ->
            {
                return boardStateArray.contains("J4") ||
                        boardStateArray.contains("K4");
            }
            case "J6" ->
            {
                return boardStateArray.contains("J5") ||
                        boardStateArray.contains("K5");
            }
        }
        return false;
    }


    /**
     * Check if the available resources are sufficient to build the
     * specified structure, without considering trades or swaps.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     *         resources, false otherwise.
     */
    public static boolean checkResources(String structure, int[] resource_state)
    {
        /*
         * There are 4 types of structures, road, knight, settlement and city
         * Each type of structure has a different cost
         * The cost of a road is 1 brick and 1 lumber - represented by R and then a number
         * The cost of a knight is 1 ore, 1 wool and 1 grain - represented by a K and then a number
         * The cost of a settlement is 1 brick, 1 lumber, 1 wool and 1 grain - represented by an S and then a number
         * The cost of a city is 3 ore and 2 grain - represented by a C and then a number
         * The resources are represented by their ID in the resource state array
         */
        boolean flag = true;
        char struct = structure.charAt(0);
        switch (struct)
        {
            case 'R' ->
            {
                if (resource_state[BRICK_ID] < 1 ||
                        resource_state[LUMBER_ID] < 1) flag = false;
            }
            case 'K', 'J' ->
            {
                if (resource_state[ORE_ID] < 1 ||
                        resource_state[WOOL_ID] < 1 ||
                        resource_state[GRAIN_ID] < 1) flag = false;
            }
            case 'S' ->
            {
                if (resource_state[WOOL_ID] < 1  ||
                        resource_state[GRAIN_ID] < 1 ||
                        resource_state[LUMBER_ID] < 1 ||
                        resource_state[BRICK_ID] < 1) flag = false;
            }
            case 'C' ->
            {
                if (resource_state[ORE_ID] < 3 ||
                        resource_state[GRAIN_ID] < 2) flag = false;
            }
            default -> flag = false;
        }
        return flag;
    }

    /**
     * Check if the available resources are sufficient to build the
     * specified structure, considering also trades and/or swaps.
     * This method needs access to the current board state because the
     * board state encodes which Knights are available to perform swaps.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     *         resources, false otherwise.
     */
    public static boolean checkResourcesWithTradeAndSwap(String structure,
                                                         String board_state,
                                                         int[] resource_state)
    {
        if (checkResources(structure, resource_state)) return true;
        String[] boardArray = board_state.split(",");
        String[] knightsAvailable = new String[6];
        // finding available knights
        for (String s : boardArray)
        {
            if (s.charAt(0) == 'J')
            {
                knightsAvailable[Integer.parseInt(s.substring(1)) - 1] = s;
            }
        }
        int[] availableResources = new int[6];
        int[] missingResources = new int[6];

        // creating available resources array and missing resources array
        for (int i = 0; i < 6; i++)
        {
            int n = 0;
            switch (structure.charAt(0))
            {
                case 'R' ->
                {
                    n = resource_state[i] - roadResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
                case 'J' ->
                {
                    n = resource_state[i] - knightResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
                case 'C' ->
                {
                    n = resource_state[i] - cityResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;

                }
                case 'S' ->
                {
                    n = resource_state[i] - settlementResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;

                }
            }
        }
        System.out.println("Available Resources: " + Arrays.toString(availableResources));
        System.out.println("Missing Resources: " + Arrays.toString(missingResources));

        if (Math.abs(2 * intSumArray(missingResources)) <= availableResources[5])
        {
            // we can perform gold trades only to get all the resources we need to build the structure
            return true;
        }
        // iterate through the first 5 resources (the ones we need for building), and find all
        // possible ways to get them above zero
        for (int i = 0; i < 5; i++)
        {
            if (missingResources[i] < 0)
            {
                if (knightsAvailable[i] != null)
                {
                    // we can perform a swap with the knight, so we want to update the arrays accordingly
                    // find the first available resource that we can swap with
                    for (int j = 0; j < 5; j++)
                    {
                        if (availableResources[j] > 0)
                        {
                            // we can swap with this resource
                            availableResources[j]--;
                            missingResources[i]++;
                            knightsAvailable[i] = null;
                            if (missingResources[i] < 0) i--; // we want to redo this iteration of the loop and see if
                            // there are other ways to get the resources we need
                            System.out.println("Available Resources: " + Arrays.toString(availableResources));
                            System.out.println("Missing Resources: " + Arrays.toString(missingResources));
                            System.out.println("Available Knights: " + Arrays.toString(knightsAvailable));
                            break;
                        }
                    }
                }
                else if (knightsAvailable[5] != null)
                {
                    // we can perform the swap with the wildcard knight
                    // find the first available resource that we can swap with
                    for (int j = 0; j < 5; j++)
                    {
                        if (availableResources[j] > 0)
                        {
                            // we can swap with this resource
                            availableResources[j]--;
                            missingResources[i]++;
                            knightsAvailable[5] = null;
                            if (missingResources[i] < 0) i--; // we want to redo this iteration of the loop and see if
                            // there are other ways to get the resources we need
                            System.out.println("Available Resources: " + Arrays.toString(availableResources));
                            System.out.println("Missing Resources: " + Arrays.toString(missingResources));
                            System.out.println("Available Knights: " + Arrays.toString(knightsAvailable));
                            break;
                        }
                    }
                }
                // finally we want to try and trade for gold to get the resources we need
                else if (availableResources[5] >= 2)
                {
                    // we can trade for gold
                    availableResources[5] -= 2;
                    missingResources[i]++;
                    if (missingResources[i] < 0) i--; // we want to redo this iteration of the loop and see if
                    // there are other ways to get the resources we need
                    System.out.println("Available Resources: " + Arrays.toString(availableResources));
                    System.out.println("Missing Resources: " + Arrays.toString(missingResources));
                    System.out.println("Available Knights: " + Arrays.toString(knightsAvailable));
                }
                else
                {
                    // we can't get the resources we need
                    return false;
                }

            }

        }
        return true;
    }

    private static int intSumArray(int[] input)
    {
        int total = 0;
        for (int i : input)
        {
            total += i;
        }
        return total;
    }

    /**
     * Check if a player action (build, trade or swap) is executable in the
     * given board and resource state.
     *
     * @param action: String representation of the action to check.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action is applicable, false otherwise.
     */
    public static boolean canDoAction(String action,
                                      String board_state,
                                      int[] resource_state)
    {
//       proceed to systematically check all conditions whereby the action would be invalid
        if (!isBoardStateWellFormed(board_state)) return false;
        else if (!isActionWellFormed(action)) return false;
        else
        {
            char actionType = action.charAt(0);
            switch (actionType)
            {
                case 'b' ->
                {
                    String[] actionSplit = action.split(" ");
                    String structure = actionSplit[1].replace(",", ""); // not sure if this is necessary
//                    System.out.println(structure);
                    if (!checkResources(structure, resource_state))
                    {
                        System.out.println("Not enough resources to build " + structure);
                        return false;
                    }
                    // otherwise ensure the building can be built
                    else if (!checkBuildConstraints(structure, board_state))
                    {
                        System.out.println("Cannot build " + structure + " at this location");
                        return false;
                    }
                }
                case 't' ->
                {
                    return resource_state[resource_state.length - 1] >= 2;
                }
                case 's' ->
                {
                    try
                    {
                        int resourceToSwap = Integer.parseInt(action.split(" ")[1]);
                        if (resource_state[resourceToSwap] == 0) return false;
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Invalid resource to swap");
                        System.out.println(e);
                        return false;
                    }

                    try
                    {
                        int knightNumber = Integer.parseInt(action.split(" ")[2]) + 1;
                        String knightToUse = "J" + knightNumber; // if this knight (J<knightToUse> is not available, we need to check if the J6 is available
                        List<String> builtStructures = Arrays.asList(board_state.split(","));
                        if (!builtStructures.contains(knightToUse)) knightToUse = "J6";
                        if (!builtStructures.contains(knightToUse)) return false;
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Invalid knight to use");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check if the specified sequence of player actions is executable
     * from the given board and resource state.
     *
     * @param actions: The sequence of (string representations of) actions.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action sequence is executable, false otherwise.
     */
    public static boolean canDoSequence(String[] actions,
                                        String board_state,
                                        int[] resource_state)
    {
        // first we will want to validate that the first action can be done
        // then we update the list of actions, board state and resource state, and recursively check the next action
        // if the list of actions is null or has length zero then we can do the sequence (base case of the recursion)
        if (actions == null || actions.length == 0) return true;
        else if (!isBoardStateWellFormed(board_state)) return false;
        else if (!isAllActionsWellFormed(actions)) return false;
        else
        {
            String actionThisIteration = actions[0];
            String newBoardState;
            int[] newResourceState;
            String[] newActions = new String[actions.length - 1];
            if (canDoAction(actionThisIteration, board_state, resource_state))
            {
                // update the board state, resource state and action list for the next iteration
                if (actions.length == 1) return true;
                else
                {
                    newResourceState = updateResourceState(actionThisIteration, resource_state);
                    newBoardState = updateBoardState(actionThisIteration, board_state);
                    System.arraycopy(actions, 1, newActions, 0, actions.length - 1);
                    return canDoSequence(newActions, newBoardState, newResourceState);
                }

            }
            else return false; // if the first action can't be done then we can't do the rest of the sequence
        }
    }

    private static boolean isAllActionsWellFormed(String[] actions)
    {
        boolean output = true;
        for (String action : actions)
        {
            output = output && isActionWellFormed(action);
        }
        return output;
    }

    private static int[] updateResourceState(String actionThisIteration, int[] resource_state)
    {
        assert isActionWellFormed(actionThisIteration);
        int[] output = resource_state.clone();
        String actionType = actionThisIteration.split(" ")[0].charAt(0) + "";
        switch (actionType)
        {
            case "b" ->
            {
                // remove the resources used in the build action
                String buildingType = actionThisIteration.split(" ")[1].charAt(0) + "";
                switch (buildingType)
                {
                    case "J" ->
                    {
                        output[ORE_ID]--;
                        output[WOOL_ID]--;
                        output[GRAIN_ID]--;
                    }
                    case "C" ->
                    {
                        output[ORE_ID] -= 3;
                        output[GRAIN_ID] -= 2;
                    }
                    case "R" ->
                    {
                        output[BRICK_ID]--;
                        output[LUMBER_ID]--;
                    }
                    case "S" ->
                    {
                        output[BRICK_ID]--;
                        output[LUMBER_ID]--;
                        output[WOOL_ID]--;
                        output[GRAIN_ID]--;
                    }
                }

            }
            case "t" ->
            {
                // remove the gold used and add the resource gained
                assert resource_state[GOLD_ID] >= 2;
                try
                {
                    int resourceGained = Integer.parseInt(actionThisIteration.split(" ")[1]);
                    output[GOLD_ID] -= 2;
                    output[resourceGained]++;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error: the resource gained in the trade action is not a number");
                    System.exit(1);
                }

            }
            case "s" ->
            {
                // add the resource gained and remove the resource lost -
                // the joker lost will need to be updated by updateBoardState
                try
                {
                    int resourceLost = Integer.parseInt(actionThisIteration.split(" ")[1]);
                    int resourceGained = Integer.parseInt(actionThisIteration.split(" ")[2]);
                    output[resourceLost]--;
                    output[resourceGained]++;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error: the resource gained or lost in the swap action is not a number");
                    System.exit(1);
                }

            }
        }
        return output;
    }

    private static String updateBoardState(String actionThisIteration, String board_state)
    {
        assert isActionWellFormed(actionThisIteration);
        assert isBoardStateWellFormed(board_state);
        StringBuilder output = new StringBuilder(board_state);
        String actionType = actionThisIteration.split(" ")[0].charAt(0) + "";
        switch (actionType)
        {
            case "b" ->
            {
                // add the building to the board state
                String buildingType = actionThisIteration.split(" ")[1].charAt(0) + "";
                String buildingPoint = actionThisIteration.split(" ")[1].substring(1);
                output.append(",");
                output.append(buildingType);
                output.append(buildingPoint);
            }
            case "t" ->
            {
                // a trade will not affect the board state string as all a trade does is update the available resources
            }
            case "s" ->
            {
                // we will need to update the correct joker to a knight
                // first we try to update the joker that corresponds to the resource gained,
                // but if that one isn't available then we will use the wildcard joker.
                List<String> boardStateList = Arrays.asList(board_state.split(","));
                try
                {
                    int jokerInt = Integer.parseInt(actionThisIteration.split(" ")[2]) + 1;
                    String jokerToReplace = "J" + jokerInt;
                    if (boardStateList.contains(jokerToReplace))
                    {
                        output = new StringBuilder(output.toString().replace(jokerToReplace, "K" + jokerInt));
                    }
                    else
                    {
                        output = new StringBuilder(output.toString().replace("J6", "K6"));
                    }

                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error: the resource gained in the swap action is not a number");
                    System.exit(1);
                }
            }
        }
        return output.toString();
    }

    /**
     * Find the path of roads that need to be built to reach a specified
     * (unbuilt) structure in the current board state. The roads should
     * be returned as an array of their string representation, in the
     * order in which they have to be built. The array should _not_ include
     * the target structure (even if it is a road). If the target structure
     * is reachable via the already built roads, the method should return
     * an empty array.
     *
     * Note that on the Island One map, there is a unique path to every
     * structure.
     *
     * @param target_structure: The string representation of the structure
     *        to reach.
     * @param board_state: The string representation of the board state.
     * @return An array of string representations of the roads along the
     *         path.
     */
    public static String[] pathTo(String target_structure, String board_state) throws IllegalArgumentException
    {
        String[] alreadyBuilt = {};
        String[] path = new String[15];
        int pathLength = 0;
        if (target_structure == null
                || target_structure.length() == 0
                || target_structure.length() == 1)
        {
            throw new IllegalArgumentException("Invalid target structure");
        }
        if (!isBoardStateWellFormed(board_state))
        {
            throw new IllegalArgumentException("Invalid board state");
        }

        switch (target_structure.charAt(0))
        {
            case 'R' ->
            {
                switch (target_structure.charAt(1))
                {
                    case '0' ->
                    {
                        if (target_structure.length() == 2)
                        {
                            return alreadyBuilt;
                        }
                    }

                    case '1' ->
                    {
                        if (target_structure.length() == 2)
                        {
                            return pathToHelper("R1", board_state, path, pathLength);
                        }
                        else if (target_structure.length() == 3)
                        {
                            switch (target_structure.charAt(2))
                            {
                                case '0' ->
                                {
                                    return pathToHelper("R9", board_state, path, pathLength);
                                }
                                case '1' ->
                                {
                                    return pathToHelper("R10", board_state, path, pathLength);
                                }
                                case '2' ->
                                {
                                    return pathToHelper("R7", board_state, path, pathLength);
                                }
                                case '3' ->
                                {
                                    return pathToHelper("R12", board_state, path, pathLength);
                                }
                                case '4' ->
                                {
                                    return pathToHelper("R13", board_state, path, pathLength);
                                }
                                case '5' ->
                                {
                                    return pathToHelper("R14", board_state, path, pathLength);
                                }
                            }
                        }
                    }
                    case '2' ->
                    {
                        if (target_structure.length() == 2)
                        {
                            return pathToHelper("R0", board_state, path, pathLength);
                        }
                    }
                    case '3' ->
                    {
                        if (target_structure.length() == 2)
                        {
                            return pathToHelper("R2", board_state, path, pathLength);
                        }
                    }
                    case '4', '5' ->
                    {
                        if (target_structure.length() == 2)
                        {
                            return pathToHelper("R3", board_state, path, pathLength);
                        }
                    }
                    case '6' ->
                    {
                        if (target_structure.length() == 2)
                        {
                            return pathToHelper("R5", board_state, path, pathLength);
                        }
                    }
                    case '7' ->
                    {
                        if (target_structure.length() == 2)
                        {
                            return pathToHelper("R6", board_state, path, pathLength);
                        }
                    }
                    case '8' ->
                    {
                        if (target_structure.length() == 2)
                        {
                            return pathToHelper("R7", board_state, path, pathLength);
                        }
                    }
                    case '9' ->
                    {
                        if (target_structure.length() == 2)
                        {
                            return pathToHelper("R8", board_state, path, pathLength);
                        }
                    }
                }
            }

            case 'S' ->
            {
                if (target_structure.length() == 2)
                {
                    switch (target_structure.charAt(1))
                    {
                        case '3' ->
                        {
                            return alreadyBuilt;
                        }
                        case '4' ->
                        {
                            return pathToHelper("R2", board_state, path, pathLength);
                        }
                        case '5' ->
                        {
                            return pathToHelper("R5", board_state, path, pathLength);
                        }
                        case '7' ->
                        {
                            return pathToHelper("R7", board_state, path, pathLength);
                        }
                        case '9' ->
                        {
                            return pathToHelper("R9", board_state, path, pathLength);
                        }
                    }
                }
                else if (target_structure.length() == 3)
                {
                    if (target_structure.equals("S11"))
                    {
                        return pathToHelper("R11", board_state, path, pathLength);
                    }
                }
            }
            case 'C' ->
            {
                if (target_structure.length() == 2)
                {
                    if (target_structure.equals("C7"))
                    {
                        return pathToHelper("R1", board_state, path, pathLength);
                    }
                }
                else if (target_structure.length() == 3)
                {
                    switch (target_structure)
                    {
                        case "C12" ->
                        {
                            return pathToHelper("R4", board_state, path, pathLength);
                        }
                        case "C20" ->
                        {
                            return pathToHelper("R13", board_state, path, pathLength);
                        }
                        case "C30" ->
                        {
                            return pathToHelper("R15", board_state, path, pathLength);
                        }
                    }
                }
            }
            case 'J' ->
            {
                return alreadyBuilt;
            }
        }

        return alreadyBuilt; // This line is here only so this code will compile if you don't modify it.
    }

    public static String[] pathToHelper(String ts, String bs, String[] acc, int pl) {
        String[] path = acc;
        int pathLength = pl;
        switch (ts.charAt(1)) {
            case '0' -> {
                if (!bs.contains("R0")) {
                    System.arraycopy(path, 0, path, 1, pathLength);
                    path[0] = "R0";
                    pathLength++;
                }
            }
            case '1' -> {
                if (ts.length()==2) {
                    if (!bs.endsWith("R1") && !bs.contains("R1,")) {
                        System.arraycopy(path, 0, path, 1, pathLength);
                        path[0] = "R1";
                        pathLength++;
                    }
                    return pathToHelper("R0", bs, path, pathLength);
                } else {
                    switch (ts.charAt(2)) {
                        case '0' -> {
                            if (!bs.contains("R10")) {
                                System.arraycopy(path, 0, path, 1, pathLength);
                                path[0] = "R10";
                                pathLength++;
                            }
                            return pathToHelper("R9", bs, path, pathLength);
                        }
                        case '1' -> {
                            if (!bs.contains("R11")) {
                                System.arraycopy(path, 0, path, 1, pathLength);
                                path[0] = "R11";
                                pathLength++;
                            }
                            return pathToHelper("R10", bs, path, pathLength);
                        }
                        case '2' -> {
                            if (!bs.contains("R12")) {
                                System.arraycopy(path, 0, path, 1, pathLength);
                                path[0] = "R12";
                                pathLength++;
                            }
                            return pathToHelper("R7", bs, path, pathLength);
                        }
                        case '3' -> {
                            if (!bs.contains("R13")) {
                                System.arraycopy(path, 0, path, 1, pathLength);
                                path[0] = "R13";
                                pathLength++;
                            }
                            return pathToHelper("R12", bs, path, pathLength);
                        }
                        case '4' -> {
                            if (!bs.contains("R14")) {
                                System.arraycopy(path, 0, path, 1, pathLength);
                                path[0] = "R14";
                                pathLength++;
                            }
                            return pathToHelper("R13", bs, path, pathLength);
                        }
                        case '5' -> {
                            if (!bs.contains("R15")) {
                                System.arraycopy(path, 0, path, 1, pathLength);
                                path[0] = "R15";
                                pathLength++;
                            }
                            return pathToHelper("R14", bs, path, pathLength);
                        }
                    }
                }
            }
            case '2' -> {
                if (!bs.contains("R2")) {
                    System.arraycopy(path, 0, path, 1, pathLength);
                    path[0] = "R2";
                    pathLength++;
                }
                return pathToHelper("R0", bs, path, pathLength);
            }
            case '3' -> {
                if (!bs.contains("R3")) {
                    System.arraycopy(path, 0, path, 1, pathLength);
                    path[0] = "R3";
                    pathLength++;
                }
                return pathToHelper("R2", bs, path, pathLength);
            }
            case '4' -> {
                if (!bs.contains("R4")) {
                    System.arraycopy(path, 0, path, 1, pathLength);
                    path[0] = "R4";
                    pathLength++;
                }
                return pathToHelper("R3", bs, path, pathLength);
            }
            case '5' -> {
                if (!bs.contains("R5")) {
                    System.arraycopy(path, 0, path, 1, pathLength);
                    path[0] = "R5";
                    pathLength++;
                }
                return pathToHelper("R3", bs, path, pathLength);
            }
            case '6' -> {
                if (!bs.contains("R6")) {
                    System.arraycopy(path, 0, path, 1, pathLength);
                    path[0] = "R6";
                    pathLength++;
                }
                return pathToHelper("R5", bs, path, pathLength);
            }
            case '7' -> {
                if (!bs.contains("R7")) {
                    System.arraycopy(path, 0, path, 1, pathLength);
                    path[0] = "R7";
                    pathLength++;
                }
                return pathToHelper("R6", bs, path, pathLength);
            }
            case '8' -> {
                if (!bs.contains("R8")) {
                    System.arraycopy(path, 0, path, 1, pathLength);
                    path[0] = "R8";
                    pathLength++;
                }
                return pathToHelper("R7", bs, path, pathLength);
            }
            case '9' -> {
                if (!bs.contains("R9")) {
                    System.arraycopy(path, 0, path, 1, pathLength);
                    path[0] = "R9";
                    pathLength++;
                }
                return pathToHelper("R8", bs, path, pathLength);
            }
        }
        String[] pathFinal = new String[pathLength];
        System.arraycopy(path,0,pathFinal,0,pathLength);
        return pathFinal;
    }


    /**
     * Generate a plan (sequence of player actions) to build the target
     * structure from the given board and resource state. The plan may
     * include trades and swaps, as well as building other structures if
     * needed to reach the target structure or to satisfy the build order
     * constraints.
     *
     * However, the plan must not have redundant actions. This means it
     * must not build any other structure that is not necessary to meet
     * the building constraints for the target structure, and it must not
     * trade or swap for resources if those resources are not needed.
     *
     * If there is no valid build plan for the target structure from the
     * specified state, return null.
     *
     * @param target_structure: The string representation of the structure
     *        to be built.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return An array of string representations of player actions. If
     *         there exists no valid build plan for the target structure,
     *         the method should return null.
     */
    // FIXME - Task 14 - Matthew
    public static String[] buildPlan(String target_structure,
                                     String board_state,
                                     int[] resource_state)
    {
        String[] path = pathTo(target_structure, board_state);
        if (path == null) {
            return null;
        }
        return new String[]{};
    }

    public static boolean checkResourcesWithTradeButNoSwap(String structure, int[] resource_state)
    {
        int[] availableResources = new int[6];
        int[] missingResources = new int[6];
        // creating available resources array and missing resources array
        for (int i = 0; i < 6; i++)
        {
            int n = 0;
            switch (structure.charAt(0))
            {
                case 'R' ->
                {
                    n = resource_state[i] - roadResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
                case 'J' ->
                {
                    n = resource_state[i] - knightResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
                case 'C' ->
                {
                    n = resource_state[i] - cityResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;

                }
                case 'S' ->
                {
                    n = resource_state[i] - settlementResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
            }
        }
        return (Math.abs(intSumArray(missingResources)) * 2 <= availableResources[5]);
    }


    public static void main(String[] args){

    }

}
