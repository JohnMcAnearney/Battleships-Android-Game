package uk.ac.qub.eeecs.game.BattleShips;
//40201925 - Hannah: User Story 8 Sprint 3
public class Board
{
    //Creating the battleship grid board = 10x10 integer, initialised by -1

    public static void initBoard(int[][] board)
    //this method initially sets the value -1 in all blocks of the game board
    {
        for (int row = 0; row < 10; row++)
            for (int column = 0; column < 10; column++)
                board[row][column] = -1;
    }
    //~ - Temporary Water in the block for the board
    //* - Temporary Shots fired with no ship in that position
    //X - Temporary Ship

    public static void showBoard(int[][] board)
    //this method gets int matrix and displays the game board
    {
        System.out.println("\t1 \t2 \t3 \t4 \t5");
        System.out.println();

        for (int row = 0; row < 10; row++){
            System.out.println((row+1)+"");
            for (int column = 0; column < 10; column++){
                if(board[row][column]==-1){ //-1 = No shot was given
                    System.out.print("\t"+"~");
                }
                else if(board[row][column]==0){ //Shot was given with no ship there
                    System.out.print("\t"+"*");
                }
                else if(board[row][column]==1){
                    System.out.print("\t"+"X");
                }
            }
            System.out.println();
        }
    }
}
