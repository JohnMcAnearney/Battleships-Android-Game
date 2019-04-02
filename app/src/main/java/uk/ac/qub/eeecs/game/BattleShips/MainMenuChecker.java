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
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

/* Author : Edgars (40203154)
 * This is a Main Menu checker class, which will appear if the user selects to return to the main
 * menu from the pause screen. This screen will prompt the user with the consequences if they do
 * decide to return to the main menu and plays a sound if they do.
 */
public class MainMenuChecker extends GameScreen
{
    // Defining variables to be used for the main menu checker background
    private Bitmap mMainMenuCheckerBackground, mMainMenuCheckerTitle;
    private int mScreenWidth, mScreenHeight;
    private Rect mRect;
    private Paint mPaint;

    // Defining all the buttons and a list which will store all of the buttons
    private PushButton mYesButton, mNoButton;
    private List<PushButton> mButtonCollection = new ArrayList<>();

    // Defining variables related to audio
    private AudioManager mAudioManager = getGame().getAudioManager();
    private Music mBackgroundMusic;
    private Sound mYesButtonSound;

    /**
     * CONSTRUCTOR - for the MainMenuChecker class, which runs three methods which set up the screen
     * @param game
     */
    public MainMenuChecker(Game game) {
        super("MainMenuChecker", game);

        // Method which loads all the assets
        loadAssets();

        // Start to play the background music and make sure background music is playing
        playBackgroundMusicIfNotPlaying();

        // Method which creates all the buttons
        createButtons();
    }

    /**
     * Update method for the MainMenuChecker class
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
     * Draw method for the MainMenuChecker class
     * @param elapsedTime
     * @param graphics2D
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        // Method which draws all the bitmaps within the screen
        drawBitmaps(graphics2D);

        // Drawing the buttons
        drawButtons(elapsedTime, graphics2D);
    }

    //----METHODS----

    // Method which loads all the assets
    private void loadAssets()
    {
        // Initialising the asset manager
        AssetManager assetManager = mGame.getAssetManager();

        // Loading in the JSON file
        mGame.getAssetManager().loadAssets("txt/assets/MainMenuCheckerAssets.JSON");

        // Initialising the Loading Background with an appropriate bitmap
        mMainMenuCheckerBackground = assetManager.getBitmap("BattleshipBackground");

        // Initialising the Loading Title with appropriate bitmap
        mMainMenuCheckerTitle = assetManager.getBitmap("MainMenuCheckerTitle");

        // Initialising the Background Music with music file
        mBackgroundMusic = mGame.getAssetManager().getMusic("RickRoll");

        // Initialising the Yes Button sound
        mYesButtonSound = assetManager.getSound("YesButtonSound");

        // Initialising the Screen Width and Height
        mScreenWidth = 0;
        mScreenHeight = 0;

        // Initialising a blank paint
        mPaint = new Paint();
    }

    // Method which creates all the push buttons
    private void createButtons()
    {
        // Initialising the width and height of the layer viewport
        float viewportWidth = mDefaultLayerViewport.getWidth();
        float viewportHeight = mDefaultLayerViewport.getHeight();

        // Yes Button
        mYesButton = new PushButton(viewportWidth / 2,viewportHeight  / 2, viewportWidth / 4.2f, viewportHeight / 9,
                "YesButton", "YesButtonP", this);
        mButtonCollection.add(mYesButton);

        // No Button
        mNoButton = new PushButton(viewportWidth / 2, viewportHeight / 3f, viewportWidth / 4.2f, viewportHeight / 9,
                "NoButton", "NoButtonP", this);
        mButtonCollection.add(mNoButton);
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
        // If the yes button is triggered, remove all current screens and move to the main menu
        if (mYesButton.isPushTriggered())
        {
            // Method which will play the yes button sound
            playButtonSound(mYesButtonSound);

            // Removing all current screens and adding a new MainMenu screen
            mGame.getScreenManager().removeAllScreens();
            moveToNewGameScreen(new MainMenu(mGame));
        }
        // If the no button is triggered, return to the pause screen
        else if (mNoButton.isPushTriggered())
        {
            returnToPreviousGameScreen();
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
        mRect = new Rect(0,0,mScreenWidth,mScreenHeight);
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
     * Method which draws all the appropriate bitmaps within the screen
     * @param graphics2D
     */
    private void drawBitmaps(IGraphics2D graphics2D)
    {
        // Drawing the background image
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mMainMenuCheckerBackground,null, mRect,null);

        // Working out variables which will hold, 1% of the device screen width and height to allow for easy usage
        int onePercentOfScreenHeight, onePercentOfScreenWidth;
        onePercentOfScreenHeight = graphics2D.getSurfaceHeight()/100;
        onePercentOfScreenWidth = graphics2D.getSurfaceWidth()/100;

        // Drawing the loading title bitmap
        Rect titleRectangle = new Rect(onePercentOfScreenWidth, onePercentOfScreenHeight, onePercentOfScreenWidth*100, onePercentOfScreenHeight*35);
        graphics2D.drawBitmap(mMainMenuCheckerTitle, null, titleRectangle, mPaint);
    }

    /**
     * Method which allows you to move to a new game screen of your choice
     * @param newScreen
     */
    public void moveToNewGameScreen(GameScreen newScreen)
    {
        mGame.getScreenManager().addScreen(newScreen);
    }

    // Method which allows you to return to the previous game screen
    public void returnToPreviousGameScreen()
    {
        mGame.getScreenManager().removeScreen(this);
    }

    /**
     * Method which takes in a sound file and then plays the button sound
     * @param sound
     */
    public Sound playButtonSound(Sound sound)
    {
        if (mAudioManager.getEffectsEnabled())
        {
            mAudioManager.play(sound);
        }

        return sound;
    }
}