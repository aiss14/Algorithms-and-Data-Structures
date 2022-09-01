import java.util.Stack;

public class ContentAwareImageResizing {
    public int sx, sy;
    public Image image;

    public ContentAwareImageResizing(Image image) {
        sx = image.sizeX();
        sy = image.sizeY();
        this.image = image;
    }

    /**
     * Calculates the corresponding Node to a given coordinate (x,y)
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the Node
     */
     public int coordinateToNode(int x, int y) {
        return x + y * sx;
    }

    /**
     * Calculates the corresponding note to a given coordinate
     * @param p the Coordinate
     * @return the Node
     */
    public int coordinateToNode(Coordinate p) {
        return coordinateToNode(p.x, p.y);
    }

     /**
     * converts a node (index of a node) to a pixel coordinate
     * @param v the Node
     * @return the Coordinate
     */
    public Coordinate nodeToCoordinate(int v) {
        int y = v / sx;
        return new Coordinate(v - y * sx, y);
    }

    /** Builds up a grid graph for representing an image with contrast values as weights.
     *  add auxiliary source and target node
     *  see figure 4 on the exercises sheet
     *  @return the created graph
     */
    public WeightedDigraph makeVGraph() {
        WeightedDigraph graph = new WeightedDigraph(sx * sy + 2);
        final int delta = 1;
        for (int y = 1; y < sy; y++) {
            for (int x = 0; x < sx; x++) {
                Coordinate pFrom = new Coordinate(x, y - 1);
                int vFrom = coordinateToNode(pFrom);
                for (int d = -delta; d <= delta; d++) {
                    if (x + d >= 0 && x + d < sx) {
                        Coordinate pTo = new Coordinate(x + d, y);
                        int vTo = coordinateToNode(pTo);
                        graph.addEdge(vFrom, vTo, image.contrast(pFrom, pTo));
                    }
                }
            }
        }
        int vSource = sx * sy;
        int vTarget = sx * sy + 1;
        for (int x = 0; x < sx; x++) {
            int vTo = coordinateToNode(x, 0);
            graph.addEdge(vSource, vTo, 0);
            int vFrom = coordinateToNode(x, sy - 1);
            graph.addEdge(vFrom, vTarget, 0);
        }
        return graph;
    }
    
    /** A method to get the vertical path with the least contrast in the Image Image
     * @return the Vertical Path with the least contrast
     */
    public int[] leastContrastImageVPath() {
        // TODO
        int[] weg=new int[sy];
        WeightedDigraph grid = makeVGraph();
        ShortestPathsTopological kurz= new ShortestPathsTopological(grid, sx*sy);
        Stack<Integer>revpath=new Stack<Integer>();
        revpath= kurz.pathTo(sx*sy +1);// +1 because pathto takes the target node and goes the inverse way
        revpath.pop();//we take the final node away first
        while(revpath.size()>1){

           Coordinate p = nodeToCoordinate(revpath.pop());
           weg[p.y]=p.x;// first convert the node to a
            // coordinate with the nodetoCoor API, weg is vertical so it is filled with y coordinates , who 
            // are actually the x coordinate of every stack node          
        }


        return weg;
    }
    
    /** Removes a vertical path from the image
     * @param path
     */
    public void removeVPath(int[] path) {
        image.removeVPath(path);
        sx--;
    }

    public static void demoMatrixImage(String filename) throws java.io.FileNotFoundException {
        MatrixImage image = new MatrixImage(filename);
        ContentAwareImageResizing cair = new ContentAwareImageResizing(image);
        System.out.println("Original:");
        image.render();
        for (int k = 0; k < 2; k++) {
            cair.removeVPath(cair.leastContrastImageVPath());
            System.out.println("After removing one path:");
            image.render();
        }
    }

    public static void demoPictureImage(String filename) {
        PictureImage image = new PictureImage(filename);
        ContentAwareImageResizing cair = new ContentAwareImageResizing(image);
        int nDeletions = image.sizeX()/2;
        for (int k = 0; k < nDeletions; k++) {
            System.out.println("removing path " + k);
            cair.removeVPath(cair.leastContrastImageVPath());
            image.render();
        }
    }

    public static void main(String[] args) throws java.io.FileNotFoundException {
//        demoPictureImage(args[0]);
       demoPictureImage("640px-Broadway_tower_edit.jpg");
//        demoPictureImage("640px-foto_2017_07_30_202914-cropped.jpg");
  //      demoMatrixImage("testImage.txt");
    }
}

