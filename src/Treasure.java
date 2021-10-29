/**
 * for storing different types of treasures of the game.
 * Keeping the class package private.
 */
class Treasure {
  int value;
  String name;

  public int getValue() {
    return value;
  }

  public String getName() {
    return name;
  }

  public Treasure(int value, String name) {
    this.value = value;
    this.name = name;
  }
}
