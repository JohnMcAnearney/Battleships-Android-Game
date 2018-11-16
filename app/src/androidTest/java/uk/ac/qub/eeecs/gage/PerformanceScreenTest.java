package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.game.DemoGame;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PerformanceScreenTest {

    private Context context;
    private DemoGame game;
    AssetManager assetManager;
    @Before
    public void setUp(){
        context = InstrumentationRegistry.getTargetContext();
        setupGameManager();
    }

    private void setupGameManager() {
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        assetManager = new AssetManager(game);
        game.mAssetManager = new AssetManager(game);
        game.mAssetManager=assetManager;
        game.mScreenManager= new ScreenManager(game);
    }


    @Test
    public void loadandAddBitmapRectangle_TestisSuccessfull()
    {
        boolean success = assetManager.loadAndAddBitmap("rectangle","img/rectangle.jpg");

        assertTrue(success);
    }


//    @Test
//    public void checkIfDrawnRectanglesArenotOutOfBounds()
//    {
//
//
//    }
}
