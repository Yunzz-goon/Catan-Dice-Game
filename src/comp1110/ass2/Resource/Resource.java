package comp1110.ass2.Resource;

public class Resource
{
//    public final static int GOLD_ID = 1;
//    public final static int GRAIN_ID = 2;
//    public final static int LUMBER_ID = 3;
//    public final static int WOOL_ID = 4;
//    public final static int ORE_ID = 5;
//    public final static int BRICK_ID = 6;

    private int resourceID;
    private ResourceType resourceType;

    public Resource(int resourceID, ResourceType resourceType)
    {
        this.resourceID = resourceType.ordinal() + 1;
        this.resourceType = resourceType;
    }

    public int getResourceID()
    {
        return resourceID;
    }

    public ResourceType getResourceType()
    {
        return resourceType;
    }
}
