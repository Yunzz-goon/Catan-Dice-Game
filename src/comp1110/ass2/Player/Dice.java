package comp1110.ass2.Player;

public class Dice
{
    int dice1;
    int dice2;
    int dice3;
    int dice4;
    int dice5;
    int dice6;

    public Dice()
    {
        dice1 = 0;
        dice2 = 0;
        dice3 = 0;
        dice4 = 0;
        dice5 = 0;
        dice6 = 0;
    }
    public void rollAll()
    {
        dice1 = (int) (Math.random() * 6 + 1);
        dice2 = (int) (Math.random() * 6 + 1);
        dice3 = (int) (Math.random() * 6 + 1);
        dice4 = (int) (Math.random() * 6 + 1);
        dice5 = (int) (Math.random() * 6 + 1);
        dice6 = (int) (Math.random() * 6 + 1);
    }

    public void rollSpecific(int diceNumber)
    {
        switch (diceNumber) {
            case 1 -> dice1 = (int) (Math.random() * 6 + 1);
            case 2 -> dice2 = (int) (Math.random() * 6 + 1);
            case 3 -> dice3 = (int) (Math.random() * 6 + 1);
            case 4 -> dice4 = (int) (Math.random() * 6 + 1);
            case 5 -> dice5 = (int) (Math.random() * 6 + 1);
            case 6 -> dice6 = (int) (Math.random() * 6 + 1);
        }
    }

}
