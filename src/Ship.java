/*****************************************
 * Author : rich
 * Date : 4/15/16
 * Assignment: Ship
 ******************************************/
// FIXME: 4/15/16

public abstract class Ship {
    public static enum Ships {
        AIRCRAFT(5, "Aircraft Carrier"),
        BATTLESHIP(4, "Battleship"),
        DESTROYER(3, "Destroyer"),
        SUBMARINE(3, "Submarine"),
        PATROL(2, "Patrol Boat");

        private final int length;
        private final String name;

        private Ships(int length, String name) {
            this.length = length;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getLength() {
            return length;
        }
    }


    public static boolean areCoordinatesValid(Ships ship, String coords, String direction) {
        boolean valid = false;
        valid = areCoordinatesValid(ship.length - 1, coords, direction);
        return valid;
    }

    public static boolean wasShipHit(Ships ship, String guess, String shipCoords, String shipDirection) {
        return areCoordinatesValid(ship, shipCoords, shipDirection) && wasShipHit(ship.length, guess, shipCoords, shipDirection);
    }

    private static boolean wasShipHit(int length, String guess, String coords, String direction) {
        Integer column = Integer.parseInt(coords.substring(1));
        char row = coords.toUpperCase().toCharArray()[0];
        char newRow = row;
        String possibleCoords;
        int i = 0;
        switch (direction.toUpperCase()) {
            case "N":
                while (i < length) {
                    newRow = (char) (row - i);
                    possibleCoords = String.valueOf(newRow) + column.toString();
                    if (possibleCoords.equalsIgnoreCase(guess)) {
                        return true;
                    }
                    i++;
                }
                break;
            case "E":
                while (i < length) {
                    possibleCoords = String.valueOf(row) + (column + i);
                    if (possibleCoords.equalsIgnoreCase(guess)) {
                        return true;
                    }
                    i++;
                }
                break;
            case "S":
                while (i < length) {
                    newRow = (char) (row + i);
                    possibleCoords = String.valueOf(newRow) + column;
                    if (possibleCoords.equalsIgnoreCase(guess)) {
                        return true;
                    }
                    i++;
                }
                break;
            case "W":
                while (i < length) {
                    possibleCoords = String.valueOf(row) + (column - i);
                    if (possibleCoords.equalsIgnoreCase(guess)) {
                        return true;
                    }
                    i++;
                }
                break;
            default:
                return false;
        }

        return false;
    }

    private static boolean areCoordinatesValid(int length, String coords, String direction) {
        Integer column = Integer.parseInt(coords.substring(1));
        char row = coords.toUpperCase().toCharArray()[0];
        Integer endColumn;
        char endRow;

        switch (direction.toUpperCase()) {
            case "N":
                endRow = (char) (row - length);
                endColumn = column;
                break;
            case "E":
                endRow = row;
                endColumn = column + length;
                break;
            case "S":
                endRow = (char) (row + length);
                endColumn = column;
                break;
            case "W":
                endRow = row;
                endColumn = column - length;
                break;
            default:
                return false;
        }


        if (endRow >= 'A' && endRow <= 'J') {
            if (endColumn >= 1 && endColumn <= 10) {
                return true;
            }
        }

        return false;
    }
}








