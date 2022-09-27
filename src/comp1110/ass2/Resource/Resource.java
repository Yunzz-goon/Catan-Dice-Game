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

    protected ResourceType resourceType;

    protected int resourceQuantity;

    public Resource(ResourceType resourceType, int resourceQuantity)
    {
        this.resourceType = resourceType;
        this.resourceQuantity = resourceQuantity;
    }


    public int getResourceQuantity()
    {
        return resourceQuantity;
    }

    public ResourceType getResourceType()
    {
        return resourceType;
    }

    public void setResourceQuantity(int resourceQuantity)
    {
        this.resourceQuantity = resourceQuantity;
    }
}
