package uk.ac.qub.eeecs.gage;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.content.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BattleShips.Key;
import uk.ac.qub.eeecs.game.DemoGame;

/**
 * @author Hannah Cunningham (40201925)
 */

@RunWith(AndroidJUnit4.class)
public class KeyTest
{
    private Context context;
    private DemoGame game;
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

    //test method to test constructor in key class
    @Test
    public void keyConstructor()
    {
        Key key = new Key(0.5f, 0.5f, 2.5f, 5.5f, 'a', gameScreen);
        assert (key.getGameScreen().equals("Key"));
        assert (key.getGameScreen().equals(gameScreen));
    }
}

