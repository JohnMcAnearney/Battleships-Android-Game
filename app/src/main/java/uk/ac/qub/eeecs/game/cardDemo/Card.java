package uk.ac.qub.eeecs.game.cardDemo;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.wifi.hotspot2.pps.Credential;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.engine.input.TouchHandler;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.Pool;
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
//    protected float[] mAcceleration = new float[3];
    protected boolean[] mTouchIdExists = new boolean[4];  //boolean array of size 4 as i don't think we will need anymore touches
    protected float[][] mTouchLocation = new float[mTouchIdExists.length][2]; //basically just gets how many touches there are and then uses it as x value then y value to 2
    protected Game mGame;

    public BoundingBox cardBound = new BoundingBox(CARD_WIDTH/2, CARD_HEIGHT/2, CARD_WIDTH/4, CARD_HEIGHT/4);
        //setting the bounding box for the card so that my method within CardDemoScreen can work

    private boolean showingBackOfCard = false;
    private AssetManager assetManager;
    private TouchEvent cardTouchEvent;
    protected List<TouchEvent> listOfEvents;

    //Defining the card background bitmap
    //private Bitmap cardBackground;

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


    public boolean handleTouchEvents( List<TouchEvent> listOfEvents){      //this is a list storing any touch events occuring e.g. a press event alongside a drag event etc
        //  Type       name    array        syntax for enhanced for loop
        for(TouchEvent event : listOfEvents) {
              this.onEvent(event);      //this calls the method for each event to decide what the event is, and what to do to the event
          }

          return true;
    }

    public void onEvent(TouchEvent event) {

        if(mBound.contains(event.x, event.y))
        switch(event.type){
            case 0:
                //touch event down
                this.setPosition(event.x, event.y);
                break;
            case 1:
                //touch event up
                this.setPosition(event.x, event.y);
                        break;
            case 2:
                //touch event dragged
                this.setPosition(event.x, event.y);
                break;

        }
    }


    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport,
                     ScreenViewport screenViewport) {
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
    }

    @Override
    public void update(ElapsedTime elapsedTime) {   //this method is updating the card event every nanosecond to see if there are any changes
        this.handleTouchEvents(mGame.getInput().getTouchEvents());      //this is checking on the card to see if there is any touch event
        this.onEvent(cardTouchEvent);
        // Process any touch events occurring since the last update
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
