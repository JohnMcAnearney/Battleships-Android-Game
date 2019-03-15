package uk.ac.qub.eeecs.game.BattleShips;

// This class will be responsible for displaying the board. On this screen the user will be able to setup their
// fleet with their ships in whatever way they wish


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

    /////////////////////////////////////////// - GENERAL STUFF - /////////////////////////////////////////////////////////////////

    private Bitmap boardSetupBackground, battleshipTitle;
    private PushButton mBackButton, mRotateButton, mPauseButton;
    private Paint paint = new Paint();
    private String message = "Not Detected", message2  ="";
    private float x,y;      // Coordinate values
    private int moveBackground =0;
    private final float MAX_SNAP_TO_DISTANCE = 10.0f;
    private boolean occupied; // Will hold the status of the box, true if there is a

    ////////////////////////////////////////// - BOX VARIABLES - //////////////////////////////////////////////////////////////////

    private float bigBoxLeftCoor=0;        // I could do a test method for these, testing if i change these variables that they all still fit in the
    private float bigBoxTopCoor=0;        // screen and if not set it back so it fits in screen
    private float bigBoxRightCoor=0;     // simple if right>screenwidth then return false and fix
    private float bigBoxBottomCoor=0;
    private float smallBoxWidth = 0;
    private float smallBoxHeight = 0;
    private boolean smallBoxDetected = false;      // If user clicked inside a small box this will be true
    private float[][] smallBoxCoordinates = new float[200][5];        // 2D array to store all of the small box co-ordinates, storing left, top, right,bottom
    private int numberOfSmallBoxesDrawn = -1;          // Counter for how many small boxes have been drawn
    private int numberofSmallBoxDetected ;             // Holds the 2d array index of the small box detected
    private boolean smallboxCoordinatesCaptured = false, smallboxCoordinatesCaptured2 = false;  // If the smallBoxCoordinates array has been populated by capturing the co ordinates for both boards this will be set to true

    ////////////////////////////////////////// - SHIP VARIABLES - //////////////////////////////////////////////////////////////////

    private boolean setShipBound = false;
    private Ship[] shipArray;  // Hold all of the ship objects
    private Ship selectedShip; // Ship object holder which will be used when user clicks on the ship and drags it across the screen
    private int shipToDragPointerIndexOfInput; // Pointer index holder, when user presses the screen the input index will be stored for dragging
    private enum GameShipPlacementState {SHIP_SELECT,SHIP_DRAG} // Game states, will swap between the states when the user will place ships onto the grid before the start of the game
    private GameShipPlacementState gameShipPlacementState = BoardSetupScreen.GameShipPlacementState.SHIP_SELECT;  // Initialized enum object set to ship select, for when the game starts
    private Vector2 dragShipOffset = new Vector2(); // Vector to store the x and y coordinates of the ship's coordinates minus the touch location
    private boolean toRotateShip = false;  // Boolean value used as a identifier to rotate the ship
    private boolean shipOutOfBound = false;

    ////////////////////////////////////////// - Constructor + UPDATE AND DRAW - //////////////////////////////////////////////////////////////////
    public BoardSetupScreen(Game game){
        super("BoardSetupBackground", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("WaterBackground", "img/Water_Tile.png");
        assetManager.loadAndAddBitmap("SettingsBackButton", "img/BackB.png");
        assetManager.loadAndAddBitmap("SettingsBackButtonP", "img/BackBPressed.png");
        assetManager.loadAndAddBitmap("rotateButton","img/rotateButton.png");
        assetManager.loadAndAddBitmap("Title", "img/Title.png");
        assetManager.loadAndAddBitmap("PauseButton", "img/Pause.png");
        battleshipTitle = assetManager.getBitmap("Title");
        boardSetupBackground = assetManager.getBitmap("WaterBackground");
        assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png");
        assetManager.loadAndAddBitmap("CargoShip", "img/CargoShip.png");
        assetManager.loadAndAddBitmap("CruiseShip", "img/CruiseShip.png");
        assetManager.loadAndAddBitmap("Destroyer", "img/Destroyer.png");
        assetManager.loadAndAddBitmap("Submarine", "img/Submarine.png");
        Ship aircraftCarrier = new Ship("AircraftCarrier", 0.641f,0.537f,assetManager.getBitmap("AircraftCarrier"), 5);
        Ship cargoShip = new Ship("CargoShip", 0.594f,0.397f,assetManager.getBitmap("CargoShip"), 4);
        Ship cruiseShip = new Ship("CruiseShip", 0.532f,0.487f,assetManager.getBitmap("CruiseShip"), 4);
        Ship submarine = new Ship("Submarine", 0.387f,0.573f,assetManager.getBitmap("Submarine"), 3);
        Ship destroyer = new Ship("Destroyer", 0.258f,0.363f,assetManager.getBitmap("Destroyer"), 2);
        shipArray = new Ship[]{aircraftCarrier,cargoShip,cruiseShip,destroyer,submarine};



    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        // Store the x and y co-ordinates from touch events
        for(TouchEvent touchEvent: touchEvents) {
            x = touchEvent.x;
            y = touchEvent.y;
        }


        switch(gameShipPlacementState)
        {
            case SHIP_SELECT: shipSelect(input); break;
            case SHIP_DRAG: shipDrag(input);break;
        }

        // If statement to detect if a touch event has happened
        if (touchEvents.size() > 0) {
            // Updating all the push buttons when a touch event is detected
            mBackButton.update(elapsedTime);
            mRotateButton.update(elapsedTime);
            mPauseButton.update(elapsedTime);

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
                selectedShip = shipArray[0];
                }
            else
            {
                rotateShipBy90Degrees();
            }
            }


            // Calling method to check if user input of x,y are inside a small box
            detectionIfUserSelectedSmallBox(x,y);

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

        if (!setShipBound )
        {
            setUpShipmBound(graphics2D);
        }
        drawShips(graphics2D);

        for(Ship shipArray: shipArray)
        {
            graphics2D.drawRect(shipArray.mBound.x, shipArray.mBound.y, shipArray.mBound.x + shipArray.mBound.getWidth(), shipArray.mBound.y + shipArray.mBound.getHeight(), highlight);
        }

        // If an user clicked on a small box highlight by painting a square using paint otherwise do nothing
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
        mRotateButton.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        mPauseButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }

    ////////////////////////////////////////// - OUR OWN METHODS - //////////////////////////////////////////////////////////////////
    private void drawBoardOne(IGraphics2D graphics2D){
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();

        bigBoxLeftCoor = (screenWidth/14f);       // I could do a test method for these, testing if i change these variables that they all still fit in the
        bigBoxTopCoor = screenHeight/5f;          // screen and if not set it back so it fits in screen
        bigBoxRightCoor = bigBoxLeftCoor*6f;      // simple if right>screenwidth then return false and fix
        bigBoxBottomCoor = (bigBoxTopCoor*4.5f);

        smallBoxWidth = (bigBoxRightCoor - bigBoxLeftCoor)/10f;       // These two must be added to the value as they are the square dimensions
        smallBoxHeight = (bigBoxBottomCoor - bigBoxTopCoor)/10f;

        // Step 2 - draw lots of smaller squares
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        numberOfSmallBoxesDrawn =-1;
        for(int rows =0; rows<10; rows++) {
            float moveConstLeft = 0;
            for (int column = 0; column < 10; column++) {
                numberOfSmallBoxesDrawn++;
                // Draw each of the small boxes
                graphics2D.drawRect((bigBoxLeftCoor + moveConstLeft), bigBoxTopCoor,      // Same start position
                        (bigBoxLeftCoor + smallBoxWidth + moveConstLeft), (bigBoxTopCoor + smallBoxHeight), paint);

                // Store all of the small box coordinates in a 2d array
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
                    paint.setStyle(Paint.Style.STROKE);       // THIS METHOD COULD BE USED FOR IF HIT PAINT RED ETC.
                    paint.setColor(Color.WHITE);
                }

                // Draw each of the small boxes
                graphics2D.drawRect((bigBoxLeftCoor + moveConstLeft), bigBoxTopCoor,      // Same start position
                        (bigBoxLeftCoor + smallBoxWidth + moveConstLeft), (bigBoxTopCoor + smallBoxHeight), paint);

                moveConstLeft += smallBoxWidth;

            }

            bigBoxTopCoor+=smallBoxHeight;
            bigBoxBottomCoor+=smallBoxHeight;

        }
        smallboxCoordinatesCaptured = true;
        bigBoxLeftCoor = (screenWidth/14f);       // I could do a test method for these, testing if i change these variables that they all still fit in the
        bigBoxTopCoor = screenHeight/5f;          // screen and if not set it back so it fits in screen
        bigBoxRightCoor = bigBoxLeftCoor*6f;      // simple if right>screenwidth then return false and fix
        bigBoxBottomCoor = (bigBoxTopCoor*4.5f);
    }

    private void drawBoardTwo(IGraphics2D graphics2D){
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();

        float bigBoxLeftCoor2 = (screenWidth/14f)*7.5f;       // I could do a test method for these, testing if i change these variables that they all still fit in the
        float bigBoxTopCoor2 = screenHeight/5f;               // screen and if not set it back so it fits in screen
        float bigBoxRightCoor2 = (bigBoxLeftCoor2*1.684f);    // simple if right>screenwidth then return false and fix
        float bigBoxBottomCoor2 = (bigBoxTopCoor2*4.5f);

        smallBoxWidth = (bigBoxRightCoor2 - bigBoxLeftCoor2)/10f;       // these two must be added to the value as they are the square dimensions
        smallBoxHeight = (bigBoxBottomCoor2 - bigBoxTopCoor2)/10f;

        // Step 2 - draw lots of smaller squares
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        numberOfSmallBoxesDrawn =99;
        for(int rows =0; rows<10; rows++) {
            float moveConstLeft = 0;
            for (int column = 0; column < 10; column++) {
                numberOfSmallBoxesDrawn++;
                // Draw each of the small boxes
                graphics2D.drawRect((bigBoxLeftCoor2 + moveConstLeft), bigBoxTopCoor2,      //same start position
                        (bigBoxLeftCoor2 + smallBoxWidth + moveConstLeft), (bigBoxTopCoor2 + smallBoxHeight), paint);

                // Store all of the small box coordinates in a 2d array
                if(!smallboxCoordinatesCaptured2 ) {
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][0] = bigBoxLeftCoor2 + moveConstLeft;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][1] = bigBoxTopCoor2;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][2] = bigBoxLeftCoor2 + smallBoxWidth + moveConstLeft;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][3] = bigBoxTopCoor2 + smallBoxHeight;

                }

