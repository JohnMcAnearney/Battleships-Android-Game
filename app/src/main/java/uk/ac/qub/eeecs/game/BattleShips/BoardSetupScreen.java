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
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class BoardSetupScreen extends GameScreen {

    private Bitmap boardSetupBackground, battleshipTitle;
    private PushButton mBackButton;
    private Paint paint = new Paint();
    private String message = "Not Detected", message2  ="";
    private float x,y;
    private float bigBoxLeftCoor=0;       //i could do a test method for these, testing if i change these variables that they all still fit in the
    private float bigBoxTopCoor=0;         //screen and if not set it back so it fits in screen
    private float bigBoxRightCoor=0;     //simple if right>screenwidth then return false and fix
    private float bigBoxBottomCoor=0;
    private boolean smallBoxDetected = false;      //if user clicked inside a small box this will be true
    private float[][] smallBoxCoordinates = new float[200][5];        //2D array to store all of the small box co-ordinates, storing left, top, right,bottom
    private int numberOfSmallBoxesDrawn = -1;          //counter for how many small boxes have been drawn
    private int numberofSmallBoxDetected ;             //holds the 2d array index of the small box detected
    private boolean smallboxCoordinatesCaptured = false, smallboxCoordinatesCaptured2 = false;  //if the smallBoxCoordinates array has been populated by capturing the co ordinates for both boards this will be set to true
    private boolean setShipBound = false;
    private Ship[] shipArray;  // hold all of the ship objects
    private int moveBackground =0;

    private Ship shipToDrag;                                                                 //Ship object holder which will be used when user clicks on the ship and drags it across the screen
    private int shipToDragPointerIndexOfInput;                                                         //pointer index holder, when user presses the screen the input index will be stored for dragging
    private enum GameShipPlacementState {SHIP_SELECT,SHIP_DRAG}                              //Game states, will swap between the states when the user will place ships onto the grid before the start of the game
    private GameShipPlacementState gameShipPlacementState = BoardSetupScreen.GameShipPlacementState.SHIP_SELECT;  //Initialized enum object set to ship select, for when the game starts
    private Vector2 dragShipOffset = new Vector2();                  //Vector to store the x and y coordinates of the ship's coordinates minus the touch location


    public BoardSetupScreen(Game game){
        super("BoardSetupBackground", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("WaterBackground", "img/Water_Tile.png");
        assetManager.loadAndAddBitmap("SettingsBackButton", "img/BackB.png");
        assetManager.loadAndAddBitmap("SettingsBackButtonP", "img/BackBPressed.png");
        assetManager.loadAndAddBitmap("Title", "img/Title.png");
        battleshipTitle = assetManager.getBitmap("Title");
        boardSetupBackground = assetManager.getBitmap("WaterBackground");
        assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png");
        Ship aircraftCarrier = new Ship("Aircraft Carrier", 0,0,assetManager.getBitmap("AircraftCarrier"));
        shipArray = new Ship[]{aircraftCarrier};



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
        }


        switch(gameShipPlacementState)
        {
            case SHIP_SELECT: shipSelect(input); break;
            case SHIP_DRAG: shipDrag(input);break;
        }

        if (touchEvents.size() > 0) {
            mBackButton.update(elapsedTime);
            if(mBackButton.isPushTriggered()){
                //back to main menu
                mGame.getScreenManager().addScreen(new MainMenu(mGame));
            }


            //Calling method to check if user input of x,y are inside a small box
            detectionIfUserSelectedSmallBox();


//            // Update each button and transition if needed
//            mBackButton.update(elapsedTime);
//            if (mBackButton.isPushTriggered()){
//                mGame.getScreenManager().removeScreen(this);
//            }
        }

        moveBackground += elapsedTime.stepTime * 50.0f;
        if(moveBackground> 300){
            moveBackground = 0;
        }

        message2 = "X CoOr: "+String.valueOf(x) + "\n" +"YcoOr:" + String.valueOf(y);
    }

    Paint textPaint = new Paint();
    Paint highlight = new Paint();
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
        drawStaticImages(graphics2D);
        drawBoardOne(graphics2D);
        drawBoardTwo(graphics2D);
        if (!setShipBound ) {
            setUpShipmBound(graphics2D);
        }
        drawShips(graphics2D);
        shipPlacement(graphics2D);
        //if an user clicked on a small box highlight by painting a square using paint
        //otherwise do nothing
        if(smallBoxDetected)
        {
            highlight.setARGB(75,232,0,0);
            highlightBoxGiven(numberofSmallBoxDetected,highlight,graphics2D);
            message = "detected" + numberofSmallBoxDetected;
        }
        else
        {
            message = "Not detected";
        }

        textPaint.setTextSize(50.0f);
        textPaint.setTextAlign(Paint.Align.LEFT);
        graphics2D.drawText(message, 100.0f, 100.0f, textPaint);
        graphics2D.drawText(message2, 100.0f, 200.0f, textPaint);
        createButtons();
        mBackButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

    }

    private void drawBoardOne(IGraphics2D graphics2D){
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();

        bigBoxLeftCoor = (screenWidth/14f);       //i could do a test method for these, testing if i change these variables that they all still fit in the
        bigBoxTopCoor = screenHeight/5f;          //screen and if not set it back so it fits in screen
        bigBoxRightCoor = (bigBoxLeftCoor*3f)*2f;      //simple if right>screenwidth then return false and fix
        bigBoxBottomCoor = (bigBoxTopCoor*4.5f);

        float smallBoxWidth = (bigBoxRightCoor - bigBoxLeftCoor)/10f;       // these two must be added to the value as they are the square dimensions
        float smallBoxHeight = (bigBoxBottomCoor - bigBoxTopCoor)/10f;

        //Step 2 - draw lots of smaller squares
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        numberOfSmallBoxesDrawn =-1;
        for(int rows =0; rows<10; rows++) {
            float moveConstLeft = 0;
            for (int column = 0; column < 10; column++) {
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

                if(smallBoxCoordinates[numberOfSmallBoxesDrawn][4] == 1){
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.YELLOW);
                } else if (smallBoxCoordinates[numberOfSmallBoxesDrawn][4] == 0){
                    paint.setStyle(Paint.Style.STROKE);       //THIS METHOD COULD BE USED FOR IF HIT PAINT RED ETC.
                    paint.setColor(Color.WHITE);
                }

                //draw each of the small boxes
                graphics2D.drawRect((bigBoxLeftCoor + moveConstLeft), bigBoxTopCoor,      //same start position
                        (bigBoxLeftCoor + smallBoxWidth + moveConstLeft), (bigBoxTopCoor + smallBoxHeight), paint);

                moveConstLeft += smallBoxWidth;

            }

            bigBoxTopCoor+=smallBoxHeight;
            bigBoxBottomCoor+=smallBoxHeight;

        }
        smallboxCoordinatesCaptured = true;
        bigBoxLeftCoor = (screenWidth/14f);       //i could do a test method for these, testing if i change these variables that they all still fit in the
        bigBoxTopCoor = screenHeight/5f;          //screen and if not set it back so it fits in screen
        bigBoxRightCoor = bigBoxLeftCoor*6f;      //simple if right>screenwidth then return false and fix
        bigBoxBottomCoor = (bigBoxTopCoor*4.5f);
    }

    private void drawBoardTwo(IGraphics2D graphics2D){
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();

        bigBoxLeftCoor = (screenWidth/14f)*7.5f;       //i could do a test method for these, testing if i change these variables that they all still fit in the
        bigBoxTopCoor = screenHeight/5f;          //screen and if not set it back so it fits in screen
        bigBoxRightCoor = (bigBoxLeftCoor*1.684f);      //simple if right>screenwidth then return false and fix
        bigBoxBottomCoor = (bigBoxTopCoor*4.5f);

        float smallBoxWidth = (bigBoxRightCoor - bigBoxLeftCoor)/10f;       // these two must be added to the value as they are the square dimensions
        float smallBoxHeight = (bigBoxBottomCoor - bigBoxTopCoor)/10f;

        //Step 2 - draw lots of smaller squares
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        numberOfSmallBoxesDrawn =99;
        for(int rows =0; rows<10; rows++) {
            float moveConstLeft = 0;
            for (int column = 0; column < 10; column++) {
                numberOfSmallBoxesDrawn++;
                //draw each of the small boxes
                graphics2D.drawRect((bigBoxLeftCoor + moveConstLeft), bigBoxTopCoor,      //same start position
                        (bigBoxLeftCoor + smallBoxWidth + moveConstLeft), (bigBoxTopCoor + smallBoxHeight), paint);

                //store all of the small box coordinates in a 2d array
                if(!smallboxCoordinatesCaptured2 ) {
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][0] = bigBoxLeftCoor + moveConstLeft;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][1] = bigBoxTopCoor;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][2] = bigBoxLeftCoor + smallBoxWidth + moveConstLeft;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][3] = bigBoxTopCoor + smallBoxHeight;

                }

