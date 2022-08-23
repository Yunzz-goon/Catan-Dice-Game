package comp1110.ass2;

public class Settlement extends Building implements Assess {
    public static Road adjacent_road;
    public static Settlement last_settlement;

    public Settlement(int point, int[] source_required, Road adjacent_road,
                      Settlement last_settlement) {
        super(point, source_required);
        this.adjacent_road = adjacent_road;
        this.last_settlement = last_settlement;
    }

    /**
     * judge whether the settlement could access
     * @return the result of juding whether the Knight could access
     */
    @Override
    public boolean isBuildingAssess() {
        if (this.last_settlement.getStatus() && adjacent_road.getStatus()) {
            return true;
        } else {
            return false;
        }
    }
}