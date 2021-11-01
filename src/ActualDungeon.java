
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
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

  ActualDungeon(int rows, int cols, int doc, boolean isWrapping) throws IllegalArgumentException {

    if(rows < 4 || cols < 4) {
      throw new IllegalArgumentException("The number of rows and columns"
        + "should be at least 4");
    }

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
    generateStartEnd();
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

  private Position getPosition(int id) {

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
//    generateStartEnd();
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
        if(c.caveId == start && c.caveId != getId(playerPosition)) {
          System.out.print("S");
        }
        else if(c.caveId == dungeon[playerPosition.x][playerPosition.y].caveId) {
          System.out.print("P");
        }
        else if(c.caveId == end) {
          System.out.print("E");
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

  //remember to make this method private
   void generateStartEnd() {

    int count = 0;
    while(count <= 200) {
      start = (int)Math.floor(Math.random() * ((size[0] * size[1]) - 1));
      end = (int)Math.floor(Math.random() * ((size[0] * size[1]) - 1));
      System.out.println("Generated start: " + start);
      System.out.println("Generated end: " + end);
      playerPosition = getPosition(start);
//      boolean dfs = DFS(getPosition(start), 0, end);
      int bfs_dist = BFS();
//      System.out.println(String.format("For starting id %d and end id %d \n" +
//          "using DFS we get that the distance is more than 5 or not? ",start,end) + dfs);
      count++;
      System.out.println(count + ":: failed Iteration");
      if(bfs_dist >= 5) {
        System.out.println(count + ":: Iteration");
        break;
      }
    }

  }
  private int getId(Position p) {
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


  private boolean DFS(Position check, int count, int end) {
//    System.out.println("current position ->" + check);
    if(check == null) {
      return false;
    }
    if(visited.contains(getId(check))) {
      return false;
    }
    if(count >= 6 && !visited.contains(dungeon[check.x][check.y].caveId)
        && dungeon[check.x][check.y].caveId == end ) {
      return true;
    }
    visited.add(dungeon[check.x][check.y].caveId);
    return DFS(dungeon[check.x][check.y].north, count + 1, end)
        || DFS(dungeon[check.x][check.y].south, count + 1, end)
        || DFS(dungeon[check.x][check.y].east, count + 1, end)
        || DFS(dungeon[check.x][check.y].west, count + 1, end);
  }

  protected void movePlayer() throws IOException {


    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    Directions direction = Directions.West;
    Position isValid;
    int [] arr = new int[0];
    boolean flag;
    System.out.println("Starting with player at position : " + playerPosition);
    if(playerPosition.x == -1 || playerPosition.y == -1) {
      System.out.println("Player position x or y is -1");
      generateStartEnd();
    }
    while(getId(playerPosition) != end) {
      dungeonPathsAvailable(playerPosition);
      System.out.println("Enter the direction you wish to move in:");
      char dir = (char) br.read();
      br.readLine();
      flag = false;
      int x = 0;
      int y = 0;
      switch (dir) {
        case 'U':
        case 'u':
          if (dungeon[playerPosition.x][playerPosition.y].north_id != -1) {
            x = dungeon[playerPosition.x][playerPosition.y].north.x;
            y = dungeon[playerPosition.x][playerPosition.y].north.y;
            playerPosition.x = x;
            playerPosition.y = y;
            flag = true;
          }
          break;
        case 'D':
        case 'd':
          if (dungeon[playerPosition.x][playerPosition.y].south_id != -1) {
            x = dungeon[playerPosition.x][playerPosition.y].south.x;
            y = dungeon[playerPosition.x][playerPosition.y].south.y;
            playerPosition.x = x;
            playerPosition.y = y;
            flag = true;
          }
          break;
        case 'L':
        case 'l':
          if (dungeon[playerPosition.x][playerPosition.y].west_id != -1) {
            x = dungeon[playerPosition.x][playerPosition.y].west.x;
            y = dungeon[playerPosition.x][playerPosition.y].west.y;
            playerPosition.x = x;
            playerPosition.y = y;
            flag = true;
          }
          break;
        case 'R':
        case 'r':
          if (dungeon[playerPosition.x][playerPosition.y].east_id != -1) {
            x = dungeon[playerPosition.x][playerPosition.y].east.x;
            y = dungeon[playerPosition.x][playerPosition.y].east.y;
            playerPosition.x = x;
            playerPosition.y = y;
            flag = true;
          }
          break;
        default:
          continue;
      } if(!flag) {
        System.out.println("Can not move in this direction!");
      }
    displayDungeon();
    }
  }
  private void dungeonPathsAvailable(Position x) {
    System.out.println("Dungeon paths available at Position :" + x
        + "And the corresponding id is :" + getId(x));
    System.out.println("North -> " + dungeon[x.x][x.y].north);
    System.out.println("North id-> " + dungeon[x.x][x.y].north_id);
    System.out.println("South -> " + dungeon[x.x][x.y].south);
    System.out.println("South id-> " + dungeon[x.x][x.y].south_id);
    System.out.println("East -> " + dungeon[x.x][x.y].east);
    System.out.println("East id-> " + dungeon[x.x][x.y].east_id);
    System.out.println("West -> " + dungeon[x.x][x.y].west);
    System.out.println("West id-> " + dungeon[x.x][x.y].west_id);
  }
}
