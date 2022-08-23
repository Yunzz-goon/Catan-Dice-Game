package comp1110.ass2.Resource;

public class ResourceJoker extends Resource
{
    private boolean used; // whether this joker has been used or not

    /**
     * The ResourceJoker should be initialised as a single use resource
     * Once the ResourceJoker is used, it will need to be marked as such
     * so that it cannot be used again for the rest of the game
     * @param resourceID the numerical ID of the resource
     * @param resourceType the type of resource
     */
    public ResourceJoker(int resourceID, ResourceType resourceType)
    {
        super(resourceID, resourceType);
        this.used = false;
    }

    public boolean isUsed()
    {
        return used;
    }

    public void setUsed()
    {
        this.used = true;
    }
}
