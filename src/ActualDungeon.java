
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ActualDungeon {
  public Cave [][] dungeon;
  static int [] size;
  Set<Edges> validEdges;
  boolean isWrapping;
  int doc;
  Position playerPosition;
  List<Position> treasure;
  int start;
  int end;

  public Set<Edges> getValidEdges() {
    return validEdges;
  }

  public void setValidEdges(Set<Edges> validEdges) {
    this.validEdges = validEdges;
  }

  ActualDungeon(int rows, int cols, int doc, boolean isWrapping) {
    dungeon = new Cave[rows][cols];
    validEdges = new HashSet<>();
    size = new int[2];
    start = 0;
    end = 0;
    size[0] = rows;
    size[1] = cols;
    treasure = new ArrayList<>();
    playerPosition = new Position(-1,-1);
    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < cols; k++) {
        dungeon[i][k] = new Cave();
      }
    }
    this.isWrapping = isWrapping;
    this.doc = doc;
  }

  protected void generateDungeon() {
    if(!isWrapping) {
      genNonWrapEdges();
    }
    else {
      genWrapEdges();
    }
    Kruskal k = new Kruskal(size[0]*size[1],validEdges.size(),validEdges.toArray(new Edges[0]));
    Edges [] pathways = k.KruskalMST(doc);
    updateDungeon(pathways);


    validEdges = Stream.of(pathways).collect(Collectors.toSet());
  }

//  /**
//   * I am still thinking of applying checks here.
//   * Will go through this if wrapping dungeon fails.
//   * @param pathways
//   */
//  private void updateDungeonNonWrap(Edges [] pathways) {
//    Position x2 = getPosition(0);
//    Position x1 = getPosition(6);
//
//    System.out.println(x1 +"->"+x2);
//    System.out.println(x2 +"->"+x1);
//
//    for(Edges e : pathways) {
//      Position x = getPosition(e.getSrc());
//      Position y = getPosition(e.getDest());
//      if(x.x == y.x) {
//        if(x.y > y.y) {
//          dungeon[x.x][x.y].east = y;
//          dungeon[x.x][x.y].east_id = dungeon[y.x][y.y].caveId;
//
//          dungeon[y.x][y.y].west_id = dungeon[x.x][x.y].caveId;
//          dungeon[y.x][y.y].west = x;
//        }
//         if(x.y < y.y) {
//          dungeon[x.x][x.y].west = y;
//          dungeon[x.x][x.y].west_id = dungeon[y.x][y.y].caveId;
//
//           dungeon[y.x][y.y].east_id = dungeon[x.x][x.y].caveId;
//           dungeon[y.x][y.y].east = x;
//        }
//      }
//       if(x.y == y.y) {
//           if (x.x > y.x) {
//             if(y.x != 0 || x.x!= 0) {
//               dungeon[x.x][x.y].north = y;
//               dungeon[x.x][x.y].north_id = dungeon[y.x][y.y].caveId;
//
//
//               dungeon[y.x][y.y].south_id = dungeon[x.x][x.y].caveId;
//               dungeon[y.x][y.y].south = x;
//             }
//             else{
//               if(y.y == 0)
//               System.out.println("Inside y = 0");
//               if(x.x == 0)
//                 System.out.println("Inside x = 0");
//             }
//           }
//           if (x.x < y.x) {
//             dungeon[x.x][x.y].south = y;
//             dungeon[x.x][x.y].south_id = dungeon[y.x][y.y].caveId;
//
//             dungeon[y.x][y.y].north_id = dungeon[x.x][x.y].caveId;
//             dungeon[y.x][y.y].north = x;
//           }
//
//      }
//    }
//  }
  private void updateEast(Position x, Position y) {
    dungeon[x.x][x.y].east = y;
    dungeon[x.x][x.y].east_id = dungeon[y.x][y.y].caveId;

    dungeon[y.x][y.y].west_id = dungeon[x.x][x.y].caveId;
    dungeon[y.x][y.y].west = x;
  }

  private void updateWest(Position x, Position y) {
    dungeon[x.x][x.y].west = y;
    dungeon[x.x][x.y].west_id = dungeon[y.x][y.y].caveId;

    dungeon[y.x][y.y].east_id = dungeon[x.x][x.y].caveId;
    dungeon[y.x][y.y].east = x;
  }

  private void updateNorth(Position x, Position y) {
    dungeon[x.x][x.y].north = y;
    dungeon[x.x][x.y].north_id = dungeon[y.x][y.y].caveId;


    dungeon[y.x][y.y].south_id = dungeon[x.x][x.y].caveId;
    dungeon[y.x][y.y].south = x;
  }

  private void updateSouth(Position x, Position y) {
    dungeon[x.x][x.y].south = y;
    dungeon[x.x][x.y].south_id = dungeon[y.x][y.y].caveId;

    dungeon[y.x][y.y].north_id = dungeon[x.x][x.y].caveId;
    dungeon[y.x][y.y].north = x;
  }

  private void updateDungeon(Edges [] pathways) {
//    System.out.println("Inside update dungeon wrapped");
    for(Edges e : pathways) {
      Position x = getPosition(e.getSrc());
      Position y = getPosition(e.getDest());
      if(x.x == y.x) {
        if(x.y == 0 && y.y == size[1]-1){
//          System.out.println("For "+ x + "->" + y);
          updateWest(x,y);
//          System.out.println("West id is:-");
//          System.out.println(dungeon[x.x][x.y].west_id);
//          System.out.println("East id is:-");
//          System.out.println(dungeon[y.x][y.y].east_id);
        }
        else if(x.y == size[1]-1 && y.y == 0) {
//          System.out.println("For "+ x + "->" + y);
          updateEast(x,y);
//          System.out.println("East id is:-");
//          System.out.println(dungeon[x.x][x.y].east_id);
//          System.out.println("West id is:-");
//          System.out.println(dungeon[y.x][y.y].west_id);
        }
        else {
          if(x.y < y.y) {
            updateEast(x,y);
//            System.out.println("For "+ x + "->" + y);
//            updateEast(x,y);
//            System.out.println("East id is:-");
//            System.out.println(dungeon[x.x][x.y].east_id);
          }
          else {
            updateWest(x,y);
//            System.out.println("For "+ x + "->" + y);
//            updateWest(x,y);
//            System.out.println("West id is:-");
//            System.out.println(dungeon[x.x][x.y].west_id);
          }
        }
      }
      if(x.y == y.y) {
        if(x.x == 0 && y.x == size[0]-1) {
          updateNorth(x,y);
        }
        else if(x.x == size[0] - 1 && y.x == 0) {
          updateSouth(x,y);
        }
        else {
          if (x.x > y.x) {
            updateNorth(x, y);
          }
          if (x.x < y.x) {
            updateSouth(x, y);
          }
        }
      }
    }
  }

  private void generateStartEnd() {
    start = 0;
    end = 1;
    playerPosition = getPosition(0);
  }
  private Position getPosition(int id) {
    int row;
    if(id == 0) {
      row = 0;
    } else {
      row = (int) Math.floor(id / (size[0]));
    }
    for(int j = 0; j < size[1]; j++) {
      if(dungeon[row][j].caveId == id) {
        return new Position(row,j);
      }
    }
    throw new IllegalArgumentException("Id does not exist");
  }
  private void genWrapEdges() {
    for(int i = 0; i < size[0]; i++) {
      for(int j = 0; j < size[1]; j++) {
        for (int dir = 0; dir < 4; dir++) {
          Position p = new Position(i, j);
          Directions d = Directions.North;
          int [] check = d.moveInDirection(p,dir);
          for (int c = 0; c < 2; c++) {
            if (check[c] == -1) {
              check[c] = size[1] - 1;
            }
            if (check[c] == size[1]) {
              check[c] = 0;
            }
          }
          Edges actualEdge = new Edges(dungeon[i][j].caveId, dungeon[check[0]][check[1]].caveId);
          validEdges.add(actualEdge);
        }
      }
    }
  }

