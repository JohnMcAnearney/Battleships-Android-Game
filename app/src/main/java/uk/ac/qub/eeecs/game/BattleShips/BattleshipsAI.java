package uk.ac.qub.eeecs.game.BattleShips;

import java.util.ArrayList;
import java.util.Collections;
public class BattleshipsAI {

    /*This class is used to allow the ai to decide where to hit next and store its shots so far so it can make a more educated shot
            This class contains 5 public methods:
                -BattleshipsAI() which is the constructor
                -setupBoard()    which prepares the AI's board before the game
                -nextShot()      which returns the next shot which the AI is going to take (depending on the difficulty)
                -checkAIBoard()  which checks the coordinates passed through against the aiboard[][] array and returns the state of the relevant field
                -updateAIBoard() which sets the field which the player picked to 2 (to demonstrate that it has been hit before and prevent accidental duplicate moves)
            */

    //Explanation of shooting algorithms:
        /*  The AI has 3 different difficulty levels
            - Basic randomised sequence of shots, hit or miss the ai's shots are entirely random
            - More reactive approach which will finish off ships once it hits one
                     -- Here the ai will randomly hit the board until it hits a ship, once it hits
                        a ship it will randomly hit around it until it hits again and then randomly
                        shoot at either end of this 2 block ship until it misses twice, which means
                        that ship has been sunk
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
    private static int[] enemyBoard = new int[100]; // players board as seen by AI
    private static int[] pboard = new int [100]; // players board used to verify hits/misses
    //This array has been made public so that we can verify if the player has hit any of the AI's ships outside of this class
                //Key: 0 - empty ; 1 - unhit ship ; 2 - hit ship
    private static int[]aiboard = new int [100]; //AI's board used for ship placement;

    //The constructor allowing to set the difficulty and give the ai access to the players board
    public BattleshipsAI(int diff, int[]playerBoard){
        pboard = playerBoard;
        difficulty = diff;
        if(difficulty < 2){
            randomiseEasyShotSequence();
        }else{
            randomiseHardShotSequence();
        }

    }

    /**
     *
     * @param coord the coordinates to check
     * @return 0, 1 or 2. 0 means this spot is empty, 1 means there is a ship here which hasn't been hit yet. 2 means that this spot has been hit before
     */
    public int checkAIBoard(int coord){
        return aiboard[coord];
    }

    /**
     * This method is used to update the AI's board once the player makes their turn.
     * @param coord the coordinates of the field to update
     */
    public void updateAIBoard(int coord) { aiboard[coord] = 2; }


    /**
     * @return Returns a 2 digit string representing the x & y coordinates to be hit. If the difficulty hasnt been set to either 0, 1 or 2 this method will return -1.
     *
     */
    public int nextShot()
    {
        switch (difficulty){
            case 0:
                return shotSequence.remove(0);
            case 1:
                //implement if to decide whether to use reactiveShot() or not
                if(lastShotHit){
                    return reactiveShot(shotSequence.indexOf(0));
                }else {
                    lastShot = randomShot();
                    if(pboard[lastShot] == 1){
                        firstReactiveShot = true;
                        lastShotHit = true;
                    }
                    return lastShot;
                }
            case 2:
                break;

        }
        return -1;
    }

    /**
     * Resets the aiboard[] array and places the AI's ships
     */
    public void setupBoard(){
        //iterates through the aiboard array setting every field to 0 (i.e. empty)
        for (int i = 0; i<100; i++){
                aiboard[i] = 0;
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
            aiboard[i] = 1;
        }
        for(int i = 10; i<14; i++){
            aiboard[i] = 1;
        }
        for(int i = 20; i<23; i++){
            aiboard[i] = 1;
        }
        for(int i = 30; i<33; i++){
            aiboard[i] = 1;
        }
        for(int i = 40; i<42; i++){
            aiboard[i] = 1;
        }



        /* placeShip(5);
        placeShip(4);
        placeShip(3);
        placeShip(3);
        placeShip(2);  */
    }
    /*private void placeShip(int length){ //DOESNT WORK, AT ALL
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
*/


