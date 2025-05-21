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
  private Difficulty difficulty;
  private Colour lastHumanColour;
  private LeastUsedColour leastUsedStrategy;
  private int lastAiScore;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    String namePlayer = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
    this.numRounds = numRounds;
    this.namePlayer = namePlayer;
    this.roundCounter = 1;
    this.strategy = DifficultyFactory.createStrategy(difficulty);
    this.difficulty = difficulty;
    this.lastHumanColour = null;
    this.lastAiScore = 0;

    if (difficulty == Difficulty.HARD) {
      this.leastUsedStrategy = new LeastUsedColour();
    }
  }

  public void setStrategy(Strategies strategy) {
    this.strategy = strategy;
  }

  public void play() {
    MessageCli.START_ROUND.printMessage(String.valueOf(roundCounter), String.valueOf(numRounds));

    while (true) {
      MessageCli.ASK_HUMAN_INPUT.printMessage();

      String input = Utils.scanner.nextLine().toUpperCase();
      String[] parts = input.split("\\s+");

      if (input == null || input.isEmpty() || parts.length != 2 || !isValidColour(parts)) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        continue;
      }

      Colour colour1 = Colour.fromInput(parts[0]);
      Colour colour2 = Colour.fromInput(parts[1]);

      // Set strategy
      if (difficulty == Difficulty.MEDIUM && roundCounter >= 2) {
        switchToAvoidStrategy();
      } else if (difficulty == Difficulty.HARD) {
        if (roundCounter == 1 || roundCounter == 2) {
          setStrategy(new RandomStrategy());
        } else if (roundCounter == 3) {
          setStrategy(leastUsedStrategy);
        } else if (roundCounter > 3) {
          if (lastAiScore > 0) {
            // Keeping previous strategy
            if (strategy instanceof LeastUsedColour) {
              setStrategy(leastUsedStrategy);
            } else {
              switchToAvoidStrategy();
            }
          } else {
            // Switching to opposite strategy
            if (strategy instanceof LeastUsedColour) {
              switchToAvoidStrategy();
            } else {
              setStrategy(leastUsedStrategy);
            }
          }
        }
      }

      Colour[] aiColours = strategy.getAiStrategy();

      // Updating usage counts for HARD difficulty
      if (difficulty == Difficulty.HARD) {
        // Tracking ai chosen colour
        leastUsedStrategy.updateAiColour(aiColours[0]);
        // Tracking human chosen colour
        leastUsedStrategy.updateHumanColour(colour1);
      }

      int playerCounter = 0;
      int aiCounter = 0;

      if (roundCounter % 3 == 0) {
        Colour powerColour = Colour.getRandomColourForPowerColour();
        MessageCli.PRINT_POWER_COLOUR.printMessage(powerColour.name());

        // Case if player wins + power colour
        if (colour2 == aiColours[0]) {
          playerCounter = 1;
          if (colour2 == powerColour) {
            playerCounter += 2;
          }
        }
        // Case if ai wins + power colour
        if (aiColours[1] == colour1) {
          aiCounter = 1;
          if (aiColours[1] == powerColour) {
            aiCounter += 2;
          }
        }
      } else {
        // Case if player wins
        if (colour2 == aiColours[0]) {
          playerCounter = 1;
        }
        // Case if ai wins
        if (aiColours[1] == colour1) {
          aiCounter = 1;
        }
      }

      MessageCli.PRINT_INFO_MOVE.printMessage(namePlayer, colour1.name(), colour2.name());
      MessageCli.PRINT_INFO_MOVE.printMessage(AI_NAME, aiColours[0].name(), aiColours[1].name());

      MessageCli.PRINT_OUTCOME_ROUND.printMessage(namePlayer, String.valueOf(playerCounter));
      MessageCli.PRINT_OUTCOME_ROUND.printMessage(AI_NAME, String.valueOf(aiCounter));

      lastHumanColour = colour1;
      lastAiScore = aiCounter;
      roundCounter++;
      break;
    }
  }

  private boolean isValidColour(String[] parts) {
    for (String part : parts) {
      Colour colour = Colour.fromInput(part);
      if (colour == null) {
        return false;
      }
    }
    return true;
  }

  private void switchToAvoidStrategy() {
    AvoidLastColour avoidStrategy = new AvoidLastColour();
    avoidStrategy.setLastHumanColour(lastHumanColour);
    setStrategy(avoidStrategy);
  }

  public void showStats() {}
}
