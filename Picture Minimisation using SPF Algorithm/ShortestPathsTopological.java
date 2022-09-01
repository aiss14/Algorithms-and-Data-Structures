import java.util.Stack;

public class ShortestPathsTopological {
    private int[] parent;
    private int s;
    private double[] dist;

    public ShortestPathsTopological(WeightedDigraph G, int s) {
        // TODO
        parent=new int[G.V()];//attribute initialisations
        this.s=s;
        dist=new double[G.V()];

        //Initialisation of dist with biggest integers
        for(int i=0;i< G.V();i++){
            if(i==s) dist[s]=0;
            else dist[i]=Integer.MAX_VALUE;
            parent[i]=-1;
        }

        TopologicalWD work= new TopologicalWD(G);
        work.dfs(s);

        while(work.order().isEmpty()==false){
            int elem=work.order().pop();
            //for every stack element (elem which is every node) we take every edge it has from the adjcacency list(G.incident)
            // in the for loop and apply
            // the relax methode in order to update every edge

            for (DirectedEdge e : G.incident(elem)){
                relax(e);
            }


        }


    }

    public void relax(DirectedEdge e) {
        // TODO
        //just copied the pseudocode in the Watch Chapter videos
        int v=e.from();
        int w=e.to();
        if((dist[v]+e.weight()) < dist[w]){
            dist[w]= dist[v]+e.weight();
            parent[w]=v;

        }
    }

    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }
}

