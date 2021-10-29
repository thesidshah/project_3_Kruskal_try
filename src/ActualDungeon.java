
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ActualDungeon {
  public Cave [][] dungeon;
  static int [] size;
  Set<Edges> validEdges;
  boolean isWrapping;
  int doc;

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
    size[0] = rows;
    size[1] = cols;
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
//    validEdges = Stream.of(pathways).collect(Collectors.toSet());
  }

  private void updateDungeon(Edges [] pathways) {
    for(Edges e : pathways) {
      Position x = getPosition(e.getSrc());
      Position y = getPosition(e.getDest());
      if(x.x == y.x) {
        if(x.y > y.y) {
          dungeon[x.x][x.y].east = y;
          dungeon[x.x][x.y].east_id = dungeon[y.x][y.y].caveId;

          dungeon[y.x][y.y].west_id = dungeon[x.x][x.y].caveId;
          dungeon[y.x][y.y].west = x;
        }
         if(x.y < y.y) {
          dungeon[x.x][x.y].west = y;
          dungeon[x.x][x.y].west_id = dungeon[y.x][y.y].caveId;

           dungeon[y.x][y.y].east_id = dungeon[x.x][x.y].caveId;
           dungeon[y.x][y.y].east = x;
        }
      }
       if(x.y == y.y) {
        if(x.x > y.x) {
          dungeon[x.x][x.y].north = y;
          dungeon[x.x][x.y].north_id = dungeon[y.x][y.y].caveId;


          dungeon[y.x][y.y].south_id = dungeon[x.x][x.y].caveId;
          dungeon[y.x][y.y].south = x;
        }
         if(x.x < y.x) {
          dungeon[x.x][x.y].south = y;
          dungeon[x.x][x.y].south_id = dungeon[y.x][y.y].caveId;

           dungeon[y.x][y.y].north_id = dungeon[x.x][x.y].caveId;
           dungeon[y.x][y.y].north = x;
        }
      }
    }
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
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < size[0]; i++) {
      for(int j = 0; j < size[1]; j++) {
        if(j != 0) {
          System.out.print(" ");
        }
        if(dungeon[i][j].north_id != -1) {
          System.out.print("|");
        }
          //System.out.print(" ");
      }
      System.out.println();
      for(int j = 0; j < size[1]; j++) {
        Cave c = dungeon[i][j];
        if(c.east_id != -1) {
          System.out.print("-");
        }
        System.out.print(c.caveId);
        if(c.west_id != -1) {
          System.out.print("-");
        }
        else {
          System.out.print(" ");
        }
      }
      System.out.println();
      for(int j = 0; j < size[1]; j++) {
        if(j != 0) {
          System.out.print(" ");
        }
        if(dungeon[i][j].south_id != -1) {
          System.out.print("|");
        }
        //System.out.print(" ");
      }
      System.out.println();
    }
    return null;
  }

}
