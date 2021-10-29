import java.util.ArrayList;

public class Kruskal {
  class subset {
    int parent, rank;
  }

    int V, E;
    Edges edge[];

    Kruskal(int v, int e, Edges [] actualEdges) {
      V = v;
      E = e;
      edge = actualEdges;
    }
  int find(subset subsets[], int i)
  {
    // find root and make root as parent of i
    // (path compression)
    if (subsets[i].parent != i)
      subsets[i].parent
          = find(subsets, subsets[i].parent);

    return subsets[i].parent;
  }
  // A function that does union of two sets
  // of x and y (uses union by rank)
  void Union(subset subsets[], int x, int y)
  {
    int xroot = find(subsets, x);
    int yroot = find(subsets, y);

    // Attach smaller rank tree under root
    // of high rank tree (Union by Rank)
//    if (subsets[xroot].rank
//        < subsets[yroot].rank)
//      subsets[xroot].parent = yroot;
//    else if (subsets[xroot].rank
//        > subsets[yroot].rank)
//      subsets[yroot].parent = xroot;
//
//      // If ranks are same, then make one as
//      // root and increment its rank by one
//    else {
      subsets[yroot].parent = xroot;
      subsets[xroot].rank++;
    //}
  }
  // The main function to construct MST using Kruskal's
  // algorithm
  void KruskalMST(int doc)
  {
    // Tnis will store the resultant MST
    ArrayList <Edges> result;
    ArrayList <Edges> removed;

    // An index variable, used for result[]
    int e = 0;
    int k = 0;

    // An index variable, used for sorted edges
    int i = 0;
    result = new ArrayList<>();
    removed = new ArrayList<>();

    // Allocate memory for creating V subsets
    subset subsets[] = new subset[V];
    for (i = 0; i < V; ++i)
      subsets[i] = new subset();

    // Create V subsets with single elements
    for (int v = 0; v < V; ++v)
    {
      subsets[v].parent = v;
      subsets[v].rank = 0;
    }

    i = 0; // Index used to pick next edge
    int count = 0;
    // Number of edges to be taken is equal to V-1
    if((V + doc) > edge.length) {
      throw new IllegalArgumentException("Degree of connectivity is more than "
          + "the available edges");
    }
    while (e < (V + doc))
    {
      // Step 2: Pick the smallest edge. And increment
      // the index for next iteration
      Edges next_edge = edge[i++];

      int x = find(subsets, next_edge.getSrc());
      int y = find(subsets, next_edge.getDest());

      // If including this edge does't cause cycle,
      // include it in result and increment the index
      // of result for next edge
      if (x != y) {
        result.add(next_edge);
        e++;
        Union(subsets, x, y);
        count++;
      }
      else {
        // Else save the next_edge
        removed.add(next_edge);
        e++;
        System.out.println("I am in removed");
        System.out.println(next_edge.getSrc() + "->" + next_edge.getDest());
      }
    }

    // print the contents of result[] to display
    // the built MST
    int k1 = 0;
    System.out.println("Count = " + count);
    while(k1 <= doc) {
      System.out.println("removed edge is:");
      System.out.println(removed.get(k1).getSrc() + "->" + removed.get(k1).getDest());
      result.add(removed.get(k1++));
    }
    System.out.println("Following are the edges in "
        + "the constructed MST");

    for(i=0;i<result.size();i++)
    {
        System.out.println(result.get(i).getSrc() + " -- "
            + result.get(i).getDest());
      }
    }
}
