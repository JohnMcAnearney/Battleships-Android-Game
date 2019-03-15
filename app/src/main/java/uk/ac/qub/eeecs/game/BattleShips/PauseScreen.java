package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
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

    private Bitmap mPauseBackground, mPause;
    private int screenWidth=0, screenHeight=0;
    private PushButton mBackButton, mVolumeButton, mInstructionsButton, mSettingsButton;
    private Rect rect;

    public PauseScreen(Game game){
        super("PauseScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BackArrow", "img/BackB.png");
        assetManager.loadAndAddBitmap("BackArrowP", "img/BackBPressed.png");
        assetManager.loadAndAddBitmap("BattleshipBackground", "img/background.jpg");
        assetManager.loadAndAddBitmap("VolumeOn", "img/VolumeOn.png");
        assetManager.loadAndAddBitmap("VolumeOff", "img/VolumeOff.png");
        assetManager.loadAndAddBitmap("InstructionsButton", "img/InstructionsB.png");
        assetManager.loadAndAddBitmap("InstructionsButtonP", "img/InstructionsBPressed.png");
        assetManager.loadAndAddBitmap("SettingsButton", "img/SettingsB.png");
        assetManager.loadAndAddBitmap("SettingsButtonP", "img/SettingsBPressed.png");
        assetManager.loadAndAddBitmap("Paused", "img/Paused.png");
        mPauseBackground = assetManager.getBitmap("BattleshipBackground");
        mPause = assetManager.getBitmap("Paused");
    }

    @Override
    public void update(ElapsedTime elapsedTime)
    {
        // Used to process any touch input within the screen
        Input input = mGame.getInput();

        // A list which stores the history of touch inputs to allow for appropriate processing
        List<TouchEvent> touchEvents = input.getTouchEvents();
        // If statement to process the possible inputs
        if (touchEvents.size() > 0)
        {
            // Update all the push buttons if a touch event is detected.
            mBackButton.update(elapsedTime);
            mVolumeButton.update(elapsedTime);
            mInstructionsButton.update(elapsedTime);
            mSettingsButton.update(elapsedTime);
            // If back button is pressed, remove the current screen to return to the main menu
            if (mBackButton.isPushTriggered())
            {
                mGame.getScreenManager().removeScreen(this);
            }
            // If instructions button is triggered, add a new screen to the instruction screen.
            else if (mInstructionsButton.isPushTriggered())
            {
                mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));
            }
            // If settings button is triggered, add a new screen to the settings screen.
            else if (mSettingsButton.isPushTriggered())
            {
                mGame.getScreenManager().addScreen(new SettingsScreen(mGame));
            }
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        // Drawing the screen background
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mPauseBackground,null,rect,null);

        // Drawing the buttons and pause text bitmap
        graphics2D.drawBitmap(mPause, null, drawRectangle(mDefaultLayerViewport.getWidth() * 0.1f, mDefaultLayerViewport.getHeight() * 0.5f, mDefaultLayerViewport.getWidth() * 0.5f, mDefaultLayerViewport.getHeight() /1.3f + mDefaultLayerViewport.getHeight()/ 1.5f), null);
        mBackButton.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        mVolumeButton.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        mInstructionsButton.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
        mSettingsButton.draw(elapsedTime,graphics2D,mDefaultLayerViewport,mDefaultScreenViewport);
    }

    public void getWidthAndHeightOfScreen(IGraphics2D graphics2D)
    {
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

    // Method which allows you to put in the float values of the destination rectangle
    public Rect drawRectangle(float left, float top, float right, float bottom)
    {

        Rect rectangle = new Rect((int)left, (int)top, (int)right, (int)bottom);
        return rectangle;
    }

    // Method which creates all the push buttons
    public void createButton()
    {
        mInstructionsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 5.5f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "InstructionsButton", "InstructionsButtonP", this);
        mSettingsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 3f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "SettingsButton", "SettingsButtonP", this);
        mBackButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.95f, mDefaultLayerViewport.getHeight() * 0.1f, mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.1f, "BackArrow", "BackArrowP", this);
        mVolumeButton = new PushButton(mDefaultLayerViewport.getWidth() * 0.05f, mDefaultLayerViewport.getHeight() * 0.1f, mDefaultLayerViewport.getWidth() * 0.075f, mDefaultLayerViewport.getHeight() * 0.1f, "VolumeOn", "VolumeOff", this);
    }
}
