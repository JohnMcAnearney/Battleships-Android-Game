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

/* Author : Edgars (40203154)
 * This is a pause screen class which will be accessible through the game screen, by clicking
 * a pause button; allowing the player to change their settings, return to main menu and give them
 * a quick launch option to mute the music
 */
public class PauseScreen extends GameScreen {

    // Defining variables to be used for the pause screen background
    private Bitmap mPauseBackground, mPause;
    private int screenWidth=0, screenHeight=0;
    private Rect rect;
    private Paint mPaint;

    // Defining all the buttons and a list which will store all of the buttons
    private PushButton mBackButton, mVolumeButton, mInstructionsButton, mSettingsButton;
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
            performPushButtonActions();
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        // Drawing the screen background
        drawBitmaps(graphics2D);

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
        mPaint = new Paint();
    }

    // Method which draws all the appropriate bitmaps within the screen
    private void drawBitmaps(IGraphics2D graphics2D)
    {
        // Drawing the background image
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mPauseBackground,null,rect,null);

        // Working out variables which will hold, 1% of the device screen width and height to allow for easy usage
        int onePercentOfScreenHeight, onePercentOfScreenWidth;
        onePercentOfScreenHeight = graphics2D.getSurfaceHeight()/100;
        onePercentOfScreenWidth = graphics2D.getSurfaceWidth()/100;
        // Drawing the loading title bitmap
        Rect titleRectangle = new Rect(onePercentOfScreenWidth*30, onePercentOfScreenHeight*1, onePercentOfScreenWidth*75, onePercentOfScreenHeight*35);
        graphics2D.drawBitmap(mPause, null, titleRectangle, mPaint);
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
        mBackButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.1f, mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.1f, "SettingsBackButton", "SettingsBackButtonP", this);
        mButtonCollection.add(mBackButton);
        // Volume Button
        mVolumeButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.05f, mDefaultLayerViewport.getHeight() * 0.1f, mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.1f, "VolumeOn", this);
        mButtonCollection.add(mVolumeButton);
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
    private void performPushButtonActions()
    {
        // If back button is pressed, remove the current screen to return to the main menu
        if (mBackButton.isPushTriggered())
        {
            returnToPreviousGameScreen();
        }
        // If instructions button is triggered, add a new screen to the instruction screen.
        else if (mInstructionsButton.isPushTriggered())
        {
            moveToNewGameScreen(new InstructionsScreen(mGame));
        }
        // If settings button is triggered, add a new screen to the settings screen.
        else if (mSettingsButton.isPushTriggered())
        {
            moveToNewGameScreen(new SettingsScreen(mGame));
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

    // Method which performs the mute button actions - Method taken from Aileen(40207942), adjusted method slightly to suit my class
    private void performMuteButtonActions(){

        if(audioManager.isMusicPlaying()){
            audioManager.stopMusic();
            mVolumeButton.setBitmap(mGame.getAssetManager().getBitmap("VolumeOff"));

        }else {
            mVolumeButton.setBitmap(mGame.getAssetManager().getBitmap("VolumeOn"));
            playBackgroundMusicIfNotPlaying();
        }
    }

    // Method which starts the music and also checks if the music is playing - Method taken from Aileen(40207942), adjusted method slightly to suit my class
    private void playBackgroundMusicIfNotPlaying()
    {
        if(!audioManager.isMusicPlaying())
        {
            audioManager.playMusic(backgroundMusic);
        }
    }

    // Method which allows you to move to a new game screen of your choice
    private void moveToNewGameScreen(GameScreen newScreen)
    {
        mGame.getScreenManager().addScreen(newScreen);
    }

    // Method which allows you to return to the previous game screen
    private void returnToPreviousGameScreen()
    {
        mGame.getScreenManager().removeScreen(this);
    }
}

