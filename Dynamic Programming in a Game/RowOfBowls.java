import java.util.ArrayList;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {
     private int [][]zwisch;
     private int [] zahlen;

    public RowOfBowls() {

    }
    
    /**
     * Implements an optimal game using dynamic programming
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGain(int[] values)
    { int [][] matrix = new int[values.length][values.length] ;
        if (values.length==0)return 0;
        for(int i=0;i<values.length;i++){
            matrix[0][i]=values[i];
        }
        for (int step=1 ; step<values.length;step++){
            for(int i=0;i<values.length-step;i++){
                matrix[step][i]= Math.max(values[i]-matrix[step-1][i+1],values[i+step]-matrix[step-1][i] );
            }
        }
        this.zwisch=matrix;
        this.zahlen=values;
        return matrix[values.length-1][0];
        // TODO
    }

    /**
     * Implements an optimal game recursively.
     *
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGainRecursive(int[] values) {
        if (values.length == 0) return 0;
        int player = 1;
        int eval = 0;
        return maxGainRecursive(values, player, eval);
    }

        private int maxGainRecursive(int[] values, int player , int eval){
            if (values.length==1){
                return eval + (player * values[0]);
            }
            else{
                int [] right = new int[values.length-1];
                int [] left = new int[values.length-1];
                for(int i=0;i<values.length-1;i++){
                    right[i]=values[i];
                    left[i]=values[i+1];
                }
                if (player==1){
                    return Math.max(maxGainRecursive(right,-player,eval+values[values.length-1]),maxGainRecursive(left,-player,eval+values[0]));
                }
                else{
                    return Math.min(maxGainRecursive(right,-player,eval-values[values.length-1]),maxGainRecursive(left,-player,eval-values[0]));
                }

            }

        }
        // TODO


    
    /**
     * Calculates an optimal sequence of bowls using the partial solutions found in maxGain(int values)
     * @return optimal sequence of chosen bowls (represented by the index in the values array)
     */
    public Iterable<Integer> optimalSequence()
    {
        maxGain(zahlen);
        ArrayList<Integer> seq= new ArrayList<>();

        return seq;// TODO
    }


    public static void main(String[] args)
    { RowOfBowls row = new RowOfBowls();
      int [] values = {4,7,2,3};
      row.zahlen=values;
      int p = row.maxGain(values);
      System.out.print(p);
        // For Testing
        
        }
}

