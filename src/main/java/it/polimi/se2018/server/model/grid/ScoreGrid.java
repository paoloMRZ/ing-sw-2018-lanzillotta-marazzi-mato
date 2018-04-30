package it.polimi.se2018.server.model.grid;

public class ScoreGrid {

    private int[] scoreLines;

    public ScoreGrid(int numberOfPlayers) {
        scoreLines = new int[numberOfPlayers];
    }

    public void updatePoints(int player, int howMuch){
        scoreLines[player] = scoreLines[player]+howMuch;
    }
}
