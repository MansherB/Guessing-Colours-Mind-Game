package nz.ac.auckland.se281.engine;

public interface GameResult {
  void updateScores(int playerPoints, int aiPoints);

  void showStats();
}
