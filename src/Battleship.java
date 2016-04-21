import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

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
    private ArrayList<ArrayList<String>> player1ShipCoordinates = new ArrayList<>(); //[[A1,N]]
    private ArrayList<ArrayList<String>> player2ShipCoordinates = new ArrayList<>();
    private ArrayList<String> player1Guesses = new ArrayList<>();
    private ArrayList<String> player2Guesses = new ArrayList<>();
    private ArrayList<String> player1Hits = new ArrayList<>();
    private ArrayList<String> player2Hits = new ArrayList<>();
    private int[] player1ShipHits = new int[5];
    private int[] player2ShipHits = new int[5];
    private int[] player1ShipsLeft;
    private int[] player2ShipsLeft;

    private enum Phases {PLACEMENT, GUESSING}

    private Phases phase = Phases.PLACEMENT;

    public Battleship(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;

        //[["A1","N"]]
    }

    //hitsLeft(ship : int)


    public boolean wasHit(Player player, String guess) {
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

        return shipWasHit;
    }

    private boolean shipsLeft(Player player) {
        //make code to determine how many ships are left
        return false;
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
            nextTurn();
        }

        // TODO: 4/18/16 Implement guessing portion of the game

        if(getCurrentPlayer().getType() == Player.playerType.HUMAN) {
            humanTurn();
        }

    }

    //todo: This is so that erica can test validation and compare what she's doing with what rich did.

//    public void placeComputerShips(Player player) {
//        Ship.Ships ship;
//        Random rand = new Random();
//        for (int i = 0; i < 5; i++) {
//            ship = Ship.getShipByIndex(i);
//            ArrayList<ArrayList<String>> possibleCoords = ship.getPossibleCoordinates();
//            //Remove all invalid placements
//            for (int coord = 0; coord < possibleCoords.size(); coord++) {
//                ArrayList<String> coordinate = possibleCoords.get(coord);
//                if (!validatePlacement(ship, coordinate.get(0), coordinate.get(1))) {
//                    possibleCoords.remove(coord);
//                }
//            }
//
//            ArrayList<String> randomPlacement = possibleCoords.get(rand.nextInt(possibleCoords.size()));
//            getCurrentPlayerShips().add(randomPlacement);
//          char row = (char) (rand.nextInt(10) + 65);
//            Integer column = rand.nextInt(10);
//            System.out.println(row);
//        }
//        // TODO: 4/13/16 Some randomization to place and store the position of computer ships
//    }

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
        }

        // TODO: 4/13/16 Some input to request human placement. Should have boundries. Should take coord. and Direction (NESW)
    }

    private boolean validatePlacement(Ship.Ships ship, String coordinates, String direction) {
        if (!Ship.areCoordinatesValid(ship, coordinates, direction)) {
            System.out.println("The coordinates or direction you entered were not valid please enter a valid response.");
            placeHumanShips();
        }

        boolean overlap = wasHit(currentPlayer, coordinates);
        if (overlap) {
            System.out.println("Your requested coordinates overlap with an existing ship. Please select again.");
            placeHumanShips();
        }

        return true;
    }


    private void placeComputerShips() {
        // TODO: 4/13/16 Some randomization to place and store the position of computer ships
        String compPlaceShips;
        compPlaceShips = getRandomCompPlaceShips();
    }

    String getRandomCompPlaceShips() {
        Random compPlaceShips = new Random();
        char row = (char) (compPlaceShips.nextInt(10));
        int column = compPlaceShips.nextInt(10) + 1;
        return String.format("%c%d", row, column);
        //getcurrentplayerships
        //stored in an ArrayList coordinate and direction [A1, E]
        //char cast to string for NESW
        //String.valueOf(char) or String.format
        //each array list need to be placed into currentplayer arraylist in correct order large to small

    }


    private void drawBoard() {
        String board = String.format("%s%n", "_____|_1_|_2_|_3_|_4_|_5_|_6_|_7_|_8_|_9_|_10_");
        char row = 'A';
        Integer column;
        String currentCoordinate;
        String nextCol;
        while (row <= 'J') {
            column = 1;
            currentCoordinate = String.valueOf(row) + column;
            ArrayList<ArrayList<String>> ships = getCurrentPlayerShips();
            String nextRow = String.format("__%s__|", String.valueOf(row));
            while (column <= 10) {
                if (wasHit(currentPlayer, currentCoordinate)) {
                    nextCol = "_=_|";
                    nextRow += nextCol;
                    column++;
                    currentCoordinate = String.valueOf(row) + column;
                    continue;
                }

                nextRow += "___|";
                ++column;
                currentCoordinate = String.valueOf(row) + column;
            }
            board += String.format("%s%n", nextRow);
            ++row;
        }

        System.out.print(board);
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
    }

    String getRandomCompTurn() {
        Random compGuess = new Random();
        char row = (char) (compGuess.nextInt(10));
        int column = compGuess.nextInt(10) + 1;
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

        if(wasHit(getNextPlayer(),guess)) {
            System.out.println("HIT!");
            getCurrentPlayerHits().add(guess);
        } else {
            System.out.println("Miss :( ");
            getCurrentPlayerGuesses().add(guess);
        }

    }

    private boolean validateGuess(String guess) {
        char[] guessArray = guess.toCharArray();
        if (guessArray[0] >= 'A' && guessArray[0] <= 'J') {
            Integer column = Integer.valueOf(String.valueOf(guessArray[1]));
            System.out.println(column);
            if (column >= 1 && column <= 10) {
                return true;
            }
        }

        return false;

    }


}
