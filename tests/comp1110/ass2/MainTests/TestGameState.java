package comp1110.ass2.MainTests;

import comp1110.ass2.Building.Available;
import comp1110.ass2.Main.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGameState
{
    /*
     * Things to Test:
     *  1. Initialisation of the GameState at any point in the game
     *  2. That a partial state can be modified with input values
     *  3. That the methods in GameState work as expected, on an empty, partial and full GameState
     *   Specifically, that the following methods work as expected:
     *     - updateTurn()
     *     - updateScore() -
     *     - calculateAvailableBuildings()
     *     - removeAvailableDuplicates()
     */

    //TODO - these tests are not yet fully written, although a decent chunk is here



    @Test
    public void testInitialScore()
    {
        assertEquals(0, Structures.startState.getCurrentScore());
        // TODO - Test the initial score on the partial state
        // TODO - test the initial score on the full state
    }

    @Test
    public void testInitialTurn()
    {
        assertEquals(0, Structures.startState.getTurn());
        // TODO - Test the initial turn on the partial state
        // TODO - test the initial turn on the full state
    }

    @Test
    public void testInitialResources()
    {
        // TODO - Test the initial resources on the start state
        // TODO - Test the initial resources on the partial state
        // TODO - Test the initial resources on the full state
    }

    @Test
    public void testInitialBuildings()
    {
        // TODO - Test the initial buildings on the start state
        // TODO - Test the initial buildings on the partial state
        // TODO - Test the initial buildings on the full state
    }

    @Test
    public void testInitialJokers()
    {
        // TODO - Test the initial jokers on the start state
        // TODO - Test the initial jokers on the partial state
        // TODO - Test the initial jokers on the full state
    }


    // Modifying states test - ensure that the various methods implemented to modify the states work as expected

    @Test
    public void testModifyState1()
    {
        GameState tempState = Structures.startState;
        tempState.addBuilding(0, Available.ROAD_1);
    }

    @Test
    public void testModifyState2()
    {
    }

    @Test
    public void testModifyState3()
    {
    }

    @Test
    public void testModifyState4()
    {
    }

    @Test
    public void testModifyState5()
    {
    }

    @Test
    public void testUpdateTurnInitial()
    {
        GameState state = Structures.startState;
        state.updateTurn();
        assertEquals(1, state.getTurn());
    }

    @Test
    public void testUpdateTurnPartial()
    {
        GameState state = Structures.partialState;
        state.updateTurn();
        assertEquals(4, state.getTurn());
    }

    @Test
    public void testUpdateTurnFinal()
    {
        GameState state = Structures.finalState;
        state.updateTurn();
        assertEquals(15, state.getTurn());
    }

    @Test
    public void testUpdateScoreInitial()
    {
        GameState state = Structures.startState;
        // need to add buildings to the state
        state.updateScore();
        assertEquals(-1, state.getCurrentScore());
    }

    @Test
    public void testUpdateScorePartial()
    {
        GameState state = Structures.partialState;
        // need to add buildings to the state
        state.updateScore();
        assertEquals(-1, state.getCurrentScore());
    }

    @Test
    public void testUpdateScoreFinal()
    {
        GameState state = Structures.finalState;
        // need to add buildings to the state
        state.updateScore();
        assertEquals(-1, state.getCurrentScore());
    }

    @Test
    public void testCalculateAvailableBuildingsInitial()
    {
    }

    @Test
    public void testCalculateAvailableBuildingsPartial()
    {
    }

    @Test
    public void testCalculateAvailableBuildingsFinal()
    {
    }

    @Test
    public void testRemoveAvailableDuplicatesInitial()
    {
    }

    @Test
    public void testRemoveAvailableDuplicatesPartial()
    {
    }

    @Test
    public void testRemoveAvailableDuplicatesFinal()
    {
    }

}
