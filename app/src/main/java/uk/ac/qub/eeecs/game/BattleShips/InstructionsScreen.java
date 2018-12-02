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

public class InstructionsScreen extends GameScreen {
    //User story <insert # here> MJ
    private Bitmap mInstructionBackground;
    private int screenWidth=0, screenHeight=0;
    private PushButton mBackButton;
    private Rect rect;

    public InstructionsScreen(Game game){
        super("InstructionsScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("BattleshipBackground", "img/background.jpg");
        mInstructionBackground = assetManager.getBitmap("BattleshipBackground");

    }

    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            // Update each button and transition if needed
            mBackButton.update(elapsedTime);
            if (mBackButton.isPushTriggered()){
                mGame.getScreenManager().removeScreen(this);
            }

        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mInstructionBackground,null,rect,null);
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
