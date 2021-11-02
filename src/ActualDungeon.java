
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ActualDungeon {
  public static Cave [][] dungeon;
  static int [] size;
  Set<Edges> validEdges;
  boolean isWrapping;
  int doc;
  Position playerPosition;
  List<Position> treasure;
  int start;
  int end;
  int treasurePercent;

  public Set<Edges> getValidEdges() {
    return validEdges;
  }

  public void setValidEdges(Set<Edges> validEdges) {
    this.validEdges = validEdges;
  }

  ActualDungeon(int rows, int cols, int doc, int treasurePercent, boolean isWrapping ) throws IllegalArgumentException {

    if(rows < 5 && cols < 5) {
      throw new IllegalArgumentException("The number of rows and columns"
        + "should be at least 5");
    }
    if(treasurePercent < 0) {
      System.out.println("The treasure% passed should be non negative.");
    }
    dungeon = new Cave[rows][cols];
    validEdges = new HashSet<>();
    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < cols; k++) {
        dungeon[i][k] = new Cave();
      }
    }
    size = new int[2];
    start = 0;
    end = 0;
    size[0] = rows;
    size[1] = cols;
    treasure = new ArrayList<>();
    playerPosition = new Position(-1,-1);
    this.isWrapping = isWrapping;
    this.doc = doc;
    this.treasurePercent= treasurePercent;
    generateDungeon();

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
    updateState();
    generateStartEnd();
    updateTreasures(treasurePercent);
//    playerPosition = getPosition(start);
//    System.out.println("Player position after updated treasure points " + playerPosition);
  }

  private void updateState() {
    for(int i = 0; i < size[0]; i++) {
      for (Cave c : dungeon[i]) {
        c.setType();
      }
    }
  }

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

    for(Edges e : pathways) {
      Position x = getPosition(e.getSrc());
      Position y = getPosition(e.getDest());
      if(x.x == y.x) {
        if(x.y == 0 && y.y == size[1]-1){
          updateWest(x,y);
        }
        else if(x.y == size[1]-1 && y.y == 0) {
          updateEast(x,y);
        }
        else {
          if(x.y < y.y) {
            updateEast(x,y);
          }
          else {
            updateWest(x,y);
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

  //package private for cave to call.
   static Position getPosition(int id) {

    if(id == 0) {
      return new Position(0,0);
    } else {
//      row = (int) Math.floor(id / (size[0]));
//      System.out.println("Calculated row is " + row);

    for(int i = 0; i < size[0]; i++) {
      for (int j = 0; j < size[1]; j++) {
        if (dungeon[i][j].caveId == id) {
          return new Position(i, j);
        }
      }
    }
    }
    throw new IllegalArgumentException("Id " + id + " does not exist");
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
                check[c] = size[c] - 1;
            }
            if (check[c] == size[1] || check[c] == size[0]) {
              check[c] = 0;
            }
          }
//          System.out.println("check[0]:"+check[0]);
//          System.out.println("check[1]:"+check[1]);
          Edges actualEdge = new Edges(dungeon[i][j].caveId, dungeon[check[0]][check[1]].caveId);
          validEdges.add(actualEdge);
        }
      }
    }
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
          validEdges.add(actualEdge);
        }
      }
    }
  }

  /**
   * Need to work on its time complexity.
   * @return String dump of dungeon for testing.
   */
  String displayDungeon() {
//    generateStartEnd();
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < size[0]; i++) {
      for(int j = 0; j < size[1]; j++) {
          sb.append(" ");
        if(dungeon[i][j].north_id != -1) {
          sb.append("|");
        }
      }
      sb.append("\n");
      for(int j = 0; j < size[1]; j++) {
        Cave c = dungeon[i][j];
        if(c.west_id != -1) {
          sb.append("-");
        }
        if(c.caveId == start && c.caveId != getId(playerPosition)) {
          sb.append("S");
        }
        else if(c.caveId == dungeon[playerPosition.x][playerPosition.y].caveId) {
          sb.append("P");
        }
        else if(c.caveId == end) {
          sb.append("E");
        }
        else {
          sb.append(c.caveId);
        }
        if(c.east_id != -1) {
          sb.append("-");
        }
        else {
          sb.append(" ");
        }
      }
      sb.append("\n");
      for(int j = 0; j < size[1]; j++) {
        sb.append(" ");
        if(dungeon[i][j].south_id != -1) {
          sb.append("|");
        }

      }
      sb.append("\n");
    }
    return sb.toString();
  }

  //remember to make this method private
  //Made it package private for cases where
  // the start and player position are not on the board.
  void generateStartEnd() {

    int count = 0;
    while(count <= 200) {
      start = (int)Math.floor(Math.random() * ((size[0] * size[1]) - 1));
      end = (int)Math.floor(Math.random() * ((size[0] * size[1]) - 1));
//      System.out.println("Generated start: " + start);
//      System.out.println("Generated end: " + end);
//      playerPosition = getPosition(start);
//      boolean dfs = DFS(getPosition(start), 0, end);
      int bfs_dist = BFS();
//      System.out.println(String.format("For starting id %d and end id %d \n" +
//          "using DFS we get that the distance is more than 5 or not? ",start,end) + dfs);
      count++;
      playerPosition = getPosition(start);
//      System.out.println("start is " + start);
//      System.out.println("Player position is "+ playerPosition);
      System.out.println(count + ":: failed Iteration");// remember to delete this
      if(bfs_dist >= 5) {
        playerPosition = getPosition(start);
        System.out.println("Start is :: "+ start);
        System.out.println(playerPosition);
        break;
      }
    }

  }
  static int getId(Position p) {
    return dungeon[p.x][p.y].caveId;
  }
  List<Integer> visited = new ArrayList<>();

  private int BFS() {
    Bfs g = new Bfs();
    int v = size[0]*size[1];
    ArrayList<ArrayList<Integer>> adj =
        new ArrayList<ArrayList<Integer>>(v);
    for (int i = 0; i < v; i++) {
      adj.add(new ArrayList<Integer>());
    }
    for(Edges ed : validEdges) {
      g.addEdge(adj, ed.getSrc(), ed.getDest());
    }
    int dist = Bfs.ShortestDistance(adj, start,end,v);
    return dist;
    }


  private void updateTreasures(int numCaves) {
    int l;
    int id;
    int count = numCaves;
    do {
      id = (int)Math.floor(Math.random() * numCaves);
      Position x = getPosition(id);
      if(dungeon[x.x][x.y].canSetTreasure()) {
        Treasure e = Treasure.Gold;
        dungeon[x.x][x.y].addTreasure(e);
        count--;
      }
    } while(count >= 0);
  }
}
