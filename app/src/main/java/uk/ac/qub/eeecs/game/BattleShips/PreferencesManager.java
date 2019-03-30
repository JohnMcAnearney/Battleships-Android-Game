package uk.ac.qub.eeecs.game.BattleShips;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferencesManager {

    private Activity activity;

    private SharedPreferences mPreferences ;
    private SharedPreferences.Editor mPreferencesEditor;

    public static final String MUSIC_SHAREDPREF_KEY = "MusicPreference";
    public static final String EFFECT_SHAREDPREF_KEY = "EffectPreference";

    public static final String MUTE_MUSIC_SHAREDPREF_KEY = "MuteMusicPreference";
    public static final String MUTE_EFFECT_SHAREDPREF_KEY = "MuteEffectPreference";

    private float mSharedPreferenceCurrentMusicVolume;
    private float mSharedPreferenceCurrentEffectVolume;


    public PreferencesManager(Activity activity) {
        this.mPreferences= PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        this.mPreferences = activity.getSharedPreferences(activity.getPackageName()+"_preferences", Context.MODE_PRIVATE );
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

    public void saveCurrentMusicVolume(float currentMusic){
        mPreferencesEditor.putFloat(MUSIC_SHAREDPREF_KEY, currentMusic);
        mPreferencesEditor.commit();
    }

    public void saveCurrentEffectVolume(float currentEffects) {
        mPreferencesEditor.putFloat(EFFECT_SHAREDPREF_KEY, currentEffects);
        mPreferencesEditor.commit();
    }
}
