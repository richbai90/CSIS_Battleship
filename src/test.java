import java.util.ArrayList;

/*****************************************
 * Author : rich
 * Date : 4/13/16
 * Assignment: test
 ******************************************/
public class test {
    public static void main(String[] args) {
//        Integer i = 1;
//        System.out.print(Ship.wasShipHit(Ship.Ships.AIRCRAFT,"A6","A1","E"));
        Player player1 = new Player("player1", Player.playerType.COMPUTER, 1);
        Player player2 = new Player("player2", Player.playerType.HUMAN, 2);
        Battleship battleship = new Battleship(player1, player2);
        while (!battleship.isGameOver()) {
            battleship.nextTurn();
        }

        battleship.winner(battleship.getWinner());
//        System.out.print(battleship.wasHit(player1, "A4"));

    }
}
