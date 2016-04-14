/*****************************************
 * Author : rich
 * Date : 4/13/16
 * Assignment: test
 ******************************************/
public class test {
    public static void main(String[] args) {
        battleship = new Battleship();
        while(!battleship.winner) {
            battleship.nextTurn();
        }
    }
}
