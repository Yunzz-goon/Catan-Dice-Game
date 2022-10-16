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

    public ExchangingResource exchangableThing;
    public boolean disposable_status;
    public boolean buildingStatus;
    public Knight lastKnight;
    public Knight(int point, ExchangingResource exchangableThing,
                  Knight lastKnight) {
        super(point);
        this.exchangableThing = exchangableThing;
        this.disposable_status = false;
        this.lastKnight = lastKnight;
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
        if (this.lastKnight == null){
            return true;
        }
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