//                if(smallBoxCoordinates[numberOfSmallBoxesDrawn][4] == 1){
//                    paint.setStyle(Paint.Style.FILL);
//                    paint.setColor(Color.YELLOW);
//                } else{
//                    paint.setStyle(Paint.Style.STROKE);       THIS METHOD COULD BE USED FOR IF HIT PAINT RED ETC.
//                    paint.setColor(Color.WHITE);
//                }
//
//                // Draw each of the small boxes
//                graphics2D.drawRect((bigBoxLeftCoor + moveConstLeft), bigBoxTopCoor,      //same start position
//                        (bigBoxLeftCoor + smallBoxWidth + moveConstLeft), (bigBoxTopCoor + smallBoxHeight), paint);

                moveConstLeft += smallBoxWidth;

            }

            bigBoxTopCoor2+=smallBoxHeight;
            bigBoxBottomCoor2+=smallBoxHeight;

        }
        smallboxCoordinatesCaptured2 = true;
        bigBoxLeftCoor2 = (screenWidth/14f);       // I could do a test method for these, testing if i change these variables that they all still fit in the
        bigBoxTopCoor2 = screenHeight/5f;          // screen and if not set it back so it fits in screen
        bigBoxRightCoor2 = bigBoxLeftCoor2*6f;     // simple if right>screenwidth then return false and fix
        bigBoxBottomCoor2 = (bigBoxTopCoor2*4.5f);
    }

    private void setUpShipmBound(IGraphics2D graphics2D)
    {
        float screenWidth = graphics2D.getSurfaceWidth();
        float screenHeight = graphics2D.getSurfaceHeight();
        float widthOfSmallBox = (bigBoxRightCoor - bigBoxLeftCoor)/10f;
        float heightOfSmallBox = (bigBoxBottomCoor - bigBoxTopCoor)/10f;
        // Setting the ships bounding box, including the x,y co-ordinates and the half width and half height using bounding box setter
        shipArray[0].setmBound(Math.round(screenWidth*0.015),
                Math.round(screenHeight*0.2),
                Math.round(widthOfSmallBox*shipArray[0].getShipLength()/2.0f),
                heightOfSmallBox/2f);

        shipArray[1].setmBound(Math.round(screenWidth*0.015),
                Math.round(screenHeight*0.28),
                Math.round(widthOfSmallBox*shipArray[1].getShipLength()/2.0f),
                heightOfSmallBox/2f);

        shipArray[2].setmBound(Math.round(screenWidth*0.015),
                Math.round(screenHeight*0.36),
                Math.round(widthOfSmallBox*shipArray[2].getShipLength()/2.0f),
                heightOfSmallBox/2f);

        shipArray[3].setmBound(Math.round(screenWidth*0.015),
                Math.round(screenHeight*0.44),
                Math.round(widthOfSmallBox*shipArray[3].getShipLength()/2.0f),
                heightOfSmallBox/2f);

        shipArray[4].setmBound(Math.round(screenWidth*0.015),
                Math.round(screenHeight*0.52),
                Math.round(widthOfSmallBox*shipArray[4].getShipLength()/2.0f),
                heightOfSmallBox/2f);

        setShipBound = true;
    }

    // Method to draw the ships stored in the shipArray onto the screen
    private void drawShips(IGraphics2D graphics2D){

        for(Ship ship: shipArray) {
            ship.drawShip(graphics2D);
        }
    }

    // Highlight the small box passed in as a parameter
    private void highlightBoxGiven(int numberofSmallBox,Paint p, IGraphics2D iGraphics2D)
    {

        iGraphics2D.drawRect(smallBoxCoordinates[numberofSmallBox][0], smallBoxCoordinates[numberofSmallBox][1],
                smallBoxCoordinates[numberofSmallBox][2],smallBoxCoordinates[numberofSmallBox][3], p);

    }

    // This method checks small box co-ordinates against the x and y input co-ordinates, sets parameter smallBoxDetected to true if yes, false otherwise
    private void detectionIfUserSelectedSmallBox(float x,float y)
    {
        smallBoxDetected = false;
        if(bigBoxRightCoor == 0)
        {

        }
        else {

            if (x <= bigBoxRightCoor) {
                numberofSmallBoxDetected = binarySearchBox(smallBoxCoordinates, 0, 100, x, y);
                if (numberofSmallBoxDetected >= 0)
                    smallBoxDetected = true;

            } else if (x > bigBoxRightCoor){
                numberofSmallBoxDetected = binarySearchBox(smallBoxCoordinates, 100, 200, x, y);
            if (numberofSmallBoxDetected >= 0)
                smallBoxDetected = true;
        }

        }

        /*for(int i = 0; i < 200; i++)
        {
            if( x > smallBoxCoordinates[i][0] && x < smallBoxCoordinates[i][2]
                    && y > smallBoxCoordinates[i][1] && y < smallBoxCoordinates[i][3]) {
                numberofSmallBoxDetected = i;
                smallBoxDetected = true;
                break;
            }
        }*/
    }

    private void createButtons() {
        // Trigger Button at the bottom left of the screen
        mBackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "SettingsBackButton","SettingsBackButtonP",  this);
        mBackButton.setPlaySounds(true, true);

        mRotateButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.88f,   mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "rotateButton", "rotateButton", this);

        mPauseButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.9f, mDefaultLayerViewport.getHeight() * 0.9f,
                mDefaultLayerViewport.getWidth() * 0.05f, mDefaultLayerViewport.getHeight() * 0.05f,
                "PauseButton", this);
        mPauseButton.setPlaySounds(true, true);
    }

    public void drawStaticImages(IGraphics2D graphics2D){
        Matrix bcgMatrix = new Matrix();

        bcgMatrix.setScale(2.5f, 2.5f);
        bcgMatrix.postTranslate(-moveBackground,0);
        graphics2D.drawBitmap(boardSetupBackground, bcgMatrix, paint);
        // Could do some maths to figure out exact ,middle using bitmaps ac size but looks ok for now
        Rect titleRect = new Rect(graphics2D.getSurfaceWidth()/3, 10, (graphics2D.getSurfaceWidth()/3)*2, graphics2D.getSurfaceHeight()/9);
        graphics2D.drawBitmap(battleshipTitle, null, titleRect, paint);
    }

    // When user touches on a ship object store the ship object that was touched
    private void shipSelect(Input input)
    {
        // Check if the user has pressed on the screen
        List<TouchEvent> touchEvents = input.getTouchEvents();
        for(TouchEvent touchEvent: touchEvents)
        {
            if(touchEvent.type == touchEvent.TOUCH_DOWN)
            {
                // Check if the touch was on any of the ships bounding box if yes change game state, store the pointer index and the ship
                for(Ship ship: shipArray)
                {
                    if(touchEvent.x > ship.mBound.x &&
                            touchEvent.x < (ship.mBound.x + ship.mBound.getWidth()) &&
                            touchEvent.y > ship.mBound.y &&
                            touchEvent.y < (ship.mBound.y + ship.mBound.getHeight() ))
                    {
                        // Set x and y coordinates dragShipOffset vector, so when user drags the ship, the ship will be dragged from the point of touch
                        dragShipOffset.set(ship.mBound.x - touchEvent.x, ship.mBound.y - touchEvent.y);
                        selectedShip = ship;
                        shipToDragPointerIndexOfInput = touchEvent.pointer;
                        gameShipPlacementState = GameShipPlacementState.SHIP_DRAG;
                    }
                }
            }
        }
    }

    // Drag the stored ship following the user's input
    private void shipDrag(Input input) {
        if (input.existsTouch(shipToDragPointerIndexOfInput)) {
            selectedShip.mBound.x = input.getTouchX(shipToDragPointerIndexOfInput) + dragShipOffset.x;
            selectedShip.mBound.y = input.getTouchY(shipToDragPointerIndexOfInput) + dragShipOffset.y;
        }

        // When user lifts their finger off the screen drop the bitmap, and change the game state
        List<TouchEvent> touchEvents = input.getTouchEvents();
        for (TouchEvent touchEvent : touchEvents)
            if (touchEvent.type == TouchEvent.TOUCH_UP
                    && touchEvent.pointer == shipToDragPointerIndexOfInput) {
                shipPlacement(); // Call ship placement after the user has actually placed it makes more sense. This stops visual glitches.
                gameShipPlacementState = GameShipPlacementState.SHIP_SELECT;
            }

    }


    private void shipPlacement(){

        for(int i = 0;i<200;i++){
            if(selectedShip.getmBound().x > smallBoxCoordinates[i][0] && selectedShip.getmBound().x < smallBoxCoordinates[i][2]
                    && selectedShip.getmBound().y > smallBoxCoordinates[i][1] && selectedShip.getmBound().y < smallBoxCoordinates[i][3] ){

                isShipOutOfBound(i);

                if(shipOutOfBound == true){
                    resetShipPosition();
                    break;
                }
                // smallBoxCoordinates[i][4] = 1;      // This sets that occupancy flag to 1, meaning there is a ship here
                for(int x = 0; x<selectedShip.getShipLength()-1; x++){
                    smallBoxCoordinates[i][4] = 1;
                    i++;
                }
            }
            else{
                smallBoxCoordinates[i][4] = 0;
            }
        }
        // Get the ship bound
        // Get the first and last coordinates
        // If ship bound is in this box, mark it as occupied
    }

    private void rotateShipBy90Degrees()
    {
        selectedShip.rotate = true;
    }

    private void isShipOutOfBound(int i){
        // THIS 0 will need fixed, so can do with all ships
        if (smallBoxCoordinates[i][3] != smallBoxCoordinates[i + selectedShip.getShipLength() - 1][3]) {
            shipOutOfBound = true;
        } else {
            shipOutOfBound = false;
        }

    }

    private void resetShipPosition(){
        selectedShip.setmBound(1500,120, Math.round(((bigBoxRightCoor - bigBoxLeftCoor)/10f)*2.5),
                ((bigBoxBottomCoor - bigBoxTopCoor)/10f)/1.25f);
    }

    private void shipSnapToBox(int i){
        // Closest box is between smallBoxWidth/2 coor and ship coor
//        float closestSlotDistanceSqrd = Float.MAX_VALUE;
//
//        for(int x = 0; x<100;x++){
//            if(smallBoxCoordinates[i][4]==1){
//                break;  //as no need to calculate, this box is occupied
//            }
//            else{
//                float distanceSqrd =
//                        (smallBoxCoordinates[x][0] - shipArray[0].getmBound().x) * (smallBoxCoordinates[x][0] - shipArray[0].getmBound().x)
//                                + (smallBoxCoordinates[x][1] - shipArray[0].getmBound().y) * (smallBoxCoordinates[x][1] - shipArray[0].getmBound().y);
//                if(distanceSqrd < closestSlotDistanceSqrd) {
//                    numberofSmallBoxDetected = x;
//                    closestSlotDistanceSqrd = distanceSqrd;
//                }
//            }
//        }
//
//        if (Math.sqrt(closestSlotDistanceSqrd) <= MAX_SNAP_TO_DISTANCE){
//            shipArray[0].setmBound(100,400, Math.round(((bigBoxRightCoor - bigBoxLeftCoor)/10f)*2.5),
//               ((bigBoxBottomCoor - bigBoxTopCoor)/10f)/1.25f);
//        }
    }

    private int binarySearchBox(float[][] array,int lower, int higher, float x, float y)
    {
        if(lower <=higher) {
            int mid = (int)( (lower + higher ) / 2);

            if(boxContainsInput(array,mid,x,y))
            {
                return mid;
            }

            if(y > array[mid][1] && y < array[mid][3])
            {
                return binarySearchRows(array,mid-(mid%10),0,10, x,y );
            }

            if(y > array[mid][1] && y > array[mid][3])
                return binarySearchBox(array,mid,higher,x,y);

            if(y<array[mid][1] && y < array[mid][3])
                return binarySearchBox(array,lower,mid,x,y);
        }
        return -1;
    }


    private int binarySearchRows(float[][] array,int numberOfSmallBox, int lower,int higher, float x, float y)
    {
        int mid = (int)(numberOfSmallBox + (((lower%10)+higher )/2));
        if(boxContainsInput(array,mid,x,y))
        {
            return mid;
        }
        if(x < array[mid][0] && x < array[mid][2])
            return binarySearchRows(array,numberOfSmallBox,lower,mid%10,x,y);

        if(x > array[mid][0] && x > array[mid][2])
            return binarySearchRows(array,numberOfSmallBox,mid%10,higher,x,y);

        return -1;
    }

    private boolean boxContainsInput(float[][] smallBoxCoordinates, int numberOfSmallBox, float x, float y)
    {
        if(x > smallBoxCoordinates[numberOfSmallBox][0] &&
                x < smallBoxCoordinates[numberOfSmallBox][2] &&
                y > smallBoxCoordinates[numberOfSmallBox][1] &&
                y < smallBoxCoordinates[numberOfSmallBox][3])
        {
            return true;
        }

        return false;
    }

}
