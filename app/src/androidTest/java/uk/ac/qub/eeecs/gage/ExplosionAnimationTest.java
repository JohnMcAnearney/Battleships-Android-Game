package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationSettings;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.BoardSetupScreen;
import uk.ac.qub.eeecs.game.BattleShips.ExplosionAnimation;
import uk.ac.qub.eeecs.game.DemoGame;

@RunWith(AndroidJUnit4.class)
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

        assertEquals("img/ExplosionSheet.png",explosionAnimation.getSpritesheet());
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

        assertEquals(1.5,explosionAnimation.getTotalPeriod());
    }

    @Test
    public void ExplosionAnimationTestStartFrame()
    {
        AnimationSettings animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");;
        ExplosionAnimation explosionAnimation = new ExplosionAnimation(animationSettings, 0);

        assertEquals(0,explosionAnimation.getStartFrame());
    }

}
