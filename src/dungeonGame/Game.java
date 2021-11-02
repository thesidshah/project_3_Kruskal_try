package dungeonGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
  private ActualDungeon aDungeon;
  private int row;
  private int col;
  private int doc;
  private Boolean isWrapping;
  BufferedReader br;
  Player p;

  public Game() {
    row = 0;
    col = 0;
    doc = 0;
    br = new BufferedReader(new InputStreamReader(System.in));

    isWrapping = false;
    Boolean flag = true;
    aDungeon = null;
    while (flag) {
      try {
        System.out.println("Enter the number of caves in a row for the dungeon");
        row = Integer.parseInt(br.readLine());
        System.out.println("Enter the number of columns for the dungeon");
        col = Integer.parseInt(br.readLine());
        System.out.println("Enter the degree of connectivity");
        doc = Integer.parseInt(br.readLine());
        System.out.println("Should the dungeon be wrapping? Enter true/ false");
        isWrapping = Boolean.parseBoolean(br.readLine());
        System.out.println("Enter the percentage of treasure:");
        double t = Integer.parseInt(br.readLine());
        int treasurePercent = (int)Math.ceil((row*col) * t/100);
        System.out.println("Number of treasure caves are " + treasurePercent);
        aDungeon = new ActualDungeon(col,row,doc, treasurePercent,isWrapping);
        p = new ActualPlayer(aDungeon);
        System.out.println("dungeonGame.Player position retrieved from Dungeon is :" + aDungeon.playerPosition);
        p.setPosition(aDungeon.playerPosition);
        System.out.println("dungeonGame.Player position set is "+ p.getPosition());
        flag = false;
        System.out.println(aDungeon.displayDungeon());
      }
      catch (IOException e) {
        System.out.println("The input is invalid");
        System.out.println(e.getMessage());
      }

    }
  }

  protected void movePlayer() throws IOException {

    boolean flag = false;
    System.out.println("Starting with player at position : " + aDungeon.playerPosition);
    if(aDungeon.playerPosition.x == -1 || aDungeon.playerPosition.y == -1) {
      System.out.println("dungeonGame.Player position x or y is -1");
    }
    while(!flag) {
      aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].cavePathsAvailable(aDungeon.playerPosition);

      System.out.println("Enter the direction you wish to move in:");
      System.out.println("U : up \nD : down\nR : right\nL : left");
      char dir = (char) br.read();
      br.readLine();
      flag = false;
      int x = 0;
      int y = 0;
      switch (dir) {
        case 'U':
        case 'u':
          if (aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].north_id != -1) {
            x = aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].north.x;
            y = aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].north.y;
            aDungeon.playerPosition.x = x;
            aDungeon.playerPosition.y = y;
            p.setPosition(aDungeon.playerPosition);
            flag = true;
          }
          break;
        case 'D':
        case 'd':
          if (aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].south_id != -1) {
            x = aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].south.x;
            y = aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].south.y;
            aDungeon.playerPosition.x = x;
            aDungeon.playerPosition.y = y;
            p.setPosition(aDungeon.playerPosition);
            flag = true;
          }
          break;
        case 'L':
        case 'l':
          if (aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].west_id != -1) {
            x = aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].west.x;
            y = aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].west.y;
            aDungeon.playerPosition.x = x;
            aDungeon.playerPosition.y = y;
            p.setPosition(aDungeon.playerPosition);
            flag = true;
          }
          break;
        case 'R':
        case 'r':
          if (aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].east_id != -1) {
            x = aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].east.x;
            y = aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].east.y;
            aDungeon.playerPosition.x = x;
            aDungeon.playerPosition.y = y;
            p.setPosition(aDungeon.playerPosition);
            flag = true;
          }
          break;
        default:
          continue;
      } if(!flag) {
        System.out.println("Can not move in this direction!");
      }
    }
  }
  private void pickTreasureForPlayer() throws IOException {

    if(aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y].hasTreasure()) {
      System.out.println("The cave contains treasure(s)");
      System.out.println("Do you wish to pick a treasure?");
      System.out.println("Enter Y/N:");
      String str = br.readLine();
      if(str.equalsIgnoreCase("Y")) {
        p.pickTreasure();
      }
    }
    else {
      System.out.println("dungeonGame.Treasure not found");
    }
  }

  private void options() throws IOException {

    while(aDungeon.getId(aDungeon.playerPosition) != aDungeon.end) {
      System.out.println("1. Dump dungeon on screen");
      System.out.println("2. Print player state");
      System.out.println("3. Print cave state");
      System.out.println("4. Move dungeonGame.Player");
      System.out.println("5. Pick up treasure");
      int ch = Integer.parseInt(br.readLine());
      switch(ch) {
        case 1:
          System.out.println(aDungeon.displayDungeon());
          break;
        case 2:
          System.out.println(p.getState());
          break;
        case 3:
          System.out.println(aDungeon.dungeon[aDungeon.playerPosition.x][aDungeon.playerPosition.y]);
          break;
        case 4:
          movePlayer();
          break;
        case 5:
          pickTreasureForPlayer();
          break;
        default:
          System.out.println("Error in parsing the input,"
              + "please try again");
      }
    }
  }
  public void startGame() throws IOException {
    options();
  }

  public Cave [][] getDungeon() {
    return aDungeon.getDungeon();
  }
}
