package uk.ac.qub.eeecs.game.BattleShips;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

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

public class   SettingsScreen extends GameScreen {

    /*Created by AT: 40207942*/

    /**Define the properties for the background of the settings screen
     *
     */
    private Bitmap mSettingsBackground;
    private int screenWidth = 0, screenHeight = 0;
    private Rect rect;
    private SharedPreferences sharedPreferences;


    /**
     *Define the buttons
     * */
    private PushButton mBackButton, mIncreaseMusicButton, mDecreaseMusicButton, mIncreaseEffectButton, mDecreaseEffectButton, mMuteMusicButton, mMusicText, mEffectsText;
    private List<PushButton> mAllButtons = new ArrayList<>();
    //private Map<PushButton, String> mButtonTriggers = new HashMap<>();

    /**
     * Define the properties for the volume controls bars for the volume controls
     * */
    private UpdateBarDisplay musicBarDisplay, effectsBarDisplay;
    private AudioManager audioManager = getGame().getAudioManager();
    private Music musicOnScreen;

    /**
     * Constructor
     */
    public SettingsScreen(Game game) {
        super("SettingsScreen", game);
        loadAssets();
        playBackgroundMusic();
        createBarDisplayVolume();
        createButton();
        musicOnScreen=mGame.getAssetManager().getMusic("RickRoll");
    }

    /**
    * Method to load all of the game assets
    * */
    public void loadAssets(){
        AssetManager assetManager = mGame.getAssetManager();
        mGame.getAssetManager().loadAssets("txt/assets/SettingsScreenAssets.JSON");
        mSettingsBackground = assetManager.getBitmap("SettingBackground");
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mSettingsBackground, null, rect, null);

