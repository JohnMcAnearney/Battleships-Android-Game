package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.BoardSetupScreen;
import uk.ac.qub.eeecs.game.BattleShips.InstructionsScreen;
import uk.ac.qub.eeecs.game.BattleShips.PauseScreen;
import uk.ac.qub.eeecs.game.BattleShips.SettingsScreen;
import uk.ac.qub.eeecs.game.DemoGame;

/*
* Author: Edgars(402030154)
* A test class which will test if the transition to new screens occurs correctly
*/
@RunWith(AndroidJUnit4.class)
public class PauseScreenTest
{
    Context mContext;
    Game mGame;
    ElapsedTime mElapsedTime;

    // Initialising the setup method for the test, which will run @Before
    @Before
    public void setup()
    {
        // Initialising the game context
        mContext = InstrumentationRegistry.getTargetContext();

        // Initialising the mGame variable with a new DemoGame()
        mGame = new DemoGame();

        // Initialising variables neeeded for the test class
        mElapsedTime = new ElapsedTime();
        AssetManager assetManager = new AssetManager(mGame);
        mGame.mAssetManager = new AssetManager(mGame);
        mGame.mAssetManager = assetManager;

        // Loading in the PauseScreen Assets
        mGame.getAssetManager().loadAssets("txt/assets/PauseScreenAssets.JSON");

        // Initialising the FileIO
        FileIO fileIO = new FileIO(mContext);
        mGame.mFileIO = fileIO;
    }

    // A test which initialises a pause screen and a InstructionsScreen and sees if the moveToNewGameScreen() method works correctly
    @Test
    public void changeScreenToInstructionScreen()
    {
       PauseScreen pauseScreen = new PauseScreen(mGame);
       mGame.mScreenManager.addScreen(pauseScreen);

       InstructionsScreen instructionsScreen = new InstructionsScreen(mGame);
       mGame.mScreenManager.addScreen(instructionsScreen);

       pauseScreen.moveToNewGameScreen(instructionsScreen);

       Assert.assertEquals(mGame.getScreenManager().getCurrentScreen().getName(), instructionsScreen.getName());
    }

    // A test which initialises a pause screen and a SettingsScreen and sees if the moveToNewGameScreen() method works correctly
    @Test
    public void changeScreenToSettingsScreen()
    {
        PauseScreen pauseScreen = new PauseScreen(mGame);
        mGame.mScreenManager.addScreen(pauseScreen);

        SettingsScreen settingsScreen = new SettingsScreen(mGame);
        mGame.mScreenManager.addScreen(settingsScreen);

        pauseScreen.moveToNewGameScreen(settingsScreen);

        Assert.assertEquals(mGame.getScreenManager().getCurrentScreen().getName(), settingsScreen.getName());
    }

    // A test which initialises a pause screen and a BoardSetup screen and sees if the returnToPreviousGameScreen() method works correctly
    @Test
    public void returnToGameScreen()
    {
        PauseScreen pauseScreen = new PauseScreen(mGame);
        mGame.mScreenManager.addScreen(pauseScreen);

        BoardSetupScreen boardSetupScreen = new BoardSetupScreen(mGame);
        mGame.mScreenManager.addScreen(boardSetupScreen);

        pauseScreen.returnToPreviousGameScreen();

        Assert.assertEquals(mGame.getScreenManager().getCurrentScreen().getName(), boardSetupScreen.getName());
    }
}