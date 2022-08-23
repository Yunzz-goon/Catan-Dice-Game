package comp1110.ass2.Player;

public class Dice
{
    private int dice1;
    private int dice2;
    private int dice3;
    private int dice4;
    private int dice5;
    private int dice6;

    private int dice1Count;
    private int dice2Count;
    private int dice3Count;
    private int dice4Count;
    private int dice5Count;
    private int dice6Count;


    public Dice()
    {
        dice1 = 0;
        dice2 = 0;
        dice3 = 0;
        dice4 = 0;
        dice5 = 0;
        dice6 = 0;
    }

    /**
     * Rolls all the dice
     */
    public void rollAll()
    {
        dice1 = (int) (Math.random() * 6 + 1);
        dice2 = (int) (Math.random() * 6 + 1);
        dice3 = (int) (Math.random() * 6 + 1);
        dice4 = (int) (Math.random() * 6 + 1);
        dice5 = (int) (Math.random() * 6 + 1);
        dice6 = (int) (Math.random() * 6 + 1);
    }

    /**
     * Rolls the dice with the given number - it needs to check if the total number of dice rolls
     * for the requested dice will be less than or equal to three after this reroll. If it is, then
     * reroll the specified dice. If it isn't, return the current value of the die.
     * @param diceNumber the number of the dice to roll
     */
    public void rollSpecific(int diceNumber)
    {
        switch (diceNumber) {
            case 1 ->
            {
                if (dice1Count < 3)
                {
                    dice1 = (int) (Math.random() * 6 + 1);
                    dice1Count++;
                }
            }
        }
    }

    /**
     * Allows outside classes to access all elements of the dice array
     * @return the dice array
     */
    public int[] getDiceArray()
    {
        return new int[]{dice1, dice2, dice3, dice4, dice5, dice6};
    }

}
