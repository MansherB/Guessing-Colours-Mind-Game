package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;

public class Game {
  public static String AI_NAME = "HAL-9000";
  private int numRounds;
  private int roundCounter;
  private String namePlayer;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    String namePlayer = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
    this.numRounds = numRounds;
    this.namePlayer = namePlayer;
    this.roundCounter = 1;
  }

  public void play() {
    MessageCli.START_ROUND.printMessage(roundCounter, numRounds);

    MessageCli.ASK_HUMAN_INPUT.printMessage();

    String input = Utils.scanner.nextLine();

    String[] parts = input.split("\\s+");

    if (input == null || input.isEmpty() || parts.length != 2) {
      MessageCli.INVALID_HUMAN_INPUT.printMessage();
    } else {
      roundCounter++;
      MessageCli.PRINT_INFO_MOVE.printMessage(namePlayer, parts[0], parts[1]);
    }
  }

  public void showStats() {}
}
