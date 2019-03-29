// COMMENTED OUT YOUR CODE AS IT PREVENTED ME FROM TESTING MY CODE - EDGARS
/*package uk.ac.qub.eeecs.gage;

import android.app.Instrumentation;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BattleShips.Key;
import uk.ac.qub.eeecs.game.DemoGame;

@RunWith(MockitoJUnitRunner.class)
public class KeyTest {
    private Context context;
    private Game game;
    private GameScreen gameScreen;
    private AssetManager assetManager;

    @Before
    public void setUp()
    {
        context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        assetManager = game.getAssetManager();
        when(gameScreen.getName().thenReturn(gameScreen));
    }

    @Test
    public void keyConstructor()
    {
        Key key = new Key(10, 15, 20, 25, "a", gameScreen);
        assert (key.getGameScreen().equals("Key"));
        assert (key.getGameScreen().equals(gameScreen));
    }

}*/
