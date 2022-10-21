package comp1110.ass2;

import java.util.Arrays;

import static comp1110.ass2.Main.CatanDice.*;
import static comp1110.ass2.Main.Constants.*;
import static java.lang.Character.getNumericValue;




public class Task14 {

    public static int[] updateResourceState(String actionThisIteration, int[] resource_state)
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

    public static int greatestCurrentBuildingsFinder2(String board_state5) {
        String[] boardArray1 = stringToArrayOfString(board_state5);
        int greatestCurrentKnight1 = 0;
        for (String structure : boardArray1) {
            if ((structure.charAt(0) == 'J'
                    && getNumericValue(structure.charAt(1)) > greatestCurrentKnight1)
                    || (structure.charAt(0) == 'K'
                    && getNumericValue(structure.charAt(1)) > greatestCurrentKnight1)) {
                greatestCurrentKnight1 = getNumericValue(structure.charAt(1));
            }
        }
        System.out.println("The knight with the highest point value is: " + greatestCurrentKnight1);
        return greatestCurrentKnight1;
    }

    static String[] stringToArrayOfString(String board_state5) {
        return board_state5.split(",");
    }

    public static int[] knightsOnBoard(String board_state5)
    {
        String[] boardArray1 = stringToArrayOfString(board_state5);
        int[] knightsOnBoardOutput = {0, 0, 0, 0, 0, 0};
        for (String s : boardArray1)
            if (s.charAt(0) == 'J') knightsOnBoardOutput[Integer.parseInt(String.valueOf(s.charAt(1))) - 1] = 1;
        System.out.println(Arrays.toString(knightsOnBoardOutput));
        return knightsOnBoardOutput;
    }

