/*****************************************
 * Author : rich
 * Date : 4/13/16
 * Assignment: test
 ******************************************/
public class test {
    public static void main(String[] args) {
//        Integer i = 1;
//        System.out.print(Ship.wasShipHit(Ship.Ships.AIRCRAFT,"A6","A1","E"));
        Player player1 = new Player("player1", Player.playerType.HUMAN, 1);
        Player player2 = new Player("player1", Player.playerType.COMPUTER, 2);
        Battleship battleship = new Battleship(player1, player2);
        battleship.nextTurn();
//        System.out.print(battleship.wasHit(player1, "A4"));

    }
}
