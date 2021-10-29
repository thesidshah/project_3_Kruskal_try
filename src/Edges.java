import java.util.Objects;

class Edges {


  public int getSrc() {
    return src;
  }

  public void setSrc(int src) {
    this.src = src;
  }

  public int getDest() {
    return dest;
  }

  public void setDest(int dest) {
    this.dest = dest;
  }

  private int src, dest;

  public Edges(int src, int dest) {

    this.src = src;
    this.dest = dest;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Edges edges = (Edges) o;
    return edges.src == src && edges.dest == dest
        || edges.src == dest && edges.dest == src;
  }

  @Override
  public int hashCode() {
    return Objects.hash(src + dest);
  }
}