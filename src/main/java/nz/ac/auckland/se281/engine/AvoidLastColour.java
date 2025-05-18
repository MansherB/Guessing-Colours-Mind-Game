package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class AvoidLastColour implements Strategies {

  private Colour lastHumanColour;

  public void setLastHumanColour(Colour colour) {
    this.lastHumanColour = colour;
  }

  @Override
  public Colour[] getAiStrategy() {
    Colour[] colours = new Colour[2];
    // Initial random ai colour
    colours[0] = Colour.getRandomColourForAi();
    if (lastHumanColour == null) {
      // Random guess for round 1
      colours[1] = Colour.getRandomColourForAi();
    } else {
      // Exluding previously guessed colour
      colours[1] = Colour.getRandomColourExcluding(lastHumanColour);
    }
    return colours;
  }
}
