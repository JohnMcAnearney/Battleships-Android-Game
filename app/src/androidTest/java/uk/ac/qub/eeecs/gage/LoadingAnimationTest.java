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

/*
 * Author: Edgars(402030154)
 * A test class which will test if the loading animation gets the correct frame height, width and
 * makes sure that the loading animation has an appropriate start and end frame
 */
@RunWith(MockitoJUnitRunner.class)
/*
 * Reference @ Mantas(40203133)
 * Used the 'ExplosionAnimationTest' to help create unit tests for my animation class as I was
 * struggling to get my own unit tests to work
 */
public class LoadingAnimationTest
{
    private Game mGame;
    private Context mContext;

    // Initialising the setup method for the test, which will run @Before
    @Before
    public void setup()
    {
       mGame = new DemoGame();
       mContext = InstrumentationRegistry.getTargetContext();
       mGame.mFileIO = new FileIO(mContext);
       mGame.mAssetManager = new AssetManager(mGame);
    }

    /*
     * Reference @ Mantas(40203133)
     * Used the 'ExplosionAnimationTest' to see what kind of tests I should carry out for my class
     */
    // Test which will make sure the Frame Height is the expected height
    @Test
    public void testFrameHeight()
    {
        // Initialising the Animation Settings and then initialising a new Loading Animation class
        AnimationSettings animationSettings = new AnimationSettings(mGame.getAssetManager(),
                "txt/animation/LoadingAnimation.JSON");
        LoadingAnimation loadingAnimation = new LoadingAnimation(animationSettings, 0);

        Assert.assertEquals(230, loadingAnimation.getFrameHeight());
    }

    // Test which will make sure the Frame Width is the expected width
    @Test
    public void testFrameWidth()
    {
        // Initialising the Animation Settings and then initialising a new Loading Animation class
        AnimationSettings animationSettings = new AnimationSettings(mGame.getAssetManager(),
                "txt/animation/LoadingAnimation.JSON");
        LoadingAnimation loadingAnimation = new LoadingAnimation(animationSettings, 0);

        Assert.assertEquals(230, loadingAnimation.getFrameWidth());
    }

    // Test which will make sure the the start frame is correct
    @Test
    public void testStartFrame()
    {
        // Initialising the Animation Settings and then initialising a new Loading Animation class
        AnimationSettings animationSettings = new AnimationSettings(mGame.getAssetManager(),
                "txt/animation/LoadingAnimation.JSON");
        LoadingAnimation loadingAnimation = new LoadingAnimation(animationSettings, 0);

        Assert.assertEquals(0, loadingAnimation.getStartFrame());
    }

    // Test which will make sure the the end frame is correct
    @Test
    public void testEndFrame()
    {
        // Initialising the Animation Settings and then initialising a new Loading Animation class
        AnimationSettings animationSettings = new AnimationSettings(mGame.getAssetManager(),
                "txt/animation/LoadingAnimation.JSON");
        LoadingAnimation loadingAnimation = new LoadingAnimation(animationSettings, 0);

        Assert.assertEquals(23, loadingAnimation.getEndFrame());
    }
}
