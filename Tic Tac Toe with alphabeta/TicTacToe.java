/**
 * This class implements and evaluates game situations of a TicTacToe game.
 */
public class TicTacToe {

    /**
     * Returns an evaluation for player at the current board state.
     * Arbeitet nach dem Prinzip der Alphabeta-Suche. Works with the principle of Alpha-Beta-Pruning.
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
     * @return          rating of game situation from player's point of view
    **/
    public static int alphaBeta(Board board, int player)
    { if (player==1){ return alphaBeta(board,player,Integer.MIN_VALUE,Integer.MAX_VALUE,0);}
      else{
          return -alphaBeta(board,player,Integer.MIN_VALUE,Integer.MAX_VALUE,0);
      }

    }
        // TODO

    public static int alphaBeta(Board board , int player , int alpha , int beta , int depth) {
        if (board.isGameWon()) {
            if (player == 1) {
                return board.nFreeFields() + 1;
            } else {
                return -board.nFreeFields() - 1;
            }
        }
        else if (board.nFreeFields() == 0) {
            return 0; }
        else {
            if (player == 1) {
                int maxeval = Integer.MIN_VALUE;
                Iterable<Position> plays = board.validMoves();
                for (Position pos : plays) {
                    board.doMove(pos, player);
                    int evaluation = alphaBeta(board, -player, alpha, beta, depth + 1);
                    board.undoMove(pos);
                    maxeval = Math.max(evaluation, maxeval);
                    alpha = Math.max(maxeval, alpha);
                    if (beta <= alpha) {
                        break;
                    }
                }
                return maxeval;
            }
            else if (player == -1) {
                int mineval = Integer.MAX_VALUE;
                Iterable<Position> plays = board.validMoves();
                for (Position pos : plays) {
                    board.doMove(pos, player);
                    int evaluation = alphaBeta(board, -player, alpha, beta, depth +1);
                    board.undoMove(pos);
                    mineval = Math.min(evaluation, mineval);
                    beta = Math.min(mineval, beta);
                    if (beta <= alpha) {
                        break;
                    }
                }
                return mineval;
            }
        }
    return 0;}

    
    /**
     * Vividly prints a rating for each currently possible move out at System.out.
     * (from player's point of view)
     * Uses Alpha-Beta-Pruning to rate the possible moves.
     * formatting: See "Beispiel 1: Bewertung aller Zugmöglichkeiten" (Aufgabenblatt 4).
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
    **/
    public static void evaluatePossibleMoves(Board board, int player)
    { if(player==1){System.out.println("Evalution for player 'x':");}
      else {System.out.println("Evalution for player 'o':");}

      String[][] string= new String[board.getN()][board.getN()];
      Iterable<Position> plays = board.validMoves();
        for(Position pos:plays){
            board.doMove(pos,player);
            int val=-alphaBeta(board, -player);
            board.undoMove(pos);
            string[pos.x][pos.y]=val + "|";
        }
        for (int x = 0; x < board.getN(); x++) {
            for (int y = 0; y < board.getN(); y++) {
                if (string[x][y] == null) {
                    if (board.getField(new Position(x, y)) == -1) {
                        System.out.print("o|");
                    } else {
                        System.out.print("x|");
                    }
                }
                else{
                    System.out.print(string[x][y]);

                }
            }
            System.out.println();

                // TODO
        }
    }


    public static void main(String[] args)
    {
        Board brett= new Board(3);
        brett.doMove(new Position(2, 2), 1);
        brett.doMove(new Position(0, 1), -1);
        evaluatePossibleMoves(brett, 1);
    }
}

