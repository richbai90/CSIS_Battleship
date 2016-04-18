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


    private boolean wasHit(Player player, String guess) {
        final int AIRCRAFT = 0;
        final int BATTLESHIP = 1;
        final int SUBMARINE = 2;
        final int DESTROYER = 3;
        final int PATROL = 4;
        ArrayList playerShips = (player.getPlayerNumber() == 1) ? player1ShipCoordinates : player2ShipCoordinates;
        ArrayList shipDetails;
        String shipCoord;
        String shipDirection;
        boolean shipWasHit = false;
        int ship = 0;

        while (ship < playerShips.size() && !shipWasHit) {

            shipDetails = (ArrayList) playerShips.get(ship);
            shipCoord = shipDetails.get(0).toString();
            shipDirection = shipDetails.get(1).toString();

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

        return false;
    }

    private boolean shipsLeft(Player player) {
        //make code to determine how many ships are left
        return false;
    }

    @Override
    public void nextTurn() {
        while (turns < 5) {
            if (currentPlayer.getType() == Player.playerType.COMPUTER) {
                placeComputerShips(currentPlayer);
                currentPlayer = player2;
            } else {
                placeHumanShips(currentPlayer);
                turns++;
            }

        }
        if (phase == Phases.PLACEMENT) {
            phase = Phases.GUESSING;
        }

        // TODO: 4/13/16 Determine if currentplayer is human/computer and call appropriate method. Switch current player.
        // TODO: 4/13/16 Switch the game phase after ships are placed after start after guesses

    }

    private void placeComputerShips(Player player) {
        // TODO: 4/13/16 Some randomization to place and store the position of computer ships
    }

    private void placeHumanShips(Player player) {
        Ship.Ships ship;
        switch (turns) {
            case 0:
                ship = Ship.Ships.AIRCRAFT;
                break;
            case 1:
                ship = Ship.Ships.BATTLESHIP;
                break;
            case 2:
                ship = Ship.Ships.SUBMARINE;
                break;
            case 3:
                ship = Ship.Ships.DESTROYER;
                break;
            case 4:
                ship = Ship.Ships.PATROL;
                break;
            default:
                throw new IllegalArgumentException("turns > 4 should never happen in placeHumanShips method");
        }


        Scanner input = new Scanner(System.in);
        System.out.printf("Input Coordinates For %s : ", ship.getName());
        String coordinates = input.nextLine();
        System.out.printf("Input A Cardinal Direction (NESW) To Place Your %s : ", ship.getName());
        String direction = input.nextLine();

        if (!Ship.areCoordinatesValid(ship, coordinates, direction)) {
            System.out.println("The coordinates or direction you entered were not valid please enter a valid response.");
            placeHumanShips(player);
        }

        ArrayList<String> playerCoordinates = new ArrayList<>();
        playerCoordinates.add(coordinates);
        playerCoordinates.add(direction);

        boolean overlap = false;

        if (player.getPlayerNumber() == 1) {
            for (ArrayList<String> player1ShipCoordinate : player1ShipCoordinates) {
                overlap = Ship.wasShipHit(ship, coordinates, player1ShipCoordinate.get(0), player1ShipCoordinate.get(1));
            }
            if (overlap) {
                System.out.println("Your requested coordinates overlap with an existing ship. Please select again.");
                placeHumanShips(player);
            }
            player1ShipCoordinates.add(playerCoordinates);
        } else {
            System.out.println("Your requested coordinates overlap with an existing ship. Please select again.");
            for (ArrayList<String> player2ShipCoordinate : player2ShipCoordinates) {
                overlap = Ship.wasShipHit(ship, coordinates, player2ShipCoordinate.get(0), player2ShipCoordinate.get(1));
            }
            if (overlap) {
                placeHumanShips(player);
            }
            player2ShipCoordinates.add(playerCoordinates);
        }

        // TODO: 4/13/16 Some input to request human placement. Should have boundries. Should take coord. and Direction (NESW)
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
