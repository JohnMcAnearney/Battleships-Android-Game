package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.audio.Music;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class PauseScreen extends GameScreen {

    // Defining variables to be used for the pause screen background
    private Bitmap mPauseBackground, mPause;
    private int screenWidth=0, screenHeight=0;
    private Rect rect;

    // Defining all the buttons and a list which will store all of the buttons
    private PushButton mBackButton, mVolumeButton, mInstructionsButton, mSettingsButton, mPauseTitle;
    private List<PushButton> mButtonCollection = new ArrayList<>();

    // Defining variables related to audio
    private AudioManager audioManager = getGame().getAudioManager();
    private Music backgroundMusic;

    /*
    CONSTRUCTOR
    */
    public PauseScreen(Game game){
        super("PauseScreen", game);

        // Method which loads all the assets
        loadAssets();

        // Start to play the background music and make sure background music is playing
        playBackgroundMusicIfNotPlaying();

        // Method which creates all the buttons
        createButtons();
    }

    @Override
    public void update(ElapsedTime elapsedTime)
    {
        // Used to process any touch input within the screen
        Input input = mGame.getInput();

        // A list which stores the history of touch inputs to allow for appropriate processing
        List<TouchEvent> touchEvents = input.getTouchEvents();
        // If statement to process the possible inputs
        if (touchEvents.size() > 0)
        {
            // Update all the push buttons if a touch event is detected.
            updateAllPushButtons(elapsedTime);

            // Method which performs all the push button actions
            performButtonActions();
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        // Drawing the screen background
        drawScreenBackground(graphics2D);

        // Drawing pause text bitmap *Changed value left to 0.3 from 0.1 to push the image off the screen, a temporary thing whilst I figure out bitmap drawing*
        graphics2D.drawBitmap(mPause, null, drawRectangle(mDefaultLayerViewport.getWidth() * 0.3f, mDefaultLayerViewport.getHeight() * 0.5f,
                mDefaultLayerViewport.getWidth() * 0.5f, mDefaultLayerViewport.getHeight() /1.3f + mDefaultLayerViewport.getHeight()/ 1.5f), null);
        mBackButton.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);

        // Drawing the buttons
        drawButtons(elapsedTime, graphics2D);
    }

    /*
    METHODS
    */
    // Method which loads all the assets
    private void loadAssets()
    {
        AssetManager assetManager = mGame.getAssetManager();
        mGame.getAssetManager().loadAssets("txt/assets/PauseScreenAssets.JSON");
        mPauseBackground = assetManager.getBitmap("BattleshipBackground");
        mPause = assetManager.getBitmap("Paused");
        backgroundMusic = mGame.getAssetManager().getMusic("RickRoll");
    }

    // Method which draws the screen background
    private void drawScreenBackground(IGraphics2D graphics2D)
    {
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mPauseBackground,null,rect,null);
    }

    // Method which creates all the push buttons
    private void createButtons()
    {
        // Instruction Button
        mInstructionsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 5.5f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "InstructionsButton", "InstructionsButtonP", this);
        mButtonCollection.add(mInstructionsButton);
        // Settings Button
        mSettingsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 3f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "SettingsButton", "SettingsButtonP", this);
        mButtonCollection.add(mSettingsButton);
        // Back Button
        mBackButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.1f, mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.1f, "BackArrow", "BackArrowP", this);
        mButtonCollection.add(mBackButton);
        // Volume Button
        mVolumeButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.05f, mDefaultLayerViewport.getHeight() * 0.1f, mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.1f, "VolumeOn", this);
        mButtonCollection.add(mVolumeButton);
        // Pause Button
        mPauseTitle = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() /1.3f , mDefaultLayerViewport.getWidth() / 2f, mDefaultLayerViewport.getHeight()/ 3f, "PauseTitle", this);
        mButtonCollection.add(mPauseTitle);
    }

    // Method which draws all the buttons
    private void drawButtons(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        for(PushButton button: mButtonCollection)
        {
            button.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        }
    }

    // Method which carries out all the push button updates
    private void updateAllPushButtons(ElapsedTime elapsedTime)
    {
        for(PushButton button: mButtonCollection)
        {
            button.update(elapsedTime);
        }
    }

    // Method which executes the actions of all the push buttons
    private void performButtonActions()
    {
        // If back button is pressed, remove the current screen to return to the main menu
        if (mBackButton.isPushTriggered())
        {
            mGame.getScreenManager().removeScreen(this);
        }
        // If instructions button is triggered, add a new screen to the instruction screen.
        else if (mInstructionsButton.isPushTriggered())
        {
            mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));
        }
        // If settings button is triggered, add a new screen to the settings screen.
        else if (mSettingsButton.isPushTriggered())
        {
            mGame.getScreenManager().addScreen(new SettingsScreen(mGame));
        }
        else if(mPauseTitle.isPushTriggered())
        {
            //Maybe add some functionality, however this is just acting as a place holder until I figure out how to do the bitmap
        }
        else if(mVolumeButton.isPushTriggered())
        {
            performMuteButtonActions();
        }
    }

    // Method which gets the screen width and height of the device screen
    private void getWidthAndHeightOfScreen(IGraphics2D graphics2D)
    {
        if (screenHeight == 0 || screenWidth == 0) {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            updateRect();
        }
    }

    // Method which creates a new rectangle size of the screen
    private void updateRect()
    {
        rect = new Rect(0,0,screenWidth,screenHeight);
    }

    // Method which allows you to put in the float values of the destination rectangle
    private Rect drawRectangle(float left, float top, float right, float bottom)
    {

        Rect rectangle = new Rect((int)left, (int)top, (int)right, (int)bottom);
        return rectangle;
    }

    // Method which performs the mute button actions
    private void performMuteButtonActions(){

        if(audioManager.isMusicPlaying()){
            audioManager.stopMusic();
            mVolumeButton.setBitmap(mGame.getAssetManager().getBitmap("VolumeOff"));

        }else{
            mVolumeButton.setBitmap(mGame.getAssetManager().getBitmap("VolumeOn"));
            playBackgroundMusicIfNotPlaying();
        }
    }

    // Method which starts the music and also checks if the music is playing
    private void playBackgroundMusicIfNotPlaying()
    {
        if(!audioManager.isMusicPlaying())
        {
            audioManager.playMusic(backgroundMusic);
        }
    }
}

