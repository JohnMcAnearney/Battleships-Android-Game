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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationSettings;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.BoardSetupScreen;
import uk.ac.qub.eeecs.game.BattleShips.ExplosionAnimation;
import uk.ac.qub.eeecs.game.DemoGame;

@RunWith(MockitoJUnitRunner.class)
public class ExplosionAnimationTest {

    private Game game;
    private AssetManager assetManager;
    private BoardSetupScreen boardScreen;
    private Context context;

    @Before
    public void setup() {

        context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        assetManager = game.getAssetManager();
    }

    @Test
    public void ExplosionAnimationTestName()
    {
        AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
        ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

        assertEquals("Explosion",explosionAnimation.getName());
    }

    @Test
    public void ExplosionAnimationTestSpriteSheet()
    {
        AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
        ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

        Bitmap explosionSheet = assetManager.getBitmap("img/ExplosionSheet.png");
        assertEquals(explosionSheet,explosionAnimation.getSpritesheet());
    }

    @Test
    public void ExplosionAnimationTestFrameHeight()
    {
        AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
        ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

        assertEquals(96,explosionAnimation.getFrameHeight());
    }

    @Test
    public void ExplosionAnimationTestFrameWidth()
    {
        AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
        ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

        assertEquals(100,explosionAnimation.getFrameWidth());
    }

    @Test
    public void ExplosionAnimationTestNumberOfRows()
    {
        AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
        ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

        assertEquals(6,explosionAnimation.getNumRows());
    }

    @Test
    public void ExplosionAnimationTestNumberOfColumns()
    {
        AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
        ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

        assertEquals(6,explosionAnimation.getNumColumns());
    }

    @Test
    public void ExplosionAnimationTestTotalPeriod()
    {
        AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
        ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

        assertEquals(1.5f,explosionAnimation.getTotalPeriod());
    }

    @Test
    public void ExplosionAnimationTestStartFrame()
    {
        AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
        ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

        assertEquals(0,explosionAnimation.getStartFrame());
    }

    @Test
    public void ExplosionAnimationTestLoopAnimation()
    {
            AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
            ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

            assertEquals(0,explosionAnimation.getLoopAnimation());
    }

    @Test
    public void playTestWhileAnimationNotPlaying()
    {
        //Variables passed in as paramters to the method, variable holding realistic values
        ElapsedTime elapsedTime = Mockito.mock(ElapsedTime.class);
        elapsedTime.totalTime = 50;
        float x = 100, y = 100, right = 50, bottom = 50;
        //mocked class objects
        float objectsX = 0, objectsY = 0, objectsRight = 0, objectsBottom = 0;
        boolean isPlaying = false;
        double animationStartTime = 0;
        int startFrame = 0, currentFrame = 4;

        //method
        objectsX = x;
        objectsY = y;
        objectsRight = right;
        objectsBottom = bottom;

        // Only commence playback if the animation is not currently playing
        if(!isPlaying) {
            animationStartTime = elapsedTime.totalTime;
            currentFrame = startFrame;
            isPlaying = true;
        }

        assertTrue(isPlaying);
        assertEquals(100f,objectsX);
        assertEquals(100f,objectsY);
        assertEquals(50f,objectsRight);
        assertEquals(50f,objectsBottom);
        assertEquals(0, currentFrame);
        assertEquals(50.0,animationStartTime);

    }

    @Test
    public void playTestWhileAnimationPlaying()
    {
        //Variables passed in as paramters to the method, variable holding realistic values
        ElapsedTime elapsedTime = Mockito.mock(ElapsedTime.class);
        elapsedTime.totalTime = 50;
        float x = 100, y = 100, right = 50, bottom = 50;
        //mocked class objects
        float objectsX = 0, objectsY = 0, objectsRight = 0, objectsBottom = 0;
        boolean isPlaying = true;
        double animationStartTime = 0;
        int startFrame = 0, currentFrame = 12;

        //method
        objectsX = x;
        objectsY = y;
        objectsRight = right;
        objectsBottom = bottom;

        // Only commence playback if the animation is not currently playing
        if(!isPlaying) {
            animationStartTime = elapsedTime.totalTime;
            currentFrame = startFrame;
            isPlaying = true;
        }

        assertTrue(isPlaying);
        assertEquals(100f,objectsX);
        assertEquals(100f,objectsY);
        assertEquals(50f,objectsRight);
        assertEquals(50f,objectsBottom);
        assertEquals(12, currentFrame);
        assertEquals(0.0,animationStartTime);

    }

