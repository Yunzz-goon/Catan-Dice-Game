package comp1110.ass2.Building;

public class Settlement extends Building implements Assess {
    public Road adjacentRoad;
    public Settlement lastSettlement;

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
        if (this.adjacentRoad == null){
            return true;
        }
        if (this.lastSettlement == null && adjacentRoad.getStatus()){
            return true;
        }
        if (this.lastSettlement.getStatus() && adjacentRoad.getStatus()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public char toChar() {
        return 'S';
    }

    public static final int[] settlementResources = {0, 1, 1, 1, 1, 0};
}