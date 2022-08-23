package comp1110.ass2;

public class Road extends Building implements Assess {
    public static Road last_road;

    public Road(int point, int[] source_required, Road last_road) {
        super(point, source_required);
        this.last_road = last_road;
    }

    /**
     * judge whether the road could access
     * @return the result of judging whether the road could access
     */
    @Override
    public boolean isBuildingAssess(){
        if (this.last_road.building_status){
            return true;
        }else{
            return false;
        }
    }





}
