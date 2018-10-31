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
    private Bitmap mCard;
    /**
     * Create the Card game screen
     *
     * @param game Game to which this screen belongs
     */
    public CardDemoScreen(Game game) {
        super("CardScreen", game);

        //Not too sure if this codes belongs here or the Card class. - Edgars
        /*AssetManager assetManager = mGame.getAssetManager();

        // Load the blank card Bitmap
        assetManager.loadAndAddBitmap(
                "BlankCard", "img/blankCard.png");

        // Retrieve the blank card Bitmap
        mCard = assetManager.getBitmap("blankCard");
        */
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

        //Not too sure if this codes belongs here or the Card class. - Edgars
        /*//Get the screen size for positioning and sizing
        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();
        Paint bitmapPaint = new Paint();

        Rect sourceRect = new Rect(
                0, 0, mCard.getWidth(), mCard.getHeight());
        Rect destRect = new Rect(
                (int) (width * 0.1f), (int) (height * 0.1f), (int) (width * 0.9f), (int) (height * 0.35f));
        graphics2D.drawBitmap(mCard, sourceRect, destRect, bitmapPaint);
        */
    }
}
