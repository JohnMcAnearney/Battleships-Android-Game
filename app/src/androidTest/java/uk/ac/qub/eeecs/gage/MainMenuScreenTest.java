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
import uk.ac.qub.eeecs.gage.engine.audio.Music;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.InstructionsScreen;
import uk.ac.qub.eeecs.game.BattleShips.LoadingScreen;
import uk.ac.qub.eeecs.game.BattleShips.MainMenu;
import uk.ac.qub.eeecs.game.BattleShips.SettingsScreen;
import uk.ac.qub.eeecs.game.DemoGame;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainMenuScreenTest {

    /*Created 100% by AT: 40207942*/
    /*References: QUBBATTLE, TRIBALHUNTER, AVANT, LECTURE CODE*/
    private Context mContext;
    private DemoGame mGame;
    private MainMenu mMainMenuScreen;
    private Music mBackgroundMusic;
    private Sound mEffectSound;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        setupGameManager();
        mGame.mScreenManager.addScreen(new MainMenu(mGame));
        mBackgroundMusic = mGame.mAssetManager.getMusic("RickRoll");
        mEffectSound = mGame.mAssetManager.getSound("ButtonsEffect");
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
        assertEquals(loadingScreen.getName(), mGame.getScreenManager().getCurrentScreen().getName());
    }

    @Test
    public void changeScreen_changeToInstructionScreen(){
        InstructionsScreen instructionScreen = new InstructionsScreen(mGame);
        mMainMenuScreen.changeScreen(instructionScreen);
        assertEquals(instructionScreen.getName(), mGame.getScreenManager().getCurrentScreen().getName());
    }
    @Test
    public void checkIfBackgruondMusicPlaying_true(){
       mGame.mAudioManager.setMusicEnabled(true);
       mMainMenuScreen.playBackgroundMusic(mBackgroundMusic);
       assertEquals(true, mGame.mAudioManager.isMusicPlaying());
    }
    @Test
    public void checkIfBackgruondMusicPlaying_false(){
        mGame.mAudioManager.setMusicEnabled(false);
        mMainMenuScreen.playBackgroundMusic(mBackgroundMusic);
        boolean isMusicPlaying =mGame.mAudioManager.getMusicEnabled();
        Assert.assertFalse(isMusicPlaying);
    }

    @Test
    public void testEffectSoundPlays(){
        Sound soundPlayed = mMainMenuScreen.playSoundEffect();
        assertEquals(mEffectSound,soundPlayed);
    }

}