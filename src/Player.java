/**Daniel Humeniuk
 * CSIS 1400 5:00p.m.-6:50p.m. MW
 * Group Project
 */
public class Player {

        public enum playerType{HUMAN, COMPUTER}
        public String name;
        private int wins;
        private int score;
        private playerType type;
        private int playerNumber;

        public Player(String name, int PlayerType, int playerNumber)
        {

        }

        public String getName()
        {
            return name;
        }

        public int getWins()
        {
            return wins;
        }

        public int getScore()
        {
            return score;
        }

        public void setName()
        {
            this.name = name;
        }

        public void setWins()
        {
            this.wins = wins;
        }

        public void setScore()
        {
            this.score = score;
        }

        public void setPlayerNumber()
        {
            this.playerNumber = playerNumber;
        }

        public int getPlayerNumber()
        {
            return playerNumber;
        }



}
