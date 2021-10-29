import java.util.ArrayList;
import java.util.List;

public class Cave {
//  private final boolean north;
//  private final boolean south;
//  private final boolean east;
//  private final boolean west;
//  private List<Treasure> treasure;
  String type;
  boolean isBorder;
  boolean isCorner;
  Position north;
  Position south;
  Position east;
  Position west;
  static int id = 0;
  int caveId;

  /**
   * default constructor of a cave.
   */
  public Cave() {
    north = south = east = west = null;
//    treasure= new ArrayList<>();
    isBorder = isCorner = false;
    caveId = id++;
  }

  public Cave(Position north, Position south, Position east, Position west) {
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
//    treasure = new ArrayList<>();
    isBorder = isCorner = false;
    caveId = id++;
  }

  public String state() {
    String st = //treasure.toString()
         "\nNorth: " + north
        + "\nSouth: " + south
        + "\nEast: " + east
        + "\nWest: " + west;
    return st;
  }

  boolean isValidMove(Directions d) {
    switch(d) {
      case North:
        if(north != null) {
          return true;
        }
      case South:
        if(south != null) {
          return true;
        }
      case East:
        if(east != null) {
          return true;
        }
      case West:
        if(west != null) {
          return true;
        }
      default:
        return false;
    }
  }

  private void setType() {
    int c = 0;
    if (north != null) {
      c++;
    }
    if (south != null) {
      c++;
    }
    if (east != null) {
      c++;
    }
    if (west != null) {
      c++;
    }
    if (c == 2) {
      type = "Tunnel";
//      treasure = null;
    }
    else {
      type = "Cave";
    }
  }
  boolean canSetTreasure() {
    return !type.equals("tunnel");
  }
}