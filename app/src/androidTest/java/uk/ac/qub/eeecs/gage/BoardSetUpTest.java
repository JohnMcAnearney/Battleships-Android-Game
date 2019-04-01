package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.runner.AndroidJUnit4;

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
import uk.ac.qub.eeecs.game.BattleShips.Ship;
import uk.ac.qub.eeecs.game.DemoGame;

@RunWith(MockitoJUnitRunner.class)
public class BoardSetUpTest {

    private Game game;
    private GameScreen gameScreen;
    private AssetManager assetManager;
    private Context context;
    private BoundingBox aBoundingBox;
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
        float[][] boxArray = new float[100][4];
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
    public void binarySearchBoxTest()
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

        //Test y co-ordinates which are out of the box's bounds, box y bounds are 0 to 50
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
    public void detectionIfUserSelectedSmallBox()
    {

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tests Made by John McAnearney (40203900)
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void boardSetupTest(){
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
}