    public static String[] buildPlan(String target_Structure, String boardState, int[] resources) {
        int greatestCurrentKnight = greatestCurrentBuildingsFinder2(boardState);
        if (!checkResourcesWithTradeAndSwap(target_Structure, boardState, resources)) {
            System.out.println("Build Plan checkResourcesWithTradeAndSwap has returned false, returning null");
            return null;
        }
        if (target_Structure.charAt(0) == 'R') {
            if (pathTo(target_Structure, boardState).length >= 4) {
                System.out.println("Build Plan pathTo too long for case road - returning null");
                return null;
            }
        }
        if (target_Structure.charAt(0) == 'S') {
            if (pathTo(target_Structure, boardState).length >= 2) {
                System.out.println("Build Plan pathTo too long for case settlement - returning null");
                return null;
            }
        }
        if (target_Structure.charAt(0) == 'C') {
            if (pathTo(target_Structure, boardState).length >= 1) {
                System.out.println("Build Plan pathTo too long for case city - returning null");
                return null;
            }
        }
        if (target_Structure.charAt(0) == 'J') {
            if (getNumericValue(target_Structure.charAt(1)) - greatestCurrentKnight >= 3) {
                System.out.println("Build Plan pathTo too long for case Joker - returning null");
                return null;
            }
        }
        System.out.println("target structure is " + target_Structure);
        if (!checkBuildConstraints(target_Structure, boardState)) {
            System.out.println("check of build contraints returned false ");
            if (!nextBuild(target_Structure, boardState, resources)) {
                System.out.println("check of next build fails  - returning null");
                return null;
            }
        }

        System.out.println("line 85");
        if (checkBuildConstraints(target_Structure, boardState) && checkResources(target_Structure, resources)) {
            System.out.println("building structure " + target_Structure);
            return new String[]{"build " + target_Structure};
        }
        if (checkBuildConstraints(target_Structure, boardState) && checkResourcesWithTradeButNoSwap(target_Structure, resources)) {
            System.out.println("line 91 - doing this thing " + Arrays.toString(stringToArrayOfString(tradesCalculator(boardState, target_Structure, resources).append("build ").append(target_Structure).toString())));
            return stringToArrayOfString(tradesCalculator(boardState, target_Structure, resources).append("build " + target_Structure).toString());
        }
        String[] tempPath = pathTo(target_Structure, boardState);
        String structureToBuildFirstPath = "";
        String structureToBuildSecondPath;
        if (tempPath.length >= 1) structureToBuildFirstPath = tempPath[0];
        if (tempPath.length >= 2) {
            structureToBuildSecondPath = tempPath[1];


            // note in below case, should switch check resources with tradeCalculator or similar
            if (checkResources(pathTo(target_Structure, boardState)[0], resources) && checkBuildConstraints(pathTo(target_Structure, boardState)[0], boardState)) {
                System.out.println("It worked out structure to build first path: " + structureToBuildFirstPath);
                if (target_Structure.charAt(0) == 'S' && checkBuildConstraints(structureToBuildFirstPath, boardState)
                        && checkResources(structureToBuildFirstPath, resources)
                        && checkResources(target_Structure,
                        updateResourceState("build " + structureToBuildFirstPath, resources))) {
                    System.out.println("here too");
                    return new String[]{
                            "build " + pathTo(target_Structure, boardState)[0],
                            "build " + target_Structure
                    };
                }
                if (target_Structure.charAt(0) == 'C'
                        && checkBuildConstraints(target_Structure, boardState)
                        && checkResources(target_Structure, resources)) {
                    return new String[]{
                            "build " + target_Structure
                    };
                }

                if (target_Structure.charAt(0) == 'R'
                        && checkBuildConstraints(target_Structure,
                        boardState + "," + structureToBuildFirstPath + "," + structureToBuildSecondPath)
                        && checkResources(target_Structure,
                        updateResourceState("build " + structureToBuildFirstPath,
                                updateResourceState("build " + structureToBuildSecondPath,
                                        resources)))
                        && checkResources(structureToBuildFirstPath,
                        updateResourceState("build " +
                                structureToBuildSecondPath, resources))

                        && checkResources(structureToBuildSecondPath, resources)
                        && checkBuildConstraints(structureToBuildFirstPath, boardState + "," + structureToBuildSecondPath)
                        && checkBuildConstraints(structureToBuildSecondPath, boardState)) {
                    return new String[]
                            {
                                    "build" + structureToBuildSecondPath + "," + "build " + structureToBuildFirstPath + "," + "build " + target_Structure
                            };
                }
            }
        }
        if (target_Structure.charAt(0) == 'J' && checkBuildConstraints("J" + ((Integer.parseInt(String.valueOf(target_Structure.charAt(1)))) - 1), boardState)
                && checkResources(("J" + (Integer.parseInt(String.valueOf(target_Structure.charAt(1)))-1)), resources)
                && checkBuildConstraints(target_Structure, boardState + "," + "J" + ((Integer.parseInt(String.valueOf(target_Structure.charAt(1)))) - 1))
                && checkResources(target_Structure, updateResourceState("build " + "J" + ((Integer.parseInt(String.valueOf(target_Structure.charAt(1)))) - 1) , resources))) {
            System.out.println("here too for joker");
            return new String[]{
                    "build " + "J" + ((Integer.parseInt(String.valueOf(target_Structure.charAt(1)))) - 1),
                    "build " + target_Structure
            };
        }
        int[] resourceCostArray = resourcesRequired(target_Structure, resources);
        int knightsToResourcesWeHaveComparison = intSumArray(resourceCostArray) - intSumArray(knightsOnBoard(boardState));
        String actionPlanner = "";
        if (checkResourcesWithTradeAndSwap(target_Structure, boardState, resources)) {
            // case where knight swaps and no gold trades are mandatory
            if (knightsToResourcesWeHaveComparison == 0) {
                String[] boardArray = stringToArrayOfString(boardState);
                String[] knightsAvailable = new String[6];
                // finding available knights
                for (String s : boardArray) {
                    if (s.charAt(0) == 'J') {
                        knightsAvailable[Integer.parseInt(s.substring(1)) - 1] = s;
                    }
                }
                int[] availableResources = new int[6];
                int[] missingResources = new int[6];

                // creating available resources array and missing resources array
                for (int i = 0; i < 6; i++) {
                    int n = 0;
                    switch (target_Structure.charAt(0)) {
                        case 'R' -> {
                            n = resources[i] - costFinder("R1")[i];
                            if (n < 0) missingResources[i] = n;
                            else availableResources[i] = n;
                        }
                        case 'J' -> {
                            n = resources[i] - costFinder("J")[i];
                            if (n < 0) missingResources[i] = n;
                            else availableResources[i] = n;
                        }
                        case 'C' -> {
                            n = resources[i] - costFinder("C7")[i];
                            if (n < 0) missingResources[i] = n;
                            else availableResources[i] = n;

                        }
                        case 'S' -> {
                            n = resources[i] - costFinder("S1")[i];
                            if (n < 0) missingResources[i] = n;
                            else availableResources[i] = n;
                        }
                    }
                }
                System.out.println(Arrays.toString(knightsAvailable));
                System.out.println(Arrays.toString(availableResources));
                System.out.println(Arrays.toString(missingResources));
                int[] availableClone = availableResources.clone();
                int[] missingClone = missingResources.clone();

                // iterate through the first 5 resources (the ones we need for building), and find all
                // possible ways to get them above zero
                for (int i = 0; i < 5; i++) {
                    if (missingResources[i] < 0) {
                        if (knightsAvailable[i] != null) {
                            // we can perform a swap with the knight, so we want to update the arrays accordingly
                            // find the first available resource that we can swap with
                            for (int j = 0; j < 6; j++) {
                                if (availableResources[j] > 0) {
                                    System.out.println("checking in here for j");
                                    // we can swap with this resource
                                    availableResources[j]--;
                                    missingResources[i]++;
                                    knightsAvailable[i] = null;
                                    if (missingResources[i] < 0)
                                        i--; // we want to redo this iteration of the loop and see if
                                    // there are other ways to get the resources we need
                                    break;
                                }
                            }
                        } else if (knightsAvailable[5] != null) {
                            System.out.println("checking in here for  if it made it");
                            // we can perform the swap with the wildcard knight
                            // find the first available resource that we can swap with
                            for (int j = 0; j < 6; j++) {
                                if (availableResources[j] > 0) {
                                    // we can swap with this resource
                                    availableResources[j]--;
                                    missingResources[i]++;
                                    knightsAvailable[5] = null;
                                    if (missingResources[i] < 0)
                                        i--; // we want to redo this iteration of the loop and see if
                                    // there are other ways to get the resources we need

                                    break;
                                }
                            }
                        }
                    }
                }
                System.out.println("Matt is really really dumb");
                for (int i = 0; i < intSumArray(knightsOnBoard(boardState)); i++) {
                    int resourceUsed = -1;
                    int resourceGained = -1;
                    for (int j = 0; j < availableClone.length; j++) {
                        if (availableClone[j] > 0) {
                            resourceUsed = j;
                            availableClone[j]--;
                            break;
                        }
                    }

                    for (int k = 0; k < missingClone.length; k++) {
                        if (missingClone[k] < 0) {
                            resourceGained = k;
                            missingClone[k]++;
                            break;
                        }

                    }

                    actionPlanner += "swap " + resourceUsed + " " + resourceGained + ",";
                }

                return stringToArrayOfString(actionPlanner);


                // case where knight swaps and gold trades are mandatory

                // case where both are required in a specific arrangement s.t. gold is left for knights
//                return null;


            }


        }
        System.out.println("Matt is really really really dumb");
        return null; // this is a placeholder

    }