    @Test
    public void updateTestAnimationNotPlaying()
    {
        //Variables passed in as paramaters
        ElapsedTime elapsedTime = Mockito.mock(ElapsedTime.class);
        elapsedTime.totalTime = 10;
        boolean isPlaying = false, loopAnimation = false;
        int animationStartTime = 0;
        float totalPeriod = 1.5f;
        int currentFrame = 5, endFrame = 34, startFrame = 0;


        if (!isPlaying) return;

        // Determine the length of time the animation has been playing for
        float timeSinceAnimationStart =
                (float) (elapsedTime.totalTime - animationStartTime);

        // If the animation period has been exceeded and the animation is not
        // looping, then end playback and default to the final frame of the animation
        if (!loopAnimation
                && timeSinceAnimationStart > totalPeriod) {
            currentFrame = endFrame;
            //stop();
        } else {
            // Select an appropriate animation frame
            float animationPosition =
                    timeSinceAnimationStart / totalPeriod;
            animationPosition -= (int) animationPosition;

            currentFrame = (int) (
                    (float) (endFrame - startFrame + 1) * animationPosition) + startFrame;
        }
        assertFalse(isPlaying);
        assertEquals(5,currentFrame);
    }

    @Test
    public void updateTestAnimationPlayingTimeGreaterThanPlayingTime()
    {
        //Variables passed in as paramaters
        ElapsedTime elapsedTime = Mockito.mock(ElapsedTime.class);
        elapsedTime.totalTime = 10;
        boolean isPlaying = true, loopAnimation = false;
        int animationStartTime = 0;
        float totalPeriod = 1.5f;
        int currentFrame = 5, endFrame = 34, startFrame = 0;
        boolean stop = false;


        if (!isPlaying) return;

        // Determine the length of time the animation has been playing for
        float timeSinceAnimationStart =
                (float) (elapsedTime.totalTime - animationStartTime);

        // If the animation period has been exceeded and the animation is not
        // looping, then end playback and default to the final frame of the animation
        if (!loopAnimation
                && timeSinceAnimationStart > totalPeriod) {
            currentFrame = endFrame;
            stop = true;
        } else {
            // Select an appropriate animation frame
            float animationPosition =
                    timeSinceAnimationStart / totalPeriod;
            animationPosition -= (int) animationPosition;

            currentFrame = (int) (
                    (float) (endFrame - startFrame + 1) * animationPosition) + startFrame;
        }
        assertTrue(isPlaying);
        assertTrue(stop);
        assertEquals(34, currentFrame);
    }

    @Test
    public void updateTestAnimationPlayingTimeLessThanPlayingTime()
    {
        //Variables passed in as paramaters
        ElapsedTime elapsedTime = Mockito.mock(ElapsedTime.class);
        elapsedTime.totalTime = 10;
        boolean isPlaying = true, loopAnimation = false;
        float animationStartTime = 9.5f;
        float totalPeriod = 1.5f;
        int currentFrame = 10, endFrame = 34, startFrame = 0;
        boolean stop = false;


        if (!isPlaying) return;

        // Determine the length of time the animation has been playing for
        float timeSinceAnimationStart =
                (float) (elapsedTime.totalTime - animationStartTime);

        // If the animation period has been exceeded and the animation is not
        // looping, then end playback and default to the final frame of the animation
        if (!loopAnimation
                && timeSinceAnimationStart > totalPeriod) {
            currentFrame = endFrame;
            stop = true;
        } else {
            // Select an appropriate animation frame
            float animationPosition =
                    timeSinceAnimationStart / totalPeriod;
            animationPosition -= (int) animationPosition;

            currentFrame = (int) (
                    (float) (endFrame - startFrame + 1) * animationPosition) + startFrame;
        }
        assertTrue(isPlaying);
        assertFalse(stop);
        assertEquals(11, currentFrame);
    }

}
