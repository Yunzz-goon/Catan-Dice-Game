package comp1110.ass2.Building;

public class Knight extends Building implements Assess {
    public enum ExchangingResource{
        BRICK,
        LUMBER,
        WOOL,
        GRAIN,
        ORE,
        UNDETERMINED;
    }

    public static ExchangingResource exchangableThing;
    public static boolean disposable_status;
    public static boolean buildingStatus;
    public static Knight lastKnight;
    public Knight(int point, ExchangingResource exchangableThing,
                  Knight lastKnight) {
        super(point);
        this.exchangableThing = exchangableThing;
        this.buildingStatus = false;
        this.disposable_status = false;
        this.lastKnight = lastKnight;
    }

    public void setBuiltStatus(boolean status){
        this.buildingStatus = status;
    }

    public void setDisposableStatus(boolean status){
        this.disposable_status = status;
    }


    /**
     * judge whether the Knight could access
     * @return the result of judging whether the Knight could access
     */
    @Override
    public boolean isBuildingAssess() {
        if(this.lastKnight.getStatus()) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public char toChar() {
        return 'K';
    }


}
