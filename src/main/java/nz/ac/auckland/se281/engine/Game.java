package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;

public class Game {
  public static String AI_NAME = "HAL-9000";
  private int numRounds;
  private int roundCounter;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    String namePlayer = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
    this.numRounds = numRounds;
    this.roundCounter = 1;
  }

  public void play() {
    MessageCli.ASK_HUMAN_INPUT.printMessage();
    MessageCli.INVALID_HUMAN_INPUT.printMessage();
    MessageCli.START_ROUND.printMessage(roundCounter, numRounds);
    roundCounter++;
  }

  public void showStats() {}
}
