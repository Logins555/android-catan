package edu.up.cs.androidcatan;

import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import edu.up.cs.androidcatan.catan.devcards.DevelopmentCard;
import edu.up.cs.androidcatan.game.GameHumanPlayer;
import edu.up.cs.androidcatan.game.GameMainActivity;
import edu.up.cs.androidcatan.game.infoMsg.GameInfo;

// todo removed unused imports please
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * @author Alex Weininger
 * @author Andrew Lang
 * @author Daniel Borg
 * @author Niraj Mali
 * @version October 30th, 2018
 * https://github.com/alexweininger/android-catan
 **/

public class Player extends GameHumanPlayer implements OnClickListener{

    private static final String TAG = "Player";

    /* Player instance variables */

    // resourceCard index values: 0 = Brick, 1 = Grain, 2 = Lumber, 3 = Ore, 4 = Wool
    private int[] resourceCards = new int[5]; // array for number of each resource card a player has

    // array for relating resource card names to resource card ids in the resourceCards array above
    private String[] resourceCardIds = {"Brick", "Grain", "Lumber", "Ore", "Wool"};

    private ArrayList<DevelopmentCard> developmentCards = new ArrayList<>(); // ArrayList of the development cards the player owns
    private HashMap<String, Integer> availableBuildings = new HashMap<>(); // // k: resource id, v: buildings available TODO change data type to better one
    private int armySize; // determined by how many knight dev cards the player has played, used for determining who currently has the largest army trophy
    private int playerId;   // playerId

    /**
     * Player constructor
     */
    public Player(int id) {
        // initialize all resource card counts to 0
        super("" + id + "");
        for (int i = 0; i < this.resourceCards.length; i++) {
            this.resourceCards[i] = 0;
        }
        this.armySize = 0;
        this.playerId = id;
    }

    /**
     * deepCopy constructor
     *
     * @param player - Player object to copy
     */
    public Player(Player player) {
        super("" + player.getPlayerId() + "");
        this.developmentCards = player.getDevelopmentCards();
        this.armySize = player.getArmySize();
        this.availableBuildings = player.getAvailableBuildings();
        this.playerId = player.getPlayerId();
        this.resourceCards = player.getResourceCards();
        this.resourceCardIds = player.getResourceCardIds();
    }

    /**
     * error checking:
     * - checks for valid resourceCardId
     *
     * @param resourceCardId - index value of resource to add (0-4) defined above
     * @param numToAdd       - number of resource cards of this type to add to the players inventory AW
     */
    public void addResourceCard(int resourceCardId, int numToAdd) {
        if (resourceCardId < 0 || resourceCardId >= 5) { // check for a valid resourceCardId
            Log.d("devError", "ERROR addResourceCard: given resourceCardId: " + resourceCardId + " is invalid. Must be an integer (0-4).");
        } else {
            Log.d("devInfo", "INFO addResourceCard: added numToAdd: " + numToAdd + " resourceCardId: " + resourceCardId + " to playerId: " + this.playerId + " resourceCards.");
            this.resourceCards[resourceCardId] += numToAdd; // increase count of the resource card
        }
    }

    /**
     * @param resourceCardId - resource to check
     * @param numToCheckFor  - number of resources to make sure the player has
     * @return - whether they have at least that many resources of the given type
     */
    public boolean checkResourceCard(int resourceCardId, int numToCheckFor) {
        if (resourceCardId < 0 || resourceCardId >= 5) { // check for valid resourceCardId
            Log.d("devError", "ERROR removeResourceCard: given resourceCardId: " + resourceCardId + " is invalid. Must be an integer (0-4).");
            return false; // did not remove resource cards to players inventory
        }
        return this.resourceCards[resourceCardId] < numToCheckFor;
    }

    /**
     * @param resourceCost - resourceCost array, e.g. Settlement.resourceCost
     * @return - true of false, does the player have all of these resources?
     */
    public boolean checkResourceBundle(int[] resourceCost) {
        for (Integer id : resourceCost) {
            if (!checkResourceCard(id, resourceCost[id])) {
                return false;
            }
        }
        return true;
    }

    /**
     * error checking:
     * - error checks for valid resourceCardId
     * - error checks for preventing negative resource card counts
     *
     * @param resourceCardId - id of resource card to remove from players inventory
     * @param numToRemove    - number of resource cards of this type to remove
     * @return - if numToRemove resource card(s) have been removed from the players inventory
     */
    public boolean removeResourceCard(int resourceCardId, int numToRemove) {
        if (resourceCardId < 0 || resourceCardId >= 5) { // check for valid resourceCardId
            Log.i(TAG, "removeResourceCard: given resourceCardId: " + resourceCardId + " is invalid. Must be an integer (0-4).");
            return false; // did not remove resource cards to players inventory
        } else {
            if (this.resourceCards[resourceCardId] >= numToRemove) { // check to prevent negative card counts
                Log.i(TAG, "removeResourceCard: removed numToRemove: " + numToRemove + " resourceCardId: " + resourceCardId + " from playerId: " + this.playerId + " resourceCards.");
                this.resourceCards[resourceCardId] -= numToRemove; // remove cards
                return true; // removed cards to players inventory
            } else {
                Log.i(TAG, "removeResourceCard: cannot remove numToRemove: " + numToRemove + " resourceCardId: " + resourceCardId + " from playerId: " + this.playerId + ". Player currently has " + this.resourceCards[resourceCardId] + " cards of this resource.");
                return false; // did not remove resource cards to players inventory
            }
        }
    }

