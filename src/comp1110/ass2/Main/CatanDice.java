package comp1110.ass2.Main;

import java.util.*;

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
//        BuildBuilding buildings = new BuildBuilding();
//
//        // deploy the status of the building
//        if (Objects.equals(board_state, "")) {
//            return true;
//        }
//        String[] states = board_state.split(",");
//        for (String state : states) {
//            char build_type = state.charAt(0);
//            int length = state.length() - 1;
//            char[] build_no = new char[length];
//            state.getChars(1, length + 1, build_no, 0);
//            String build_no_str = new String(build_no);
//            Integer build_no_int = Integer.valueOf(build_no_str);
//
////            if (build_type == 'R'){
////                buildings.roads.get(build_no_int).setStatus(true);
////            } else if (build_type == 'S') {
////                buildings.settlements.get(build_no_int).setStatus(true);
////            } else if (build_type == 'C') {
////                buildings.cities.get(build_no_int).setStatus(true);
////            } else if (build_type == 'J') {
////                buildings.knights.get(build_no_int).setStatus(true);
////            } else if (build_type == 'K') {
////                buildings.knights.get(build_no_int).setStatus(true);
////                buildings.knights.get(build_no_int).setDisposableStatus(true);
////            } else{
////                System.out.println("Unexpected type");
////            }
//            switch (build_type) {
//                case 'R' -> buildings.roads.get(build_no_int).setStatus(true);
//                case 'S' -> buildings.settlements.get(build_no_int).setStatus(true);
//                case 'C' -> buildings.cities.get(build_no_int).setStatus(true);
//                case 'J' -> buildings.knights.get(build_no_int).setStatus(true);
//                case 'K' -> {
//                    buildings.knights.get(build_no_int).setStatus(true);
//                    buildings.knights.get(build_no_int).setDisposableStatus(true);
//                }
//                default -> System.out.println("Unexpected type");
//            }
//        }
//        char build_type = structure.charAt(0);
//        int length = structure.length() - 1;
//        char[] build_no = new char[length];
//        structure.getChars(1, length + 1, build_no, 0);
//        String build_no_str = new String(build_no);
//        Integer build_no_int = Integer.valueOf(build_no_str);
////        if (build_type == 'R') {
////            return buildings.roads.get(build_no_int).isBuildingAssess();
////        } else if (build_type == 'S') {
////            return buildings.settlements.get(build_no_int).isBuildingAssess();
////        } else if (build_type == 'C') {
////            return buildings.cities.get(build_no_int).isBuildingAssess();
////        } else if (build_type == 'J') {
////            return buildings.knights.get(build_no_int).isBuildingAssess();
////        } else if (build_type == 'K') {
////            return buildings.knights.get(build_no_int).isBuildingAssess();
////        } else {
////            System.out.println("Unexpected type");
////        }
//        switch (build_type)
//        {
//            case 'R' ->
//            {
//                return buildings.roads.get(build_no_int).isBuildingAssess();
//            }
//            case 'S' ->
//            {
//                return buildings.settlements.get(build_no_int).isBuildingAssess();
//            }
//            case 'C' ->
//            {
//                return buildings.cities.get(build_no_int).isBuildingAssess();
//            }
//            case 'J', 'K' ->
//            {
//                return buildings.knights.get(build_no_int).isBuildingAssess();
//            }
//            default -> System.out.println("Unexpected type");
//        }
//        return false;
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
                                                         int[] resource_state) throws IllegalArgumentException {
        int[] resourcesAfterBuild;
        if (checkResources(structure, resource_state))
            return true; // if the basic check works then we don't need to do anything else
            // first we need to check which trades and swaps can be performed and store them all in a list
        else {
            String struct = String.valueOf(structure.charAt(0));
            assert struct.equals("J") || struct.equals("R") || struct.equals("C") || struct.equals("S"); // if the structure is not one of these then we have a problem
            int[] availableResources = resource_state.clone();
            // for the code to get here we know that the basic check failed, thus the input resource state is missing some element.
            // we need to work out what is missing, and then check if we can get it from a trade or swap
            // we need to check what knights are available to perform swaps
            // we need to check how much gold we have available to trade for gold
            // this array will have some negative values in it, which will be the amount of resources we need to get from trades or swaps

            // this is what is missing
            resourcesAfterBuild = new int[6];
            for (int i = 0; i < 6; i++) {
                switch (struct) {
                    case "R" -> resourcesAfterBuild[i] = availableResources[i] - roadResources[i];
                    case "J" -> resourcesAfterBuild[i] = availableResources[i] - knightResources[i];
                    case "C" -> resourcesAfterBuild[i] = availableResources[i] - cityResources[i];
                    case "S" -> resourcesAfterBuild[i] = availableResources[i] - settlementResources[i];
                }
                ;
            }

            // this is the gold available to trade for resources - swapping with gold should be prioritised over using
            // a knight to trade for resources, however as we are just doing validation that the build is possible
            // we don't need to worry about that, and we can use the knights first and then see if we have enough gold
            // to complete the build
            int goldAvailable = resource_state[GOLD_ID];

            // this is the knights available to trade with
            ArrayList<Integer> knightsList = new ArrayList<>();
            String[] boardArray = board_state.split(",");
            for (String s : boardArray)
            {
                if (s.charAt(0) == 'J')
                {
                    try {
                        knightsList.add(Integer.parseInt(s.substring(1)) - 1); // this will be the index of the resource we can get from the knight (apart from 5 which we can use for anything)
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing knight number");
                        return false;
                    }
                }
            }
            // for each required resource, we first want to see if we can get it from the specific knight for that
            // resource (by trading using any resource in the available resources array that has a value greater than one)
            // if we can't get it from the specific knight then we want to see if we can get it from the wildcard knight
            // if we can't get if from that knight then we want to see if we can get it from gold (if we can we add one
            // to the resource count and subtract two from the gold count)

            for (int pos = 0; pos < availableResources.length - 1; pos++)
            {
                int timesRound = 0; // this is to make sure we don't get stuck in an infinite loop
                if (availableResources[pos] >= 0) continue;
                else
                {
                    // first see if we have the specific knight for that resource
                    if (knightsList.contains(pos))
                    {

                        timesRound++;
                    }
                    // otherwise see if we have the wildcard knight
                    else if (knightsList.contains(5))
                    {

                        timesRound++;
                    }
                    // otherwise see if we can trade for gold
                    else if (availableResources[GOLD_ID] >= 2)
                    {
                        availableResources[GOLD_ID] -= 2;
                        availableResources[pos] += 1;
                        timesRound ++;
                    }
                    // maybe we need to loop back as we have done one of the above but we still need more resources
                    if (availableResources[pos] < 0 && timesRound < 5)
                    {
                        timesRound = 0;
                        pos = -1;
                    }
                    // end the cycle if we have done it too many times
                    else if (availableResources[pos] < 0 && timesRound >= 5)
                    {
                        return false;
                    }
                }
            }

        }
        return resourcesAfterBuild[0] >= 0 &&
                resourcesAfterBuild[1] >= 0 &&
                resourcesAfterBuild[2] >= 0 &&
                resourcesAfterBuild[3] >= 0 &&
                resourcesAfterBuild[4] >= 0 &&
                resourcesAfterBuild[5] >= 0; // FIXME: Task #12 - Matthew
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
    public static String[] pathTo(String target_structure,
                                  String board_state) {
        if (board_state.contains(target_structure)) {
            //create array string representation
            return new String[]{}; //return the empty array.
        }
        String[] path = {
                "S3,J1,R0",
                "R0,R1,J2,C7",
                "R0,R2,S4,R3", //String index in
                "R3,J3,R4,C12",
                "R3,R5,S5,R6,R7,S7",
                "S7,R12,R13,C20,R14,R15,C30",
                "S7,R8,J4,R9,S9,R10,J5,R11,J6,S11"
                };
        String point[] = board_state.split(","); //A value in stack memory = (connection) heap memory Wired question
        String result = path[0] + ",";
        String temp[] = result.split(",");
        String last = temp[temp.length - 1];
        for (int i = 1; i < path.length; i++) {
            if (path[i].contains(target_structure)) {
                result = result + path[i].substring(3) + ",";
                break;
            }
            if (path[i].startsWith(last)) {
                temp = path[i].split(",");
                System.out.println(path[i]);
                if (path[i+1].startsWith(temp[temp.length - 1])){
                    last = temp[temp.length - 1];
                    result = result + path[i].substring(3) + ",";
                }
            }
        }
        result = result.substring(0, result.length()-1);
        String unbuilt = "";
        for (String j: result.split(","))
        { //break the result into several string
            if (!board_state.contains(j)){
                if(j.equals(target_structure)){
                    break;
                } else {
                    if (j.charAt(0) == 'R')
                    {
                        unbuilt = unbuilt + j + ","; // eg. R1,R2,R3,R4
                    }
                }
            }
        }
        if (unbuilt.length() == 0){
            return new String[]{}; // none array
        } else {
            return unbuilt.substring(0).split(",");
        }
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
    public static String[] buildPlan(String target_structure,
                                     String board_state,
                                     int[] resource_state) {
        System.out.println(target_structure);
        System.out.println(board_state);
        String[] path = pathTo(target_structure, board_state);
        return null; // FIXME: Task #14 - Matthew

    }

    public static void main(String[] args){

    }

}
