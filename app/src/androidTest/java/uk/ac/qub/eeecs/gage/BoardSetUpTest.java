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
    public void calculateShipRatioX()
    {
        assertTrue(assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png"));
        int shipBitmapHeight = assetManager.getBitmap("AircraftCarrier").getWidth();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tests Made by JoSh Macaroni (402696969)
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
