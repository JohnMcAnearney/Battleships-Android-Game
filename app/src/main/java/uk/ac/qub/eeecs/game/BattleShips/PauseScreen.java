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
public class PauseScreen extends GameScreen
{
    // Defining variables to be used for the pause screen background
    private Bitmap mPauseBackground, mPause;
    private int mScreenWidth, mScreenHeight;
    private Rect mRect;
    private Paint mPaint;

    // Defining all the buttons and a list which will store all of the buttons
    private PushButton mBackButton, mVolumeButton, mInstructionsButton, mSettingsButton, mMainMenu;
    private List<PushButton> mButtonCollection = new ArrayList<>();

    // Defining variables related to audio
    private AudioManager mAudioManager = getGame().getAudioManager();
    private Music mBackgroundMusic;

    /**
     * CONSTRUCTOR - for the PauseScreen class, which runs three methods which set up the screen
     * @param game
    */
    protected PauseScreen(Game game){
        super("PauseScreen", game);

        // Method which loads all the assets
        loadAssets();

        // Start to play the background music and make sure background music is playing
        playBackgroundMusicIfNotPlaying();

        // Method which creates all the buttons
        createButtons();
    }

    /**
     * Update method for the PauseScreen class
     * @param elapsedTime
     */
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

    /**
     * Draw method for the PauseScreen class
     * @param elapsedTime
     * @param graphics2D
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        // Drawing the screen background
        drawBitmaps(graphics2D);

        // Drawing the buttons
        drawButtons(elapsedTime, graphics2D);
    }

    // ----METHODS----

    // Method which loads all the assets
    private void loadAssets()
    {
        // Initialising the asset manager
        AssetManager assetManager = mGame.getAssetManager();

        // Loading in the JSON file
        mGame.getAssetManager().loadAssets("txt/assets/PauseScreenAssets.JSON");

        // Initialising the Pause Background with an appropriate bitmap
        mPauseBackground = assetManager.getBitmap("BattleshipBackground");

        // Initialising the Pause Title with an appropriate bitmap
        mPause = assetManager.getBitmap("Paused");

        // Initialising the Background Music with music file
        mBackgroundMusic = mGame.getAssetManager().getMusic("RickRoll");

        // Initialising a blank paint
        mPaint = new Paint();

        // Initialising the Screen Width and Height
        mScreenWidth = 0;
        mScreenHeight = 0;
    }

    /**
     * Method which draws all the appropriate bitmaps within the screen
     * @param graphics2D
     */
    private void drawBitmaps(IGraphics2D graphics2D)
    {
        // Drawing the background image
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mPauseBackground,null, mRect,null);

        // Working out variables which will hold, 1% of the device screen width and height to allow for easy usage
        int onePercentOfScreenHeight, onePercentOfScreenWidth;
        onePercentOfScreenHeight = graphics2D.getSurfaceHeight()/100;
        onePercentOfScreenWidth = graphics2D.getSurfaceWidth()/100;

        // Drawing the loading title bitmap
        Rect titleRectangle = new Rect(onePercentOfScreenWidth*30, onePercentOfScreenHeight, onePercentOfScreenWidth*75, onePercentOfScreenHeight*35);
        graphics2D.drawBitmap(mPause, null, titleRectangle, mPaint);
    }

    // Method which creates all the push buttons
    private void createButtons()
    {
        // Initialising the width and height of the layer viewport
        float viewportWidth = mDefaultLayerViewport.getWidth();
        float viewportHeight = mDefaultLayerViewport.getHeight();

        // Main Menu Button
        mMainMenu = new PushButton(viewportWidth / 2,viewportHeight  / 2, viewportWidth / 4, viewportHeight / 8,
                "NewGameButton", "NewGameButtonP", this);
        mButtonCollection.add(mMainMenu);

        // Instruction Button
        mInstructionsButton = new PushButton(viewportWidth / 2, viewportHeight / 5.5f, viewportWidth / 4, viewportHeight / 8,
                "InstructionsButton", "InstructionsButtonP", this);
        mButtonCollection.add(mInstructionsButton);

        // Settings Button
        mSettingsButton = new PushButton(viewportWidth / 2, viewportHeight / 3f, viewportWidth / 4, viewportHeight / 8,
                "SettingsButton", "SettingsButtonP", this);
        mButtonCollection.add(mSettingsButton);

        // Back Button
        mBackButton = new PushButton(viewportWidth * 0.95f, viewportHeight * 0.1f, viewportWidth * 0.075f, viewportHeight * 0.1f,
                "SettingsBackButton", "SettingsBackButtonP", this);
        mButtonCollection.add(mBackButton);

        // Volume Button
        mVolumeButton = new PushButton(viewportWidth * 0.05f, viewportHeight * 0.1f, viewportWidth * 0.075f, viewportHeight * 0.1f,
                "VolumeOn", this);
        mButtonCollection.add(mVolumeButton);

    }

    /**
     * Method which draws all the push buttons using a for loop to cycle through all of the buttons
     * in the collection and applying the draw() method
     * @param elapsedTime
     * @param graphics2D
     */
    private void drawButtons(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        for(PushButton button: mButtonCollection)
        {
            button.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        }
    }

    /**
     * Method which carries out all the push button updates, this is done by running through a for
     * loop and calling the update() method on each button
     * @param elapsedTime
     */
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
        // If volume button is triggered, mute sound and change bitmap
        else if(mVolumeButton.isPushTriggered())
        {
            performMuteButtonActions();
        }
        // If the main menu button is triggered, return to the main menu and delete game screen
        else if(mMainMenu.isPushTriggered())
        {
            mGame.getScreenManager().removeAllScreens();
            moveToNewGameScreen(new MainMenu(mGame));
        }
    }

    /**
     * Method which gets the screen width and height of the device screen
     * @param graphics2D
     */
    private void getWidthAndHeightOfScreen(IGraphics2D graphics2D)
    {
        if (mScreenHeight == 0 || mScreenWidth == 0) {
            mScreenWidth = graphics2D.getSurfaceWidth();
            mScreenHeight = graphics2D.getSurfaceHeight();
            updateRect();
        }
    }

    // Method which creates a new rectangle size of the screen
    private void updateRect()
    {
        mRect = new Rect(0,0, mScreenWidth, mScreenHeight);
    }

    /*
     * Method which performs the mute button actions - Method taken from Aileen(40207942),
     * adjusted method slightly to suit my class
     */
    private void performMuteButtonActions(){

        if(mAudioManager.isMusicPlaying()){
            mAudioManager.stopMusic();
            mVolumeButton.setBitmap(mGame.getAssetManager().getBitmap("VolumeOff"));

        }else {
            mVolumeButton.setBitmap(mGame.getAssetManager().getBitmap("VolumeOn"));
            playBackgroundMusicIfNotPlaying();
        }
    }

    /*
     * Method which starts the music and also checks if the music is playing - Method taken from
     * Aileen(40207942), adjusted method slightly to suit my class
     */
    private void playBackgroundMusicIfNotPlaying()
    {
        if(!mAudioManager.isMusicPlaying())
        {
            mAudioManager.playMusic(mBackgroundMusic);
        }
    }

    /**
     * Method which allows you to move to a new game screen of your choice
     * @param newScreen
     */
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

