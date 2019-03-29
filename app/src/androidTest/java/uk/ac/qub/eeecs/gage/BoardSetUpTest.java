package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BattleShips.Ship;
import uk.ac.qub.eeecs.game.DemoGame;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class BoardSetUpTest {

    private Game game;
    private GameScreen gameScreen;
    private AssetManager assetManager;
    private Context context;
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
        //Set up 2D array
        //offsets for setting co-ordinates
        int offsetLeftX = 0;
        int offsetRightX = 5;
        int offsetTopY = 0;
        int offsetBottomY = 5;
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

        assertEquals(0,binarySearchRows(boxArray,0,0,9,3,3));
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tests Made by JoSh Macaroni (402696969)
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
