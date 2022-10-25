package comp1110.ass2.Main;

import comp1110.ass2.Building.City;
import comp1110.ass2.Building.Knight;
import comp1110.ass2.Building.Road;
import comp1110.ass2.Building.Settlement;

import java.util.ArrayList;
import java.util.HashMap;

import static comp1110.ass2.Building.Knight.ExchangingResource.*;
import static comp1110.ass2.CatanDice.checkBuildConstraints;
import static comp1110.ass2.CatanDice.checkResources;


public class BuildBuilding
{
    /**
     *  Initialize the building state, the default building status is unbuilt.
     */

    public static String board_state = "";

    public ArrayList<Road> roads = new ArrayList<>();
    public HashMap<Integer, Settlement> settlements = new HashMap<Integer, Settlement>();
    public HashMap<Integer, City> cities = new HashMap<Integer, City>();
    public HashMap<Integer, Knight> knights = new HashMap<Integer, Knight>();

    public static HashMap<Character, int[]> resourceRequired = new HashMap<>();

    public BuildBuilding(){
        // initialize roads
        Road road = new Road(1, null);
        Road old_road = road;
        roads.add(road);
        for (int i = 0; i < 16; i++){
            old_road = road;
            road = new Road(1, old_road);
            roads.add(road);
        }

        // initialize settlements
//        ArrayList<Settlement> settlements = new ArrayList<>();
        Settlement settlement = new Settlement(3, null, null);
        settlements.put(settlement.getPoint(), settlement);
        settlement = new Settlement(4, roads.get(2), settlement);
        settlements.put(settlement.getPoint(), settlement);
        settlement = new Settlement(5, roads.get(5), settlement);
        settlements.put(settlement.getPoint(), settlement);
        settlement = new Settlement(7, roads.get(7), settlement);
        settlements.put(settlement.getPoint(), settlement);
        settlement = new Settlement(9, roads.get(9), settlement);
        settlements.put(settlement.getPoint(), settlement);
        settlement = new Settlement(11, roads.get(11), settlement);
        settlements.put(settlement.getPoint(), settlement);

        // initialize cities
//        ArrayList<City> cities = new ArrayList<>();

        City city = new City(7, roads.get(1), null);
        cities.put(city.getPoint(), city);
        city = new City(12, roads.get(4), city);
        cities.put(city.getPoint(), city);
        city = new City(20, roads.get(13), city);
        cities.put(city.getPoint(), city);
        city = new City(30, roads.get(15), city);
        cities.put(city.getPoint(), city);

        // initialize knight
//        ArrayList<Knight> knights = new ArrayList<>();
        Knight knight = new Knight(1, BRICK, null);
        knights.put(knight.getPoint(), knight);
        knight = new Knight(2, LUMBER, knight);
        knights.put(knight.getPoint(), knight);
        knight = new Knight(3, WOOL, knight);
        knights.put(knight.getPoint(), knight);
        knight = new Knight(4, GRAIN, knight);
        knights.put(knight.getPoint(), knight);
        knight = new Knight(5, ORE, knight);
        knights.put(knight.getPoint(), knight);
        knight = new Knight(6, UNDETERMINED, knight);
        knights.put(knight.getPoint(), knight);

        int[] resourceList;
        resourceList = new int[]{3, 4};
        resourceRequired.put('R', resourceList);
        resourceList = new int[]{0, 1, 2};
        resourceRequired.put('J', resourceList);
        resourceRequired.put('K', resourceList);
        resourceList = new int[]{1, 2, 3, 4};
        resourceRequired.put('S', resourceList);
        resourceList = new int[]{0, 0, 0, 1, 1};
        resourceRequired.put('C', resourceList);



        // (0) Ore, (1) Grain, (2) Wool, (3) Timber, (4) Bricks and (5) Gold.

    }

    private void spendResources(int[] resource_status, String structure){
        if (checkResources(structure, resource_status ) == false) {
            System.out.println("resource is not enough to build such a building");
            return;
        }
        Character build_type = structure.charAt(0);
        int[] resource_list = resourceRequired.get(build_type);
        for (int resource_index : resource_list){
            resource_status[resource_index] -= 1;
        }
    }

    /**
     * Build a building if current state satisfies the building constraints and resource constraints.
     * @param structure a desired to be built structure
     * @param resource_state the current resource state
     */
    public int buildBuilding(String structure, int[] resource_state){
        if (checkBuildConstraints(structure, board_state) && (checkResources(structure, resource_state))) {
            char build_type;
            int length;
            char[] build_no;
            Integer build_no_int;
            String[] states;
            String build_no_str;
            build_type = structure.charAt(0);
            length = structure.length() - 1;
            build_no = new char[length];
            structure.getChars(1, length + 1, build_no, 0);
            build_no_str = new String(build_no);
            build_no_int = Integer.valueOf(build_no_str);
            spendResources(resource_state, structure);
            if (board_state.equals("")){
                board_state +=  structure;
            }else{
                board_state +=  "," + structure;
            }
            System.out.println("current board state " + board_state);
            if (build_type == 'R') {
                roads.get(build_no_int).setStatus(true);
                return roads.get(build_no_int).getPoint();
            } else if (build_type == 'C') {
                cities.get(build_no_int).setStatus(true);
                return cities.get(build_no_int).getPoint();
            } else if (build_type == 'S') {
                settlements.get(build_no_int).setStatus(true);
                return settlements.get(build_no_int).getPoint();
            } else if (build_type == 'J') {
                knights.get(build_no_int).setStatus(true);
                return knights.get(build_no_int).getPoint();
            }

        } else{
            if (checkBuildConstraints(structure, board_state) ==  false){
                System.out.println("it can not be built due to building contraints");
                System.out.println("the board state now is"+ board_state);
            }
            else{
                System.out.println("it can not be built due to resource contraints");
            }

            return 0;
        }
        return 0;
    }

    public void knightUsed(Knight knight){
        knight.setDisposableStatus(true);
    }

}