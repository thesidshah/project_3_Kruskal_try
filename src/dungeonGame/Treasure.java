package dungeonGame;

/**
 * for storing different types of treasures of the game.
 * Keeping the class package private.
 */
enum Treasure {

  Sapphire(20), Gold(30), Ruby(40), Diamonds(50);
  private final int value;

  Treasure(int value) {
    this.value = value;
  }
  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.name() + " " + value;
  }
}
