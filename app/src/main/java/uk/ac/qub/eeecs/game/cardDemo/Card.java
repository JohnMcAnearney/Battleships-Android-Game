package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.wifi.hotspot2.pps.Credential;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;

//General Card class - Edgars
public class Card extends Sprite {
    //Setting up default size for cards
    protected static final int CARD_HEIGHT = 240;
    protected static final int CARD_WIDTH = 160;

    //Setting up the variables to store if there are actually any touches occurring
    protected float[] mAcceleration = new float[3];
    protected boolean[] mTouchIdExists = new boolean[4];  //boolean array of size 4 as i don't think we will need anymore touches
    protected float[][] mTouchLocation = new float[mTouchIdExists.length][2]; //basically just gets how many touches there are and then uses it as x value then y value to 2
    protected Game mGame;
    private boolean showingBackOfCard = false;
    private AssetManager assetManager;

    //Defining the card background bitmap
    private Bitmap cardBackground;

    //Setting up constructor from the Sprite super class
    public Card(float x, float y, GameScreen gameScreen) {
        super(x, y, CARD_WIDTH, CARD_HEIGHT, null, gameScreen);
        //Setting up the asset manager
        assetManager = gameScreen.getGame().getAssetManager();
        //Loading the blankCard image onto a bitmap named "cardBackground"
        assetManager.loadAndAddBitmap("cardBackground", "img/blankCard.png");
        assetManager.loadAndAddBitmap("backOfCard", "img/CardBackground1.png");
        //Setting the bitmap of mBitmap, which the constructor uses, to the "cardBackground" bitmap loaction.
        mBitmap = assetManager.getBitmap("cardBackground");
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport,
                     ScreenViewport screenViewport) {
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
    }

    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        Input input = mGame.getInput();


        // Store acceleration information for the device
        mAcceleration[0] = input.getAccelX();
        mAcceleration[1] = input.getAccelY();
        mAcceleration[2] = input.getAccelZ();

//        // Store touch point information.
//        for (int pointerId = 0; pointerId < mTouchIdExists.length; pointerId++) {
//            mTouchIdExists[pointerId] = input.existsTouch(pointerId);
//            if (mTouchIdExists[pointerId]) {
//                mTouchLocation[pointerId][0] = input.getTouchX(0);
//                mTouchLocation[pointerId][1] = input.getTouchY(0);
//            }
//
//        }

        Matrix m = new Matrix();
        for(int pointerId = 0; pointerId < mTouchIdExists.length; pointerId++){
            mTouchIdExists[pointerId] = input.existsTouch(pointerId);
            if(input.getTouchX(pointerId) < CARD_WIDTH/2 && input.getTouchY(pointerId) < CARD_HEIGHT/2) {
                mTouchLocation[pointerId][0] = input.getTouchX(0);
                mTouchLocation[pointerId][1] = input.getTouchY(0);

                m.setTranslate(mTouchLocation[pointerId][0] = input.getTouchX(0), mTouchLocation[pointerId][1] = input.getTouchY(0));
                setPosition(mTouchLocation[pointerId][0] = input.getTouchX(0), mTouchLocation[pointerId][1] = input.getTouchY(0));
            }

        }


        // Get any touch events that have occurred since the last update
   /* List<TouchEvent> touchEvents = input.getTouchEvents();
    if (touchEvents.size() > 0) {

        // Store the touch event information
        for (TouchEvent touchEvent : touchEvents) {
            // Collection information on the touch event
            String touchEventInfo = touchEventTypeToString(touchEvent.type) +
                    String.format(" [%.0f,%.0f,ID=%d]",
                            touchEvent.x, touchEvent.y, touchEvent.pointer);*/




    }

    public void showBackOfCard()
    {

        if(showingBackOfCard == false) {
            mBitmap = assetManager.getBitmap("backOfCard");

        }
        else if(showingBackOfCard == true)
        {
            mBitmap = assetManager.getBitmap("cardBackground");
        }
        showingBackOfCard = !showingBackOfCard;
    }

}
