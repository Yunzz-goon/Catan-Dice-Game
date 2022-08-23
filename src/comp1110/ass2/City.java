package comp1110.ass2;

public class City extends Building implements Assess {
    public static Road adjacent_road;
    public static City last_city;

    public City(int point, int[] source_required, Road adjacent_road, City last_city) {
        super(point, source_required);
        this.adjacent_road = adjacent_road;
        this.last_city = last_city;

    }


    /**
     * judge whether the city could access
     * @return the result of judging whether the Knight could access
     */
    @Override
    public boolean isBuildingAssess() {
        if (this.last_city.getStatus() && adjacent_road.getStatus()) {
            return true;
        } else {
            return false;
        }
    }
}

