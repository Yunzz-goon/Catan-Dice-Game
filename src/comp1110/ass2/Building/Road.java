package comp1110.ass2.Building;

public class Road extends Building implements Assess {
    public static Road lastRoad;

    public Road(int point, int[] source_required, Road lastRoad) {
        super(point, source_required);
        this.lastRoad = lastRoad;
    }

    /**
     * judge whether the road could access
     * @return the result of judging whether the road could access
     */
    @Override
    public boolean isBuildingAssess(){
        if (this.lastRoad.buildingStatus){
            return true;
        }else{
            return false;
        }
    }





}
