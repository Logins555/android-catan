package edu.up.cs.androidcatan.catan.actions;

import android.util.Log;

import edu.up.cs.androidcatan.game.GamePlayer;
import edu.up.cs.androidcatan.game.actionMsg.GameAction;

public class CatanUseRoadBuildingCardAction extends GameAction {
    private static final String TAG = "CatanUseRoadBuildingCardAction";

    public CatanUseRoadBuildingCardAction(GamePlayer player){
        super(player);
        Log.d(TAG, "CatanUseRoadBuildingCardAction called");
    }
}
