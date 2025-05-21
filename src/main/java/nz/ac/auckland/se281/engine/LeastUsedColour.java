package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class LeastUsedColour implements Strategies {
  private int[] aiColour;
  private int[] humanColour;

  public LeastUsedColour() {
    this.aiColour = new int[Colour.values().length];
    this.humanColour = new int[Colour.values().length];
  }

  public void updateAiColour(Colour colour) {
    // Using ordinal to update the count for ai's chosen colour
    aiColour[colour.ordinal()]++;
  }

  public void updateHumanColour(Colour colour) {
    // Using ordinal to update the count for humans chosen colour
    humanColour[colour.ordinal()]++;
  }

  @Override
  public Colour[] getAiStrategy() {
    Colour[] colours = new Colour[2];

    // Selecting random colour for AI
    colours[0] = Colour.getRandomColourForAi();

    // Finding humans least used colour
    int humanLeastUsed = Integer.MAX_VALUE;
    for (int counter : humanColour) {
      if (counter < humanLeastUsed) {
        humanLeastUsed = counter;
      }
    }

    // Selecting ai's guessed colour bassed on humans least used
    int colourIndex = 0;
    for (int i = 0; i < humanColour.length; i++) {
      if (humanColour[i] == humanLeastUsed) {
        colourIndex = i;
        break;
      }
    }
    // AI's guess
    colours[1] = Colour.values()[colourIndex];

    return colours;
  }
}
