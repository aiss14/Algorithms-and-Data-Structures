import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static java.lang.Math.abs;
/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private int n;
    private int[][] board;
    private Position last;
    /**
     *  Creates Board object, am game board of size n * n with 1<=n<=10.
     */
    public Board(int n)
    { if(n<=0 || n>10){
        throw new InputMismatchException("n must be 0<n<=10");
      }
      this.n=n;
      this.board=new int[n][n];
      this.last= null;
        // TODO
    }
    
    /**
     *  @return     length/width of the Board object
     */
    public int getN() { return n; }
    
    /**
     *  @return     number of currently free fields
     */
    public int nFreeFields() {
        int free=n*n;
        return free;
        // TODO
    }
    
    /**
     *  @return     token at position pos
     */
    public int getField(Position pos) throws InputMismatchException
    {
      if (pos.x<0 || pos.x>n-1 || pos.y<0 || pos.y>n-1){
          throw new InputMismatchException("you are out of the board");}
      return this.board[pos.x][pos.y];
        // TODO
    }

    /**
     *  Sets the specified token at Position pos.
     */    
    public void setField(Position pos, int token) throws InputMismatchException
    { if (!(token==1 || token==-1||token==0)){
        throw new  InputMismatchException("token should be either 0,1 or -1");
        }
      if (pos.x<0 || pos.x>n-1 || pos.y<0 || pos.y>n-1)  {
          throw new InputMismatchException("you are out of the board");
      }
        this.board[pos.x][pos.y] = token;
        last= new Position(pos.x,pos.y);
        // TODO
    }
    
    /**
     *  Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player)
    { if (getField(pos) != 0) {
        throw new RuntimeException("already taken ");
      }
      else this.setField(pos, player);
        // TODO
    }

    /**
     *  Clears board at Position pos.
     */
    public void undoMove(Position pos)
    { this.setField(pos,0);
        // TODO
    }
    
    /**
     *  @return     true if game is won, false if not
     */
    public boolean isGameWon() {
        if (last==null) return false;
        int co =0;// test the lines
        int cx=0;
        for(int lines=0; lines<n;lines++){
            if (board[lines][last.y]==1){cx++;}
            if (board[lines][last.y]==-1){co++;}
        }
        if (co==n||cx==n) return true;

         co =0;//test the columns
         cx=0;
        for(int colum=0;colum<n;colum++){
            if (board[last.x][colum]==1){cx++;}
            if (board[last.x][colum]==-1){co++;}
        }
        if (co==n||cx==n) return true;

        co =0;//test the first diagonal
        cx=0;
        for (int diag=0;diag<n;diag++){
            if (board[diag][diag]==1){cx++;}
            if (board[diag][diag]==-1){co++;}
        }
        if (co==n||cx==n) return true;

        co =0;//test the second diagonal
        cx=0;
        for (int diag=0;diag<n;diag++){
            if (board[n-1-diag][n-1-diag]==1){cx++;}
            if (board[n-1-diag][n-1-diag]==-1){co++;}
        }
        if (co==n||cx==n) return true;

        return false;
        // TODO
    }

    /**
     *  @return     set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves()
    {
        LinkedList<Position> list = new LinkedList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(getField(new Position(i,j))==0){
                    list.add(new Position(i,j));
                }
            }
        }
        return list;
        // TODO
    }

    /**
     *  Outputs current state representation of the Board object.
     *  Practical for debugging.
     */
    public void print()
    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j]+" ");
                // TODO
            }
         System.out.println();
        }
    }
}

