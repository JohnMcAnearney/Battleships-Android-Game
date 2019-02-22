package uk.ac.qub.eeecs.game.BattleShips;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.Game;


public class DemoScreen extends GameScreen {
    //constructors
    String mKeyLabels = "1234567890abcdefghijklmnopqrstuvwxyz";
    ArrayList<Key> mKeys = new ArrayList<>();
    StringBuffer mName = new StringBuffer();

    public DemoScreen(Game game) {
        super("MainMenu", game);
        getGame().getAssetManager().loadAndAddBitmap("key", "img/key.png");
        createAndPositionKeys();
    }

    private void createAndPositionKeys() {
        final int rowLength = 10;
        final float topLeftKeyX = 60.0f, topLeftKeyY = 180.0f;
        final float keyWidth = 35.0f, keyHeight = 35.0f;
        final float keyXSpacing = 5.0f, keyYSpacing = 5.0f;

        float keyX = topLeftKeyX, keyY = topLeftKeyY;
        for (int keyIdx = 0; keyIdx < mKeyLabels.length();
             keyIdx++) {
            Key key = new Key(keyX, keyY, keyWidth, keyHeight, mKeyLabels.charAt(keyIdx), this);
            key.setmLinkedStringBuffer(mName);
            mKeys.add(key);

            if (keyIdx > 0 && (keyIdx + 1) % rowLength == 0) {
                keyY -= keyHeight + keyYSpacing;
                keyX = topLeftKeyX;
            } else keyX += keyWidth + keyXSpacing;
        }
    }

    //Methods
    @Override
    // Process any touch events occurring since the last update
    public void update(ElapsedTime elapsedTime) {
        List<TouchEvent> touchEvents = mGame.getInput().getTouchEvents();
        for (Key key : mKeys)
            key.update(elapsedTime);
    }
    private Paint textPaint = new Paint();

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.BLUE);
        for(Key key : mKeys)
            key.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        textPaint.setTextSize(50.0f);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(Typeface.MONOSPACE);

        graphics2D.drawText(mName.toString(), 100.0f, 100.0f, textPaint);
    }



    }
