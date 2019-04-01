package uk.ac.qub.eeecs.game.BattleShips;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

public class    SettingsScreen extends GameScreen {

    /*Created 100% by AT: 40207942*/

    /**
     * Define the properties for the settings screen;
     */
    //Properties for background;
    private Bitmap mSettingsBackground;
    private int mScreenWidth, mScreenHeight;
    private Rect mRect;
    //Properties relating to the shared preferences and preference manager;
    private Activity mActivity;
    private PreferencesManager mPreferencesManager;

    //Properties relating to the buttons and list of buttons
    private PushButton mBackButton, mMuteMusicButton, mMuteEffectButton, mMusicText, mEffectsText;

    private List<PushButton> mAllButtons= new ArrayList<>();
    private List<PushButton>mMusicControlButtons= new ArrayList<>();
    private List<PushButton>mEffectControlButtons= new ArrayList<>();

    private Map<PushButton, Float> mMusicButtonVolumeControls = new HashMap<>();
    private Map<PushButton, Float> mEffectButtonVolumeControls = new HashMap<>();

    //Properties relating to the update display bar and audio
    private UpdateBarDisplay mMusicBarDisplay, mEffectsBarDisplay;
    private AudioManager mAudioManager;
    private Music mMusicOnScreen;
    private Sound mEffectsButtonSound;

    /**
     * Constructor
     * @param game
     */
    public SettingsScreen(Game game) {
        super("SettingsScreen", game);
        mActivity= mGame.getActivity();
        mPreferencesManager = new PreferencesManager(mActivity);
        mAudioManager = mGame.getAudioManager();
        mScreenHeight=0;
        mScreenWidth=0;
        mMusicOnScreen=mGame.getAssetManager().getMusic("RickRoll");
        mEffectsButtonSound = mGame.getAssetManager().getSound("ButtonsEffect");
        //Call the methods
        loadAssets();
        playBackgroundMusic();
        createBarDisplayVolume();
        createButton();
        }

    /**
    * Method to load all of the game assets
    * */
    public void loadAssets(){
        AssetManager assetManager = mGame.getAssetManager();
        mGame.getAssetManager().loadAssets("txt/assets/SettingsScreenAssets.JSON");
        mSettingsBackground = assetManager.getBitmap("SettingBackground");
    }

