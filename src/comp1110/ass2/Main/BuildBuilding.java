package comp1110.ass2.Main;
import comp1110.ass2.Building.*;
import comp1110.ass2.Main.CatanDice;
import comp1110.ass2.Resource.PlayerResources;

import java.util.ArrayList;
import java.util.HashMap;

import static comp1110.ass2.Building.Knight.ExchangingResource.*;
import static comp1110.ass2.Main.CatanDice.checkBuildConstraints;
import static comp1110.ass2.Main.CatanDice.checkResources;
import static comp1110.ass2.Resource.PlayerResources.*;


public class BuildBuilding {
    /**
     *  Initialize the building state, the default building status is unbuilt.
     */

    public String borad_state = "";
    public ArrayList<Road> roads = new ArrayList<>();
    public HashMap<Integer, Settlement> settlements = new HashMap<Integer, Settlement>();
    public HashMap<Integer, City> cities = new HashMap<Integer, City>();
    public HashMap<Integer, Knight> knights = new HashMap<Integer, Knight>();

    public BuildBuilding(){
        // initialize roads
        Road road = new Road(1, null);
        roads.add(road);
        for (int i = 0; i < 16; i++){
            road = new Road(1, road);
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


    }

    /**
     * Build a building if current state satisfies the building constraints and resource constraints.
     * @param structure a desired to be built struture
     * @param resource_state the current resource state
     */
    public void buildBuilding(String structure, int[] resource_state){
        if (checkBuildConstraints(structure, borad_state) && (checkResources(structure, resource_state))) {
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
            if (build_type == 'R') {
                roads.get(build_no_int).setStatus(true);
            } else if (build_type == 'C') {
                cities.get(build_no_int).setStatus(true);
            } else if (build_type == 'S') {
                settlements.get(build_no_int).setStatus(true);
            } else if (build_type == 'K') {
                knights.get(build_no_int).setStatus(true);
            }
            PlayerResources.spendResources(resource_state, build_type);
            borad_state += "," + structure;
        }
    }

    public void knightUsed(Knight knight){
        knight.setDisposableStatus(true);
    }

}
