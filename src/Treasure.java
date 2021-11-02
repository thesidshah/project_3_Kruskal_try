/**
 * for storing different types of treasures of the game.
 * Keeping the class package private.
 */
enum Treasure {
//  int value;
//  String name;
//
//  public int getValue() {
//    return value;
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public Treasure(int value, String name) {
//    this.value = value;
//    this.name = name;
  Emeralds(20), Gold(30), Rubies(40);
  private int value;

  Treasure(int value) {
    this.value = value;
  }
  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.name() + value + " ";
  }
}
