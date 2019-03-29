package uk.ac.qub.eeecs.gage;

import android.graphics.Bitmap;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
//import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BattleShips.Ship;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith()
public class BoardSetUpTest {

    @Mock private Game game;
    @Mock private GameScreen gameScreen;
    @Mock private AssetManager assetManager;
   // @Mock private Bitmap bitmap;
    @Mock private IGraphics2D graphics2D;

    @Before
    public void setup()
    {
        when(gameScreen.getGame()).thenReturn(game);
        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(any(String.class))).thenReturn(bitmap);

    }

    @Test
    public void required_Bitmaps_Loaded()
    {
//        assertTrue(assetManager.loadAndAddBitmap("PlayButton", "img/AcceptButton.png"));
//        assertTrue(assetManager.loadAndAddBitmap("rotateButton","img/rotateButton.png"));
//        assertTrue(assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png"));
//        assertTrue(assetManager.loadAndAddBitmap("CargoShip", "img/CargoShip.png"));
//        assertTrue(assetManager.loadAndAddBitmap("CruiseShip", "img/CruiseShip.png"));
//        assertTrue(assetManager.loadAndAddBitmap("Destroyer", "img/Destroyer.png"));
//        assertTrue(assetManager.loadAndAddBitmap("Submarine", "img/Submarine.png"));
    }


    @Test
    public void createShipObjects()
    {
        //Ship aircraftCarrier = new Ship("AircraftCarrier", 0,0,assetManager.getBitmap("AircraftCarrier"), 5);
        //Ship cargoShip = new Ship("CargoShip", calculateShipRatioX("CargoShip",4),calculateShipRatioY("CargoShip"),assetManager.getBitmap("CargoShip"), 4);
        //Ship cruiseShip = new Ship("CruiseShip", calculateShipRatioX("CruiseShip",4),calculateShipRatioY("CruiseShip"),assetManager.getBitmap("CruiseShip"), 4);
       //Ship submarine = new Ship("Submarine", calculateShipRatioX("Submarine",3),calculateShipRatioY("Submarine"),assetManager.getBitmap("Submarine"), 3);
        //Ship destroyer = new Ship("Destroyer", calculateShipRatioX("Destroyer",2),calculateShipRatioY("Destroyer"),assetManager.getBitmap("Destroyer"), 2);
    }

    @Test
    public void boardSetupTest(){
//        float screenWidth = graphics2D.getSurfaceWidth();
//        float screenHeight = graphics2D.getSurfaceHeight();
//        float bigBoxLeftCoor = screenWidth/14f;
//        float bigBoxTopCoor = screenHeight/5f;            //resetting the bounds to the first boxes' parameters as to ensure rest of methods work.
//        float bigBoxRightCoor = (screenWidth/14f)*6f;     //this is done as we are only ever checking input and variation in the first board as the second board is
//        float bigBoxBottomCoor = (screenHeight/5f)*4.5f;
//        BoundingBox boardBoundingBox = new BoundingBox((bigBoxLeftCoor + bigBoxRightCoor)/2,
//                (bigBoxBottomCoor + bigBoxTopCoor)/2,
//                ((bigBoxLeftCoor + bigBoxRightCoor)/2)-bigBoxLeftCoor,
//                ((bigBoxBottomCoor + bigBoxTopCoor)/2)-bigBoxTopCoor);

        BoundingBox boardBoundingBox = new BoundingBox();

        assertEquals(aBoundingBox, boardBoundingBox);
    }
}
