package uk.ac.qub.eeecs.gage;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.AndroidException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertEquals;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BattleShips.MainMenu;
import uk.ac.qub.eeecs.game.DemoGame;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.SplashScreen;

@RunWith(AndroidJUnit4.class)
public class SplashScreenTest
{
    //setting the test class variables
    private Context context;
    private Game game;
    private GameScreen gameScreen;
    private AssetManager assetManager;
    private Bitmap symbolBitmap;

    @Before
    public void setup()
    {
        //I will first initialise the game's content
        context = InstrumentationRegistry.getTargetContext();
        //the below will initialise the Game variable with a new DemoGame()
        game = new DemoGame();
        //initialising the FileIO class to aid with functionality in the test class
        game.mFileIO = new FileIO(context);
        //initialising AssetManager class to aid with functionality in the test class
        game.mAssetManager = new AssetManager(game);
        assetManager = game.getAssetManager();
    }

    @Test
    public void bitmaps_to_be_loaded()
    {
        assertTrue(assetManager.loadAndAddBitmap("splashBackground", "img/splashBackground.png"));
        assertTrue(assetManager.loadAndAddBitmap("symbol", "img/symbol.png"));

    }

    @Test
    public void fonts_to_be_loaded()
    {
        assertTrue(assetManager.loadAndAddFont("regularUnderworld", "fonts/underWorld.ttf"));
    }
  @Test
    public void testGoToMenuScreenMethod() {
      SplashScreen splashScreen = new SplashScreen(game);
      game.getScreenManager().addScreen(splashScreen);

      MainMenu mainMenu = new MainMenu(game);
      game.getScreenManager().addScreen(mainMenu);
      splashScreen.goToMenuScreen();

      Assert.assertEquals(game.getScreenManager().getCurrentScreen().getName(), mainMenu.getName());
  }
}























