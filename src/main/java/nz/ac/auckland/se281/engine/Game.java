package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;
import nz.ac.auckland.se281.model.Colour;

public class Game {
  static final String AI_NAME = "HAL-9000";
  private int numRounds;
  private int roundCounter;
  private String namePlayer;
  private Strategies strategy;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    // Initialising player name, rounds and counter
    String namePlayer = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
    this.numRounds = numRounds;
    this.namePlayer = namePlayer;
    this.roundCounter = 1;
    this.strategy = new RandomStrategy();
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
      Colour[] aiColours = strategy.getAiStrategy();

      int playerCounter = 0;
      int aiCounter = 0;

      if (roundCounter % 3 == 0 && colour2 == aiColours[0]) {
        MessageCli.PRINT_POWER_COLOUR.printMessage(Colour.getRandomColourForPowerColour());
        playerCounter = playerCounter + 2;
      }
      roundCounter++;
      MessageCli.PRINT_INFO_MOVE.printMessage(namePlayer, colour1, colour2);

      MessageCli.PRINT_INFO_MOVE.printMessage(AI_NAME, aiColours[0], aiColours[1]);

      if (colour2 == aiColours[0]) {
        playerCounter++;
      }

      if (aiColours[1] == colour1) {
        aiCounter++;
      }

      MessageCli.PRINT_OUTCOME_ROUND.printMessage(namePlayer, playerCounter);
      MessageCli.PRINT_OUTCOME_ROUND.printMessage(AI_NAME, aiCounter);
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
