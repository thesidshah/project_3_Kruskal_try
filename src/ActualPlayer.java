import java.util.ArrayList;
import java.util.List;

public class ActualPlayer implements Player {
  List<Treasure> treasureCollected;
  static Position p;

  ActualPlayer(){
    treasureCollected = new ArrayList<>();
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
          + "Current Position is " + p + " " + ActualDungeon.getId(p);
    }
    else {
      return "No Treasure collected, current position is " + p;
    }
  }

  @Override
  public boolean pickTreasure() {
    if(!ActualDungeon.dungeon[p.x][p.y].hasTreasure()) {
      return false;
    }
    Treasure t = ActualDungeon.dungeon[p.x][p.y].pickTreasure();
    treasureCollected.add(t);
    return true;
  }
}
