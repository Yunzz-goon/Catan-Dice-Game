package comp1110.ass2.Main;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static comp1110.ass2.Resource.Resource.*;

public class CatanDice {

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
        // FIXME: Task #6
        /*
        The order of the resources is (0) Ore, (1) Grain, (2) Wool, (3) Timber, (4) Bricks and (5) Gold.
        For example, the array { 1, 0, 1, 2, 0, 2 } indicates that the player has 1 Ore, 1 Wool, 2 Timber
        and 2 Gold available. Note that the total quantity of resources can vary.
         */
        Random random = new Random(6710);
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
        // the following code assumes that check resources has already been called (hence that the player does
        // have the resources to build the structure, as the resource_state argument isn't passed to this function)
        /* what are the build constraints?
         * The settlements, cities and knights need to have the settlement/city/knight with fewer points than it build first
         * For settlements, this means that the 12 point settlement must be built, then the 20 point one, then the 30 point on
         * For knights, this means the knight with the number one lower than the knight of this number needs to have been built
         * For cities, the 3 point comes first, then the 4, 5, 7, 9 and 11
         * Note that the first of each of these can be built provided the necessary roads / other previous items have been bult
         * Roads, Settlements and Cities must form a connected network, starting from the initial Road (which is already on the board)
         */

        if (!isBoardStateWellFormed(board_state)) return false;
        if (!isStateWellFormed(structure)) return false;
        String copyBoardState = board_state;
        copyBoardState += ",";
        copyBoardState += structure;
        String[] boardStateArray = copyBoardState.split(",");
        int length = boardStateArray.length;
        if (length == 1)
            return Objects.equals(boardStateArray[1], "C3") && Objects.equals(structure, "R0");
        else
        {
           // iterate through the board state array, ensuring that for each stage, all actions are correct,
            // including the last action we have added on
            for (int i = 0; i < length; i++)
            {
                // the array we have checked up too
                String[] tempArray = Arrays.copyOfRange(boardStateArray, 0, i);
                char referenceChar = tempArray[i].charAt(0);
                switch (referenceChar)
                {
                    case 'R' ->
                    {
                        // if the road is not well-formed, return false
                        if (!isRoadWellFormed(tempArray[i])) return false;
                        // if the road is not inline with the build constraints for a road, return false
                        if (!isRoadConnected(tempArray[i], tempArray)) return false;

                    }
                    case 'S' ->
                    {
                        // if the settlement is not well-formed, return false
                        if (!isSettlementWellFormed(tempArray[i])) return false;
                        // if the settlement is not inline with the build constraints for a settlement, return false
                        if (!isSettlementConnected(tempArray[i], tempArray)) return false;

                    }
                    case 'C' ->
                    {
                        // if the city is not well-formed, return false
                        if (!isCityWellFormed(tempArray[i])) return false;
                        // if the city is not inline with the build constraints for a city, return false
                        if (!isCityConnected(tempArray[i], tempArray)) return false;

                    }
                    case 'K' ->
                    {
                        // if the knight is not well-formed, return false
                        if (!isKnightWellFormed(tempArray[i])) return false;
                        // if the knight is not inline with the build constraints for a knight, return false
                        if (!isKnightConnected(tempArray[i], tempArray)) return false;

                    }
                    // i'm not 100% sure that this case is needed but better safe than sorry
                    case 'J' ->
                    {
                        // if the joker is not well-formed, return false
                        if (!isJokerWellFormed(tempArray[i])) return false;
                        // if the joker is not inline with the build constraints for a junction, return false
                        if (!isJokerConnected(tempArray[i], tempArray)) return false;

                    }
                    default ->
                    {
                        return false;
                    }
                }
            }
        }
        return true; // FIXME: Task #8
    }

    /*
     * these methods are for Jingru to implement - between them they should cover all of the cases
     * needed to validate that a structure is within the build constraints
     */
    // TODO - all of these methods need to be implemented - Jingru I'll leave this up to you
    public static boolean isRoadWellFormed(String road)
    {
        return false;
    }
    public static boolean isSettlementWellFormed(String settlement)
    {
        return false;
    }
    public static boolean isCityWellFormed(String city)
    {
        return false;
    }
    public static boolean isKnightWellFormed(String knight)
    {
        return false;
    }
    public static boolean isJokerWellFormed(String joker)
    {
        return false;
    }
    public static boolean isRoadConnected(String road, String[] boardStateArray)
    {
        return false;
    }
    public static boolean isSettlementConnected(String settlement, String[] boardStateArray)
    {
        return false;
    }
    public static boolean isCityConnected(String city, String[] boardStateArray)
    {
        return false;
    }
    public static boolean isKnightConnected(String knight, String[] boardStateArray)
    {
        return false;
    }
    // again i'm not sure if this method is 100% needed, but better safe than sorry, and it should
    // be very similar to isKnightConnected
    public static boolean isJokerConnected(String joint, String[] boardStateArray)
    {
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
                                                         int[] resource_state) {
        return false; // FIXME: Task #12
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
                                      int[] resource_state) {
        return false; // FIXME: Task #9
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
                                        int[] resource_state) {
        return false; // FIXME: Task #11
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
        String[] result = {};
        return result; // FIXME: Task #13
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
        return null; // FIXME: Task #14
    }

}
