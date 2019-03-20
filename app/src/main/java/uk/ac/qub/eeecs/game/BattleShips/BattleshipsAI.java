package uk.ac.qub.eeecs.game.BattleShips;

import java.util.ArrayList;
import java.util.Collections;
public class BattleshipsAI {
    /*This class is used to allow the ai to decide where to hit next and store its shots so far so it can make a more educated shot
            This class contains 3 methods:
                -setupBoard()   which prepares the AI's board before the game
                -nextShot()     which returns the next shot which the AI is going to take (depending on the difficulty)
                -checkAIBoard() which checks the coordinates passed through against the aiboard[][] array and returns the state of the relevant field

            */

    //Explanation of shooting algorithms:
        /*  The AI has 3 different difficulty levels
            - Basic randomised sequence of shots, hit or miss the ai's shots are entirely random
            - More reactive approach which will finish off ships once it hits one
                     -- Here the ai will randomly hit the board until it hits a ship, once it hits
                        a ship it will randomly hit around it until it hits again and then randomly
                        shoot at either end of this 2 block ship until it misses twice, which means
                        that ship has been sunk

                        --- Adjacent ships??

            - More efficient randomised sequence which uses the reactive response alongside scaning the board for ships
              as efficiently as possible without looking at far more in-depth analysis of common ship placements.
                     -- The more efficient pattern is shooting at every other space due to the fact
                        that the shortest ship is 2 long we only have to hit half of the squares on
                        the board to have eventually hit every possible ship
                */


    //This int is used to determine what algorithm to use when making a shot
    private static int difficulty = 0;
    //This int constant stores the length of the board so that populating the shot sequence is easier
    private final int boardLength = 10;
    //this 2 dimensional array is used to store the players board from the AI's point of view
        // ie - its used to store every hit and miss the ai has made
                //Key: 0 - empty ; 1 - shot and miss ; 2 - shot and hit
    private static int[][] board = new int[10][10]; // players board as seen by AI
    private static int[][] pboard = new int [10][10]; // players board used to verify hits/misses
    //This array has been made public so that we can verify if the player has hit any of the AI's ships outside of this class
                //Key: 0 - empty ; 1 - unhit ship ; 2 - hit ship
    private static int[][] aiboard = new int [10][10]; //AI's board used for ship placement;


    /**
     *
     * @param x the x coordinate to check
     * @param y the y coordinate to check
     * @return 0, 1 or 2. 0 means this spot is empty, 1 means there is a ship here which hasn't been hit yet. 2 means that this spot has been hit before
     */
    public int checkAIBoard(int x, int y){
        return aiboard[y][x];
    }


    /**
     * @return Returns a 2 digit string representing the x & y coordinates to be hit. If the difficulty hasnt been set to either 0, 1 or 2 this method will return "ERROR - incorrect difficulty".
     *
     */
    public String nextShot()
    {
        switch (difficulty){
            case 0:
                return randomShot();
            case 1:
                break;
            case 2:
                break;

        }
        return "ERROR - incorrect difficulty";
    }

    /**
     * Reset's the aiboard[][] array and places the AI's ships
     */
    public void setupBoard(){
        //iterates through the aiboard array setting every field to 0 (i.e. empty)
        for (int i = 0; i<boardLength; i++){
            for (int j = 0; j<boardLength; j++){
                aiboard[i][j] = 0;
            }
        }

        //These next 5 for loops are used to place the ships in a preset position to allow for testing and playing of the game

        //for future reference this places the ships horizontally like so:
        /*
            1 1 1 1 1 0 0..  <- loop 1
            1 1 1 1 0 0 0..  <- loop 2
            1 1 1 0 0 0 0..        etc.
            1 1 1 0 0 0 0..
            1 1 0 0 0 0 0..
            0 0 0 0 0 0 0..
            . . . . . . .
            . . . . . . .

         */
        for(int i = 0; i<5; i++){
            aiboard[0][i] = 1;
        }
        for(int i = 0; i<4; i++){
            aiboard[1][i] = 1;
        }
        for(int i = 0; i<3; i++){
            aiboard[2][i] = 1;
        }
        for(int i = 0; i<3; i++){
            aiboard[3][i] = 1;
        }
        for(int i = 0; i<2; i++){
            aiboard[4][i] = 1;
        }



        /* placeShip(5);
        placeShip(4);
        placeShip(3);
        placeShip(3);
        placeShip(2);  */
    }
    private void placeShip(int length){ //DOESNT WORK, AT ALL
        boolean horizontal = Math.random()<0.5;
        boolean fits = false; //Used to check if the ship fit
        int x, y; //used to find a location for the ship
        do{
            if(horizontal){
                x = (int)Math.round(Math.random()*(boardLength-length));
                y = (int)Math.round(Math.random()*boardLength);
                for(int i = 0; i<=length; i++){
                    if (aiboard[y][x+i] != 0) break;
                    if (i == length) fits = true;
                }
            }else{
                x = (int)Math.round(Math.random()*boardLength);
                y = (int)Math.round(Math.random()*(boardLength-length));
                for(int i = 0; i<=length; i++){
                    if (aiboard[y+i][x] != 0) break;
                    if (i == length) fits = true;
                }
            }
        }while(!fits);
    }



    //this method will be called by the easiest AI, just randomly picks a point which hasn't been picked this game with no strategy

    /**
     * This method picks a random point on the players board which hasn't been hit yet and returns a 2 digit string of the format XY.
     * @return 2 digit string made up from the X and Y coordinates of the format XY.
     */

    private String randomShot(){
        int x,y;
        String shot = "";
        do{
            x = (int)Math.round(Math.random()*boardLength);
            y = (int)Math.round(Math.random()*boardLength);}while(board[y][x]!=0);
        //checking if we got a hit or miss;
        if(pboard[y][x] == 1){
            board[y][x] = 2;
        }else{board[y][x] = 1;}

        shot.concat(String.valueOf(x));
        shot.concat(String.valueOf(y));
        return shot;
    }

        //To keep the game random and to prevent a strategy to always beat the AI arising
        //the set of squares and the order in which they are hit will be randomised at the start of
        //each game
    private ArrayList<String> shotSequence = new ArrayList<String>();

        //This method randomly decides which of the two patterns to populates the shot sequence queue with and does that
            //Does this by deciding whether the first square will be A1 or A2, then proceeds to add B2 or B1 respectively
        //Still to come - shuffling the queue so that each game is different

        /*  x - determines the first squares coordinate for each loop
            y - determines whether the corresponding square in the row below is before or after
            j - integer used to iterate through the rows which is reset to the value of x at the start of each loop
            */

        private void randomiseShotSequence(){
            int x, y,j ;
            if (Math.random()<0.5){
                x = 1;
                y = 1;
            }else {
                x = 2;
                y = -1;
            }


            for (int i = 0; i<boardLength;i+=2){
                j = x;
                while (j<=boardLength){
                    shotSequence.add(String.valueOf(String.valueOf((char)(65+i)) + j));
                    if(j+y<=boardLength)
                        shotSequence.add(String.valueOf(String.valueOf((char)(65+i+1)) + (j + y )));
                    j+=2;
                }
            }
            Collections.shuffle(shotSequence);
        }
    }
