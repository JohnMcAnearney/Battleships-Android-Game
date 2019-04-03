/*package uk.ac.qub.eeecs.gage;

import android.app.Activity;
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
import uk.ac.qub.eeecs.game.BattleShips.PreferencesManager;
import uk.ac.qub.eeecs.game.BattleShips.SettingsScreen;
import uk.ac.qub.eeecs.game.DemoGame;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SettingsScreenTest {
    private Context context;
    private DemoGame mGame;
    private SettingsScreen mSettingsScreen;
    private AudioManager mAudioManager;
    private PreferencesManager mPreferencesManager;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        setupGameManager();
    }

    private void setupGameManager() {
        mGame = new DemoGame();
        mGame.mFileIO = new FileIO(context);
        mGame.mAssetManager = new AssetManager(mGame);
        mGame.mAudioManager = new AudioManager(mGame);
        mGame.mScreenManager = new ScreenManager(mGame);
        mPreferencesManager = new PreferencesManager(context);
        mGame.mScreenManager.addScreen(mSettingsScreen);
        mSettingsScreen = new SettingsScreen(mGame);
    }
    @Test
    public void preformEffectsButtonActions_increaseVolume_effectsEnabled_volumeShouldIncreaseByOne(){
        mAudioManager.setMusicEnabled(true);
        mPreferencesManager.saveMuteMusicStatus(true);
        mPreferencesManager.saveCurrentEffectVolume(mAudioManager.getMusicVolume());
        mAudioManager.setMusicVolume(0.2f);
        mSettingsScreen.preformEffectsButtonActions(0.1f);
        assertEquals(0.3f, mAudioManager.getMusicVolume());
    }


/*
    @Test
    public void changeScreen_changeToMainMenu(){
        MainMenu mainMenu = new MainMenu(game);
        settingsScreen.changeScreen(mainMenu);
        Assert.assertEquals(mainMenu.getName(), game.getScreenManager().getCurrentScreen().getName());
    }

*/

//}