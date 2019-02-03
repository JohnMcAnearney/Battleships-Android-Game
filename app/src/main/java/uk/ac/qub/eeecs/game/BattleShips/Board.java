package uk.ac.qub.eeecs.game.BattleShips;
//40201925 - Hannah: User Story 8 Sprint 3

/*this class will hold the information for the board. The 10x10 grid array, the coordinates
of the ships, rotate methods etc.
*/

import android.graphics.Rect;

import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.world.GameObject;

public class Board //extends GameObject             might have to extend as gameobject
{
    final int BOARD_WIDTH = 600;    //actual pixel width of the board
    final int BOARD_HEIGHT = 600;   //actual pixel height of the board
    final int BLOCK_WIDTH = 60;     //individual block w
    final int BLOCK_HEIGHT = 60;    //individual block h
    final int BOARD_BLOCKWIDTH = 10;    //10 blocks high
    final int BOARD_BLOCKHEIGHT = 10;   //10 blocks high
    final int NO_ACTION = -1;   //if the square has not yet been interacted with
    final int ACTION_MISS = 0;  //if the square was selected but it was a miss
    final int ACTION_HIT = 1;   //if the square was selected and it was a hit

    int[][] shipCoordinates;
    int [][] board = new int [BOARD_BLOCKWIDTH][BOARD_BLOCKHEIGHT]; //sets the size of the array to 10x10
    Rect [][] boundsForBoxes = new Rect[BOARD_BLOCKWIDTH][BOARD_BLOCKHEIGHT]; //sets bounding
    final BoundingBox bayBound; //the bound for the ships to be displayed in

    public Board(){
        //this is the constructor. The board will only ever be of size 10x10 and have this bound. The Board will not vary.
        boardSetup();
        bayBound = new BoundingBox(500, 100, 500, 100); //this is just the bounding box at the top for where the ships will all go
    }

    //Creating the battleship grid board = 10x10 integer, initialised by -1
    public void boardSetup()
    //this method initially sets the value -1 in all blocks of the game board
    {
        for (int row = 0; row < BOARD_BLOCKWIDTH; row++) {
            for (int column = 0; column < BOARD_BLOCKHEIGHT; column++) {
                int boxBoundMidpointX = 30, boxBoundMidpointY = 30; //first mid point of first box in pixels
                board[row][column] = NO_ACTION;
                                                        //left              top                     right                  bottom
                boundsForBoxes[row][column] = new Rect(boxBoundMidpointX, boxBoundMidpointY, BLOCK_WIDTH/2, BLOCK_HEIGHT/2);    //creates a bound every box in array
                boxBoundMidpointX+=60;  //incrementing the midpoints by 60 to go to mid point of next box
                boxBoundMidpointY+=60;
                // for each individual block, put a bound over it so we can detect things and ac use the block
            }
        }
    }

    //These methods should be in BoardSetupScreen!!!!!
    //
//    public void shipPosition(Ship selectedShip){
//        //this will take the selected ship and place its coordinates to where ever the player drops the ship
//
//        // 1 touch event on ship in the bay area at top
//        // 2 player drags ship icon over board
//        // 3 board highlights current drop location
//        // 4 player let go
//        // 5 ship in place
//        // 6 if player moves the repeat 1-5
//
//    }
//
//    public void rotateShip(Ship currentShip){
//        //this will take the current ships coordinates and turn the 90 degrees anti-clockwise relative to the top coordinate
//
//        // 1 get selected ship first coor, i.e. the the square bound it is in
//        // 2 find length of ship i.e. if it is 3 long etc.
//        /* 3 (by default the ship will be horizontal) therefore to rotate we go to first coor
//              and do nothing, go through each one to the right/left and swap x and y values
//              */
//        // 4 update
//    }




    public void showBoard(int[][] board)
    //this method gets int matrix and displays the game board
    //if hit detected set coor to 1 and mark with hit, if missed set to 0 and marked with miss marker
    {
        //this method will be used in update of BoardGameScreen to show if the current state of board.
        //going to leave this console version in so i can remember this idea

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

    //all the getters that may be needed for future
    public int getBOARD_WIDTH() { return BOARD_WIDTH; }
    public int getBOARD_HEIGHT() { return BOARD_HEIGHT; }
    public int getBLOCK_WIDTH() { return BLOCK_WIDTH; }
    public int getBLOCK_HEIGHT() { return BLOCK_HEIGHT; }
    public int getBOARD_BLOCKWIDTH() { return BOARD_BLOCKWIDTH; }
    public int getBOARD_BLOCKHEIGHT() { return BOARD_BLOCKHEIGHT; }
    public int getNO_ACTION() { return NO_ACTION; }
    public int getACTION_MISS() { return ACTION_MISS; }
    public int getACTION_HIT() { return ACTION_HIT; }
}


