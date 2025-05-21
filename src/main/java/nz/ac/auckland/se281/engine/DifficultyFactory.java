package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;

public class DifficultyFactory {

  // Factory strategy to switch between different difficulties
  // All difficulties begin with Random, then switch to AvoidLastStrategy or LeastusedColourStrategy
  public static Strategies createStrategy(Difficulty difficulty) {
    switch (difficulty) {
      case EASY:
        return new RandomStrategy();
      case MEDIUM:
        return new RandomStrategy();
      case HARD:
        return new RandomStrategy();
      default:
        System.err.println("Wrong Difficulty");
        System.exit(0);
    }
    return null;
  }
}
