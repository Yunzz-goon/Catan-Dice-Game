package comp1110.ass2;

public class Building {
    public static int point;
    public static boolean building_status;
    public static int[] source_required;
    public Building(int point, int[] source_required) {
        this.point = point;
        this.source_required = source_required;
        this.building_status = false;
    }

    /**
     * Set the status of the building
     * It is noticed that all building except the first road is initialized as unbuilt(false) and
     * when creating the first road we have to set the status of it
     * @param status the status that we want to set for the building
     */
    public void setStatus(boolean status){
        this.building_status = status;
    }

    /**
     * get the status of the building
     * @return return the status of the building
     */
    public boolean getStatus(){
        return this.building_status;
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
        return this.source_required;
    }
}
