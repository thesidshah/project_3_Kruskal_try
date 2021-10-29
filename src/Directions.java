/**
 * utility class.
 */

public enum Directions {
  North, South, East, West;

  public Directions randomDirection() {
    Randomizer r = new Randomizer();
    int i = r.nextIntWithinInterval(0, 3);
    switch (i) {
      case 0:
        return Directions.North;
      case 1:
        return Directions.East;
      case 2:
        return Directions.South;
      case 3:
        return Directions.West;
      default:
        throw new RuntimeException("Random variable out of bounds: " + i);
    }
  }
  //up,down,left,right
  final int [] dx = new int[]{0, 0, -1, 1};
  final int [] dy = new int[]{1, -1, 0, 0};
  int[] moveInDirection(Position p, int d) {
    switch(d) {
      case 0:
        return new int[]{p.x + dx[0],p.y + dy[0]};
      case 1:
        return new int[]{p.x + dx[1],p.y + dy[1]};
      case 2:
        return new int[]{p.x + dx[2],p.y + dy[2]};
      case 3:
        return new int[]{p.x + dx[3],p.y + dy[3]};
      default:
        throw new IllegalArgumentException("The direction passed is not valid");
    }
  }
}