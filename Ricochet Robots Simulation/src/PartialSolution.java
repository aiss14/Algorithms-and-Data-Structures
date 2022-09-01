import java.util.LinkedList;

/**
 * ParitialSolution provides at least the functionality which is required
 * for the use in searching for solutions of the game in a search tree.
 * It can store a game situation (Board) and a sequence of mooves.
 */
public class PartialSolution implements Comparable<PartialSolution>{
    public Board puzzle;
    public LinkedList<Move> moves;
   //copied from blatt10
    public PartialSolution(Board board) {
        // TODO 1.2 PartialSolution(board)
        puzzle= new Board(board);
        moves=new LinkedList<Move>();

    }

    /**
     * Copy constructor, generates a deep copy of the input
     *
     * @param that The partial solution that is to be copied
     */
    public PartialSolution(PartialSolution that) {
        // TODO 1.2 PartialSolution(PartialSolution)
        puzzle= new Board(that.puzzle);
        this.moves=new LinkedList<>(that.moves);
    }

    /**
     * Performs a move on the board of the partial solution and updates
     * the cost.
     *
     * @param move The move that is to be performed
     */
    public void doMove(Move move) {
        // TODO 1.2 doMove
        puzzle.doMove(move);//first apply the move
        moves.addLast(move);//add the move to the list

    }

    /**
     * Tests whether the solution has been reached, i.e. whether
     * current board is in the goal state.
     *
     * @return {@code true}, if the board is in goal state
     */
    public boolean isSolution() {
        // TODO 1.2 isSolution
        return puzzle.targetReached();// apply the API in Board
    }
    /**
     * Compares partial solutions based on their cost.
     * (For performance reasons, the costs should be pre-calculated
     * and stored for each partial solution, rather than computed
     * here each time anew.)
     *
     * @param that the other partial solution
     * @return result of cost comparistion between this and that
     */
    public int compareTo(PartialSolution that) {
        // TODO 1.2 compareTo
        return Integer.compare(this.moves.size(), that.moves.size());
    }



    /**
     * Generates all possible moves on the current board, <em>except</em>
     * the move which would undo the previous move (if there is one).
     *
     * @return moves to be considered in the current situation
     */
    public Iterable<Move> validMoves() {
        // TODO 1.2 validMoves
        return puzzle.validMoves();//apply validmoves on the last one
    }



    /* TODO */
    /* Add object variables, constructors, methods as required and desired.      */

    /**
     * Return the sequence of moves which resulted in this partial solution.
     *
     * @return The sequence of moves.
     */
    public LinkedList<Move> moveSequence() {
        /* TODO */ return moves;
    }

    @Override
    public String toString() {
        String str = "";
        int lastRobot = -1;
        for (Move move : moveSequence()) {
            if (lastRobot == move.iRobot) {
                str += " -> " + move.endPosition;
            } else {
                if (lastRobot != -1) {
                    str += ", ";
                }
                str += "R" + move.iRobot + " -> " + move.endPosition;
            }
            lastRobot = move.iRobot;
        }
        return str;
    }
}

