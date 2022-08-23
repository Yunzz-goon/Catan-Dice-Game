package comp1110.ass2.Building;

public class City extends Building implements Assess {
    public static Road adjacentRoad;
    public static City lastCity;

    public City(int point, int[] source_required, Road adjacent_road, City last_city) {
        super(point, source_required);
        this.adjacentRoad = adjacent_road;
        this.lastCity = last_city;

    }


    /**
     * judge whether the city could access
     * @return the result of judging whether the Knight could access
     */
    @Override
    public boolean isBuildingAssess() {
        if (this.lastCity.getStatus() && adjacentRoad.getStatus()) {
            return true;
        } else {
            return false;
        }
    }
}

