package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;
import android.graphics.Color;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;

public class OptionsScreen extends GameScreen {

    private GameObject optionsBackGround;
    private LayerViewport optionsLayerViewport;
    private PushButton mBackButton;

    public OptionsScreen(Game game){
        super("OptionsScreen", game);

        // Load in the bitmaps used on the options screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("background", "img/optionsBackground.png");

        optionsBackGround = new GameObject(1000 / 2.0f,
                1000 / 2.0f, 1000, 1000, getGame()
                .getAssetManager().getBitmap("background"), this);

//                mBackButton = new PushButton(
//                mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.10f,
//                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
//                "img/BackArrow.png", "img/BackArrowSelected.png", this);
//        mBackButton.setPlaySounds(true, true);

    }

    @Override
    public void update(ElapsedTime elapsedTime) {

//        mBackButton.update(elapsedTime);
//        if (mBackButton.isPushTriggered())
//            mGame.getScreenManager().removeScreen(this);


    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.RED);
        optionsBackGround.draw(elapsedTime,graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        //null pointer on layerviewport

      //  mBackButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

    }
}
