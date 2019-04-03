package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationSettings;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.LoadingAnimation;
import uk.ac.qub.eeecs.game.DemoGame;

@RunWith(MockitoJUnitRunner.class)
public class LoadingAnimationTest
{
    private Game mGame;
    private AssetManager mAssetManager;
    private Context mContext;

    // Initialising the setup method for the test, which will run @Before
    @Before
    public void setup()
    {
       mGame = new DemoGame();
       mContext = InstrumentationRegistry.getTargetContext();
       mGame.mFileIO = new FileIO(mContext);
       mGame.mAssetManager = new AssetManager(mGame);
       mAssetManager = mGame.getAssetManager();
    }

    @Test
    public void testFrameHeight()
    {
        AnimationSettings animationSettings = new AnimationSettings(mGame.getAssetManager(), "txt/animation/LoadingAnimation.JSON");
        LoadingAnimation loadingAnimation = new LoadingAnimation(animationSettings, 0);

        Assert.assertEquals(230, loadingAnimation.getFrameHeight());
    }

    @Test
    public void testFrameWidth()
    {
        AnimationSettings animationSettings = new AnimationSettings(mGame.getAssetManager(), "txt/animation/LoadingAnimation.JSON");
        LoadingAnimation loadingAnimation = new LoadingAnimation(animationSettings, 0);

        Assert.assertEquals(230, loadingAnimation.getFrameWidth());
    }

    @Test
    public void testStartFrame()
    {
        AnimationSettings animationSettings = new AnimationSettings(mGame.getAssetManager(), "txt/animation/LoadingAnimation.JSON");
        LoadingAnimation loadingAnimation = new LoadingAnimation(animationSettings, 0);

        Assert.assertEquals(0, loadingAnimation.getStartFrame());
    }

    @Test
    public void testEndFrame()
    {
        AnimationSettings animationSettings = new AnimationSettings(mGame.getAssetManager(), "txt/animation/LoadingAnimation.JSON");
        LoadingAnimation loadingAnimation = new LoadingAnimation(animationSettings, 0);

        Assert.assertEquals(23, loadingAnimation.getEndFrame());
    }
}
