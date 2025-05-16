package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class RandomStrategy implements Strategies {

  @Override
  public Colour[] getAiStrategy() {
    Colour[] colours = new Colour[2];
    colours[0] = Colour.getRandomColourForAi();
    colours[1] = Colour.getRandomColourForAi();
    return colours;
  }
}
