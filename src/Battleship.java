import org.omg.CORBA.portable.ApplicationException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/*****************************************
 * Author : rich
 * Date : 4/6/16
 * Assignment: Battleship
 ******************************************/
class Battleship implements Game {


    private int turns = 0;
    private Player currentPlayer;

    private Player player1;
    private Player player2;
    private ArrayList<ArrayList<String>> player1ShipCoordinates = new ArrayList<>(); //[[A1,N]]
    private ArrayList<ArrayList<String>> player2ShipCoordinates = new ArrayList<>();
    private ArrayList<String> player1Guesses = new ArrayList<>();
    private ArrayList<String> player2Guesses = new ArrayList<>();
    private ArrayList<String> player1Hits = new ArrayList<>();
    private ArrayList<String> player2Hits = new ArrayList<>();
    private int[] player1ShipHits = new int[]{5, 4, 3, 3, 2};
    private int[] player2ShipHits = new int[]{5, 4, 3, 3, 2};
    private boolean isGameOver = false;
    private Player winner;

    public boolean isGameOver() {
        return isGameOver;
    }

    public Player getWinner() {
        return winner;
    }


    private enum Phases {PLACEMENT, GUESSING}

    private Phases phase = Phases.PLACEMENT;

    Battleship(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;

        //[["A1","N"]]
    }

    //hitsLeft(ship : int)

    private boolean wasHit(Player player, String guess) {
        return wasHit(player, guess, true);
    }


    private boolean wasHit(Player player, String guess, boolean commit) {
        final int AIRCRAFT = 0;
        final int BATTLESHIP = 1;
        final int SUBMARINE = 2;
        final int DESTROYER = 3;
        final int PATROL = 4;
        ArrayList<ArrayList<String>> playerShips = (player.getPlayerNumber() == 1) ? player1ShipCoordinates : player2ShipCoordinates;
        ArrayList<String> shipDetails;
        String shipCoord;
        String shipDirection;
        boolean shipWasHit = false;
        int ship = 0;

        while (ship < playerShips.size() && !shipWasHit) {

            shipDetails = playerShips.get(ship);
            shipCoord = shipDetails.get(0);
            shipDirection = shipDetails.get(1);

            switch (ship) {
                case AIRCRAFT:
                    shipWasHit = Ship.wasShipHit(Ship.Ships.AIRCRAFT, guess, shipCoord, shipDirection);
                    break;
                case BATTLESHIP:
                    shipWasHit = Ship.wasShipHit(Ship.Ships.BATTLESHIP, guess, shipCoord, shipDirection);
                    break;
                case SUBMARINE:
                    shipWasHit = Ship.wasShipHit(Ship.Ships.SUBMARINE, guess, shipCoord, shipDirection);
                    break;
                case DESTROYER:
                    shipWasHit = Ship.wasShipHit(Ship.Ships.DESTROYER, guess, shipCoord, shipDirection);
                    break;
                case PATROL:
                    shipWasHit = Ship.wasShipHit(Ship.Ships.PATROL, guess, shipCoord, shipDirection);
                    break;
            }

            ship++;
        }

        if (shipWasHit && phase == Phases.GUESSING && commit) {
            int[] hitsLeft = getPlayerShipHits(player);
            --hitsLeft[ship - 1];
            setPlayerShipHits(player, hitsLeft);
            if (getCurrentPlayer().getType() == Player.playerType.HUMAN) {
                System.out.println((hitsLeft[ship - 1] > 0) ? "HIT!" : String.format("Sunk %s's %s!", player.getName(), Ship.getShipByIndex(ship - 1).getName()));
            }
        }

        return shipWasHit;
    }

    private int[] getPlayerShipHits(Player player) {
        return (player.getPlayerNumber() == 1) ? player1ShipHits : player2ShipHits;
    }

    private void setPlayerShipHits(Player player, int[] hitsLeft) {
        if (player.getPlayerNumber() == 1) {
            player1ShipHits = hitsLeft;
        } else {
            player2ShipHits = hitsLeft;
        }
    }

    private boolean shipsLeft() {
        boolean shipsLeft = false;
        Player player = getNextPlayer();
        if (player.getPlayerNumber() == 1) {

            for (int hits : player1ShipHits) {
                if (hits > 0) {
                    shipsLeft = true;
                    break;
                }
            }
        } else {
            for (int hits : player2ShipHits) {
                System.out.println(hits);
                if (hits > 0) {
                    shipsLeft = true;
                    break;
                }
            }
        }
        return shipsLeft;
    }

