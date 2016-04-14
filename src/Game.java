/*****************************************
 * Author : rich
 * Date : 4/11/16
 * Assignment: Game
 ******************************************/
public interface Game {
    public void nextTurn();
    public void winner(Player player);
    public Player getCurrentPlayer();
    public int getTurns();
    public void displayScore();
}
