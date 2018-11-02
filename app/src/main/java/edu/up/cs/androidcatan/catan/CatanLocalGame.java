package edu.up.cs.androidcatan.catan;

import android.util.Log;

import edu.up.cs.androidcatan.catan.actions.CatanBuildCityAction;
import edu.up.cs.androidcatan.catan.actions.CatanBuildRoadAction;
import edu.up.cs.androidcatan.catan.actions.CatanBuildSettlementAcation;
import edu.up.cs.androidcatan.catan.actions.CatanBuyDevCardAction;
import edu.up.cs.androidcatan.catan.actions.CatanEndTurnAction;
import edu.up.cs.androidcatan.catan.actions.CatanRollDiceAction;
import edu.up.cs.androidcatan.catan.actions.CatanUseDevCardAcation;
import edu.up.cs.androidcatan.game.GamePlayer;
import edu.up.cs.androidcatan.game.LocalGame;
import edu.up.cs.androidcatan.game.actionMsg.GameAction;


public class CatanLocalGame extends LocalGame {
    CatanGameState gameState;
    private final static String TAG = "CatanLocalGame";

    public CatanLocalGame() {
        gameState = new CatanGameState();
    }

    /**
     * Tell whether the given player is allowed to make a move at the
     * present point in the game.
     *
     * @param playerIdx the player's player-number (ID)
     * @return true iff the player is allowed to move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == gameState.getCurrentPlayerId();
    }

    /**
     * Makes a move on behalf of a player.
     *
     * @param action The move that the player has sent to the game
     * @return Tells whether the move was a legal one.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        Log.d(TAG, "makeMove() called with: action = [" + action + "]");
        if (action instanceof CatanRollDiceAction) {
            Log.d(TAG, "makeMove() called with: action = [" + action + "]");
            return gameState.rollDice();
        }

        if (action instanceof CatanEndTurnAction) {

        }

        if (action instanceof CatanBuildRoadAction) {

        }

        if (action instanceof CatanBuildSettlementAcation) {

        }

        if (action instanceof CatanBuildCityAction) {

        }


        if (action instanceof CatanBuyDevCardAction) {

        }

        if (action instanceof CatanUseDevCardAcation) {

        }



        return false;
    }

    /**
     * Notify the given player that its state has changed. This should involve sending
     * a GameInfo object to the player. If the game is not a perfect-information game
     * this method should remove any information from the game that the player is not
     * allowed to know.
     *
     * @param p the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    /**
     * Check if the game is over. It is over, return a string that tells
     * who the winner(s), if any, are. If the game is not over, return null;
     *
     * @return a message that tells who has won the game, or null if the
     * game is not over
     */
    @Override
    protected String checkIfGameOver() {
        return null;
    }
}