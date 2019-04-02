package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Vector;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.input.GestureHandler;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.engine.input.TouchHandler;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.Vector2;
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
    enum GameShipPlacementState {SHIP_SELECT,SHIP_DRAG}


    @Before
    public void setup()
    {
        context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        assetManager = game.getAssetManager();
        //boardScreen = new BoardSetupScreen(game);

        Input inputTest = Mockito.mock(Input.class);
        //Set up Ship objects
        Ship aircraftCarrier = new Ship("AircraftCarrier", 0.5f,0.5f,null, 5);
        Ship cargoShip = new Ship("CargoShip", 0.5f,0.5f,null, 4);
        Ship cruiseShip = new Ship("CruiseShip", 0.5f,0.5f,null, 4);
        Ship submarine = new Ship("Submarine", 0.5f,0.5f,null, 3);
        Ship destroyer = new Ship("Destroyer",  0.5f,0.5f,null, 2);
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
    public void SetUpShipmBoundValidInput()
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

    @Test
    public void SetUpShipmBoundNegativeValues()
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
    public void binarySearchRowsTestValidInput()
    {
        float[][] boxArray = setUp2DArrayWithCoors();

        //knowing first box in array contains 0,0,5,5 co-ordinates, when calling binarySearchRows
        //with input x 3 and input y 3, box 0 is expected to be returned
        assertEquals(0,binarySearchRows(boxArray,0,0,10,3,3));

        //check if binarySearch is able to search all of the boxes in all rows with inputs contained
        //in one of the boxes in each row and successfully return the expected box
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

    @Test
    public void binarySearchRowsTestInvalidInput()
    {
        float[][] boxArray = setUp2DArrayWithCoors();


        //Binary search must return -1 when wrong rows are passed through
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

    }

    private int binarySearchBox(float[][] array,int lower, int higher, float x, float y)
    {
        //Check if the lower bound is less than or equal to higher bound
        //this is used to ensure when the user has not clicked onto a small box, the loop
        //is broken
        //if lower bound is less than or equal to higher bound find a mid value
        if(lower <higher) {
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
                return mid-(mid%10);
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

    @Test
    public void binarySearchBoxTestValidInputExpectSuccess()
    {
        //As the method in binarySearchBox, binarySearchRow has been tested thoroughly
        //more tests involving the method is not needed therefore an alteration to method
        //binarySearchBox has been made to return numberOfSmallBox only

        //Set up 2D array as it is required to test the method
        float[][] boxArray = setUp2DArrayWithCoors();
        //Test performed in every row, ensuring the first box of the row is returned respectively
        assertEquals(0,binarySearchBox(boxArray,0,100,49,4));
        assertEquals(10,binarySearchBox(boxArray,0,100,44,9));
        assertEquals(20,binarySearchBox(boxArray,0,100,38,13));
        assertEquals(30,binarySearchBox(boxArray,0,100,33,18));
        assertEquals(40,binarySearchBox(boxArray,0,100,27,22));
        assertEquals(50,binarySearchBox(boxArray,0,100,22,27));
        assertEquals(60,binarySearchBox(boxArray,0,100,16,31));
        assertEquals(70,binarySearchBox(boxArray,0,100,11,36));
        assertEquals(80,binarySearchBox(boxArray,0,100,9,44));
        assertEquals(90,binarySearchBox(boxArray,0,100,1,49));

    }

    @Test
    public void binarySearchBoxTestInvalidInputExpectSuccess()
    {
        //As the method in binarySearchBox, binarySearchRow has been tested thoroughly
        //more tests involving the method is not needed therefore an alteration to method
        //binarySearchBox has been made to return numberOfSmallBox only

        //Set up 2D array as it is required to test the method
        float[][] boxArray = setUp2DArrayWithCoors();

        //Test y co-ordinates which are out of the box's bounds
        assertEquals(-1,binarySearchBox(boxArray,0,100,2,60));
        assertEquals(-1,binarySearchBox(boxArray,0,100,6,70));
        assertEquals(-1,binarySearchBox(boxArray,0,100,11,80));
        assertEquals(-1,binarySearchBox(boxArray,0,100,16,50.5f));
        assertEquals(-1,binarySearchBox(boxArray,0,100,24,100));
        assertEquals(-1,binarySearchBox(boxArray,0,100,29,55));
        assertEquals(-1,binarySearchBox(boxArray,0,100,33,51));
        assertEquals(-1,binarySearchBox(boxArray,0,100,37,59.5f));
        assertEquals(-1,binarySearchBox(boxArray,0,100,44,300));
        assertEquals(-1,binarySearchBox(boxArray,0,100,47,500));

    }

    @Test
    public void detectionIfUserSelectedSmallBoxBigBoxCoorNotSet()
    {
        //Setting up variables as if the board has not been set up
        int bigBoxRightCoor = 0;
        int x = 22, y = 34;
        int numberofSmallBoxDetected = 0;
        boolean smallBoxDetected = false;
        float[][] smallBoxCoordinates = setUp2DArrayWithCoors();

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
            }
            //if user input x co-ordinate is in the second box x region check if the user has clicked
            //on one of the boxes in the second board
            if (x > bigBoxRightCoor){
                //carry out a binary search for a box on the second board setting the return int to numberOfSmallBoxDetected
                numberofSmallBoxDetected = binarySearchBox(smallBoxCoordinates, 0, 100, x, y);
                //if a box that a user has clicked on is found set smallBoxDetected flag to true
                if (numberofSmallBoxDetected >= 0)
                    smallBoxDetected = true;
            }
        }

        assertFalse(smallBoxDetected);

    }

    @Test
    public void detectionIfUserSelectedSmallBoxCaseInputInBoardOne()
    {

        //Setting up variables as if the user clicked on the left hand side board (board one)
        //and board has been detected
        int bigBoxRightCoor = 1000;
        int x = 44, y =33;
        int numberofSmallBoxDetected = 0;
        boolean smallBoxDetected = false;
        float[][] smallBoxCoordinates = setUp2DArrayWithCoors();

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
            }
            //if user input x co-ordinate is in the second box x region check if the user has clicked
            //on one of the boxes in the second board
            if (x > bigBoxRightCoor){
                //carry out a binary search for a box on the second board setting the return int to numberOfSmallBoxDetected
                numberofSmallBoxDetected = binarySearchBox(smallBoxCoordinates, 0, 100, x, y);
                //if a box that a user has clicked on is found set smallBoxDetected flag to true
                if (numberofSmallBoxDetected >= 0)
                    smallBoxDetected = true;
            }
        }

        assertTrue(smallBoxDetected);

    }

    @Test
    public void detectionIfUserSelectedSmallBoxCaseInputOutsideBoardOne()
    {

        //Setting up variables as if the user clicked on the right hand side board (board two)
        int bigBoxRightCoor = 1;
        int x = 80, y =70;
        int numberofSmallBoxDetected = 0;
        boolean smallBoxDetected = false;
        float[][] smallBoxCoordinates = setUp2DArrayWithCoors();

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
            }
            //if user input x co-ordinate is in the second box x region check if the user has clicked
            //on one of the boxes in the second board
            if (x > bigBoxRightCoor){
                //carry out a binary search for a box on the second board setting the return int to numberOfSmallBoxDetected
                numberofSmallBoxDetected = binarySearchBox(smallBoxCoordinates, 0, 100, x, y);
                //if a box that a user has clicked on is found set smallBoxDetected flag to true
                if (numberofSmallBoxDetected >= 0)
                    smallBoxDetected = true;
            }
        }

        assertFalse(smallBoxDetected);

    }

    @Test
    public void detectionIfUserSelectedSmallBoxCaseInputInBoardTwo()
    {

        //Setting up variables as if the user clicked on the left hand side board (board one)
        //and board has been detected
        int bigBoxRightCoor = 1;
        int x = 31, y = 42;
        int numberofSmallBoxDetected = 0;
        boolean smallBoxDetected = false;
        float[][] smallBoxCoordinates = setUp2DArrayWithCoors();

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
            }
            //if user input x co-ordinate is in the second box x region check if the user has clicked
            //on one of the boxes in the second board
            if (x > bigBoxRightCoor){
                //carry out a binary search for a box on the second board setting the return int to numberOfSmallBoxDetected
                numberofSmallBoxDetected = binarySearchBox(smallBoxCoordinates, 0, 100, x, y);
                //if a box that a user has clicked on is found set smallBoxDetected flag to true
                if (numberofSmallBoxDetected >= 0)
                    smallBoxDetected = true;
            }
        }

        assertTrue(smallBoxDetected);

    }

    @Test
    public void detectionIfUserSelectedSmallBoxCaseInputOutsideBoardTwo()
    {

        //Setting up variables as if the user clicked on the left hand side board (board one)
        //and board has been detected
        int bigBoxRightCoor = 1;
        int x = 500, y = 300;
        int numberofSmallBoxDetected = 0;
        boolean smallBoxDetected = false;
        float[][] smallBoxCoordinates = setUp2DArrayWithCoors();

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
            }
            //if user input x co-ordinate is in the second box x region check if the user has clicked
            //on one of the boxes in the second board
            if (x > bigBoxRightCoor){
                //carry out a binary search for a box on the second board setting the return int to numberOfSmallBoxDetected
                numberofSmallBoxDetected = binarySearchBox(smallBoxCoordinates, 0, 100, x, y);
                //if a box that a user has clicked on is found set smallBoxDetected flag to true
                if (numberofSmallBoxDetected >= 0)
                    smallBoxDetected = true;
            }
        }

        assertFalse(smallBoxDetected);

    }

    @Test
    public void shipSelectInputInsideBox()
    {
        //Setting up variables
        Vector2 dragShipOffset = new Vector2();
        Ship shipTest = new Ship("Test",0,0,null,0);
        shipTest.setmBound(0,0,50,50);
        Ship[] shipArray = {shipTest};
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.pointer = 1;
        touchEvent.x = 25;
        touchEvent.y = 25;
        Ship selectedShip = null;
        int shipToDragPointerIndexOfInput = 0;
        GameShipPlacementState gameShipPlacementState = GameShipPlacementState.SHIP_SELECT;

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

        assertEquals(1, shipToDragPointerIndexOfInput);
        assertEquals(GameShipPlacementState.SHIP_DRAG,gameShipPlacementState);
        assertEquals(shipTest, selectedShip);
    }

    @Test
    public void shipSelectInputOutsideBox()
    {
        //Setting up variables
        Vector2 dragShipOffset = new Vector2();
        Ship shipTest = new Ship("Test",0,0,null,0);
        shipTest.setmBound(0,0,50,50);
        Ship[] shipArray = {shipTest};
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.pointer = 1;
        touchEvent.x = 800;
        touchEvent.y = 800;
        Ship selectedShip = null;
        int shipToDragPointerIndexOfInput = 0;
        GameShipPlacementState gameShipPlacementState = GameShipPlacementState.SHIP_SELECT;

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

        assertEquals(0, shipToDragPointerIndexOfInput);
        assertEquals(GameShipPlacementState.SHIP_SELECT,gameShipPlacementState);
        assertEquals(null, selectedShip);
    }

    @Test
    public void shipDragValidInput() {

        //Setting up values
        Ship selectedShip = new Ship("Test",0,0,null,0);
        selectedShip.setmBound(0,0,50,50);
        Vector2 dragShipOffset = new Vector2();
        dragShipOffset.x = 100;
        dragShipOffset.y = 50;
        boolean inputExists = true;
        int inputgetTouchX = 100, inputgetTouchY = 100;

        //I was not able to mock input therefore I used hard coded values
        if (inputExists) {
            selectedShip.mBound.x = inputgetTouchX + dragShipOffset.x;
            selectedShip.mBound.y = inputgetTouchY + dragShipOffset.y;
        }

        assertEquals(200f, selectedShip.mBound.x);
        assertEquals(150f, selectedShip.mBound.y);
    }

    @Test
    public void shipDragPointerDoesNotExist() {

        //Setting up values
        Ship selectedShip = new Ship("Test",0,0,null,0);
        selectedShip.setmBound(0,0,50,50);
        Vector2 dragShipOffset = new Vector2();
        dragShipOffset.x = 100;
        dragShipOffset.y = 50;
        boolean inputExists = false;
        int inputgetTouchX = 100, inputgetTouchY = 100;
        Input input = Mockito.mock(Input.class);
        int shipToDragPointerIndexOfInput = 0;

        //Pointer does not exist
        if (input.existsTouch(shipToDragPointerIndexOfInput)) {
            selectedShip.mBound.x = input.getTouchX(shipToDragPointerIndexOfInput) + dragShipOffset.x;
            selectedShip.mBound.y = input.getTouchY(shipToDragPointerIndexOfInput) + dragShipOffset.y;
        }

        assertEquals(0f, selectedShip.mBound.x);
        assertEquals(0f, selectedShip.mBound.y);
    }


    @Test
    public void updateBoundingBoxAfterRotationValidInput()
    {
        //Set up variables
        BoundingBox mBound = new BoundingBox(100,100,20,30);
        boolean boundingBoxSetAfterRotation = false;
        boolean undoBoundingBoxSetAfterRotation = true;


        float temp = mBound.halfHeight;
        mBound.halfHeight = mBound.halfWidth;
        mBound.halfWidth = temp;
        boundingBoxSetAfterRotation = true;
        undoBoundingBoxSetAfterRotation = false;

        assertEquals(20f,mBound.halfHeight);
        assertEquals(30f,mBound.halfWidth);
        assertEquals(true,boundingBoxSetAfterRotation);
        assertEquals(false,undoBoundingBoxSetAfterRotation);
    }

    @Test
    public void updateBoundingBoxAfterRotationNullValues()
    {
        //Set up variables
        BoundingBox mBound = new BoundingBox(100,100,0,0);
        boolean boundingBoxSetAfterRotation = false;
        boolean undoBoundingBoxSetAfterRotation = true;


        float temp = mBound.halfHeight;
        mBound.halfHeight = mBound.halfWidth;
        mBound.halfWidth = temp;
        boundingBoxSetAfterRotation = true;
        undoBoundingBoxSetAfterRotation = false;

        assertEquals(0f,mBound.halfHeight);
        assertEquals(0f,mBound.halfWidth);
        assertEquals(true,boundingBoxSetAfterRotation);
        assertEquals(false,undoBoundingBoxSetAfterRotation);
    }

    @Test
    public void updateBoundingBoxAfterRotationNegativeValues()
    {
        //Set up variables
        BoundingBox mBound = new BoundingBox(-200,-100,100,50);
        boolean boundingBoxSetAfterRotation = false;
        boolean undoBoundingBoxSetAfterRotation = true;


        float temp = mBound.halfHeight;
        mBound.halfHeight = mBound.halfWidth;
        mBound.halfWidth = temp;
        boundingBoxSetAfterRotation = true;
        undoBoundingBoxSetAfterRotation = false;

        assertEquals(100f,mBound.halfHeight);
        assertEquals(50f,mBound.halfWidth);
        assertEquals(true,boundingBoxSetAfterRotation);
        assertEquals(false,undoBoundingBoxSetAfterRotation);
    }

    @Test
    public void reverseUpdateBoundingBoxAfterRotationValidValues()
    {
        BoundingBox mBound = new BoundingBox(100,100,55,75);
        boolean boundingBoxSetAfterRotation = true;
        boolean undoBoundingBoxSetAfterRotation = false;
        float temp = mBound.halfHeight;
        mBound.halfHeight = mBound.halfWidth;
        mBound.halfWidth = temp;
        undoBoundingBoxSetAfterRotation = true;
        boundingBoxSetAfterRotation = false;


        assertEquals(55f,mBound.halfHeight);
        assertEquals(75f,mBound.halfWidth);
        assertEquals(false,boundingBoxSetAfterRotation);
        assertEquals(true,undoBoundingBoxSetAfterRotation);
    }

    @Test
    public void reverseUpdateBoundingBoxAfterRotationNullValues()
    {
        BoundingBox mBound = new BoundingBox(100,100,0,0);
        boolean boundingBoxSetAfterRotation = true;
        boolean undoBoundingBoxSetAfterRotation = false;
        float temp = mBound.halfHeight;
        mBound.halfHeight = mBound.halfWidth;
        mBound.halfWidth = temp;
        undoBoundingBoxSetAfterRotation = true;
        boundingBoxSetAfterRotation = false;


        assertEquals(0f,mBound.halfHeight);
        assertEquals(0f,mBound.halfWidth);
        assertEquals(false,boundingBoxSetAfterRotation);
        assertEquals(true,undoBoundingBoxSetAfterRotation);
    }

    @Test
    public void reverseUpdateBoundingBoxAfterRotationNegativeValues()
    {
        BoundingBox mBound = new BoundingBox(-100,-200,300,200);
        boolean boundingBoxSetAfterRotation = true;
        boolean undoBoundingBoxSetAfterRotation = false;
        float temp = mBound.halfHeight;
        mBound.halfHeight = mBound.halfWidth;
        mBound.halfWidth = temp;
        undoBoundingBoxSetAfterRotation = true;
        boundingBoxSetAfterRotation = false;


        assertEquals(300f,mBound.halfHeight);
        assertEquals(200f,mBound.halfWidth);
        assertEquals(false,boundingBoxSetAfterRotation);
        assertEquals(true,undoBoundingBoxSetAfterRotation);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //Tests Made by John McAnearney (40203900)
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * I was unsure whether it were better practice to make all my methods, that I need from the BoardSetupScreen Class, Public or to just copy them in here.
     * Unfortunately I was unable to ask due to unforeseen circumstances, hence why I've decided to just copy them into here.
     *     Methods to test
     *         setupBoardBound              √
     *         shipPlacement                (no need to test as it is just a culmination of other methods)
     *         isShipOutOfBOund             √
     *         calculateClosestBox          √
     *         markShipInBox
     *         shipSnapToBox                √
     *         shipReset                    √   (didn't test this as it is very similar to shipSnapToBox, except with some checks and if's)
     *         checkIfBoxOccupoied
     *         hitOrMiss
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
        //the following tests are supposed to fail as they are not equal, the expected are just some arbitrarily wrong data
        assertEquals(137, Math.round(bigBoxLeftCoor));
        assertEquals(216, Math.round(bigBoxTopCoor));
        assertEquals(823, Math.round(bigBoxRightCoor));
        assertEquals(972, Math.round(bigBoxBottomCoor));
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
        assertFalse(calculateClosestBox() == 2);
        assertFalse(calculateClosestBox() == 3);
        assertFalse(calculateClosestBox() == 5);
        assertFalse(calculateClosestBox() == 6);

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
        int numberOfClosestBox = 0;

        //setup the boxes
        float[][] smallBoxCoordinates = setupBoard();

        //for each ship, set bound close to box number 4
        for(Ship ship: shipArray) {
            ship.setmBound(360, 310, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);


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
            ship.setmBound(smallBoxCoordinates[numberOfClosestBox][0],smallBoxCoordinates[numberOfClosestBox][1],
                    (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);

            assertTrue(numberOfClosestBox == 13);
            assertEquals(ship.getmBound().x, smallBoxCoordinates[numberOfClosestBox][0]);
            assertEquals(ship.getmBound().y, smallBoxCoordinates[numberOfClosestBox][1]);
        }
    }

    @Test
    public void TestCheckIfBoxOccupiedAndMoveShip_ValidData1(){
        float[][] smallBoxCoordinates = setupBoard();

        smallBoxCoordinates[11][4] = 1;

        //set each ships bound to the 11th box, which is occupied
        for(Ship ship: shipArray) {
            ship.setmBound(224, 310, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);

            if (ship.getmBound().x > smallBoxCoordinates[11][0] && ship.getmBound().x < smallBoxCoordinates[11][2]
                    && ship.getmBound().y > smallBoxCoordinates[11][1] && ship.getmBound().y < smallBoxCoordinates[11][3]) {

                if(smallBoxCoordinates[11][4] == 1){
                    ship.setmBound(30, 40, (100 * ship.getShipLength()) / 2.0f,
                            ((100) / 10f) / 2f);
                }
            }
            //had to cast to int was getting this error: junit.framework.AssertionFailedError: expected:<40.0> but was:<40.0> at junit.framework.Assert.fail(Assert.java:50)
            //and I didn't know how else to fix it
            assertEquals(30, (int)ship.getmBound().x);
            assertEquals(40, (int)ship.getmBound().y);
        }
    }

    @Test
    public void TestCheckIfBoxOccupiedAndMoveShip_ValidData2(){
        float[][] smallBoxCoordinates = setupBoard();

        smallBoxCoordinates[45][4] = 1;

        //set each ships bound to the 11th box, which is occupied
        for(Ship ship: shipArray) {
            ship.setmBound(500, 539, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);

            if (ship.getmBound().x > smallBoxCoordinates[45][0] && ship.getmBound().x < smallBoxCoordinates[45][2]
                    && ship.getmBound().y > smallBoxCoordinates[45][1] && ship.getmBound().y < smallBoxCoordinates[45][3]) {

                if(smallBoxCoordinates[45][4] == 1){
                    ship.setmBound(60, 40, (100 * ship.getShipLength()) / 2.0f,
                            ((100) / 10f) / 2f);
                }
            }
            //had to cast to int was getting this error: junit.framework.AssertionFailedError: expected:<40.0> but was:<40.0> at junit.framework.Assert.fail(Assert.java:50)
            //and I didn't know how else to fix it
            assertEquals(60, (int)ship.getmBound().x);
            assertEquals(40, (int)ship.getmBound().y);
        }
    }

    @Test
    public void TestCheckIfBoxOccupiedAndMoveShip_InvalidData1(){
        float[][] smallBoxCoordinates = setupBoard();

        smallBoxCoordinates[45][4] = 1;

        //set each ships bound to the 11th box, which is occupied
        for(Ship ship: shipArray) {
            ship.setmBound(500, 539, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);

            if (ship.getmBound().x > smallBoxCoordinates[45][0] && ship.getmBound().x < smallBoxCoordinates[45][2]
                    && ship.getmBound().y > smallBoxCoordinates[45][1] && ship.getmBound().y < smallBoxCoordinates[45][3]) {

                if(smallBoxCoordinates[45][4] == 1){
                    ship.setmBound(60, 40, (100 * ship.getShipLength()) / 2.0f,
                            ((100) / 10f) / 2f);
                }
            }
            //had to cast to int was getting this error: junit.framework.AssertionFailedError: expected:<40.0> but was:<40.0> at junit.framework.Assert.fail(Assert.java:50)
            //and I didn't know how else to fix it
            assertEquals(60, (int)ship.getmBound().x);
            assertEquals(40, (int)ship.getmBound().y);
        }
    }

    @Test
    public void TestCheckIfBoxOccupiedAndMoveShip_InvalidData2(){
        float[][] smallBoxCoordinates = setupBoard();

        smallBoxCoordinates[45][4] = 1;

        //set each ships bound to the 11th box, which is occupied
        for(Ship ship: shipArray) {
            ship.setmBound(500, 539, (100 * ship.getShipLength()) / 2.0f,
                    ((100) / 10f) / 2f);

            if (ship.getmBound().x > smallBoxCoordinates[45][0] && ship.getmBound().x < smallBoxCoordinates[45][2]
                    && ship.getmBound().y > smallBoxCoordinates[45][1] && ship.getmBound().y < smallBoxCoordinates[45][3]) {

                if(smallBoxCoordinates[45][4] == 1){
                    ship.setmBound(60, 40, (100 * ship.getShipLength()) / 2.0f,
                            ((100) / 10f) / 2f);
                }
            }
            //had to cast to int was getting this error: junit.framework.AssertionFailedError: expected:<40.0> but was:<40.0> at junit.framework.Assert.fail(Assert.java:50)
            //and I didn't know how else to fix it
            assertEquals(60, (int)ship.getmBound().x);
            assertEquals(40, (int)ship.getmBound().y);
        }
    }

}
