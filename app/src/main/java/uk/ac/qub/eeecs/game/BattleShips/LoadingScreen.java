package uk.ac.qub.eeecs.game.BattleShips;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class LoadingScreen extends GameScreen
{

    private Bitmap mLoadingBackground;
    private int screenWidth=0, screenHeight=0;
    private Rect rect;

    public LoadingScreen(Game game)
    {
        super("LoadingScreen", game);

        // Load all the assets needed for the screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BattleshipBackground", "img/background.jpg");
        mLoadingBackground = assetManager.getBitmap("BattleshipBackground");

    }

    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime)
    {
        // try-catch to catch the InterruptedException which can occur using the delayLoading method; and a transition after a delay to the main game.
        try
        {
            delayLoading(3);
            mGame.getScreenManager().addScreen(new BoardSetupScreen(mGame));
        }
        catch(InterruptedException e)
        {
            System.out.println(e);
        }

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
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mLoadingBackground,null,rect,null);
    }

    public void getWidthAndHeightOfScreen(IGraphics2D graphics2D)
    {
        if (screenHeight == 0 || screenWidth == 0) {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            updateRect();
        }
    }

    public void updateRect()
    {
        rect = new Rect(0,0,screenWidth,screenHeight);
    }

    // Method which takes a integer (seconds) and then sets up an appropriate time delay
    public void delayLoading(int seconds) throws InterruptedException
    {
        int sleepTime = seconds*1000;
        Thread.sleep(sleepTime);
    }

}