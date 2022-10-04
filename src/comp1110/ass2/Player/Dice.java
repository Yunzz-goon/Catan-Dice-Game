package comp1110.ass2.Player;

import java.util.Random;

public class Dice
{
    private String ORE;
    private String GRAIN;
    private String WOOL;
    private String TIMBER;
    private String BRICKS;
    private String GOLD;
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
    private String ORECount;
    private String GRAINCount;
    private String WOOLCount;
    private String TIMBERCount;
    private String BRICKSCount;
    private String GOLDCount;

    public Random random = new Random();


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
        dice1 = random.nextInt(6) + 1;
        dice2 = random.nextInt(6) + 1;
        dice3 = random.nextInt(6) + 1;
        dice4 = random.nextInt(6) + 1;
        dice5 = random.nextInt(6) + 1;
        dice6 = random.nextInt(6) + 1;
    }

    /**
     * Rolls the dice with the given number - it needs to check if the total number of dice rolls
     * for the requested dice will be less than or equal to three after this reroll. If it is, then
     * reroll the specified dice. If it isn't, return the current value of the die.
     * @param diceNumber the number of the dice to roll
     */
    public void rollSpecific(int diceNumber)
    {
        switch (diceNumber)
        {
            case 1 ->
             {
                if (dice1Count < 3)
                {
                    dice1 = random.nextInt(6) + 1;
                    dice1Count++;
                }
            }
            case 2 ->
            {
                if (dice2Count < 3)
                {
                    dice2 = random.nextInt(6) + 1;
                    dice2Count++;
                }
            }
            case 3 ->
            {
                if (dice3Count < 3)
                {
                    dice3 = random.nextInt(6) + 1;
                    dice3Count++;
                }
            }
            case 4 ->
            {
                if (dice4Count < 3)
                {
                    dice4 = random.nextInt(6) + 1;
                    dice4Count++;
                }
            }
            case 5 ->
            {
                if (dice5Count < 3)
                {
                    dice5 = random.nextInt(6) + 1;
                    dice5Count++;
                }
            }
            case 6 ->
            {
                if (dice6Count < 3)
                {
                    dice6 = random.nextInt(6) + 1;
                    dice6Count++;
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
