package comp1110.ass2;

public class Knight extends Building implements Assess{
    enum ExchangingResource{
        BRICK,
        LUMBER,
        WOOL,
        GRAIN,
        ORE,
        UNDETERMINED;
    }

    public static ExchangingResource exchangable_thing;
    public static boolean disposable_status;
    public static Knight last_knight;
    public Knight(int point, int[] source_required, ExchangingResource exchangable_thing,
                  Knight last_knight) {
        super(point, source_required);
        this.exchangable_thing = exchangable_thing;
        this.disposable_status = false;
        this.last_knight = last_knight;
    }

    /**
     * judge whether the Knight could access
     * @return the result of judging whether the Knight could access
     */
    @Override
    public boolean isBuildingAssess() {
        if(this.last_knight.getStatus()) {
            return true;
        }else{
            return false;
        }
    }


}