    public boolean removeResourceBundle(int[] resourceCost) {
        if (checkResourceBundle(resourceCost)) {
            Log.e(TAG, "removeResourceBundle: Cannot remove resource bundle from player " + this.playerId + ". Insufficient resources. Must do error checking before calling this method!");
            return false;
        }
        for (int i : resourceCost) {
            if(!this.removeResourceCard(i, resourceCost[i])) {
                Log.e(TAG, "removeResourceBundle: Cannot remove resource bundle from player " + this.playerId + ". Player.removeResourceCard method returned false.");
                return false;
            }
        }
        Log.d(TAG, "removeResourceBundle successfully removed resourceCost = [" + resourceCost.toString() + "] from players inventory.");
        return true;
    }

    /**
     * @return String showing the number of each resource card the player has
     */
    public String printResourceCards() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.resourceCards.length; i++) {
            str.append(this.resourceCardIds[i]).append(": ").append(this.resourceCards[i]).append(", ");
        }
        return str.toString();
    }

    public int[] getResourceCards() {
        return this.resourceCards;
    }

    public void setResourceCards(int[] resourceCards) {
        this.resourceCards = resourceCards;
    }

    /**
     * @return the size of the player's army
     */
    public int getArmySize() {
        return armySize;
    }

    /**
     * @param armySize the size of the player's army
     */
    public void setArmySize(int armySize) {
        this.armySize = armySize;
    }

    /**
     * @param devCard dev card to add
     */
    public void addDevelopmentCard(DevelopmentCard devCard) {
        developmentCards.add(devCard);
    }

    /**
     * @param devCard dev card to remove
     * @return if action was possible
     */
    public boolean useDevCard(DevelopmentCard devCard) {
        if (developmentCards.contains(devCard)) {
            developmentCards.remove(devCard);
            return true;
        }
        return false;
    }

    //use to allow the player to use the dev card they built the turn prior
    public void setDevelopmentCardsAsPlayable() {
        for (int i = 0; i < developmentCards.size(); i++) {
            developmentCards.get(i).setPlayable(true);
        }
    }

    /**
     * @return the player's id
     */
    public int getPlayerId() {
        return this.playerId;
    }

    public ArrayList<DevelopmentCard> getDevelopmentCards() {
        return developmentCards;
    }

    public void setDevelopmentCards(ArrayList<DevelopmentCard> developmentCards) {
        this.developmentCards = developmentCards;
    }

    public HashMap<String, Integer> getAvailableBuildings() {
        return availableBuildings;
    }

    public void setAvailableBuildings(HashMap<String, Integer> availableBuildings) {
        this.availableBuildings = availableBuildings;
    }

    public String[] getResourceCardIds() {
        return resourceCardIds;
    }

    private int getTotalResourceCardCount() {
        int result = 0;
        for (int i = 0; i < this.resourceCards.length; i++) {
            result += this.resourceCards[i];
        }
        return result;
    }

    int getRandomCard() {

        if(this.getTotalResourceCardCount() < 1) {
            Log.e(TAG, "getRandomCard: Player does not have any resources cards.");
            return -1;
        }

        Random random = new Random();
        int randomResourceId;
        do {
            randomResourceId = random.nextInt(4); // 0-4
        } while (!checkResourceCard(randomResourceId, 1));

        if (!removeResourceCard(randomResourceId, 1)) {
            Log.e(TAG, "getRandomCard: Player does not have random card that was checked for.");
            return -1;
        }

        return randomResourceId;
    }

    //TODO Figure out what these methods from the GameHumanPlayer and OnClickListener do and implement them

    public void onClick(View button){

    }

    public void setAsGui(GameMainActivity activity){

    }

    public View getTopView() {
        //return myActivity.findViewById(R.id.top_gui_layout);
        return null;
    }

    public void receiveInfo(GameInfo info){

    }

    /**
     * @return string representation of a Player
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player ");
        sb.append(playerId);
        sb.append("\nDevelopment Cards = ");
        sb.append(this.developmentCards);
        sb.append("\navailableBuildings = ");
        sb.append(availableBuildings);
        sb.append("\narmySize = ");
        sb.append(armySize);
        sb.append("\n");
        return sb.toString();
    }
}


