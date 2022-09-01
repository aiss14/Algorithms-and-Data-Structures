import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
/**
 * Class that represents a maze with N*N junctions.
 * 
 * @author Vera Röhr
 */
public class Maze{
    private final int N;
    private Graph M;    //Maze
    public int startnode;
        
	public Maze(int N, int startnode) {
		
        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M= new Graph(N*N);
        this.startnode= startnode;
        buildMaze();
	}
	
    public Maze (In in) {
    	this.M = new Graph(in);
    	this.N= (int) Math.sqrt(M.V());
    	this.startnode=0;
    }

	
    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
		// TODO
        if (v!=w) M.addEdge(v, w);

    }
    
    /**
     * Returns true if there is an edge between 'v' and 'w'
     * @param v one vertex
     * @param w another vertex
     * @return true or false
     */
    public boolean hasEdge( int v, int w){
		// TODO
        boolean eq = M.adj(v).contains(w);
        if(v==w) return true;
        else return eq ;
    }	
    
    /**
     * Builds a grid as a graph.
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {
        Graph maze= new Graph(N*N);
        for(int i=0;i<N-1;i++){
            for(int j=0;j<N-1;j++){
                maze.addEdge(i+j*N,i+1+j*N);
                maze.addEdge(i+j*N,i+((j+1)*N));

            }
        }
        //Randwerte
        for (int i=0; i<N-1; i++){
            maze.addEdge(i+(N-1)*N, i+1+(N-1)*N);
        }
        for (int j=0; j<N-1; j++) {
            maze.addEdge(N-1+j*N, N-1+(j+1)*N);
        }
		return maze;// TODO
    }
    
    /**
     * Builds a random maze as a graph.
     * The maze is build with a randomized DFS as the Graph M.
     */
    private void buildMaze() {
		// TODO
        Graph labyrinth= mazegrid();
        RandomDepthFirstPaths rand= new RandomDepthFirstPaths(labyrinth,startnode);
        rand.randomDFS(labyrinth);
        for(int i=0;i<N*N;i++){
            if(hasEdge(rand.edge()[i],i)==false) addEdge(i,rand.edge()[i]);
        }
    }

    /**
     * Find a path from node v to w
     * @param v start node
     * @param w end node
     * @return List<Integer> -- a list of nodes on the path from v to w (both included) in the right order.
     */
    public List<Integer> findWay(int v, int w){
		// TODO
        DepthFirstPaths zebi=new DepthFirstPaths(this.M,w);
        zebi.dfs(this.M);
        List<Integer>weg=zebi.pathTo(v);
        return weg;
    }
    
    /**
     * @return Graph M
     */
    public Graph M() {
    	return M;
    }

    public static void main(String[] args) {
		// FOR TESTING
    }


}

