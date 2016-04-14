import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.Scanner;

/*****************************************
 * Author : rich
 * Date : 4/6/16
 * Assignment: Battleship
 ******************************************/
public class Battleship implements Game {


    private int turns = 0;
    private Player currentPlayer;

    private Player player1;
    private Player player2;
    private ArrayList<ArrayList<String>> player1ShipCoordinates = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> player2ShipCoordinates = new ArrayList<ArrayList<String>>();
    private ArrayList<String> player1Guesses = new ArrayList<String>();
    private ArrayList<String> player2Guesses = new ArrayList<String>();
    private ArrayList<String> player1Hits = new ArrayList<String>();
    private ArrayList<String> player2Hits = new ArrayList<String>();
    private int[] player1ShipHits = new int[5];
    private int[] player2ShipHits = new int[5];
    private int[] player1ShipsLeft;
    private int[] player2ShipsLeft;

    public Battleship (Player player1, Player player2)
    {
        this.player1 = player1;
        this.player2 = player2;

        //[["A1","N"]]
    }

    //hitsLeft(ship : int)




    private boolean wasHit(Player player, String coordinate)
    {
        //make code to return t/f if it was hit
        return false;
    }

    private boolean shipsLeft(Player player)
    {
        //make code to determine how many ships are left
        return false;
    }

    @Override
    public void nextTurn() {
        // TODO: 4/13/16 Determine if currentplayer is human/computer and call appropriate method. Switch current player.
        // TODO: 4/13/16 Switch the game phase after ships are placed after start after guesses

    }

    private void placeComputerShips(Player player) {
        // TODO: 4/13/16 Some randomization to place and store the position of computer ships
    }

    private void placeHumanShips(Player player) {
        // TODO: 4/13/16 Some input to request human placement. Should have boundries. Should take coord. and Direction (NESW)
    }

    private boolean aircraftWasHit(Player player) {
        // TODO: 4/13/16 Determine if the aircraft was hit. Should implement Player.getPlayerNumber()
        return false;
    }

    private boolean battleshipWasHit(Player player) {
        // TODO: 4/13/16 Determine if the battleship was hit. Should implement Player.getPlayerNumber()
        return false;
    }

    private boolean submarineWasHit(Player player) {
        // TODO: 4/13/16 Determine if the submarine was hit. Should implement Player.getPlayerNumber()
        return false;
    }

    private boolean destroyerWasHit(Player player) {
        // TODO: 4/13/16 Determine if the destroyer was hit. Should implement Player.getPlayerNumber()
        return false;
    }

    private boolean patrolWasHit(Player player) {
        // TODO: 4/13/16 Determine if the patrol was hit. Should implement Player.getPlayerNumber()
        return false;
    }

    private void drawBoard() {
        // TODO: 4/13/16 Should draw board in any scenario where board is to be drawn should be different if drawing first time should switch on game phase
    }

    private void showMenu() {
        // TODO: 4/13/16 should print the menu.
    }

    @Override
    public void winner(Player player) {
        System.out.println(player.getName());
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public int getTurns() {
        return turns;
    }

    @Override
    public void displayScore() {
        System.out.printf("%-5s : %s%n", player1.getName(), player1.getScore());
        System.out.printf("%-5s : %s%n", player2.getName(), player2.getScore());
    }


    private void computerTurn() {
        // TODO: 4/13/16 Come up with some randomization for the computers turn
    }


    private void humanTurn() {
        // TODO: 4/13/16 Come up with the inputs and boundries for placing ships
    }


}
