import java.util.Random;

public class Randomizer {
  private transient final Random generator = new Random();
  public int nextIntWithinInterval(final int lowerBound, final int upperBound) {
    // nextInt(x) delivers uniformly distributed value in [0,x-1]
    // say d = upperBound-lowerBound, then we want a
    // result lowerBound + uniform(0,d) and uniform(0,d) is delivered by nextInt(d+1)
    assert(lowerBound <= upperBound) : "parameter error, lower bound " + lowerBound + "> upper bound" + upperBound ;
    return lowerBound + generator.nextInt(upperBound - lowerBound + 1) ;
  }

  static int randId = 0;
  static int [] pseudoRandom = {0,3,4,2};
  /**
   * generates the sequence : lowerBound + 0,3,4
   * @param lowerBound
   * @param upperBound
   * @return
   */
  public int nextIntWithinIntervalKnown(final int lowerBound, final int upperBound) {

    int ret = (lowerBound + pseudoRandom[randId++]);
    if(ret > upperBound) {
      System.out.println("Current ret : " + ret);
      ret = ret % upperBound;
    }

    if(randId == 3) {
      randId = 0;
    }
    return ret;
  }
}
