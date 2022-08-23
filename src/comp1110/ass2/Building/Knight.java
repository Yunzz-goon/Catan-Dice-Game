package comp1110.ass2.Building;

public class Knight extends Building implements Assess {
    enum ExchangingResource{
        BRICK,
        LUMBER,
        WOOL,
        GRAIN,
        ORE,
        UNDETERMINED;
    }

    public static ExchangingResource exchangableThing;
    public static boolean disposable_status;
    public static Knight lastKnight;
    public Knight(int point, int[] source_required, ExchangingResource exchangableThing,
                  Knight lastKnight) {
        super(point, source_required);
        this.exchangableThing = exchangableThing;
        this.disposable_status = false;
        this.lastKnight = lastKnight;
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


}
