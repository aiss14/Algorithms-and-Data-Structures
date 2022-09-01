import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;


public class FlowApplications {

    /**
     * cloneFlowNetwork() makes a deep copy of a FlowNetwork
     * (FlowNetwork has unfortunately no copy constructor)
     *
     * @param flowNetwork the flow network that should be cloned
     * @return cloned flow network (deep copy) with same order of edges
     */
    private static FlowNetwork cloneFlowNetwork(FlowNetwork flowNetwork) {
        int V = flowNetwork.V();
        FlowNetwork clone = new FlowNetwork(V);

//        Simple version (but reverses order of edges)
//        for (FlowEdge e : flowNetwork.edges()) {
//            FlowEdge eclone = new FlowEdge(e.from(), e.to(), e.capacity());
//            clone.addEdge(eclone);
//        }

        for (int v = 0; v < flowNetwork.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<FlowEdge> reverse = new Stack<>();
            for (FlowEdge e : flowNetwork.adj(v)) {
                if (e.to() != v) {
                    FlowEdge eclone = new FlowEdge(e.from(), e.to(), e.capacity());
                    reverse.push(eclone);
                }
            }
            while (!reverse.isEmpty()) {
                clone.addEdge(reverse.pop());
            }
        }
        return clone;
    }




    /**
     * numberOfEdgeDisjointPaths() returns the (maximum) number of edge-disjoint paths that exist in
     * an undirected graph between two nodes s and t using Edmonds-Karp.
     *
     * @param graph the graph that is to be investigated
     * @param s     node on one end of the path
     * @param t     node on the other end of the path
     * @return number of edge-disjoint paths in graph between s and t
     */

    public static int numberOfEdgeDisjointPaths(Graph graph, int s, int t) {
        FlowNetwork net= new FlowNetwork(graph.V());//first turn the graph into a flownetwork

        for(int vnode=0; vnode<net.V();vnode++){//for each graph node
            for(int wnode : graph.adj(vnode)){//for each adjacency matrix node{

               FlowEdge kante= new FlowEdge(vnode,wnode,1);// convert each edge to a
                // flow one with capacity 1 for each
                net.addEdge(kante);

            }
        }
        FordFulkerson flow = new FordFulkerson(net,s,t);//apply Ford Fulkerson
        int anzahl= (int)flow.value();
        return anzahl;
        // TODO
    }

    /**
     * edgeDisjointPaths() returns a maximal set of edge-disjoint paths that exist in
     * an undirected graph between two nodes s and t using Edmonds-Karp.
     *
     * @param graph the graph that is to be investigated
     * @param s     node on one end of the path
     * @param t     node on the other end of the path
     * @return a {@code Bag} of edge-disjoint paths in graph between s and t
     * Each path is stored in a {@code LinkedList<Integer>}.
     */

    public static Bag<LinkedList<Integer>> edgeDisjointPaths(Graph graph, int s, int t) {
        // TODO
         FlowNetwork net = new FlowNetwork(graph.V());//first turn the graph into a flownetwork
        Bag<LinkedList<Integer>> dispath = new Bag<>();
        LinkedList<FlowEdge>[] neighbour = new LinkedList[graph.V()];// array of lists to save the neighbours of each node

        for (int vnode = 0; vnode < net.V(); vnode++) {//for each graph node
            neighbour[vnode] = new LinkedList<FlowEdge>();
            for (int wnode : graph.adj(vnode)) {//for each adjacency matrix node{

                FlowEdge kante = new FlowEdge(vnode, wnode, 1);// convert each edge to a
                // flow one with capacity 1 for each
                net.addEdge(kante);// add it to the Flownet
                neighbour[vnode].add(kante);// add to the list

            }
        }// We have a Flow graph and an array of lists , each one containing the neighbours of each node
        new  FordFulkerson(net,s,t);// apply edmonds-Karp on flownetwork net
        while (neighbour[s].isEmpty() == false) {//going through all source neighbours
            FlowEdge sedge = neighbour[s].get(0);
            if ( sedge.flow()>0) neighbour[s].remove(sedge);// means the edge was already used
            else //means edge is still not used//
            {
                LinkedList<Integer> path = new LinkedList<>();
                dispath.add(path);// put the list in the return value
                path.add(s);//always add the source to the path

                neighbour[s].remove(sedge); // remove the edge so it is not used twice
                int w = sedge.to();// we start creating the path from the end node of the adjacent edge
                while (w != t) {
                    FlowEdge current = neighbour[w].get(0);//we start taking each adjacent edge to the previous endnode
                    if (current.flow() >0)
                        neighbour[w].remove(current);// remove from the list if already visited
                        //if not :
                    else {
                        path.add(w);
                        neighbour[w].remove(current);
                        w = current.to();


                    }
                }
                path.add(t);

            }

        } return dispath;

       
        


    }



