package dungeonGame;

import java.util.ArrayList;
import java.util.List;

public class ActualPlayer implements Player {
  List<Treasure> treasureCollected;
  static Position p;
  ActualDungeon aDungeon;

  ActualPlayer(ActualDungeon aDungeon){
    treasureCollected = new ArrayList<>();
    this.aDungeon = aDungeon;
  }
  public List<Treasure> getTreasureCollected() {
    return treasureCollected;
  }

  public void setTreasureCollected(ArrayList<Treasure> treasureCollected) {
    this.treasureCollected = treasureCollected;
  }

  public Position getPosition() {
    return p;
  }

  public void setPosition(Position p) {
    this.p = p;
  }

  @Override
  public String getState() {
    if(treasureCollected != null && !treasureCollected.isEmpty()) {
      return treasureCollected.toString()
          + "Current dungeonGame.Position is " + p + " " + aDungeon.getId(p);
    }
    else {
      return "No dungeonGame.Treasure collected, current position is " + p;
    }
  }

  @Override
  public boolean pickTreasure() {
    if(!aDungeon.dungeon[p.x][p.y].hasTreasure()) {
      return false;
    }
    Treasure t = aDungeon.dungeon[p.x][p.y].pickTreasure();
    treasureCollected.add(t);
    return true;
  }
}
