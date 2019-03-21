package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

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
    // Defining variables to be used for the pause screen background
    private Bitmap mLoadingBackground, mLoadingTitle;
    private int screenWidth=0, screenHeight=0;
    private Rect rect;
    private Paint mPaint;

    // Defining variables related to audio
    private AudioManager audioManager = getGame().getAudioManager();
    private Music backgroundMusic;

    // Defining variables related to the loading animation
    private static LoadingAnimation loadingAnimation ;
    private static AnimationSettings animationSettings;

    /*
   CONSTRUCTOR
   */
    public LoadingScreen(Game game) {
        super("LoadingScreen", game);

        // Method which loads all the assets
        loadAssets();

        // Start to play the background music and make sure background music is playing
        playBackgroundMusicIfNotPlaying();
    }

    @Override
    public void update(ElapsedTime elapsedTime)
    {
        // Method which delays the game loading allowing for the effects of loading assets
        delayLoading();

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

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        drawBitmaps(graphics2D);
    }

    /*
    METHODS
    */
    // Method which loads all the assets
    private void loadAssets()
    {
        AssetManager assetManager = mGame.getAssetManager();
        mGame.getAssetManager().loadAssets("txt/assets/LoadingScreenAssets.JSON");
        mLoadingBackground = assetManager.getBitmap("BattleshipBackground");
        backgroundMusic = mGame.getAssetManager().getMusic("RickRoll");
        mLoadingTitle = assetManager.getBitmap("LoadingTitle");
        mPaint = new Paint();
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

    // Method which takes a integer (seconds) and then sets up an appropriate time delay
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
            delay(2);
            mGame.getScreenManager().addScreen(new BoardSetupScreen(mGame));
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

    // Method which draws all the appropriate bitmaps within the screen
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
}