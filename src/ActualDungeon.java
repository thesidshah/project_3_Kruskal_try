
import java.util.HashSet;
import java.util.Set;


public class ActualDungeon {
  public Cave [][] dungeon;
  static int [] size;
  Set<Edges> validEdges;

  public Set<Edges> getValidEdges() {
    return validEdges;
  }

  public void setValidEdges(Set<Edges> validEdges) {
    this.validEdges = validEdges;
  }

  ActualDungeon(int rows, int cols) {
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
  }

  protected void generateDungeon() {
    genNonWrapEdges();
  }

  private boolean ifValidContainsEdge(Edges actualEdge) {
    boolean flag = false;
    for (Edges ed : validEdges){
      if (ed.equals(actualEdge)) {
        flag = true;
        break;
      }
    }
    return flag;
  }
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

//          if(ifValidContainsEdge(actualEdge)) {
//            continue;
//          }
          validEdges.add(actualEdge);
        }
      }
    }
  }

  String displayDungeon() {
    for(int i = 0; i < size[0]; i++) {
      for(int j = 0; j < size[1]; j++) {

      }
    }
    return null;
  }
}