    public static int[] resourcesRequired(String structure9, int[] resources9) {
        int[] costs = costFinder(structure9);
        System.out.println(new int[]{resources9[0] - costs[0],
                resources9[1] - costs[1],
                resources9[2] - costs[2],
                resources9[3] - costs[3],
                resources9[4] - costs[4],
                resources9[5] - costs[5]});
        return new int[] {resources9[0] - costs[0],
                resources9[1] - costs[1],
                resources9[2] - costs[2],
                resources9[3] - costs[3],
                resources9[4] - costs[4],
                resources9[5] - costs[5]};
    }

    public static boolean checkResourcesWithToTradeButNoSwap(String structure, String boardState, int[] resources) {
        int[] availableResources = new int[6];
        int[] missingResources = new int[6];
        // creating available resources array and missing resources array
        for (int i = 0; i < 6; i++) {
            int n = 0;
            switch (structure.charAt(0)) {
                case 'R' -> {
                    n = resources[i] - costFinder("R1")[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
                case 'J' -> {
                    n = resources[i] - costFinder("J1")[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
                case 'C' -> {
                    n = resources[i] - costFinder("C1")[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;

                }
                case 'S' -> {
                    n = resources[i] - costFinder("S4")[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
            }
        }
        return (Math.abs(intSumArray(missingResources)) * 2 <= availableResources[5]);
    }

    public static int[] costFinder(String structure) {
        if (structure.charAt(0) == 'S') {
            return new int[]{0, 1, 1, 1, 1, 0};
        } else if (structure.charAt(0) == 'R') {
            return new int[]{0, 0, 1, 1, 0, 0};
        } else if (structure.charAt(0) == 'J') {
            return new int[]{1, 1, 1, 0, 0, 0};
        } else {
            return new int[]{3, 2, 0, 0, 0, 0};
        }
    }

    public static StringBuilder tradesCalculator(String boardState10, String structure, int[] resources1110) {
        System.out.println(resources1110);
        int[] neededResources = resourcesRequired(structure, resources1110);
        System.out.println("needed resources" + neededResources);
        int usedGold1 = 0;
        int usedGold2 = 0;
        int usedGold3 = 0;
        int usedGold4 = 0;
        int usedGold5 = 0;
        int usedGoldPermanent1 = 0;
        int usedGoldPermanent2 = 0;
        int usedGoldPermanent3 = 0;
        int usedGoldPermanent4 = 0;
        int usedGoldPermanent5 = 0;
        System.out.println(resources1110);
        while (resources1110[5] > 1) {
//            System.out.println("While resources10[5] > 1");
            System.out.println("resources10[5] = " + resources1110[5]);
            if (neededResources[0] > 0) {
                System.out.println(1);
                neededResources[0]++;
                resources1110[5]--;
                resources1110[5]--;
                usedGold1++;
                usedGoldPermanent1++;
            } else if (neededResources[1] > 0) {
                System.out.println(2);
                neededResources[1]++;
                resources1110[5]--;
                resources1110[5]--;
                usedGold2++;
                usedGoldPermanent2++;
            } else if (neededResources[2] > 0) {
                System.out.println(3);
                neededResources[2]++;
                resources1110[5]--;
                resources1110[5]--;
                usedGold3++;
                usedGoldPermanent3++;
            } else if (neededResources[3] > 0) {
                System.out.println(4);
                neededResources[3]++;
                resources1110[5]--;
                resources1110[5]--;
                usedGold4++;
                usedGoldPermanent4++;
            } else if (neededResources[4] > 0) {
                System.out.println(5);
                neededResources[4]++;
                resources1110[5]--;
                resources1110[5]--;
                usedGold5++;
                usedGoldPermanent5++;
            }
        }
        StringBuilder stringOfSwaps = new StringBuilder();
        while (usedGold1 > 0) {
            System.out.println("Gold 1 while loop");
            stringOfSwaps.append("trade ").append(usedGold1).append(",");
            usedGold1--;
        }
        while (usedGold2 > 0) {
            System.out.println("Gold 2 while loop");
            stringOfSwaps.append("trade ").append(usedGold2).append(",");
            usedGold2--;
        }
        while (usedGold3 > 0) {
            System.out.println("Gold 3 while loop");
            stringOfSwaps.append("trade ").append(usedGold3).append(",");
            usedGold3--;
        }
        while (usedGold4 > 0) {
            System.out.println("Gold 4 while loop");
            stringOfSwaps.append("trade ").append(usedGold4).append(",");
            usedGold4--;
        }
        while (usedGold5 > 0) {
            System.out.println("Gold 5 while loop");
            stringOfSwaps.append("trade ").append(usedGold5).append(",");
            usedGold5--;
        }
        int[] newResourceTotals = {resources1110[0] + usedGoldPermanent1,
                resources1110[1] + usedGoldPermanent2,
                resources1110[2] + usedGoldPermanent3,
                resources1110[3] + usedGoldPermanent4,
                resources1110[4] + usedGoldPermanent5,
                resources1110[5] - 2 * (usedGoldPermanent1 + usedGoldPermanent2 + usedGoldPermanent3 + usedGoldPermanent4 + usedGoldPermanent5)};
        return stringOfSwaps;
    }

    public static int intSumArray(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }

    public static boolean checkResourcesWithTradeButNoSwap(String structure, int[] resource_state) {
        System.out.println("checkResourcesWithTradeButNoSwap made it to here especially for testSimple");
        int[] availableResources = new int[6];
        int[] missingResources = new int[6];
        // creating available resources array and missing resources array
        for (int i = 0; i < 6; i++) {
            int n = 0;
            switch (structure.charAt(0)) {
                case 'R' -> {
                    n = resource_state[i] - roadResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
                case 'J' -> {
                    n = resource_state[i] - knightResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
                case 'C' -> {
                    n = resource_state[i] - cityResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;

                }
                case 'S' -> {
                    n = resource_state[i] - settlementResources[i];
                    if (n < 0) missingResources[i] = n;
                    else availableResources[i] = n;
                }
            }
        }
        return (Math.abs(intSumArray(missingResources)) * 2 <= availableResources[5]);
    }


    public static Boolean nextBuild(String target_Structure1,String boardState111,int[] resources100) {
        String[] pathToTarget = pathTo(target_Structure1, boardState111);
        if (!checkBuildConstraints(target_Structure1,boardState111)){
            if (pathToTarget.length == 0)
            {return checkResourcesWithTradeAndSwap(target_Structure1, boardState111, resources100);//end of the recursion
            }
            else if (!checkResourcesWithTradeAndSwap(target_Structure1, boardState111, resources100)) {
                return false;
            } else nextBuild(target_Structure1,
                    boardState111 + pathToTarget[0],
                    updateResourceState(("build " + pathToTarget[0]),
                            resources100));

            return checkResourcesWithTradeAndSwap(target_Structure1, boardState111, resources100);
        }

        return true;
    }

    public String settlementPrevious (String settlement) {
        switch (settlement) {
            case "S3" -> {
                return "";
            }
            case "S4" -> {
                return "S3";
            }
            case "S5" -> {
                return "S4";
            }
            case "S7" -> {
                return "S5";
            }
            case "S9" -> {
                return "S7";
            }
            case "S11" -> {
                return "S9";
            }
        }
        return "";
    }
}


