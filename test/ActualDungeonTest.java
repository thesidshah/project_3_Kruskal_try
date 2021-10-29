
import org.junit.Test;

import java.io.IOException;

public class ActualDungeonTest {

  @Test
  public void testDungeonCreation() {
    ActualDungeon ad = new ActualDungeon(5,5);
    for(int i = 0; i < 5; i++) {
      for(int k = 0; k < 5; k++) {
        System.out.print(ad.dungeon[i][k].caveId);
      }
      System.out.println();
    }
  }
  @Test
  public void testGenerateValidEdgesNonWrapping() throws IOException {
    ActualDungeon ad = new ActualDungeon(3,3);
    ad.generateDungeon();
    for(Edges i : ad.validEdges) {
      System.out.println(i.getSrc() + "->" + i.getDest());
    }
    System.out.println(ad.validEdges.size());
  }
  @Test
  public void testMST() {
    ActualDungeon ad = new ActualDungeon(4,3);
    ad.generateDungeon();
    Kruskal k = new Kruskal((ActualDungeon.size[0]*ActualDungeon.size[1]),ad.getValidEdges().size(), ad.getValidEdges().toArray(new Edges[0]));
    k.KruskalMST(5);
  }
}