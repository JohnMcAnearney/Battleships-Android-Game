package uk.ac.qub.eeecs.gage;

//package uk.ac.qub.eeecs.game.cardDemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.LoadingScreen;
import uk.ac.qub.eeecs.game.BattleShips.MainMenu;
import uk.ac.qub.eeecs.game.BattleShips.SettingsScreen;
import uk.ac.qub.eeecs.game.DemoGame;

@RunWith(AndroidJUnit4.class)
public class MainMenuScreenTest {
    private Context mContext;
    private DemoGame mGame;
    private MainMenu mMainMenuScreen;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        setupGameManager();
        mGame.mScreenManager.addScreen(new MainMenu(mGame));
        mMainMenuScreen = new MainMenu(mGame);
    }

    private void setupGameManager() {
        mContext = InstrumentationRegistry.getTargetContext();
        mGame = new DemoGame();
        mGame.mFileIO = new FileIO(mContext);
        mGame.mAssetManager = new AssetManager(mGame);
        mGame.mScreenManager = new ScreenManager(mGame);
        mGame.mAudioManager = new AudioManager(mGame);
    }

    @Test
    public void changeScreen_changeToLoadingScreen(){
        LoadingScreen loadingScreen = new LoadingScreen(mGame);
        mMainMenuScreen.changeScreen(loadingScreen);
        Assert.assertEquals(loadingScreen.getName(), mGame.getScreenManager().getCurrentScreen().getName());
    }





    /*
    @Test
    public void testEffectSoundPlays(){
        mMainMenuScreen.changeScreen(loadingScreen);
        mGame.getAudioManager().getSoundPool();
        Assert.assertEquals(game.g.getName(), mGame.getScreenManager().getCurrentScreen().getName());
    }*/
/*
    @Test
    public void changeScreen_changeToSettingsScreen(){
        SettingsScreen settingsScreen = new SettingsScreen(mGame);
        mMainMenuScreen.changeScreen(settingsScreen);
        Assert.assertEquals(settingsScreen.getName(), mGame.getScreenManager().getCurrentScreen().getName());
    }
*/


}