import java.io.FileInputStream;
import java.util.*;

public class RicochetRobots {

    /**
     * Find the shortest move sequence for the given board situation to the goal state,
     * i.e., the designated robot has reached the target field.
     * The task is accomplished by using breadth-first-search. In order to avoid checking
     * the same situations over and over again, each investigated board is put in a hash set.
     *
     * @param board Initial configuration of the game.
     * @return The partial solution containing the the shortest move sequence to the target
     */
    public static PartialSolution bfsWithHashing(Board board) {
        /* TODO */
        // nearly the same process as the one in Astarpuzzle , only changes when it comes to hash tables
        PartialSolution current= new PartialSolution(board);//current situation
        PriorityQueue<PartialSolution> finished= new PriorityQueue<>();//queue list like Dijkstra's that contains
        //each solution(node) ;
        ArrayList<Integer>hash=new ArrayList<>();// arraylist to store different hashs, hash table
        hash.add(current.puzzle.hashCode());//add current situation's hashcode
        finished.add(current);//always add the current situation (start node)
        //start the algorithm and only stop when the queue is empty or when the solution is found
        while(finished.isEmpty()==false){
            if (finished.peek().isSolution()) return finished.poll();// return the element if it is a solution
            PartialSolution lös= finished.poll();// must store the first partial solution in a variable
            // because otherwise it will lead to an infinite loop
            hash.add(lös.puzzle.hashCode());
            for(Move valid: lös.validMoves()){ // iterate through each validmove from the partial solution
                PartialSolution copy= new PartialSolution(lös);
                copy.doMove(valid);// same process as blatt10 until here
                if(hash.contains(copy.puzzle.hashCode())==false){// here comes the only difference
                    // if the hash table still doesn't contain the hashcode of the copy then we add
                    //it to the queue and the hash table
                    finished.add(copy);
                    hash.add(copy.puzzle.hashCode());
                }

            }
        } return null; // i must put a return statement , so it returns null if no solution is found
    }

    public static void printBoardSequence(Board board, Iterable<Move> moveSequence) {
        int moveno = 0;
        for (Move move : moveSequence) {
            board.print();
            System.out.println((++moveno) + ". Move: " + move);
            board.doMove(move);
        }
        board.print();
    }

    public static void main(String[] args) throws java.io.FileNotFoundException {
 //       System.setIn(new FileInputStream("samples/rrBoard-sample00.txt"));
//        System.setIn(new FileInputStream("samples/rrBoard-sample01.txt"));
//        System.setIn(new FileInputStream("samples/rrBoard-sample02.txt"));
       System.setIn(new FileInputStream("samples/rrBoard-sample03.txt"));
        Board board = new Board(new Scanner(System.in));
        long start = System.nanoTime();
        PartialSolution sol = bfsWithHashing(board);
        long duration1 = (System.nanoTime() - start) / 1000;
        if (sol == null) {
            System.out.println("Board is unsolvable.");
        } else {
            printBoardSequence(board, sol.moveSequence());
            System.out.println("Found solution with " + sol.moveSequence().size() + " moves:\n" + sol);
            System.out.println("Computing time: " + duration1 / 1000 + " ms");
        }
    }
}

