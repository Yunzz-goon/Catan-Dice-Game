package comp1110.ass2.Building;

public class Building {
    private static int point;
    private static boolean buildingStatus;
    private static int[] sourceRequired;
    public Building(int point) {
        this.point = point;
        this.buildingStatus = false;
    }


    /**
     * Set the status of the building
     * It is noticed that all building except the first road is initialized as unbuilt(false) and
     * when creating the first road we have to set the status of it
     * @param status the status that we want to set for the building
     */
    public void setStatus(boolean status){
        this.buildingStatus = status;
    }

    /**
     * get the status of the building
     * @return return the status of the building
     */
    public boolean getStatus(){
        return this.buildingStatus;
    }

    /**
     * get the point of the building
     * @return return the point of the building
     */
    public int getPoint(){
        return this.point;
    }


    /**
     * get the required source of building the building
     * @return return the required source of building the building
     */
    public int[] getRequiredSource(){
        return this.sourceRequired;
    }
}