    /**
     * isUnique determines for a given Flow Network that has a guaranteed minCut,
     * if that one is unique, meaning it's the only minCut in that network
     *
     * @param flowNetworkIn the graph that is to be investigated
     * @param s             source node s
     * @param t             sink node t
     * @return true if the minCut is unique, otherwise false
     */

    public static boolean isUnique(FlowNetwork flowNetworkIn, int s, int t) {
        // TODO
        boolean [] scut= new boolean[flowNetworkIn.V()];// array for first cut
        boolean [] tcut= new boolean[flowNetworkIn.V()];// array for the second
        FordFulkerson flow= new FordFulkerson(flowNetworkIn,s,t);// apply Edmonds-Karp on input

        // create a rest graph
        FlowNetwork rest= new FlowNetwork(flowNetworkIn.V());// first create a flow graph
        for(FlowEdge e: flowNetworkIn.edges())// go through each edge and modify it
        {
            FlowEdge residual =new FlowEdge(e.to(),e.from(),e.residualCapacityTo(e.to()));
            // inverse the to and from to get the restgraph
            rest.addEdge(residual);//add each residual edge to the rest graph
        }

        FordFulkerson resflow= new FordFulkerson(rest,t,s);// apply Edmonds-Karp on the rest graph
        // BUT WITH INVERSING S AND T (ausgehend von T)
        for(int i=0;i< flowNetworkIn.V();i++){
            scut[i]=flow.inCut(i);// sees if vertex i on the s side of the mincut
            tcut[i]=resflow.inCut(i);//sees if vertex i on the s side(which is the t side) of the mincut
            if (scut[i]==tcut[i]) return false;
        }
        return true;
    }


    /**
     * findBottlenecks finds all bottleneck nodes in the given flow network
     * and returns the indices in a Linked List
     *
     * @param flowNetwork the graph that is to be investigated
     * @param s           index of the source node of the flow
     * @param t           index of the target node of the flow
     * @return {@code LinkedList<Integer>} containing all bottleneck vertices
     * @throws IllegalArgumentException is flowNetwork does not have a unique cut
     */

    public static LinkedList<Integer> findBottlenecks(FlowNetwork flowNetwork, int s, int t) {
        // TODO
         LinkedList<Integer> engpass= new LinkedList<Integer>();
        FlowNetwork aafta= cloneFlowNetwork(flowNetwork);
        // first clone the flownetwork cause testing is unique will lead to a double apllication of
        //edmonds karp on the flow
        if(!isUnique(aafta,s,t)) {
            throw new IllegalArgumentException("there must be only one mincut ");
        }


        FordFulkerson edmond=new FordFulkerson(flowNetwork,s,t);// apply edmonds karp on our flow

        for(int node=0; node< flowNetwork.V();node++) {
            for (FlowEdge kante : flowNetwork.adj(node)) {
                if (engpass.contains(kante.from()) == false && edmond.inCut(kante.from()) && edmond.inCut(kante.to())                  == false)
                    // list should not contain the from node
                    // from node should belong to the mincut and to node shouldn't ==> intersection with the ST cut
                    engpass.add(kante.from());// add the from node
            }
        }
         return engpass;
    }

    public static void main(String[] args) {
/*
        // Test for Task 2.1 and 2.2 (useful for debugging!)
        Graph graph = new Graph(new In("Graph1.txt"));
        int s = 0;
        int t = graph.V() - 1;
        int n = numberOfEdgeDisjointPaths(graph, s, t);
        System.out.println("#numberOfEdgeDisjointPaths: " + n);
        Bag<LinkedList<Integer>> paths = edgeDisjointPaths(graph, s, t);
        for (LinkedList<Integer> path : paths) {
            System.out.println(path);
        }
*/

/*
        // Example for Task 3.1 and 3.2 (useful for debugging!)
        FlowNetwork flowNetwork = new FlowNetwork(new In("Flussgraph1.txt"));
        int s = 0;
        int t = flowNetwork.V() - 1;
        boolean unique = isUnique(flowNetwork, s, t);
        System.out.println("Is mincut unique? " + unique);
        // Flussgraph1 is non-unique, so findBottlenecks() should be tested with Flussgraph2
        flowNetwork = new FlowNetwork(new In("Flussgraph2.txt"));
        LinkedList<Integer> bottlenecks = findBottlenecks(flowNetwork, s, t);
        System.out.println("Bottlenecks: " + bottlenecks);
*/
    }

}

