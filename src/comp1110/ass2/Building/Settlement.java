package comp1110.ass2.Building;

public class Settlement extends Building implements Assess {
    public static Road adjacentRoad;
    public static Settlement lastSettlement;

    public Settlement(int point, Road adjacentRoad,
                      Settlement lastSettlement) {
        super(point);
        this.adjacentRoad = adjacentRoad;
        this.lastSettlement = lastSettlement;
    }

    /**
     * judge whether the settlement could access
     * @return the result of judging whether the Knight could access
     */
    @Override
    public boolean isBuildingAssess() {
        if (this.lastSettlement.getStatus() && adjacentRoad.getStatus()) {
            return true;
        } else {
            return false;
        }
    }
}