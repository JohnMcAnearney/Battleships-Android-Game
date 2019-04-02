package uk.ac.qub.eeecs.game.BattleShips;
//based on lecture example

import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.Game;

/**
 * Demo Screen which extends from GameScreen to aid with key class for textual input
 * @author Hannah Cunningham (40201925)
 */

public class DemoScreen extends GameScreen
{
    //Variables used to define the demo screen class
    private String mKeyLabels = "1234567890abcdefghijklmnopqrstuvwxyz";
    private ArrayList<Key> mKeys = new ArrayList<>();
    private StringBuffer mName = new StringBuffer();
    private Rect rect;
    private int screenWidth, screenHeight = 0;


    //Constructor
    public DemoScreen(Game game)
    {
        super("MainMenu", game);
        getGame().getAssetManager().loadAndAddBitmap("key", "img/key.png");
        createAndPositionKeys();
    }

    /*the below method creates and positions the keys with a
    *row length of 10 with assigned magic numbers for spacing etc
    */
    private void createAndPositionKeys()
    {
        final int rowLength = 10;
        final float topLeftKeyX = 60.0f, topLeftKeyY = 180.0f;
        final float keyWidth = 35.0f, keyHeight = 35.0f;
        final float keyXSpacing = 5.0f, keyYSpacing = 5.0f;

        float keyX = topLeftKeyX, keyY = topLeftKeyY;
        for (int keyIdx = 0; keyIdx < mKeyLabels.length();
             keyIdx++)
        {
            Key key = new Key(keyX, keyY, keyWidth, keyHeight, mKeyLabels.charAt(keyIdx), this);
            key.setmLinkedStringBuffer(mName);
            mKeys.add(key);

            if (keyIdx > 0 && (keyIdx + 1) % rowLength == 0)
            {
                keyY -= keyHeight + keyYSpacing;
                keyX = topLeftKeyX;
            } else keyX += keyWidth + keyXSpacing;
        }
    }

    //the below method will capture the width and height of the game screen
    private void getWidthAndHeightOfScreen(IGraphics2D graphics2D)
    {
        if (screenHeight == 0 || screenWidth == 0)
        {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            updateRect();
        }
    }

    //the below method will create a new rectangular size of the game screen
    private void updateRect()
    {
        rect = new Rect(0, 0, screenWidth, screenHeight);
    }

    //Methods
    @Override
    // Process any touch events occurring since the last update
    public void update(ElapsedTime elapsedTime)
    {
        //this method is used to process any touch inputs
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0)
        {
            for (Key key : mKeys)
                key.update(elapsedTime);
        }
    }

    private Paint textPaint = new Paint();

    //this method will override the draw method in the gameScreen class to desired
    //specifications for each key in the key class,
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        graphics2D.clear(Color.BLUE);
        for(Key key : mKeys)
            key.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        textPaint.setTextSize(50.0f);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(Typeface.MONOSPACE);

        graphics2D.drawText(mName.toString(), 100.0f, 100.0f, textPaint);
    }
}
