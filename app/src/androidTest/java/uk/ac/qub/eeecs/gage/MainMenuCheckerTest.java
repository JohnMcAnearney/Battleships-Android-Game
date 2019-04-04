package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.mockito.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.MainMenu;
import uk.ac.qub.eeecs.game.BattleShips.MainMenuChecker;
import uk.ac.qub.eeecs.game.DemoGame;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Author: Edgars(402030154)
 * A test class which will test if the transition to a new screens occurs correctly
 * and a test which makes sure the correct button sound is played
 */
@RunWith(AndroidJUnit4.class)
public class MainMenuCheckerTest
{
    Context mContext;
    @Mock
    DemoGame mGame;
    ElapsedTime mElapsedTime;

    // Initialising the setup method for the test, which will run @Before
    @Before
    public void setup()
    {
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

        /*
         * Setting up a ScreenManager to allow for testing of the screen transitions within
         * the PauseScreen
         */
        ScreenManager testScreenManager = new ScreenManager(mGame);
        when(mGame.getScreenManager()).thenReturn(testScreenManager);

        // Mocking the FileIO class to allow for full functionality within the test class
        FileIO testFileIO = mock(FileIO.class);
        when(mGame.getFileIO()).thenReturn(testFileIO);

        // Initialising the elapsed time variable
        mElapsedTime = new ElapsedTime();

        // Initialising the FileIO
        FileIO fileIO = new FileIO(mContext);
        mGame.mFileIO = fileIO;
    }

    /*
     * A test which initialises a MainMenuChecker and a MainMenuScreen and sees if the
     * moveToNewGameScreen() method works correctly
     */
    @Test
    public void changeScreenToMainMenuScreen()
    {
        // Initialising a mainMenuChecker class
        MainMenuChecker mainMenuChecker = new MainMenuChecker(mGame);
        mGame.getScreenManager().addScreen(mainMenuChecker);

        // Initialising a MainMenu class
        MainMenu mainMenu = new MainMenu(mGame);
        mGame.getScreenManager().addScreen(mainMenu);

        mainMenuChecker.moveToNewGameScreen(mainMenu);
        Assert.assertEquals(mGame.getScreenManager().getCurrentScreen().getName(),
                mainMenu.getName());
    }

    /*
     * A test which initialises a MainMenuChecker and the yesButtonSound and sees if the sound is
     * played correctly
     */
    @Test
    public void testYesButtonSound()
    {
        // Initialising a mainMenuChecker class
        MainMenuChecker mainMenuChecker = new MainMenuChecker(mGame);
        mGame.getScreenManager().addScreen(mainMenuChecker);

        // Initialising the sound file
        Sound yesButtonSound = mGame.getAssetManager().getSound(
                "sound/ButtonEffectSound.wav");

        Assert.assertEquals(mainMenuChecker.playButtonSound(yesButtonSound), yesButtonSound);
    }
}