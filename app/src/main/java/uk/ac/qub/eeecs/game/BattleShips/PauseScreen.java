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

public class PauseScreen extends GameScreen {

    private Bitmap mPauseBackground;
    private int screenWidth=0, screenHeight=0;
    private PushButton mBackButton;
    private Rect rect;

    public PauseScreen(Game game){
        super("PauseScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("BattleshipBackground", "img/background.jpg");
        mPauseBackground = assetManager.getBitmap("BattleshipBackground");

    }
    @Override
    public void update(ElapsedTime elapsedTime) {
        //Used to process any touch input within the screen
        Input input = mGame.getInput();

        //A list which stores the history of touch inputs to allow for appropriate processing
        List<TouchEvent> touchEvents = input.getTouchEvents();
        //If statement to process the possible inputs
        if (touchEvents.size() > 0) {
            mBackButton.update(elapsedTime);
            //If back button is pressed, remove the current screen to return to the main menu
            if (mBackButton.isPushTriggered()){
                mGame.getScreenManager().removeScreen(this);
            }

        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mPauseBackground,null,rect,null);
        mBackButton.draw(elapsedTime,graphics2D);
    }

    public void getWidthAndHeightOfScreen(IGraphics2D graphics2D) {

        if (screenHeight == 0 || screenWidth == 0) {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            updateRect();
            createButton();
        }
    }

    public void updateRect()
    {
        rect = new Rect(0,0,screenWidth,screenHeight);
    }

    public void createButton()
    {
        mBackButton = new PushButton(screenWidth/2,screenHeight/2,screenWidth/4,screenHeight/2,"BackArrow",this);
    }
}