        //Buttons
        for(PushButton buttons: mAllButtons){
            buttons.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        }
        //DisplayBars
        musicBarDisplay.draw(elapsedTime, graphics2D);
        effectsBarDisplay.draw(elapsedTime, graphics2D);

    }


    /**
     * Method to get width and height of screen for background image
     * @param graphics2D
     */
    public void getWidthAndHeightOfScreen(IGraphics2D graphics2D) {

        if (screenHeight == 0 || screenWidth == 0) {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            updateRect();

        }

    }


    /**
     * Updates rectangle based of screen size of device
     */
    public void updateRect() {
        rect = new Rect(0, 0, screenWidth, screenHeight);
    }

    /**
     *  Creates the display bars
     */
    public void createBarDisplayVolume(){
        //numberOfBits and scale added for ease of change
        int numberOfBits=10;
        int scaleOfBar = 26;
        musicBarDisplay = new UpdateBarDisplay(numberOfBits, audioManager.getMusicVolume(), 0f, 1f, mDefaultLayerViewport.getWidth()+600.0f, (mDefaultLayerViewport.getHeight()/0.75f), scaleOfBar, this );
        effectsBarDisplay = new UpdateBarDisplay(numberOfBits,audioManager.getSfxVolume(), 0,1,mDefaultLayerViewport.getWidth()+600.0f, mDefaultLayerViewport.getHeight()/2+600, scaleOfBar, this );
    }


    /*private void constructButtons(String buttonsToConstructJSONFile, List<PushButton> buttons) {

        String loadedJSON;
        try {
            loadedJSON = mGame.getFileIO().loadJSON(buttonsToConstructJSONFile);
        } catch (IOException e) {
            throw new RuntimeException(
                    "DemoMenuScreen.constructButtons: Cannot load JSON [" + buttonsToConstructJSONFile + "]");
        }

        // Attempt to extract the JSON information
        try {
            JSONObject settings = new JSONObject(loadedJSON);
            JSONArray buttonDetails = settings.getJSONArray("pushButtons");

            // Store the game layer width and height
            float layerWidth = mDefaultLayerViewport.getWidth();
            float layerHeight = mDefaultLayerViewport.getHeight();

            // Construct each button
            for (int i = 0; i < buttonDetails.length(); i++){
                float x = (float)buttonDetails.getJSONObject(i).getDouble("x");
                float y = (float)buttonDetails.getJSONObject(i).getDouble("y");
                float width = (float)buttonDetails.getJSONObject(i).getDouble("width");
                float height = (float)buttonDetails.getJSONObject(i).getDouble("height");

                String defaultBitmap = buttonDetails.getJSONObject(i).getString("defaultBitmap");
                String pushBitmap = buttonDetails.getJSONObject(i).getString("pushBitmap");
                String triggeredGameScreen = buttonDetails.getJSONObject(i).getString("triggeredGameScreen");

                PushButton button = new PushButton(x*layerWidth, y*layerHeight,
                        width*layerWidth, height*layerHeight,
                        defaultBitmap, pushBitmap, this);
                buttons.add(button);
                mButtonTriggers.put(button, triggeredGameScreen);
            }

        } catch (JSONException | IllegalArgumentException e) {
            throw new RuntimeException(
                    "DemoMenuScreen.constructButtons: JSON parsing error [" + e.getMessage() + "]");
        }
    }
    */




    /**
     * Method to create all buttons on screen
     */
    public void createButton() {

        //Music Increase
        mIncreaseMusicButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.82f, mDefaultLayerViewport.getHeight() * 0.6f,
                35.0f, 35.0f,
                "IncreaseMusic",  this);
        mAllButtons.add(mIncreaseMusicButton);
        //Music Decrease
        mDecreaseMusicButton=new PushButton(
                mDefaultLayerViewport.getWidth() * 0.17f, mDefaultLayerViewport.getHeight() * 0.6f, 35.0f,
                35.0f, "DecreaseMusic",  this);
        mAllButtons.add(mDecreaseMusicButton);
        //Effect Increase
        mIncreaseEffectButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.82f, mDefaultLayerViewport.getHeight() * 0.3f,
                35.0f, 35.0f,
                "IncreaseMusic",  this);
        mAllButtons.add(mIncreaseEffectButton);
        //Effect Decrease
        mDecreaseEffectButton=new PushButton(
                mDefaultLayerViewport.getWidth() * 0.17f, mDefaultLayerViewport.getHeight() * 0.3f, 35.0f,
                35.0f, "DecreaseMusic",  this);
        mAllButtons.add(mDecreaseEffectButton);
        //Back Button
        mBackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "SettingsBackButton","SettingsBackButtonP",  this);
        mBackButton.setPlaySounds(true, true);
        mAllButtons.add(mBackButton);

        mEffectsText= new PushButton(mDefaultLayerViewport.getWidth()*0.5f, mDefaultLayerViewport.getHeight() /2.25f,
                mDefaultLayerViewport.getWidth() * 0.3f, mDefaultLayerViewport.getHeight() /5.0f, "EffectText", this);
        mAllButtons.add(mEffectsText);
        mMusicText = new PushButton(mDefaultLayerViewport.getWidth() *0.5f, mDefaultLayerViewport.getHeight()-80f,
                mDefaultLayerViewport.getWidth() * 0.3f, mDefaultLayerViewport.getHeight() /5.0f, "MusicText", this);
        mAllButtons.add(mMusicText);
        //Mute Button
        mMuteMusicButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.6f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f, "UnmuteButton", this);
        mAllButtons.add(mMuteMusicButton);
    }



    /**
     * Method to check if music is playing on screen and plays it if not
     */
    public void playBackgroundMusic() {
        if(!audioManager.isMusicPlaying())
            audioManager.playMusic(
                    getGame().getAssetManager().getMusic("RickRoll"));
    }


    /**
     * Update method
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if(touchEvents.size()>0){
            updateAllButtons(elapsedTime);
            pressedButtonsActions();
        }
    }


  /**
   *Updates all Buttons
   */
    public void updateAllButtons (ElapsedTime elapsedTime){
        for(PushButton button: mAllButtons){
            button.update(elapsedTime);
        }
    }


    /**
    * Method preforms music button actions
    * Sets the new volume, stops the music, plays it at the new volume and updates the bar
    * */
    public void preformMusicButtonActions(float volumeValue){
        audioManager.setMusicVolume(audioManager.getMusicVolume()+volumeValue);
        musicBarDisplay.setValue(audioManager.getMusicVolume());
        audioManager.stopMusic();
        audioManager.playMusic(musicOnScreen);
        musicBarDisplay.update();
    }


    /**
    *Changes volume of effects
    * */
    public void preformEffectsButtonActions(float volumeValue){
        audioManager.setSfxVolume(audioManager.getSfxVolume()+volumeValue);
        effectsBarDisplay.setValue(audioManager.getSfxVolume());
        effectsBarDisplay.update();
    }


    /**
     * Method checks if music is playing when button triggered
     * Then stops the music and changes the bitmap image to the mute one
     * Else sets bitmap to unmute and plays the background music
     * */
    public void preformMuteButtonActions(){

        if(audioManager.isMusicPlaying()){
            audioManager.stopMusic();
            mMuteMusicButton.setBitmap(mGame.getAssetManager().getBitmap("MuteButton"));

        }else{
            mMuteMusicButton.setBitmap(mGame.getAssetManager().getBitmap("UnmuteButton"));
            playBackgroundMusic();
        }
    }


    /**
     * Method checks if button actions pressed and preform specific action
     */
    public void pressedButtonsActions(){
        if(mIncreaseMusicButton.isPushTriggered()){
            preformMusicButtonActions(0.10f);
        }
        if(mDecreaseMusicButton.isPushTriggered()){
            preformMusicButtonActions(-0.10f);
        }
        if(mIncreaseEffectButton.isPushTriggered()){
            preformEffectsButtonActions(+0.10f);
        }
        if(mDecreaseEffectButton.isPushTriggered()){
            preformEffectsButtonActions(-0.1f);
        }
        if(mMuteMusicButton.isPushTriggered()){
            preformMuteButtonActions();
        }
        if(mBackButton.isPushTriggered()){
            changeScreen(new MainMenu(mGame));
        }
        if(mMusicText.isPushTriggered()){
            //ViewPort
        }
        if(mEffectsText.isPushTriggered()){
            //ViewPort
        }
    }

    public void changeScreen(GameScreen screen) {
        // mGame.getScreenManager().removeScreen(this.getName());
        // mGame.getScreenManager().dispose();

        mGame.getScreenManager().addScreen(screen);
    }



}
