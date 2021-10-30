
import org.junit.Test;

import java.io.IOException;

public class ActualDungeonTest {

  @Test
  public void testDungeonCreation() {
    ActualDungeon ad = new ActualDungeon(5,5, 3, false);
    for(int i = 0; i < 5; i++) {
      for(int k = 0; k < 5; k++) {
        System.out.print(ad.dungeon[i][k].caveId);
      }
      System.out.println();
    }
  }

  @Test
  public void testGenerateValidEdgesNonWrapping() throws IOException {
    ActualDungeon ad = new ActualDungeon(3,3, 0, false);
    ad.generateDungeon();
    for(Edges i : ad.validEdges) {
      System.out.println(i.getSrc() + "->" + i.getDest());
    }
    System.out.println(ad.validEdges.size());
    ad.displayDungeon();
    System.out.println(ad.dungeon[1][2].south_id);
  }

  @Test
  public void testMST() {
    ActualDungeon ad = new ActualDungeon(3,3, 2, true);
    ad.generateDungeon();
    Kruskal k = new Kruskal((ActualDungeon.size[0]*ActualDungeon.size[1]),ad.getValidEdges().size(), ad.getValidEdges().toArray(new Edges[0]));
    Edges [] ed = k.KruskalMST(0);
    for(Edges ed2 : ed) {
      System.out.println(ed2.getSrc()+"->"+ed2.getDest());
    }
    ad.displayDungeon();
  }
}