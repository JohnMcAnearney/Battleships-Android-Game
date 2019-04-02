package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
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
    private int mScreenWidth, mScreenHeight;
    private Rect mRect;
    private Paint mPaint;

    // Defining variables related to audio
    private AudioManager mAudioManager = getGame().getAudioManager();
    private Music mBackgroundMusic;

    // Defining variables related to the loading and playing a loading animation
    private static LoadingAnimation mLoadingAnimation;
    private static AnimationSettings mAnimationSettings;

    // Defining random number generator
    private Random mRand;

     /**
     * CONSTRUCTOR - for the LoadingScreen class, which runs two methods which set up the screen
     * @param game
     */
    public LoadingScreen(Game game)
    {
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
        mLoadingAnimation.draw(graphics2D);
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
        mBackgroundMusic = mGame.getAssetManager().getMusic("RickRoll");

        // Initialising the Loading Title with appropriate bitmap
        mLoadingTitle = assetManager.getBitmap("LoadingTitle");

        // Initialising the Screen Width and Height
        mScreenWidth = 0;
        mScreenHeight = 0;

        // Initialising a blank paint
        mPaint = new Paint();

        // Loading in the spritesheet JSON file using animation settings
        mAnimationSettings = new AnimationSettings(assetManager, "txt/animation/LoadingAnimation.JSON");

        // Creating a new animation object so that the animation can be used within the class
        mLoadingAnimation = new LoadingAnimation(mAnimationSettings, 0);

        // Initialising random number generator
        mRand = new Random();
    }

    /**
     * Method which will play through the animation and will also delay the animation at a semi-random
     * time to help and to give the effect of the game loading
     * @param elapsedTime
     * @param stripIndex
     */
    private void playAnimation(ElapsedTime elapsedTime, int stripIndex)
    {
        // Line of code which will update the animation
        mLoadingAnimation.update(elapsedTime);

        // Working out 1% of the screen
        int onePercWidth = (mGame.getScreenWidth()/100);
        int onePercHeight = (mGame.getScreenHeight()/100);

        // Start to play the animation at a given location as the screen is loaded
        mLoadingAnimation.playAnimation(elapsedTime, onePercWidth*81, onePercHeight*75, onePercWidth*93, onePercHeight*99);

        /*
         * If statement which checks when the loading animation has reached the half way point minus
         * (Between 0 - 10) frames and executes a delay in loading and then checks if the animation
         * has stopped playing if it has, move to the game screen
         */
        if(mLoadingAnimation.getCurrentFrame() == ((mLoadingAnimation.getEndFrame()/2)-mRand.nextInt(10)))
        {
            delayLoading();
        }
        else if(mLoadingAnimation.getCurrentFrame() == mLoadingAnimation.getEndFrame())
        {
            moveToNewGameScreen(new BoardSetupScreen(mGame));
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
            // Working out the random delay time, either 0, 1 or 2 seconds.
            double delayTime = mRand.nextInt(3);
            delayTime /= 2;
            delay((int)Math.round(delayTime));
        }
        catch(InterruptedException e)
        {
            System.out.println();
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
     * Method which draws all the appropriate bitmaps within the screen
     * @param graphics2D
     */
    private void drawBitmaps(IGraphics2D graphics2D)
    {
        // Drawing the background image
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mLoadingBackground,null, mRect,null);

        // Working out variables which will hold, 1% of the device screen width and height to allow for easy usage
        int onePercentOfScreenHeight, onePercentOfScreenWidth;
        onePercentOfScreenHeight = graphics2D.getSurfaceHeight()/100;
        onePercentOfScreenWidth = graphics2D.getSurfaceWidth()/100;

        // Drawing the loading title bitmap
        Rect titleRectangle = new Rect(onePercentOfScreenWidth*28, onePercentOfScreenHeight, onePercentOfScreenWidth*73, onePercentOfScreenHeight*35);
        graphics2D.drawBitmap(mLoadingTitle, null, titleRectangle, mPaint);
    }

    /**
     * Method which allows you to move to a new game screen of your choice
     * @param newScreen
     */
    public void moveToNewGameScreen(GameScreen newScreen)
    {
        mGame.getScreenManager().addScreen(newScreen);
    }
}