package comp1110.ass2.Resource;

public class ResourceJoker extends Resource
{
    private boolean used; // whether this joker has been used or not
    private int points; // the number of points this joker is worth

    /**
     * The ResourceJoker should be initialised as a single use resource
     * Once the ResourceJoker is used, it will need to be marked as such
     * so that it cannot be used again for the rest of the game
     * @param resourceType the type of resource
     */
    public ResourceJoker(ResourceType resourceType)
    {
        super(resourceType, 1); // the joker is a single use resource
        this.used = false;
        this.points = switch (resourceType)
                {
                    case ORE -> 1;
                    case GRAIN -> 2;
                    case WOOL -> 3;
                    case LUMBER -> 4;
                    case BRICK -> 5;
                    case NULL -> 6; // this is the points for the wild card joker
                    case GOLD -> throw new IllegalArgumentException("Gold is not a valid resource type for a joker");
                };
    }

    public boolean isUsed()
    {
        return used;
    }

    public void setUsed()
    {
        this.used = true;
    }

    public int getJokerPoints()
    {
        return points;
    }
}
