package uk.ac.qub.eeecs.gage;

import android.graphics.Bitmap;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BattleShips.Ship;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardSetUpTest {

    @Mock private Game game;
    @Mock private GameScreen gameScreen;
    @Mock private AssetManager assetManager;
    @Mock private Bitmap bitmap;

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
        assertTrue(assetManager.loadAndAddBitmap("PlayButton", "img/AcceptButton.png"));
        assertTrue(assetManager.loadAndAddBitmap("rotateButton","img/rotateButton.png"));
        assertTrue(assetManager.loadAndAddBitmap("AircraftCarrier", "img/AircraftCarrier.png"));
        assertTrue(assetManager.loadAndAddBitmap("CargoShip", "img/CargoShip.png"));
        assertTrue(assetManager.loadAndAddBitmap("CruiseShip", "img/CruiseShip.png"));
        assertTrue(assetManager.loadAndAddBitmap("Destroyer", "img/Destroyer.png"));
        assertTrue(assetManager.loadAndAddBitmap("Submarine", "img/Submarine.png"));
    }


    @Test
    public void createShipObjects()
    {
        Ship aircraftCarrier = new Ship("AircraftCarrier", 0,0,assetManager.getBitmap("AircraftCarrier"), 5);
        //Ship cargoShip = new Ship("CargoShip", calculateShipRatioX("CargoShip",4),calculateShipRatioY("CargoShip"),assetManager.getBitmap("CargoShip"), 4);
        //Ship cruiseShip = new Ship("CruiseShip", calculateShipRatioX("CruiseShip",4),calculateShipRatioY("CruiseShip"),assetManager.getBitmap("CruiseShip"), 4);
       //Ship submarine = new Ship("Submarine", calculateShipRatioX("Submarine",3),calculateShipRatioY("Submarine"),assetManager.getBitmap("Submarine"), 3);
        //Ship destroyer = new Ship("Destroyer", calculateShipRatioX("Destroyer",2),calculateShipRatioY("Destroyer"),assetManager.getBitmap("Destroyer"), 2);
    }
}
