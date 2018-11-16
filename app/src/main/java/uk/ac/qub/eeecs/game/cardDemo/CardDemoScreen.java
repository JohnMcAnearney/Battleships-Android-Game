package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.io.IOException;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.world.GameScreen;

/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class CardDemoScreen extends GameScreen {
    //blankCard bitmap variable
    private Card mCard;
    private Paint textPaint = new Paint();
    private final float LEVEL_WIDTH = (1000.0F)*2;
    private final float LEVEL_HEIGHT = (1000.0F)*2;
    public boolean hasLeft=false;
    /**
     * Create the Card game screen
     *
     * @param game Game to which this screen belongs
     */
    public CardDemoScreen(Game game) {
        super("CardScreen", game);
        //Initialising a card object within the cardDemoScreen so that it can be drawn by the draw method.
        mCard = new Card(400,200,this);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Update Methods
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        Input input = mGame.getInput();

        // Store acceleration information for the device
        mCard.mAcceleration[0] = input.getAccelX();
        mCard.mAcceleration[1] = input.getAccelY();
        mCard.mAcceleration[2] = input.getAccelZ();

        // Store touch point information.
        for (int pointerId = 0; pointerId < mCard.mTouchIdExists.length; pointerId++) {
            mCard.mTouchIdExists[pointerId] = input.existsTouch(pointerId);
            if (mCard.mTouchIdExists[pointerId]) {
                mCard.mTouchLocation[pointerId][0] = input.getTouchX(0);
                mCard.mTouchLocation[pointerId][1] = input.getTouchY(0);
            }
        }
        //MotionEvent.ACTION_MOVE;    //probably implement something like this

        //Matrix m = new Matrix();
        for(int pointerId = 0; pointerId < mCard.mTouchIdExists.length; pointerId++) {
            mCard.mTouchIdExists[pointerId] = input.existsTouch(pointerId);
            if (input.getTouchX(pointerId) < mCard.CARD_WIDTH / 2 && input.getTouchY(pointerId) < mCard.CARD_HEIGHT / 2) {
                mCard.mTouchLocation[pointerId][0] = input.getTouchX(0);
                mCard.mTouchLocation[pointerId][1] = input.getTouchY(0);

                mCard.setPosition(mCard.mTouchLocation[pointerId][0] = input.getTouchX(0), mCard.mTouchLocation[pointerId][1] = input.getTouchY(0));
            }
        }

        //mCard.setPosition(mCard.mTouchLocation[0][0] = input.getTouchX(0), mCard.mTouchLocation[1][1] = input.getTouchY(0));


        // Get any touch events that have occurred since the last update
   /* List<TouchEvent> touchEvents = input.getTouchEvents();
    if (touchEvents.size() > 0) {

        // Store the touch event information
        for (TouchEvent touchEvent : touchEvents) {
            // Collection information on the touch event
            String touchEventInfo = touchEventTypeToString(touchEvent.type) +
                    String.format(" [%.0f,%.0f,ID=%d]",
                            touchEvent.x, touchEvent.y, touchEvent.pointer);*/

        // Update the card game object
        updateCardGameObjects(elapsedTime);

    }
    //Code that makes sure the card cant leave the world .
    public void ensuresCardCantLeaveWorld(){
        BoundingBox cardBound = mCard.getBound();
        if(cardBound.getLeft()<0){
            mCard.position.x-= cardBound.getLeft();
            hasLeft=true;
        }
        else if(cardBound.getRight()> LEVEL_WIDTH) {
            mCard.setPosition(mCard.position.x, mCard.position.y);
            hasLeft = true;
        }
        if(cardBound.getBottom()<0) {
            mCard.position.y -= cardBound.getBottom();
            hasLeft=true;
        }
        else if(cardBound.getTop()> LEVEL_HEIGHT) {
            mCard.position.y -= (cardBound.getTop() - LEVEL_HEIGHT);
            hasLeft=true;
        }
    }

    /**Update card game objetcts
     * @param elapsedTime Elapsed time info
     */
    private void updateCardGameObjects(ElapsedTime elapsedTime){

        ensuresCardCantLeaveWorld();

    }
    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        //Canvas canvas = new Canvas();
        // Matrix cardMoved = new Matrix();

        graphics2D.clear(Color.WHITE);
        textPaint.setTextSize(30);

        //Method to draw card onto the CardDemoScreen
        mCard.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);

        for (int pointerIdx = 0; pointerIdx < mCard.mTouchIdExists.length; pointerIdx++) {
            if (mCard.mTouchIdExists[pointerIdx]) {
                graphics2D.drawText("Pointer Id " + pointerIdx + ": Detected [" +
                                String.format("%.3f, %.3f]", mCard.mTouchLocation[pointerIdx][0], mCard.mTouchLocation[pointerIdx][1]),
                        0.0f, 100, textPaint);
            } else {
                graphics2D.drawText("Pointer Id " + pointerIdx + ": Not detected.",
                        0.0f, 150, textPaint);
            }
        }
         //This is just a tester method to see if the card detects touch events and it does

    }




}

