package edu.up.cs.androidcatan.catan.actions;

import android.util.Log;

import edu.up.cs.androidcatan.game.GamePlayer;
import edu.up.cs.androidcatan.game.actionMsg.GameAction;

public class CatanUseYearOfPlentyCardAction extends GameAction {
    private static final String TAG = "CatanUseYearOfPlentyCardAction";

    public CatanUseYearOfPlentyCardAction(GamePlayer player){
        super(player);
        Log.d(TAG, "CatanUseYearOfPlentyCardAction called");
    }
}