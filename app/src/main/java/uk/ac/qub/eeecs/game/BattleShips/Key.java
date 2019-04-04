package uk.ac.qub.eeecs.game.BattleShips;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

/*
 * This is a developed key class
 * which is extended from the PushButton class to aid with textual input functionality
 * @author Hannah Cunningham (40201925)
 * @reference: lecture example
 */

public class Key extends PushButton
{
    //variables for this class is as shown below
    private String mKey;
    private Paint mKeyPaint;
    private StringBuffer mLinkedStringBuffer = null;

    //constructors can be found below which consists of the width and height of each key
    public Key(float x, float y, float width, float height, char Key, GameScreen gameScreen)
    {
        super(x, y, width, height, "Key", gameScreen);
        processInLayerSpace(true);

        float fontSize = ViewportHelper.convertXDistanceFromLayerToScreen(height,
                gameScreen.getDefaultLayerViewport(),gameScreen.getDefaultScreenViewport());

        mKey = String.valueOf(Key);
        mKeyPaint = new Paint();
        mKeyPaint.setTextSize(fontSize);
        mKeyPaint.setTextAlign(Paint.Align.CENTER);
        mKeyPaint.setColor(Color.BLUE);
        mKeyPaint.setTypeface(Typeface.MONOSPACE);
    }

    public void setmLinkedStringBuffer(StringBuffer linkedStringBuffer)
    {
        mLinkedStringBuffer = linkedStringBuffer;
    }

    @Override
    public void update(ElapsedTime elapsedTime)
    {
        super.update(elapsedTime);
        if (mLinkedStringBuffer != null && isPushTriggered())
        {
            mLinkedStringBuffer.append(mKey);
        }
    }

    private Rect textBounds = new Rect();
    private Vector2 screenPos = new Vector2();

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport,
                     ScreenViewport screenViewport)
    {
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        ViewportHelper.convertLayerPosIntoScreen(layerViewport, mBound.x, mBound.y, screenViewport, screenPos);
        //the below code will align the character into the middle of the y-axis
        mKeyPaint.getTextBounds(mKey, 0, mKey.length(), textBounds);
        screenPos.y -= textBounds.exactCenterY();

        graphics2D.drawText(mKey, screenPos.x, screenPos.y, mKeyPaint);
    }
}