    //this method will be called by the easiest AI, just randomly picks a point which hasn't been picked this game with no strategy

    /**
     * This method picks a random point on the players board which hasn't been hit yet and returns a 2 digit string of the format XY.
     * @return 2 digit string made up from the X and Y coordinates of the format XY.
     */

    private int randomShot(){
        int shot;

        do{
            shot = (int)Math.round(Math.random()*boardLength);
            shot += 10*(int)Math.round(Math.random()*boardLength);}while(enemyBoard[shot]!=0);
        //checking if we got a hit or miss;
        if(pboard[shot] == 1){
            enemyBoard[shot] = 2;
        }else{enemyBoard[shot] = 1;}

        return shot;
    }

    private boolean lastShotHit = false;    //I dont think this is neccessary here but will be needed to skip out this method call in the nextShot() method
    private boolean firstReactiveShot = true;
    private int lastShot, initialHit;
    private int shotDirection = -1; //1-UP;2-RIGHT;3-DOWN;4-LEFT
    private ArrayList<Integer> reactiveShotSequence = new ArrayList<Integer>(); //Arraylist used to determine which way to shoot.

    private int reactiveShot(int nextShot){

        //TODO - determine direction, test proposed shot
        if(firstReactiveShot){
            boolean foundNextShot = false;
            int proposedShot;
            reactiveShotSequence.add(initialHit -10); //try up
            reactiveShotSequence.add(initialHit +1);    //right
            reactiveShotSequence.add(initialHit +10);   //down
            reactiveShotSequence.add(initialHit -1);    //left
            do{
                proposedShot = reactiveShotSequence.get(0);
                //If we check all 4 directions and none are valid use the next random shot passed in & reset variables
                if (reactiveShotSequence.isEmpty()){
                    lastShotHit = false;
                    firstReactiveShot = true;
                    return nextShot;
                }
                //Checks if the shot matches all criteria
                if(proposedShot < 0 || proposedShot > 99 || ((initialHit%10==0)&&((proposedShot+1)%10 == 0)) || (((initialHit+1)%10==0)&&(proposedShot%10 == 0)) || enemyBoard[proposedShot] > 0 ){
                    reactiveShotSequence.remove(0);
                }else{
                    //if a valid shot is found remove it form the array, prepare for second part of the algorithm  and return the value
                    firstReactiveShot = false;
                    proposedShot = reactiveShotSequence.get(0);
                    lastShot = proposedShot;
                    reactiveShotSequence.remove(0);
                    return proposedShot;
                }
            }while(!foundNextShot);
        } else {
            //here we have fired the first reactive shot and need to find the direction;
            if(shotDirection == -1){
                //switch to determine direction.
            }
        }
        return nextShot;
    }


        //To keep the game random and to prevent a strategy to always beat the AI arising
        //the set of squares and the order in which they are hit will be randomised at the start of
        //each game
    private ArrayList<Integer> shotSequence = new ArrayList<Integer>();
    private void randomiseEasyShotSequence(){
        for (int i = 0; i < 100; i++){
            shotSequence.add(i);
        }
        Collections.shuffle(shotSequence);
    }
        //This method randomly decides which of the two patterns to populates the shot sequence queue with and does that
            //by deciding whether the first square will be 0 or 1, then proceeds to add 11 or 10 respectively
        //After one of the two patterns has been decided upon the queue is then shuffled and the AI will make random shots
            //in a lattice pattern to more efficiently hit every enemy ship

        /*  x - determines the first squares coordinate for each loop
            y - determines whether the corresponding square in the row below is before or after
            j - integer used to iterate through the rows which is reset to the initial value of x at the start of each loop
            */

        private void randomiseHardShotSequence(){
            int x, y,j ;
            if (Math.random()<0.5){
                x = 0;
                y = 1;
            }else {
                x = 1;
                y = -1;
            }


            for (int i = 0; i<boardLength;i+=2){
                j = x;
                while (j<boardLength){
                    shotSequence.add(i*10 + j);
                    if(j+y<=boardLength)
                        shotSequence.add(((i+1)*10)+j+y);
                    j+=2;
                }
            }
            Collections.shuffle(shotSequence);
        }
    }
