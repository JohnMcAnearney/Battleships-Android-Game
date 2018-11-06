package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.IOException;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.world.GameScreen;

/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class CardDemoScreen extends GameScreen {
    //blankCard bitmap variable
    private Card mCard;
    /**
     * Create the Card game screen
     *
     * @param game Game to which this screen belongs
     */
    public CardDemoScreen(Game game) {
        super("CardScreen", game);
        //Initialising a card object within the cardDemoScreen so that it can be drawn by the draw method.
        mCard = new Card(200,200,this);
    }
    // /////////////////////////////////////////////////////////////////////////
    // Methods
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
    }
    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
        //Method to draw card onto the CardDemoScreen
        mCard.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
    }
}
