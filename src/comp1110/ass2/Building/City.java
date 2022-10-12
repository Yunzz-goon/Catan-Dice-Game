package comp1110.ass2.Building;

public class City extends Building implements Assess {
    public Road adjacentRoad;
    public City lastCity;

    public City(int point, Road adjacentRoad, City lastCity) {
        super(point);
        this.adjacentRoad = adjacentRoad;
        this.lastCity = lastCity;
    }


    /**
     * judge whether the city could access
     * @return the result of judging whether the Knight could access
     */
    @Override
    public boolean isBuildingAssess() {
        if (this.lastCity == null && this.adjacentRoad.getStatus()){
            return true;
        }
        if (this.lastCity.getStatus() && this.adjacentRoad.getStatus()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public char toChar() {
        return 'C';
    }
}

