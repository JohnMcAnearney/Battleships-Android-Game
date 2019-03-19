package uk.ac.qub.eeecs.game.BattleShips;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class MainMenu extends GameScreen {

    private Bitmap mBattleShipBackground;
    private int screenWidth = 0, screenHeight = 0;
    private Rect rect;
    private PushButton mStartButton, mInstructionsButton, mSettingsButton, mTitle;

    public MainMenu(Game game) {
        super("MenuScreen", game);
        // Load all of the assets
        loadAssets();
    }

    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
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
            //mTitle.update(elapsedTime);

            // Push trigger if statement to check if a specific button has been pressed, if it is, create the appropriate screen
            if (mStartButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new LoadingScreen(mGame));
            } else if (mInstructionsButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new InstructionsScreen(mGame));
            } else if (mSettingsButton.isPushTriggered()) {
                mGame.getScreenManager().addScreen(new SettingsScreen(mGame));
            }else if(mTitle.isPushTriggered()){
                // Add functionality - sound plays etc.
            }
        }
    }

    public void getWidthAndHeightOfScreen(IGraphics2D graphics2D) {

        if (screenHeight == 0 || screenWidth == 0) {
            screenWidth = graphics2D.getSurfaceWidth();
            screenHeight = graphics2D.getSurfaceHeight();
            createButton();
            updateRect();
        }
    }

    public void updateRect() {
        rect = new Rect(0, 0, screenWidth, screenHeight);
    }

    // Create the buttons and set the sound to true
    public void createButton() {

        mStartButton = new PushButton(mDefaultLayerViewport.getWidth() / 2,mDefaultLayerViewport.getHeight()  / 2, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "NewGameButton", "NewGameButtonP", this);
        mStartButton.setPlaySounds(true, true);

        mInstructionsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 5.5f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "InstructionsButton", "InstructionsButtonP", this);
        mInstructionsButton.setPlaySounds(true, true);

        mSettingsButton = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() / 3f, mDefaultLayerViewport.getWidth() / 4, mDefaultLayerViewport.getHeight() / 8, "SettingsButton", "SettingsButtonP", this);
        mSettingsButton.setPlaySounds(true, true);

        mTitle = new PushButton(mDefaultLayerViewport.getWidth() / 2, mDefaultLayerViewport.getHeight() /1.3f , mDefaultLayerViewport.getWidth() / 2f, mDefaultLayerViewport.getHeight()/ 3f, "Title", this);
        // Why is this a button? I think this is a mistake but it could be used for an easter egg
    }

    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        // Draw the battleship background
        drawScreenBackground(graphics2D);

        // Draw all the push buttons
        drawButtons(elapsedTime, graphics2D);
    }

    // Method which loads all the assets for the screen
    private void loadAssets()
    {
        AssetManager assetManager = mGame.getAssetManager();
        mGame.getAssetManager().loadAssets("txt/assets/MainMenuScreenAssets.JSON");
        mBattleShipBackground = assetManager.getBitmap("BattleshipBackground");
    }

    // Method which draws all of the push buttons
    private void drawButtons(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        mStartButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mInstructionsButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mSettingsButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mTitle.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }

    // Method which draws the screen background
    private void drawScreenBackground(IGraphics2D graphics2D)
    {
        getWidthAndHeightOfScreen(graphics2D);
        graphics2D.clear(Color.WHITE);
        graphics2D.drawBitmap(mBattleShipBackground,null,rect,null);
    }
}