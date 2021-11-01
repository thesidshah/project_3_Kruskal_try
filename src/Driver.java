import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Driver {

  public static void main(String [] args) throws IOException {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int row = 0, col = 0, doc = 0;
    Boolean isWrapping = false;
    Boolean flag = true;
    ActualDungeon ad = null;
    while (flag) {
      try {
        System.out.println("Enter the number of caves in a row for the dungeon\n");
        row = Integer.parseInt(br.readLine());
        System.out.println("Enter the number of columns for the dungeon\n");
        col = Integer.parseInt(br.readLine());
        System.out.println("Enter the degree of connectivity\n");
        doc = Integer.parseInt(br.readLine());
        System.out.println("Should the dungeon be wrapping? Enter true/ false");
        isWrapping = Boolean.parseBoolean(br.readLine());
        ad = new ActualDungeon(col,row,doc, isWrapping);
        flag = false;
        ad.generateDungeon();
        ad.displayDungeon();

      } catch (Exception e) {
        System.out.println("The input is invalid");
      }

    }
    ad.movePlayer();
  }
}
