package dungeonGame;

import java.util.ArrayList;
import java.util.List;

public class Cave {
//  private final boolean north;
//  private final boolean south;
//  private final boolean east;
//  private final boolean west;
  List<Treasure> treasure;
  String type;
  Position north;
  Position south;
  Position east;
  Position west;
  int north_id;
  int south_id;
  int east_id;
  int west_id;
  //Static variable used for generating ids.
  private static int id = 0;
  int caveId;
  int caveValue;

  /**
   * default constructor of a cave.
   */
  public Cave() {
    north = south = east = west = null;
    treasure = new ArrayList<>();
    caveId = id++;
    north_id = -1;
    south_id = -1;
    east_id = -1;
    west_id = -1;
    int caveValue;
  }

  public String state() {
    String st = "dungeonGame.Cave : " + caveId
        + "\nType: " + type + "\n"
        + "Treasures:" + treasure
        + "\nNorth: " + north
        + "\nSouth: " + south
        + "\nEast: " + east
        + "\nWest: " + west;
    return st;
  }

  @Override
  public String toString() {
    String st = "dungeonGame.Cave : " + caveId
        + "\nType: " + type + "\n"
        + "Treasures:" + this.treasure
        + "\nNorth: " + north
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

   void setType() {
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
      treasure = null;
    }
    else {
      type = "dungeonGame.Cave";
    }
  }
  boolean canSetTreasure() {
    return !type.equalsIgnoreCase("tunnel");
  }

  public void addTreasure(Treasure e) {
//    treasure = new ArrayList<>();
    if(treasure == null) {
      System.out.println("Inside a tunnel");
    }
    treasure.add(e);
    caveValue += e.getValue();
  }

  public Treasure pickTreasure() {
    if(this.treasure.isEmpty()) {
      System.out.println("Inside empty cave");
      return null;
    }
    System.out.println(caveId);
    System.out.println(treasure);
    Treasure e = treasure.remove(0);
    System.out.println(treasure);
    return e;
  }

  public boolean hasTreasure() {
    return treasure != null && !treasure.isEmpty();
  }

  //Package private for dungeon to call.
   void cavePathsAvailable(Position x) {
    System.out.println("Dungeon paths available at :" + caveId);
    System.out.println("North -> " + north);
    System.out.println("North id-> " + north_id);
    System.out.println("South -> " + south);
    System.out.println("South id-> " + south_id);
    System.out.println("East -> " + east);
    System.out.println("East id-> " + east_id);
    System.out.println("West -> " + west);
    System.out.println("West id-> " + west_id);
  }
}