//  private boolean ifValidContainsEdge(Edges actualEdge) {
//    boolean flag = false;
//    for (Edges ed : validEdges){
//      if (ed.equals(actualEdge)) {
//        flag = true;
//        break;
//      }
//    }
//    return flag;
//  }
  
  private void genNonWrapEdges() {
    for (int i = 0; i < size[0]; i++) {
      for (int j = 0; j < size[1]; j++) {
        for(int di = 0; di < 4; di ++) {
          Position p = new Position(i, j);
          Directions dir = Directions.North;
          int[] check = dir.moveInDirection(p,di);
          if(check[0] < 0 || check[0] >= size[0] || check[1] < 0 || check[1] >= size[1]) {
            continue;
          }
          Edges actualEdge = new Edges(dungeon[i][j].caveId, dungeon[check[0]][check[1]].caveId);
          validEdges.add(actualEdge);
        }
      }
    }
  }

  String displayDungeon() {
    generateStartEnd();
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < size[0]; i++) {
      for(int j = 0; j < size[1]; j++) {
          System.out.print(" ");
        if(dungeon[i][j].north_id != -1) {
          System.out.print("|");
        }
          //System.out.print(" ");
      }
      System.out.println();
      for(int j = 0; j < size[1]; j++) {
        Cave c = dungeon[i][j];
        if(c.west_id != -1) {
          System.out.print("-");
        }
        if(c.caveId == dungeon[playerPosition.x][playerPosition.y].caveId) {
          System.out.print("P");
        }
        else {
          System.out.print(c.caveId);
        }
        if(c.east_id != -1) {
          System.out.print("-");
        }
        else {
          System.out.print(" ");
        }
      }
      System.out.println();
      for(int j = 0; j < size[1]; j++) {
        System.out.print(" ");
        if(dungeon[i][j].south_id != -1) {
          System.out.print("|");
        }

      }
      System.out.println();
    }
    return null;
  }

}
