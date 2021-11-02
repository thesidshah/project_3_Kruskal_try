package dungeonGame;

public interface Dungeon {
  Cave [][] generateDungeon();
  int [] generatePath(); //or String?
  String displayDungeon();
}
