package Reversi;

import javafx.scene.control.Label;

public class PlayersScore {
    private static Label humanScore = new Label();
    private static Label computerScore = new Label();
    private static String nick;

    public static Label getHumanScore() {
        return humanScore;
    }

    public void setPlayerScore(Label humanScore) {
        this.humanScore = humanScore;
    }

    public static Label getComputerScore() {
        return computerScore;
    }

    public void setComputerScore(Label computerScore) {
        this.computerScore = computerScore;
    }

    public static String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
