package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.BattleShips.PreferencesManager;
import uk.ac.qub.eeecs.game.DemoGame;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PreferenceManagerTest {
    /*Created 100% by AT: 40207942*/
    /*References: QUBBATTLE, TRIBALHUNTER, AVANT, LECTURE CODE*/

    private Context context;
    private DemoGame mGame;
    private PreferencesManager mPreferencesManager;
    //defining final strings to be the keys for the shared prefereneces;
    private static final String MUSIC_SHAREDPREF_KEY_TEST = "MusicPreferenceTest";
    private static final String EFFECT_SHAREDPREF_KEY_TEST= "EffectPreferenceTest";
    private static final String MUTE_MUSIC_SHAREDPREF_KEY_TEST = "MuteMusicPreferenceTest";
    private static final String MUTE_EFFECT_SHAREDPREF_KEY_TEST= "MuteEffectPreferenceTest";

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
        mPreferencesManager = new PreferencesManager(context);
    }

    //TESTS TO CHECK SAVING MUTE ACTIONS WORKS CORRECTLY
    @Test
    public void saveMusicStatus_False_ExpectedFalse(){
        mPreferencesManager.saveMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, false);
        assertEquals(false,mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, false));
    }
    @Test
    public void saveMusicStatus_True_ExpectedTrue(){
        mPreferencesManager.saveMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, true);
        assertEquals(true,mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, true));
    }
    @Test
    public void saveEffectStatus_False_ExpectedFalse(){
        mPreferencesManager.saveMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY_TEST, false);
        assertEquals(false,mPreferencesManager.loadMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY_TEST, false));
    }
    @Test
    public void saveEffectStatus_True_ExpectedTrue(){
        mPreferencesManager.saveMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY_TEST, true);
        assertEquals(true,mPreferencesManager.loadMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY_TEST, true));
    }

    ///TESTS TO CHECK LOADING MUTE ACTIONS WORKS CORRECTLY
    @Test
    public void loadMuteMusicStatus_False_ExpectedFalse(){
        //save to be false
        mPreferencesManager.saveMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, false);
        //load to check
        boolean checkStatus = mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, false);
        assertEquals(checkStatus,mPreferencesManager.getSharedPreferencesIsMusicMuted());
    }
    @Test
    public void loadMuteEffectStatus_True_ExpectedTrue(){
        //save to be true
        mPreferencesManager.saveMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, true);
        //load it in
        boolean checkStatus = mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, true);
        assertEquals(checkStatus,mPreferencesManager.getSharedPreferencesIsMusicMuted());
    }
    @Test
    public void loadEffectStatus_False_ExpectedFalse(){
        mPreferencesManager.saveMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY_TEST, false);
        boolean checkStatus = mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, false);
        assertEquals(checkStatus,mPreferencesManager.getSharedPreferencesIsMusicMuted());
    }
    @Test
    public void loadEffectStatus_True_ExpectedTrue(){
        mPreferencesManager.saveMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, true);
        boolean checkStatus = mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY_TEST, true);
        assertEquals(checkStatus,mPreferencesManager.getSharedPreferencesIsMusicMuted());
    }

    //TESTS CHECK SAVING AUDIO VALUE WORKS
    @Test
    public void saveCurrentEffectVolume_expectedValueReturned(){
        mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY_TEST,0.0f) ;
        assertEquals(0.0f,mPreferencesManager.loadCurrentEffectsVolume(EFFECT_SHAREDPREF_KEY_TEST, 0.0f));
    }
    @Test
    public void saveCurrentMusicVolume_expectedValueReturned(){
        mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST,0.0f) ;
        assertEquals(0.0f,mPreferencesManager.loadCurrentEffectsVolume(MUSIC_SHAREDPREF_KEY_TEST, 0.0f));
    }
    @Test
    public void loadCurrentEffectVolume_expectedValueReturned(){
        mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY_TEST,0.0f) ;
        //load to check
        float checkValue = mPreferencesManager.loadCurrentEffectsVolume(EFFECT_SHAREDPREF_KEY_TEST, 0.0f);
        assertEquals(0.0f, checkValue);
    }
    @Test
    public void loadCurrentMusicVolume_expectedValueReturned(){
        mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST,0.0f) ;
        //load to check
        float checkValue = mPreferencesManager.loadCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST, 0.0f);
        assertEquals(0.0f,checkValue);
    }

    @Test
    public void saveCurrentEffectVolume_increasing_multipleSaves_expectedLastSaveToLoad(){
        for(float i =0.1f; i<=0.5; i +=0.1){
            mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY_TEST,i) ;
        }
        float checkValue = mPreferencesManager.loadCurrentEffectsVolume(EFFECT_SHAREDPREF_KEY_TEST, 0.5f);
        assertEquals(0.5f,checkValue);
    }

    @Test
    public void saveCurrentMusicVolume_increasing_multipleSaves_expectedLastSaveToLoad(){
        for(float i =0.1f; i<=0.5; i +=0.1){
            mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST,i) ;
        }
        float checkValue = mPreferencesManager.loadCurrentEffectsVolume(MUSIC_SHAREDPREF_KEY_TEST, 0.5f);
        assertEquals(0.5f,checkValue);
    }
    @Test
    public void saveCurrentEffectVolume_decreasing_multipleSaves_expectedLastSaveToLoad(){
        mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY_TEST,0.5f);
        mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY_TEST,0.4f);
        mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY_TEST,0.3f);
        mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY_TEST,0.2f);
        mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY_TEST,0.1f);
        mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY_TEST,0.0f);
        float checkValue = mPreferencesManager.loadCurrentEffectsVolume(EFFECT_SHAREDPREF_KEY_TEST, 0.0f);
        assertEquals(0.0f,checkValue);
    }

    @Test
    public void saveCurrentMusicVolume_decreasing_multipleSaves_expectedLastSaveToLoad(){
        mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST,0.5f) ;
        mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST,0.4f) ;
        mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST,0.3f) ;
        mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST,0.2f) ;
        mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST,0.1f) ;
        mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY_TEST,0.0f) ;
        float checkValue = mPreferencesManager.loadCurrentEffectsVolume(MUSIC_SHAREDPREF_KEY_TEST, 0.0f);
        assertEquals(0.0f,checkValue);
    }
}