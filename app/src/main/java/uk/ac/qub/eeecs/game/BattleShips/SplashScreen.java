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
import uk.ac.qub.eeecs.game.MenuScreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.List;

public class SplashScreen extends GameScreen
{
    final private long SPLASH_TIMEOUT = 5000;
    private long timeOnCreate, currentTime;
    private GameObject background, symbol;
    private AssetManager assetManager;
    private Game game;
    private ScreenViewport mScreenViewport;
    private LayerViewport mLayerViewport;
    private Bitmap backgroundBitmap, symbolBitmap;
    private Canvas canvas;
    private Paint paint;
    private Typeface regularUnderworld;
    private float canvasTextX, canvasTextY, alphaCount = 0;


    private SplashScreen(Game game) {
        super("SplashScreen", game);
        this.game = game;


        mScreenViewport = new ScreenViewport(0, 0, game.getScreenWidth(),
                game.getScreenHeight());

        mLayerViewport = new LayerViewport(mScreenViewport.width / 2, mScreenViewport.height / 2,
                mScreenViewport.width / 2, mScreenViewport.height / 2);

        timeOnCreate = System.currentTimeMillis();
        assetManager = mGame.getAssetManager();
        createBackground();
        createSymbol();
        createFont();
        createPaint();
        createCanvas();
    }


    public void createBackground() {
        assetManager.loadAndAddBitmap("splashBackground", "img/splashBackground.png");
        backgroundBitmap = Bitmap.createScaledBitmap(assetManager.getBitmap("splashBackground"),
                mScreenViewport.width, mScreenViewport.height, false);
        Bitmap backgroundBitmapMutable = backgroundBitmap.copy(Bitmap.Config.ARGB_8888, true);
        background = new GameObject(game.getScreenWidth() * 0.5f, game.getScreenHeight() * 0.5f, game.getScreenWidth(), game.getScreenHeight(), backgroundBitmapMutable, this);
    }

    public void createSymbol()
    {
        assetManager.loadAndAddBitmap("symbol", "img/symbol.png");
        symbolBitmap = Bitmap.createScaledBitmap(assetManager.getBitmap("symbol"), 400, 400, false);
        symbol = new GameObject(game.getScreenWidth()* 0.5f, game.getScreenHeight() * 0.5f, symbolBitmap, this);
    }

    public void createFont()
    {
        assetManager.loadAndAddFont("regularUnderworld", "fonts/underworld.ttf");
        regularUnderworld = assetManager.getFont("regularUnderworld");
    }

    public void createPaint()
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(game.getScreenWidth() * 0.08f);
        paint.setTypeface(regularUnderworld);
        paint.setAlpha(0);
    }
    public void createCanvas()
    {
        canvas = new Canvas(background.getBitmap());
        canvasTextX = game.getScreenWidth() * 0.26f;
        canvasTextY = game.getScreenHeight() * 0.92f;
    }

    private void increaseAlpha()
    {
        paint.setAlpha(paint.getAlpha() + 1);
    }

    public void writeTitleOnBackground()
    {
        canvas.drawText("Battleships", canvasTextX, canvasTextY, paint);
    }

    /**
     * following method will update the splash screen
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime)
    {
        //increase of opacity over a period of time
        if (paint.getAlpha() < 100)
        {
            if(alphaCount % 3 == 0)
            {
                increaseAlpha();
                writeTitleOnBackground();
            }
            alphaCount ++;
        }

        //the below method will get the current time and check for a timeout
        currentTime = System.currentTimeMillis();
        if(currentTime - timeOnCreate >= SPLASH_TIMEOUT)
        {
            goToMenuScreen();
        }

        //the below method will process any touch events occurring since the last update
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0)
        {
            goToMenuScreen();
        }
    }
    /**
     * the method below will remove the current game screen
     * and then change to the menu screen
     */
    public void goToMenuScreen()
    {
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(new MenuScreen(mGame));
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
    public void setBackgroundObject(GameObject backgroundObject)
    {
        this.background = backgroundObject;
    }

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
    public void setPaint(Paint paint)
    {
        this.paint = paint;
    }
    public Bitmap getSymbolBitmap()
    {
        return symbolBitmap;
    }
    public void setSymbol(Bitmap symbolBitmap)
    {
        this.symbolBitmap = symbolBitmap;
    }

}
