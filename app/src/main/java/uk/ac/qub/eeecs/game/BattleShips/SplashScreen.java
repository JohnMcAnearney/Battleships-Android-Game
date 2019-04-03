/*
*@reference package uk.ac.qub.eeecs.ragnarok.screens;
 */
package uk.ac.qub.eeecs.game.BattleShips;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.game.BattleShips.MainMenu;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import java.util.List;

/**
 * This is a developed splash screen class
 * @author Hannah Cunningham (40201925)
 */

public class SplashScreen extends GameScreen
{
    //Variables used to define the splash screen class
    private long SPLASH_TIMEOUT = 6000;
    private long timeToCreate, currentTime;
    private GameObject background, symbol;
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;
    private Bitmap backgroundBitmap, symbolBitmap;
    private Canvas canvas;
    private Paint paint;
    private Typeface audiowide;
    private float canvasTextX, canvasTextY, alphaCount = 0;
    private AssetManager assetManager;
    private Game game;


    //Constructor for this class
    public SplashScreen(Game game)
    {
        super("SplashScreen", game);
        this.game = game;

        //this method will load the class' assets
        loadAssets();
        mScreenViewport = new ScreenViewport(0, 0, game.getScreenWidth(),
                game.getScreenHeight());
        mLayerViewport = new LayerViewport(mScreenViewport.width / 2, mScreenViewport.height / 2,
                mScreenViewport.width / 2, mScreenViewport.height / 2);

        //the timeToCreate variable is set to return the current time in milliseconds
        timeToCreate = System.currentTimeMillis();

        assetManager = mGame.getAssetManager();
        createBackground();
        createSymbol();
        createFont();
        createPaint();
        createCanvas();
    }

    //the below method will load all the assets in this class from the desired JSON files
    public void loadAssets()
    {
        AssetManager assetManager = mGame.getAssetManager();
        mGame.getAssetManager().loadAssets("txt/assets/SplashScreenAssets.JSON");
        backgroundBitmap = assetManager.getBitmap("splashBackground");
        symbolBitmap = assetManager.getBitmap("symbol");
        audiowide = assetManager.getFont("audiowide");
        paint = new Paint();
    }

    //the below method will create a new background for the game screen and is referenced from: https://stackoverflow.com/questions/30425789/convert-immutable-bitmap-file-to-mutable-bitmap
    //this particular method will convert an immutable bitmap file, such as the "splashBackground" image to scale, to a mutable bitmap
    private void createBackground()
    {
        assetManager.loadAndAddBitmap("splashBackground", "img/splashBackground.png");
        backgroundBitmap = Bitmap.createScaledBitmap(assetManager.getBitmap("splashBackground"),
                mScreenViewport.width, mScreenViewport.height, false);
        //the below demonstrates that the background bitmap will store each pixel on 4 bytes
        Bitmap backgroundBitmapMutable = backgroundBitmap.copy(Bitmap.Config.ARGB_8888, true);
        background = new GameObject(game.getScreenWidth() * 0.5f, game.getScreenHeight() * 0.5f, game.getScreenWidth(), game.getScreenHeight(), backgroundBitmapMutable, this);
    }

    //this method will load and draw the symbol bitmap from gameObject
    private void createSymbol()
    {
        assetManager.loadAndAddBitmap("symbol", "img/symbol.png");
        symbolBitmap = Bitmap.createScaledBitmap(assetManager.getBitmap("symbol"), 400, 400, false);
        symbol = new GameObject(game.getScreenWidth()* 0.5f, game.getScreenHeight() * 0.5f, symbolBitmap, this);
    }

    //this method will draw the desired font type
    private void createFont()
    {
        assetManager.loadAndAddFont("audiowide", "font/Audiowide.ttf");
        audiowide = assetManager.getFont("audiowide");
    }

    //this method will store the desired text and colour styles
    private void createPaint()
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(game.getScreenWidth() * 0.08f);
        paint.setTypeface(audiowide);
        //this sets the colour's alpha value
        paint.setAlpha(0);
    }

    /*this method will create a canvas which initially represents a blank rectangular area on the game screen of a
      given width and height which will capture and draw the background "splashBackground" image
     */
    private void createCanvas()
    {
        canvas = new Canvas(background.getBitmap());
        canvasTextX = game.getScreenWidth() * 0.26f;
        canvasTextY = game.getScreenHeight() * 0.92f;
    }

    //this set method will allow to increase the opacity
    private void increaseAlpha()
    {
        paint.setAlpha(paint.getAlpha() + 1);
    }

    //this method will display the title "Battleships" on the canvas created
    private void writeTitleOnBackground()
    {
        canvas.drawText("Battleships", canvasTextX, canvasTextY, paint);
    }

    //Update method for the Splash Screen class which will update its elapsed time information using
    //an if statement
    @Override
    public void update(ElapsedTime elapsedTime)
    {
        //this particular if statement will gradually increase the opacity over a period of time
        if (paint.getAlpha() < 100)
        {
            if(alphaCount % 3 == 0)
            {
                increaseAlpha();
                writeTitleOnBackground();
            }
            alphaCount ++;
        }

        //the below method will get the current time and check for a timeout to return to the main menu
        currentTime = System.currentTimeMillis();
        if(currentTime - timeToCreate >= SPLASH_TIMEOUT)
        {
            goToMainMenu();
        }


        //the below method will process any touch events occurring since the last update and if so, return to main menu
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0)
        {
            goToMainMenu();
        }
    }

    /**
     * the method below will remove the current game screen
     * and then change to the menu screen
     */
    public void goToMainMenu()
    {
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(new MainMenu(mGame));
    }

    /**
     * the method below will draw the splash screen
     * @param elapsedTime Elapsed time information
     * @param graphics2D Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        //the below will clear the screen and draw the background
        graphics2D.clear(Color.BLUE);
        //the below will draw the splash screen
        background.draw(elapsedTime, graphics2D, mLayerViewport, mScreenViewport);
        symbol.draw(elapsedTime, graphics2D);
    }

    //the below are the class' getters and setters
    public GameObject getBackgroundObject()
    {
        return background;
    }
    public void setBackgroundObject(GameObject backgroundObject) { this.background = backgroundObject; }

    public Bitmap getBackground()
    {
        return backgroundBitmap;
    }
    public void setBackground(Bitmap backgroundBitmap)
    {
        this.backgroundBitmap = backgroundBitmap;
    }

    public Canvas getCanvas()
    {
        return canvas;
    }
    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
    }

    public Paint getPaint()
    {
        return paint;
    }
    public void setPaint(Paint paint) { this.paint = paint; }

    public Bitmap getSymbolBitmap()
    {
        return symbolBitmap;
    }
    public void setSymbol(Bitmap symbolBitmap)
    {
        this.symbolBitmap = symbolBitmap;
    }
}
