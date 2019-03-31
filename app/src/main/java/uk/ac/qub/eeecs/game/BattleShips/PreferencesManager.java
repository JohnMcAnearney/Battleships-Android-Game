package uk.ac.qub.eeecs.game.BattleShips;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferencesManager {
    /*Created 100% by AT: 40207942*/

    /**
    * Preference manager was created to store the shared preferences of the game
    * Specifically the shared preference of the volume but can be extended to manage all
    * shared preferences in the future
    * All enables testing of screens with shared preferences;
    */


    /**
     * Define the properties of the Preference Manager
     */
    private Activity activity;
    //Define the shared preferences
    private SharedPreferences mPreferences ;
    private SharedPreferences.Editor mPreferencesEditor;
    //final variables for storing specific values in shared preferences of the game
    private static final String MUSIC_SHAREDPREF_KEY = "MusicPreference";
    private static final String EFFECT_SHAREDPREF_KEY = "EffectPreference";
    private static final String MUTE_MUSIC_SHAREDPREF_KEY = "MuteMusicPreference";
    private static final String MUTE_EFFECT_SHAREDPREF_KEY = "MuteEffectPreference";
    //For the music values in shared preferences.
    private float mSharedPreferenceCurrentMusicVolume;
    private float mSharedPreferenceCurrentEffectVolume;
    //For the effect values in shared preferences.
    private boolean mSharedPreferencesIsMusicMuted;
    private boolean mSharedPreferencesIsEffectMuted;



    //CONSTRUCTOR
    /**
     * @param
     */
    public PreferencesManager(Context context) {
        //this.activity=activity;

        this.mPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        //this.mPreferences = activity.getSharedPreferences(activity.getPackageName()+"_preferences", Context.MODE_PRIVATE );

        this.mPreferencesEditor = this.mPreferences.edit();

    }

    //METHODS//

    /**
     * Method that get the volume of music from shared preferences;
     * @param currentMusic
     * @return
     */
    public  float loadCurrentMusicVolume(float currentMusic){
        mSharedPreferenceCurrentMusicVolume=mPreferences.getFloat(MUSIC_SHAREDPREF_KEY, currentMusic);
        return mSharedPreferenceCurrentMusicVolume;
    }

    /**
     * Method that get the volume of effect sounds from shared preferences;
     * @param currentEffect
     * @return
     */
    public  float loadCurrentEffectsVolume(float currentEffect){
        mSharedPreferenceCurrentEffectVolume=mPreferences.getFloat(EFFECT_SHAREDPREF_KEY, currentEffect);
        return  mSharedPreferenceCurrentEffectVolume;
    }


    /**
     * Method that get the boolean if the music is muted or not from shared preferences;
     * @param muteStatus
     * @return
     */
    public boolean loadMuteMusicStatus(boolean muteStatus){
        mSharedPreferencesIsMusicMuted = mPreferences.getBoolean(MUTE_MUSIC_SHAREDPREF_KEY, muteStatus);
        return mSharedPreferencesIsMusicMuted;
    }

    /**
     * Method that get the boolean if the effect sound is muted or not from shared preferences;
     * @param muteStatus
     * @return
     */
    public boolean loadMuteEffectStatus(boolean muteStatus){
        mSharedPreferencesIsEffectMuted = mPreferences.getBoolean(MUTE_EFFECT_SHAREDPREF_KEY, muteStatus);
        return mSharedPreferencesIsEffectMuted;
    }

    /**
     * Method that saves the boolean if the effects is muted or not to shared preferences;
     * @param muteStatus
     * @return
     */

    public void saveMuteEffectStatus(boolean muteStatus){
        mPreferencesEditor.putBoolean(MUTE_EFFECT_SHAREDPREF_KEY, muteStatus);
        mPreferencesEditor.commit();
    }
    /**
     * Method that saves the boolean if the music is muted or not to shared preferences;
     * @param muteStatus
     * @return
     */
    public void saveMuteMusicStatus(boolean muteStatus){
        mPreferencesEditor.putBoolean(MUTE_MUSIC_SHAREDPREF_KEY, muteStatus);
        mPreferencesEditor.commit();
    }

    /**
     * Method that saves the new float of the music volume to shared preferences;
     * @param currentMusic
     */
    public void saveCurrentMusicVolume(float currentMusic){
        mPreferencesEditor.putFloat(MUSIC_SHAREDPREF_KEY, currentMusic);
        mPreferencesEditor.commit();
    }

    /**
     * Method that saves the new float of the effect volume to shared preferences;
     * @param currentEffects
     */
    public void saveCurrentEffectVolume(float currentEffects) {
        mPreferencesEditor.putFloat(EFFECT_SHAREDPREF_KEY, currentEffects);
        mPreferencesEditor.commit();
    }
}
