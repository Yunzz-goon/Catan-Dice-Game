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

    /**
     * allows the player to exchange two gold resources for any one of the other resources
     * will have to check to see if the player has enough gold - if so, remove two of the
     * gold and exchange it for the first resource. If the player then has enough to trade
     * again, they may do so, and the game will attempt to trade for resource two, and then
     * resource three
     * @param rTG1 the first resource the player wants to trade for the gold
     * @param rTG2 the second resource the player wants to trade for the gold
     * @param rTG3 the third resource the player wants to trade for the gold
     */
    public void tradeForGold(Resource rTG1, Resource rTG2, Resource rTG3)
    {

    }

    /**
     * Empty's the player's resources at the end of the round - should be automatically
     * called at the end of a player's turn
     */
    public void emptyResources()
    {

    }
}
