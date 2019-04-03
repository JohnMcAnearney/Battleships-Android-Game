package uk.ac.qub.eeecs.game.BattleShips;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.Music;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class SettingsScreen extends GameScreen {

    /*Created 100% by AT: 40207942*/
    /*References: QUBBATTLE, TRIBALHUNTER, AVANT, LECTURE CODE

    /**
     * Define the properties for the settings screen;
     */
    //Properties for background;
    private AssetManager mAssetManager;
    private Bitmap mSettingsBackground;
    private int mScreenWidth, mScreenHeight;
    private Rect mRect;

    //Properties relating to the shared preferences and preference manager;
    private Activity mActivity;
    private Context mContext;
    private PreferencesManager mPreferencesManager;

    //Properties relating to the buttons and list of buttons
    private PushButton mBackButton, mMuteMusicButton, mMuteEffectButton, mMusicText, mEffectsText;
    private List<PushButton> mAllButtons;
    private List<PushButton>mMusicControlButtons;
    private List<PushButton>mEffectControlButtons;

    //Hash maps created to get information from the JSON file
    private Map<PushButton, Float> mMusicButtonVolumeControls;
    private Map<PushButton, Float> mEffectButtonVolumeControls;

    //Properties relating to the update display bar and audio
    private UpdateBarDisplay mMusicBarDisplay, mEffectsBarDisplay;

    //Properties relating the the audio settings
    private AudioManager mAudioManager;
    private Music mBackgroundMusic;
    private Sound mEffectsButtonSound;

    //Final strings for the shared preference keys
    private static final String MUSIC_SHAREDPREF_KEY = "MusicPreference";
    private static final String EFFECT_SHAREDPREF_KEY = "EffectPreference";
    private static final String MUTE_MUSIC_SHAREDPREF_KEY = "MuteMusicPreference";
    private static final String MUTE_EFFECT_SHAREDPREF_KEY = "MuteEffectPreference";


    /**
     * CONSTRUCTOR
     * @param mGame
     */
    public SettingsScreen(Game mGame) {
        super("SettingsScreen", mGame);
        init(mGame);
        loadAssets();
        playBackgroundMusic(mBackgroundMusic);
        createBarDisplayVolume();
        createButtons();
        }


     public void init(Game mGame){
         mActivity= mGame.getActivity();
         mContext= mGame.getActivity().getApplicationContext();

         mPreferencesManager = new PreferencesManager(mGame);
         mAssetManager = mGame.getAssetManager();
         mAudioManager = mGame.getAudioManager();

         mScreenHeight=0;
         mScreenWidth=0;

         mAllButtons= new ArrayList<>();
         mMusicControlButtons= new ArrayList<>();
         mEffectControlButtons= new ArrayList<>();
         mMusicButtonVolumeControls = new HashMap<>();
         mEffectButtonVolumeControls = new HashMap<>();

     }





    /**
    * Method to load all of the game assets
    * */
    public void loadAssets(){
        mAssetManager.loadAssets("txt/assets/SettingsScreenAssets.JSON");
        //Setting assets
        mSettingsBackground = mAssetManager.getBitmap("SettingBackground");
        mBackgroundMusic=mAssetManager.getMusic("RickRoll");
        mEffectsButtonSound = mAssetManager.getSound("ButtonsEffect");
    }


    /**
     * method to draw the instances
     * @param elapsedTime Elapsed time information for the frame
     * @param graphics2D  Graphics instance used to draw the screen
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        //Drawing the background
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mSettingsBackground, null, mRect, null);

        //Drawing all of the buttons
        for(PushButton buttons: mAllButtons){
            buttons.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        }

        //Drawing the two display bars
        mMusicBarDisplay.draw(elapsedTime, graphics2D);
        mEffectsBarDisplay.draw(elapsedTime, graphics2D);
    }


    /**
     * Method to get width and height of screen for background image
     * @param graphics2D
     */
    public void getWidthAndHeightOfScreen(IGraphics2D graphics2D) {
        if (mScreenHeight == 0 || mScreenWidth == 0) {
            mScreenWidth = graphics2D.getSurfaceWidth();
            mScreenHeight = graphics2D.getSurfaceHeight();
            updateRect();
        }
    }

    /**
     * Updates rectangle based of screen size of device
     */
    public void updateRect() {
        mRect = new Rect(0, 0, mScreenWidth, mScreenHeight);
    }

    /**
     *  Creates the update display bars for the audio controls
     */
    public void createBarDisplayVolume(){
        //numberOfBits and scale added for ease of change
        int numberOfBits=10;
        int scaleOfBar = 26;
        mMusicBarDisplay = new UpdateBarDisplay(numberOfBits, mPreferencesManager.loadCurrentMusicVolume(MUSIC_SHAREDPREF_KEY, mAudioManager.getMusicVolume()), 0f, 1f, mDefaultLayerViewport.getWidth()+600.0f, (mDefaultLayerViewport.getHeight()/0.75f), scaleOfBar, this );
        mEffectsBarDisplay = new UpdateBarDisplay(numberOfBits,mPreferencesManager.loadCurrentEffectsVolume(EFFECT_SHAREDPREF_KEY, mAudioManager.getSfxVolume()), 0f,1f,mDefaultLayerViewport.getWidth()+600.0f, mDefaultLayerViewport.getHeight()/2+600, scaleOfBar, this );
    }

    /**
     * Method to construst the audio buttons
     * Reference: Lecture code sent from Phillip Hanna whihc I have developed by 40207942 to suit audio needs
     * @param buttonsToConstructJSONFile
     * @param mAllButtons
     */
    private void constructAudioButtons(String buttonsToConstructJSONFile, List<PushButton> mAllButtons) {
        String loadedJSON;
        try {
            loadedJSON = mGame.getFileIO().loadJSON(buttonsToConstructJSONFile);
        } catch (IOException e) {
            throw new RuntimeException(
                    "DemoMenuScreen.constructButtons: Cannot load JSON [" + buttonsToConstructJSONFile + "]");
        }
        try {
            JSONObject settings = new JSONObject(loadedJSON);
            JSONArray buttonInfo = settings.getJSONArray("pushButtons");
            for (int i = 0; i < buttonInfo.length(); i++) {
                float x = (float) buttonInfo.getJSONObject(i).getDouble("x");
                float y = (float) buttonInfo.getJSONObject(i).getDouble("y");
                float width = (float) buttonInfo.getJSONObject(i).getDouble("width");
                float height = (float) buttonInfo.getJSONObject(i).getDouble("height");
                String defaultBitmap = buttonInfo.getJSONObject(i).getString("defaultBitmap");
                //Developed the code and added new fields to sort the buttons
                float volumeValue =(float) buttonInfo.getJSONObject(i).getDouble("volumeValue");
                boolean isMusicButton = buttonInfo.getJSONObject(i).getBoolean("isMusicButton");
                PushButton button = new PushButton(x * mDefaultLayerViewport.getWidth(), y * mDefaultLayerViewport.getHeight(),
                            width, height,
                            defaultBitmap, this);
                //Developed the code to split the buttons into two types, music buttons and effects buttons.
                if(isMusicButton==true){
                    //Adds the music buttons to specific list and hash map
                    mMusicControlButtons.add(button);
                    mMusicButtonVolumeControls.put(button, volumeValue);
                }else{
                    //Adds the effect buttons to specific list and hash map
                    mEffectControlButtons.add(button);
                    mEffectButtonVolumeControls.put(button, volumeValue);
                }
                button.setPlaySounds(true, true);
                mAllButtons.add(button);
            }
        } catch (JSONException | IllegalArgumentException e) {
            throw new RuntimeException(
                    "DemoMenuScreen.constructButtons: JSON parsing error [" + e.getMessage() + "]");
        }
    }

    /**
     * Method to create all buttons on screen and add them to the array list
     */
    public void createButtons() {

        //Calls the method to create the audio control buttons
        constructAudioButtons("txt/assets/SettingScreenButtons.JSON", mAllButtons);

        //Creates the buttons, sets the sound to true and adds it to the list of all the buttons
        //Back Button
        mBackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "SettingsBackButton","SettingsBackButtonP",  this);
        mBackButton.setPlaySounds(true, true);
        mAllButtons.add(mBackButton);

        //Text Buttons
        mEffectsText= new PushButton(mDefaultLayerViewport.getWidth()*0.5f, mDefaultLayerViewport.getHeight() /2.25f,
                mDefaultLayerViewport.getWidth() * 0.3f, mDefaultLayerViewport.getHeight() /5.0f, "EffectText", this);
        mAllButtons.add(mEffectsText);
        mMusicText = new PushButton(mDefaultLayerViewport.getWidth() *0.5f, mDefaultLayerViewport.getHeight()-80f,
                mDefaultLayerViewport.getWidth() * 0.3f, mDefaultLayerViewport.getHeight() /5.0f, "MusicText", this);
        mAllButtons.add(mMusicText);


        // Music Mute Buttons
        //Checks if the music is previously muted in the shared preferences and displays the appropiate bitmap to screen.
        if (mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY,mAudioManager.getMusicEnabled())) {
            mMuteMusicButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.6f,
                    mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f, "UnmuteButton", this);
            mAllButtons.add(mMuteMusicButton);
        }else{
            mMuteMusicButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.6f,
                    mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f, "MuteButton", this);
            mAllButtons.add(mMuteMusicButton);
        }

        // Effect Mute Buttons
        //Checks if the effect is previously muted in the shared preferences and displays the appropiate bitmap to screen.
        if(mPreferencesManager.loadMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY, mAudioManager.getEffectsEnabled())){
            mMuteEffectButton= new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.3f,
                    mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f, "UnmuteButton", this);
            mAllButtons.add(mMuteEffectButton);
        }else{
            mMuteEffectButton= new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.3f,
                    mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f, "MuteButton", this);
            mAllButtons.add(mMuteEffectButton);
        }
    }

    /**
     * Method to check if music is playing on screen and plays it if not
     */
    public void playBackgroundMusic(Music musicToPlay) {
        //Checks if music is muted in shared preferences
        if(mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY, mAudioManager.getMusicEnabled()==true)) {
            //Checks if the music is already playing
            if(!mAudioManager.isMusicPlaying()) {
                //Sets the music to volume in shared preferences and plays it
                mAudioManager.setMusicVolume(mPreferencesManager.loadCurrentMusicVolume(MUSIC_SHAREDPREF_KEY, mAudioManager.getMusicVolume()));
                mAudioManager.playMusic(musicToPlay);
            }
        }
    }


    /**
     * Update method
     * @param elapsedTime Elapsed time information for the frame
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if(touchEvents.size()>0){
            for(PushButton button: mAllButtons) {
                button.update(elapsedTime);
                pressedButtonsActions();
            }
        }
    }


    /**
     * Method preforms music button actions
     * Sets the new volume, stops the music, plays it at the new volume and updates the bar
     * Method has been refactor to do the job of increasing or decreasing the current volume instead of two seperate methods;
     * @param volumeValue
     */

    public void preformMusicButtonActions( float volumeValue){
        //NOTE: The musics sound can only be changed when not muted.
        if(mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY, mAudioManager.getMusicEnabled())) {
            //Gets the current volume of music
            float currentMusic = mPreferencesManager.loadCurrentMusicVolume(MUSIC_SHAREDPREF_KEY, mAudioManager.getMusicVolume());
            //Increases/Decrease by the volumeValue
            mAudioManager.setMusicVolume(currentMusic + volumeValue);
            //Updates the music display bar
            mMusicBarDisplay.setValue(mAudioManager.getMusicVolume());
            mMusicBarDisplay.update();
            //Stops the music at old volume and plays it again at new volume.
            mAudioManager.stopMusic();
            mAudioManager.playMusic(mBackgroundMusic);
            //Saves the new volume of the screen on the shared preferences;
            mPreferencesManager.saveCurrentMusicVolume(MUSIC_SHAREDPREF_KEY, mAudioManager.getMusicVolume());
        }
    }


    /**
     * Method preforms effect button actions
     * Sets the new volume, stops the music, plays it at the new volume and updates the bar
     * Method has been refactor to do the job of increasing or decreasing the current volume instead of two seperate methods;
     * @param volumeValue
     */
    public void preformEffectsButtonActions(float volumeValue){
        //NOTE: The effects sound can only be changed when not muted.
        if(mPreferencesManager.loadMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY, mAudioManager.getEffectsEnabled())) {
            //Gets the current volume of effects
            float currentEffect = mPreferencesManager.loadCurrentEffectsVolume(EFFECT_SHAREDPREF_KEY,mAudioManager.getSfxVolume());
            //Increases/Decrease by the volumeValue.
            mAudioManager.setSfxVolume(currentEffect+volumeValue);
            //Saves the new volume of the effects on the shared preferences;
            mPreferencesManager.saveCurrentEffectVolume(EFFECT_SHAREDPREF_KEY, mAudioManager.getSfxVolume());
            //Update the effects display bar
            mEffectsBarDisplay.setValue(mAudioManager.getSfxVolume());
            mEffectsBarDisplay.update();
            //Plays sound to demonstrate its working
            mAudioManager.play(mEffectsButtonSound);
        }
    }

    /**
     * Method checks if music is playing when button triggered
     * Then stops the music and changes the bitmap image to the mute one
     * Else sets bitmap to unmute and plays the background music
     * Note both mute methods do work on shared prefernces for the settings screen however the main menu screen does not use
     * shared prefernces for audio so test it after going the to the settings screen once.
     * It remembers the shared preferences but the main menu doesnt so click mute one on the music all everything works
     * */

    public void preformMuteMusicButtonActions(){
        boolean isMusicMuted = mPreferencesManager.loadMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY,mAudioManager.getMusicEnabled());
        if(isMusicMuted==true){
            mAudioManager.setMusicEnabled(false);
            mPreferencesManager.saveMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY, mAudioManager.getMusicEnabled());
            mMuteMusicButton.setBitmap(mGame.getAssetManager().getBitmap("MuteButton"));
            mAudioManager.stopMusic();
        }else {
            mMuteMusicButton.setBitmap(mGame.getAssetManager().getBitmap("UnmuteButton"));
            mAudioManager.setMusicEnabled(true);
            mPreferencesManager.saveMuteMusicStatus(MUTE_MUSIC_SHAREDPREF_KEY, mAudioManager.getMusicEnabled());
            playBackgroundMusic(mBackgroundMusic);
        }
    }

    /**
     * Method to mute effects sound
     */
    public void preformMuteEffectButtonActions(){
        boolean isEffectsMuted = mPreferencesManager.loadMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY,mAudioManager.getEffectsEnabled());
        if(isEffectsMuted==true){
            mAudioManager.setEffectEnabled(false);
            mPreferencesManager.saveMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY, mAudioManager.getEffectsEnabled());
            mMuteEffectButton.setBitmap(mGame.getAssetManager().getBitmap("MuteButton"));
        } else{
            mMuteEffectButton.setBitmap(mGame.getAssetManager().getBitmap("UnmuteButton"));
            mAudioManager.setEffectEnabled(true);
            mPreferencesManager.saveMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY, mAudioManager.getEffectsEnabled());
        }
    }

    /**
     * Method checks if button actions pressed and preform specific action
     */
    public void pressedButtonsActions(){

        //For loop that runs through the music control buttons and checks if  triggered
       for (PushButton button : mMusicControlButtons) {
            if(button.isPushTriggered()) {
                //passes the hash of the button with the volume value to the preform music control actions if triggered.
                preformMusicButtonActions(mMusicButtonVolumeControls.get(button));
            }
        }
        for (PushButton button : mEffectControlButtons) {
            if(button.isPushTriggered()) {
                //passes the hash of the button with the volume value to the preform effect control actions if triggered.
                preformEffectsButtonActions(mEffectButtonVolumeControls.get(button));
            }
        }
        if(mMuteMusicButton.isPushTriggered()){
            preformMuteMusicButtonActions();
        }
        if(mMuteEffectButton.isPushTriggered()){
            preformMuteEffectButtonActions();
        }
        if(mBackButton.isPushTriggered()){
            // Changed the code so that if the settings is accessed through the pause screen it returns
            // to the pause screen and if it's accessed through the main menu it returns to the main menu - Edgars
            mGame.getScreenManager().removeScreen(this);
        }
        //If text buttons pushed sound effect plays;
        if(mEffectsText.isPushTriggered()){
           if(mPreferencesManager.loadMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY,mAudioManager.getEffectsEnabled())){
               mAudioManager.play(mEffectsButtonSound);
           }
        }
        if(mMusicText.isPushTriggered()){
            if(mPreferencesManager.loadMuteEffectStatus(MUTE_EFFECT_SHAREDPREF_KEY, mAudioManager.getMusicEnabled())) {
                mAudioManager.play(mEffectsButtonSound);
            }
        }
    }

}
