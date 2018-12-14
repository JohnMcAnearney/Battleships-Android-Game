package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class SettingsScreen extends GameScreen {
    private Bitmap mSettingsBackground;
    private int screenWidth = 0, screenHeight = 0;
    private Rect rect;
    private PushButton mBackButton;

    //Constructor;
    public SettingsScreen(Game game) {
        super("SettingsScreen", game);

    //Load the bitmap for the settings screen
    AssetManager assetManager = mGame.getAssetManager();
    assetManager.loadAndAddBitmap("SettingBackground","img/SettingsBackground.png" );
    assetManager.loadAndAddBitmap("SettingsBackButton", "img/BackArrow.png");
    assetManager.loadAndAddBitmap("SettingsTitle", "img/SettingsTitle.png");

        mSettingsBackground = assetManager.getBitmap("SettingBackground");
    }




    @Override
    public void update(ElapsedTime elapsedTime) {
        //For the touch events since update
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if(touchEvents.size()>0){
            mBackButton.update(elapsedTime);
            if(mBackButton.isPushTriggered()){
                //back to mainmenu
                mGame.getScreenManager().addScreen(new MainMenu(mGame));
            }
        }
    }
    /**
     * drawing
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mSettingsBackground, null, rect, null);
        mBackButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

    }
    public void getWidthAndHeightOfScreen(IGraphics2D graphics2D) {

        if (screenHeight == 0 || screenWidth == 0) {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            updateRect();
            createButton();
        }

    }
    public void updateRect() {
        rect = new Rect(0, 0, screenWidth, screenHeight);
    }


    public void createButton() {
        //Trigger Buttons
        mBackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.10f,
                "SettingsBackButton", this);

    }


}
