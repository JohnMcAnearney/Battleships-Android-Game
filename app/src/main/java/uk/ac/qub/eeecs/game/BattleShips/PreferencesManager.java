package uk.ac.qub.eeecs.game.BattleShips;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import uk.ac.qub.eeecs.gage.Game;


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
     * */
    private Game mGame;
    private Activity mActivity;

    //Define the shared preferences
    private SharedPreferences mPreferences ;
    private SharedPreferences.Editor mPreferencesEditor;

    //For the music values in shared preferences.
    private float mSharedPreferenceCurrentMusicVolume;
    private float mSharedPreferenceCurrentEffectVolume;

    //For the effect values in shared preferences.
    private boolean mSharedPreferencesIsMusicMuted;
    private boolean mSharedPreferencesIsEffectMuted;

    /**
     * CONSTRUCTOR
     * Used specifically for game setting screen
     * @param mGame
     */
    public PreferencesManager(Game mGame) {
        this.mGame = mGame;
        mActivity=mGame.getActivity();
        this.mPreferences= PreferenceManager.getDefaultSharedPreferences(mActivity.getApplicationContext());
        this.mPreferencesEditor = this.mPreferences.edit();

    }
    /**
     * CONSTRUCTOR
     * Used specifically for the testing
     * @param mContext
     */
    public PreferencesManager(Context mContext) {
        this.mPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);
        this.mPreferencesEditor = this.mPreferences.edit();

    }

    //METHODS//
    /**
     * Method that get the volume of music from shared preferences;
     * @param currentMusic
     * @return
     */
    public  float loadCurrentMusicVolume(String key, float currentMusic){
        mSharedPreferenceCurrentMusicVolume=mPreferences.getFloat(key, currentMusic);
        return mSharedPreferenceCurrentMusicVolume;
    }

    /**
     * Method that get the volume of effect sounds from shared preferences;
     * @param currentEffect
     * @return
     */
    public  float loadCurrentEffectsVolume(String key, float currentEffect){
        mSharedPreferenceCurrentEffectVolume=mPreferences.getFloat(key, currentEffect);
        return mSharedPreferenceCurrentEffectVolume;
    }

    /**
     * Method that get the boolean if the music is muted or not from shared preferences;
     * @param muteStatus
     * @return
     */
    public boolean loadMuteMusicStatus(String key, boolean muteStatus){
        mSharedPreferencesIsMusicMuted = mPreferences.getBoolean(key, muteStatus);
        return mSharedPreferencesIsMusicMuted;
    }

    /**
     * Method that get the boolean if the effect sound is muted or not from shared preferences;
     * @param muteStatus
     * @return
     */
    public boolean loadMuteEffectStatus(String key, boolean muteStatus){
        mSharedPreferencesIsEffectMuted = mPreferences.getBoolean(key, muteStatus);
        return mSharedPreferencesIsEffectMuted;
    }

    /**
     * Method that saves the boolean if the effects is muted or not to shared preferences;
     * @param muteStatus
     * @return
     */

    public void saveMuteEffectStatus(String key, boolean muteStatus){
        mPreferencesEditor.putBoolean(key , muteStatus);
        mPreferencesEditor.commit();
    }
    /**
     * Method that saves the boolean if the music is muted or not to shared preferences;
     * @param muteStatus
     * @return
     */
    public void saveMuteMusicStatus(String key, boolean muteStatus){
        mPreferencesEditor.putBoolean(key, muteStatus);
        mPreferencesEditor.commit();
    }

    /**
     * Method that saves the new float of the music volume to shared preferences;
     * @param currentMusic
     */
    public void saveCurrentMusicVolume(String key, float currentMusic){
        mPreferencesEditor.putFloat(key, currentMusic);
        mPreferencesEditor.commit();
    }

    /**
     * Method that saves the new float of the effect volume to shared preferences;
     * @param currentEffects
     */
    public void saveCurrentEffectVolume(String key, float currentEffects) {
        mPreferencesEditor.putFloat(key, currentEffects);
        mPreferencesEditor.commit();
    }
    public boolean getSharedPreferencesIsMusicMuted(){
        return mSharedPreferencesIsMusicMuted;
    }
    public boolean getSharedPreferencesIsEffectMuted(){
        return mSharedPreferencesIsEffectMuted;
    }
}
