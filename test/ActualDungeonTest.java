
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

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
    ActualDungeon ad = new ActualDungeon(3,3, 14, true);
//    Kruskal k = new Kruskal((ad.size[0]*ad.size[1]),ad.getValidEdges().size(), ad.getValidEdges().toArray(new Edges[0]));
    ad.generateDungeon();
    Set<Edges> ed = ad.validEdges;
    for(Edges ed2 : ed) {
      System.out.println(ed2.getSrc()+"->"+ed2.getDest());
    }
    ad.displayDungeon();
  }

  @Test
  public void testStartEnd() {
    ActualDungeon ad = new ActualDungeon(4,4,16,false);
    ad.generateDungeon();
    ad.displayDungeon();
  }
}