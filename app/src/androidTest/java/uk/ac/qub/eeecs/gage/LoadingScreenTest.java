package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.BoardSetupScreen;
import uk.ac.qub.eeecs.game.BattleShips.LoadingScreen;
import uk.ac.qub.eeecs.game.DemoGame;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Author: Edgars(402030154)
 * A test class which will test if the transition to new screens occurs correctly
 */
@RunWith(AndroidJUnit4.class)
public class LoadingScreenTest {
    Context mContext;
    @Mock
    DemoGame mGame;
    ElapsedTime mElapsedTime;

    // Initialising the setup method for the test, which will run @Before
    @Before
    public void setup() {
        // Initialising the game context
        mContext = InstrumentationRegistry.getTargetContext();

        // Initialising the mGame variable with a new DemoGame()
        mGame = mock(DemoGame.class);

        // Mocking the AssetManager class to allow for full functionality within the test class
        AssetManager testAssetManager = mock(AssetManager.class);
        when(mGame.getAssetManager()).thenReturn(testAssetManager);

        // Mocking the AudioManager class to allow for full functionality within the test class
        AudioManager testAudioManager = mock(AudioManager.class);
        when(mGame.getAudioManager()).thenReturn(testAudioManager);

        // Setting up a ScreenManager to allow for testing of the screen transitions within the PauseScreen
        ScreenManager testScreenManager = new ScreenManager(mGame);
        when(mGame.getScreenManager()).thenReturn(testScreenManager);

        // Mocking the FileIO class to allow for full functionality within the test class
        FileIO testFileIO = mock(FileIO.class);
        when(mGame.getFileIO()).thenReturn(testFileIO);

        // Initialising the elapsed time variable
        mElapsedTime = new ElapsedTime();
    }

    // Test which sees if the transition to board setup screen occurs - NEED TO COME BACK TO
    /*@Test
    public void changeScreenToBoardSetupScreen() {
        BoardSetupScreen boardSetupScreen = new BoardSetupScreen(mGame);
        mGame.getScreenManager().addScreen(boardSetupScreen);

        LoadingScreen loadingScreen = new LoadingScreen(mGame);
        mGame.getScreenManager().addScreen(loadingScreen);

        loadingScreen.moveToNewGameScreen(boardSetupScreen);

        Assert.assertEquals(mGame.getScreenManager().getCurrentScreen().getName(), boardSetupScreen.getName());
    }*/
}