//                if(smallBoxCoordinates[numberOfSmallBoxesDrawn][4] == 1){
//                    paint.setStyle(Paint.Style.FILL);
//                    paint.setColor(Color.YELLOW);
//                } else{
//                    paint.setStyle(Paint.Style.STROKE);       THIS METHOD COULD BE USED FOR IF HIT PAINT RED ETC.
//                    paint.setColor(Color.WHITE);
//                }
//
//                //draw each of the small boxes
//                graphics2D.drawRect((bigBoxLeftCoor + moveConstLeft), bigBoxTopCoor,      //same start position
//                        (bigBoxLeftCoor + smallBoxWidth + moveConstLeft), (bigBoxTopCoor + smallBoxHeight), paint);

                moveConstLeft += smallBoxWidth;

            }

            bigBoxTopCoor+=smallBoxHeight;
            bigBoxBottomCoor+=smallBoxHeight;

        }
        smallboxCoordinatesCaptured2 = true;
        bigBoxLeftCoor = (screenWidth/14f);       //i could do a test method for these, testing if i change these variables that they all still fit in the
        bigBoxTopCoor = screenHeight/5f;          //screen and if not set it back so it fits in screen
        bigBoxRightCoor = bigBoxLeftCoor*6f;      //simple if right>screenwidth then return false and fix
        bigBoxBottomCoor = (bigBoxTopCoor*4.5f);
    }

    private void setUpShipmBound(IGraphics2D graphics2D)
    {
        //Setting the ships bounding box, including the x,y co-ordinates and the half width and half height using bounding box setter
        shipArray[0].setmBound(Math.round(graphics2D.getSurfaceWidth()*0.05),
                Math.round(graphics2D.getSurfaceWidth()*0.1),
                Math.round(((bigBoxRightCoor - bigBoxLeftCoor)/10f)*2.5),
                ((bigBoxBottomCoor - bigBoxTopCoor)/10f)/2);
        setShipBound = true;
    }

    //Method to draw the ship bitmaps onto the screen
    private void drawShips(IGraphics2D graphics2D){

        //Setting up the rectangle for drawing by calling bounding box x,y co-ordinates, width and height getters
        Rect shipRect = new Rect((int)shipArray[0].mBound.x ,
                (int)shipArray[0].mBound.y,
                (int)(shipArray[0].mBound.getWidth()+shipArray[0].mBound.x),
                (int)(shipArray[0].mBound.getHeight() + shipArray[0].mBound.y));
        //Drawing the aircraft carrier bitmap
        graphics2D.drawBitmap(shipArray[0].bitmap,null,shipRect,paint);
    }

    //Highlight the small box passed in as a parameter
    private void highlightBoxGiven(int numberofSmallBox,Paint p, IGraphics2D iGraphics2D)
    {

        iGraphics2D.drawRect(smallBoxCoordinates[numberofSmallBox][0], smallBoxCoordinates[numberofSmallBox][1],
                smallBoxCoordinates[numberofSmallBox][2],smallBoxCoordinates[numberofSmallBox][3], p);

    }

    //This method checks small box co-ordinates against the x and y input co-ordinates, sets parameter smallBoxDetected to true if yes, false otherwise
    private void detectionIfUserSelectedSmallBox()
    {
        smallBoxDetected = false;
        for(int i = 0; i < 200; i++)
        {
            if( x > smallBoxCoordinates[i][0] && x < smallBoxCoordinates[i][2]
                    && y > smallBoxCoordinates[i][1] && y < smallBoxCoordinates[i][3]) {
                numberofSmallBoxDetected = i;
                smallBoxDetected = true;
                break;
            }
        }
    }

    private void createButtons() {
        //Trigger Button at the bottom left of the screen
        mBackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "SettingsBackButton","SettingsBackButtonP",  this);
        mBackButton.setPlaySounds(true, true);

    }

    public void drawStaticImages(IGraphics2D graphics2D){
        Matrix bcgMatrix = new Matrix();

        bcgMatrix.setScale(2.5f, 2.5f);
        bcgMatrix.postTranslate(-moveBackground,0);
        graphics2D.drawBitmap(boardSetupBackground, bcgMatrix, paint);

        //                      could do some maths to figure out exact ,middle using bitmaps ac size but looks ok for now
        Rect titleRect = new Rect(graphics2D.getSurfaceWidth()/3, 10, (graphics2D.getSurfaceWidth()/3)*2, graphics2D.getSurfaceHeight()/9);
        graphics2D.drawBitmap(battleshipTitle, null, titleRect, paint);
    }

    //When user touches on a ship object store the ship object that was touched
    private void shipSelect(Input input)
    {
        //Check if the user has pressed on the screen
        List<TouchEvent> touchEvents = input.getTouchEvents();
        for(TouchEvent touchEvent: touchEvents)
        {
            if(touchEvent.type == touchEvent.TOUCH_DOWN)
            {
                //check if the touch was on any of the ships bounding box if yes change game state, store the pointer index and the ship
                for(Ship ship: shipArray)
                {
                    if(touchEvent.x > ship.mBound.x &&
                            touchEvent.x < (ship.mBound.x + ship.mBound.getWidth()) &&
                            touchEvent.y > ship.mBound.y &&
                            touchEvent.y < (ship.mBound.y + ship.mBound.getHeight() ))
                    {
                        //Set x and y coordinates dragShipOffset vector, so when user drags the ship, the ship will be dragged from the point of touch
                        dragShipOffset.set(ship.mBound.x - touchEvent.x, ship.mBound.y - touchEvent.y);
                        shipToDrag = ship;
                        shipToDragPointerIndexOfInput = touchEvent.pointer;
                        gameShipPlacementState = GameShipPlacementState.SHIP_DRAG;
                    }
                }
            }
        }
    }

    //Drag the stored ship following the user's input
    private void shipDrag(Input input) {
        if (input.existsTouch(shipToDragPointerIndexOfInput)) {
            shipToDrag.mBound.x = input.getTouchX(shipToDragPointerIndexOfInput) + dragShipOffset.x;
            shipToDrag.mBound.y = input.getTouchY(shipToDragPointerIndexOfInput) + dragShipOffset.y;
        }

        //When user lifts their finger off the screen drop the bitmap, and change the game state
        List<TouchEvent> touchEvents = input.getTouchEvents();
        for (TouchEvent touchEvent : touchEvents)
            if (touchEvent.type == TouchEvent.TOUCH_UP
                    && touchEvent.pointer == shipToDragPointerIndexOfInput) {
                gameShipPlacementState = GameShipPlacementState.SHIP_SELECT;
            }

    }


    private void shipPlacement(IGraphics2D graphics2D){

        for(int i = 0;i<200;i++){
            if(shipArray[0].getmBound().x > smallBoxCoordinates[i][0] && shipArray[0].getmBound().x < smallBoxCoordinates[i][2]
                    && shipArray[0].getmBound().y > smallBoxCoordinates[i][1] && shipArray[0].getmBound().y < smallBoxCoordinates[i][3] ){

                smallBoxCoordinates[i][4] = 1;      //this sets that occupancy flag to 1, meaning there is a ship here

            }

            else{
                smallBoxCoordinates[i][4] = 0;
            }
        }
        //get the ship bound

        //get the first and last coordinates

        //if ship bound is in this box, mark it as occupied

    }

}
