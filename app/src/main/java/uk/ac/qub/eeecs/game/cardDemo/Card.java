package uk.ac.qub.eeecs.game.cardDemo;

import android.graphics.Bitmap;
import android.net.wifi.hotspot2.pps.Credential;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
//General Card class - Edgars
public class Card extends Sprite {
    //Setting up default size for cards
    private static final int CARD_HEIGHT = 240;
    private static final int CARD_WIDTH = 160;
    //Defining the card background bitmap
    private Bitmap cardBackground;
    //Setting up constructor from the Sprite super class
    public Card(float x, float y, GameScreen gameScreen) {
        super(x, y, CARD_WIDTH, CARD_HEIGHT, null, gameScreen);
        //Setting up the asset manager
        AssetManager assetManager = gameScreen.getGame().getAssetManager();
        //Loading the blankCard image onto a bitmap named "cardBackground"
        assetManager.loadAndAddBitmap("cardBackground", "img/blankCard.png");
        //Setting the bitmap of mBitmap, which the constructor uses, to the "cardBackground" bitmap loaction.
        mBitmap = assetManager.getBitmap("cardBackground");
    }
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport,
                     ScreenViewport screenViewport) {
        super.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
    }
}
