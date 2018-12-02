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
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;

public class MainMenu extends GameScreen {


    private LayerViewport mSpaceLayerViewport;
    private Bitmap mBattleShipBackground;
    private int screenWidth=0, screenHeight=0;
    private Rect rect;
    private PushButton mStartButton, mInstructionsButton;

    public MainMenu(Game game)
    {
        super("MenuScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BattleIcon", "img/battlebutton.png");
        assetManager.loadAndAddBitmap("battleshipbackground", "img/background.jpg");
        assetManager.loadAndAddBitmap("InstructionsButton", "img/InstructionsButton.png");
        mBattleShipBackground = assetManager.getBitmap("battleshipbackground");

    }


    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            // Update each button and transition if needed
            mStartButton.update(elapsedTime);
            mInstructionsButton.update(elapsedTime);
            if(mStartButton.isPushTriggered()) {

            }else if(mInstructionsButton.isPushTriggered()){
                mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));
            }


        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mBattleShipBackground,null,rect,null);
        mStartButton.draw(elapsedTime,graphics2D);
        mInstructionsButton.draw(elapsedTime, graphics2D);

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
        mStartButton = new PushButton(screenWidth/2,screenHeight/2,screenWidth/4,screenHeight/2,"BattleIcon",this);
        mInstructionsButton = new PushButton(screenWidth/2, 7*screenHeight/8, screenWidth/4, screenHeight/4, "InstructionsButton", "BattleIcon", this );
    }
}
