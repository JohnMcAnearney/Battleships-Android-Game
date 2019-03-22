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
import uk.ac.qub.eeecs.game.BattleShips.InstructionsScreen;
import uk.ac.qub.eeecs.game.BattleShips.PauseScreen;
import uk.ac.qub.eeecs.game.BattleShips.SettingsScreen;
import uk.ac.qub.eeecs.game.DemoGame;

/*
* Author: Edgars(402030154)
*/
@RunWith(AndroidJUnit4.class)
public class PauseScreenTest
{
    Context mContext;
    Game mGame;
    ElapsedTime mElapsedTime;


    @Before
    public void setup()
    {
        mContext = InstrumentationRegistry.getTargetContext();

        mGame = new DemoGame();
        mElapsedTime = new ElapsedTime();
        AssetManager assetManager = new AssetManager(mGame);
        mGame.mAssetManager = new AssetManager(mGame);
        mGame.mAssetManager = assetManager;
        mGame.getAssetManager().loadAssets("txt/assets/PauseScreenAssets.JSON");

        FileIO fileIO = new FileIO(mContext);
        mGame.mFileIO = fileIO;
    }

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
}