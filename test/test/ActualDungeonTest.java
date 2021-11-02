package test;

import dungeonGame.*;
import org.junit.Test;


public class ActualDungeonTest {

  @Test
  public void testVisitNodesAll() {

  Game g1 = new Game();
  Cave[][] result = g1.getDungeon();

  for(int u = 0; u < result[0].length; u++) {
    for(int v = 0; v < result[1].length; v++) {
      System.out.println(result[u][v]);
    }
  }

//  Bfs g = new Bfs();
//  int v = result[0].length * result[1].length;
//  ArrayList<ArrayList<Integer>> adj =
//      new ArrayList<ArrayList<Integer>>(v);
//    for(int i = 0; i < v; i++) {
//    adj.add(new ArrayList<Integer>());
//  }
//    for(Edges ed : validEdges) {
//    g.addEdge(adj, ed.getSrc(), ed.getDest());
//  }
//  int dist = Bfs.ShortestDistance(adj, start,end,v);
//  int [] path = bfs.getPath();
  }
}