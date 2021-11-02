package dungeonGame;

import java.util.Objects;

public class Position {
  int x;
  int y;
  boolean isBorder;

  Position(int x, int y) {
    this.x = x;
    this.y = y;
//    visited = false;
    isBorder = false;
//    Parent = new dungeonGame.Position(x, y);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public String toString() {
        return x + "," + y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  void randomValues() {
    x = (int) (Math.random() * ActualDungeon.size[0]);
    y = (int) (Math.random() * ActualDungeon.size[1]);
  }

  public boolean isBorder() {
    return isBorder;
  }

  public void setBorder(boolean border) {
    isBorder = border;
  }
}