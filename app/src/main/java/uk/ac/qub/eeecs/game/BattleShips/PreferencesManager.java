package uk.ac.qub.eeecs.game.BattleShips;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferencesManager {
    /*Created 100% by AT: 40207942*/

    private Activity activity;

    private SharedPreferences mPreferences ;
    private SharedPreferences.Editor mPreferencesEditor;

    private static final String MUSIC_SHAREDPREF_KEY = "MusicPreference";
    private static final String EFFECT_SHAREDPREF_KEY = "EffectPreference";
    private static final String MUTE_MUSIC_SHAREDPREF_KEY = "MuteMusicPreference";
    private static final String MUTE_EFFECT_SHAREDPREF_KEY = "MuteEffectPreference";

    private float mSharedPreferenceCurrentMusicVolume;
    private float mSharedPreferenceCurrentEffectVolume;
    private boolean mSharedPreferencesIsMusicMuted;
    private boolean mSharedPreferencesIsEffectMuted;


    public PreferencesManager(Activity activity) {
        this.mPreferences= PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
       // this.mPreferences = activity.getSharedPreferences(activity.getPackageName()+"_preferences", Context.MODE_PRIVATE );
        this.mPreferencesEditor = this.mPreferences.edit();
        this.activity=activity;
    }

    public  float loadCurrentMusicVolume(float currentMusic){
        mSharedPreferenceCurrentMusicVolume=mPreferences.getFloat(MUSIC_SHAREDPREF_KEY, currentMusic);
        return mSharedPreferenceCurrentMusicVolume;
    }
    public  float loadCurrentEffectsVolume(float currentEffect){
        mSharedPreferenceCurrentEffectVolume=mPreferences.getFloat(EFFECT_SHAREDPREF_KEY, currentEffect);
        return  mSharedPreferenceCurrentEffectVolume;
    }
    public boolean loadMuteMusicStatus(boolean muteStatus){
        mSharedPreferencesIsMusicMuted = mPreferences.getBoolean(MUTE_MUSIC_SHAREDPREF_KEY, muteStatus);
        return mSharedPreferencesIsMusicMuted;
    }
    public boolean loadMuteEffectStatus(boolean muteStatus){
        mSharedPreferencesIsEffectMuted = mPreferences.getBoolean(MUTE_EFFECT_SHAREDPREF_KEY, muteStatus);
        return mSharedPreferencesIsEffectMuted;
    }

    public void saveMuteEffectStatus(boolean muteStatus){
        mPreferencesEditor.putBoolean(MUTE_EFFECT_SHAREDPREF_KEY, muteStatus);
        mPreferencesEditor.commit();
    }

    public void saveMuteMusicStatus(boolean muteStatus){
        mPreferencesEditor.putBoolean(MUTE_MUSIC_SHAREDPREF_KEY, muteStatus);
        mPreferencesEditor.commit();
    }

    public void saveCurrentMusicVolume(float currentMusic){
        mPreferencesEditor.putFloat(MUSIC_SHAREDPREF_KEY, currentMusic);
        mPreferencesEditor.commit();
    }

    public void saveCurrentEffectVolume(float currentEffects) {
        mPreferencesEditor.putFloat(EFFECT_SHAREDPREF_KEY, currentEffects);
        mPreferencesEditor.commit();
    }
}
