package uk.ac.qub.eeecs.gage;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetRatioTest {
    Game mGame;
    @Before
    public void setUp()
    {
        mGame = new Game(){};
    }

    @Test
    public void getRatio_validData_testIsSuccessful()
    {
        Bitmap Platform = mGame.getAssetManager().getBitmap("rectangularPlatform");
        float ratio = (Platform.getWidth()/Platform.getHeight());
        boolean success = false;
        if(ratio == 2) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void getRatio_validData_testIsSuccessful2()
    {
        Bitmap Platform = mGame.getAssetManager().getBitmap("Platform");
        float ratio = (Platform.getWidth()/Platform.getHeight());
        boolean success = false;
        if(ratio == 1) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void getRatio_validData_testIsUnsuccessful()
    {
        Bitmap Platform = mGame.getAssetManager().getBitmap("rectangularPlatform");
        float ratio = (Platform.getWidth()/Platform.getHeight());
        boolean success = true;
        if(ratio == 1) {
            success = false;
        }
        assertFalse(success);
    }

    @Test
    public void getRatio_validData_testIsUnsuccessful2()
    {
        Bitmap Platform = mGame.getAssetManager().getBitmap("Platform");
        float ratio = (Platform.getWidth()/Platform.getHeight());
        boolean success = true;
        if(ratio == 2) {
            success = false;
        }
        assertFalse(success);
    }
}
