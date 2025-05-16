package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;
import nz.ac.auckland.se281.model.Colour;

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

    while (true) {
      MessageCli.ASK_HUMAN_INPUT.printMessage();

      // Getting input from user via scanner
      String input = Utils.scanner.nextLine().toUpperCase();

      // Splitting parts by space
      String[] parts = input.split("\\s+");

      if (input == null || input.isEmpty() || parts.length != 2 || !isValidColour(parts)) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        continue;
      }
      // Extracts the full colour names from switch input
      Colour colour1 = Colour.fromInput(parts[0]);
      Colour colour2 = Colour.fromInput(parts[1]);
      roundCounter++;
      MessageCli.PRINT_INFO_MOVE.printMessage(namePlayer, colour1, colour2);
      break;
    }
  }

  // Validating each part using Colour.fromInput
  private boolean isValidColour(String[] parts) {
    for (String part : parts) {
      Colour colour = Colour.fromInput(part);
      if (colour == null) {
        return false;
      }
    }
    return true;
  }

  public void showStats() {}
}
