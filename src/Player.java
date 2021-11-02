public interface Player {
  String getState();

  void setPosition(Position p);

  boolean pickTreasure();

  Position getPosition();
}
