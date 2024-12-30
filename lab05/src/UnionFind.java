public class UnionFind {
    // TODO: Instance variables
    private int[] parent;
    /* The indices of the array represent the elements of our set.
    The value at an index is the set number it belongs to. */
    private int[] size;
    /* The value at an index is the size of the set that index belongs to. */

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        size = new int[N+1]; // size array to be one larger than N
        parent = new int[N+1]; // parent array to be one larger than N
        for (int i = 0; i <= N; i++) {
            size[i] = 1; // each set is size 1 to begin
            parent[i] = i; // each item is its own parent
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int root = find(v); // root of value v is the head of the tree
        return size[root]; // maybe I can just call size on v?
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (parent[v] == v) { // if parent of v is itself
            return -size[v]; // then return -size of its tree
        }
        return parent[v]; // otherwise call parent again
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2); // if they belong to the same tree will be true
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Not allowed to do that buddy");
        }
        if (parent[v] != v) { // if parent of v is not v
            parent[v] = find(parent[v]); // then set parent of v to the root of tree v is in
        }
        return parent[v]; // return the root of the tree
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int v1_parent = find(v1); // v1_parent = parent of v1
        int v2_parent = find(v2); // v2_parent = parent of v2

        if (v1_parent != v2_parent) {
            if (sizeOf(v1_parent) > sizeOf(v2_parent)) { // if size of v1 tree > size of v2 tree
                parent[v2_parent] = v1_parent; // parent at v1 = parent of v2
                size[v1_parent] += size[v2_parent]; // size of v2 parent += size of v1 parent
            } else { // size of v1 <= size of v2 tree
                parent[v1_parent] = v2_parent; // parent at v2 = parent of v1
                size[v2_parent] += size[v1_parent]; // size of v1 parent += size of v2 parent
            }
        }
    }

}
