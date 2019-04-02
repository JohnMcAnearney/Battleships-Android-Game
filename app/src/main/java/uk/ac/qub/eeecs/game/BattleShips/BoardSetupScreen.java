package uk.ac.qub.eeecs.game.BattleShips;

//this class will be responsible for displaying the board. On this screen the user will be able to setup their
//fleet with their ships in whatever way they wish


import android.graphics.*;
import android.text.method.Touch;

import java.util.List;
import java.util.Vector;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationSettings;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class BoardSetupScreen extends GameScreen {

    /**
     * Class Authors: John McAnearney 40203900 and Mantas Stadnik 40203133
     * John McA KEY: I used camelCase for all except FINAL variables where I_USED_THIS_SYNYAX
     */

    /////////////////////////////////////////// - GENERAL VARIABLES - /////////////////////////////////////////////////////////////////

    private Bitmap boardSetupBackground, battleshipTitle, boundsMessage;
    private PushButton mBackButton, mRotateButton, mPauseButton, mPlayButton;
    private AssetManager assetManager;
    private Paint paint = new Paint();
    private String message = "Not Detected", message2  ="";
    private float x,y;      // Coordinate values of user input
    private int moveBackground =0;
    private final float MAX_SNAP_TO_DISTANCE = 1000.0f;
    float closestSlotDistanceSqrd = Float.MAX_VALUE;
    int numberOfClosestBox = 0;
    private PushButton[] pushButtonArray;      //Array to store all of the buttons

    ////////////////////////////////////////// - BOX VARIABLES - //////////////////////////////////////////////////////////////////

    private float bigBoxLeftCoor=0;       //i could do a test method for these, testing if i change these variables that they all still fit in the
    private float bigBoxTopCoor=0;         //screen and if not set it back so it fits in screen
    private float bigBoxRightCoor=0;     //simple if right>screenwidth then return false and fix
    private float bigBoxBottomCoor=0;
    private float smallBoxWidth = 0;
    private float smallBoxHeight = 0;
    private boolean smallBoxDetected = false;      //if user clicked inside a small box this will be true
    private float[][] smallBoxCoordinates = new float[200][5];        //2D array to store all of the small box co-ordinates, storing left, top, right,bottom
    private int numberOfSmallBoxesDrawn = -1;          //counter for how many small boxes have been drawn
    private int numberofSmallBoxDetected ;             //holds the 2d array index of the small box detected
    private boolean smallboxCoordinatesCaptured = false, smallboxCoordinatesCaptured2 = false;  //if the smallBoxCoordinates array has been populated by capturing the co ordinates for both boards this will be set to true
    private float screenWidth = 0;
    private float screenHeight = 0;
    private BoundingBox boardBoundingBox;
    private final int NUMBER_ROWS = 10, NUMBER_COLUMNS = 10;
    private final int BOARD_TWO_SIZE = 100;

    ////////////////////////////////////////// - SHIP VARIABLES - //////////////////////////////////////////////////////////////////

    private boolean shipSetUp = false;  //flag which indicates if the ship objects have been created
    private Ship[] shipArray;  // hold all of the ship objects
    private Ship selectedShip; //Ship object holder which will be used when user clicks on the ship and drags it across the screen
    private int shipToDragPointerIndexOfInput; //pointer index holder, when user presses the screen the input index will be stored for dragging
    private enum GameShipPlacementState {SHIP_SELECT,SHIP_DRAG} //Game states, will swap between the states when the user will place ships onto the grid before the start of the game
    private GameShipPlacementState gameShipPlacementState = BoardSetupScreen.GameShipPlacementState.SHIP_SELECT;  //Initialized enum object set to ship select, for when the game starts
    private Vector2 dragShipOffset = new Vector2(); //Vector to store the x and y coordinates of the ship's coordinates minus the touch location
    private boolean toRotateShip = false;  //boolean value used as a identifier to rotate the ship
    private boolean shipOutOfBound = false;

    ////////////////////////////////////////// - Animation - //////////////////////////////////////////////////////////////////
    private static ExplosionAnimation explosionAnimation ;     //Object holder for explosionAnimation
    private static AnimationSettings animationSettings;        //Object holder for animationSettings

    ////////////////////////////////////////// - Constructor + UPDATE AND DRAW - //////////////////////////////////////////////////////////////////
    public BoardSetupScreen(Game game){
        super("BoardSetupBackground", game);
        assetManager = mGame.getAssetManager();  // create a global asset Manager
        /**
         * Load all of the required images
         * Added JSON file for all your assets - Edgars(40203154)
         */
        mGame.getAssetManager().loadAssets("txt/assets/BoardSetupScreenAssets.JSON");
        battleshipTitle = assetManager.getBitmap("Title");
        boardSetupBackground = assetManager.getBitmap("WaterBackground");
        boundsMessage = assetManager.getBitmap("boundsMessage");
        assetManager.loadAndAddBitmap("PlayButton", "img/AcceptButton.png");


        //Mantas Stadnik (40203133) load bitmaps which were used by my methods
        assetManager.loadAndAddBitmap("rotateButton","img/rotateButton.png");
        assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png");
        assetManager.loadAndAddBitmap("CargoShip", "img/CargoShip.png");
        assetManager.loadAndAddBitmap("CruiseShip", "img/CruiseShip.png");
        assetManager.loadAndAddBitmap("Destroyer", "img/Destroyer.png");
        assetManager.loadAndAddBitmap("Submarine", "img/Submarine.png");
        /**
         * Creating animationSettings object which will load the JSON file and the image sprite sheet
         * to be used for an explosion animation
         */
        animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");
        /**
         * create explosion animation object which will allow for explosion to be drawn
         */
        explosionAnimation = new ExplosionAnimation(animationSettings,0);
    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        //Store the x and y co-ordinates from touch events
        for(TouchEvent touchEvent: touchEvents) {
            y = touchEvent.y;
            x = touchEvent.x;

            //Check if user touched down on the screen
            if(touchEvent.type == touchEvent.TOUCH_DOWN &&
                    gameShipPlacementState == GameShipPlacementState.SHIP_SELECT) {
                detectionIfUserSelectedSmallBox(elapsedTime);
            shipSelect(touchEvent);
            }
            // When user lifts their finger off the screen drop the bitmap, and change the game state
            if (touchEvent.type == TouchEvent.TOUCH_UP
                    && touchEvent.pointer == shipToDragPointerIndexOfInput
                    && gameShipPlacementState == GameShipPlacementState.SHIP_DRAG) {
                // Call ship placement after the user has placed the ship object.
                shipPlacement();
                //Set the gamestate to ship select since the user is no longer pressing onto the screen
                gameShipPlacementState = GameShipPlacementState.SHIP_SELECT;
            }
        }

        //If game is in shipDrag state, call method shipDrag
        if(gameShipPlacementState == GameShipPlacementState.SHIP_DRAG)
            shipDrag(input);


        // If statement to detect if a touch event has happened
        if (touchEvents.size() > 0) {
            // Updating all the push buttons when a touch event is detected
            mBackButton.update(elapsedTime);
            mRotateButton.update(elapsedTime);
            mPauseButton.update(elapsedTime);
            mPlayButton.update(elapsedTime);

            // If statement which processes all the appropriate touch events within the class
            if(mBackButton.isPushTriggered())
            {
                // Back to main menu
                mGame.getScreenManager().addScreen(new MainMenu(mGame));
                mGame.getScreenManager().removeScreen(this);
            }
            else if (mPauseButton.isPushTriggered()) {
                // To pause screen
                mGame.getScreenManager().addScreen(new PauseScreen(mGame));
            }
            else if(mRotateButton.isPushTriggered())
            {
                if(selectedShip == null)
                {
                    //check if a ship has been selected, do nothing no ship has been selected
                }
            else
            {
                //rotate the ship by 90 degrees
                rotateShipBy90Degrees();
            }
            }
            else if(mPlayButton.isPushTriggered())
            {
                //TODO - MJ
                prepareBoard();
                //^^Check all ships have been placed
                //^^place bitmaps in the correct positions
                //Create AI class
                //Place AI ships
                //Turn indicator
                //Enter game loop

            }
            //Calling method to check if user input of x,y are inside a small box
        }

        //update the animation frame
        explosionAnimation.update(elapsedTime);


        moveBackground += elapsedTime.stepTime * 50.0f;
        if(moveBackground> 300){
            moveBackground = 0;
        }

        message2 = "X CoOr: "+String.valueOf(x) + "\n" +"YcoOr:" + String.valueOf(y);
    }

    //Prepares the board to allow the player to play the game
    private void prepareBoard() {
        if(!checkAllShipsPlaced()){
            //Display warning
        } else {
            //All the ships have been placed

        }
    }
    //Iterates through the smallBoxCoordinate array to check if all the ships have been placed
    private boolean checkAllShipsPlaced() {
        int noOfShipSquares = 0;
        for (int i =0; i<100; i++){
            if(smallBoxCoordinates[i][4] == 1){
                noOfShipSquares++;
            }
        }
        if(noOfShipSquares == 17){return true;}
        return false;
    }

    Paint textPaint = new Paint();
    Paint highlight = new Paint();

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        screenWidth = graphics2D.getSurfaceWidth();
        screenHeight = graphics2D.getSurfaceHeight();
        //Collective method which draws required items, boards and static images
        drawItems(graphics2D);
        setupBoardBound();

        //Set up ship bounds and create ship objects only once
        if (!shipSetUp ) {
            createShipObjects(graphics2D);
            shipSetUp = true;
        }

        //Call draw methods for all of the ship in the ship's object class
        drawShips(graphics2D);

        //Used for testing to display the bounding box of the ships
        for(Ship shipArray: shipArray)
        {
            graphics2D.drawRect(shipArray.mBound.x, shipArray.mBound.y, shipArray.mBound.x + shipArray.mBound.getWidth(), shipArray.mBound.y + shipArray.mBound.getHeight(), highlight);
        }

        // If an user clicked on a small box highlight by painting a square using paint otherwise do nothing
        if(smallBoxDetected)
        {
            smallBoxDetected = false;
            highlight.setARGB(75,232,0,0);
            highlightBoxGiven(numberofSmallBoxDetected,highlight,graphics2D);                           //used for testing
            message = "detected" + numberofSmallBoxDetected;                                            //used for testing
            message = message;

        }
        else
        {
            message = "Not detected";
        }
        //if the ship is out of bounds of the box, display a message to indicate to the user
        if(shipOutOfBound){
            drawMessageToScreen(graphics2D);
        }

        //Call the explosion animation method in the object's class
        explosionAnimation.draw(elapsedTime,graphics2D);

        //Set up and draw messages used for testing
        textPaint.setTextSize(50.0f);
        textPaint.setTextAlign(Paint.Align.LEFT);
        graphics2D.drawText(message, 100.0f, 100.0f, textPaint);
        graphics2D.drawText(message2, 100.0f, 200.0f, textPaint);

        //Create all of the buttons used in this gamescreen collectively
        createButtons();

        mBackButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mRotateButton.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        mPauseButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mPlayButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        //Draw all of the buttons
        drawAllButtons(elapsedTime,graphics2D);
    }

    ////////////////////////////////////////////// - OUR OWN METHODS - /////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////// - John's methods - //////////////////////////////////////////////////////////////////////////

    /**
     * This is the refactored method for drawing the two boards. They fundamentally do the exact same thing just using different variables.
     * @param graphics2D - required when drawing anything
     * @param bigBoxLeft - the left-hand-side x-coordinate of the big, outer grid
     * @param bigBoxTop - the top-side y-coordinate of the big, outer grid
     * @param bigBoxRight - the right-hand-side x-coordinate of the big, outer grid
     * @param bigBoxBottom - the bottom-side y-coordinate of the big, outer grid
     * @param boxNumberConstant - the number of boxes currently drawn. Used so that the method knows the number of the box that it is storing the small box coordinates for.
     */
    private void drawBoards(IGraphics2D graphics2D, float bigBoxLeft, float bigBoxTop, float bigBoxRight, float bigBoxBottom, int boxNumberConstant){

        bigBoxLeftCoor = bigBoxLeft;            //these are the variables for the big outer box, enclosing the smaller boxes, for each grid (left and right)
        bigBoxTopCoor = bigBoxTop;
        bigBoxRightCoor = bigBoxRight;
        bigBoxBottomCoor = bigBoxBottom;

        smallBoxWidth = (bigBoxRightCoor - bigBoxLeftCoor)/10f;     //this calculates the size of each small box.
        smallBoxHeight = (bigBoxBottomCoor - bigBoxTopCoor)/10f;    //each box is a 10th of the bigger box

        //Step 2 - draw lots of smaller squares
        numberOfSmallBoxesDrawn =boxNumberConstant-1;
        smallboxCoordinatesCaptured = false;

        //calculates each smaller box and stores their coordinates in a 2d array
        for(int rows =0; rows<NUMBER_ROWS; rows++) {

            //move constant defined to MOVE EACH BOX TO THE RIGHT. Called moveConstLeft as it moves the left hand coordinate of each box.
            float moveConstLeft = 0;
            for (int column = 0; column < NUMBER_COLUMNS; column++) {
                numberOfSmallBoxesDrawn++;
                //draw each of the small boxes
                graphics2D.drawRect((bigBoxLeftCoor + moveConstLeft), bigBoxTopCoor,      //same start position
                        (bigBoxLeftCoor + smallBoxWidth + moveConstLeft), (bigBoxTopCoor + smallBoxHeight), paint);

                //store all of the small box coordinates in a 2d array
                if(!smallboxCoordinatesCaptured ) {
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][0] = bigBoxLeftCoor + moveConstLeft;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][1] = bigBoxTopCoor;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][2] = bigBoxLeftCoor + smallBoxWidth + moveConstLeft;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][3] = bigBoxTopCoor + smallBoxHeight;

                }

                //TODO
                // add the grid reset here
               // resetBoardPlacements();

                //draw each of the small boxes
                /**
                 * https://developer.android.com/reference/android/graphics/Rect
                 * Used the above link in order to understand how rect's were actually used.
                 * Specifically how they used the left, top, right and bottom parameters
                 * no code was copied in this method.
                 */

                graphics2D.drawRect((bigBoxLeftCoor + moveConstLeft), bigBoxTopCoor,      //same start position
                        (bigBoxLeftCoor + smallBoxWidth + moveConstLeft), (bigBoxTopCoor + smallBoxHeight), paint);

                //move the box to the right
                moveConstLeft += smallBoxWidth;
            }

            //after first row is drawn, move down a row and draw 10 more boxes.
            //these two lines move the boxes down a row
            bigBoxTopCoor+=smallBoxHeight;
            bigBoxBottomCoor+=smallBoxHeight;

        }
        //resetting the bounds
        smallboxCoordinatesCaptured = true;
        bigBoxLeftCoor = bigBoxLeft;
        bigBoxTopCoor = bigBoxTop;
        bigBoxRightCoor = bigBoxRight;
        bigBoxBottomCoor = bigBoxBottom;
    }

    /**
     * Method to draw the left hand board
     * Calculates the size to draw the board based on the devise's screen
     * @param graphics2D
     */
    private void drawBoardOne(IGraphics2D graphics2D){

        //  I know these are magic numbers but I couldnt think of any other way to do it as the coordinates rely on the screen width which I need
        //  Graphics2D for, which I cannot declare using a global variable, therefore just know that these variables are what they are to make the game look nice
        drawBoards(graphics2D, screenWidth/14f, screenHeight/5f, (screenWidth/14f)*6f, (screenHeight/5f)*4.5f, 0);
    }
    /**
     * Method to draw the right hand board
     * Calculates the size to draw the board based on the devise's screen
     * @param graphics2D
     */
    private void drawBoardTwo(IGraphics2D graphics2D){

        //  I know these are magic numbers but I couldnt think of any other way to do it as the coordinates rely on the screen width which I need
        //  Graphics2D for, which I cannot declare using a global variable, therefore just know that these variables are what they are to make the game look nice
        drawBoards(graphics2D, (screenWidth/14f)*7.5f, screenHeight/5f, (((screenWidth/14f)*7.5f)*1.684f), (screenHeight/5f*4.5f), 100);
        bigBoxLeftCoor = screenWidth/14f;
        bigBoxTopCoor = screenHeight/5f;            //resetting the bounds to the first boxes' parameters as to ensure rest of methods work.
        bigBoxRightCoor = (screenWidth/14f)*6f;     //this is done as we are only ever checking input and variation in the first board as the second board is
        bigBoxBottomCoor = (screenHeight/5f)*4.5f;  //solely for the AI.

    }

    /**
     * Method to setup the bound for the board in order to be used to detect if ships are out of bound.
     */
    private void setupBoardBound(){
        //simply sets board bound of first board
        boardBoundingBox = new BoundingBox((bigBoxLeftCoor + bigBoxRightCoor)/2,
                (bigBoxBottomCoor + bigBoxTopCoor)/2,
                ((bigBoxLeftCoor + bigBoxRightCoor)/2)-bigBoxLeftCoor,
                ((bigBoxBottomCoor + bigBoxTopCoor)/2)-bigBoxTopCoor);
    }

    /**
     * simple method used to draw images that will not be interacted with (nor changed) directly; like the Title and background.
     * @param graphics2D
     */
    public void drawConstantImages(IGraphics2D graphics2D){

        /**
         * for the moving of the background I followed Philip's tutorials. https://www.youtube.com/watch?v=tsQYqFiZ26Q
         * I watched this, got an understanding and then altered it to suit what I wanted. Phil also done a presentation in class
         * but I can't find if he had sent any source code out or not
         */
        Matrix bcgMatrix = new Matrix();
        //magnifies background image
        bcgMatrix.setScale(3.5f, 3.5f);
        //moves background image to the left
        bcgMatrix.postTranslate(-moveBackground,0);
        //draws background image
        graphics2D.drawBitmap(boardSetupBackground, bcgMatrix, paint);
        //draws the battlehships title at the top of the screen. titleTop returns 1% of screen, therefore multiply by whatever you desire.
        int titleLeft = graphics2D.getSurfaceWidth()/3, titleTop = graphics2D.getSurfaceHeight()/graphics2D.getSurfaceHeight();
        Rect titleRect = new Rect(titleLeft, titleTop*10, titleLeft*2, titleTop*160);
        graphics2D.drawBitmap(battleshipTitle, null, titleRect, paint);
    }

    /**
     * Searches for where the ship has been placed, conducts tests to check if ship is in the box, if this box is already occupied
     * and then it finally marks the ship's placement
     */
    private void shipPlacement(){
        //check if ship out of bounds
        isShipOutOfBound();
        for(int i = 0;i<100;i++){

            //then see if the ship is in the box
            if (selectedShip.mBound.x > smallBoxCoordinates[i][0] && selectedShip.mBound.x < smallBoxCoordinates[i][2]
                    && selectedShip.mBound.y > smallBoxCoordinates[i][1] && selectedShip.mBound.y < smallBoxCoordinates[i][3]) {
                //check if box is occupied
                checkIfBoxOccupied(i);
                //if check above is ok then snap to box and mark the boxes
                shipSnapToBox();
            }
        }
    }

    /**
     * Compares the mBound of the selected ship and the boards bound, and returns appropriate answer.
     * If out of bounds, then reset the ship
     */
    private boolean isShipOutOfBound(){

        //pretty self explanatory
        if(boardBoundingBox.contains(selectedShip.mBound.x,  selectedShip.mBound.y) &&
                boardBoundingBox.contains(selectedShip.mBound.x + (selectedShip.mBound.halfWidth * 2),
                        selectedShip.mBound.y + (selectedShip.mBound.halfHeight * 2))){
             shipOutOfBound = false;
        }
        else {
            shipOutOfBound =true;
            //if its out of bound, reset the ship
            shipReset();
        }

        return shipOutOfBound;
    }

    /**
     * Simple method to mark the occupied boxes accordingly for the selected ships length
     */
    private void markShipInBox(){
        if(selectedShip.isRotated){
            for (int x = 0; x < selectedShip.getShipLength(); x++) {
                //mark each box as occupied, starting with the leftmost box the ship is in until the length of the ship
                smallBoxCoordinates[numberOfClosestBox][4] = 1;
                numberOfClosestBox+=10;
            }
        } else {
            for (int x = 0; x < selectedShip.getShipLength(); x++) {
                //mark each box as occupied, starting with the leftmost box the ship is in until the length of the ship
                //have to do -1 of shiplength because the it takes the (box its in + length of ship)
                // e.g. length = 5 then total boxes would be 6 total, therefore must do length -1
                smallBoxCoordinates[numberOfClosestBox][4] = 1;
                numberOfClosestBox++;
            }
        }
    }

    /**
     * Calculates the closest box to ship based off the top-left coordinates of each box relative to the top-left coordinates of the selected ship
     * @return returns the number of the closest box from the smallBoxCoordinates array
     */
    private int calculateClosestBox(){

        /**
         * this is basically all phil's code except with the applicable alterations I needed to suit our game
         */
        //closest box is between smallBoxWidth/2 coor and ship coor
        //set to this becasuse the closest distance will, basically, always be less than this. Always less in this game.
        closestSlotDistanceSqrd = Float.MAX_VALUE;
        //variable to track the closest box
        numberOfClosestBox = 0;

        for(int x = 0; x<100;x++){
            if(smallBoxCoordinates[x][4]==1){   //if box is occupied, skip the calculation as no need
                continue;
            }
            else{
                //this is (x2-x1)^2 + (y2-y1)^2, using equation between two points
                float distanceSqrd =
                        ((smallBoxCoordinates[x][0] - selectedShip.mBound.x) * (smallBoxCoordinates[x][0] - selectedShip.mBound.x)
                                + (smallBoxCoordinates[x][1] - selectedShip.mBound.y) * (smallBoxCoordinates[x][1] - selectedShip.mBound.y));
                if(distanceSqrd < closestSlotDistanceSqrd) {
                    //if the new calculated distance to the current evaluated square is less than the current closest square, then this evaluated square becomes the closest one.
                    numberOfClosestBox = x;
                    closestSlotDistanceSqrd = distanceSqrd;
                }
            }

        }

        return numberOfClosestBox;
    }

    /**
     * Uses the calculation of the closest box then snaps the selected ship to the appropriate box
     */
    private void shipReset(){
        //get the closest square
        calculateClosestBox();
        if (Math.sqrt(closestSlotDistanceSqrd) <= MAX_SNAP_TO_DISTANCE){
            //if smallboxcoors[closestbox] is greater than bound then go back some steps
            //if the closest box is any of these then move it back some boxes as before the ship would snap to these and still stick out
            if(numberOfClosestBox == 9|| numberOfClosestBox == 19|| numberOfClosestBox == 29|| numberOfClosestBox == 39|| numberOfClosestBox == 49||
                    numberOfClosestBox == 59|| numberOfClosestBox == 69|| numberOfClosestBox == 79|| numberOfClosestBox == 89|| numberOfClosestBox == 99){

                switch (selectedShip.getShipLength()){
                    case 2: selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox-1][0];
                        selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                        break;
                    case 3: selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox-2][0];
                        selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                        break;
                    case 4: selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox-3][0];
                        selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                        break;
                    case 5: selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox-4][0];
                        selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                        break;
                }
            }
            //otherwise just snap to the closest box
            else {
                switch (selectedShip.getShipLength()) {
                    case 2:
                        selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox][0];
                        selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                        break;
                    case 3:
                        selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox][0];
                        selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                        break;
                    case 4:
                        selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox][0];
                        selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                        break;
                    case 5:
                        selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox][0];
                        selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                        break;
                }
            }

        }

    }

    private void shipSnapToBox(){
        calculateClosestBox();
        //same as ship reset but doesnt check out of bounds
        //needed these two separated as there was a strange glitch where it would mark the ship where it wasn't present and I couldn't figure it out.
        switch (selectedShip.getShipLength()) {
            case 2:
                selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox][0];
                selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                markShipInBox();
                break;
            case 3:
                selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox][0];
                selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                markShipInBox();
                break;
            case 4:
                selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox][0];
                selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                markShipInBox();
                break;
            case 5:
                selectedShip.mBound.x = smallBoxCoordinates[numberOfClosestBox][0];
                selectedShip.mBound.y = smallBoxCoordinates[numberOfClosestBox][1];
                markShipInBox();
                break;
        }
    }

    /**
     * Checks if current box looking at in is occupied, if it is just move it to these coordinates
     * @param currentBox - current box looking at in smallBoxCoordinates array
     */
    private void checkIfBoxOccupied(int currentBox){
        if(smallBoxCoordinates[currentBox][4] ==1 ){
            //simple reset, if the box is occupied set it back to the given box
            switch (selectedShip.getShipLength()) {
                case 2:
                    selectedShip.mBound.x = smallBoxCoordinates[30][0];
                    selectedShip.mBound.y = smallBoxCoordinates[30][1];
                    break;
                case 3:
                    selectedShip.mBound.x = smallBoxCoordinates[20][0];
                    selectedShip.mBound.y = smallBoxCoordinates[20][1];
                    break;
                case 4:
                    selectedShip.mBound.x = smallBoxCoordinates[10][0];
                    selectedShip.mBound.y = smallBoxCoordinates[10][1];
                    break;
                case 5:
                    selectedShip.mBound.x = smallBoxCoordinates[40][0];
                    selectedShip.mBound.y = smallBoxCoordinates[40][1];
                    break;
              }
        }
    }

    /**
     * The Enlcosed box is what Mantas Stadnik(40203133) implemented in this method.
     * The rest is John McAnearney's (40203900)
     * If the current box clicked is occupied by a ship, then it is a hit, otherwise it is a miss
     * @param currentBox - box clicked
     * @param elapsedTime - elapsed time of program
     */
    private void hitOrMiss(int currentBox, ElapsedTime elapsedTime){

        //if box is occupied by a ship and the user clicks the box, it is a hit
        if(smallBoxDetected == true && smallBoxCoordinates[currentBox][4] == 1){
            message = "HIT!";

            //////////////////////////////////////////////////////////////////////////////////////////
            //                                      - Mantas Stadnik -                              //
            //Play explosion Animation, since game loop has not been developed as of 01/04/2019
            // this is one way of demonstrating the animation I have developed
              explosionAnimation.play(elapsedTime, smallBoxCoordinates[numberofSmallBoxDetected][0],//
                    smallBoxCoordinates[numberofSmallBoxDetected][1],                               //
                    smallBoxCoordinates[numberofSmallBoxDetected][2],                               //
                    smallBoxCoordinates[numberofSmallBoxDetected][3]);                              //
            //                                                                                      //
            //////////////////////////////////////////////////////////////////////////////////////////
        }

        //otherwise its a miss
        else{
            message = "MISS!";
        }

    }

    /**
     * When the users moves the ship out of bounds, a message appears warning them until the user moves it back within the bounds
     * @param graphics2D - used to draw the actual bitmap
     */
    private void drawMessageToScreen(IGraphics2D graphics2D) {
        Paint messagePaint = new Paint();
        //https://stackoverflow.com/questions/11285961/how-to-make-a-background-20-transparent-on-android source on how to do transparency
        //the above was referenced in order to find out how to change the opacity of an image
        messagePaint.setAlpha(220); //this is an opacity of 80%, no need to convert to hex
        Rect messageRect = new Rect((graphics2D.getSurfaceWidth()/2) - (battleshipTitle.getWidth()/2),
                (graphics2D.getSurfaceHeight()/2) - (battleshipTitle.getHeight()/2),
                (graphics2D.getSurfaceWidth()/2) + (battleshipTitle.getWidth()/2),
                (graphics2D.getSurfaceHeight()/2) + (battleshipTitle.getHeight()));
        graphics2D.drawBitmap(boundsMessage, null, messageRect, messagePaint);
    }

    ////////////////////////////////////////////// - Collective methods - //////////////////////////////////////////////////////////////////////////

    private void createButtons() {
        // Trigger Button at the bottom left of the screen
        mBackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.88f, mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "SettingsBackButton","SettingsBackButtonP",  this);
        mBackButton.setPlaySounds(true, true);

        mRotateButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.80f,   mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "rotateButton", "rotateButton", this);

        mPauseButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.9f, mDefaultLayerViewport.getHeight() * 0.9f,
                mDefaultLayerViewport.getWidth() * 0.05f, mDefaultLayerViewport.getHeight() * 0.05f,
                "PauseButton", this);
        mPauseButton.setPlaySounds(true, true);

        mPlayButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight()* 0.10f,
                mDefaultLayerViewport.getWidth()*0.075f, mDefaultLayerViewport.getHeight()*0.10f, "PlayButton", this);
        mPlayButton.setPlaySounds(true, true);

        pushButtonArray = new PushButton[]{mBackButton,mRotateButton,mPauseButton,mPlayButton};
    }

    ////////////////////////////////////////////// - Mantas' methods 40203133 - //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Draw required objects
     */
    private void drawItems(IGraphics2D graphics2D)
    {
        drawConstantImages(graphics2D);
        drawBoardOne(graphics2D);
        drawBoardTwo(graphics2D);
    }

    /**
     * Draw all of the buttons in the pushButtonArray
     * @param elapsedTime
     * @param graphics2D
     */
    private void drawAllButtons(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        for(PushButton pushButton: pushButtonArray)
        {
            pushButton.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        }
    }

    /**
     * Create all of the ship objects
     * More ships can easily be added to the list as this method is extensible
     * @param graphics2D
     */
    private void createShipObjects(IGraphics2D graphics2D)
    {
        Ship aircraftCarrier = new Ship("AircraftCarrier", calculateShipRatioX("AircraftCarrier", 5),calculateShipRatioY("AircraftCarrier"),assetManager.getBitmap("AircraftCarrier"), 5);
        Ship cargoShip = new Ship("CargoShip", calculateShipRatioX("CargoShip",4),calculateShipRatioY("CargoShip"),assetManager.getBitmap("CargoShip"), 4);
        Ship cruiseShip = new Ship("CruiseShip", calculateShipRatioX("CruiseShip",4),calculateShipRatioY("CruiseShip"),assetManager.getBitmap("CruiseShip"), 4);
        Ship submarine = new Ship("Submarine", calculateShipRatioX("Submarine",3),calculateShipRatioY("Submarine"),assetManager.getBitmap("Submarine"), 3);
        Ship destroyer = new Ship("Destroyer", calculateShipRatioX("Destroyer",2),calculateShipRatioY("Destroyer"),assetManager.getBitmap("Destroyer"), 2);
        shipArray = new Ship[]{aircraftCarrier,cargoShip,cruiseShip,destroyer,submarine};
        setUpShipmBound(graphics2D);
    }

    /**
     * Dynamically calculate the ship's X ratio to the size of the squares by comparing
     * image size to required size and ship's length
     * @param bitmapName
     * @param shipLength
     * @return
     */
    private float calculateShipRatioX(String bitmapName, int shipLength)
    {
        int shipBitmapWidth = assetManager.getBitmap(bitmapName).getWidth();
        return (((bigBoxRightCoor - bigBoxLeftCoor)/10f) * shipLength) / shipBitmapWidth;
    }

    /**
     * Will dynamically calculate the ship's Y ratio to the size of the squares by comparing
     * image size to required size
     * @param bitmapName
     * @return
     */
    private float calculateShipRatioY(String bitmapName)
    {
        int shipBitmapHeight = assetManager.getBitmap(bitmapName).getHeight();
        return (((bigBoxBottomCoor - bigBoxTopCoor)/10f)) / shipBitmapHeight;
    }

    /**
     * Method to draw the ships stored in the shipArray onto the screen
     * * @param graphics2D
     */
    private void drawShips(IGraphics2D graphics2D){
        for(Ship ship: shipArray) {
            ship.drawShip(graphics2D); }
    }

    /**
     * Set up the ships bounding box, placing them 0.08% apart
     * @param graphics2D
     */
    private void setUpShipmBound(IGraphics2D graphics2D) {
        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();
        float screenHeightOffset = 0.1f;
        //Setting the ships bounding box, including the x,y co-ordinates and the half width and half height using bounding box setter
        for (Ship ship : shipArray) {
            screenHeightOffset = screenHeightOffset + 0.08f;
            ship.setmBound(screenWidth * 0.015f,
                    screenHeight * screenHeightOffset,
                    ((bigBoxRightCoor - bigBoxLeftCoor) / 10f) * ship.getShipLength() / 2.0f,
                    ((bigBoxBottomCoor - bigBoxTopCoor) / 10f) / 2f); }
    }

    /**
     * Highlight the small box passed in as a parameter used for testing
     * @param numberofSmallBox
     * @param paint
     * @param iGraphics2D
     */
    private void highlightBoxGiven(int numberofSmallBox,Paint paint, IGraphics2D iGraphics2D)
    {
        iGraphics2D.drawRect(smallBoxCoordinates[numberofSmallBox][0], smallBoxCoordinates[numberofSmallBox][1],
                smallBoxCoordinates[numberofSmallBox][2],smallBoxCoordinates[numberofSmallBox][3], paint);
    }

    /**
     * This method checks small box co-ordinates against the x and y input co-ordinates, sets parameter smallBoxDetected to true if yes, false otherwise
     * calls hitOrMiss method
     * @param elapsedTime
     */
    private void detectionIfUserSelectedSmallBox(ElapsedTime elapsedTime)
    {
        if(bigBoxRightCoor == 0){ //if board co-ordinates have not been set, do nothing
        }
        else {
            //if user input x co-ordinate is in the first box x region check if the user has clicked
            //on one of the boxes in the first box
            if (x <= bigBoxRightCoor) {
                //carry out a binary search for a box on the first board setting the return int to numberOfSmallBoxDetected
                numberofSmallBoxDetected = binarySearchBox(smallBoxCoordinates, 0, 100, x, y);
                //if a box that a user has clicked on is found set smallBoxDetected flag to true
                if (numberofSmallBoxDetected >= 0)
                    smallBoxDetected = true;
                    hitOrMiss(numberofSmallBoxDetected,elapsedTime);
            }
            //if user input x co-ordinate is in the second box x region check if the user has clicked
            //on one of the boxes in the second board
             if (x > bigBoxRightCoor){
                 //carry out a binary search for a box on the second board setting the return int to numberOfSmallBoxDetected
                numberofSmallBoxDetected = binarySearchBox(smallBoxCoordinates, 100, 200, x, y);
                //if a box that a user has clicked on is found set smallBoxDetected flag to true
            if (numberofSmallBoxDetected >= 0)
                smallBoxDetected = true;
             }
        }

    }

    /**
     * Checks if an user has clicked on the screen, if so, check if the user has clicked onto a ship
     * @param touchEvent
     */
    private void shipSelect(TouchEvent touchEvent)
    {
        //Check if the user has pressed on the screen
                for(Ship ship: shipArray)
                {
                    // Check if the touch was on any of the ships bounding box if yes change game state, store the pointer index and the ship
                    if(boxContainsInput(ship.mBound.x, ship.mBound.x +ship.mBound.getWidth(),ship.mBound.y, ship.mBound.y + ship.mBound.getHeight(),touchEvent.x,touchEvent.y))
                    {
                        // Set x and y coordinates dragShipOffset vector, so when user drags the ship, the ship will be dragged from the point of touch
                        dragShipOffset.set(ship.mBound.x - touchEvent.x, ship.mBound.y - touchEvent.y);
                        selectedShip = ship;
                        shipToDragPointerIndexOfInput = touchEvent.pointer;
                        gameShipPlacementState = GameShipPlacementState.SHIP_DRAG;
                    }
                }
    }

    /**
     * Drag the stored ship following the user's input as long as the user has the same finger
     * onto the screen the whole time
     * @param input
     */
    private void shipDrag(Input input) {

        //Move the ship to the users finger's location
        if (input.existsTouch(shipToDragPointerIndexOfInput)) {
            selectedShip.mBound.x = input.getTouchX(shipToDragPointerIndexOfInput) + dragShipOffset.x;
            selectedShip.mBound.y = input.getTouchY(shipToDragPointerIndexOfInput) + dragShipOffset.y;
        }
    }

    /**
     * Sets a rotation flag to true
     */
    private void rotateShipBy90Degrees()
    {
        selectedShip.rotate = true;
    }

    /**
     * Searches for the number of the small box which the user has clicked on, otherwise returns -1
     * @param array
     * @param lower
     * @param higher
     * @param x
     * @param y
     * @return
     */
    private int binarySearchBox(float[][] array,int lower, int higher, float x, float y)
    {
        //Check if the lower bound is less than or equal to higher bound
        //this is used to ensure when the user has not clicked onto a small box, the loop
        //is broken
        //if lower bound is less than or equal to higher bound find a mid value
        if(lower < higher) {
            int mid =  (lower + higher) / 2;

            //if the mid small box contains the user's input x and y values the box
            // have been found return the number of the small box
            if(boxContainsInput(array[mid][0],array[mid][2], array[mid][1], array[mid][3],x,y))
            {
                return mid;
            }

            //check if the user's input y value is in the current row, if yes the row has been found
            //proceed to calling a binary search on the current row
            if(y > array[mid][1] && y < array[mid][3])
            {
                //numberOfSmallBox is the number of first box in the row. 0 = row 1, 10 = row 2,
                // 20 = row 3 and so on
                return binarySearchRows(array,mid-(mid%10),0,10, x,y );
            }

            //check if the user's input y value is below the current row, if yes recursive call
            //current method with lower boundary being the current row + 1
            if(y > array[mid][1] && y > array[mid][3])
                return binarySearchBox(array,mid+1,higher,x,y);

            //check if the user's input y value is below the current row, if yes recursive call
            //current method with higher boundary being the current row -1
            if(y<array[mid][1] && y < array[mid][3])
                return binarySearchBox(array,lower,mid-1,x,y);
        }
        return -1;
    }

    /**
     * Searches for the user clicked on small box when the row has been identified, returning
     * the number of the small box detected otherwise returning -1
     * @param array
     * @param numberOfSmallBox
     * @param lower
     * @param higher
     * @param x
     * @param y
     * @return
     */
    private int binarySearchRows(float[][] array,int numberOfSmallBox, int lower,int higher, float x, float y)
    {
        //Check if the lower bound is less than or equal to higher bound
        //this is used to ensure when the user has not clicked onto a small box, the loop
        //is broken
        //if lower bound is less than the higher bound find a mid value
        if(lower <higher) {
            int mid = numberOfSmallBox + ((lower + higher) / 2);

            //if the mid small box contains the user's input x and y values the box
            // have been found, return the number of the small box
            if (boxContainsInput(array[mid][0],array[mid][2], array[mid][1], array[mid][3], x, y)) {
                return mid;
            }
            //if the user's input x value is lower than the current small box, recursive call
            //current method with the higher bound set to mid
            if (x < array[mid][0] && x < array[mid][2]){
                return binarySearchRows(array, numberOfSmallBox, lower, mid % 10 , x, y);}

                //if the user's input x value is higher than the current small box, recursive call
            //current method with the lower bound set to mid
            else if (x > array[mid][0] && x > array[mid][2]){
                return binarySearchRows(array, numberOfSmallBox, mid % 10 +1 , higher, x, y);}

        }
        return -1;
    }

    /**
     * Check if the user has clicked within the box of which co-ordinates passed
     * in as parameters, if yes return true
     * @param leftX
     * @param rightX
     * @param TopY
     * @param bottomY
     * @param inputX
     * @param inputY
     * @return
     */
    private boolean boxContainsInput(float leftX,  float rightX, float TopY, float bottomY, float inputX, float inputY)
    {
        if(inputX > leftX&&
                inputX < rightX &&
                inputY > TopY &&
                inputY < bottomY)
        {
            return true;
        }

        return false;
    }

}
