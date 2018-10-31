package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
//General card class - Edgars
public class Card {
    private String name;
    //blankCard bitmap variable
    private Bitmap mCard;

    /**
     * @param mGame refers to a Game class.
     */
    Card(String name, Game mGame) {
        this.name = name;

        AssetManager assetManager = mGame.getAssetManager();

        // Load the blank card Bitmap
        assetManager.loadAndAddBitmap(
                "BlankCard", "img/blankCard.png");

        // Retrieve the blank card Bitmap
        mCard = assetManager.getBitmap("blankCard");
    }
    //Method to draw the Bitmap
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.WHITE);
        //Get the screen size for positioning and sizing
        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();
        Paint bitmapPaint = new Paint();

        Rect sourceRect = new Rect(
                0, 0, mCard.getWidth(), mCard.getHeight());
        Rect destRect = new Rect(
                (int) (width * 0.1f), (int) (height * 0.1f), (int) (width * 0.9f), (int) (height * 0.35f));
        graphics2D.drawBitmap(mCard, sourceRect, destRect, bitmapPaint);
    }
}
