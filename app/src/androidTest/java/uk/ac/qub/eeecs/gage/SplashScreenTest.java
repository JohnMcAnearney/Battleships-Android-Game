package uk.ac.qub.eeecs.gage;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.AndroidException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.Assert;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertEquals;
import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BattleShips.MainMenu;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.SplashScreen;

/**
 * @author Hannah Cunningham (40201925)
 */

@RunWith(AndroidJUnit4.class)
public class SplashScreenTest
{
    //setting the test class variables
    private Context context;
    private Game game;
    private GameScreen gameScreen;
    private AssetManager assetManager;

    @Before
    public void setUp()
    {
        //I will initialise the game's content
        context = InstrumentationRegistry.getTargetContext();
        setupGameManager();
    }

    public void setupGameManager()
    {
        //the below will initialise the Game variable with a new DemoGame()
        game = new DemoGame();
        //initialising the FileIO class to aid with functionality in the test class
        game.mFileIO = new FileIO(context);
        //initialising AssetManager class to aid with functionality in the test class
        game.mAssetManager = new AssetManager(game);
        //initialising ScreenManager class to aid with functionality in the test class
        game.mScreenManager = new ScreenManager(game);
    }

    @Test
    public void bitmapsToBeLoaded()
    {
        assertTrue(assetManager.loadAndAddBitmap("splashBackground", "img/splashBackground.png"));
        assertTrue(assetManager.loadAndAddBitmap("symbol", "img/symbol.png"));
    }

    @Test
    public void fontsToBeLoaded()
    {
        assertTrue(assetManager.loadAndAddFont("regularUnderworld", "fonts/underWorld.ttf"));
    }

    @Test
    public void testGoToMainMenuMethod()
    {
      SplashScreen splashScreen = new SplashScreen(game);
      game.getScreenManager().addScreen(splashScreen);
      MainMenu mainMenu = new MainMenu(game);
      game.getScreenManager().addScreen(mainMenu);
      splashScreen.goToMainMenu();
      Assert.assertEquals(game.getScreenManager().getCurrentScreen().getName(), mainMenu.getName());
    }
}























