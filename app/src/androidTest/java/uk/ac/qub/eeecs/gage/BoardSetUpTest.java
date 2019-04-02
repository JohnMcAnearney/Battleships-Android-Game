package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.junit.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BattleShips.BoardSetupScreen;
import uk.ac.qub.eeecs.game.BattleShips.Ship;
import uk.ac.qub.eeecs.game.DemoGame;

@RunWith(MockitoJUnitRunner.class)
public class BoardSetUpTest {

    private Game game;
    private GameScreen gameScreen;
    private AssetManager assetManager;
    private BoardSetupScreen boardScreen;
    private Context context;
    private boolean shipOutOfBound;
    private BoundingBox boardBoundingBox ;
    private Ship[] shipArray;   //setting up the ship array so that they can be tested globally
    // this line causes an empty test suite
    //private @Mock Bitmap bitmap;


    @Before
    public void setup()
    {
        context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        assetManager = game.getAssetManager();
        boardScreen = new BoardSetupScreen(game);

        //Firstly, Setting up each of the ships as they will be tested quite a bit
        //load bitmaps
        assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png");
        assetManager.loadAndAddBitmap("CargoShip", "img/CargoShip.png");
        assetManager.loadAndAddBitmap("CruiseShip", "img/CruiseShip.png");
        assetManager.loadAndAddBitmap("Submarine", "img/Submarine.png");
        assetManager.loadAndAddBitmap("Destroyer", "img/Destroyer.png");
        //Set up Ship objects
        Ship aircraftCarrier = new Ship("AircraftCarrier", 0.5f,0.5f,assetManager.getBitmap("AircraftCarrier"), 5);
        Ship cargoShip = new Ship("CargoShip", 0.5f,0.5f,assetManager.getBitmap("CargoShip"), 4);
        Ship cruiseShip = new Ship("CruiseShip", 0.5f,0.5f,assetManager.getBitmap("CruiseShip"), 4);
        Ship submarine = new Ship("Submarine", 0.5f,0.5f,assetManager.getBitmap("Submarine"), 3);
        Ship destroyer = new Ship("Destroyer",  0.5f,0.5f,assetManager.getBitmap("Destroyer"), 2);
        shipArray = new Ship[]{aircraftCarrier,cargoShip,cruiseShip,destroyer,submarine};

        int screenWidth = 1920;
        int screenHeight = 1080;
        float screenHeightOffset = 0.1f;
        //Setting the ships bounding box, including the x,y co-ordinates and the half width and half height using bounding box setter
        for (Ship ship : shipArray) {
            screenHeightOffset = screenHeightOffset + 0.08f;
            ship.setmBound(screenWidth * 0.015f,
                    screenHeight * screenHeightOffset,
                    (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f); }

        float bigBoxLeftCoor = screenWidth/14f;
        float bigBoxTopCoor = screenHeight/5f;
        float bigBoxRightCoor = (screenWidth/14f)*6f;
        float bigBoxBottomCoor = (screenHeight/5f)*4.5f;
        boardBoundingBox = new BoundingBox((bigBoxLeftCoor + bigBoxRightCoor)/2,
                (bigBoxBottomCoor + bigBoxTopCoor)/2,
                ((bigBoxLeftCoor + bigBoxRightCoor)/2)-bigBoxLeftCoor,
                ((bigBoxBottomCoor + bigBoxTopCoor)/2)-bigBoxTopCoor);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tests Made by Mantas Stadnik (40203133)
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void required_Bitmaps_Loaded()
    {

        assertTrue(assetManager.loadAndAddBitmap("PlayButton", "img/AcceptButton.png"));
        assertTrue(assetManager.loadAndAddBitmap("rotateButton","img/rotateButton.png"));
        assertTrue(assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png"));
        assertTrue(assetManager.loadAndAddBitmap("CargoShip", "img/CargoShip.png"));
        assertTrue(assetManager.loadAndAddBitmap("CruiseShip", "img/CruiseShip.png"));
        assertTrue(assetManager.loadAndAddBitmap("Destroyer", "img/Destroyer.png"));
        assertTrue(assetManager.loadAndAddBitmap("Submarine", "img/Submarine.png"));
    }

    @Test
    public void createShipObjectAircraftCarrier()
    {
        assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png");
        Ship aircraftCarrier = new Ship("AircraftCarrier", 0.64f,0.7f,assetManager.getBitmap("AircraftCarrier"), 5);
        assertEquals("AircraftCarrier", aircraftCarrier.getShipType());
        assertEquals(0.64f, aircraftCarrier.getScaleRatioX());
        assertEquals(0.7f, aircraftCarrier.getScaleratioY());
        assertEquals(5, aircraftCarrier.getShipLength());
    }


    @Test
    public void createShipObjectCargoShip()
    {
        assetManager.loadAndAddBitmap("CargoShip", "img/CargoShip.png");
        Ship cargoShip = new Ship("CargoShip", 0.5f,0.4f,assetManager.getBitmap("CargoShip"), 4);
        assertEquals("CargoShip", cargoShip.getShipType());
        assertEquals(0.5f, cargoShip.getScaleRatioX());
        assertEquals(0.4f, cargoShip.getScaleratioY());
        assertEquals(4, cargoShip.getShipLength());
    }

    @Test
    public void createShipObjectCruiseShip()
    {
        assetManager.loadAndAddBitmap("CruiseShip", "img/CruiseShip.png");
        Ship cruiseShip = new Ship("CruiseShip",0.3f ,5f,assetManager.getBitmap("CruiseShip"), 4);
        assertEquals("CruiseShip", cruiseShip.getShipType());
        assertEquals(0.3f, cruiseShip.getScaleRatioX());
        assertEquals(5f, cruiseShip.getScaleratioY());
        assertEquals(4, cruiseShip.getShipLength());
    }

    @Test
    public void createShipObjectSubmarine()
    {
        assetManager.loadAndAddBitmap("Submarine", "img/Submarine.png");
        Ship submarine = new Ship("Submarine", 0.1f,0.2f,assetManager.getBitmap("Submarine"), 3);
        assertEquals("Submarine", submarine.getShipType());
        assertEquals(0.1f, submarine.getScaleRatioX());
        assertEquals(0.2f, submarine.getScaleratioY());
        assertEquals(3, submarine.getShipLength());
    }

    @Test
    public void createShipObjectDestroyer()
    {
        assetManager.loadAndAddBitmap("Destroyer", "img/Destroyer.png");
        Ship destroyer = new Ship("Destroyer", 0,0,assetManager.getBitmap("Destroyer"), 2);
        assertEquals("Destroyer", destroyer.getShipType());
        assertEquals(0f, destroyer.getScaleRatioX());
        assertEquals(0f, destroyer.getScaleratioY());
        assertEquals(2, destroyer.getShipLength());
    }


    @Test
    public void SetUpShipmBound()
    {
        //load bitmaps
        assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png");
        assetManager.loadAndAddBitmap("CargoShip", "img/CargoShip.png");
        assetManager.loadAndAddBitmap("CruiseShip", "img/CruiseShip.png");
        assetManager.loadAndAddBitmap("Submarine", "img/Submarine.png");
        assetManager.loadAndAddBitmap("Destroyer", "img/Destroyer.png");
        //Set up Ship objects
        Ship aircraftCarrier = new Ship("AircraftCarrier", 0.5f,0.5f,assetManager.getBitmap("AircraftCarrier"), 5);
        Ship cargoShip = new Ship("CargoShip", 0.5f,0.5f,assetManager.getBitmap("CargoShip"), 4);
        Ship cruiseShip = new Ship("CruiseShip", 0.5f,0.5f,assetManager.getBitmap("CruiseShip"), 4);
        Ship submarine = new Ship("Submarine", 0.5f,0.5f,assetManager.getBitmap("Submarine"), 3);
        Ship destroyer = new Ship("Destroyer",  0.5f,0.5f,assetManager.getBitmap("Destroyer"), 2);
        Ship[] shipArray = new Ship[]{aircraftCarrier,cargoShip,cruiseShip,destroyer,submarine};


        int screenWidth = 1920;
        int screenHeight = 1080;
        float screenHeightOffset = 0.1f;
        //Setting the ships bounding box, including the x,y co-ordinates and the half width and half height using bounding box setter
        for (Ship ship : shipArray) {
            screenHeightOffset = screenHeightOffset + 0.08f;
            ship.setmBound(screenWidth * 0.015f,
                    screenHeight * screenHeightOffset,
                    (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f); }

                    assertEquals(29,Math.round(shipArray[0].getmBound().x));
        assertEquals(194,Math.round(shipArray[0].getmBound().y));
        assertEquals(250,Math.round(shipArray[0].getmBound().halfWidth));
        assertEquals(5,Math.round(shipArray[0].getmBound().halfHeight));

        assertEquals(29,Math.round(shipArray[1].getmBound().x));
        assertEquals(281,Math.round(shipArray[1].getmBound().y));
        assertEquals(200,Math.round(shipArray[1].getmBound().halfWidth));
        assertEquals(5,Math.round(shipArray[1].getmBound().halfHeight));

        assertEquals(29,Math.round(shipArray[2].getmBound().x));
        assertEquals(367,Math.round(shipArray[2].getmBound().y));
        assertEquals(200,Math.round(shipArray[2].getmBound().halfWidth));
        assertEquals(5,Math.round(shipArray[2].getmBound().halfHeight));

        assertEquals(29,Math.round(shipArray[3].getmBound().x));
        assertEquals(454,Math.round(shipArray[3].getmBound().y));
        assertEquals(100,Math.round(shipArray[3].getmBound().halfWidth));
        assertEquals(5,Math.round(shipArray[3].getmBound().halfHeight));

        assertEquals(29,Math.round(shipArray[4].getmBound().x));
        assertEquals(540,Math.round(shipArray[4].getmBound().y));
        assertEquals(150,Math.round(shipArray[4].getmBound().halfWidth));
        assertEquals(5,Math.round(shipArray[4].getmBound().halfHeight));
    }

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

    @Test
    public void boxContainsInputTest()
    {
        assertFalse(boxContainsInput(0,0,0,0,0,0));
        assertFalse(boxContainsInput(0,0,0,0,10,10));
        assertFalse(boxContainsInput(100,100,100,100,0,0));
        assertFalse(boxContainsInput(100,100,100,100,99.9f,99.9f));
        assertFalse(boxContainsInput(1000,1200,1000,1200,1000,1000));
        assertFalse(boxContainsInput(500,800,400,600,0,0));
        assertFalse(boxContainsInput(200,350,200,220,250,500));

        assertTrue(boxContainsInput(0,700,0,800,50,50));
        assertTrue(boxContainsInput(0,100,0,100,99,99));
        assertTrue(boxContainsInput(0,100,0,100,1,1));
        assertTrue(boxContainsInput(0,200,0,300,55.5f,55.5f));
    }

    //Method to set up 2d array with co-ordinates of boxes for testing the binary search methods
    public float[][] setUp2DArrayWithCoors()
    {
        //Set up 2D array
        //offsets for setting co-ordinates
        int offsetLeftX = 0;
        int offsetRightX = 5;
        int offsetTopY = 0;
        int offsetBottomY = 5;
        //keep count of number of boxes stored
        int numberOfBoxesStored = 0;
        float[][] boxArray = new float[100][5];
        //populate the 2D array store in order, left , top, right ,bottom co-ordinates
        for(int i = 0; i < 10; i ++)
        {
            offsetLeftX = 0;
            offsetRightX = 5;
            for(int j = 0; j < 10; j++)
            {
                boxArray[numberOfBoxesStored][0] = offsetLeftX;
                boxArray[numberOfBoxesStored][2] = offsetRightX;
                boxArray[numberOfBoxesStored][3] = offsetBottomY;
                boxArray[numberOfBoxesStored][1] = offsetTopY;
                offsetLeftX += 5;
                offsetRightX += 5;
                numberOfBoxesStored++;
            }
            offsetTopY +=5;
            offsetBottomY +=5;
        }
        return boxArray;
    }


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

    @Test
    public void binarySearchRowsTest()
    {
        float[][] boxArray = setUp2DArrayWithCoors();

        //knowing first box in array contains 0,0,5,5 co-ordinates, when calling binarySearchRows
        //with input x 3 and input y 3, box 0 is expected to be returned
        assertEquals(0,binarySearchRows(boxArray,0,0,10,3,3));
        //Binary search must return -1 when other rows are passed through
        assertEquals(-1,binarySearchRows(boxArray,10,0,10,3,3));
        assertEquals(-1,binarySearchRows(boxArray,20,0,10,3,3));
        assertEquals(-1,binarySearchRows(boxArray,30,0,10,3,3));
        assertEquals(-1,binarySearchRows(boxArray,40,0,10,3,3));
        assertEquals(-1,binarySearchRows(boxArray,50,0,10,3,3));
        assertEquals(-1,binarySearchRows(boxArray,60,0,10,3,3));
        assertEquals(-1,binarySearchRows(boxArray,70,0,10,3,3));
        assertEquals(-1,binarySearchRows(boxArray,80,0,10,3,3));
        assertEquals(-1,binarySearchRows(boxArray,90,0,10,3,3));
        //knowing first box in array contains 0,0,5,5 co-ordinates, when calling binarySearchRows
        //with input x 1000 and input y 1000, -1 is expected to be returned indicating box was not found
        //for all 10 rows
        assertEquals(-1,binarySearchRows(boxArray,0,0,10,1000,1000));
        assertEquals(-1,binarySearchRows(boxArray,10,0,10,1000,1000));
        assertEquals(-1,binarySearchRows(boxArray,20,0,10,1000,1000));
        assertEquals(-1,binarySearchRows(boxArray,30,0,10,1000,1000));
        assertEquals(-1,binarySearchRows(boxArray,40,0,10,1000,1000));
        assertEquals(-1,binarySearchRows(boxArray,50,0,10,1000,1000));
        assertEquals(-1,binarySearchRows(boxArray,60,0,10,1000,1000));
        assertEquals(-1,binarySearchRows(boxArray,70,0,10,1000,1000));
        assertEquals(-1,binarySearchRows(boxArray,80,0,10,1000,1000));
        assertEquals(-1,binarySearchRows(boxArray,90,0,10,1000,1000));

        //check if binarySearch is able to search all of the boxes in all rows with inputs contained
        //in one of the boxes in each row and successfully return the right box
        assertEquals(1,binarySearchRows(boxArray,0,0,10,6,2));
        assertEquals(11,binarySearchRows(boxArray,10,0,10,7,7));
        assertEquals(22,binarySearchRows(boxArray,20,0,10,14,11));
        assertEquals(33,binarySearchRows(boxArray,30,0,10,19,16));
        assertEquals(44,binarySearchRows(boxArray,40,0,10,22,24));
        assertEquals(55,binarySearchRows(boxArray,50,0,10,28,27));
        assertEquals(66,binarySearchRows(boxArray,60,0,10,31,33));
        assertEquals(77,binarySearchRows(boxArray,70,0,10,36,37));
        assertEquals(88,binarySearchRows(boxArray,80,0,10,43,42));
        assertEquals(99,binarySearchRows(boxArray,90,0,10,49,49));

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //Tests Made by John McAnearney (40203900)
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * I was unsure whether it were better practice to make all my methods, that I need from the BoardSetupScreen Class, Public or to just copy them in here.
     * Unfortunately I was unable to ask Philip due to unforeseen circumstances, hence why I've decided to just copy them into here.
     */
    @Test
    public void boardBoundSetupTest_ValidData(){
        float screenWidth = 1920;
        float screenHeight = 1080;
        float bigBoxLeftCoor = screenWidth/14f;
        float bigBoxTopCoor = screenHeight/5f;
        float bigBoxRightCoor = (screenWidth/14f)*6f;
        float bigBoxBottomCoor = (screenHeight/5f)*4.5f;
        BoundingBox boardBoundingBox = new BoundingBox((bigBoxLeftCoor + bigBoxRightCoor)/2,
                (bigBoxBottomCoor + bigBoxTopCoor)/2,
                ((bigBoxLeftCoor + bigBoxRightCoor)/2)-bigBoxLeftCoor,
                ((bigBoxBottomCoor + bigBoxTopCoor)/2)-bigBoxTopCoor);


        assertNotNull(boardBoundingBox);    //asserts that an object was actually created
        //some of the following tests failed due to rounding errors however, they have been fixed so as to all pass
        assertEquals(137, Math.round(bigBoxLeftCoor));
        assertEquals(216, Math.round(bigBoxTopCoor));
        assertEquals(823, Math.round(bigBoxRightCoor));
        assertEquals(972, Math.round(bigBoxBottomCoor));
    }

    @Test
    public void boardBoundSetupTest_InvalidData(){
        float screenWidth = 1920;
        float screenHeight = 1080;      //these are just arbitrary, wrong values
        float bigBoxLeftCoor = 20;
        float bigBoxTopCoor = 30;
        float bigBoxRightCoor = (screenWidth/14f);
        float bigBoxBottomCoor = (screenHeight/5f);
        BoundingBox boardBoundingBox = new BoundingBox((bigBoxLeftCoor + bigBoxRightCoor)/2,
                (bigBoxBottomCoor + bigBoxTopCoor)/2,
                ((bigBoxLeftCoor + bigBoxRightCoor)/2)-bigBoxLeftCoor,
                ((bigBoxBottomCoor + bigBoxTopCoor)/2)-bigBoxTopCoor);


        assertNotNull(boardBoundingBox);    //asserts that an object was actually created
        assertFalse(137 == Math.round(bigBoxLeftCoor));
        assertFalse(216 == Math.round(bigBoxTopCoor));
        assertFalse(823 == Math.round(bigBoxRightCoor));
        assertFalse(972 == Math.round(bigBoxBottomCoor));
    }

    private boolean isShipOutOfBound(Ship ship){

        //pretty self explanatory
        if(boardBoundingBox.contains(ship.getmBound().x,  ship.getmBound().y) &&
                boardBoundingBox.contains(ship.getmBound().x + (ship.getmBound().halfWidth * 2),
                        ship.getmBound().y + (ship.getmBound().halfHeight * 2))){
            shipOutOfBound = false;
        }
        else {
            shipOutOfBound =true;
        }

        return shipOutOfBound;
    }

    @Test
    public void isShipOutOfBoundTest_ValidData1(){
        //firstly set position of ship
        for(Ship ship: shipArray){
            ship.setmBound(300,600, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f );
            assertFalse(isShipOutOfBound(ship));
        }
        //refactored this test so there wouldn't be multiple unnecessary tests
    }

    @Test
    public void isShipOutOfBoundTest_ValidData2(){
        //firstly set position of ship
        for(Ship ship: shipArray){
            ship.setmBound(200,700, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f );
            assertFalse(isShipOutOfBound(ship));
        }
        //refactored this test so there wouldn't be multiple unnecessary tests
    }

    @Test
    public void isShipOutOfBoundTest_InvalidData1(){
        //firstly set position of ship
        for(Ship ship: shipArray){
            ship.setmBound(700,700, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f );
            assertTrue(isShipOutOfBound(ship));
        }
        //refactored this test so there wouldn't be multiple unnecessary tests
    }
    @Test
    public void isShipOutOfBoundTest_InvalidData2(){
        //firstly set position of ship
        for(Ship ship: shipArray){
            ship.setmBound(438,195, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f );
            assertTrue(isShipOutOfBound(ship));
        }
        //refactored this test so there wouldn't be multiple unnecessary tests
    }

    @Test
    public void isShipOutOfBoundTest_VeryCloseDataRightsideInvalid(){
        // setting position of ships very close to the right side of the board
        for(Ship ship: shipArray){
            ship.setmBound(973,195, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f );
            assertTrue(isShipOutOfBound(ship));
        }
        //refactored this test so there wouldn't be multiple unnecessary tests
    }
    @Test
    public void isShipOutOfBoundTest_VeryCloseDataLeftsideInvalid(){
        // setting position of ships very close to the left side of the board
        //x = (-113) is for the ship of length 5 to be right beside the box, testing it being close
        // the other ships will be smaller and so theyre right-hand-side co-ordinate will be farther away from the LeftSide of board,
        // meaning they too are out of bounds
        for(Ship ship: shipArray){
            ship.setmBound(-113,195, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f );
            assertTrue(isShipOutOfBound(ship));
        }
        //refactored this test so there wouldn't be multiple unnecessary tests
    }

    @Test
    public void isShipOutOfBoundTest_VeryCloseDataTopsideInvalid(){
        // setting position of ships very close to the top of the board
        for(Ship ship: shipArray){
            ship.setmBound(300,206, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f );
            assertTrue(isShipOutOfBound(ship));
        }
        //refactored this test so there wouldn't be multiple unnecessary tests
    }

    @Test
    public void isShipOutOfBoundTest_VeryCloseDataBottomsideInvalid(){
        // setting position of ships very close to the top of the board
        for(Ship ship: shipArray){
            ship.setmBound(300,982, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f );
            assertTrue(isShipOutOfBound(ship));
        }
        //refactored this test so there wouldn't be multiple unnecessary tests
    }

    public int calculateClosestBox(){

        int numberOfClosestBox = 0;

        //setup the boxes
        float[][] smallBoxCoordinates = setupBoard();

        //for each ship, set bound close to the box number to be tested + 5 pixels in x and y
        //this allows for quick easy testing instead of hard coding
        for(Ship ship: shipArray) { //440, 240
           // ship.setmBound(smallBoxCoordinates[numberOfBoxToBeTested][0]+5, smallBoxCoordinates[numberOfBoxToBeTested][1]+5, (100 * ship.getShipLength()) / 2.0f,
           //         ((100) / 10f) / 2f);


            //closest box is between smallBoxWidth/2 coor and ship coor
            //set to this becasuse the closest distance will, basically, always be less than this. Always less in this game.
            float closestSlotDistanceSqrd = Float.MAX_VALUE;

            for (int x = 0; x < 100; x++) {
                //this is (x2-x1)^2 + (y2-y1)^2, using equation between two points
                float distanceSqrd =
                        ((smallBoxCoordinates[x][0] - ship.getmBound().x) * (smallBoxCoordinates[x][0] - ship.getmBound().x)
                                + (smallBoxCoordinates[x][1] - ship.getmBound().y) * (smallBoxCoordinates[x][1] - ship.getmBound().y));
                if (distanceSqrd < closestSlotDistanceSqrd) {
                    //if the new calculated distance to the current evaluated square is less than the current closest square, then this evaluated square becomes the closest one.
                    numberOfClosestBox = x;
                    closestSlotDistanceSqrd = distanceSqrd;
                }
            }
        }

        return numberOfClosestBox;
    }

    @Test
    public void TestCalculateClosestBoxIs4() {

        float[][] smallBoxCoordinates = setupBoard();

        for(Ship ship : shipArray) {
            ship.setmBound(smallBoxCoordinates[4][0] + 5, smallBoxCoordinates[4][1] + 5, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);
        }

            //assert for each ship the closest box is box 62
            assertTrue(calculateClosestBox() == 4);

            //assert that the closest is not 1 and 2 blocks either side
            assertFalse(calculateClosestBox() == 2);
            assertFalse(calculateClosestBox() == 3);
            assertFalse(calculateClosestBox() == 5);
            assertFalse(calculateClosestBox() == 6);

    }
    @Test
    public void TestCalculateClosestBoxIs62() {

        float[][] smallBoxCoordinates = setupBoard();

        for(Ship ship : shipArray) {
            ship.setmBound(smallBoxCoordinates[62][0] + 5, smallBoxCoordinates[62][1] + 5, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);
        }

        //assert for each ship the closest box is box 62
        assertTrue(calculateClosestBox() == 62);

        //assert that the closest is not 1 and 2 blocks either side
        assertFalse(calculateClosestBox() == 60);
        assertFalse(calculateClosestBox() == 61);
        assertFalse(calculateClosestBox() == 63);
        assertFalse(calculateClosestBox() == 64);

    }
    @Test
    //this test tests the right hand side of the board
    public void TestCalculateClosestBoxIs79() {

        float[][] smallBoxCoordinates = setupBoard();

        for(Ship ship : shipArray) {
            ship.setmBound(smallBoxCoordinates[79][0] + 5, smallBoxCoordinates[79][1] + 5, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);
        }

        //assert for each ship the closest box is box 62
        assertTrue(calculateClosestBox() == 79);

        //assert that the closest is not 1 and 2 blocks either side
        assertFalse(calculateClosestBox() == 59);
        assertFalse(calculateClosestBox() == 69);
        assertFalse(calculateClosestBox() == 89);
        assertFalse(calculateClosestBox() == 99);

    }
    @Test
    public void TestCalculateClosestBoxIs79_WhenShipOutsideOfBoundOnRightSide() {

        float[][] smallBoxCoordinates = setupBoard();

        for(Ship ship : shipArray) {
            //                              +80 because of each box width being ~69 pixels
            //this depends on the screen size, this test assumes 1920 pixels wide (which most screens are)
            ship.setmBound(smallBoxCoordinates[79][0] + 80, smallBoxCoordinates[79][1] + 5, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);
        }

        //assert for each ship the closest box is box 62
        assertTrue(calculateClosestBox() == 79);

        //assert that the closest is not 1 and 2 blocks either side
        assertFalse(calculateClosestBox() == 59);
        assertFalse(calculateClosestBox() == 69);
        assertFalse(calculateClosestBox() == 89);
        assertFalse(calculateClosestBox() == 99);

    }
    @Test
    public void TestCalculateClosestBoxIs79_WhenShipOutsideOfBoundOnLeftSide() {

        float[][] smallBoxCoordinates = setupBoard();

        for(Ship ship : shipArray) {
            //                              +80 because of each box width being ~69 pixels
            //this depends on the screen size, this test assumes 1920 pixels wide (which most screens are)
            ship.setmBound(smallBoxCoordinates[20][0] - 80, smallBoxCoordinates[20][1] + 5, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);
        }

        //assert for each ship the closest box is box 62
        assertTrue(calculateClosestBox() == 20);

        //assert that the closest is not 1 and 2 blocks either side
        assertFalse(calculateClosestBox() == 0);
        assertFalse(calculateClosestBox() == 10);
        assertFalse(calculateClosestBox() == 30);
        assertFalse(calculateClosestBox() == 40);

    }

    public float[][] setupBoard(){
        float bigBoxLeftCoor = 1920/14f;            //these are the variables for the big outer box, enclosing the smaller boxes, for each grid (left and right)
        float bigBoxTopCoor = 1080/5f;
        float bigBoxRightCoor = (1920/14f)*6f;
        float bigBoxBottomCoor = (1080/5f)*4.5f;

        float[][] smallBoxCoordinates = new float[100][5];

        float smallBoxWidth = (bigBoxRightCoor - bigBoxLeftCoor)/10f;     //this calculates the size of each small box.
        float smallBoxHeight = (bigBoxBottomCoor - bigBoxTopCoor)/10f;    //each box is a 10th of the bigger box

        //Step 2 - draw lots of smaller squares
        int numberOfSmallBoxesDrawn =-1;
        boolean smallboxCoordinatesCaptured = false;

        //calculates each smaller box and stores their coordinates in a 2d array
        for(int rows =0; rows<10; rows++) {

            //move constant defined to MOVE EACH BOX TO THE RIGHT. Called moveConstLeft as it moves the left hand coordinate of each box.
            float moveConstLeft = 0;
            for (int column = 0; column < 10; column++) {
                numberOfSmallBoxesDrawn++;

                //store all of the small box coordinates in a 2d array
                if(!smallboxCoordinatesCaptured ) {
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][0] = bigBoxLeftCoor + moveConstLeft;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][1] = bigBoxTopCoor;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][2] = bigBoxLeftCoor + smallBoxWidth + moveConstLeft;
                    smallBoxCoordinates[numberOfSmallBoxesDrawn][3] = bigBoxTopCoor + smallBoxHeight;

                }

                //move the box to the right
                moveConstLeft += smallBoxWidth;
            }

            //after first row is drawn, move down a row and draw 10 more boxes.
            //these two lines move the boxes down a row
            bigBoxTopCoor+=smallBoxHeight;
            bigBoxBottomCoor+=smallBoxHeight;

        }
//        //resetting the bounds
//        smallboxCoordinatesCaptured = true;
//        bigBoxLeftCoor = bigBoxLeftCoor;
//        bigBoxTopCoor = bigBoxTopCoor;
//        bigBoxRightCoor = bigBoxRightCoor;
//        bigBoxBottomCoor = bigBoxBottomCoor;

        return smallBoxCoordinates;
    }

    public void shipSnapToBox(int numberOfBoxToBeTested){

        calculateClosestBox(); //knows closest = 4
        float[][] smallBoxCoordinates = setupBoard();

        for(Ship ship: shipArray) {
            ship.setmBound(smallBoxCoordinates[numberOfBoxToBeTested][0],smallBoxCoordinates[numberOfBoxToBeTested][1],
                    (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);
            }
    }

    @Test
    public void shipSnapToBox4(){

        float[][] smallBoxCoordinates = setupBoard();

        for(Ship ship : shipArray) {
            ship.setmBound(smallBoxCoordinates[4][0] + 5, smallBoxCoordinates[4][1] + 5, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);
        }

        shipSnapToBox(4);

        for(Ship ship: shipArray) {

            assertTrue(calculateClosestBox() == 4);
            assertEquals(ship.getmBound().x, smallBoxCoordinates[4][0]);
            assertEquals(ship.getmBound().y, smallBoxCoordinates[4][1]);
        }

    }

    @Test
    public void shipSnapToBox13(){
        float[][] smallBoxCoordinates = setupBoard();

        for(Ship ship : shipArray) {
            ship.setmBound(smallBoxCoordinates[4][0] + 5, smallBoxCoordinates[4][1] + 5, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);
        }

        shipSnapToBox(4);

        for(Ship ship: shipArray) {

            assertTrue(calculateClosestBox() == 4);
            assertEquals(ship.getmBound().x, smallBoxCoordinates[4][0]);
            assertEquals(ship.getmBound().y, smallBoxCoordinates[4][1]);
        }
    }

    public boolean checkIfBoxOccupied_ValidData(int boxToBeTested){

        boolean occupied = false;
        float[][] smallBoxCoordinates = setupBoard();
        smallBoxCoordinates[boxToBeTested][4] = 1;

        for(Ship ship : shipArray){
            //set each ships coordinates to slightly inside the boxToBeTested
            ship.setmBound(smallBoxCoordinates[boxToBeTested][0] + 5, smallBoxCoordinates[boxToBeTested][1] + 5, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);

            //if its inside this box, which it will be in these tests
            if (ship.getmBound().x > smallBoxCoordinates[boxToBeTested][0] && ship.getmBound().x < smallBoxCoordinates[boxToBeTested][2]
                    && ship.getmBound().y > smallBoxCoordinates[boxToBeTested][1] && ship.getmBound().y < smallBoxCoordinates[boxToBeTested][3]) {

                //check if this box is occupied, if it is return false
                if(smallBoxCoordinates[boxToBeTested][4] == 1){
                    ship.setmBound(30, 40, (100 * ship.getShipLength()) / 2.0f,
                            ((100) / 10f) / 2f);
                     occupied = true;
                }
            }
        }

        return occupied;
    }
    public boolean checkIfBoxOccupied_InvalidData(int boxToBeTested){

        boolean occupied = false;
        float[][] smallBoxCoordinates = setupBoard();
        //smallBoxCoordinates[boxToBeTested][4] = 1; // I only have to comment out this line as to set all the boxes to 0 by default, i.e. not occupied

        for(Ship ship : shipArray){
            //set each ships coordinates to slightly inside the boxToBeTested
            ship.setmBound(smallBoxCoordinates[boxToBeTested][0] + 5, smallBoxCoordinates[boxToBeTested][1] + 5, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);

            //if its inside this box, which it will be in these tests
            if (ship.getmBound().x > smallBoxCoordinates[boxToBeTested][0] && ship.getmBound().x < smallBoxCoordinates[boxToBeTested][2]
                    && ship.getmBound().y > smallBoxCoordinates[boxToBeTested][1] && ship.getmBound().y < smallBoxCoordinates[boxToBeTested][3]) {

                //check if this box is occupied, if it is return false
                if(smallBoxCoordinates[boxToBeTested][4] == 1){
                    ship.setmBound(30, 40, (100 * ship.getShipLength()) / 2.0f,
                            ((100) / 10f) / 2f);
                    occupied = true;
                }
            }
        }

        return occupied;
    }

    @Test
    public void TestCheckIfBoxOccupiedAndMoveShip_ValidData1(){
        assertTrue(checkIfBoxOccupied_ValidData(11));
    }

    @Test
    public void TestCheckIfBoxOccupiedAndMoveShip_ValidData2(){
        assertTrue(checkIfBoxOccupied_ValidData(45));
    }

    @Test
    public void TestCheckIfBoxOccupiedAndMoveShip_InvalidData1(){
        assertFalse(checkIfBoxOccupied_InvalidData(35));
    }

    @Test
    public void TestCheckIfBoxOccupiedAndMoveShip_InvalidData2(){
        assertFalse(checkIfBoxOccupied_InvalidData(17));
    }

}
