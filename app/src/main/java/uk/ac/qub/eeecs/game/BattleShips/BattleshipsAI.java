package uk.ac.qub.eeecs.game.BattleShips;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
public class BattleshipsAI {
    //This class is used to decide for the AI where to hit next and store its shots so far so it can make a more educated shot

    //This int is used to determine what algorithm to use when making a shot
    private static int difficulty = 0;
    //This integer constant stores the length of the board so that populating the shot sequence is easier
    private final int boardLength = 9;
    //this 2 dimensional array is used to store the players board from the AI's point of view
        // ie - its used to store every hit and miss the ai has made
                //Key: 0 - empty ; 1 - shot and miss ; 2 - shot and hit
    private static int[][] board = new int[10][10];

    //Every ship is atleast 2 squares long, meaning that to hit every ship at least once
    //the AI must simply shoot every other space, and then the spaces it didnt hit on the next row.
    //This means there are two different patterns which the ai could follow
        //To keep the game random and to prevent a strategy to always beat the AI arising
        //the set of squares and the order in which they are hit will be randomised at the start of
        //each game played on the hard difficulty
    private Queue<String> hardShotSequence;

        //This method randomly decides which of the two patterns to populates the shot sequence queue with and does that
            //Does this by deciding whether the first square will be A1 or A2, then proceeds to add B2 or B1 respectively
        //Still to come - shuffling the queue so that each game is different

        /*  x - determines the first squares coordinate for each loop
            y - determines whether the corresponding square in the row below is before or after
            j - integer used to go iterate through the rows which is reset to the value of x at the start of each loop
            */
        public void randomiseShotSequence(){
            Queue<String> hardShotSequence = new LinkedList<>();
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
                    hardShotSequence.add(String.valueOf(String.valueOf((char)(65+i)) + j));
                    if(j+1*y<=boardLength)
                        hardShotSequence.add(String.valueOf(String.valueOf((char)(65+i+1)) + (j + 1*y )));
                    j+=2;
                }
            }
        }


        //this method will be called by the easiest class, just randomly picks a point with no strategy
        public String randomShot(){
            int x,y;
            String shot;
            do{
            x = (int)Math.round(Math.random()*boardLength);
            y = (int)Math.round(Math.random()*boardLength);}while(board[y][x]!=0);

            board[y][x] = 1;
            return String.valueOf((char)(44+x) + y);
        }




    }
