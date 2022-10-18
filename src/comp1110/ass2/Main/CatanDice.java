package comp1110.ass2.Main;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

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
                                                String board_state) {
        BuildBuilding buildings = new BuildBuilding();

        // deploy the status of the building
        if (Objects.equals(board_state, "")) {
            return true;
        }
        String[] states = board_state.split(",");
        for (String state : states) {
            char build_type = state.charAt(0);
            int length = state.length() - 1;
            char[] build_no = new char[length];
            state.getChars(1, length + 1, build_no, 0);
            String build_no_str = new String(build_no);
            Integer build_no_int = Integer.valueOf(build_no_str);

//            if (build_type == 'R'){
//                buildings.roads.get(build_no_int).setStatus(true);
//            } else if (build_type == 'S') {
//                buildings.settlements.get(build_no_int).setStatus(true);
//            } else if (build_type == 'C') {
//                buildings.cities.get(build_no_int).setStatus(true);
//            } else if (build_type == 'J') {
//                buildings.knights.get(build_no_int).setStatus(true);
//            } else if (build_type == 'K') {
//                buildings.knights.get(build_no_int).setStatus(true);
//                buildings.knights.get(build_no_int).setDisposableStatus(true);
//            } else{
//                System.out.println("Unexpected type");
//            }
            switch (build_type) {
                case 'R' -> buildings.roads.get(build_no_int).setStatus(true);
                case 'S' -> buildings.settlements.get(build_no_int).setStatus(true);
                case 'C' -> buildings.cities.get(build_no_int).setStatus(true);
                case 'J' -> buildings.knights.get(build_no_int).setStatus(true);
                case 'K' -> {
                    buildings.knights.get(build_no_int).setStatus(true);
                    buildings.knights.get(build_no_int).setDisposableStatus(true);
                }
                default -> System.out.println("Unexpected type");
            }
        }
        char build_type = structure.charAt(0);
        int length = structure.length() - 1;
        char[] build_no = new char[length];
        structure.getChars(1, length + 1, build_no, 0);
        String build_no_str = new String(build_no);
        Integer build_no_int = Integer.valueOf(build_no_str);
//        if (build_type == 'R') {
//            return buildings.roads.get(build_no_int).isBuildingAssess();
//        } else if (build_type == 'S') {
//            return buildings.settlements.get(build_no_int).isBuildingAssess();
//        } else if (build_type == 'C') {
//            return buildings.cities.get(build_no_int).isBuildingAssess();
//        } else if (build_type == 'J') {
//            return buildings.knights.get(build_no_int).isBuildingAssess();
//        } else if (build_type == 'K') {
//            return buildings.knights.get(build_no_int).isBuildingAssess();
//        } else {
//            System.out.println("Unexpected type");
//        }
        switch (build_type)
        {
            case 'R' ->
            {
                return buildings.roads.get(build_no_int).isBuildingAssess();
            }
            case 'S' ->
            {
                return buildings.settlements.get(build_no_int).isBuildingAssess();
            }
            case 'C' ->
            {
                return buildings.cities.get(build_no_int).isBuildingAssess();
            }
            case 'J', 'K' ->
            {
                return buildings.knights.get(build_no_int).isBuildingAssess();
            }
            default -> System.out.println("Unexpected type");
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
                                                         int[] resource_state) {
        return false; // FIXME: Task #12 - Matthew
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
                    if (!checkResources(structure, resource_state)) return false;
                    // otherwise ensure the building can be built
                    else if (!checkBuildConstraints(structure, board_state)) return false;
                }
                case 't' ->
                {
                    return resource_state[resource_state.length - 1] >= 2;
                }
                case 's' ->
                {
                    String[] actionSplit = action.split(" ");
                    String knightResource = actionSplit[1].replace(",", ""); // not sure if this is necessary
                    try {
                        int knightID = Integer.parseInt(knightResource) + 1;
                        if (resource_state[Integer.parseInt(knightResource)] < 1) return false;
                        ArrayList<Integer> knightsBuilt = new ArrayList<>();
                        for (String s : board_state.split(","))
                        {
                            if (s.charAt(0) == 'J') {
                                knightsBuilt.add(Integer.parseInt(s.substring(1)));
                            }
                        }
                        if (!knightsBuilt.contains(knightID)) return false;
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Invalid resource ID");
                        return false;
                    }
                }
            }
        }

        return true; // FIXME: Task #9 - Matthew
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
        return false; // FIXME: Task #11 - Jingru
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
        return result; // FIXME: Task #13 - Yungzhong
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
        return null; // FIXME: Task #14 - Matthew
    }

    public static void main(String[] args) {

    }

}
