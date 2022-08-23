package comp1110.ass2.Resource;

public class PlayerResources
{
    private Resource[] resources = new Resource[6];

    /**
    * given a die and the resource it shows, add that resource to the player's resources
    * @param diceNumber which die was rolled (a number between 1 and 6)
    * @param resource the resource shown on the face of the die
     */
    public void setResourcesFromDice(Resource resource, int diceNumber)
    {
        resources[diceNumber - 1] = resource;
    }

    public Resource[] getResources()
    {
        return resources;
    }

    /**
     * allows the player to spend the resources they have rolled
     * will need to check if the player has the resources available to spend
     * (i.e. the player resources can never go negative)
     * once a player has spent the resources they will be removed from the
     * array of available resources
     * @param resourcesToSpend the resources the player wants to spend
     */
    public void spendResources(Resource[] resourcesToSpend)
    {

    }
}