    @Override
    public void nextTurn() {
        if (currentPlayer.getType() == Player.playerType.HUMAN) {
            drawBoard();
        }
        while (turns < 5 && phase == Phases.PLACEMENT) {
            if (currentPlayer.getType() == Player.playerType.COMPUTER) {
                placeComputerShips();
                break;
            } else {
                placeHumanShips();
                turns++;
                drawBoard();
            }
        }

        //[[A1,E],]

        if (currentPlayer == player1 && phase == Phases.PLACEMENT) {
            currentPlayer = player2;
            turns = 0;
            nextTurn();
        }

        if (phase == Phases.PLACEMENT) {
            currentPlayer = player1;
            phase = Phases.GUESSING;
            turns = 0;
        }

        // TODO: 4/18/16 Implement guessing portion of the game

        if (getCurrentPlayer().getType() == Player.playerType.HUMAN) {
            humanTurn();
        } else {
            computerTurn();
        }

        if (!shipsLeft()) {
            isGameOver = true;
            getNextPlayer().incrementWins();
            winner = getCurrentPlayer();
        }

        currentPlayer = getNextPlayer();
        ++turns;

    }

    //todo: This is so that erica can test validation and compare what she's doing with what rich did.

    public void placeComputerShips() {
        Ship.Ships ship;
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            ship = Ship.getShipByIndex(i);
            ArrayList<ArrayList<String>> possibleCoords = ship.getPossibleCoordinates();
            ArrayList<ArrayList<String>> finalCoords = ship.getPossibleCoordinates();
            //Remove all invalid placements
            for (int coord = 0; coord < possibleCoords.size(); coord++) {
                ArrayList<String> coordinate = possibleCoords.get(coord);
                if (!validatePlacement(ship, coordinate.get(0), coordinate.get(1))) {
                    finalCoords.remove(possibleCoords.get(coord));
                }
            }

            ArrayList<String> randomPlacement = finalCoords.get(rand.nextInt(possibleCoords.size()));
            ArrayList<ArrayList<String>> coordinate = getCurrentPlayerShips();
            coordinate.add(randomPlacement);
            System.out.println(coordinate);
            setCurrentPlayerShips(coordinate);
        }
        // TODO: 4/13/16 Some randomization to place and store the position of computer ships
    }

    private void placeHumanShips() {
        Ship.Ships ship = Ship.getShipByIndex(turns);

        Scanner input = new Scanner(System.in);
        System.out.printf("Input Coordinates For %s (length : %s) : ", ship.getName(), ship.getLength());
        String coordinates = input.nextLine();
        System.out.printf("Input A Cardinal Direction (NESW) To Place Your %s (length : %s) : ", ship.getName(), ship.getLength());
        String direction = input.nextLine();

        if (validatePlacement(ship, coordinates, direction)) {
            ArrayList<String> playerCoordinates = new ArrayList<>();
            playerCoordinates.add(coordinates);
            playerCoordinates.add(direction);
            getCurrentPlayerShips().add(playerCoordinates);
        } else {
            placeHumanShips();
        }

        // TODO: 4/13/16 Some input to request human placement. Should have boundries. Should take coord. and Direction (NESW)
    }

    private boolean validatePlacement(Ship.Ships ship, String coordinates, String direction) {
        if (!Ship.areCoordinatesValid(ship, coordinates, direction)) {
            System.out.println("The coordinates or direction you entered were not valid please enter a valid response.");
            return false;
        }

        boolean overlap = wasHit(currentPlayer, coordinates, false);
        if (overlap) {
            if (currentPlayer.getType() == Player.playerType.HUMAN) {
                System.out.println("Your requested coordinates overlap with an existing ship. Please select again.");
            }
            return false;
        }

        return true;
    }


//    private void placeComputerShips() {
//        // TODO: 4/13/16 Some randomization to place and store the position of computer ships
//        String compPlaceShips;
//        compPlaceShips = getRandomCompPlaceShips();
//    }

