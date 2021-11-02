package dungeonGame;

import java.io.IOException;

public class Driver {

  public static void main(String [] args) throws IOException {
    Game game = new Game();
    game.startGame();
//    Game g1 = new Game();
    Cave[][] result = game.getDungeon();
    System.out.println("size[0] :-" + result[0].length);
    System.out.println("size[1] :-" + result[1].length);
    for(int u = 0; u < result[0].length; u++) {
      for (int v = 0; v < result[1].length; v++) {
        System.out.println(result[u][v]);
      }
    }
  }
}