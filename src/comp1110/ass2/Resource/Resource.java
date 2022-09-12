package comp1110.ass2.Resource;

import static comp1110.ass2.Resource.ResourceType.*;

public class Resource
{
    public final static int GOLD_ID = GOLD.ordinal();
    public final static int GRAIN_ID = GRAIN.ordinal();
    public final static int LUMBER_ID = LUMBER.ordinal();
    public final static int WOOL_ID = WOOL.ordinal();
    public final static int ORE_ID = ORE.ordinal();
    public final static int BRICK_ID = BRICK.ordinal();

    private int resourceID;
    private ResourceType resourceType;

    public Resource(int resourceID, ResourceType resourceType)
    {
        this.resourceID = resourceType.ordinal();
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