    /**
     * method to draw the instances
     * @param elapsedTime Elapsed time information for the frame
     * @param graphics2D  Graphics instance used to draw the screen
     */

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        //Background
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mSettingsBackground, null, mRect, null);

        for(PushButton buttons: mAllButtons){
            buttons.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        }
        //Display Bars
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
        mMusicBarDisplay = new UpdateBarDisplay(numberOfBits, mPreferencesManager.loadCurrentMusicVolume(mAudioManager.getMusicVolume()), 0f, 1f, mDefaultLayerViewport.getWidth()+600.0f, (mDefaultLayerViewport.getHeight()/0.75f), scaleOfBar, this );
        mEffectsBarDisplay = new UpdateBarDisplay(numberOfBits,mPreferencesManager.loadCurrentEffectsVolume(mAudioManager.getSfxVolume()), 0f,1f,mDefaultLayerViewport.getWidth()+600.0f, mDefaultLayerViewport.getHeight()/2+600, scaleOfBar, this );
    }

    /**
     * Method to construst the audio buttons
     * Reference: Lecture code sent from Phillip Hanna whihc I have developed by 40207942 to suit audio needs
     * @param buttonsToConstructJSONFile
     * @param buttons
     */
    private void constructAudioButtons(String buttonsToConstructJSONFile, List<PushButton> buttons) {
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

                button.setPlaySounds(true, true);
                mAllButtons.add(button);
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
            }
        } catch (JSONException | IllegalArgumentException e) {
            throw new RuntimeException(
                    "DemoMenuScreen.constructButtons: JSON parsing error [" + e.getMessage() + "]");
        }
    }

    /**
     * Method to create all buttons on screen and add them to the array list
     */
    public void createButton() {

        constructAudioButtons("txt/assets/SettingScreenButtons.JSON", mAllButtons);

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


        //Mute Buttons
        //Checks if the music is previously muted in the shared preferences and displays the appropiate bitmap to screen.
        if (mPreferencesManager.loadMuteMusicStatus(mAudioManager.getMusicEnabled())) {
            mMuteMusicButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.6f,
                    mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f, "UnmuteButton", this);
            mAllButtons.add(mMuteMusicButton);
        }else{
            mMuteMusicButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.6f,
                    mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f, "MuteButton", this);
            mAllButtons.add(mMuteMusicButton);
        }

        if(mPreferencesManager.loadMuteEffectStatus(mAudioManager.getEffectsEnabled())){
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
    public void playBackgroundMusic() {
        if(mPreferencesManager.loadMuteMusicStatus(mAudioManager.getMusicEnabled()==true)) {
            mAudioManager.setMusicVolume(mPreferencesManager.loadCurrentMusicVolume(mAudioManager.getMusicVolume()));
            mAudioManager.playMusic(
                    getGame().getAssetManager().getMusic("RickRoll"));
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
            }
                pressedButtonsActions();
        }
    }


    /**
     * Method preforms music button actions
     * Sets the new volume, stops the music, plays it at the new volume and updates the bar
     * Method has been refactor to do the job of increasing or decreasing the current volume instead of two seperate methods;
     * @param volumeValue
     */

    public void preformMusicButtonActions( float volumeValue){
        if(mPreferencesManager.loadMuteMusicStatus(mAudioManager.getMusicEnabled())) {
            //Gets the current volume of music
            float currentMusic = mPreferencesManager.loadCurrentMusicVolume(mAudioManager.getMusicVolume());
            //Increases/Decrease by the volumeValue
            mAudioManager.setMusicVolume(currentMusic + volumeValue);
            //Updates the music display bar
            mMusicBarDisplay.setValue(mAudioManager.getMusicVolume());
            mMusicBarDisplay.update();
            //Stops the music at old volume and plays it again at new volume.
            mAudioManager.stopMusic();
            mAudioManager.playMusic(mMusicOnScreen);
            //Saves the new volume of the screen on the shared preferences;
            mPreferencesManager.saveCurrentMusicVolume(mAudioManager.getMusicVolume());
        }
    }


    /**
     * Method preforms effect button actions
     * Sets the new volume, stops the music, plays it at the new volume and updates the bar
     * Method has been refactor to do the job of increasing or decreasing the current volume instead of two seperate methods;
     * @param volumeValue
     */
    public void preformEffectsButtonActions(float volumeValue){
        if(mPreferencesManager.loadMuteEffectStatus(mAudioManager.getEffectsEnabled())) {
            //Gets the current volume of effects
            float currentEffect = mPreferencesManager.loadCurrentEffectsVolume(mAudioManager.getSfxVolume());
            //Increases/Decrease by the volumeValue.
            mAudioManager.setSfxVolume(currentEffect+volumeValue);
            //Update the effects display bar
            mEffectsBarDisplay.setValue(mAudioManager.getSfxVolume());
            mEffectsBarDisplay.update();
            //Plays sound to demonstrate its working
            mAudioManager.play(mEffectsButtonSound);
            //Saves the new volume of the effects on the shared preferences;
            mPreferencesManager.saveCurrentEffectVolume(mAudioManager.getSfxVolume());
        }
    }

    /**
     * Method checks if music is playing when button triggered
     * Then stops the music and changes the bitmap image to the mute one
     * Else sets bitmap to unmute and plays the background music
     * */

    public void preformMuteMusicButtonActions(){
        boolean isMusicMuted = mPreferencesManager.loadMuteMusicStatus(mAudioManager.getMusicEnabled());
        if(isMusicMuted==true){
            mAudioManager.setMusicEnabled(false);
            mPreferencesManager.saveMuteMusicStatus(mAudioManager.getMusicEnabled());
            mMuteMusicButton.setBitmap(mGame.getAssetManager().getBitmap("MuteButton"));
            mAudioManager.stopMusic();
        }else {
            mMuteMusicButton.setBitmap(mGame.getAssetManager().getBitmap("UnmuteButton"));
            mAudioManager.setMusicEnabled(true);
            mPreferencesManager.saveMuteMusicStatus(mAudioManager.getMusicEnabled());
            playBackgroundMusic();
        }
    }

    /**
     * Method to mute sfx
     */
    public void preformMuteEffectButtonActions(){
        boolean isEffectsMuted = mPreferencesManager.loadMuteEffectStatus(mAudioManager.getEffectsEnabled());
        if(isEffectsMuted==true){
            mAudioManager.setEffectEnabled(false);
            mPreferencesManager.saveMuteEffectStatus(mAudioManager.getEffectsEnabled());
            mMuteEffectButton.setBitmap(mGame.getAssetManager().getBitmap("MuteButton"));
        } else{
            mMuteEffectButton.setBitmap(mGame.getAssetManager().getBitmap("UnmuteButton"));
            mAudioManager.setEffectEnabled(true);
            mPreferencesManager.saveMuteEffectStatus(mAudioManager.getEffectsEnabled());
        }
    }

    /**
     * Method checks if button actions pressed and preform specific action
     */
    public void pressedButtonsActions(){
       for (PushButton button : mMusicControlButtons) {
            if(button.isPushTriggered()) {
                preformMusicButtonActions(mMusicButtonVolumeControls.get(button));
            }
        }
        for (PushButton button : mEffectControlButtons) {
            if(button.isPushTriggered()) {
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
        if(mEffectsText.isPushTriggered()){
           mAudioManager.play(mEffectsButtonSound);
        }
        if(mMusicText.isPushTriggered()){
            mAudioManager.play(mEffectsButtonSound);
        }
    }

}
