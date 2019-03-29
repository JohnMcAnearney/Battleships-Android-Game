package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationSettings;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.audio.Music;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;

/* Author : Edgars (40203154)
 * This is a loading screen class, which will appear before the game begins to simulate actual loading
 * within the game, it will display a 'Loading' bitmap and a animation which will help as a indication
 * that a loading process is happening.
 */
public class LoadingScreen extends GameScreen
{
    // Defining variables to be used for the loading screen background
    private Bitmap mLoadingBackground, mLoadingTitle;
    private int screenWidth=0, screenHeight=0;
    private Rect rect;
    private Paint mPaint;
    private boolean animationPlayed;

    // Defining variables related to audio
    private AudioManager audioManager = getGame().getAudioManager();
    private Music backgroundMusic;

    // Defining variables related to the loading and playing a loading animation
    private static LoadingAnimation loadingAnimation;
    private static AnimationSettings animationSettings;

     /**
     * CONSTRUCTOR - for the LoadingScreen class, which runs two methods which set up the screen
     * @param game
     */
    public LoadingScreen(Game game) {
        super("LoadingScreen", game);

        // Method which loads all the assets
        loadAssets();

        // Start to play the background music and make sure background music is playing
        playBackgroundMusicIfNotPlaying();
    }

    /**
     * Update method for the LoadingScreen class
     * @param elapsedTime
     */
    @Override
    public void update(ElapsedTime elapsedTime)
    {
        // Method which plays the animation
        playAnimation(elapsedTime, 0);

        // Method which delays the game loading allowing for the effects of loading assets

        // Used to process any touch input within the screen
        Input input = mGame.getInput();

        // A list which stores the history of touch inputs to allow for appropriate processing
        List<TouchEvent> touchEvents = input.getTouchEvents();

        // If statement to process the possible inputs
        if (touchEvents.size() > 0)
        {
            // Leaving code as may be used later.
        }
    }

    /**
     * Draw method for the LoadingScreen class
     * @param elapsedTime
     * @param graphics2D
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        // Method which draws all the bitmaps within the screen
        drawBitmaps(graphics2D);

        // Drawing the loading animation
        loadingAnimation.draw(graphics2D);
    }

    //----METHODS----

    // Method which loads all the assets
    private void loadAssets()
    {
        // Initialising the asset manager
        AssetManager assetManager = mGame.getAssetManager();

        // Loading in the JSON file
        mGame.getAssetManager().loadAssets("txt/assets/LoadingScreenAssets.JSON");

        // Initialising the Loading Background with an appropriate bitmap
        mLoadingBackground = assetManager.getBitmap("BattleshipBackground");

        // Initialising the Background Music with music file
        backgroundMusic = mGame.getAssetManager().getMusic("RickRoll");

        // Initialising the Loading Title with appropriate bitmap
        mLoadingTitle = assetManager.getBitmap("LoadingTitle");

        // Initialising a blank paint
        mPaint = new Paint();

        // Loading in the Image Strip JSON file using animation settings
        animationSettings = new AnimationSettings(assetManager, "txt/animation/LoadingAnimation.JSON");

        // Creating a new animation object so that the animation can be used within the class
        loadingAnimation = new LoadingAnimation(animationSettings, 0);

        /**
         * Creating animationSettings object which will load the JSON file and the image sprite sheet
         * to be used for an explosion animation
         */
        animationSettings = new AnimationSettings(assetManager,"txt/animation/ExplosionAnimation.JSON");

        // Initialising the boolean value to false, as animation has not played yet
        animationPlayed = false;
    }

    /**
     * Method which gets the screen width and height of the device screen
     * @param graphics2D
     */
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

    /**
     * Method which takes a integer (seconds) and then sets up an appropriate time delay
     * @param seconds
     */
    private void delay(int seconds) throws InterruptedException
    {
        int sleepTime = seconds*1000;
        Thread.sleep(sleepTime);
    }

    // Method which applies the delay method, this is done to allow for more control over the delay method if needed
    private void delayLoading()
    {
        try
        {
            delay(4);
        }
        catch(InterruptedException e)
        {
            System.out.println();
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

    /**
     * Method which draws all the appropriate bitmaps within the screen
     * @param graphics2D
     */
    private void drawBitmaps(IGraphics2D graphics2D)
    {
        // Drawing the background image
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mLoadingBackground,null,rect,null);

        // Working out variables which will hold, 1% of the device screen width and height to allow for easy usage
        int onePercentOfScreenHeight, onePercentOfScreenWidth;
        onePercentOfScreenHeight = graphics2D.getSurfaceHeight()/100;
        onePercentOfScreenWidth = graphics2D.getSurfaceWidth()/100;
        // Drawing the loading title bitmap
        Rect titleRectangle = new Rect(onePercentOfScreenWidth*28, onePercentOfScreenHeight*1, onePercentOfScreenWidth*73, onePercentOfScreenHeight*35);
        graphics2D.drawBitmap(mLoadingTitle, null, titleRectangle, mPaint);
    }

    /**
     * Method which allows you to move to a new game screen of your choice
     * @param newScreen
     */
    private void moveToNewGameScreen(GameScreen newScreen)
    {
        mGame.getScreenManager().addScreen(newScreen);
    }

    // Method which will check if the animation has played through
    private void playAnimation(ElapsedTime elapsedTime, int stripIndex)
    {
        // Line of code which will update the animation
        loadingAnimation.update(elapsedTime);

        // Working out 1% of the screen
        int onePercWidth = (mGame.getScreenWidth()/100);
        int onePercHeight = (mGame.getScreenHeight()/100);
        // Start to play the animation at a given location as the screen is loaded
        loadingAnimation.playAnimation(elapsedTime, onePercWidth*81, onePercHeight*75, onePercWidth*93, onePercHeight*99);

        // If statement which checks if the animation has stopped playing if it has, move to the game screen
        if(loadingAnimation.getCurrentFrame() == loadingAnimation.getEndFrame())
        {
            moveToNewGameScreen(new BoardSetupScreen(mGame));
        }
    }
}