//    private String getRandomCompPlaceShips() {
//        Random compPlaceShips = new Random();
//        char row = (char) (compPlaceShips.nextInt(10));
//        int column = compPlaceShips.nextInt(10) + 1;
//        return String.format("%c%d", row, column);
//        //getcurrentplayerships
//        //stored in an ArrayList coordinate and direction [A1, E]
//        //char cast to string for NESW
//        //String.valueOf(char) or String.format
//        //each array list need to be placed into currentplayer arraylist in correct order large to small
//
//    }


    private void drawBoard() {
        //Draw the ships
        String board = String.format("%s%60s%n", "_____|_1_|_2_|_3_|_4_|_5_|_6_|_7_|_8_|_9_|_10_", "_____|_1_|_2_|_3_|_4_|_5_|_6_|_7_|_8_|_9_|_10_");
        char row = 'A';
        int column;
        String currentCoordinate;
        String nextCol;
        while (row <= 'J') {
            column = 1;
            currentCoordinate = String.format("%c%d", row, column);
            ArrayList<ArrayList<String>> ships = getCurrentPlayerShips();
            String nextRow = String.format("__%s__|", String.valueOf(row));
            String nextRowForTrackingHitOrMiss = String.format("%20s", String.format("__%c__|", row));
            while (column <= 10) {
                if (wasHit(currentPlayer, currentCoordinate, false)) {
                    nextCol = "_=_|";
                    nextRow += nextCol;
                    column++;
                    currentCoordinate = String.format("%c%d", row, column);
                    continue;
                }

                nextRow += "___|";
                ++column;
                currentCoordinate = String.format("%c%d", row, column);
            }
            while (column > 10 && column <= 20) {
                currentCoordinate = String.format("%c%d", row, column - 10);
                ArrayList<String> currentPlayerGuesses = getCurrentPlayerGuesses();
                ArrayList<String> currentPlayerHits = getCurrentPlayerHits();
                if (currentPlayerGuesses.indexOf(currentCoordinate) > -1) {
                    if (currentPlayerHits.indexOf(currentCoordinate) > -1) {
                        nextCol = "_x_|";
                    } else {
                        nextCol = "_o_|";
                    }
                } else {
                    nextCol = "___|";
                }
                nextRowForTrackingHitOrMiss += nextCol;
                ++column;
            }
            board += String.format("%s%20s%n", nextRow, nextRowForTrackingHitOrMiss);
            ++row;
        }

        System.out.println(board);
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

    public Player getNextPlayer() {
        return (currentPlayer.getPlayerNumber() == 1) ? player2 : player1;
    }

    public ArrayList<ArrayList<String>> getCurrentPlayerShips() {
        if (currentPlayer.getPlayerNumber() == 1) {
            return player1ShipCoordinates;
        }

        return player2ShipCoordinates;
    }

    public void setCurrentPlayerShips(ArrayList<ArrayList<String>> coordinates) {
        if (currentPlayer.getPlayerNumber() == 1) {
            player1ShipCoordinates = coordinates;
        } else {
            player1ShipCoordinates = coordinates;
        }

        System.out.println(getCurrentPlayerShips());
    }

    public ArrayList<String> getCurrentPlayerHits() {
        if (currentPlayer.getPlayerNumber() == 1) {
            return player1Hits;
        }

        return player2Hits;
    }

    public ArrayList<String> getCurrentPlayerGuesses() {
        if (currentPlayer.getPlayerNumber() == 1) {
            return player1Guesses;
        }

        return player2Guesses;
    }

    public int[] getCurrentPlayerShipHits() {
        if (currentPlayer.getPlayerNumber() == 1) {
            return player1ShipHits;
        } else {
            return player2ShipHits;
        }
    }

    public void setCurrentPlayerShipHits(int[] shipHits) {
        if (currentPlayer.getPlayerNumber() == 1) {
            player1ShipHits = shipHits;
        } else {
            player2ShipHits = shipHits;
        }
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
        String compTurn;
        compTurn = getRandomCompTurn();
        boolean compTurnIsValid = validateGuess(compTurn);

        if (!compTurnIsValid) {
            throw(new RuntimeException("Erica EFFED IT UP!"));
        }

        if (wasHit(getNextPlayer(), compTurn)){
            System.out.println("HIT! :(");
            getCurrentPlayerHits().add(compTurn);
        } else {
            System.out.println("Miss!!!");
        }

        getCurrentPlayerGuesses().add(compTurn);
    }

    private String getRandomCompTurn() {
        Random compGuess = new Random();
        char row = (char) (compGuess.nextInt(10) + 65);
        int column = compGuess.nextInt(9) + 1;
        return String.format("%c%d", row, column);

    }


    private void humanTurn() {
        System.out.print("Enter coordinates for your guess : ");
        Scanner input = new Scanner(System.in);
        String guess = input.nextLine();
        boolean guessIsValid = validateGuess(guess);

        if (!guessIsValid) {
            System.out.println("You Moron look at the effing board! That doesn't even make sense!");
            humanTurn();
        }

        if (wasHit(getNextPlayer(), guess)) {
            getCurrentPlayerHits().add(guess);
        } else {
            System.out.println("Miss :( ");
        }

        getCurrentPlayerGuesses().add(guess);

    }

    private boolean validateGuess(String guess) {
        //use substring so that we can get all numbers after the first char
        Integer column = Integer.valueOf(guess.substring(1));
        char[] guessArray = guess.toCharArray();
        if (guessArray[0] >= 'A' && guessArray[0] <= 'J') {
            if (column >= 1 && column <= 10) {
                return true;
            }
        }

        return false;

    }


}
