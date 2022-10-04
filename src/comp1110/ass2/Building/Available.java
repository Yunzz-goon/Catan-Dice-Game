package comp1110.ass2.Building;

import comp1110.ass2.Resource.ResourceJoker;

import static comp1110.ass2.Resource.ResourceType.*;

public class Available
{
    public final static Road ROAD_0 = new Road(0, null);
    public final static Road ROAD_1 = new Road(1, ROAD_0);
    public final static Road ROAD_2 = new Road(2, ROAD_0);
    public final static Road ROAD_3 = new Road(3, ROAD_2);
    public final static Road ROAD_4 = new Road(4, ROAD_3);
    public final static Road ROAD_5 = new Road(5, ROAD_3);
    public final static Road ROAD_6 = new Road(6, ROAD_5);
    public final static Road ROAD_7 = new Road(7, ROAD_6);
    public final static Road ROAD_8 = new Road(8, ROAD_7);
    public final static Road ROAD_9 = new Road(9, ROAD_8);
    public final static Road ROAD_10 = new Road(10, ROAD_9);
    public final static Road ROAD_11 = new Road(11, ROAD_10);
    public final static Road ROAD_12 = new Road(12, ROAD_7);
    public final static Road ROAD_13 = new Road(13, ROAD_12);
    public final static Road ROAD_14 = new Road(14, ROAD_13);
    public final static Road ROAD_15 = new Road(15, ROAD_14);

    public final static ResourceJoker JOKER_1 = new ResourceJoker(ORE);
    public final static ResourceJoker JOKER_2 = new ResourceJoker(GRAIN);
    public final static ResourceJoker JOKER_3 = new ResourceJoker(WOOL);
    public final static ResourceJoker JOKER_4 = new ResourceJoker(LUMBER);
    public final static ResourceJoker JOKER_5 = new ResourceJoker(BRICK);
    public final static ResourceJoker JOKER_6 = new ResourceJoker(NULL);

    public final static City CITY_3 = new City(3, null, null);
    public final static City CITY_4 = new City(4, ROAD_2, CITY_3);
    public final static City CITY_5 = new City(5, ROAD_5, CITY_4);
    public final static City CITY_7 = new City(7, ROAD_7, CITY_5);
    public final static City CITY_9 = new City(9, ROAD_9, CITY_7);
    public final static City CITY_11 = new City(11, ROAD_11, CITY_9);

    public final static Settlement SETTLEMENT_7 = new Settlement(7, ROAD_1, null);
    public final static Settlement SETTLEMENT_12 = new Settlement(12, ROAD_4, SETTLEMENT_7);
    public final static Settlement SETTLEMENT_20 = new Settlement(20, ROAD_13, SETTLEMENT_12);
    public final static Settlement SETTLEMENT_30 = new Settlement(30, ROAD_15, SETTLEMENT_20);
}
