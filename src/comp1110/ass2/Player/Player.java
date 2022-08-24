package comp1110.ass2.Player;

public class Player
{
    private int turnNumber;
    private int playerID;

    public Player(int playerID)
    {
        this.turnNumber = 0;
        this.playerID = playerID;
    }

    public int getTurnNumber()
    {
        return turnNumber;
    }


    public int getPlayerID()
    {
        return playerID;
    }

    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * updates the player's turn
     */
    public void updateTurn()
    {
        this.turnNumber += 1;
        if (this.turnNumber >= 15) endGame();
    }

    /**
     * ends the game after 15 turns
     */
    public void endGame()
    {

    }
}
