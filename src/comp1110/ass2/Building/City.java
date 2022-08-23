package comp1110.ass2.Building;

public class City extends Building implements Assess {
    public static Road adjacentRoad;
    public static City lastCity;

    public City(int point, int[] sourceRequired, Road adjacentRoad, City lastCity) {
        super(point, sourceRequired);
        this.adjacentRoad = adjacentRoad;
        this.lastCity = lastCity;
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

