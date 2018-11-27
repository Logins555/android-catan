package edu.up.cs.androidcatan.test;

import org.junit.Test;

import java.util.ArrayList;

import edu.up.cs.androidcatan.catan.CatanGameState;
import edu.up.cs.androidcatan.catan.Player;
import edu.up.cs.androidcatan.catan.gamestate.Board;
import edu.up.cs.androidcatan.catan.gamestate.buildings.Building;
import edu.up.cs.androidcatan.catan.gamestate.buildings.City;
import edu.up.cs.androidcatan.catan.gamestate.buildings.Road;
import edu.up.cs.androidcatan.catan.gamestate.buildings.Settlement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testValidBuildingLocationSetupPhaseEmptyIntersection(){
        Board board = new Board();
        assertTrue(board.validBuildingLocation(0, true, 0));
        assertFalse(board.validBuildingLocation(0, true, -1));
        assertFalse(board.validBuildingLocation(0, true, 90));
        assertFalse(board.validBuildingLocation(-3, true, -1));
        assertFalse(board.validBuildingLocation(-1, true, 5));      //bug
        assertTrue(board.validBuildingLocation(3, true, 53));
        assertTrue(board.validBuildingLocation(2, true, 24));
    }

    @Test
    public void testValidBuildingLocationSetupPhaseTakenIntersection(){
        Board board = new Board();
        board.addBuilding(0, new City(0));
        assertFalse(board.validBuildingLocation(1, true, 0));
        assertFalse(board.validBuildingLocation(0, true, 0));
        assertFalse(board.validBuildingLocation(0, true, -1));
        assertFalse(board.validBuildingLocation(0, true, 90));
        assertFalse(board.validBuildingLocation(-3, true, -1));
        assertFalse(board.validBuildingLocation(-1, true, 5));      //bug
        assertTrue(board.validBuildingLocation(3, true, 53));
        assertTrue(board.validBuildingLocation(2, true, 24));
    }

    @Test
    public void testAddBuilding(){
        Board board = new Board();
        Board boardTrue = new Board();
        Building settlement = new Settlement(0);

        board.addBuilding(3, settlement);
    }

    @Test
    public void testHasBuilding(){
        Board board = new Board();
        Building settlement = new Settlement(0);
        board.addBuilding(0, settlement);
        assertTrue(board.hasBuilding(0));
        assertFalse(board.hasBuilding(53));
    }

    @Test
    public void testHGraph() {
        Board board = new Board();
        boolean hGraph[][] = board.getHGraph();
        assertTrue(hGraph[5][14]);
        assertTrue(hGraph[5][0]);
        assertTrue(hGraph[5][14]);
        assertTrue(hGraph[5][15]);
        assertTrue(hGraph[5][16]);
        assertTrue(hGraph[5][6]);
    }

    @Test
    public void testIsConnected(){
        Board board = new Board();

        board.getBuildings()[1] = new Settlement(1);

        assertFalse(board.isConnected(1,30));
        assertFalse(board.isConnected(1, -20));
        assertFalse(board.isConnected(0,1)); //wrong playerId
        assertFalse(board.isConnected(1,2));

        assertTrue(board.isConnected(1,1));
    }

    @Test
    public void testValidRoadPlacement(){
        Board board = new Board();

        board.getBuildings()[1] = new Settlement(1);

        assertTrue(board.validRoadPlacement(1,false,1,2));
        assertFalse(board.validRoadPlacement(1, false, 6,7));
    }

    @Test
    public void testAddRoadArray(){
        Board board = new Board();

        board.addRoad(1,1,2);
        if (board.getRoads().size() != 0){
            assert true;
        }
        else {
            assert false;
        }
    }

    @Test
    public void testAddRoadMatrix(){
        Board board = new Board();

        board.addRoad(1, 1,2);
        assertEquals(board.getRoadMatrix()[1][2].getOwnerId(), 1);
        assertFalse(board.getRoadMatrix()[1][2].getOwnerId() == 3);
    }

    @Test
    public void testHasRoad(){
        Board board = new Board();

        board.getBuildings()[1] = new Settlement(1);
        board.addRoad(1,1,2);

        assertTrue(board.hasRoad(1));
        assertTrue(board.hasRoad(2));

        assertFalse(board.hasRoad(45));
        assertFalse(board.hasRoad(-20));
    }

    @Test
    public void testGetPlayerWithLongestRoad(){
        Board board = new Board();
        CatanGameState gameState = new CatanGameState();

        assertTrue(board.getPlayerWithLongestRoad(gameState.getPlayerList()) == -1);

        assertFalse(board.getPlayerWithLongestRoad(gameState.getPlayerList()) == 0);
        assertFalse(board.getPlayerWithLongestRoad(gameState.getPlayerList()) == 1);
        assertFalse(board.getPlayerWithLongestRoad(gameState.getPlayerList()) == 2);
        assertFalse(board.getPlayerWithLongestRoad(gameState.getPlayerList()) == 3);
    }

    @Test
    public void testValidBuildingLocation(){
        Board board = new Board();
        int playerId = -1;

        assertFalse(board.validBuildingLocation(playerId, false, -1));
        assertFalse(board.validBuildingLocation(1, false, 2));

        board.getBuildings()[0] = new Settlement(1);
        board.addRoad(1,0,1);

        assertTrue(board.validBuildingLocation(1, true, 20));
        assertFalse(board.validBuildingLocation(1, true,0));
    }

    @Test
    public void testValidCityLocation(){
        
    }
}