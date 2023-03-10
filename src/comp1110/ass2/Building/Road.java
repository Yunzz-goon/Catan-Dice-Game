package comp1110.ass2.Building;

public class Road extends Building implements Assess {
    public Road lastRoad;

    public Road(int point, Road lastRoad) {
        super(point);
        this.lastRoad = lastRoad;
    }



    /**
     * judge whether the road could access
     * @return the result of judging whether the road could access
     */
    @Override
    public boolean isBuildingAssess(){
        if (this.lastRoad == null){
            return true;
        }
        if (this.lastRoad.getStatus()){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public char toChar() {
        return 'R';
    }

    public static final int[] roadResources = {0, 0, 0, 1, 1, 0};






}
