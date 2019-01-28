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
    private int screenWidth = 0, screenHeight = 0;
    private Rect rect;
    private PushButton mStartButton, mInstructionsButton, mSettingsButton, mPauseButton;

    public MainMenu(Game game) {
        super("MenuScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BattleIcon", "img/battlebutton.png");
        assetManager.loadAndAddBitmap("BattleshipBackground", "img/background.jpg");
        assetManager.loadAndAddBitmap("InstructionsButton", "img/InstructionsButton.png");
        assetManager.loadAndAddBitmap("SettingsButton", "img/optionsButton.png");
        assetManager.loadAndAddBitmap("PauseButton", "img/Pause.png");

        mBattleShipBackground = assetManager.getBitmap("BattleshipBackground");

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
            mSettingsButton.update(elapsedTime);
            mPauseButton.update(elapsedTime);


            if (mStartButton.isPushTriggered()) {

            } else if (mInstructionsButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));
            }else if (mSettingsButton.isPushTriggered()){
                mGame.getScreenManager().addScreen(new SettingsScreen(mGame));
            //Push trigger to check if the Pause button has been pressed, if it is, create new Pause Screen
            }else if (mPauseButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new PauseScreen(mGame));
            }


        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mBattleShipBackground, null, rect, null);
        mStartButton.draw(elapsedTime, graphics2D);
        mInstructionsButton.draw(elapsedTime, graphics2D);
        mSettingsButton.draw(elapsedTime, graphics2D);
        mPauseButton.draw(elapsedTime, graphics2D);

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
        mStartButton = new PushButton(screenWidth / 2, screenHeight / 2, screenWidth / 4, screenHeight / 2, "BattleIcon", this);
        mInstructionsButton = new PushButton(screenWidth / 2, 7 * screenHeight / 8, screenWidth / 4, screenHeight / 4, "InstructionsButton", "BattleIcon", this);
        mSettingsButton = new PushButton(screenWidth / 8, screenHeight / 4, screenWidth / 4, screenHeight / 4, "SettingsButton",  this);
        mPauseButton = new PushButton((screenWidth / 100) * 90,(screenHeight / 100) * 10, (screenWidth / 100) * 15, (screenHeight / 100) * 18, "PauseButton", this);
    }
}